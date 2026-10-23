use std::fmt::Write as _;
use std::fs;

use anyhow::{Context as _, Result};
use camino::{Utf8Path, Utf8PathBuf};
use re_types_builder::{
    AtomicDataType, Docs, EnumIntegerType, Object, ObjectClass, Objects, RerunAttr, Type,
    TypeRegistry,
};

const JAVA_PACKAGE_PREFIX: &str = "org.teamvan.moneyshift";
const CORE_PACKAGE: &str = "org.teamvan.moneyshift";

/// Namespaces whose definitions only exist to exercise Rerun's own codegen. They are not part of
/// the public SDK, so they are neither generated nor cleaned up as public packages.
const SKIPPED_NAMESPACES: [&str; 1] = ["rerun.testing."];

/// Whether a definition belongs to one of the [`SKIPPED_NAMESPACES`].
fn is_skipped(fqname: &str) -> bool {
    SKIPPED_NAMESPACES
        .iter()
        .any(|prefix| fqname.starts_with(prefix))
        // Blueprints are not part of the NetworkTables surface: they are viewer configuration, not
        // robot telemetry. Views exist only to describe blueprints, so they go too.
        || fqname.starts_with("rerun.blueprint.")
        || fqname.starts_with("rerun.views.")
}

/// Java types that can be passed to the generated code without an encoding wrapper.
const PRIMITIVE_JAVA_TYPES: [&str; 8] = [
    "boolean",
    "byte",
    "short",
    "int",
    "long",
    "float",
    "double",
    "java.lang.String",
];

pub fn generate(
    output_dir: &Utf8Path,
    objects: &Objects,
    type_registry: &TypeRegistry,
) -> Result<()> {
    let generator = JavaGenerator {
        objects,
        registry: type_registry,
    };

    // Remove previous generated trees so that a renamed, deleted, or newly skipped definition
    // cannot leave a stale Java class behind. This includes the trees of skipped namespaces, so
    // that e.g. an earlier run's `blueprint` package is dropped. Only the top-level packages
    // implied by the definitions are touched; the hand-written core lives elsewhere.
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

    for object in objects
        .values()
        .filter(|object| !is_skipped(&object.fqname))
    {
        let path = output_path(output_dir, object);
        println!(
            "[+] Generating Java code for {} to {}",
            &object.fqname, &path
        );
        if let Some(parent) = path.parent() {
            fs::create_dir_all(parent).with_context(|| format!("creating {parent}"))?;
        }
        fs::write(&path, generator.render(object)?).with_context(|| format!("writing {path}"))?;
    }

    validate_representable(objects)?;
    Ok(())
}

/// Every datatype in the tree must have a NetworkTables column layout.
///
/// The layout covers everything Rerun defines today; if a future Rerun version adds a new atomic
/// type, fail loudly at generation time instead of silently dropping data at runtime.
fn validate_representable(objects: &Objects) -> Result<()> {
    for object in objects
        .values()
        .filter(|object| !is_skipped(&object.fqname))
    {
        if matches!(object.class, ObjectClass::Enum(_)) {
            // Enum variants are integers; a `null` variant is just a zero payload.
            continue;
        }
        let inside_union = matches!(object.class, ObjectClass::Union);
        for field in &object.fields {
            validate_type(&object.fqname, &field.typ, objects, inside_union)?;
        }
    }
    Ok(())
}

fn validate_type(owner: &str, typ: &Type, objects: &Objects, inside_union: bool) -> Result<()> {
    match typ {
        Type::Atomic(AtomicDataType::Null) => {
            if !inside_union {
                anyhow::bail!(
                    "{owner} contains a bare `null` field, which the NetworkTables layout cannot represent"
                );
            }
        }
        Type::Atomic(_) | Type::Binary | Type::Utf8 => {}
        Type::FixedSizeList { elem_type, .. } => {
            validate_type(owner, elem_type, objects, false)?;
        }
        Type::List { elem_type } => {
            validate_type(owner, elem_type, objects, false)?;
        }
        Type::Object { fqname } => {
            let object = objects
                .get(fqname.as_str())
                .with_context(|| format!("{owner} refers to unknown definition {fqname}"))?;
            if matches!(object.class, ObjectClass::Enum(_)) {
                // Enum variants are integers; a `null` variant is just the zero payload case.
                return Ok(());
            }
            for field in &object.fields {
                validate_type(
                    &object.fqname,
                    &field.typ,
                    objects,
                    matches!(object.class, ObjectClass::Union),
                )?;
            }
        }
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

/// Maps `rerun.components.Position2D` to `org.teamvan.moneyshift.components`.
fn java_package(fqname: &str) -> String {
    let parent = fqname.rsplit_once('.').map_or(fqname, |(parent, _)| parent);
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
            re_types_builder::ObjectKind::View => {
                anyhow::bail!("views are not generated for the NetworkTables SDK")
            }
        }
    }

    // ---------------------------------------------------------------- types

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

    // ------------------------------------------------------------- encodings

    fn render_encoding(&self, object: &'a Object) -> Result<String> {
        let package = java_package(&object.fqname);
        let mut code = String::new();
        write_header(&mut code, &package);
        write_docs(&mut code, &object.docs, "", self.objects);
        match object.class {
            ObjectClass::Struct => self.render_encoding_struct(&mut code, object)?,
            ObjectClass::Enum(repr) => self.render_enum(&mut code, object, repr, false)?,
            ObjectClass::Union => self.render_union(&mut code, object)?,
        }
        Ok(code)
    }

    fn render_encoding_struct(&self, code: &mut String, object: &'a Object) -> Result<()> {
        let name = java_class_name(&object.name);

        self.render_record_declaration(code, object, &name, false, None)?;
        writeln!(
            code,
            "    public static final java.lang.String NAME = \"{}\";",
            object.fqname
        )
        .unwrap();
        writeln!(code).unwrap();

        self.render_static_columns(
            code,
            &name,
            Type::Object {
                fqname: object.fqname.clone(),
            },
        )?;
        writeln!(code).unwrap();
        writeln!(
            code,
            "    @Override\n    public void writeColumns(\n        {CORE_PACKAGE}.NtEntityWriter w, java.lang.String path, java.util.List<{name}> values) {{\n        writeColumnsStatic(w, path, values);\n    }}"
        )
        .unwrap();

        // A convenience constructor for types that are just a fixed-size scalar array.
        if let Some((elem_type, length)) = fixed_size_scalar_field(object) {
            let elem = self.java_type(&elem_type, false);
            writeln!(code).unwrap();
            writeln!(code, "    public static {name} of({elem}... values) {{").unwrap();
            writeln!(
                code,
                "        if (values.length != {length}) throw new java.lang.IllegalArgumentException(\
                 \"{name} needs exactly {length} values, got \" + values.length);"
            )
            .unwrap();
            writeln!(code, "        return new {name}(values);").unwrap();
            writeln!(code, "    }}").unwrap();
        }
        writeln!(code, "}}").unwrap();
        Ok(())
    }

    fn render_record_declaration(
        &self,
        code: &mut String,
        object: &'a Object,
        name: &str,
        is_component: bool,
        component_type: Option<&str>,
    ) -> Result<()> {
        let supertypes = if is_component {
            format!(" implements {CORE_PACKAGE}.NtComponent<{name}>")
        } else {
            format!(" implements {CORE_PACKAGE}.NtEncoding<{name}>")
        };
        writeln!(code, "public record {name}(").unwrap();
        for (index, field) in object.fields.iter().enumerate() {
            let comma = if index + 1 == object.fields.len() {
                ""
            } else {
                ","
            };
            write_docs(code, &field.docs, "    ", self.objects);
            writeln!(
                code,
                "    {} {}{}",
                self.java_type(&field.typ, field.is_nullable),
                Self::java_field_name(field),
                comma
            )
            .unwrap();
        }
        writeln!(code, "){supertypes} {{").unwrap();
        // Compact constructor: normalize lists and enforce required references.
        if !object.fields.is_empty() {
            writeln!(code, "    public {name} {{").unwrap();
            for field in &object.fields {
                let field_name = Self::java_field_name(field);
                let reference_type =
                    matches!(field.typ, Type::Object { .. } | Type::Utf8 | Type::Binary);
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

        let added_unwrapped = self.render_unwrapped_constructor(code, object, name);

        // Arrays destroy the default record equality, so provide deep versions.
        if object
            .fields
            .iter()
            .any(|field| matches!(field.typ, Type::FixedSizeList { .. }))
        {
            let comparisons = object
                .fields
                .iter()
                .map(|field| {
                    let field_name = Self::java_field_name(field);
                    if matches!(field.typ, Type::FixedSizeList { .. }) {
                        format!("java.util.Arrays.equals({field_name}, that.{field_name})")
                    } else {
                        format!("java.util.Objects.equals({field_name}, that.{field_name})")
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
            if !added_unwrapped {
                writeln!(code).unwrap();
            }
            writeln!(code, "    @Override").unwrap();
            writeln!(code, "    public boolean equals(java.lang.Object other) {{").unwrap();
            writeln!(code, "        if (this == other) return true;").unwrap();
            writeln!(
                code,
                "        if (!(other instanceof {name} that)) return false;"
            )
            .unwrap();
            writeln!(code, "        return {comparisons};").unwrap();
            writeln!(code, "    }}").unwrap();
            writeln!(code).unwrap();
            writeln!(code, "    @Override").unwrap();
            writeln!(code, "    public int hashCode() {{").unwrap();
            writeln!(code, "        return java.util.Objects.hash({hashes});").unwrap();
            writeln!(code, "    }}").unwrap();
        }

        if let Some(component_type) = component_type {
            writeln!(code).unwrap();
            writeln!(
                code,
                "    public static final java.lang.String COMPONENT_TYPE = \"{component_type}\";"
            )
            .unwrap();
            writeln!(code).unwrap();
            writeln!(
                code,
                "    @Override\n    public java.lang.String componentType() {{\n        return COMPONENT_TYPE;\n    }}"
            )
            .unwrap();
        }
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
            format!(" implements {CORE_PACKAGE}.NtComponent<{name}>")
        } else {
            format!(" implements {CORE_PACKAGE}.NtEncoding<{name}>")
        };
        writeln!(code, "public enum {name}{suffix} {{").unwrap();
        for (index, field) in object.fields.iter().enumerate() {
            let comma = if index + 1 == object.fields.len() {
                ";"
            } else {
                ","
            };
            write_docs(code, &field.docs, "    ", self.objects);
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
        writeln!(
            code,
            "            if (candidate.value == value) return candidate;"
        )
        .unwrap();
        writeln!(code, "        }}").unwrap();
        writeln!(
            code,
            "        throw new java.lang.IllegalArgumentException(\"unknown {name} value: \" + value);"
        )
        .unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static final java.lang.String NAME = \"{}\";",
            object.fqname
        )
        .unwrap();
        writeln!(code).unwrap();

        self.render_enum_columns(code, &name)?;
        writeln!(code).unwrap();
        writeln!(
            code,
            "    @Override\n    public void writeColumns(\
             {CORE_PACKAGE}.NtEntityWriter w, java.lang.String path, java.util.List<{name}> values) {{\
             \n        writeColumnsStatic(w, path, values);\n    }}"
        )
        .unwrap();

        if component {
            writeln!(
                code,
                "    public static final java.lang.String COMPONENT_TYPE = \"{}\";",
                object.fqname
            )
            .unwrap();
            writeln!(code).unwrap();
            writeln!(
                code,
                "    @Override\n    public java.lang.String componentType() {{\n        return COMPONENT_TYPE;\n    }}"
            )
            .unwrap();
        }
        let _ = repr;
        writeln!(code, "}}").unwrap();
        Ok(())
    }

    fn render_enum_columns(&self, code: &mut String, name: &str) -> Result<()> {
        writeln!(
            code,
            "    public static void writeColumnsStatic(\
             {CORE_PACKAGE}.NtEntityWriter w, java.lang.String path, java.util.List<{name}> values) {{"
        )
        .unwrap();
        writeln!(code, "        int n = values.size();").unwrap();
        writeln!(code, "        long[] column = new long[n];").unwrap();
        writeln!(code, "        boolean[] present = new boolean[n];").unwrap();
        writeln!(code, "        boolean anyNull = false;").unwrap();
        writeln!(code, "        for (int i = 0; i < n; i++) {{").unwrap();
        writeln!(code, "            {name} value = values.get(i);").unwrap();
        writeln!(code, "            present[i] = value != null;").unwrap();
        writeln!(code, "            anyNull |= value == null;").unwrap();
        writeln!(
            code,
            "            column[i] = value == null ? 0L : value.value();"
        )
        .unwrap();
        writeln!(code, "        }}").unwrap();
        writeln!(code, "        w.present(path, present, anyNull);").unwrap();
        writeln!(code, "        w.longs(path, column);").unwrap();
        writeln!(code, "    }}").unwrap();
        Ok(())
    }

    fn render_union(&self, code: &mut String, object: &'a Object) -> Result<()> {
        let name = java_class_name(&object.name);
        writeln!(
            code,
            "public sealed interface {name} extends {CORE_PACKAGE}.NtEncoding<{name}> permits"
        )
        .unwrap();
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
            write_docs(code, &field.docs, "    ", self.objects);
            if field.typ.is_unit() {
                writeln!(code, "    record {variant}() implements {name} {{}}").unwrap();
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

        writeln!(code).unwrap();
        writeln!(
            code,
            "    @Override\n    default void writeColumns(\
             {CORE_PACKAGE}.NtEntityWriter w, java.lang.String path, java.util.List<{name}> values) {{\
             \n        writeColumnsStatic(w, path, values);\n    }}"
        )
        .unwrap();

        writeln!(code).unwrap();
        self.render_static_columns(
            code,
            &name,
            Type::Object {
                fqname: object.fqname.clone(),
            },
        )?;

        writeln!(code, "}}").unwrap();
        Ok(())
    }

    // ------------------------------------------------------------- writers

    fn render_static_columns(&self, code: &mut String, name: &str, self_typ: Type) -> Result<()> {
        writeln!(
            code,
            "    /** Writes this type's NetworkTables columns; one entry per value. */"
        )
        .unwrap();
        writeln!(
            code,
            "    public static void writeColumnsStatic(\
             {CORE_PACKAGE}.NtEntityWriter w, java.lang.String path, java.util.List<{name}> values) {{"
        )
        .unwrap();
        writeln!(code, "        int n = values.size();").unwrap();
        let mut emitter = ColumnsEmitter::new(self);
        let chain = Chain::root("values.get(i)");
        let ctx = NodeCtx {
            index: "i".to_owned(),
            count: "n".to_owned(),
            suffix: String::new(),
        };
        emitter.emit(&self_typ, &chain, true, true, &ctx)?;
        code.push_str(&emitter.code);
        writeln!(code, "    }}").unwrap();
        Ok(())
    }

    // ------------------------------------------------------------ components

    fn render_component(&self, object: &'a Object) -> Result<String> {
        let package = java_package(&object.fqname);
        let name = java_class_name(&object.name);
        let mut code = String::new();
        write_header(&mut code, &package);
        write_docs(&mut code, &object.docs, "", self.objects);

        if let ObjectClass::Enum(repr) = object.class {
            self.render_enum(&mut code, object, repr, true)?;
            return Ok(code);
        }

        if object.class == ObjectClass::Union {
            // A component whose datatype is a union (e.g. `TextLogLevel`-style unions).
            self.render_component_union(&mut code, object)?;
            return Ok(code);
        }

        self.render_record_declaration(&mut code, object, &name, true, Some(&object.fqname))?;
        // Components are transparent wrappers: the column layout is the wrapped encoding's.
        let field = &object.fields[0];
        let field_name = Self::java_field_name(field);
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static void writeColumnsStatic(\
             {CORE_PACKAGE}.NtEntityWriter w, java.lang.String path, java.util.List<{name}> values) {{"
        )
        .unwrap();
        writeln!(code, "        int n = values.size();").unwrap();
        let chain = Chain::root("values.get(i)").with(format!(".{field_name}()"), true);
        let mut emitter = ColumnsEmitter::new(self);
        let ctx = NodeCtx {
            index: "i".to_owned(),
            count: "n".to_owned(),
            suffix: String::new(),
        };
        emitter.emit(&field.typ, &chain, true, true, &ctx)?;
        code.push_str(&emitter.code);
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        writeln!(
            code,
            "    @Override\n    public void writeColumns(\n        {CORE_PACKAGE}.NtEntityWriter w, java.lang.String path, java.util.List<{name}> values) {{\n        writeColumnsStatic(w, path, values);\n    }}"
        )
        .unwrap();

        // Fixed-size scalar convenience factory, e.g. Position2D.of(x, y).
        if let Some((elem_type, length)) = component_fixed_size_scalar(self, object) {
            let elem = self.java_type(&elem_type, false);
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
            writeln!(code, "    }}").unwrap();
        }
        writeln!(code, "}}").unwrap();
        Ok(code)
    }

    fn render_component_union(&self, code: &mut String, object: &'a Object) -> Result<()> {
        // Rare in Rerun's surface, but the layout is the same as any other union.
        let name = java_class_name(&object.name);
        self.render_union(code, object)?;
        let _ = name;
        Ok(())
    }

    // ------------------------------------------------------------ ergonomics

    /// The encoding struct a component record wraps, when the component is a plain newtype.
    fn wrapped_encoding(&self, object: &Object) -> Option<&'a Object> {
        if object.kind != re_types_builder::ObjectKind::Component
            || object.class != ObjectClass::Struct
        {
            return None;
        }
        let [field] = object.fields.as_slice() else {
            return None;
        };
        let Type::Object { fqname } = &field.typ else {
            return None;
        };
        let encoding = self.objects.get(fqname)?;
        if encoding.kind != re_types_builder::ObjectKind::Encoding
            || encoding.class != ObjectClass::Struct
        {
            return None;
        }
        Some(encoding)
    }

    /// The Java type behind an encoding that wraps a single primitive or string.
    fn simple_primitive(&self, typ: &Type) -> Option<String> {
        let Type::Object { fqname } = typ else {
            return None;
        };
        let object = self.objects.get(fqname)?;
        if object.kind != re_types_builder::ObjectKind::Encoding
            || object.class != ObjectClass::Struct
        {
            return None;
        }
        let [field] = object.fields.as_slice() else {
            return None;
        };
        let java_type = self.java_type(&field.typ, field.is_nullable);
        PRIMITIVE_JAVA_TYPES
            .contains(&java_type.as_str())
            .then_some(java_type)
    }

    /// Emits a constructor that takes the record's data without the encoding wrappers.
    ///
    /// The canonical record constructor remains available; this one only skips the single-value
    /// newtypes that otherwise make call sites read like `new Color(new Rgba32(0xFF00E5FFL))`.
    /// Returns whether a constructor was emitted.
    fn render_unwrapped_constructor(
        &self,
        code: &mut String,
        object: &'a Object,
        name: &str,
    ) -> bool {
        let encoding = self.wrapped_encoding(object);
        let source = match encoding {
            Some(encoding) => encoding.fields.as_slice(),
            None => object.fields.as_slice(),
        };

        let mut params = Vec::with_capacity(source.len());
        let mut arguments = Vec::with_capacity(source.len());
        for field in source {
            let field_name = Self::java_field_name(field);
            if let Some(primitive) = self.simple_primitive(&field.typ) {
                params.push(format!("{primitive} {field_name}"));
                arguments.push(format!(
                    "new {}({field_name})",
                    self.java_type(&field.typ, false)
                ));
            } else {
                params.push(format!(
                    "{} {field_name}",
                    self.java_type(&field.typ, field.is_nullable)
                ));
                arguments.push(field_name);
            }
        }

        let parameter_types = params
            .iter()
            .map(|param| {
                param
                    .rsplit_once(' ')
                    .map_or_else(|| param.clone(), |(typ, _)| typ.to_owned())
            })
            .collect::<Vec<_>>();
        let canonical_types = object
            .fields
            .iter()
            .map(|field| self.java_type(&field.typ, field.is_nullable))
            .collect::<Vec<_>>();
        if parameter_types == canonical_types {
            return false;
        }

        let (delegation, doc) = match encoding {
            Some(_) => (
                format!(
                    "new {}({})",
                    self.java_type(&object.fields[0].typ, false),
                    arguments.join(", ")
                ),
                "Creates a value from unwrapped fields, skipping the encoding record.",
            ),
            None => (
                arguments.join(", "),
                "Creates a value from unwrapped fields, without encoding records.",
            ),
        };

        writeln!(code).unwrap();
        writeln!(code, "    /** {doc} */").unwrap();
        writeln!(code, "    public {name}({}) {{", params.join(", ")).unwrap();
        writeln!(code, "        this({delegation});").unwrap();
        writeln!(code, "    }}").unwrap();
        writeln!(code).unwrap();
        true
    }

    /// The Java type a builder setter can take directly for a single-value component.
    fn component_primitive(&self, component: &Object) -> Option<String> {
        let encoding = self.wrapped_encoding(component)?;
        let [field] = encoding.fields.as_slice() else {
            return None;
        };
        let java_type = self.java_type(&field.typ, field.is_nullable);
        PRIMITIVE_JAVA_TYPES
            .contains(&java_type.as_str())
            .then_some(java_type)
    }

    /// The element type of a component that itself wraps a single list.
    fn component_list_element(&self, component: &Object) -> Option<String> {
        let [field] = component.fields.as_slice() else {
            return None;
        };
        match &field.typ {
            Type::List { elem_type } => Some(self.java_type_boxed(elem_type)),
            _ => None,
        }
    }

    /// Emits builder overloads that accept a component's data without wrappers.
    fn render_builder_overloads(
        &self,
        code: &mut String,
        method: &str,
        component: &Object,
        is_list: bool,
    ) {
        let component_class = java_class(&component.fqname);
        if let Some(primitive) = self.component_primitive(component) {
            writeln!(code).unwrap();
            writeln!(
                code,
                "        /** Overload that takes the {{@code {primitive}}} value(s) directly. */"
            )
            .unwrap();
            if is_list {
                writeln!(
                    code,
                    "        public Builder {method}({primitive}... value) {{"
                )
                .unwrap();
                writeln!(
                    code,
                    "            java.util.List<{component_class}> values = \
                     new java.util.ArrayList<>(value.length);"
                )
                .unwrap();
                writeln!(code, "            for ({primitive} v : value) {{").unwrap();
                writeln!(
                    code,
                    "                values.add(new {component_class}(v));"
                )
                .unwrap();
                writeln!(code, "            }}").unwrap();
                writeln!(code, "            return {method}(values);").unwrap();
                writeln!(code, "        }}").unwrap();
            } else {
                writeln!(
                    code,
                    "        public Builder {method}({primitive} value) {{"
                )
                .unwrap();
                writeln!(
                    code,
                    "            return {method}(new {component_class}(value));"
                )
                .unwrap();
                writeln!(code, "        }}").unwrap();
            }
        }
        if let Some(element) = self.component_list_element(component) {
            writeln!(code).unwrap();
            writeln!(
                code,
                "        /** Overload that takes the elements of a single component directly. */"
            )
            .unwrap();
            writeln!(
                code,
                "        public Builder {method}({element}... value) {{"
            )
            .unwrap();
            if is_list {
                writeln!(
                    code,
                    "            return {method}(java.util.List.of(\
                     new {component_class}(java.util.List.of(value))));"
                )
                .unwrap();
            } else {
                writeln!(
                    code,
                    "            return {method}(new {component_class}(java.util.List.of(value)));"
                )
                .unwrap();
            }
            writeln!(code, "        }}").unwrap();
        }
    }

    // ------------------------------------------------------------ archetypes

    fn render_archetype(&self, object: &'a Object) -> Result<String> {
        let package = java_package(&object.fqname);
        let name = java_class_name(&object.name);
        let mut code = String::new();
        write_header(&mut code, &package);
        write_docs(&mut code, &object.docs, "", self.objects);

        writeln!(code, "public record {name}(").unwrap();
        for (index, field) in object.fields.iter().enumerate() {
            let comma = if index + 1 == object.fields.len() {
                ""
            } else {
                ","
            };
            write_docs(&mut code, &field.docs, "    ", self.objects);
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

        // Column writer: metadata first, then the present components' columns.
        writeln!(code).unwrap();
        writeln!(code, "    @Override").unwrap();
        writeln!(
            code,
            "    public java.lang.String archetypeName() {{\n        return ARCHETYPE_NAME;\n    }}"
        )
        .unwrap();
        writeln!(code).unwrap();
        writeln!(code, "    @Override").unwrap();
        writeln!(
            code,
            "    public void writeColumns({CORE_PACKAGE}.NtEntityWriter w) {{"
        )
        .unwrap();
        writeln!(
            code,
            "        java.lang.StringBuilder components = new java.lang.StringBuilder();"
        )
        .unwrap();
        for field in &object.fields {
            let field_name = Self::java_field_name(field);
            let component_fqname = field.typ.fqname().unwrap_or_default();
            let component = &self.objects[component_fqname];
            let condition = present_condition(field);
            writeln!(code, "        if ({condition}) {{").unwrap();
            writeln!(
                code,
                "            if (components.length() > 0) components.append(',');"
            )
            .unwrap();
            writeln!(
                code,
                "            components.append(\"{}\").append('=').append({}.COMPONENT_TYPE);",
                field.name,
                java_class(&component.fqname)
            )
            .unwrap();
            writeln!(code, "        }}").unwrap();
            let _ = component;
        }
        writeln!(code, "        w.metaString(\"archetype\", ARCHETYPE_NAME);").unwrap();
        writeln!(
            code,
            "        w.metaString(\"components\", components.toString());"
        )
        .unwrap();
        for field in &object.fields {
            let field_name = Self::java_field_name(field);
            let component_class = java_class(field.typ.fqname().unwrap_or_default());
            let condition = present_condition(field);
            writeln!(code, "        if ({condition}) {{").unwrap();
            if matches!(field.typ, Type::List { .. }) {
                writeln!(
                    code,
                    "            {component_class}.writeColumnsStatic(w, \"{}\", {field_name});",
                    field.name
                )
                .unwrap();
            } else {
                writeln!(
                    code,
                    "            {component_class}.writeColumnsStatic(w, \"{}\", java.util.List.of({field_name}));",
                    field.name
                )
                .unwrap();
            }
            writeln!(code, "        }}").unwrap();
        }
        writeln!(code, "    }}").unwrap();

        // Builder.
        writeln!(code).unwrap();
        writeln!(
            code,
            "    public static Builder builder() {{ return new Builder(); }}"
        )
        .unwrap();
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
            let component = &self.objects[component_fqname];
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
                self.render_builder_overloads(&mut code, &method, component, true);
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
                self.render_builder_overloads(&mut code, &method, component, false);
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
        writeln!(code, "        return new {name}({});", args.join(", ")).unwrap();
        writeln!(code, "    }}").unwrap();

        writeln!(code, "}}").unwrap();
        Ok(code)
    }
}

/// The Java condition under which an archetype field has data to write.
fn present_condition(field: &re_types_builder::ObjectField) -> String {
    let field_name = JavaGenerator::java_field_name(field);
    if matches!(field.typ, Type::List { .. }) {
        if field.has_attr(RerunAttr::Required) {
            format!("!{field_name}.isEmpty()")
        } else {
            format!("{field_name} != null && !{field_name}.isEmpty()")
        }
    } else if field.has_attr(RerunAttr::Required) {
        "true".to_owned()
    } else {
        format!("{field_name} != null")
    }
}

// ------------------------------------------------------------------ emitter

/// Emits the Java statements that write one datatype node's NetworkTables columns.
///
/// The layout follows the protocol:
///
/// * `List` writes a per-instance count column (`P/.count`, `long[]`) and puts the flattened
///   elements under `P/.items`, so lists of different lengths in one batch decode unambiguously.
/// * `Union` writes a per-instance field ordinal (`P/.variant`, `long[]`, `-1` for null) and each
///   variant's payload under `P/<variant field name>`.
///
/// Every node keeps its instance count implicit: a node's arrays all have one entry per instance,
/// which is what lets the decoder rebuild the Arrow array without a side-channel length.
///
/// Arrow-transparent encodings (single fixed-size-list wrappers such as `Vec3D`) do not add a path
/// segment: their layout *is* the inner datatype's layout, exactly like their Arrow datatype.
struct ColumnsEmitter<'g, 'a> {
    generator: &'g JavaGenerator<'a>,
    code: String,
    next_id: usize,
}

/// Accessors from a list element to a node value, with the nullability needed to guard each step.
#[derive(Clone)]
struct Chain {
    /// A Java expression producing one instance value, e.g. `values.get(i)`.
    root: String,
    steps: Vec<Step>,
}

#[derive(Clone)]
struct Step {
    /// Applied to the parent value: `.xyz()` or `[0]`.
    accessor: String,
    /// Whether the parent value may be null and therefore needs a guard.
    parent_may_be_null: bool,
}

impl Chain {
    fn root(root: impl Into<String>) -> Self {
        Self {
            root: root.into(),
            steps: Vec::new(),
        }
    }

    fn with(&self, accessor: impl Into<String>, parent_may_be_null: bool) -> Self {
        let mut steps = self.steps.clone();
        steps.push(Step {
            accessor: accessor.into(),
            parent_may_be_null,
        });
        Self {
            root: self.root.clone(),
            steps,
        }
    }
}

/// The loop variable, instance count, and static path suffix of a node.
struct NodeCtx {
    index: String,
    count: String,
    /// Static suffix below the component root, e.g. `/xyz/0`. Empty at the component root.
    suffix: String,
}

impl NodeCtx {
    fn path(&self) -> String {
        if self.suffix.is_empty() {
            "path".to_owned()
        } else {
            format!("path + \"{}\"", escape_java_string(&self.suffix))
        }
    }

    fn child(&self, extra: &str) -> Self {
        Self {
            index: self.index.clone(),
            count: self.count.clone(),
            suffix: format!("{}{}", self.suffix, extra),
        }
    }
}

impl<'g, 'a> ColumnsEmitter<'g, 'a> {
    fn new(generator: &'g JavaGenerator<'a>) -> Self {
        Self {
            generator,
            code: String::new(),
            next_id: 0,
        }
    }

    fn id(&mut self) -> usize {
        let id = self.next_id;
        self.next_id += 1;
        id
    }

    /// Emits the accessor locals for `chain` and returns the expression holding the node value.
    fn chain_expr(&mut self, chain: &Chain, id: usize, indent: &str) -> String {
        let mut current = chain.root.clone();
        for (depth, step) in chain.steps.iter().enumerate() {
            let local = format!("v{id}_{depth}");
            if step.parent_may_be_null {
                writeln!(
                    self.code,
                    "{indent}var {local} = {current} == null ? null : {current}{};",
                    step.accessor
                )
                .unwrap();
            } else {
                writeln!(
                    self.code,
                    "{indent}var {local} = {current}{};",
                    step.accessor
                )
                .unwrap();
            }
            current = local;
        }
        current
    }

    /// Emits one node.
    ///
    /// * `may_be_null`: the node's value expression may be null.
    /// * `node_nullable`: this node publishes a `.present` mask.
    fn emit(
        &mut self,
        typ: &Type,
        chain: &Chain,
        may_be_null: bool,
        node_nullable: bool,
        ctx: &NodeCtx,
    ) -> Result<()> {
        let indent = "        ".to_owned();
        if node_nullable {
            let id = self.id();
            writeln!(
                self.code,
                "{indent}boolean[] present{id} = new boolean[{}];",
                ctx.count
            )
            .unwrap();
            writeln!(self.code, "{indent}boolean anyNull{id} = false;").unwrap();
            writeln!(
                self.code,
                "{indent}for (int {i} = 0; {i} < {n}; {i}++) {{",
                i = ctx.index,
                n = ctx.count
            )
            .unwrap();
            let inner = format!("{indent}    ");
            let value = self.chain_expr(chain, id, &inner);
            writeln!(self.code, "{inner}boolean ok{id} = ({value}) != null;").unwrap();
            writeln!(
                self.code,
                "{inner}present{id}[{i}] = ok{id};",
                i = ctx.index
            )
            .unwrap();
            writeln!(self.code, "{inner}anyNull{id} |= !ok{id};").unwrap();
            writeln!(self.code, "{indent}}}").unwrap();
            writeln!(
                self.code,
                "{indent}w.present({}, present{id}, anyNull{id});",
                ctx.path()
            )
            .unwrap();
        }

        match typ {
            Type::Atomic(atomic) => self.emit_atomic(atomic, chain, may_be_null, ctx)?,
            Type::Utf8 => self.emit_utf8(chain, may_be_null, ctx),
            Type::Binary => self.emit_binary(chain, may_be_null, ctx),
            Type::FixedSizeList { elem_type, length } => {
                for offset in 0..*length {
                    let child = chain.with(format!("[{offset}]"), may_be_null);
                    self.emit(
                        elem_type,
                        &child,
                        may_be_null,
                        false,
                        &ctx.child(&format!("/{offset}")),
                    )?;
                }
            }
            Type::List { elem_type } => {
                self.emit_list(elem_type, chain, ctx)?;
            }
            Type::Object { fqname } => {
                let object = &self.generator.objects[fqname.as_str()];
                match &object.class {
                    ObjectClass::Enum(_) => self.emit_enum_leaf(object, chain, ctx),
                    ObjectClass::Struct => {
                        let transparent = object.is_arrow_transparent();
                        for field in &object.fields {
                            let accessor = format!(".{}()", JavaGenerator::java_field_name(field));
                            let child = chain.with(accessor, may_be_null);
                            let child_nullable = field.is_nullable;
                            if transparent {
                                self.emit(
                                    &field.typ,
                                    &child,
                                    may_be_null || child_nullable,
                                    child_nullable,
                                    ctx,
                                )?;
                            } else {
                                self.emit(
                                    &field.typ,
                                    &child,
                                    may_be_null || child_nullable,
                                    child_nullable,
                                    &ctx.child(&format!("/{}", field.name)),
                                )?;
                            }
                        }
                    }
                    ObjectClass::Union => {
                        self.emit_union(object, chain, may_be_null, ctx)?;
                    }
                }
            }
        }
        Ok(())
    }

    fn emit_atomic(
        &mut self,
        atomic: &AtomicDataType,
        chain: &Chain,
        may_be_null: bool,
        ctx: &NodeCtx,
    ) -> Result<()> {
        if matches!(atomic, AtomicDataType::Null) {
            anyhow::bail!("a bare `null` value has no NetworkTables representation");
        }
        let id = self.id();
        let (array_type, writer) = match atomic {
            AtomicDataType::Boolean => ("boolean", "booleans"),
            AtomicDataType::Float16 | AtomicDataType::Float32 | AtomicDataType::Float64 => {
                ("double", "doubles")
            }
            _ => ("long", "longs"),
        };
        let is_float = matches!(
            atomic,
            AtomicDataType::Float16 | AtomicDataType::Float32 | AtomicDataType::Float64
        );

        writeln!(
            self.code,
            "        {array_type}[] column{id} = new {array_type}[{}];",
            ctx.count
        )
        .unwrap();
        let mut unsigned = false;
        if matches!(atomic, AtomicDataType::UInt64) {
            writeln!(self.code, "        boolean unsigned{id} = false;").unwrap();
            unsigned = true;
        }
        let zero = match array_type {
            "boolean" => "false",
            "double" => "0.0",
            _ => "0L",
        };
        writeln!(
            self.code,
            "        for (int {i} = 0; {i} < {n}; {i}++) {{",
            i = ctx.index,
            n = ctx.count
        )
        .unwrap();
        let inner = "            ".to_owned();
        let value = self.chain_expr(chain, id, &inner);
        if may_be_null {
            writeln!(self.code, "{inner}var value{id} = {value};").unwrap();
            writeln!(
                self.code,
                "{inner}column{id}[{i}] = value{id} == null ? {zero} : value{id};",
                i = ctx.index
            )
            .unwrap();
        } else {
            writeln!(self.code, "{inner}{array_type} value{id} = {value};").unwrap();
            writeln!(
                self.code,
                "{inner}column{id}[{i}] = value{id};",
                i = ctx.index
            )
            .unwrap();
        }
        if unsigned {
            writeln!(
                self.code,
                "{inner}unsigned{id} |= column{id}[{i}] < 0L;",
                i = ctx.index
            )
            .unwrap();
        }
        writeln!(self.code, "        }}").unwrap();

        if unsigned {
            // NT topics have a fixed type, so a column can never switch from `long[]` to
            // `string[]`. Keep the native array authoritative and publish overflowing values as
            // decimal strings under `P/.u64`; once that sidecar exists it is republished with
            // every batch so a batch of small values cannot shadow the unsigned ones.
            writeln!(
                self.code,
                "        w.u64({}, column{id}, unsigned{id});",
                ctx.path()
            )
            .unwrap();
        } else {
            writeln!(self.code, "        w.{writer}({}, column{id});", ctx.path()).unwrap();
        }
        let _ = is_float;
        Ok(())
    }

    fn emit_utf8(&mut self, chain: &Chain, may_be_null: bool, ctx: &NodeCtx) {
        let id = self.id();
        writeln!(
            self.code,
            "        java.lang.String[] column{id} = new java.lang.String[{}];",
            ctx.count
        )
        .unwrap();
        writeln!(
            self.code,
            "        for (int {i} = 0; {i} < {n}; {i}++) {{",
            i = ctx.index,
            n = ctx.count
        )
        .unwrap();
        let inner = "            ".to_owned();
        let value = self.chain_expr(chain, id, &inner);
        writeln!(self.code, "{inner}java.lang.String value{id} = {value};").unwrap();
        writeln!(
            self.code,
            "{inner}column{id}[{i}] = value{id} == null ? \"\" : value{id};",
            i = ctx.index
        )
        .unwrap();
        writeln!(self.code, "        }}").unwrap();
        writeln!(self.code, "        w.strings({}, column{id});", ctx.path()).unwrap();
        let _ = may_be_null;
    }

    fn emit_binary(&mut self, chain: &Chain, may_be_null: bool, ctx: &NodeCtx) {
        let id = self.id();
        writeln!(
            self.code,
            "        int total{id} = 0;\n        byte[][] parts{id} = new byte[{}][];\n        long[] lengths{id} = new long[{}];",
            ctx.count, ctx.count
        )
        .unwrap();
        writeln!(
            self.code,
            "        for (int {i} = 0; {i} < {n}; {i}++) {{",
            i = ctx.index,
            n = ctx.count
        )
        .unwrap();
        let inner = "            ".to_owned();
        let value = self.chain_expr(chain, id, &inner);
        writeln!(
            self.code,
            "{inner}byte[] value{id} = {value};\n{inner}if (value{id} == null) value{id} = new byte[0];\n{inner}parts{id}[{i}] = value{id};\n{inner}lengths{id}[{i}] = value{id}.length;\n{inner}total{id} += value{id}.length;",
            i = ctx.index
        )
        .unwrap();
        writeln!(self.code, "        }}").unwrap();
        writeln!(
            self.code,
            "        byte[] bytes{id} = new byte[total{id}];\n        int offset{id} = 0;\n        for (int k{id} = 0; k{id} < {n}; k{id}++) {{\n            java.lang.System.arraycopy(parts{id}[k{id}], 0, bytes{id}, offset{id}, parts{id}[k{id}].length);\n            offset{id} += parts{id}[k{id}].length;\n        }}",
            n = ctx.count
        )
        .unwrap();
        writeln!(self.code, "        w.raw({}, bytes{id});", ctx.path()).unwrap();
        writeln!(
            self.code,
            "        w.longs({} + \"/.lengths\", lengths{id});",
            ctx.path()
        )
        .unwrap();
        let _ = may_be_null;
    }

    fn emit_enum_leaf(&mut self, object: &Object, chain: &Chain, ctx: &NodeCtx) {
        let id = self.id();
        let class = java_class(&object.fqname);
        writeln!(
            self.code,
            "        long[] column{id} = new long[{}];",
            ctx.count
        )
        .unwrap();
        writeln!(
            self.code,
            "        for (int {i} = 0; {i} < {n}; {i}++) {{",
            i = ctx.index,
            n = ctx.count
        )
        .unwrap();
        let inner = "            ".to_owned();
        let value = self.chain_expr(chain, id, &inner);
        writeln!(self.code, "{inner}{class} value{id} = {value};").unwrap();
        writeln!(
            self.code,
            "{inner}column{id}[{i}] = value{id} == null ? 0L : value{id}.value();",
            i = ctx.index
        )
        .unwrap();
        writeln!(self.code, "        }}").unwrap();
        writeln!(self.code, "        w.longs({}, column{id});", ctx.path()).unwrap();
    }

    fn emit_list(&mut self, elem_type: &Type, chain: &Chain, ctx: &NodeCtx) -> Result<()> {
        let id = self.id();
        let elem = self.generator.java_type_boxed(elem_type);
        writeln!(
            self.code,
            "        long[] counts{id} = new long[{}];",
            ctx.count
        )
        .unwrap();
        writeln!(self.code, "        int total{id} = 0;").unwrap();
        writeln!(
            self.code,
            "        for (int {i} = 0; {i} < {n}; {i}++) {{",
            i = ctx.index,
            n = ctx.count
        )
        .unwrap();
        let inner = "            ".to_owned();
        let value = self.chain_expr(chain, id, &inner);
        writeln!(
            self.code,
            "{inner}java.util.List<{elem}> list{id} = {value};"
        )
        .unwrap();
        writeln!(
            self.code,
            "{inner}counts{id}[{i}] = list{id} == null ? 0L : list{id}.size();",
            i = ctx.index
        )
        .unwrap();
        writeln!(
            self.code,
            "{inner}total{id} += (int) counts{id}[{i}];",
            i = ctx.index
        )
        .unwrap();
        writeln!(self.code, "        }}").unwrap();
        writeln!(
            self.code,
            "        w.longs({} + \"/.count\", counts{id});",
            ctx.path()
        )
        .unwrap();
        writeln!(
            self.code,
            "        java.util.ArrayList<{elem}> items{id} = new java.util.ArrayList<>(total{id});"
        )
        .unwrap();
        writeln!(
            self.code,
            "        for (int {i} = 0; {i} < {n}; {i}++) {{",
            i = ctx.index,
            n = ctx.count
        )
        .unwrap();
        let value = self.chain_expr(chain, id, &inner);
        writeln!(
            self.code,
            "{inner}java.util.List<{elem}> list{id} = {value};"
        )
        .unwrap();
        writeln!(
            self.code,
            "{inner}if (list{id} != null) items{id}.addAll(list{id});"
        )
        .unwrap();
        writeln!(self.code, "        }}").unwrap();
        writeln!(self.code, "        int itemCount{id} = items{id}.size();").unwrap();
        let child_chain = Chain::root(format!("items{id}.get(j{id})"));
        let child_ctx = NodeCtx {
            index: format!("j{id}"),
            count: format!("itemCount{id}"),
            suffix: format!("{}/.items", ctx.suffix),
        };
        self.emit(elem_type, &child_chain, false, false, &child_ctx)
    }

    fn emit_union(
        &mut self,
        object: &Object,
        chain: &Chain,
        may_be_null: bool,
        ctx: &NodeCtx,
    ) -> Result<()> {
        let id = self.id();
        let union_class = java_class(&object.fqname);
        writeln!(
            self.code,
            "        long[] variants{id} = new long[{}];",
            ctx.count
        )
        .unwrap();
        let mut payloads = Vec::new();
        for (ordinal, field) in object.fields.iter().enumerate() {
            if field.typ.is_unit() {
                continue;
            }
            let payload = format!("payload{id}_{ordinal}");
            let java_type = self.generator.java_type(&field.typ, false);
            writeln!(
                self.code,
                "        java.util.ArrayList<{java_type}> {payload} = new java.util.ArrayList<>();"
            )
            .unwrap();
            payloads.push((ordinal, field, payload));
        }
        writeln!(
            self.code,
            "        for (int {i} = 0; {i} < {n}; {i}++) {{",
            i = ctx.index,
            n = ctx.count
        )
        .unwrap();
        let inner = "            ".to_owned();
        let value = self.chain_expr(chain, id, &inner);
        writeln!(self.code, "{inner}{union_class} value{id} = {value};").unwrap();
        writeln!(self.code, "{inner}if (value{id} == null) {{").unwrap();
        writeln!(
            self.code,
            "{inner}    variants{id}[{i}] = -1L;",
            i = ctx.index
        )
        .unwrap();
        for (ordinal, field) in object.fields.iter().enumerate() {
            let variant = union_variant_name(field);
            if field.typ.is_unit() {
                writeln!(
                    self.code,
                    "{inner}}} else if (value{id} instanceof {union_class}.{variant}) {{"
                )
                .unwrap();
            } else {
                writeln!(
                    self.code,
                    "{inner}}} else if (value{id} instanceof {union_class}.{variant} variant{id}_{ordinal}) {{"
                )
                .unwrap();
            }
            writeln!(
                self.code,
                "{inner}    variants{id}[{i}] = {ordinal}L;",
                i = ctx.index
            )
            .unwrap();
            if !field.typ.is_unit() {
                writeln!(
                    self.code,
                    "{inner}    payload{id}_{ordinal}.add(variant{id}_{ordinal}.value());"
                )
                .unwrap();
            }
        }
        writeln!(self.code, "{inner}}} else {{").unwrap();
        writeln!(
            self.code,
            "{inner}    variants{id}[{i}] = -1L;",
            i = ctx.index
        )
        .unwrap();
        writeln!(self.code, "{inner}}}").unwrap();
        writeln!(self.code, "        }}").unwrap();
        writeln!(
            self.code,
            "        w.longs({} + \"/.variant\", variants{id});",
            ctx.path()
        )
        .unwrap();
        let _ = may_be_null;
        for (ordinal, field, payload) in payloads {
            writeln!(self.code, "        if (!{payload}.isEmpty()) {{").unwrap();
            writeln!(
                self.code,
                "            int payloadCount{id}_{ordinal} = {payload}.size();"
            )
            .unwrap();
            let child_chain = Chain::root(format!("{payload}.get(k{id}_{ordinal})"));
            let child_ctx = NodeCtx {
                index: format!("k{id}_{ordinal}"),
                count: format!("payloadCount{id}_{ordinal}"),
                suffix: format!("{}/{}", ctx.suffix, field.name),
            };
            self.emit(&field.typ, &child_chain, false, false, &child_ctx)?;
            writeln!(self.code, "        }}").unwrap();
        }
        Ok(())
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

fn component_fixed_size_scalar(
    generator: &JavaGenerator<'_>,
    object: &Object,
) -> Option<(Type, usize)> {
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

/// Declares the per-instance local used by a leaf column loop.
fn emit_value_local(
    code: &mut String,
    id: usize,
    indent: &str,
    expr: &str,
    primitive: &str,
    can_be_null: bool,
) {
    if can_be_null {
        writeln!(
            code,
            "{indent}    {} value{id} = {expr};",
            boxed_primitive(primitive)
        )
        .unwrap();
    } else {
        writeln!(code, "{indent}    {primitive} value{id} = {expr};").unwrap();
    }
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
    let result = java_identifier(name);
    if result == "null" {
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

fn escape_java_string(text: &str) -> String {
    text.replace('\\', "\\\\").replace('"', "\\\"")
}

fn write_header(code: &mut String, package: &str) {
    writeln!(
        code,
        "// DO NOT EDIT: generated from Rerun type definitions."
    )
    .unwrap();
    writeln!(code, "package {package};\n").unwrap();
}

fn write_docs(code: &mut String, docs: &Docs, indent: &str, objects: &Objects) {
    let lines = docs.only_lines_tagged("");
    if lines.is_empty() {
        return;
    }

    writeln!(code, "{indent}/**").unwrap();
    for line in lines {
        writeln!(code, "{indent} * {}", java_doc_line(line, objects)).unwrap();
    }
    writeln!(code, "{indent} */").unwrap();
}

fn java_doc_line(line: &str, objects: &Objects) -> String {
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
            output.push_str(&escape_javadoc_text(&rest[..link_start]));
            let after_start = &rest[link_start + 2..];
            if let Some(link_end) = after_start.find("`]") {
                let label = &after_start[..link_end];
                let after_label = &after_start[link_end + 2..];
                let (target, mut remainder) = if let Some(target) = after_label.strip_prefix('[') {
                    if let Some(target_end) = target.find(']') {
                        (&target[1..target_end], &target[target_end + 1..])
                    } else {
                        (label, after_label)
                    }
                } else {
                    (label, after_label)
                };

                // A markdown link whose label is itself a Rust doclink, e.g.
                // [`DepthImage`](https://...): keep the Javadoc link and drop the outer URL.
                let trailing = remainder
                    .strip_prefix("](")
                    .or_else(|| remainder.strip_prefix('('));
                if let Some(trailing) = trailing {
                    if let Some(url_end) = trailing.find(')') {
                        remainder = &trailing[url_end + 1..];
                    }
                }
                match java_doc_link(target, objects) {
                    Some(target) => {
                        output.push_str("{@link ");
                        output.push_str(&target);
                        if target != label {
                            output.push(' ');
                            output.push_str(&escape_javadoc_text(label));
                        }
                        output.push('}');
                    }
                    // The target lives in a skipped namespace (blueprints and views), so there
                    // is no Java class to link to; keep the name readable as code.
                    None => {
                        output.push_str("{@code ");
                        output.push_str(&escape_javadoc_code(label));
                        output.push('}');
                    }
                }
                rest = remainder;
                continue;
            }
        }

        if markdown_link_start
            .is_some_and(|link_start| code_start.is_none_or(|code_start| link_start < code_start))
        {
            let link_start = markdown_link_start.unwrap();
            output.push_str(&escape_javadoc_text(&rest[..link_start]));
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

        output.push_str(&escape_javadoc_text(rest));
        break;
    }

    output.replace("*/", "*\\/")
}

fn java_doc_link(path: &str, objects: &Objects) -> Option<String> {
    let path = if let Some(path) = path.strip_prefix("crate::") {
        format!("{JAVA_PACKAGE_PREFIX}.{}", path.replace("::", "."))
    } else if let Some(path) = path.strip_prefix("rerun::") {
        format!("{JAVA_PACKAGE_PREFIX}.{}", path.replace("::", "."))
    } else {
        path.to_owned()
    };
    let path = path
        .replace("::", ".")
        .replace(".datatypes.", ".encodings.");
    let path = if !path.starts_with(JAVA_PACKAGE_PREFIX)
        && matches!(
            path.split('.').next().unwrap_or_default(),
            "archetypes" | "components" | "encodings" | "views" | "blueprint" | "testing"
        ) {
        format!("{JAVA_PACKAGE_PREFIX}.{path}")
    } else {
        path
    };
    let mut parts = path.rsplitn(2, '.');
    let last = parts.next().unwrap_or_default();
    let Some(prefix) = parts.next() else {
        // A bare type name can only be resolved if the type is generated. Rerun's docs reference
        // blueprint/view types by short name; those packages are skipped.
        if is_skipped_type_name(&last, objects) {
            return None;
        }
        return Some(path);
    };

    if is_skipped_type_name(&last, objects) {
        return None;
    }

    // A Rust doclink can point at an enum variant (`FillMode::Gradient`). In Java those variants
    // are constants of the enum, so they need `#`, while a real nested type still needs `.`.
    let fqname = prefix
        .strip_prefix(JAVA_PACKAGE_PREFIX)
        .map(|prefix| format!("rerun{prefix}"));
    let is_enum = fqname
        .as_deref()
        .and_then(|fqname| objects.get(fqname))
        .is_some_and(|object| matches!(object.class, ObjectClass::Enum(_)));

    if is_enum
        || last
            .chars()
            .next()
            .is_some_and(|character| character.is_ascii_lowercase())
    {
        Some(format!("{prefix}#{last}"))
    } else {
        Some(format!("{prefix}.{last}"))
    }
}

/// Whether `name` is a type from a namespace this generator skips.
fn is_skipped_type_name(name: &str, objects: &Objects) -> bool {
    objects
        .values()
        .any(|object| object.name == name && is_skipped(&object.fqname))
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
    use re_types_builder::{Docs, Objects};

    use super::{
        is_skipped, java_doc_line, java_identifier, java_package, snake_to_camel, write_docs,
    };

    #[test]
    fn maps_rerun_names_to_java_packages() {
        assert_eq!(
            java_package("rerun.components.Position2D"),
            "org.teamvan.moneyshift.components"
        );
        assert_eq!(
            java_package("rerun.archetypes.Points3D"),
            "org.teamvan.moneyshift.archetypes"
        );
    }

    #[test]
    fn skips_testing_definitions_and_blueprints() {
        assert!(is_skipped("rerun.testing.components.AffixFuzzer1"));
        assert!(is_skipped("rerun.testing.encodings.ManyVec3"));
        assert!(is_skipped("rerun.blueprint.archetypes.ContainerBlueprint"));
        assert!(!is_skipped("rerun.components.Position2D"));
        assert!(!is_skipped("rerun.archetypes.Points3D"));
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
        write_docs(&mut code, &docs, "    ", &Objects::default());
        assert_eq!(
            code,
            "    /**\n     * A description.\n     * \n     * A closing {@code *\\/}.\n     */\n"
        );
    }

    #[test]
    fn converts_rustdoc_links_and_code() {
        assert_eq!(
            java_doc_line(
                "See [`rerun::components::Position2D`] and [`crate::foo::draw`].",
                &Objects::default(),
            ),
            "See {@link org.teamvan.moneyshift.components.Position2D} and {@link org.teamvan.moneyshift.foo#draw}."
        );
        assert_eq!(
            java_doc_line(
                "Use `Vec<T>` or [the guide](https://example.com?a=1&b=2).",
                &Objects::default(),
            ),
            "Use {@code Vec&lt;T&gt;} or <a href=\"https://example.com?a=1&amp;b=2\">the guide</a>."
        );
    }
}
