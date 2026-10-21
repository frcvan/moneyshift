use std::fmt::Write as _;
use std::fs;

use anyhow::{Context as _, Result};
use camino::{Utf8Path, Utf8PathBuf};
use re_types_builder::data_type::{DataType, UnionMode};
use re_types_builder::{
    AtomicDataType, Docs, EnumIntegerType, Object, ObjectClass, Objects, RerunAttr, Type,
    TypeRegistry,
};

const JAVA_PACKAGE_PREFIX: &str = "org.teamvan.moneyshift";
const CORE_PACKAGE: &str = "org.teamvan.moneyshift";

pub fn generate(
    output_dir: &Utf8Path,
    objects: &Objects,
    type_registry: &TypeRegistry,
) -> Result<()> {
    let generator = JavaGenerator {
        objects,
        registry: type_registry,
    };

    // Remove previous generated trees so that a renamed or deleted definition cannot leave a
    // stale Java class behind. Only the top-level packages created below are touched; the
    // hand-written core and the protobuf bindings live elsewhere.
    let mut generated_roots = std::collections::BTreeSet::new();
    for object in objects.values() {
        let package = java_package(&object.fqname);
        let relative = package
            .strip_prefix(JAVA_PACKAGE_PREFIX)
            .unwrap_or(&package)
            .trim_start_matches('.');
        if let Some(first) = relative.split('.').next() {
            generated_roots.insert(first.to_owned());
        }
    }
    for root in generated_roots {
        let path = output_dir.join(&root);
        if path.exists() {
            fs::remove_dir_all(&path).with_context(|| format!("cleaning {path}"))?;
        }
    }

    for object in objects.values() {
        let path = output_path(output_dir, object);
        println!(
            "[+] Generating Java code for {} to {}",
            &object.fqname, &path
        );
        if let Some(parent) = path.parent() {
            fs::create_dir_all(parent).with_context(|| format!("creating {parent}"))?;
        }
        fs::write(&path, generator.render(object)?)
            .with_context(|| format!("writing {path}"))?;
    }
    Ok(())
}

fn output_path(output_dir: &Utf8Path, object: &Object) -> Utf8PathBuf {
    let package = java_package(&object.fqname);
    let relative_package = package.replace('.', "/");
    let relative_package = relative_package
        .strip_prefix('/')
        .unwrap_or(&relative_package);
    output_dir
        .join(relative_package)
        .join(format!("{}.java", java_class_name(&object.name)))
}

/// Maps `rerun.blueprint.archetypes.X` to `org.teamvan.moneyshift.blueprint.archetypes`.
fn java_package(fqname: &str) -> String {
    let parent = fqname
        .rsplit_once('.')
        .map_or(fqname, |(parent, _)| parent);
    format!(
        "{JAVA_PACKAGE_PREFIX}.{}",
        parent.trim_start_matches("rerun.")
    )
}

fn java_class(fqname: &str) -> String {
    let name = fqname.rsplit('.').next().unwrap_or(fqname);
    format!("{}.{}", java_package(fqname), java_class_name(name))
}

struct JavaGenerator<'a> {
    objects: &'a Objects,
    registry: &'a TypeRegistry,
}

impl<'a> JavaGenerator<'a> {
    fn render(&self, object: &'a Object) -> Result<String> {
        match object.kind {
            re_types_builder::ObjectKind::Encoding => self.render_encoding(object),
            re_types_builder::ObjectKind::Component => self.render_component(object),
            re_types_builder::ObjectKind::Archetype => self.render_archetype(object),
            re_types_builder::ObjectKind::View => self.render_view(object),
        }
    }

    // ---------------------------------------------------------------- types

    fn data_type_for_object(&self, object: &Object) -> DataType {
        self.registry.get(&object.fqname)
    }

    fn data_type_for_field(&self, field: &re_types_builder::ObjectField) -> DataType {
        self.registry.get(&field.fqname)
    }

    fn java_type(&self, typ: &Type, nullable: bool) -> String {
        match typ {
            Type::Atomic(atomic) => {
                let primitive = match atomic {
                    AtomicDataType::Null => return "java.lang.Void".to_owned(),
                    AtomicDataType::Boolean => "boolean",
                    AtomicDataType::Int8 => "byte",
                    AtomicDataType::Int16 => "short",
                    AtomicDataType::Int32 => "int",
                    AtomicDataType::Int64 => "long",
                    AtomicDataType::UInt8 => "short",
                    AtomicDataType::UInt16 => "int",
                    AtomicDataType::UInt32 => "long",
                    AtomicDataType::UInt64 => "long",
                    AtomicDataType::Float16 | AtomicDataType::Float32 => "float",
                    AtomicDataType::Float64 => "double",
                };
                if nullable {
                    boxed_primitive(primitive).to_owned()
                } else {
                    primitive.to_owned()
                }
            }
            Type::Binary => "byte[]".to_owned(),
            Type::Utf8 => "java.lang.String".to_owned(),
            Type::FixedSizeList { elem_type, .. } => {
                format!("{}[]", self.java_type(elem_type, false))
            }
            Type::List { elem_type } => {
                format!("java.util.List<{}>", self.java_type_boxed(elem_type))
            }
            Type::Object { fqname } => java_class(fqname),
        }
    }

    fn java_type_boxed(&self, typ: &Type) -> String {
        match typ {
            Type::Atomic(atomic) => {
                let primitive = match atomic {
                    AtomicDataType::Null => "java.lang.Void",
                    AtomicDataType::Boolean => "boolean",
                    AtomicDataType::Int8 => "byte",
                    AtomicDataType::Int16 => "short",
                    AtomicDataType::Int32 => "int",
                    AtomicDataType::Int64 => "long",
                    AtomicDataType::UInt8 => "short",
                    AtomicDataType::UInt16 => "int",
                    AtomicDataType::UInt32 => "long",
                    AtomicDataType::UInt64 => "long",
                    AtomicDataType::Float16 | AtomicDataType::Float32 => "float",
                    AtomicDataType::Float64 => "double",
                };
                boxed_primitive(primitive).to_owned()
            }
            _ => self.java_type(typ, false),
        }
    }

    fn java_field_name(field: &re_types_builder::ObjectField) -> String {
        java_identifier(&snake_to_camel(&field.name))
    }

    fn vector_class(&self, typ: &Type) -> String {
        match typ {
            Type::Atomic(atomic) => atomic_vector(atomic).to_owned(),
            Type::Utf8 => "org.apache.arrow.vector.VarCharVector".to_owned(),
            Type::Binary => "org.apache.arrow.vector.LargeVarBinaryVector".to_owned(),
            Type::FixedSizeList { .. } => {
                "org.apache.arrow.vector.complex.FixedSizeListVector".to_owned()
            }
            Type::List { .. } => "org.apache.arrow.vector.complex.ListVector".to_owned(),
            Type::Object { fqname } => {
                let object = &self.objects[fqname.as_str()];
                match object.class {
                    ObjectClass::Enum(repr) => atomic_vector(&repr.to_atomic()).to_owned(),
                    ObjectClass::Union => {
                        "org.apache.arrow.vector.complex.DenseUnionVector".to_owned()
                    }
                    ObjectClass::Struct => {
                        if object.is_arrow_transparent() {
                            self.vector_class(&object.fields[0].typ)
                        } else {
                            "org.apache.arrow.vector.complex.StructVector".to_owned()
                        }
                    }
                }
            }
        }
    }

    // ------------------------------------------------------------- arrow fields

    fn arrow_field_expr(&self, data_type: &DataType, name: &str, nullable: bool) -> String {
        self.arrow_field_expr_raw(
            data_type,
            &format!("\"{}\"", escape_java_string(name)),
            &nullable.to_string(),
        )
    }

    /// `name_expr` and `nullable_expr` are Java expressions (a string literal or a parameter).
    fn arrow_field_expr_raw(
        &self,
        data_type: &DataType,
        name_expr: &str,
        nullable_expr: &str,
    ) -> String {
        match data_type {
            DataType::Atomic(atomic) => format!(
                "{CORE_PACKAGE}.ArrowFields.field({name_expr}, {nullable_expr}, {}, java.util.List.of())",
                atomic_arrow_const(atomic)
            ),
            DataType::Binary | DataType::Utf8 => format!(
                "{CORE_PACKAGE}.ArrowFields.field({name_expr}, {nullable_expr}, {}, java.util.List.of())",
                arrow_type_expr(data_type)
            ),
            DataType::Object { fqname, .. } => {
                format!("{}.arrowField({name_expr}, {nullable_expr})", java_class(fqname))
            }
            DataType::FixedSizeList(field, length) => {
                let child = self.arrow_field_expr_raw(
                    &field.data_type,
                    &format!("\"{}\"", escape_java_string(&field.name)),
                    &field.is_nullable.to_string(),
                );
                format!(
                    "{CORE_PACKAGE}.ArrowFields.field({name_expr}, {nullable_expr}, \
                     new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList({length}), \
                     java.util.List.of({child}))"
                )
            }
            DataType::List(field) => {
                let child = self.arrow_field_expr_raw(
                    &field.data_type,
                    &format!("\"{}\"", escape_java_string(&field.name)),
                    &field.is_nullable.to_string(),
                );
                format!(
                    "{CORE_PACKAGE}.ArrowFields.field({name_expr}, {nullable_expr}, \
                     new org.apache.arrow.vector.types.pojo.ArrowType.List(), \
                     java.util.List.of({child}))"
                )
            }
            DataType::Struct(fields) => {
                let children = fields
                    .iter()
                    .map(|field| {
                        self.arrow_field_expr_raw(
                            &field.data_type,
                            &format!("\"{}\"", escape_java_string(&field.name)),
                            &field.is_nullable.to_string(),
                        )
                    })
                    .collect::<Vec<_>>()
                    .join(", ");
                format!(
                    "{CORE_PACKAGE}.ArrowFields.field({name_expr}, {nullable_expr}, \
                     new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), \
                     java.util.List.of({children}))"
                )
            }
            DataType::Union(fields, mode) => {
                let children = fields
                    .iter()
                    .map(|field| {
                        self.arrow_field_expr_raw(
                            &field.data_type,
                            &format!("\"{}\"", escape_java_string(&field.name)),
                            &field.is_nullable.to_string(),
                        )
                    })
                    .collect::<Vec<_>>()
                    .join(", ");
                let arrow_mode = match mode {
                    UnionMode::Dense => "Dense",
                    UnionMode::Sparse => "Sparse",
                };
                let type_ids = (0..fields.len())
                    .map(|index| index.to_string())
                    .collect::<Vec<_>>()
                    .join(", ");
                format!(
                    "{CORE_PACKAGE}.ArrowFields.field({name_expr}, {nullable_expr}, \
                     new org.apache.arrow.vector.types.pojo.ArrowType.Union(\
                     org.apache.arrow.vector.types.UnionMode.{arrow_mode}, new int[]{{{type_ids}}}), \
                     java.util.List.of({children}))"
                )
            }
        }
    }

    fn arrow_type_expr(&self, data_type: &DataType) -> String {
        match data_type {
            DataType::Atomic(atomic) => atomic_arrow_const(atomic).to_owned(),
            DataType::Binary => format!("{CORE_PACKAGE}.ArrowFields.BINARY"),
            DataType::Utf8 => format!("{CORE_PACKAGE}.ArrowFields.UTF8"),
            DataType::Object { fqname, .. } => format!("{}.arrowType()", java_class(fqname)),
            DataType::FixedSizeList(_, length) => format!(
                "new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList({length})"
            ),
            DataType::List(_) => {
                "new org.apache.arrow.vector.types.pojo.ArrowType.List()".to_owned()
            }
            DataType::Struct(_) => {
                "new org.apache.arrow.vector.types.pojo.ArrowType.Struct()".to_owned()
            }
            DataType::Union(fields, mode) => {
                let arrow_mode = match mode {
                    UnionMode::Dense => "Dense",
                    UnionMode::Sparse => "Sparse",
                };
                let type_ids = (0..fields.len())
                    .map(|index| index.to_string())
                    .collect::<Vec<_>>()
                    .join(", ");
                format!(
                    "new org.apache.arrow.vector.types.pojo.ArrowType.Union(\
                     org.apache.arrow.vector.types.UnionMode.{arrow_mode}, new int[]{{{type_ids}}})"
                )
            }
        }
    }

    fn arrow_field_body(&self, data_type: &DataType) -> (String, String) {
        (
            self.arrow_type_expr(data_type),
            self.arrow_field_expr(data_type, "name", true),
        )
    }

    // ------------------------------------------------------------- encodings

    fn render_encoding(&self, object: &'a Object) -> Result<String> {
        let package = java_package(&object.fqname);
        let mut code = String::new();
        write_header(&mut code, &package);
        write_docs(&mut code, &object.docs, "");
        match object.class {
            ObjectClass::Struct => self.render_encoding_struct(&mut code, object)?,
            ObjectClass::Enum(repr) => self.render_enum(&mut code, object, repr, false)?,
            ObjectClass::Union => self.render_union(&mut code, object)?,
        }
        Ok(code)
    }

    fn render_encoding_struct(&self, code: &mut String, object: &'a Object) -> Result<()> {
        let name = java_class_name(&object.name);
        let data_type = self.data_type_for_object(object);

        self.render_record_declaration(code, object, &name)?;
        writeln!(
            code,
            "    public static final java.lang.String NAME = \"{}\";",
            object.fqname
        )
        .unwrap();
        writeln!(code).unwrap();

        // Arrow type accessors.
        let logical = data_type.to_logical_type();
        writeln!(
            code,
            "    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {{"
        )
        .unwrap();
        writeln!(code, "        return {};", self.arrow_type_expr(logical)).unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static org.apache.arrow.vector.types.pojo.Field arrowField(\
             java.lang.String name, boolean nullable) {{"
        )
        .unwrap();
        writeln!(
            code,
            "        return {};",
            self.arrow_field_expr_raw(logical, "name", "nullable")
        )
        .unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();

        self.render_struct_write_methods(code, object, &name)?;
        self.render_array_factory(code, object, &name)?;
        writeln!(code, "}}").unwrap();
        Ok(())
    }

    fn render_record_declaration(
        &self,
        code: &mut String,
        object: &'a Object,
        name: &str,
    ) -> Result<()> {
        writeln!(code, "public record {name}(").unwrap();
        for (index, field) in object.fields.iter().enumerate() {
            let comma = if index + 1 == object.fields.len() {
                ""
            } else {
                ","
            };
            write_docs(code, &field.docs, "    ");
            writeln!(
                code,
                "    {} {}{}",
                self.java_type(&field.typ, field.is_nullable),
                Self::java_field_name(field),
                comma
            )
            .unwrap();
        }
        writeln!(code, ") {{").unwrap();
        // Compact constructor: normalize lists and enforce required references.
        if !object.fields.is_empty() {
            writeln!(code, "    public {name} {{").unwrap();
            for field in &object.fields {
                let field_name = Self::java_field_name(field);
                let reference_type = matches!(
                    field.typ,
                    Type::Object { .. } | Type::Utf8 | Type::Binary
                );
                if reference_type && !field.is_nullable {
                    writeln!(
                        code,
                        "        java.util.Objects.requireNonNull({field_name}, \"{field_name}\");"
                    )
                    .unwrap();
                }
                if matches!(field.typ, Type::List { .. }) {
                    if field.is_nullable {
                        writeln!(
                            code,
                            "        if ({field_name} != null) {{ {field_name} = java.util.List.copyOf({field_name}); }}"
                        )
                        .unwrap();
                    } else {
                        writeln!(
                            code,
                            "        {field_name} = java.util.List.copyOf({field_name});"
                        )
                        .unwrap();
                    }
                }
            }
            writeln!(code, "    }}").unwrap();
        }

        // Arrays destroy the default record equality, so provide deep versions.
        if object
            .fields
            .iter()
            .any(|field| matches!(field.typ, Type::FixedSizeList { .. }))
        {
            let comparisons = object
                .fields
                .iter()
                .filter_map(|field| {
                    let field_name = Self::java_field_name(field);
                    match field.typ {
                        Type::FixedSizeList { .. } => {
                            Some(format!("java.util.Arrays.equals({field_name}, other.{field_name})"))
                        }
                        _ => Some(format!("java.util.Objects.equals({field_name}, other.{field_name})")),
                    }
                })
                .collect::<Vec<_>>()
                .join("\n                && ");
            let hashes = object
                .fields
                .iter()
                .map(|field| {
                    let field_name = Self::java_field_name(field);
                    if matches!(field.typ, Type::FixedSizeList { .. }) {
                        format!("java.util.Arrays.hashCode({field_name})")
                    } else {
                        format!("java.util.Objects.hashCode({field_name})")
                    }
                })
                .collect::<Vec<_>>()
                .join(", ");
            writeln!(code).unwrap();
            writeln!(code, "    @Override").unwrap();
            writeln!(code, "    public boolean equals(java.lang.Object other) {{").unwrap();
            writeln!(code, "        if (this == other) return true;").unwrap();
            writeln!(
                code,
                "        if (!(other instanceof {name} that)) return false;"
            )
            .unwrap();
            let comparisons = comparisons.replace("other.", "that.");
            writeln!(code, "        return {comparisons};").unwrap();
            writeln!(code, "    }}").unwrap();
            writeln!(code).unwrap();
            writeln!(code, "    @Override").unwrap();
            writeln!(code, "    public int hashCode() {{").unwrap();
            writeln!(code, "        return java.util.Objects.hash({hashes});").unwrap();
            writeln!(code, "    }}").unwrap();
        }
        Ok(())
    }

    fn render_struct_write_methods(
        &self,
        code: &mut String,
        object: &'a Object,
        name: &str,
    ) -> Result<()> {
        let vector_class = self.vector_class(&Type::Object {
            fqname: object.fqname.clone(),
        });
        let is_union = object.is_union();

        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static void write(org.apache.arrow.vector.FieldVector vector, \
             java.util.List<{name}> values) {{"
        )
        .unwrap();
        writeln!(code, "        int count = values.size();").unwrap();
        writeln!(code, "        for (int i = 0; i < count; i++) {{").unwrap();
        writeln!(code, "            {name} value = values.get(i);").unwrap();
        if is_union {
            writeln!(code, "            writeOne(vector, i, value);").unwrap();
        } else {
            writeln!(code, "            if (value == null) {{").unwrap();
            writeln!(code, "                (({vector_class}) vector).setNull(i);").unwrap();
            writeln!(code, "            }} else {{").unwrap();
            writeln!(code, "                writeOne(vector, i, value);").unwrap();
            writeln!(code, "            }}").unwrap();
        }
        writeln!(code, "        }}").unwrap();
        writeln!(code, "        (({vector_class}) vector).setValueCount(count);").unwrap();
        writeln!(code, "    }}").unwrap();

        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static void writeOne(org.apache.arrow.vector.FieldVector vector, \
             int index, {name} value) {{"
        )
        .unwrap();
        let mut counter = 0usize;
        if object.is_arrow_transparent() {
            let inner = &object.fields[0];
            let inner_value = format!("value.{}()", Self::java_field_name(inner));
            self.emit_write_one(
                code,
                "        ",
                &inner.typ,
                "vector",
                "index",
                &inner_value,
                &mut counter,
            )?;
        } else if object.is_union() {
            self.emit_union_write_one(code, "        ", object, "vector", "index", name)?;
        } else {
            // Plain struct.
            let struct_var = "struct";
            writeln!(
                code,
                "        org.apache.arrow.vector.complex.StructVector {struct_var} = \
                 (org.apache.arrow.vector.complex.StructVector) vector;"
            )
            .unwrap();
            writeln!(code, "        {struct_var}.setIndexDefined(index);").unwrap();
            for (ordinal, field) in object.fields.iter().enumerate() {
                let field_name = Self::java_field_name(field);
                let java_type = self.java_type(&field.typ, field.is_nullable);
                let local = format!("f{counter}");
                counter += 1;
                writeln!(
                    code,
                    "        {java_type} {local} = value.{field_name}();"
                )
                .unwrap();
                let vector_expr = format!("(org.apache.arrow.vector.FieldVector) {struct_var}.getChildByOrdinal({ordinal})");
                if field.is_nullable && !field.typ.is_union(self.objects) {
                    let concrete = self.vector_class(&field.typ);
                    writeln!(code, "        if ({local} == null) {{").unwrap();
                    writeln!(code, "            (({concrete}) {vector_expr}).setNull(index);").unwrap();
                    writeln!(code, "        }} else {{").unwrap();
                    self.emit_write_one(
                        code,
                        "            ",
                        &field.typ,
                        &vector_expr,
                        "index",
                        &local,
                        &mut counter,
                    )?;
                    writeln!(code, "        }}").unwrap();
                } else {
                    self.emit_write_one(
                        code,
                        "        ",
                        &field.typ,
                        &vector_expr,
                        "index",
                        &local,
                        &mut counter,
                    )?;
                }
            }
        }
        writeln!(code, "    }}").unwrap();

        // A convenience constructor for types that are just a fixed-size scalar array.
        if let Some((elem_type, length)) = fixed_size_scalar_field(object) {
            let elem = self.java_type(&elem_type, false);
            writeln!(code).unwrap();
            writeln!(
                code,
                "    public static {name} of({elem}... values) {{"
            )
            .unwrap();
            writeln!(
                code,
                "        if (values.length != {length}) throw new java.lang.IllegalArgumentException(\
                 \"{name} needs exactly {length} values, got \" + values.length);"
            )
            .unwrap();
            let field_name = Self::java_field_name(&object.fields[0]);
            writeln!(code, "        return new {name}(values);").unwrap();
            let _ = field_name;
            writeln!(code, "    }}").unwrap();
        }
        Ok(())
    }

    fn render_array_factory(
        &self,
        code: &mut String,
        object: &'a Object,
        name: &str,
    ) -> Result<()> {
        let _ = (code, object, name);
        Ok(())
    }

    fn render_enum(
        &self,
        code: &mut String,
        object: &'a Object,
        repr: EnumIntegerType,
        component: bool,
    ) -> Result<()> {
        let name = java_class_name(&object.name);
        let suffix = if component {
            " implements org.teamvan.moneyshift.Component"
        } else {
            ""
        };
        writeln!(code, "public enum {name}{suffix} {{").unwrap();
        for (index, field) in object.fields.iter().enumerate() {
            let comma = if index + 1 == object.fields.len() {
                ";"
            } else {
                ","
            };
            write_docs(code, &field.docs, "    ");
            let value = field.enum_or_union_variant_value.unwrap_or(0);
            writeln!(
                code,
                "    {}({}L){}",
                java_enum_constant(&field.name),
                value,
                comma
            )
            .unwrap();
        }
        writeln!(code).unwrap();
        writeln!(code, "    private final long value;").unwrap();
        writeln!(code).unwrap();
        writeln!(code, "    {name}(long value) {{").unwrap();
        writeln!(code, "        this.value = value;").unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        writeln!(code, "    public long value() {{").unwrap();
        writeln!(code, "        return value;").unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        writeln!(code, "    public static {name} fromValue(long value) {{").unwrap();
        writeln!(code, "        for ({name} candidate : values()) {{").unwrap();
        writeln!(code, "            if (candidate.value == value) return candidate;").unwrap();
        writeln!(code, "        }}").unwrap();
        writeln!(
            code,
            "        throw new java.lang.IllegalArgumentException(\"unknown {name} value: \" + value);"
        )
        .unwrap();
        writeln!(code, "    }}").unwrap();

        writeln!(code).unwrap();
        writeln!(code, "    public static final java.lang.String NAME = \"{}\";", object.fqname)
            .unwrap();
        writeln!(
            code,
            "    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {{"
        )
        .unwrap();
        writeln!(code, "        return {};", atomic_arrow_const(&repr.to_atomic())).unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static org.apache.arrow.vector.types.pojo.Field arrowField(\
             java.lang.String name, boolean nullable) {{"
        )
        .unwrap();
        writeln!(
            code,
            "        return {CORE_PACKAGE}.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());"
        )
        .unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();

        if component {
            writeln!(
                code,
                "    public static final java.lang.String COMPONENT_TYPE = \"{}\";",
                object.fqname
            )
            .unwrap();
            writeln!(
                code,
                "    public static final {CORE_PACKAGE}.ComponentDescriptor DESCRIPTOR = \
                 {CORE_PACKAGE}.ComponentDescriptor.component(COMPONENT_TYPE);"
            )
            .unwrap();
            writeln!(code).unwrap();
            writeln!(
                code,
                "    public static {CORE_PACKAGE}.ComponentBatch batch(\
                 {CORE_PACKAGE}.ComponentDescriptor descriptor, \
                 org.apache.arrow.memory.BufferAllocator allocator, \
                 java.util.List<{name}> values) {{"
            )
            .unwrap();
            writeln!(
                code,
                "        return {CORE_PACKAGE}.ComponentBatch.of(descriptor, arrowField(\"item\", true), \
                 allocator, values, {name}::write);"
            )
            .unwrap();
            writeln!(code, "    }}").unwrap();
            writeln!(code).unwrap();
            writeln!(
                code,
                "    public static {CORE_PACKAGE}.ComponentBatch batch(\
                 {CORE_PACKAGE}.ComponentDescriptor descriptor, \
                 org.apache.arrow.memory.BufferAllocator allocator, {name} value) {{"
            )
            .unwrap();
            writeln!(
                code,
                "        return batch(descriptor, allocator, java.util.List.of(value));"
            )
            .unwrap();
            writeln!(code, "    }}").unwrap();
            writeln!(code).unwrap();
            writeln!(
                code,
                "    public static {CORE_PACKAGE}.ComponentBatch batch(\
                 org.apache.arrow.memory.BufferAllocator allocator, {name} value) {{"
            )
            .unwrap();
            writeln!(
                code,
                "        return batch(DESCRIPTOR, allocator, java.util.List.of(value));"
            )
            .unwrap();
            writeln!(code, "    }}").unwrap();
        }

        self.render_enum_write_methods(code, object, &name, repr)?;
        writeln!(code, "}}").unwrap();
        Ok(())
    }

    fn render_enum_write_methods(
        &self,
        code: &mut String,
        object: &'a Object,
        name: &str,
        repr: EnumIntegerType,
    ) -> Result<()> {
        let vector = atomic_vector(&repr.to_atomic());
        let value_expr = match repr {
            EnumIntegerType::U8 | EnumIntegerType::U16 => "(int) value.value()",
            EnumIntegerType::U32 => "(int) value.value()",
            EnumIntegerType::U64 => "value.value()",
        };
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static void write(org.apache.arrow.vector.FieldVector vector, \
             java.util.List<{name}> values) {{"
        )
        .unwrap();
        writeln!(code, "        int count = values.size();").unwrap();
        writeln!(code, "        for (int i = 0; i < count; i++) {{").unwrap();
        writeln!(code, "            {name} value = values.get(i);").unwrap();
        writeln!(code, "            if (value == null) {{").unwrap();
        writeln!(code, "                (({vector}) vector).setNull(i);").unwrap();
        writeln!(code, "            }} else {{").unwrap();
        writeln!(code, "                (({vector}) vector).setSafe(i, {value_expr});").unwrap();
        writeln!(code, "            }}").unwrap();
        writeln!(code, "        }}").unwrap();
        writeln!(code, "        (({vector}) vector).setValueCount(count);").unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static void writeOne(org.apache.arrow.vector.FieldVector vector, \
             int index, {name} value) {{"
        )
        .unwrap();
        writeln!(code, "        (({vector}) vector).setSafe(index, {value_expr});").unwrap();
        writeln!(code, "    }}").unwrap();
        let _ = object;
        Ok(())
    }

    fn render_union(&self, code: &mut String, object: &'a Object) -> Result<()> {
        let name = java_class_name(&object.name);
        writeln!(code, "public sealed interface {name} permits").unwrap();
        for (index, field) in object.fields.iter().enumerate() {
            let separator = if index + 1 == object.fields.len() {
                " {"
            } else {
                ","
            };
            writeln!(
                code,
                "    {name}.{}{}",
                union_variant_name(field),
                separator
            )
            .unwrap();
        }
        for field in &object.fields {
            let variant = union_variant_name(field);
            write_docs(code, &field.docs, "    ");
            if field.typ.is_unit() {
                writeln!(
                    code,
                    "    record {variant}() implements {name} {{}}"
                )
                .unwrap();
            } else {
                writeln!(
                    code,
                    "    record {variant}({} value) implements {name} {{",
                    self.java_type(&field.typ, false)
                )
                .unwrap();
                writeln!(
                    code,
                    "        public {variant} {{ java.util.Objects.requireNonNull(value, \"value\"); }}"
                )
                .unwrap();
                writeln!(code, "    }}").unwrap();
            }
        }

        writeln!(code).unwrap();
        writeln!(code, "    java.lang.String NAME = \"{}\";", object.fqname).unwrap();

        let data_type = self.data_type_for_object(object);
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {{"
        )
        .unwrap();
        writeln!(
            code,
            "        return {};",
            self.arrow_type_expr(data_type.to_logical_type())
        )
        .unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static org.apache.arrow.vector.types.pojo.Field arrowField(\
             java.lang.String name, boolean nullable) {{"
        )
        .unwrap();
        writeln!(
            code,
            "        return {};",
            self.arrow_field_expr_raw(data_type.to_logical_type(), "name", "nullable")
        )
        .unwrap();
        writeln!(code, "    }}").unwrap();

        writeln!(code).unwrap();
        writeln!(code, "    public static int typeId({name} value) {{").unwrap();
        writeln!(code, "        if (value == null) return 0;").unwrap();
        writeln!(code, "        return switch (value) {{").unwrap();
        for (index, field) in object.fields.iter().enumerate() {
            let type_id = index + 1;
            writeln!(
                code,
                "            case {name}.{} ignored -> {type_id};",
                union_variant_name(field)
            )
            .unwrap();
        }
        writeln!(code, "        }};").unwrap();
        writeln!(code, "    }}").unwrap();

        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static void write(org.apache.arrow.vector.FieldVector vector, \
             java.util.List<{name}> values) {{"
        )
        .unwrap();
        writeln!(code, "        int count = values.size();").unwrap();
        writeln!(code, "        for (int i = 0; i < count; i++) {{").unwrap();
        writeln!(code, "            writeOne(vector, i, values.get(i));").unwrap();
        writeln!(code, "        }}").unwrap();
        writeln!(
            code,
            "        ((org.apache.arrow.vector.complex.DenseUnionVector) vector).setValueCount(count);"
        )
        .unwrap();
        writeln!(code, "    }}").unwrap();

        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static void writeOne(org.apache.arrow.vector.FieldVector vector, \
             int index, {name} value) {{"
        )
        .unwrap();
        self.emit_union_write_one(code, "        ", object, "vector", "index", &name)?;
        writeln!(code, "    }}").unwrap();

        writeln!(code, "}}").unwrap();
        Ok(())
    }

    fn emit_union_write_one(
        &self,
        code: &mut String,
        indent: &str,
        object: &'a Object,
        vector: &str,
        index: &str,
        _name: &str,
    ) -> Result<()> {
        writeln!(
            code,
            "{indent}org.apache.arrow.vector.complex.DenseUnionVector union = \
             (org.apache.arrow.vector.complex.DenseUnionVector) {vector};"
        )
        .unwrap();
        writeln!(code, "{indent}if (value == null) {{").unwrap();
        self.emit_union_null(code, &format!("{indent}    "), "union", index)?;
        writeln!(code, "{indent}    return;").unwrap();
        writeln!(code, "{indent}}}").unwrap();
        writeln!(code, "{indent}switch (value) {{").unwrap();
        for (position, field) in object.fields.iter().enumerate() {
            let type_id = position + 1;
            let variant = union_variant_name(field);
            if field.typ.is_unit() {
                writeln!(
                    code,
                    "{indent}    case {}.{} ignored -> {{",
                    java_class_name(&object.name),
                    variant
                )
                .unwrap();
                writeln!(
                    code,
                    "{indent}        org.apache.arrow.vector.FieldVector child = \
                     (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) {type_id});"
                )
                .unwrap();
                writeln!(code, "{indent}        int offset = child.getValueCount();").unwrap();
                writeln!(code, "{indent}        union.setTypeId(index, (byte) {type_id});").unwrap();
                writeln!(code, "{indent}        union.setOffset(index, offset);").unwrap();
                writeln!(code, "{indent}        child.setValueCount(offset + 1);").unwrap();
                writeln!(code, "{indent}    }}").unwrap();
            } else {
                writeln!(
                    code,
                    "{indent}    case {}.{} variant -> {{",
                    java_class_name(&object.name),
                    variant
                )
                .unwrap();
                writeln!(
                    code,
                    "{indent}        org.apache.arrow.vector.FieldVector child = \
                     (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) {type_id});"
                )
                .unwrap();
                writeln!(code, "{indent}        int offset = child.getValueCount();").unwrap();
                writeln!(code, "{indent}        union.setTypeId(index, (byte) {type_id});").unwrap();
                writeln!(code, "{indent}        union.setOffset(index, offset);").unwrap();
                writeln!(code, "{indent}        child.setValueCount(offset + 1);").unwrap();
                let mut counter = 0usize;
                self.emit_write_one(
                    code,
                    &format!("{indent}        "),
                    &field.typ,
                    "child",
                    "offset",
                    "variant.value()",
                    &mut counter,
                )?;
                writeln!(code, "{indent}    }}").unwrap();
            }
        }
        writeln!(code, "{indent}}}").unwrap();
        Ok(())
    }

    fn emit_union_null(
        &self,
        code: &mut String,
        indent: &str,
        union: &str,
        index: &str,
    ) -> Result<()> {
        writeln!(
            code,
            "{indent}org.apache.arrow.vector.FieldVector nullMarkers = \
             (org.apache.arrow.vector.FieldVector) {union}.getVectorByType((byte) 0);"
        )
        .unwrap();
        writeln!(
            code,
            "{indent}int nullOffset = nullMarkers.getValueCount();"
        )
        .unwrap();
        writeln!(code, "{indent}{union}.setTypeId({index}, (byte) 0);").unwrap();
        writeln!(code, "{indent}{union}.setOffset({index}, nullOffset);").unwrap();
        writeln!(
            code,
            "{indent}nullMarkers.setValueCount(nullOffset + 1);"
        )
        .unwrap();
        Ok(())
    }

    // -------------------------------------------------------------- writers

    fn emit_write_one(
        &self,
        code: &mut String,
        indent: &str,
        typ: &Type,
        vector: &str,
        index: &str,
        value: &str,
        counter: &mut usize,
    ) -> Result<()> {
        match typ {
            Type::Atomic(atomic) => {
                let class = atomic_vector(atomic);
                let value = match atomic {
                    AtomicDataType::Null => return Ok(()),
                    AtomicDataType::Boolean => format!("(({value}) ? 1 : 0)"),
                    AtomicDataType::UInt8 | AtomicDataType::UInt16 => {
                        format!("(int) {value}")
                    }
                    AtomicDataType::UInt32 => format!("(int) (long) {value}"),
                    AtomicDataType::Float16 => {
                        format!("java.lang.Float.floatToFloat16({value})")
                    }
                    _ => value.to_owned(),
                };
                writeln!(
                    code,
                    "{indent}(({class}) {vector}).setSafe({index}, {value});"
                )
                .unwrap();
            }
            Type::Utf8 => {
                writeln!(
                    code,
                    "{indent}((org.apache.arrow.vector.VarCharVector) {vector}).setSafe(\
                     {index}, {value}.getBytes(java.nio.charset.StandardCharsets.UTF_8));"
                )
                .unwrap();
            }
            Type::Binary => {
                writeln!(
                    code,
                    "{indent}((org.apache.arrow.vector.LargeVarBinaryVector) {vector}).setSafe(\
                     {index}, {value});"
                )
                .unwrap();
            }
            Type::FixedSizeList { elem_type, length } => {
                let id = *counter;
                *counter += 1;
                writeln!(
                    code,
                    "{indent}{{"
                )
                .unwrap();
                writeln!(
                    code,
                    "{indent}    org.apache.arrow.vector.complex.FixedSizeListVector list{id} = \
                     (org.apache.arrow.vector.complex.FixedSizeListVector) {vector};"
                )
                .unwrap();
                writeln!(
                    code,
                    "{indent}    int start{id} = list{id}.startNewValue({index});"
                )
                .unwrap();
                writeln!(
                    code,
                    "{indent}    org.apache.arrow.vector.FieldVector child{id} = list{id}.getDataVector();"
                )
                .unwrap();
                for offset in 0..*length {
                    self.emit_write_one(
                        code,
                        &format!("{indent}    "),
                        elem_type,
                        &format!("child{id}"),
                        &format!("start{id} + {offset}"),
                        &format!("{value}[{offset}]"),
                        counter,
                    )?;
                }
                writeln!(code, "{indent}}}").unwrap();
            }
            Type::List { elem_type } => {
                let id = *counter;
                *counter += 1;
                writeln!(code, "{indent}{{").unwrap();
                writeln!(
                    code,
                    "{indent}    org.apache.arrow.vector.complex.ListVector list{id} = \
                     (org.apache.arrow.vector.complex.ListVector) {vector};"
                )
                .unwrap();
                writeln!(
                    code,
                    "{indent}    int start{id} = list{id}.startNewValue({index});"
                )
                .unwrap();
                writeln!(
                    code,
                    "{indent}    org.apache.arrow.vector.FieldVector child{id} = list{id}.getDataVector();"
                )
                .unwrap();
                writeln!(
                    code,
                    "{indent}    java.util.List<{}> values{id} = {value};",
                    self.java_type_boxed(elem_type)
                )
                .unwrap();
                writeln!(
                    code,
                    "{indent}    for (int j{id} = 0; j{id} < values{id}.size(); j{id}++) {{"
                )
                .unwrap();
                self.emit_write_one(
                    code,
                    &format!("{indent}        "),
                    elem_type,
                    &format!("child{id}"),
                    &format!("start{id} + j{id}"),
                    &format!("values{id}.get(j{id})"),
                    counter,
                )?;
                writeln!(code, "{indent}    }}").unwrap();
                writeln!(
                    code,
                    "{indent}    list{id}.endValue({index}, values{id}.size());"
                )
                .unwrap();
                writeln!(code, "{indent}}}").unwrap();
            }
            Type::Object { fqname } => {
                writeln!(
                    code,
                    "{indent}{}.writeOne({vector}, {index}, {value});",
                    java_class(fqname)
                )
                .unwrap();
            }
        }
        Ok(())
    }

    // ------------------------------------------------------------ components

    fn render_component(&self, object: &'a Object) -> Result<String> {
        let package = java_package(&object.fqname);
        let name = java_class_name(&object.name);
        let mut code = String::new();
        write_header(&mut code, &package);
        write_docs(&mut code, &object.docs, "");
        if let ObjectClass::Enum(repr) = object.class {
            self.render_enum(&mut code, object, repr, true)?;
            return Ok(code);
        }

        writeln!(code, "public record {name}(").unwrap();
        for (index, field) in object.fields.iter().enumerate() {
            let comma = if index + 1 == object.fields.len() {
                ""
            } else {
                ","
            };
            write_docs(&mut code, &field.docs, "    ");
            writeln!(
                code,
                "    {} {}{}",
                self.java_type(&field.typ, field.is_nullable),
                Self::java_field_name(field),
                comma
            )
            .unwrap();
        }
        writeln!(code, ") implements {CORE_PACKAGE}.Component {{").unwrap();
        if !object.fields.is_empty() {
            writeln!(code, "    public {name} {{").unwrap();
            for field in &object.fields {
                let field_name = Self::java_field_name(field);
                let reference_type = matches!(
                    field.typ,
                    Type::Object { .. } | Type::Utf8 | Type::Binary
                );
                if reference_type && !field.is_nullable {
                    writeln!(
                        code,
                        "        java.util.Objects.requireNonNull({field_name}, \"{field_name}\");"
                    )
                    .unwrap();
                }
                if matches!(field.typ, Type::List { .. }) {
                    if field.is_nullable {
                        writeln!(
                            code,
                            "        if ({field_name} != null) {{ {field_name} = java.util.List.copyOf({field_name}); }}"
                        )
                        .unwrap();
                    } else {
                        writeln!(
                            code,
                            "        {field_name} = java.util.List.copyOf({field_name});"
                        )
                        .unwrap();
                    }
                }
            }
            writeln!(code, "    }}").unwrap();
        }
        self.render_component_members(&mut code, object, &name)?;
        writeln!(code, "}}").unwrap();
        Ok(code)
    }

    fn render_component_members(
        &self,
        code: &mut String,
        object: &'a Object,
        name: &str,
    ) -> Result<()> {
        writeln!(
            code,
            "    public static final java.lang.String COMPONENT_TYPE = \"{}\";",
            object.fqname
        )
        .unwrap();
        writeln!(
            code,
            "    public static final {CORE_PACKAGE}.ComponentDescriptor DESCRIPTOR = \
             {CORE_PACKAGE}.ComponentDescriptor.component(COMPONENT_TYPE);"
        )
        .unwrap();
        writeln!(code).unwrap();
        let field = &object.fields[0];
        let dt = self.data_type_for_field(field);
        let arrow_type = self.arrow_type_expr(&dt);
        let arrow_field = self.arrow_field_expr_raw(&dt, "name", "nullable");
        writeln!(
            code,
            "    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {{"
        )
        .unwrap();
        writeln!(code, "        return {arrow_type};").unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static org.apache.arrow.vector.types.pojo.Field arrowField(\
             java.lang.String name, boolean nullable) {{"
        )
        .unwrap();
        writeln!(code, "        return {arrow_field};").unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static {CORE_PACKAGE}.ComponentBatch batch(\
             {CORE_PACKAGE}.ComponentDescriptor descriptor, \
             org.apache.arrow.memory.BufferAllocator allocator, \
             java.util.List<{name}> values) {{"
        )
        .unwrap();
        writeln!(
            code,
            "        return {CORE_PACKAGE}.ComponentBatch.of(descriptor, arrowField(\"item\", true), \
             allocator, values, {name}::write);"
        )
        .unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static {CORE_PACKAGE}.ComponentBatch batch(\
             {CORE_PACKAGE}.ComponentDescriptor descriptor, \
             org.apache.arrow.memory.BufferAllocator allocator, {name} value) {{"
        )
        .unwrap();
        writeln!(
            code,
            "        return batch(descriptor, allocator, java.util.List.of(value));"
        )
        .unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static {CORE_PACKAGE}.ComponentBatch batch(\
             org.apache.arrow.memory.BufferAllocator allocator, {name} value) {{"
        )
        .unwrap();
        writeln!(
            code,
            "        return batch(DESCRIPTOR, allocator, java.util.List.of(value));"
        )
        .unwrap();
        writeln!(code, "    }}").unwrap();

        if let ObjectClass::Enum(repr) = object.class {
            self.render_enum_write_methods(code, object, name, repr)?;
        } else {
            self.render_component_write_methods(code, object, name)?;
        }

        // Fixed-size scalar convenience factory, e.g. Position2D.of(x, y).
        if let Some((elem_type, length)) = component_fixed_size_scalar(self, object) {
            let elem = self.java_type(&elem_type, false);
            let field_name = Self::java_field_name(&object.fields[0]);
            writeln!(code).unwrap();
            writeln!(code, "    public static {name} of({elem}... values) {{").unwrap();
            writeln!(
                code,
                "        if (values.length != {length}) throw new java.lang.IllegalArgumentException(\
                 \"{name} needs exactly {length} values, got \" + values.length);"
            )
            .unwrap();
            writeln!(
                code,
                "        return new {name}(new {}(values));",
                self.java_type(&object.fields[0].typ, false)
            )
            .unwrap();
            let _ = field_name;
            writeln!(code, "    }}").unwrap();
        }
        Ok(())
    }

    fn render_component_write_methods(
        &self,
        code: &mut String,
        object: &'a Object,
        name: &str,
    ) -> Result<()> {
        let field = &object.fields[0];
        let vector_class = self.vector_class(&field.typ);
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static void write(org.apache.arrow.vector.FieldVector vector, \
             java.util.List<{name}> values) {{"
        )
        .unwrap();
        writeln!(code, "        int count = values.size();").unwrap();
        writeln!(code, "        for (int i = 0; i < count; i++) {{").unwrap();
        writeln!(code, "            {name} value = values.get(i);").unwrap();
        if field.typ.is_union(self.objects) {
            writeln!(code, "            writeOne(vector, i, value);").unwrap();
        } else {
            writeln!(code, "            if (value == null) {{").unwrap();
            writeln!(code, "                (({vector_class}) vector).setNull(i);").unwrap();
            writeln!(code, "            }} else {{").unwrap();
            writeln!(code, "                writeOne(vector, i, value);").unwrap();
            writeln!(code, "            }}").unwrap();
        }
        writeln!(code, "        }}").unwrap();
        writeln!(code, "        (({vector_class}) vector).setValueCount(count);").unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static void writeOne(org.apache.arrow.vector.FieldVector vector, \
             int index, {name} value) {{"
        )
        .unwrap();
        let field_name = Self::java_field_name(field);
        let mut counter = 0usize;
        self.emit_write_one(
            code,
            "        ",
            &field.typ,
            "vector",
            "index",
            &format!("value.{field_name}()"),
            &mut counter,
        )?;
        writeln!(code, "    }}").unwrap();
        Ok(())
    }

    // ------------------------------------------------------------ archetypes

    fn render_archetype(&self, object: &'a Object) -> Result<String> {
        let package = java_package(&object.fqname);
        let name = java_class_name(&object.name);
        let mut code = String::new();
        write_header(&mut code, &package);
        write_docs(&mut code, &object.docs, "");

        writeln!(code, "public record {name}(").unwrap();
        for (index, field) in object.fields.iter().enumerate() {
            let comma = if index + 1 == object.fields.len() {
                ""
            } else {
                ","
            };
            write_docs(&mut code, &field.docs, "    ");
            let nullable = !field.has_attr(RerunAttr::Required);
            writeln!(
                code,
                "    {} {}{}",
                self.java_type(&field.typ, nullable),
                Self::java_field_name(field),
                comma
            )
            .unwrap();
        }
        writeln!(code, ") implements {CORE_PACKAGE}.Archetype {{").unwrap();
        if !object.fields.is_empty() {
            writeln!(code, "    public {name} {{").unwrap();
            for field in &object.fields {
                let field_name = Self::java_field_name(field);
                if field.has_attr(RerunAttr::Required) {
                    writeln!(
                        code,
                        "        java.util.Objects.requireNonNull({field_name}, \"{field_name}\");"
                    )
                    .unwrap();
                }
                if matches!(field.typ, Type::List { .. }) {
                    if field.has_attr(RerunAttr::Required) {
                        writeln!(
                            code,
                            "        {field_name} = java.util.List.copyOf({field_name});"
                        )
                        .unwrap();
                    } else {
                        writeln!(
                            code,
                            "        if ({field_name} != null) {{ {field_name} = java.util.List.copyOf({field_name}); }}"
                        )
                        .unwrap();
                    }
                }
            }
            writeln!(code, "    }}").unwrap();
        }

        writeln!(
            code,
            "    public static final java.lang.String ARCHETYPE_NAME = \"{}\";",
            object.fqname
        )
        .unwrap();
        writeln!(
            code,
            "    public static final int NUM_COMPONENTS = {};",
            object.fields.len()
        )
        .unwrap();

        let short = java_class_name(&object.name);
        for field in &object.fields {
            let field_name = Self::java_field_name(field);
            let component_fqname = field.typ.fqname().unwrap_or_default();
            let component = &self.objects[component_fqname];
            let descriptor_name = format!("DESCRIPTOR_{}", to_screaming_snake(&field_name));
            writeln!(
                code,
                "    private static final {CORE_PACKAGE}.ComponentDescriptor {descriptor_name} = \
                 new {CORE_PACKAGE}.ComponentDescriptor(ARCHETYPE_NAME, \"{short}:{}\", \"{}\");",
                field.name, component.fqname
            )
            .unwrap();
            writeln!(code).unwrap();
            write_docs(&mut code, &field.docs, "    ");
            writeln!(
                code,
                "    public static {CORE_PACKAGE}.ComponentDescriptor descriptor{}() {{",
                field.pascal_case_name()
            )
            .unwrap();
            writeln!(code, "        return {descriptor_name};").unwrap();
            writeln!(code, "    }}").unwrap();
        }

        for (label, attr) in [
            ("requiredComponents", RerunAttr::Required),
            ("recommendedComponents", RerunAttr::Recommended),
            ("optionalComponents", RerunAttr::Optional),
        ] {
            let descriptors = object
                .fields
                .iter()
                .filter(|field| field.has_attr(attr))
                .map(|field| {
                    format!("descriptor{}()", field.pascal_case_name())
                })
                .collect::<Vec<_>>()
                .join(", ");
            writeln!(code).unwrap();
            writeln!(
                code,
                "    public static java.util.List<{CORE_PACKAGE}.ComponentDescriptor> {label}() {{"
            )
            .unwrap();
            writeln!(code, "        return java.util.List.of({descriptors});").unwrap();
            writeln!(code, "    }}").unwrap();
        }

        // Serialization.
        writeln!(code).unwrap();
        writeln!(code, "    @Override").unwrap();
        writeln!(
            code,
            "    public java.util.List<{CORE_PACKAGE}.ComponentBatch> serialize(\
             org.apache.arrow.memory.BufferAllocator allocator) {{"
        )
        .unwrap();
        writeln!(
            code,
            "        java.util.List<{CORE_PACKAGE}.ComponentBatch> batches = \
             new java.util.ArrayList<>(NUM_COMPONENTS);"
        )
        .unwrap();
        for field in &object.fields {
            let field_name = Self::java_field_name(field);
            let component_fqname = field.typ.fqname().unwrap_or_default();
            let component_class = java_class(component_fqname);
            let descriptor = format!("descriptor{}()", field.pascal_case_name());
            if field.has_attr(RerunAttr::Required) {
                writeln!(
                    code,
                    "        batches.add({component_class}.batch({descriptor}, allocator, {field_name}));"
                )
                .unwrap();
            } else {
                writeln!(code, "        if ({field_name} != null) {{").unwrap();
                writeln!(
                    code,
                    "            batches.add({component_class}.batch({descriptor}, allocator, {field_name}));"
                )
                .unwrap();
                writeln!(code, "        }}").unwrap();
            }
        }
        writeln!(code, "        return batches;").unwrap();
        writeln!(code, "    }}").unwrap();

        // Builder.
        writeln!(code).unwrap();
        writeln!(code, "    public static Builder builder() {{ return new Builder(); }}").unwrap();
        writeln!(code).unwrap();
        writeln!(code, "    public static final class Builder {{").unwrap();
        for field in &object.fields {
            let nullable = !field.has_attr(RerunAttr::Required);
            writeln!(
                code,
                "        private {} {};",
                self.java_type(&field.typ, nullable),
                Self::java_field_name(field)
            )
            .unwrap();
        }
        writeln!(code, "        private Builder() {{}}").unwrap();
        for field in &object.fields {
            let field_name = Self::java_field_name(field);
            let method = format!("with{}", field.pascal_case_name());
            let component_fqname = field.typ.fqname().unwrap_or_default();
            let component_class = java_class(component_fqname);
            writeln!(code).unwrap();
            if matches!(field.typ, Type::List { .. }) {
                writeln!(
                    code,
                    "        public Builder {method}(java.util.List<{component_class}> value) {{"
                )
                .unwrap();
                writeln!(
                    code,
                    "            this.{field_name} = java.util.List.copyOf(value);"
                )
                .unwrap();
                writeln!(code, "            return this;").unwrap();
                writeln!(code, "        }}").unwrap();
                writeln!(code).unwrap();
                writeln!(
                    code,
                    "        public Builder {method}({component_class}... value) {{"
                )
                .unwrap();
                writeln!(
                    code,
                    "            return {method}(java.util.List.of(value));"
                )
                .unwrap();
                writeln!(code, "        }}").unwrap();
            } else {
                writeln!(
                    code,
                    "        public Builder {method}({component_class} value) {{"
                )
                .unwrap();
                writeln!(code, "            this.{field_name} = value;").unwrap();
                writeln!(code, "            return this;").unwrap();
                writeln!(code, "        }}").unwrap();
            }
        }
        writeln!(code).unwrap();
        writeln!(code, "        public {name} build() {{").unwrap();
        let args = object
            .fields
            .iter()
            .map(|field| Self::java_field_name(field))
            .collect::<Vec<_>>()
            .join(", ");
        writeln!(code, "            return new {name}({args});").unwrap();
        writeln!(code, "        }}").unwrap();
        writeln!(code, "    }}").unwrap();

        // Convenience constructor with the required fields.
        let required = object
            .fields
            .iter()
            .filter(|field| field.has_attr(RerunAttr::Required))
            .collect::<Vec<_>>();
        writeln!(code).unwrap();
        writeln!(code, "    public static {name} of(").unwrap();
        for (index, field) in required.iter().enumerate() {
            let comma = if index + 1 == required.len() { "" } else { "," };
            writeln!(
                code,
                "        {} {}{}",
                self.java_type(&field.typ, false),
                Self::java_field_name(field),
                comma
            )
            .unwrap();
        }
        writeln!(code, "    ) {{").unwrap();
        let required_names = required
            .iter()
            .map(|field| Self::java_field_name(field))
            .collect::<Vec<_>>();
        let mut args = Vec::new();
        for field in &object.fields {
            let field_name = Self::java_field_name(field);
            if required_names.contains(&field_name) {
                args.push(field_name);
            } else {
                args.push("null".to_owned());
            }
        }
        writeln!(
            code,
            "        return new {name}({});",
            args.join(", ")
        )
        .unwrap();
        writeln!(code, "    }}").unwrap();

        writeln!(code, "}}").unwrap();
        Ok(code)
    }

    // ----------------------------------------------------------------- views

    fn render_view(&self, object: &'a Object) -> Result<String> {
        let package = java_package(&object.fqname);
        let name = java_class_name(&object.name);
        let mut code = String::new();
        write_header(&mut code, &package);
        write_docs(&mut code, &object.docs, "");
        writeln!(code, "public record {name}(").unwrap();
        for (index, field) in object.fields.iter().enumerate() {
            let comma = if index + 1 == object.fields.len() {
                ""
            } else {
                ","
            };
            write_docs(&mut code, &field.docs, "    ");
            writeln!(
                code,
                "    {} {}{}",
                self.java_type(&field.typ, true),
                Self::java_field_name(field),
                comma
            )
            .unwrap();
        }
        writeln!(code, ") {{").unwrap();
        writeln!(
            code,
            "    public static final java.lang.String VIEW_NAME = \"{}\";",
            object.fqname
        )
        .unwrap();
        writeln!(code).unwrap();
        writeln!(code, "    public static Builder builder() {{ return new Builder(); }}").unwrap();
        writeln!(code).unwrap();
        writeln!(code, "    public static final class Builder {{").unwrap();
        for field in &object.fields {
            writeln!(
                code,
                "        private {} {};",
                self.java_type(&field.typ, true),
                Self::java_field_name(field)
            )
            .unwrap();
        }
        writeln!(code, "        private Builder() {{}}").unwrap();
        for field in &object.fields {
            let field_name = Self::java_field_name(field);
            let method = format!("with{}", field.pascal_case_name());
            let typ = self.java_type(&field.typ, true);
            writeln!(code).unwrap();
            writeln!(code, "        public Builder {method}({typ} value) {{").unwrap();
            writeln!(code, "            this.{field_name} = value;").unwrap();
            writeln!(code, "            return this;").unwrap();
            writeln!(code, "        }}").unwrap();
        }
        writeln!(code).unwrap();
        writeln!(code, "        public {name} build() {{").unwrap();
        let args = object
            .fields
            .iter()
            .map(|field| Self::java_field_name(field))
            .collect::<Vec<_>>()
            .join(", ");
        writeln!(code, "            return new {name}({args});").unwrap();
        writeln!(code, "        }}").unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code, "}}").unwrap();
        Ok(code)
    }
}

// -------------------------------------------------------------------- helpers

fn fixed_size_scalar_field(object: &Object) -> Option<(Type, usize)> {
    let field = object.fields.first()?;
    if let Type::FixedSizeList { elem_type, length } = &field.typ {
        if matches!(**elem_type, Type::Atomic(_)) {
            return Some(((**elem_type).clone(), *length));
        }
    }
    None
}

fn component_fixed_size_scalar(generator: &JavaGenerator<'_>, object: &Object) -> Option<(Type, usize)> {
    let field = object.fields.first()?;
    let Type::Object { fqname } = &field.typ else {
        return None;
    };
    let inner = &generator.objects[fqname.as_str()];
    if !inner.is_arrow_transparent() {
        return None;
    }
    fixed_size_scalar_field(inner)
}

fn boxed_primitive(primitive: &str) -> &'static str {
    match primitive {
        "boolean" => "java.lang.Boolean",
        "byte" => "java.lang.Byte",
        "short" => "java.lang.Short",
        "int" => "java.lang.Integer",
        "long" => "java.lang.Long",
        "float" => "java.lang.Float",
        "double" => "java.lang.Double",
        _ => "java.lang.Object",
    }
}

fn atomic_vector(atomic: &AtomicDataType) -> &'static str {
    match atomic {
        AtomicDataType::Null => "org.apache.arrow.vector.NullVector",
        AtomicDataType::Boolean => "org.apache.arrow.vector.BitVector",
        AtomicDataType::Int8 => "org.apache.arrow.vector.TinyIntVector",
        AtomicDataType::Int16 => "org.apache.arrow.vector.SmallIntVector",
        AtomicDataType::Int32 => "org.apache.arrow.vector.IntVector",
        AtomicDataType::Int64 => "org.apache.arrow.vector.BigIntVector",
        AtomicDataType::UInt8 => "org.apache.arrow.vector.UInt1Vector",
        AtomicDataType::UInt16 => "org.apache.arrow.vector.UInt2Vector",
        AtomicDataType::UInt32 => "org.apache.arrow.vector.UInt4Vector",
        AtomicDataType::UInt64 => "org.apache.arrow.vector.UInt8Vector",
        AtomicDataType::Float16 => "org.apache.arrow.vector.Float2Vector",
        AtomicDataType::Float32 => "org.apache.arrow.vector.Float4Vector",
        AtomicDataType::Float64 => "org.apache.arrow.vector.Float8Vector",
    }
}

fn atomic_arrow_const(atomic: &AtomicDataType) -> &'static str {
    match atomic {
        AtomicDataType::Null => "org.teamvan.moneyshift.ArrowFields.NULL",
        AtomicDataType::Boolean => "org.teamvan.moneyshift.ArrowFields.BOOL",
        AtomicDataType::Int8 => "org.teamvan.moneyshift.ArrowFields.INT8",
        AtomicDataType::Int16 => "org.teamvan.moneyshift.ArrowFields.INT16",
        AtomicDataType::Int32 => "org.teamvan.moneyshift.ArrowFields.INT32",
        AtomicDataType::Int64 => "org.teamvan.moneyshift.ArrowFields.INT64",
        AtomicDataType::UInt8 => "org.teamvan.moneyshift.ArrowFields.UINT8",
        AtomicDataType::UInt16 => "org.teamvan.moneyshift.ArrowFields.UINT16",
        AtomicDataType::UInt32 => "org.teamvan.moneyshift.ArrowFields.UINT32",
        AtomicDataType::UInt64 => "org.teamvan.moneyshift.ArrowFields.UINT64",
        AtomicDataType::Float16 => "org.teamvan.moneyshift.ArrowFields.FLOAT16",
        AtomicDataType::Float32 => "org.teamvan.moneyshift.ArrowFields.FLOAT32",
        AtomicDataType::Float64 => "org.teamvan.moneyshift.ArrowFields.FLOAT64",
    }
}

fn arrow_type_expr(data_type: &DataType) -> String {
    match data_type {
        DataType::Atomic(atomic) => atomic_arrow_const(atomic).to_owned(),
        DataType::Binary => "org.teamvan.moneyshift.ArrowFields.BINARY".to_owned(),
        DataType::Utf8 => "org.teamvan.moneyshift.ArrowFields.UTF8".to_owned(),
        DataType::Object { fqname, .. } => format!("{}.arrowType()", java_class(fqname)),
        DataType::FixedSizeList(_, length) => format!(
            "new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList({length})"
        ),
        DataType::List(_) => "new org.apache.arrow.vector.types.pojo.ArrowType.List()".to_owned(),
        DataType::Struct(_) => {
            "new org.apache.arrow.vector.types.pojo.ArrowType.Struct()".to_owned()
        }
        DataType::Union(fields, mode) => {
            let arrow_mode = match mode {
                UnionMode::Dense => "Dense",
                UnionMode::Sparse => "Sparse",
            };
            let type_ids = (0..fields.len())
                .map(|index| index.to_string())
                .collect::<Vec<_>>()
                .join(", ");
            format!(
                "new org.apache.arrow.vector.types.pojo.ArrowType.Union(\
                 org.apache.arrow.vector.types.UnionMode.{arrow_mode}, new int[]{{{type_ids}}})"
            )
        }
    }
}

fn union_variant_name(field: &re_types_builder::ObjectField) -> String {
    java_class_name(&field.pascal_case_name())
}

fn snake_to_camel(name: &str) -> String {
    if name.contains('_') {
        let mut result = String::new();
        for (index, part) in name.split('_').filter(|part| !part.is_empty()).enumerate() {
            if index == 0 {
                result.push_str(&part.to_lowercase());
            } else {
                let mut chars = part.chars();
                if let Some(first) = chars.next() {
                    result.extend(first.to_uppercase());
                    result.push_str(chars.as_str());
                }
            }
        }
        result
    } else {
        let mut chars = name.chars();
        match chars.next() {
            Some(first) => first.to_lowercase().chain(chars).collect(),
            None => String::new(),
        }
    }
}

fn to_screaming_snake(name: &str) -> String {
    let mut out = String::new();
    for (index, ch) in name.chars().enumerate() {
        if ch.is_uppercase() && index != 0 {
            out.push('_');
        }
        out.extend(ch.to_uppercase());
    }
    out
}

fn java_class_name(name: &str) -> String {
    let mut result = String::new();
    for (index, character) in name.chars().enumerate() {
        if character.is_ascii_alphanumeric() || character == '_' {
            if index == 0 && character.is_ascii_digit() {
                result.push('_');
            }
            result.push(character);
        } else {
            result.push('_');
        }
    }
    if result.is_empty() {
        result.push('_');
    }
    if is_java_keyword(&result) {
        format!("_{result}")
    } else {
        result
    }
}

fn java_identifier(name: &str) -> String {
    java_class_name(name)
}

fn java_enum_constant(name: &str) -> String {
    let mut result = String::new();
    for (index, character) in name.chars().enumerate() {
        if character.is_ascii_alphanumeric() || character == '_' {
            if index == 0 && character.is_ascii_digit() {
                result.push('_');
            }
            result.push(character);
        } else {
            result.push('_');
        }
    }
    if result.is_empty() {
        result.push('_');
    }
    if is_java_keyword(&result) || result == "null" {
        format!("_{result}")
    } else {
        result
    }
}

fn is_java_keyword(name: &str) -> bool {
    matches!(
        name,
        "abstract"
            | "assert"
            | "boolean"
            | "break"
            | "byte"
            | "case"
            | "catch"
            | "char"
            | "class"
            | "const"
            | "continue"
            | "default"
            | "do"
            | "double"
            | "else"
            | "enum"
            | "extends"
            | "final"
            | "finally"
            | "float"
            | "for"
            | "goto"
            | "if"
            | "implements"
            | "import"
            | "instanceof"
            | "int"
            | "interface"
            | "long"
            | "native"
            | "new"
            | "package"
            | "private"
            | "protected"
            | "public"
            | "return"
            | "short"
            | "static"
            | "strictfp"
            | "super"
            | "switch"
            | "synchronized"
            | "this"
            | "throw"
            | "throws"
            | "transient"
            | "try"
            | "void"
            | "volatile"
            | "while"
            | "true"
            | "false"
            | "null"
    )
}

fn write_header(code: &mut String, package: &str) {
    writeln!(code, "// DO NOT EDIT: generated from Rerun type definitions.").unwrap();
    writeln!(code, "package {package};\n").unwrap();
}

fn escape_java_string(text: &str) -> String {
    text.replace('\\', "\\\\").replace('"', "\\\"")
}

fn write_docs(code: &mut String, docs: &Docs, indent: &str) {
    let lines = docs.only_lines_tagged("");
    if lines.is_empty() {
        return;
    }

    writeln!(code, "{indent}/**").unwrap();
    for line in lines {
        writeln!(code, "{indent} * {}", java_doc_line(line)).unwrap();
    }
    writeln!(code, "{indent} */").unwrap();
}

fn java_doc_line(line: &str) -> String {
    let mut output = String::new();
    let mut rest = line;

    while !rest.is_empty() {
        let code_start = rest.find('`');
        let rust_link_start = rest.find("[`");
        let markdown_link_start = rest.find('[');

        if rust_link_start
            .is_some_and(|link_start| code_start.is_none_or(|code_start| link_start < code_start))
        {
            let link_start = rust_link_start.unwrap();
            output.push_str(&rest[..link_start]);
            let after_start = &rest[link_start + 2..];
            if let Some(link_end) = after_start.find("`]") {
                let label = &after_start[..link_end];
                let after_label = &after_start[link_end + 2..];
                let (target, remainder) = if let Some(target) = after_label.strip_prefix('[') {
                    if let Some(target_end) = target.find(']') {
                        (&target[1..target_end], &target[target_end + 1..])
                    } else {
                        (label, after_label)
                    }
                } else {
                    (label, after_label)
                };
                output.push_str("{@link ");
                output.push_str(&java_doc_link(target));
                if target != label {
                    output.push(' ');
                    output.push_str(&escape_javadoc_text(label));
                }
                output.push('}');
                rest = remainder;
                continue;
            }
        }

        if markdown_link_start
            .is_some_and(|link_start| code_start.is_none_or(|code_start| link_start < code_start))
        {
            let link_start = markdown_link_start.unwrap();
            output.push_str(&rest[..link_start]);
            let after_start = &rest[link_start + 1..];
            if let Some(label_end) = after_start.find("](") {
                let label = &after_start[..label_end];
                let after_label = &after_start[label_end + 2..];
                if let Some(url_end) = after_label.find(')') {
                    output.push_str("<a href=\"");
                    output.push_str(&escape_javadoc_attribute(&after_label[..url_end]));
                    output.push_str("\">");
                    output.push_str(&escape_javadoc_text(label));
                    output.push_str("</a>");
                    rest = &after_label[url_end + 1..];
                    continue;
                }
            }
        }

        if let Some(code_start) = code_start {
            output.push_str(&rest[..code_start]);
            let after_start = &rest[code_start + 1..];
            if let Some(code_end) = after_start.find('`') {
                output.push_str("{@code ");
                output.push_str(&escape_javadoc_code(&after_start[..code_end]));
                output.push('}');
                rest = &after_start[code_end + 1..];
                continue;
            }
        }

        output.push_str(rest);
        break;
    }

    output.replace("*/", "*\\/")
}

fn java_doc_link(path: &str) -> String {
    let path = if let Some(path) = path.strip_prefix("crate::") {
        format!("{JAVA_PACKAGE_PREFIX}.{}", path.replace("::", "."))
    } else if let Some(path) = path.strip_prefix("rerun::") {
        format!("{JAVA_PACKAGE_PREFIX}.{}", path.replace("::", "."))
    } else {
        path.to_owned()
    };
    let path = path.replace("::", ".").replace(".datatypes.", ".encodings.");
    let mut parts = path.rsplitn(2, '.');
    let last = parts.next().unwrap_or_default();
    let Some(prefix) = parts.next() else {
        return path;
    };
    if last
        .chars()
        .next()
        .is_some_and(|character| character.is_ascii_lowercase())
    {
        format!("{prefix}#{last}")
    } else {
        format!("{prefix}.{last}")
    }
}

fn escape_javadoc_code(text: &str) -> String {
    text.replace('&', "&amp;")
        .replace('<', "&lt;")
        .replace('>', "&gt;")
}

fn escape_javadoc_text(text: &str) -> String {
    text.replace('&', "&amp;")
        .replace('<', "&lt;")
        .replace('>', "&gt;")
}

fn escape_javadoc_attribute(text: &str) -> String {
    text.replace('&', "&amp;")
        .replace('"', "&quot;")
        .replace('<', "&lt;")
        .replace('>', "&gt;")
}

#[cfg(test)]
mod tests {
    use re_types_builder::Docs;

    use super::{
        java_doc_line, java_identifier, java_package, snake_to_camel, write_docs,
    };

    #[test]
    fn maps_rerun_names_to_java_packages() {
        assert_eq!(
            java_package("rerun.components.Position2D"),
            "org.teamvan.moneyshift.components"
        );
        assert_eq!(
            java_package("rerun.blueprint.archetypes.ContainerBlueprint"),
            "org.teamvan.moneyshift.blueprint.archetypes"
        );
    }

    #[test]
    fn converts_snake_case() {
        assert_eq!(snake_to_camel("show_labels"), "showLabels");
        assert_eq!(snake_to_camel("id"), "id");
    }

    #[test]
    fn escapes_invalid_field_names() {
        assert_eq!(java_identifier("3d-value"), "_3d_value");
        assert_eq!(java_identifier("class"), "_class");
    }

    #[test]
    fn renders_docs_as_javadoc() {
        let (_report, reporter) = re_types_builder::report::init();
        let docs = Docs::from_lines(
            &reporter,
            "test",
            "Test",
            [" A description.", "", " A closing `*/`."].into_iter(),
        );
        let mut code = String::new();
        write_docs(&mut code, &docs, "    ");
        assert_eq!(
            code,
            "    /**\n     * A description.\n     * \n     * A closing {@code *\\/}.\n     */\n"
        );
    }

    #[test]
    fn converts_rustdoc_links_and_code() {
        assert_eq!(
            java_doc_line("See [`rerun::components::Position2D`] and [`crate::foo::draw`]."),
            "See {@link org.teamvan.moneyshift.components.Position2D} and {@link org.teamvan.moneyshift.foo#draw}."
        );
        assert_eq!(
            java_doc_line("Use `Vec<T>` or [the guide](https://example.com?a=1&b=2)."),
            "Use {@code Vec&lt;T&gt;} or <a href=\"https://example.com?a=1&amp;b=2\">the guide</a>."
        );
    }
}
