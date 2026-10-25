use std::{
    borrow::Cow,
    num::{NonZero, NonZeroUsize},
    sync::Arc,
};

use anyhow::bail;
use arrow::datatypes::DataType;
use hashbrown::HashMap;
use indexmap::IndexMap;
use nom::{
    IResult, Parser,
    branch::alt,
    bytes::complete::tag,
    character::complete::{alpha1, alphanumeric1, multispace0, multispace1},
    combinator::recognize,
    error::Error as NomErr,
    multi::{many0_count, separated_list1},
    sequence::pair,
};

/// Maximum nesting depth while resolving struct schemas.
///
/// Real WPILib schemas nest a handful of levels; anything deeper is either a bug or a
/// self-referential schema that must not be allowed to exhaust the stack.
const MAX_STRUCT_DEPTH: usize = 32;

#[derive(Debug, Clone, Copy, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub enum WpiLibStructPrimitives {
    Bool,
    Char,
    Int8,
    Int16,
    Int32,
    Int64,
    Uint8,
    Uint16,
    Uint32,
    Uint64,
    Float,
    Double,
}
impl TryFrom<&str> for WpiLibStructPrimitives {
    type Error = ();

    fn try_from(value: &str) -> Result<Self, Self::Error> {
        Ok(match value {
            "bool" => Self::Bool,
            "char" => Self::Char,
            "int8" => Self::Int8,
            "int16" => Self::Int16,
            "int32" => Self::Int32,
            "int64" => Self::Int64,
            "uint8" => Self::Uint8,
            "uint16" => Self::Uint16,
            "uint32" => Self::Uint32,
            "uint64" => Self::Uint64,
            "float" | "float32" => Self::Float,
            "double" | "float64" => Self::Double,
            _ => return Err(()),
        })
    }
}
impl WpiLibStructPrimitives {
    pub fn size(self) -> usize {
        use WpiLibStructPrimitives::*;
        match self {
            Bool | Char | Int8 | Uint8 => 1,
            Int16 | Uint16 => 2,
            Int32 | Uint32 | Float => 4,
            Int64 | Uint64 | Double => 8,
        }
    }

    /// Whether this primitive is a single ASCII/UTF-8 byte (`char`).
    ///
    /// `char` decodes to a [`DataType::Utf8`] value, but unlike `string` its arrays are raw
    /// bytes rather than WPILog length-prefixed strings.
    #[must_use]
    pub const fn is_char(self) -> bool {
        matches!(self, Self::Char)
    }

    #[must_use]
    /// Converts the current type to an Arrow primitive [`DataType`]
    pub const fn datatype(&self) -> DataType {
        match self {
            Self::Bool => DataType::Boolean,
            Self::Char => DataType::Utf8,
            Self::Int8 => DataType::Int8,
            Self::Int16 => DataType::Int16,
            Self::Int32 => DataType::Int32,
            Self::Int64 => DataType::Int64,
            Self::Uint8 => DataType::UInt8,
            Self::Uint16 => DataType::UInt16,
            Self::Uint32 => DataType::UInt32,
            Self::Uint64 => DataType::UInt64,
            Self::Float => DataType::Float32,
            Self::Double => DataType::Float64,
        }
    }
}

#[derive(Debug, Clone, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub enum UnresolvedWpiLibStructType {
    Primitive(WpiLibStructPrimitives),
    Custom(String),
}

impl<'a> From<Cow<'a, str>> for UnresolvedWpiLibStructType {
    fn from(value: Cow<'a, str>) -> Self {
        WpiLibStructPrimitives::try_from(value.as_ref())
            .map_or_else(|_| Self::Custom(value.into_owned()), Self::Primitive)
    }
}

/// A resolved struct type.
///
/// Nested structs are shared through [`Arc`] so that resolving a schema once (see
/// [`StructSchemas`]) also makes every instance decode cheap: building a value only clones an
/// `Arc` per nested struct instead of deep-copying the nested schema.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum WpiLibStructType {
    Primitive(WpiLibStructPrimitives),
    Custom(Arc<WpiLibStructSchema<WpiLibStructType>>),
}

impl WpiLibStructType {
    pub fn size(&self) -> usize {
        match self {
            Self::Primitive(p) => p.size(),
            Self::Custom(s) => s.size(),
        }
    }
}

/// The symbolic values of an `enum` field, if the schema declared any.
///
/// Decoding always treats the field as its underlying integer; the names are kept for
/// diagnostics and for callers that want to render them.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum WpiLibStructValues {
    Value,
    Enum(Arc<HashMap<String, i64>>),
}

#[derive(Debug, Clone, PartialEq, Eq)]
pub struct WpiLibStructData<ValueType> {
    /// A Some value dictates that this is an array
    pub count: Option<NonZeroUsize>,
    pub value: WpiLibStructValues,
    pub ty: ValueType,
}

impl WpiLibStructData<WpiLibStructType> {
    pub fn size(&self) -> usize {
        self.ty.size() * self.count.map_or(1, NonZero::get)
    }
}

#[derive(Debug, Clone, PartialEq, Eq)]
pub struct WpiLibStructSchema<ValueType> {
    pub fields: IndexMap<String, WpiLibStructData<ValueType>>,
}

impl WpiLibStructSchema<WpiLibStructType> {
    #[must_use]
    pub fn size(&self) -> usize {
        self.fields.iter().map(|(_, data)| data.size()).sum()
    }
}

pub fn identifier(input: &[u8]) -> IResult<&[u8], &[u8]> {
    recognize(pair(
        alt((alpha1, tag("_"))),
        many0_count(alt((alphanumeric1, tag("_")))),
    ))
    .parse(input)
}

/// WPILib schemas spell nested struct types as `struct:Translation2d`.
///
/// Fully-qualified names (`edu.wpi.first.math.geometry.Translation2d`) are accepted as well;
/// resolution falls back to the last path segment.
fn type_identifier(input: &[u8]) -> IResult<&[u8], &[u8]> {
    recognize(pair(
        nom::combinator::opt(tag("struct:")),
        separated_list1(tag("."), identifier),
    ))
    .parse(input)
}

fn schema_error(input: &[u8]) -> nom::Err<NomErr<&[u8]>> {
    nom::Err::Failure(NomErr::new(input, nom::error::ErrorKind::Verify))
}

fn struct_parser(
    data: &[u8],
) -> IResult<&[u8], (String, WpiLibStructData<UnresolvedWpiLibStructType>)> {
    let (data, wpienum) = enum_parser(data)
        .map(|(d, h)| (d, Some(h)))
        .unwrap_or((data, None));

    let (data, _) = multispace0(data)?;
    let (data, typename) = type_identifier(data)?;
    let (data, _) = multispace1(data)?;
    let (data, identifier_name) = identifier(data)?;
    let (mut data, _) = multispace0::<_, NomErr<_>>(data)?;

    let count = match nom::combinator::opt(tag("[")).parse(data)? {
        (after, Some(_)) => {
            let (after, _) = multispace0(after)?;
            let (after, n) = nom::character::complete::usize(after)?;
            let (after, _) = multispace0(after)?;
            let (after, _) = tag("]")(after)?;
            let Some(count) = NonZeroUsize::new(n) else {
                // WPILib does not allow zero-sized arrays.
                return Err(schema_error(after));
            };
            data = after;
            Some(count)
        }
        (after, None) => {
            data = after;
            None
        }
    };

    let name = String::from_utf8_lossy(identifier_name).into_owned();
    let ty = UnresolvedWpiLibStructType::from(String::from_utf8_lossy(typename));

    let wpistruct = if let Some(wenum) = wpienum {
        WpiLibStructValues::Enum(Arc::new(wenum))
    } else {
        WpiLibStructValues::Value
    };

    Ok((
        data,
        (
            name,
            WpiLibStructData {
                count,
                value: wpistruct,
                ty,
            },
        ),
    ))
}

fn enum_parser(data: &[u8]) -> IResult<&[u8], HashMap<String, i64>> {
    let mut values = HashMap::new();

    let (data, _) = tag("enum")(data)?;
    let (data, _) = multispace0(data)?;
    let (mut data, _) = tag("{")(data)?;

    loop {
        let (new_data, _) = multispace0(data)?;

        if let Ok((remaining, _)) = tag::<_, _, NomErr<_>>("}")(new_data) {
            data = remaining;
            break;
        }

        let (new_data, identifier) = identifier(new_data)?;
        let identifier = String::from_utf8_lossy(identifier).into_owned();

        let (new_data, _) = multispace0(new_data)?;
        let (new_data, _) = tag("=")(new_data)?;
        let (new_data, _) = multispace0(new_data)?;
        let (new_data, value) = nom::character::complete::i64(new_data)?;

        let (new_data, _) = multispace0(new_data)?;
        values.insert(identifier, value);

        // Enum entries may be separated by `,` or `;`, and the final entry may omit the
        // separator entirely before the closing brace.
        let (new_data, _) = nom::combinator::opt(alt((
            tag::<_, _, NomErr<_>>(";"),
            tag::<_, _, NomErr<_>>(","),
        )))
        .parse(new_data)?;
        data = new_data;
    }

    Ok((data, values))
}

impl WpiLibStructSchema<UnresolvedWpiLibStructType> {
    /// Schemas for the geometry and kinematics structs built into WPILib.
    ///
    /// Some WPILogs reference these types without including a `.schema/` entry, so we
    /// install their canonical layouts up front. Real schemas found in a log always
    /// overwrite these fallbacks.
    #[must_use]
    pub fn builtin_wpilib_schemas() -> Vec<(&'static str, Self)> {
        [
            ("struct:Translation2d", "double x;double y;"),
            ("struct:Translation3d", "double x;double y;double z;"),
            ("struct:Rotation2d", "double value;"),
            ("struct:Quaternion", "double w;double x;double y;double z;"),
            ("struct:Rotation3d", "struct:Quaternion q;"),
            (
                "struct:Pose2d",
                "struct:Translation2d translation;struct:Rotation2d rotation;",
            ),
            (
                "struct:Pose3d",
                "struct:Translation3d translation;struct:Rotation3d rotation;",
            ),
            (
                "struct:Transform2d",
                "struct:Translation2d translation;struct:Rotation2d rotation;",
            ),
            (
                "struct:Transform3d",
                "struct:Translation3d translation;struct:Rotation3d rotation;",
            ),
            ("struct:Twist2d", "double dx;double dy;double dtheta;"),
            (
                "struct:Twist3d",
                "double dx;double dy;double dz;double rx;double ry;double rz;",
            ),
            ("struct:ChassisSpeeds", "double vx;double vy;double omega;"),
            (
                "struct:SwerveModuleState",
                "double speed;struct:Rotation2d angle;",
            ),
            (
                "struct:SwerveModulePosition",
                "double distance;struct:Rotation2d angle;",
            ),
        ]
        .into_iter()
        .map(|(name, schema)| {
            (
                name,
                Self::parse(schema.as_bytes())
                    .expect("built-in WPILib struct schema must be valid"),
            )
        })
        .collect()
    }

    /// Parses a WPILib struct schema such as `double x;double y;`.
    ///
    /// Parsing fails closed: a schema that cannot be understood completely is rejected rather
    /// than truncated. A truncated schema silently changes the byte layout of every value that
    /// uses it, which is far worse than dropping the topic.
    pub fn parse(mut data: &[u8]) -> Result<Self, anyhow::Error> {
        let mut fields = IndexMap::new();

        loop {
            data = match multispace0::<_, NomErr<_>>(data) {
                Ok((remaining, _)) => remaining,
                Err(_) => data,
            };

            if data.is_empty() {
                break;
            }

            let (remaining, (name, inner)) = struct_parser(data).map_err(|_| {
                anyhow::anyhow!(
                    "invalid struct schema near {:?}",
                    String::from_utf8_lossy(data)
                )
            })?;

            if fields.insert(name.clone(), inner).is_some() {
                bail!("duplicate struct schema field {name:?}");
            }

            match tag::<_, _, NomErr<_>>(";")(remaining) {
                Ok((remaining, _)) => data = remaining,
                Err(_) => {
                    // The final field may omit its trailing semicolon, but nothing else may
                    // follow it.
                    data = match multispace0::<_, NomErr<_>>(remaining) {
                        Ok((remaining, _)) => remaining,
                        Err(_) => remaining,
                    };
                    if !data.is_empty() {
                        bail!(
                            "invalid struct schema near {:?}",
                            String::from_utf8_lossy(data)
                        );
                    }
                    break;
                }
            }
        }

        Ok(Self { fields })
    }
}

/// Why a struct schema could not be resolved to concrete field types.
#[derive(Debug, Clone, PartialEq, Eq)]
pub enum ResolveError {
    /// No schema with this name was ever registered.
    NotFound(String),
    /// The schema refers to itself, directly or through other schemas.
    Recursive(String),
}

impl std::fmt::Display for ResolveError {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        match self {
            Self::NotFound(name) => write!(f, "unknown struct type {name}"),
            Self::Recursive(chain) => write!(f, "recursive struct schema: {chain}"),
        }
    }
}

impl std::error::Error for ResolveError {}

fn normalize_name(name: &str) -> &str {
    name.strip_prefix("struct:").unwrap_or(name)
}

fn last_segment(name: &str) -> &str {
    name.rsplit('.').next().unwrap_or(name)
}

/// The set of struct schemas known to one log, plus a cache of resolved layouts.
///
/// Resolution walks the schema graph and is memoized per type name: decoding a sampled struct
/// only clones an [`Arc`] instead of re-resolving (and re-cloning the field names of) the whole
/// schema on every value.
#[derive(Clone, Debug, Default)]
pub struct StructSchemas {
    raw: HashMap<String, WpiLibStructSchema<UnresolvedWpiLibStructType>>,
    resolved: HashMap<String, Result<Arc<WpiLibStructSchema<WpiLibStructType>>, ResolveError>>,
}

impl StructSchemas {
    /// The cache key of a type name: aliases (`struct:X` and `X`) share one entry.
    fn cache_key(name: &str) -> String {
        normalize_name(name).to_owned()
    }

    #[must_use]
    pub fn new() -> Self {
        let mut schemas = Self::default();
        for (name, schema) in WpiLibStructSchema::builtin_wpilib_schemas() {
            schemas.insert(name, schema);
        }
        schemas
    }

    /// Registers `schema` under `name`, its bare form, and its `struct:`-prefixed form.
    pub fn insert(
        &mut self,
        name: impl Into<String>,
        schema: WpiLibStructSchema<UnresolvedWpiLibStructType>,
    ) {
        let name = name.into();
        let bare = normalize_name(&name).to_owned();
        let prefixed = if name.starts_with("struct:") {
            name.clone()
        } else {
            format!("struct:{name}")
        };

        self.raw.insert(name, schema.clone());
        self.raw.insert(bare, schema.clone());
        self.raw.insert(prefixed, schema);

        // Any resolution may have used the previous version of this schema.
        self.resolved.clear();
    }

    #[must_use]
    pub fn raw(&self, name: &str) -> Option<&WpiLibStructSchema<UnresolvedWpiLibStructType>> {
        let key = self.canonical(name)?;
        self.raw.get(key)
    }

    #[must_use]
    fn canonical<'a>(&'a self, name: &'a str) -> Option<&'a str> {
        let candidates = [
            name,
            normalize_name(name),
            last_segment(name),
            normalize_name(last_segment(name)),
        ];
        candidates
            .into_iter()
            .find(|candidate| self.raw.contains_key(*candidate))
    }

    /// Resolves a schema (by any of its aliases) to a concrete, shareable layout.
    pub fn resolve(
        &mut self,
        name: &str,
    ) -> Result<Arc<WpiLibStructSchema<WpiLibStructType>>, ResolveError> {
        let key = self
            .canonical(name)
            .ok_or_else(|| ResolveError::NotFound(name.to_owned()))?
            .to_owned();

        if let Some(cached) = self.resolved.get(&Self::cache_key(&key)) {
            return cached.clone();
        }

        let result = self.resolve_key(&key, &mut Vec::new());
        self.resolved.insert(Self::cache_key(&key), result.clone());
        result
    }

    fn resolve_key(
        &mut self,
        key: &str,
        chain: &mut Vec<String>,
    ) -> Result<Arc<WpiLibStructSchema<WpiLibStructType>>, ResolveError> {
        let cache_key = Self::cache_key(key);
        if let Some(cached) = self.resolved.get(&cache_key) {
            return cached.clone();
        }

        let normalized = normalize_name(key).to_owned();
        if chain.contains(&normalized) {
            let mut cycle = chain.join(" -> ");
            cycle.push_str(" -> ");
            cycle.push_str(&normalized);
            return Err(ResolveError::Recursive(cycle));
        }
        if chain.len() >= MAX_STRUCT_DEPTH {
            return Err(ResolveError::Recursive(format!(
                "{} -> {normalized}",
                chain.join(" -> ")
            )));
        }

        let schema = self
            .raw
            .get(key)
            .cloned()
            .ok_or_else(|| ResolveError::NotFound(key.to_owned()))?;

        chain.push(normalized);
        let resolved = self.resolve_fields(&schema, chain);
        chain.pop();
        let resolved = Arc::new(resolved?);

        self.resolved.insert(cache_key, Ok(Arc::clone(&resolved)));
        Ok(resolved)
    }

    fn resolve_fields(
        &mut self,
        schema: &WpiLibStructSchema<UnresolvedWpiLibStructType>,
        chain: &mut Vec<String>,
    ) -> Result<WpiLibStructSchema<WpiLibStructType>, ResolveError> {
        let mut fields = IndexMap::with_capacity(schema.fields.len());

        for (name, data) in &schema.fields {
            let ty = match &data.ty {
                UnresolvedWpiLibStructType::Primitive(p) => WpiLibStructType::Primitive(*p),
                UnresolvedWpiLibStructType::Custom(name) => {
                    let key = self
                        .canonical(name)
                        .ok_or_else(|| ResolveError::NotFound(name.clone()))?
                        .to_owned();
                    WpiLibStructType::Custom(self.resolve_key(&key, chain)?)
                }
            };

            fields.insert(
                name.clone(),
                WpiLibStructData {
                    count: data.count,
                    value: data.value.clone(),
                    ty,
                },
            );
        }

        Ok(WpiLibStructSchema { fields })
    }
}

#[cfg(test)]
mod test {
    use std::num::NonZeroUsize;

    use crate::values::parse::wpistruct::{
        ResolveError, StructSchemas, UnresolvedWpiLibStructType, WpiLibStructData,
        WpiLibStructPrimitives, WpiLibStructType, WpiLibStructValues,
    };

    use super::WpiLibStructSchema;
    use hashbrown::HashMap;
    use indexmap::IndexMap;

    fn enum_value(values: &[(&str, i64)]) -> WpiLibStructValues {
        WpiLibStructValues::Enum(std::sync::Arc::new(
            values
                .iter()
                .map(|(name, value)| ((*name).to_owned(), *value))
                .collect::<HashMap<_, _>>(),
        ))
    }

    #[test]
    fn basic_struct() {
        let schema = b"  bool  value  ";

        let wpistruct = WpiLibStructSchema::parse(schema).unwrap();

        assert_eq!(
            wpistruct.fields,
            IndexMap::from([(
                "value".to_string(),
                WpiLibStructData {
                    count: None,
                    value: WpiLibStructValues::Value,
                    ty: UnresolvedWpiLibStructType::Primitive(WpiLibStructPrimitives::Bool)
                }
            )])
        );
    }

    #[test]
    fn array_struct() {
        let schema = b"  double  arr  [  4  ]  ";

        let wpistruct = WpiLibStructSchema::parse(schema).unwrap();

        assert_eq!(
            wpistruct.fields,
            IndexMap::from([(
                "arr".to_string(),
                WpiLibStructData {
                    count: NonZeroUsize::new(4),
                    value: WpiLibStructValues::Value,
                    ty: UnresolvedWpiLibStructType::Primitive(WpiLibStructPrimitives::Double)
                }
            )])
        );
    }

    #[test]
    fn nested_struct_without_prefix() {
        // WPILib's Java struct schemas spell nested types without the `struct:` prefix.
        let schema = b"Translation2d translation;Rotation2d rotation";
        let wpistruct = WpiLibStructSchema::parse(schema).unwrap();
        assert_eq!(wpistruct.fields.len(), 2);
        assert_eq!(
            wpistruct.fields["translation"].ty,
            UnresolvedWpiLibStructType::Custom("Translation2d".into())
        );
        assert_eq!(
            wpistruct.fields["rotation"].ty,
            UnresolvedWpiLibStructType::Custom("Rotation2d".into())
        );
    }

    #[test]
    fn basic_enum() {
        let schema = b"  enum  {  }  int8  val";

        let wpistruct = WpiLibStructSchema::parse(schema).unwrap();

        assert_eq!(
            wpistruct.fields,
            IndexMap::from([(
                "val".to_string(),
                WpiLibStructData {
                    count: None,
                    value: enum_value(&[]),
                    ty: UnresolvedWpiLibStructType::Primitive(WpiLibStructPrimitives::Int8)
                }
            )])
        );
    }

    #[test]
    fn multi_struct() {
        let schema = b"  enum  {  a  =  3  ,  }  int64  something  ;  int8  other  ;  enum  {  multi  =  64,  other=24  }  uint16  number_3[3]";

        let wpistruct = WpiLibStructSchema::parse(schema).unwrap();

        assert_eq!(
            wpistruct.fields,
            IndexMap::from([
                (
                    "something".to_string(),
                    WpiLibStructData {
                        count: None,
                        value: enum_value(&[("a", 3)]),
                        ty: UnresolvedWpiLibStructType::Primitive(WpiLibStructPrimitives::Int64,)
                    },
                ),
                (
                    "other".to_string(),
                    WpiLibStructData {
                        count: None,
                        value: WpiLibStructValues::Value,
                        ty: UnresolvedWpiLibStructType::Primitive(WpiLibStructPrimitives::Int8)
                    }
                ),
                (
                    "number_3".to_string(),
                    WpiLibStructData {
                        count: NonZeroUsize::new(3),
                        value: enum_value(&[("multi", 64), ("other", 24)]),
                        ty: UnresolvedWpiLibStructType::Primitive(WpiLibStructPrimitives::Uint16),
                    }
                )
            ])
        );
    }

    #[test]
    fn rejects_incomplete_schemas_instead_of_truncating_them() {
        // The second field cannot be parsed; keeping only `x` would silently change the byte
        // layout of every value of this type.
        assert!(WpiLibStructSchema::parse(b"double x; double[2] arr;").is_err());
        assert!(WpiLibStructSchema::parse(b"double x; double[];").is_err());
        assert!(WpiLibStructSchema::parse(b"double x; double y[0];").is_err());
        assert!(WpiLibStructSchema::parse(b"double x; garbage").is_err());
        // A missing trailing semicolon on the final field is still fine.
        assert!(WpiLibStructSchema::parse(b"double x; double y").is_ok());
    }

    #[test]
    fn rejects_duplicate_fields() {
        assert!(WpiLibStructSchema::parse(b"double x; double x;").is_err());
    }

    #[test]
    fn resolves_memoized_and_shared() {
        let mut schemas = StructSchemas::new();
        schemas.insert(
            "struct:Outer",
            WpiLibStructSchema::parse(b"struct:Translation2d t;").unwrap(),
        );

        let outer = schemas.resolve("struct:Outer").unwrap();
        let again = schemas.resolve("Outer").unwrap();
        assert!(std::sync::Arc::ptr_eq(&outer, &again));

        let WpiLibStructType::Custom(translation) = &outer.fields["t"].ty else {
            panic!("expected a nested struct");
        };
        // Nested schemas are shared with the registry, not copied per resolution.
        let direct = schemas.resolve("Translation2d").unwrap();
        assert!(std::sync::Arc::ptr_eq(translation, &direct));
    }

    #[test]
    fn rejects_recursive_schemas() {
        let mut schemas = StructSchemas::new();
        schemas.insert(
            "struct:Loop",
            WpiLibStructSchema::parse(b"struct:Loop self;").unwrap(),
        );
        assert!(matches!(
            schemas.resolve("struct:Loop"),
            Err(ResolveError::Recursive(_))
        ));

        let mut schemas = StructSchemas::new();
        schemas.insert(
            "struct:A",
            WpiLibStructSchema::parse(b"struct:B b;").unwrap(),
        );
        schemas.insert(
            "struct:B",
            WpiLibStructSchema::parse(b"struct:A a;").unwrap(),
        );
        assert!(matches!(
            schemas.resolve("struct:A"),
            Err(ResolveError::Recursive(_))
        ));
        // A failed resolution must not poison unrelated lookups.
        assert!(schemas.resolve("Translation2d").is_ok());
    }

    #[test]
    fn resolves_fully_qualified_type_names() {
        let mut schemas = StructSchemas::new();
        schemas.insert(
            "struct:Outer",
            WpiLibStructSchema::parse(
                b"edu.wpi.first.math.geometry.Translation2d t; double extra;",
            )
            .unwrap(),
        );
        let outer = schemas.resolve("struct:Outer").unwrap();
        let WpiLibStructType::Custom(translation) = &outer.fields["t"].ty else {
            panic!("expected a nested struct");
        };
        assert_eq!(translation.size(), 16);
    }

    #[test]
    fn reports_missing_types() {
        let mut schemas = StructSchemas::new();
        match schemas.resolve("struct:Nope") {
            Err(ResolveError::NotFound(name)) => assert_eq!(name, "struct:Nope"),
            other => panic!("expected a missing type, got {other:?}"),
        }
    }
}
