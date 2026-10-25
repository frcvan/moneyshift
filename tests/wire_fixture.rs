//! Decodes the NetworkTables columns published by the Java SDK and checked in as
//! `moneyshift/lib/src/test/resources/rerun_wire_fixture.json`.
//!
//! The Java `WireFixtureTest` produces that file from a real NetworkTables instance, so this test
//! verifies the writer and the decoder against each other rather than against a second copy of
//! the layout. Regenerate the fixture with:
//!
//! ```sh
//! cd moneyshift && ./gradlew :lib:test --tests '*WireFixtureTest' -Dfirstrun.writeFixture=true
//! ```

use std::collections::HashMap;
use std::path::Path;

use arrow::array::{Array, AsArray};
use arrow::datatypes::{Float32Type, UInt16Type, UInt32Type, UInt64Type};
use firstrun::nt::protocol::{
    EntityMeta, NtValue, decode_planned, entity_path_from_topic, plan_entity,
};

#[derive(Debug, serde::Deserialize)]
struct Fixture {
    version: u32,
    entities: Vec<EntityFixture>,
}

#[derive(Debug, serde::Deserialize)]
struct EntityFixture {
    /// The NetworkTables topic prefix of the entity, e.g. `/Rerun/world/points`.
    entity: String,
    archetype: Option<String>,
    components: String,
    #[serde(rename = "static")]
    static_: bool,
    columns: HashMap<String, ColumnFixture>,
}

#[derive(Debug, serde::Deserialize)]
struct ColumnFixture {
    #[serde(rename = "type")]
    ty: String,
    value: serde_json::Value,
}

fn fixture() -> Fixture {
    let path = Path::new(env!("CARGO_MANIFEST_DIR"))
        .join("moneyshift/lib/src/test/resources/rerun_wire_fixture.json");
    let text = std::fs::read_to_string(&path).unwrap_or_else(|error| {
        panic!(
            "could not read {}: {error}; regenerate it from moneyshift with \
             `./gradlew :lib:test --tests '*WireFixtureTest' -Dfirstrun.writeFixture=true`",
            path.display()
        )
    });
    serde_json::from_str(&text).expect("the wire fixture must be valid JSON")
}

fn column(fixture: &ColumnFixture) -> NtValue {
    let values = fixture
        .value
        .as_array()
        .unwrap_or_else(|| panic!("{} should hold an array", fixture.ty));
    match fixture.ty.as_str() {
        "boolean[]" => NtValue::Booleans(
            values
                .iter()
                .map(|value| value.as_bool().expect("boolean"))
                .collect(),
        ),
        "double[]" | "float[]" => NtValue::Doubles(
            values
                .iter()
                .map(|value| value.as_f64().expect("double"))
                .collect(),
        ),
        "int[]" => NtValue::Longs(
            values
                .iter()
                .map(|value| value.as_i64().expect("integer"))
                .collect(),
        ),
        "string[]" => NtValue::Strings(
            values
                .iter()
                .map(|value| value.as_str().expect("string").to_owned())
                .collect(),
        ),
        other => panic!("unexpected NetworkTables topic type {other}"),
    }
}

/// Decodes one fixture entity, returning its batches and asserting that every column had the
/// instance count the protocol promised.
fn decode(entity: &EntityFixture) -> Vec<re_types_core::SerializedComponentBatch> {
    let path = entity_path_from_topic(&entity.entity)
        .unwrap_or_else(|| panic!("{} is not a decodable entity topic", entity.entity));
    assert_eq!(
        path.iter().count(),
        2,
        "unexpected entity path for {}",
        entity.entity
    );

    let meta = EntityMeta {
        archetype: entity.archetype.clone(),
        components: EntityMeta::parse_components(&entity.components),
        static_: entity.static_,
    };
    assert!(
        !meta.components.is_empty(),
        "{} has no components",
        entity.entity
    );

    let columns = entity
        .columns
        .iter()
        .map(|(name, value)| (name.clone(), column(value)))
        .collect::<HashMap<_, _>>();

    let plan = plan_entity(&meta).expect("the entity should plan");
    assert_eq!(plan.len(), meta.components.len());
    let (batches, warnings) = decode_planned(&plan, &columns).expect("the entity should decode");
    assert!(
        warnings.is_empty(),
        "column layout drifted from the protocol: {warnings:?}"
    );
    assert_eq!(batches.len(), meta.components.len());
    batches
}

fn component<'a>(
    batches: &'a [re_types_core::SerializedComponentBatch],
    field: &str,
) -> &'a arrow::array::ArrayRef {
    &batches
        .iter()
        .find(|batch| {
            batch
                .descriptor
                .component
                .as_str()
                .ends_with(&format!(":{field}"))
        })
        .unwrap_or_else(|| panic!("missing component {field}"))
        .array
}

#[test]
fn fixture_has_the_expected_entities() {
    let fixture = fixture();
    assert_eq!(fixture.version, 1);
    let names = fixture
        .entities
        .iter()
        .map(|entity| entity.entity.as_str())
        .collect::<Vec<_>>();
    assert_eq!(
        names,
        [
            "/Rerun/world/points",
            "/Rerun/world/annotations",
            "/Rerun/world/tensor"
        ]
    );
}

#[test]
fn decodes_primitive_and_fixed_size_list_columns() {
    let fixture = fixture();
    let entity = &fixture.entities[0];
    let batches = decode(entity);

    let positions = component(&batches, "positions").as_fixed_size_list();
    assert_eq!(positions.len(), 2);
    assert_eq!(positions.value_length(), 3);

    // `Position3D` is a `FixedSizeList<f32, 3>`; the wire format publishes doubles.
    let expected = [[1.0f32, 2.0, 3.0], [-4.0, 5.5, 6.0]];
    for (index, expected) in expected.iter().enumerate() {
        let values = positions.value(index);
        let values = values.as_primitive::<Float32Type>();
        assert_eq!(values.values(), expected);
    }

    let radii = component(&batches, "radii").as_primitive::<Float32Type>();
    assert_eq!(radii.values(), &[0.25, 0.5]);

    let colors = component(&batches, "colors").as_primitive::<UInt32Type>();
    assert_eq!(colors.values(), &[0xFFFF_0000, 0xFF00_FF00]);

    let labels = component(&batches, "labels").as_string::<i32>();
    assert_eq!(
        (0..labels.len())
            .map(|index| labels.value(index))
            .collect::<Vec<_>>(),
        ["alpha", "beta"]
    );

    let class_ids = component(&batches, "class_ids").as_primitive::<UInt16Type>();
    assert_eq!(class_ids.values(), &[1, 2]);
    let keypoint_ids = component(&batches, "keypoint_ids").as_primitive::<UInt16Type>();
    assert_eq!(keypoint_ids.values(), &[7, 8]);
}

#[test]
fn decodes_nested_structs_lists_and_null_masks() {
    let fixture = fixture();
    let entity = &fixture.entities[1];
    let batches = decode(entity);

    let context = component(&batches, "context").as_list::<i32>();
    assert_eq!(context.len(), 1);
    assert_eq!(context.value_length(0), 2);

    let class_descriptions = context.value(0);
    let class_descriptions = class_descriptions.as_struct();
    assert_eq!(class_descriptions.len(), 2);

    let class_id = class_descriptions.column_by_name("class_id").unwrap();
    assert_eq!(
        class_id.as_primitive::<UInt16Type>().values(),
        &[1, 2],
        "class ids must stay aligned with the class descriptions"
    );

    let description = class_descriptions
        .column_by_name("class_description")
        .unwrap()
        .as_struct();
    let info = description.column_by_name("info").unwrap().as_struct();

    let ids = info
        .column_by_name("id")
        .unwrap()
        .as_primitive::<UInt16Type>();
    assert_eq!(ids.values(), &[1, 2]);

    let labels = info.column_by_name("label").unwrap().as_string::<i32>();
    assert_eq!(labels.value(0), "person");
    assert!(
        labels.is_null(1),
        "a null nested label must decode as a null, not an empty string"
    );

    let colors = info
        .column_by_name("color")
        .unwrap()
        .as_primitive::<UInt32Type>();
    assert_eq!(colors.value(0), 0xFF11_2233);
    assert!(colors.is_null(1));

    let annotations = description
        .column_by_name("keypoint_annotations")
        .unwrap()
        .as_list::<i32>();
    assert_eq!(annotations.value_length(0), 0);
    assert_eq!(annotations.value_length(1), 1);
    let annotation = annotations.value(1);
    let annotation = annotation.as_struct();
    assert!(annotation.column_by_name("label").unwrap().is_null(0));
}

#[test]
fn decodes_union_payloads_and_the_unsigned_sidecar() {
    let fixture = fixture();
    let entity = &fixture.entities[2];
    let batches = decode(entity);

    let data = component(&batches, "data").as_struct();

    let shape = data.column_by_name("shape").unwrap().as_list::<i32>();
    assert_eq!(shape.value_length(0), 2);
    assert_eq!(
        shape.value(0).as_primitive::<UInt64Type>().values(),
        &[2, 2]
    );

    let names = data.column_by_name("names").unwrap().as_list::<i32>();
    let names = names.value(0);
    let names = names.as_string::<i32>();
    assert_eq!(
        (0..names.len())
            .map(|index| names.value(index))
            .collect::<Vec<_>>(),
        ["height", "width"]
    );

    let buffer = data.column_by_name("buffer").unwrap().as_union();
    assert_eq!(buffer.len(), 1);
    // `TensorBuffer` is `_null_markers`, then U8, U16, U32, U64, …
    assert_eq!(buffer.type_id(0), 4, "the payload is the U64 variant");
    let values = buffer.child(4).as_list::<i32>();
    assert_eq!(values.value_length(0), 4);
    let values = values.value(0);
    assert_eq!(
        values.as_primitive::<UInt64Type>().values(),
        &[9_223_372_036_854_775_808, 0, 7, 13],
        "values above i64::MAX must come from the /.u64 sidecar"
    );
}
