use std::collections::BTreeMap;

use nohash_hasher::IntMap;
use re_chunk::{Chunk, ChunkBuilder};
use re_log;
use re_log_types::{
    ApplicationId, EntityPath, NonMinI64, StoreId, TimeInt, TimePoint, TimeType, Timeline,
};
use re_sdk_types::{
    archetypes::{RecordingInfo, ViewCoordinates},
    components::Name,
};
use re_types_core::SerializedComponentBatch;

use crate::log::{EntryLog, Timestamp};

mod components;

use components::{
    component_owner, is_metadata_path, primitive_batches, structured_batches, structured_root,
};

/// Options that control how a WPILog is translated into Rerun chunks.
#[derive(Clone, Debug)]
pub struct ConversionOptions {
    /// Prefix every entity path with this entity.
    pub entity_path_prefix: Option<EntityPath>,
    /// Extra timepoint cells (e.g. from the importer's settings) to attach to every row.
    pub timepoint: Option<TimePoint>,
    /// Added to every WPILog timestamp, in nanoseconds.
    pub timestamp_offset_ns: Option<i64>,
    /// Name to show in the Rerun recording panel.
    pub recording_name: Option<String>,
    /// Add static [`ViewCoordinates`] matching WPILib's FLU (X forward, Y left, Z up) frame.
    pub view_coordinates: bool,
}

impl Default for ConversionOptions {
    fn default() -> Self {
        Self {
            entity_path_prefix: None,
            timepoint: None,
            timestamp_offset_ns: None,
            recording_name: None,
            view_coordinates: false,
        }
    }
}

pub fn log_changes_to_chunks(
    _store_id: &StoreId,
    _application_id: &ApplicationId,
    timeline: Timeline,
    log: &mut EntryLog,
) -> Vec<Chunk> {
    log_changes_to_chunks_with_options(timeline, log, &ConversionOptions::default())
}

pub fn log_changes_to_chunks_with_options(
    timeline: Timeline,
    log: &mut EntryLog,
    options: &ConversionOptions,
) -> Vec<Chunk> {
    let mut rows = BTreeMap::<(Timestamp, EntityPath), Vec<SerializedComponentBatch>>::new();

    for (key, timestamp, value) in log.get_changed() {
        if is_metadata_path(&key) {
            continue;
        }

        if let Some((root, kind, is_array)) = structured_root(log, &key, timestamp) {
            let target = component_owner(log, &root, timestamp).unwrap_or_else(|| root.clone());
            let batches = structured_batches(log, &root, timestamp, kind, is_array);
            if !batches.is_empty() {
                insert_batches(&mut rows, timestamp, target, batches);
                continue;
            }
            // If a recognized struct is missing the fields we need (or is an empty
            // array), fall back to logging its leaves as primitive values below.
        }

        let target = component_owner(log, &key, timestamp).unwrap_or_else(|| key.clone());
        let batches = primitive_batches(&value);
        insert_batches(&mut rows, timestamp, target, batches);
    }

    let mut per_entity =
        IntMap::<EntityPath, Vec<(Timestamp, Vec<SerializedComponentBatch>)>>::default();
    for ((timestamp, entity), batches) in rows {
        let entity = match &options.entity_path_prefix {
            Some(prefix) => prefix.join(&entity),
            None => entity,
        };
        per_entity
            .entry(entity)
            .or_default()
            .push((timestamp, batches));
    }

    let mut chunks = metadata_chunks(options);
    for (entity, rows) in per_entity {
        let mut builder: ChunkBuilder = Chunk::builder(entity);
        for (timestamp, batches) in rows {
            builder = builder.with_serialized_batches(
                re_chunk::RowId::new(),
                timepoint(timeline, options, timestamp),
                batches,
            );
        }
        match builder.build() {
            Ok(chunk) => chunks.push(chunk),
            Err(error) => re_log::warn!("failed to build a chunk for WPILog data: {error}"),
        }
    }

    chunks
}

fn insert_batches(
    rows: &mut BTreeMap<(Timestamp, EntityPath), Vec<SerializedComponentBatch>>,
    timestamp: Timestamp,
    entity: EntityPath,
    batches: Vec<SerializedComponentBatch>,
) {
    if batches.is_empty() {
        return;
    }

    let row = rows.entry((timestamp, entity)).or_default();
    for batch in batches {
        // Multiple struct fields can point at the same structured root. Only emit each
        // component once per row.
        if !row
            .iter()
            .any(|existing| existing.descriptor.component == batch.descriptor.component)
        {
            row.push(batch);
        }
    }
}

fn timepoint(timeline: Timeline, options: &ConversionOptions, timestamp: Timestamp) -> TimePoint {
    let mut nanos = i128::from(timestamp.0) * 1000;
    if let Some(offset) = options.timestamp_offset_ns {
        nanos += i128::from(offset);
    }
    let value = i64::try_from(nanos).unwrap_or(i64::MAX).max(0);
    let value = NonMinI64::new(value).unwrap_or_default();
    let time = if timeline.typ() == TimeType::Sequence {
        TimeInt::from_sequence(value)
    } else {
        TimeInt::from_nanos(value)
    };

    let mut timepoint = options.timepoint.clone().unwrap_or_default();
    timepoint.insert(timeline, time);
    timepoint
}

fn metadata_chunks(options: &ConversionOptions) -> Vec<Chunk> {
    let mut chunks = Vec::new();

    if let Some(name) = &options.recording_name {
        let info = RecordingInfo::update_fields().with_name(Name::from(name.clone()));
        match Chunk::builder(EntityPath::properties())
            .with_archetype_auto_row(TimePoint::STATIC, &info)
            .build()
        {
            Ok(chunk) => chunks.push(chunk),
            Err(error) => re_log::warn!("failed to add recording metadata: {error}"),
        }
    }

    if options.view_coordinates {
        // WPILib uses X forward, Y left, Z up.
        let coordinates = ViewCoordinates::FLU();
        match Chunk::builder(EntityPath::root())
            .with_archetype_auto_row(TimePoint::STATIC, &coordinates)
            .build()
        {
            Ok(chunk) => chunks.push(chunk),
            Err(error) => re_log::warn!("failed to add view coordinates: {error}"),
        }
    }

    chunks
}

/// Static Rerun metadata chunks (recording name, view coordinates) for an imported log.
#[must_use]
pub fn metadata_chunks_for(recording_name: Option<String>) -> Vec<Chunk> {
    metadata_chunks(&ConversionOptions {
        recording_name,
        view_coordinates: true,
        ..ConversionOptions::default()
    })
}

#[cfg(test)]
mod tests {
    use arrow::array::Array as _;
    use arrow::array::{BooleanArray, Float64Array, Int64Array, StringArray};
    use re_log_types::{EntityPath, Timeline};

    use super::{ConversionOptions, log_changes_to_chunks_with_options};
    use crate::{
        log::{EntryLog, Timestamp},
        values::EntryValue,
    };

    fn chunks(log: &mut EntryLog) -> Vec<re_chunk::Chunk> {
        log_changes_to_chunks_with_options(
            Timeline::new_duration("robotime"),
            log,
            &ConversionOptions {
                view_coordinates: false,
                ..ConversionOptions::default()
            },
        )
    }

    fn find<'a>(chunks: &'a [re_chunk::Chunk], entity: &str) -> &'a re_chunk::Chunk {
        chunks
            .iter()
            .find(|chunk| chunk.entity_path() == &EntityPath::from(entity))
            .unwrap()
    }

    #[test]
    fn emits_archetype_metadata_for_scalars_and_text() {
        let mut log = EntryLog::new();
        log.add_entry(
            EntityPath::from_single_string("value"),
            Timestamp(10),
            "int64",
            &42i64.to_le_bytes(),
        )
        .unwrap();
        log.add_entry(
            EntityPath::from_single_string("label"),
            Timestamp(10),
            "string",
            b"hello",
        )
        .unwrap();

        let chunks = chunks(&mut log);

        let value = find(&chunks, "value");
        assert!(value.component_descriptors().any(|descriptor| {
            descriptor
                .archetype
                .as_ref()
                .is_some_and(|name| name == "rerun.archetypes.Scalars")
                && descriptor
                    .component_type
                    .as_ref()
                    .is_some_and(|name| name == "rerun.components.Scalar")
        }));

        let label = find(&chunks, "label");
        assert!(label.component_descriptors().any(|descriptor| {
            descriptor
                .archetype
                .as_ref()
                .is_some_and(|name| name == "rerun.archetypes.TextDocument")
        }));
    }

    #[test]
    fn keeps_all_values_of_a_primitive_array() {
        let mut log = EntryLog::new();
        let payload = [
            1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0,
        ];
        log.add_entry(
            EntityPath::from_single_string("values"),
            Timestamp(10),
            "int64[]",
            &payload,
        )
        .unwrap();

        let chunks = chunks(&mut log);
        let values = find(&chunks, "values");
        let array = values
            .components()
            .get_array("Scalars:scalars".into())
            .expect("Scalars component");
        let scalar_array = array
            .values()
            .as_any()
            .downcast_ref::<Float64Array>()
            .unwrap();
        assert_eq!(scalar_array.values(), &[1.0, 2.0, 3.0]);
    }

    #[test]
    fn keeps_booleans_as_booleans() {
        let mut log = EntryLog::new();
        log.add_entry(
            EntityPath::from_single_string("flag"),
            Timestamp(10),
            "boolean",
            &[1],
        )
        .unwrap();
        log.add_entry(
            EntityPath::from_single_string("flags"),
            Timestamp(10),
            "boolean[]",
            &[1, 0, 1],
        )
        .unwrap();

        let chunks = chunks(&mut log);

        let flag = find(&chunks, "flag");
        assert!(!flag.component_descriptors().any(|descriptor| {
            descriptor
                .archetype
                .as_ref()
                .is_some_and(|name| name == "rerun.archetypes.Scalars")
        }));
        let array = flag
            .components()
            .get_array("value".into())
            .expect("boolean component");
        let values = array
            .values()
            .as_any()
            .downcast_ref::<BooleanArray>()
            .unwrap();
        assert!(values.value(0));

        let flags = find(&chunks, "flags");
        let array = flags
            .components()
            .get_array("value".into())
            .expect("boolean component");
        let values = array
            .values()
            .as_any()
            .downcast_ref::<BooleanArray>()
            .unwrap();
        assert_eq!(
            (0..values.len())
                .map(|i| values.value(i))
                .collect::<Vec<_>>(),
            [true, false, true]
        );
    }

    #[test]
    fn expands_struct_arrays_into_points3d() {
        let mut log = EntryLog::new();
        log.add_struct(
            "struct:Translation3d",
            crate::values::parse::wpistruct::WpiLibStructSchema::parse(
                b"double x; double y; double z;",
            )
            .unwrap(),
        );
        let payload = [
            1.0f64.to_le_bytes(),
            2.0f64.to_le_bytes(),
            3.0f64.to_le_bytes(),
            4.0f64.to_le_bytes(),
            5.0f64.to_le_bytes(),
            6.0f64.to_le_bytes(),
        ]
        .into_iter()
        .flatten()
        .collect::<Vec<_>>();
        let entity = EntityPath::from_single_string("points");
        log.add_entry(
            entity.clone(),
            Timestamp(10),
            "struct:Translation3d[]",
            &payload,
        )
        .unwrap();

        let chunks = chunks(&mut log);
        let points = find(&chunks, "points");
        assert!(points.component_descriptors().any(|descriptor| {
            descriptor
                .archetype
                .as_ref()
                .is_some_and(|name| name == "rerun.archetypes.Points3D")
        }));
        let array = points
            .components()
            .get_array("Points3D:positions".into())
            .unwrap();
        assert_eq!(array.len(), 1);
        let positions = array.values();
        let positions = positions
            .as_any()
            .downcast_ref::<arrow::array::FixedSizeListArray>()
            .unwrap();
        assert_eq!(positions.len(), 2);
        let coordinates = positions
            .values()
            .as_any()
            .downcast_ref::<arrow::array::Float32Array>()
            .unwrap();
        assert_eq!(coordinates.values(), &[1.0, 2.0, 3.0, 4.0, 5.0, 6.0]);
    }

    #[test]
    fn renders_pose2d_as_a_transform() {
        let mut log = EntryLog::new();
        log.add_struct(
            "struct:Translation2d",
            crate::values::parse::wpistruct::WpiLibStructSchema::parse(b"double x; double y;")
                .unwrap(),
        );
        log.add_struct(
            "struct:Rotation2d",
            crate::values::parse::wpistruct::WpiLibStructSchema::parse(b"double value;").unwrap(),
        );
        log.add_struct(
            "struct:Pose2d",
            crate::values::parse::wpistruct::WpiLibStructSchema::parse(
                b"struct:Translation2d translation; struct:Rotation2d rotation;",
            )
            .unwrap(),
        );

        let entity = EntityPath::from_single_string("pose");
        // A Pose2d is laid out as x, y, rotation (in radians).
        let payload = [
            1.0f64.to_le_bytes(),
            2.0f64.to_le_bytes(),
            std::f64::consts::FRAC_PI_2.to_le_bytes(),
        ]
        .into_iter()
        .flatten()
        .collect::<Vec<_>>();
        log.add_entry(entity.clone(), Timestamp(10), "struct:Pose2d", &payload)
            .unwrap();

        let chunks = chunks(&mut log);
        let pose = find(&chunks, "pose");
        assert!(pose.component_descriptors().any(|descriptor| {
            descriptor
                .archetype
                .as_ref()
                .is_some_and(|name| name == "rerun.archetypes.Transform3D")
        }));
        assert!(pose.component_descriptors().any(|descriptor| {
            descriptor
                .archetype
                .as_ref()
                .is_some_and(|name| name == "rerun.archetypes.TransformAxes3D")
        }));

        let translation = pose
            .components()
            .get_array("Transform3D:translation".into())
            .unwrap();
        let translation = translation
            .values()
            .as_any()
            .downcast_ref::<arrow::array::FixedSizeListArray>()
            .unwrap();
        let coordinates = translation
            .values()
            .as_any()
            .downcast_ref::<arrow::array::Float32Array>()
            .unwrap();
        assert_eq!(coordinates.values(), &[1.0, 2.0, 0.0]);
    }

    #[test]
    fn uses_builtin_schemas_when_the_log_omits_them() {
        let mut log = EntryLog::new();
        let payload = [
            1.0f64.to_le_bytes(),
            2.0f64.to_le_bytes(),
            0.0f64.to_le_bytes(),
        ]
        .into_iter()
        .flatten()
        .collect::<Vec<_>>();
        log.add_entry(
            EntityPath::from_single_string("pose"),
            Timestamp(10),
            "struct:Pose2d",
            &payload,
        )
        .unwrap();

        let chunks = chunks(&mut log);
        let pose = find(&chunks, "pose");
        assert!(pose.component_descriptors().any(|descriptor| {
            descriptor
                .archetype
                .as_ref()
                .is_some_and(|name| name == "rerun.archetypes.Transform3D")
        }));
    }

    #[test]
    fn resolves_wpilib_style_nested_schemas() {
        let mut log = EntryLog::new();
        log.add_struct(
            "struct:Pose2d",
            crate::values::parse::wpistruct::WpiLibStructSchema::parse(
                b"Translation2d translation;Rotation2d rotation",
            )
            .unwrap(),
        );

        let payload = [
            1.0f64.to_le_bytes(),
            2.0f64.to_le_bytes(),
            0.0f64.to_le_bytes(),
        ]
        .into_iter()
        .flatten()
        .collect::<Vec<_>>();
        log.add_entry(
            EntityPath::from_single_string("pose"),
            Timestamp(10),
            "struct:Pose2d",
            &payload,
        )
        .unwrap();

        let chunks = chunks(&mut log);
        let pose = find(&chunks, "pose");
        assert!(pose.component_descriptors().any(|descriptor| {
            descriptor
                .archetype
                .as_ref()
                .is_some_and(|name| name == "rerun.archetypes.Transform3D")
        }));
    }

    #[test]
    fn renders_pose3d_as_a_transform() {
        let mut log = EntryLog::new();
        log.add_struct(
            "struct:Translation3d",
            crate::values::parse::wpistruct::WpiLibStructSchema::parse(
                b"double x; double y; double z;",
            )
            .unwrap(),
        );
        log.add_struct(
            "struct:Quaternion",
            crate::values::parse::wpistruct::WpiLibStructSchema::parse(
                b"double w; double x; double y; double z;",
            )
            .unwrap(),
        );
        log.add_struct(
            "struct:Rotation3d",
            crate::values::parse::wpistruct::WpiLibStructSchema::parse(b"struct:Quaternion q;")
                .unwrap(),
        );
        log.add_struct(
            "struct:Pose3d",
            crate::values::parse::wpistruct::WpiLibStructSchema::parse(
                b"struct:Translation3d translation; struct:Rotation3d rotation;",
            )
            .unwrap(),
        );

        let entity = EntityPath::from_single_string("pose");
        let payload = [
            1.0f64.to_le_bytes(),
            2.0f64.to_le_bytes(),
            3.0f64.to_le_bytes(),
            1.0f64.to_le_bytes(),
            0.0f64.to_le_bytes(),
            0.0f64.to_le_bytes(),
            0.0f64.to_le_bytes(),
        ]
        .into_iter()
        .flatten()
        .collect::<Vec<_>>();
        log.add_entry(entity.clone(), Timestamp(10), "struct:Pose3d", &payload)
            .unwrap();

        let chunks = chunks(&mut log);
        let pose = find(&chunks, "pose");
        assert!(pose.component_descriptors().any(|descriptor| {
            descriptor
                .archetype
                .as_ref()
                .is_some_and(|name| name == "rerun.archetypes.Transform3D")
        }));
        assert!(pose.component_descriptors().any(|descriptor| {
            descriptor
                .component_type
                .as_ref()
                .is_some_and(|name| name == "rerun.components.RotationQuat")
        }));

        let quaternion = pose
            .components()
            .get_array("Transform3D:quaternion".into())
            .unwrap();
        let quaternion = quaternion
            .values()
            .as_any()
            .downcast_ref::<arrow::array::FixedSizeListArray>()
            .unwrap();
        let components = quaternion
            .values()
            .as_any()
            .downcast_ref::<arrow::array::Float32Array>()
            .unwrap();
        assert_eq!(components.values(), &[0.0, 0.0, 0.0, 1.0]);
    }

    #[test]
    fn adds_recording_metadata_and_view_coordinates() {
        let mut log = EntryLog::new();
        log.add_entry(
            EntityPath::from_single_string("value"),
            Timestamp(10),
            "int64",
            &1i64.to_le_bytes(),
        )
        .unwrap();

        let chunks = log_changes_to_chunks_with_options(
            Timeline::new_duration("robotime"),
            &mut log,
            &ConversionOptions {
                recording_name: Some("match".into()),
                view_coordinates: true,
                ..ConversionOptions::default()
            },
        );

        assert!(chunks.iter().any(|chunk| {
            chunk.entity_path() == &EntityPath::properties()
                && chunk.component_descriptors().any(|descriptor| {
                    descriptor
                        .archetype
                        .as_ref()
                        .is_some_and(|name| name == "rerun.archetypes.RecordingInfo")
                })
        }));
        assert!(chunks.iter().any(|chunk| {
            chunk.entity_path() == &EntityPath::root()
                && chunk.component_descriptors().any(|descriptor| {
                    descriptor
                        .archetype
                        .as_ref()
                        .is_some_and(|name| name == "rerun.archetypes.ViewCoordinates")
                })
        }));
    }

    #[test]
    fn honors_entity_path_prefix_and_type_aliases() {
        let mut log = EntryLog::new();
        log.add_entry(
            EntityPath::from_single_string("value"),
            Timestamp(10),
            "int",
            &1i64.to_le_bytes(),
        )
        .unwrap();

        let chunks = log_changes_to_chunks_with_options(
            Timeline::new_duration("robotime"),
            &mut log,
            &ConversionOptions {
                entity_path_prefix: Some(EntityPath::from_single_string("robot")),
                view_coordinates: false,
                ..ConversionOptions::default()
            },
        );

        assert_eq!(chunks.len(), 1);
        assert_eq!(chunks[0].entity_path(), &EntityPath::from("robot/value"));
    }

    #[test]
    fn parses_components_metadata() {
        let mut log = EntryLog::new();
        let mut payload = Vec::new();
        payload.extend_from_slice(&1u32.to_le_bytes());
        payload.extend_from_slice(&6u32.to_le_bytes());
        payload.extend_from_slice(b"Scalar");
        log.add_entry(
            EntityPath::from("entity/.components"),
            Timestamp(10),
            "string[]",
            &payload,
        )
        .unwrap();
        log.add_entry(
            EntityPath::from("entity/Scalar"),
            Timestamp(10),
            "int64[]",
            &[1i64.to_le_bytes().to_vec(), 2i64.to_le_bytes().to_vec()]
                .into_iter()
                .flatten()
                .collect::<Vec<_>>(),
        )
        .unwrap();

        let chunks = chunks(&mut log);
        let entity = find(&chunks, "entity");
        assert!(
            entity
                .components()
                .contains_component("Scalars:scalars".into())
        );
        assert_eq!(chunks.len(), 1);
    }

    #[test]
    fn reads_string_arrays_from_the_wpilog_encoding() {
        let mut log = EntryLog::new();
        let mut payload = Vec::new();
        payload.extend_from_slice(&2u32.to_le_bytes());
        payload.extend_from_slice(&3u32.to_le_bytes());
        payload.extend_from_slice(b"one");
        payload.extend_from_slice(&3u32.to_le_bytes());
        payload.extend_from_slice(b"two");
        log.add_entry(
            EntityPath::from_single_string("messages"),
            Timestamp(10),
            "string[]",
            &payload,
        )
        .unwrap();

        let chunks = chunks(&mut log);
        let messages = find(&chunks, "messages");
        assert!(messages.component_descriptors().any(|descriptor| {
            descriptor
                .archetype
                .as_ref()
                .is_some_and(|name| name == "rerun.archetypes.TextDocument")
        }));
    }

    #[test]
    fn ignores_metadata_entries() {
        let mut log = EntryLog::new();
        log.add_entry(
            EntityPath::from("entity/.type"),
            Timestamp(10),
            "string",
            b"Entity",
        )
        .unwrap();
        log.add_entry(
            EntityPath::from("entity/value"),
            Timestamp(10),
            "int64",
            &1i64.to_le_bytes(),
        )
        .unwrap();

        let chunks = chunks(&mut log);
        assert!(find(&chunks, "entity/value").num_components() > 0);
        assert!(
            !chunks
                .iter()
                .any(|chunk| chunk.entity_path() == &EntityPath::from("entity"))
        );
    }

    #[test]
    fn decodes_fixed_width_arrays() {
        let value = EntryValue::parse_from_wpilog(
            "int64[]",
            &[1, 0, 0, 0, 0, 0, 0, 0],
            &mut crate::values::parse::wpistruct::StructSchemas::new(),
        )
        .unwrap();
        let EntryValue::Arrow(value) = value else {
            panic!("expected arrow");
        };
        let values = value.as_any().downcast_ref::<Int64Array>().unwrap();
        assert_eq!(values.values(), &[1]);
    }

    #[test]
    fn decodes_string_arrays() {
        let mut payload = Vec::new();
        payload.extend_from_slice(&1u32.to_le_bytes());
        payload.extend_from_slice(&4u32.to_le_bytes());
        payload.extend_from_slice(b"test");
        let value = EntryValue::parse_from_wpilog(
            "string[]",
            &payload,
            &mut crate::values::parse::wpistruct::StructSchemas::new(),
        )
        .unwrap();
        let EntryValue::Arrow(value) = value else {
            panic!("expected arrow");
        };
        let values = value.as_any().downcast_ref::<StringArray>().unwrap();
        assert_eq!(values.value(0), "test");
    }
}
