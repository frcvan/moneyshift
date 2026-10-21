use std::{
    borrow::Cow,
    num::{NonZero, NonZeroUsize},
};

use anyhow;
use arrow::datatypes::{DataType, Field, SchemaBuilder};
use hashbrown::HashMap;
use indexmap::IndexMap;
use nom::{
    IResult, Parser,
    branch::alt,
    bytes::complete::tag,
    character::complete::{alpha1, alphanumeric1, multispace0, multispace1},
    combinator::recognize,
    error::Error as NomErr,
    multi::many0_count,
    sequence::{delimited, pair},
};

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
        Ok(match value.as_ref() {
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
}

#[derive(Debug, Clone, PartialEq, Eq, PartialOrd, Ord, Hash)]
pub enum UnresolvedWpiLibStructType {
    Primitive(WpiLibStructPrimitives),
    Custom(String),
}

impl WpiLibStructPrimitives {
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

impl<'a> From<Cow<'a, str>> for UnresolvedWpiLibStructType {
    fn from(value: Cow<'a, str>) -> Self {
        WpiLibStructPrimitives::try_from(value.as_ref())
            .map_or_else(|_| Self::Custom(value.into_owned()), Self::Primitive)
    }
}

#[derive(Debug, Clone, PartialEq, Eq)]
pub enum WpiLibStructType {
    Primitive(WpiLibStructPrimitives),
    Custom(WpiLibStructSchema<WpiLibStructType>),
}

impl WpiLibStructType {
    pub fn datatype(&self) -> DataType {
        match self {
            Self::Primitive(p) => p.datatype(),
            Self::Custom(s) => s.datatype(),
        }
    }

    pub fn size(&self) -> usize {
        match self {
            Self::Primitive(p) => p.size(),
            Self::Custom(s) => s.size(),
        }
    }
}

#[derive(Debug, Clone, PartialEq, Eq)]
pub enum WpiLibStructValues {
    Value,
    Enum(HashMap<String, i64>),
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
    pub fn datatype(&self) -> DataType {
        let mut builder = SchemaBuilder::new();

        for (name, data) in &self.fields {
            builder.push(Field::new(name, data.ty.datatype(), true));
        }

        let fields = builder.finish().fields;

        re_log::info!(?fields);

        DataType::Struct(fields)
    }

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
fn type_identifier(input: &[u8]) -> IResult<&[u8], &[u8]> {
    recognize(pair(nom::combinator::opt(tag("struct:")), identifier)).parse(input)
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
    let (data, _) = multispace0::<_, nom::error::Error<_>>(data)?;

    let (data, count) = match delimited(
        tag("["),
        (
            multispace0::<_, nom::error::Error<_>>,
            nom::character::complete::usize,
            multispace0,
        ),
        tag("]"),
    )
    .parse(data)
    {
        Ok((remaining, (_, n, _))) => (remaining, NonZeroUsize::new(n)),
        Err(_) => (data, None),
    };

    let name = String::from_utf8_lossy(identifier_name).into_owned();
    let ty = UnresolvedWpiLibStructType::from(String::from_utf8_lossy(typename));

    let wpistruct = if let Some(wenum) = wpienum {
        WpiLibStructValues::Enum(wenum)
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

    let (data, _) = tag::<_, _, NomErr<_>>("enum")
        .parse(data)
        .unwrap_or((data, &[]));

    let (data, _) = multispace0(data)?;
    let (mut data, _) = tag("{")(data)?;

    loop {
        let (new_data, _) = multispace0::<_, NomErr<_>>(data).unwrap_or((data, &[]));

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

        let (new_data, _) = tag::<_, _, NomErr<_>>(";")(new_data)
            .or_else(|_| tag::<_, _, NomErr<_>>(",")(new_data))
            .unwrap_or((new_data, &[]));
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

    pub fn parse(mut data: &[u8]) -> Result<Self, anyhow::Error> {
        let mut fields = IndexMap::new();

        loop {
            data = match multispace0::<_, nom::error::Error<_>>(data) {
                Ok((remaining, _)) => remaining,
                Err(_) => data,
            };

            let Ok((remaining, (name, inner))) = struct_parser(data) else {
                break;
            };

            fields.insert(name, inner);

            if let Ok((remaining, _)) = tag::<_, _, nom::error::Error<_>>(";")(remaining) {
                data = remaining;
            } else {
                break;
            }
        }

        Ok(Self { fields })
    }

    pub fn resolve(
        &self,
        struct_map: &HashMap<String, Self>,
    ) -> Result<WpiLibStructSchema<WpiLibStructType>, String> {
        Ok(WpiLibStructSchema {
            fields: self
                .fields
                .iter()
                .map(|(name, data)| {
                    Ok::<_, String>((
                        name.clone(),
                        WpiLibStructData {
                            count: data.count.clone(),
                            value: data.value.clone(),
                            ty: match data.ty {
                                UnresolvedWpiLibStructType::Primitive(p) => {
                                    WpiLibStructType::Primitive(p)
                                }
                                UnresolvedWpiLibStructType::Custom(ref s) => {
                                    WpiLibStructType::Custom(
                                        struct_map
                                            .get(s)
                                            .or_else(|| {
                                                let prefixed = format!("struct:{s}");
                                                struct_map.get(&prefixed)
                                            })
                                            .or_else(|| {
                                                let bare = s.strip_prefix("struct:").unwrap_or(s);
                                                struct_map.get(bare)
                                            })
                                            .ok_or_else(|| s.clone())?
                                            .resolve(struct_map)?,
                                    )
                                }
                            },
                        },
                    ))
                })
                .collect::<Result<IndexMap<_, _>, _>>()?,
        })
    }
}

#[cfg(test)]
mod test {
    use std::num::NonZeroUsize;

    use crate::values::parse::wpistruct::{
        UnresolvedWpiLibStructType, WpiLibStructData, WpiLibStructPrimitives, WpiLibStructValues,
    };

    use super::WpiLibStructSchema;
    use hashbrown::HashMap;
    use indexmap::IndexMap;

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
                    value: WpiLibStructValues::Enum(HashMap::new()),
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
                        value: WpiLibStructValues::Enum(HashMap::from([("a".to_string(), 3)]),),
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
                        value: WpiLibStructValues::Enum(HashMap::from([
                            ("multi".to_string(), 64),
                            ("other".to_string(), 24)
                        ]),),
                        ty: UnresolvedWpiLibStructType::Primitive(WpiLibStructPrimitives::Uint16),
                    }
                )
            ])
        );
    }
}
