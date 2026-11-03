use std::sync::Arc;

use arrow::{
    array::{
        ArrayRef, Float32Array, Float64Array, Int8Array, Int16Array, Int32Array, Int64Array,
        StringArray, UInt8Array, UInt16Array, UInt32Array, UInt64Array,
    },
    compute::cast,
    datatypes::DataType,
};
use nohash_hasher::IntMap;
use re_log_types::EntityPath;
use re_sdk_types::{
    AnyValues, Rotation3D,
    archetypes::{
        Arrows2D, Arrows3D, InstancePoses3D, Points2D, Points3D, Scalars, TextDocument,
        Transform3D, TransformAxes3D,
    },
    components::{
        Blob, Position2D, Position3D, RotationAxisAngle, Scalar, Text, Translation3D, Vector2D,
        Vector3D,
    },
    encodings::{Angle, Quaternion},
};
use re_types_core::{
    AsComponents, ComponentBatch, ComponentDescriptor, FromArrow, SerializedComponentBatch,
};

use crate::log::{EntryLog, Timestamp};

/// WPILib structs that have a natural Rerun representation.
#[derive(Clone, Copy, Debug, PartialEq, Eq)]
pub(super) enum StructuredKind {
    Translation2d,
    Translation3d,
    Rotation2d,
    Rotation3d,
    Quaternion,
    Pose2d,
    Pose3d,
    Transform2d,
    Transform3d,
    Twist2d,
    Twist3d,
    ChassisSpeeds,
    SwerveModuleState,
    SwerveModulePosition,
}

impl StructuredKind {
    /// Recognizes both `Pose2d` and `struct:Pose2d[]` style WPILog type names.
    pub(super) fn from_type(ty: &str) -> Option<(Self, bool)> {
        let ty = ty.trim();
        let is_array = ty.ends_with("[]");
        let ty = ty.strip_suffix("[]").unwrap_or(ty);
        let ty = ty.strip_prefix("struct:").unwrap_or(ty);
        // Some producers use fully-qualified class names in their type metadata.
        let ty = ty.rsplit('.').next().unwrap_or(ty);

        // Compared case-insensitively in place: this runs for every entry and every field, so a
        // lowercased copy would be an allocation on the hot path.
        let is = |name: &str| ty.eq_ignore_ascii_case(name);
        let kind = if is("translation2d") || is("point2d") {
            Self::Translation2d
        } else if is("translation3d") || is("point3d") {
            Self::Translation3d
        } else if is("rotation2d") {
            Self::Rotation2d
        } else if is("rotation3d") {
            Self::Rotation3d
        } else if is("quaternion") {
            Self::Quaternion
        } else if is("pose2d") {
            Self::Pose2d
        } else if is("pose3d") {
            Self::Pose3d
        } else if is("transform2d") {
            Self::Transform2d
        } else if is("transform3d") {
            Self::Transform3d
        } else if is("twist2d") {
            Self::Twist2d
        } else if is("twist3d") {
            Self::Twist3d
        } else if is("chassisspeeds") {
            Self::ChassisSpeeds
        } else if
        // 2027 renamed `SwerveModuleState` to `SwerveModuleVelocity` (`velocity` instead of
        // `speed_meters_per_second`); both carry a speed and a steering angle.
        is("swerve/modulestate")
            || is("swerve_module_state")
            || is("swervemodulestate")
            || is("swerve/modulevelocity")
            || is("swerve_module_velocity")
            || is("swervemodulevelocity")
        {
            Self::SwerveModuleState
        } else if is("swerve/moduleposition")
            || is("swerve_module_position")
            || is("swervemoduleposition")
        {
            Self::SwerveModulePosition
        } else {
            return None;
        };

        Some((kind, is_array))
    }
}

/// Metadata entries that describe the logging schema rather than a value. They should not be
/// rendered as data.
pub(super) fn is_metadata_path(path: &EntityPath) -> bool {
    let parts = path.as_slice();
    let Some(last) = parts.last() else {
        return false;
    };
    let last = last.unescaped_str();
    last == ".type"
        || last == ".components"
        || parts
            .len()
            .checked_sub(2)
            .is_some_and(|index| parts[index].unescaped_str() == ".schema")
}

/// The per-path metadata answers conversion asks about for every change.
///
/// Resolving them once per conversion keeps the per-change cost to hash lookups. The answers
/// themselves are unchanged: a log's types and metadata entries are all known by then.
pub(super) struct Resolver {
    /// Entity path to the structured kind its WPILog type names, if it names one. `None` means
    /// the entry has a type, but not one that renders as a struct.
    structured: IntMap<EntityPath, Option<(StructuredKind, bool)>>,
    /// For every entry, the ancestors (itself included) that can name a structured kind: the
    /// ones with a WPILog type and the ones with a `.type` child. Walking these instead of the
    /// path's parents keeps the per-change lookup allocation-free.
    candidates: IntMap<EntityPath, Vec<EntityPath>>,
    /// Entries that have a `.components` sibling; only they can belong to a component owner.
    component_fields: IntMap<EntityPath, ()>,
}

impl Resolver {
    pub(super) fn new(log: &EntryLog) -> Self {
        let structured: IntMap<EntityPath, Option<(StructuredKind, bool)>> = log
            .typed_entries()
            .iter()
            .map(|(path, ty)| (path.clone(), StructuredKind::from_type(ty)))
            .collect();

        let mut with_type_meta = IntMap::default();
        let mut with_components = IntMap::default();
        for path in log.paths() {
            let (Some(parent), Some(last)) = (path.parent(), path.last()) else {
                continue;
            };
            match last.unescaped_str() {
                ".components" => {
                    with_components.insert(parent, ());
                }
                ".type" => {
                    with_type_meta.insert(parent, ());
                }
                _ => {}
            }
        }

        // Every path conversion can ask about: the entries themselves, the typed entries (a
        // struct's root has a type but no value of its own) and the entities `.type` metadata
        // names.
        let mut queried = IntMap::default();
        for path in log.paths() {
            queried.insert(path.clone(), ());
        }
        for path in structured.keys() {
            queried.insert(path.clone(), ());
        }
        for path in with_type_meta.keys() {
            queried.insert(path.clone(), ());
        }

        let mut candidates = IntMap::default();
        let mut component_fields = IntMap::default();
        for path in queried.keys() {
            let mut chain = Vec::new();
            let mut owned = false;
            let mut current = Some(path.clone());
            while let Some(candidate) = current {
                if structured.contains_key(&candidate) || with_type_meta.contains_key(&candidate) {
                    chain.push(candidate.clone());
                }
                // Only entities with a `.components` child can own components.
                owned |= with_components.contains_key(&candidate);
                current = candidate.parent();
            }
            if !chain.is_empty() {
                candidates.insert(path.clone(), chain);
            }
            if owned {
                component_fields.insert(path.clone(), ());
            }
        }

        Self {
            structured,
            candidates,
            component_fields,
        }
    }
}

/// If this entry belongs to an entity that declared rerun components via `.components`, return
/// that entity. Otherwise the entry is its own entity.
pub(super) fn component_owner(
    log: &EntryLog,
    resolver: &Resolver,
    path: &EntityPath,
    timestamp: Timestamp,
) -> Option<EntityPath> {
    if !resolver.component_fields.contains_key(path) {
        return None;
    }
    let parent = path.parent()?;
    let field = path.last()?.unescaped_str();
    let (_, value) = log.get_latest_at(&parent, &[".components"], timestamp)?;
    with_strings(value, |components| {
        components.iter().flatten().any(|component| component == field)
    })
    .unwrap_or(false)
    .then_some(parent)
}

/// Find the outermost WPILib struct that this entry belongs to, if it is one we know how to
/// render.
pub(super) fn structured_root(
    log: &EntryLog,
    resolver: &Resolver,
    path: &EntityPath,
    timestamp: Timestamp,
) -> Option<(EntityPath, StructuredKind, bool)> {
    for current in resolver.candidates.get(path)? {
        match resolver.structured.get(current) {
            // A typed entry's own type wins over the entity's `.type` metadata, even when it is
            // not a struct we render.
            Some(known) => {
                if let Some((kind, is_array)) = known {
                    return Some((current.clone(), *kind, *is_array));
                }
            }
            None => {
                if let Some(ty) = latest_string(log, current, &[".type"], timestamp)
                    && let Some((kind, is_array)) = StructuredKind::from_type(&ty)
                {
                    return Some((current.clone(), kind, is_array));
                }
            }
        }
    }
    None
}

/// Build the Rerun archetype batches for one structured value at `root`/`timestamp`.
pub(super) fn structured_batches(
    log: &EntryLog,
    root: &EntityPath,
    timestamp: Timestamp,
    kind: StructuredKind,
    is_array: bool,
) -> Vec<SerializedComponentBatch> {
    let count = if is_array {
        array_len(log, root, timestamp)
    } else {
        1
    };
    if count == 0 {
        return Vec::new();
    }

    let elements = (0..count)
        .map(|index| {
            if is_array {
                root.join(&EntityPath::from_single_string(index.to_string()))
            } else {
                root.clone()
            }
        })
        .collect::<Vec<_>>();

    match kind {
        StructuredKind::Translation2d => {
            let points = elements
                .iter()
                .filter_map(|element| read_vec2(log, element, timestamp, None))
                .map(Position2D::from)
                .collect::<Vec<_>>();
            points_2d(&points)
        }
        StructuredKind::Translation3d => {
            let points = elements
                .iter()
                .filter_map(|element| read_vec3(log, element, timestamp, None))
                .map(Position3D::from)
                .collect::<Vec<_>>();
            points_3d(&points)
        }
        StructuredKind::Rotation2d => {
            let angles = elements
                .iter()
                .filter_map(|element| read_yaw(log, element, timestamp, None))
                .map(|yaw| Scalar::from(f64::from(yaw)));
            scalars(angles)
        }
        StructuredKind::Rotation3d | StructuredKind::Quaternion => {
            let rotations = elements
                .iter()
                .filter_map(|element| read_quat(log, element, timestamp, None))
                .map(|quat| Rotation3D::Quaternion(quat.into()))
                .collect::<Vec<_>>();
            rotations_batches(&rotations)
        }
        StructuredKind::Pose2d | StructuredKind::Transform2d => {
            let poses = elements
                .iter()
                .filter_map(|element| {
                    let [x, y] = read_vec2(log, element, timestamp, Some("translation"))
                        .or_else(|| read_vec2(log, element, timestamp, None))?;
                    let yaw = read_yaw(log, element, timestamp, Some("rotation"))
                        .or_else(|| read_yaw(log, element, timestamp, None))
                        .unwrap_or(0.0);
                    Some((x, y, yaw))
                })
                .collect::<Vec<_>>();
            poses_2d(&poses)
        }
        StructuredKind::Pose3d | StructuredKind::Transform3d => {
            let poses = elements
                .iter()
                .filter_map(|element| {
                    let translation = read_vec3(log, element, timestamp, Some("translation"))
                        .or_else(|| read_vec3(log, element, timestamp, None))?;
                    let rotation = read_quat(log, element, timestamp, Some("rotation"))
                        .or_else(|| read_quat(log, element, timestamp, None));
                    Some((translation, rotation))
                })
                .collect::<Vec<_>>();
            poses_3d(&poses)
        }
        StructuredKind::Twist2d => {
            let twists = elements
                .iter()
                .filter_map(|element| {
                    let dx = read_number(log, element, timestamp, &[&["dx"], &["x"]])?;
                    let dy = read_number(log, element, timestamp, &[&["dy"], &["y"]])?;
                    let dtheta =
                        read_number(log, element, timestamp, &[&["dtheta"], &["omega"], &["w"]])
                            .unwrap_or(0.0);
                    Some(([dx as f32, dy as f32], dtheta as f32))
                })
                .collect::<Vec<_>>();
            let vectors = twists
                .iter()
                .map(|(vector, _)| Vector2D::from(*vector))
                .collect::<Vec<_>>();
            let angles = twists
                .iter()
                .map(|(_, angle)| Scalar::from(f64::from(*angle)));
            let mut batches = arrows_2d(&vectors);
            batches.extend(scalars(angles));
            batches
        }
        StructuredKind::Twist3d => {
            let twists = elements
                .iter()
                .filter_map(|element| {
                    let dx = read_number(log, element, timestamp, &[&["dx"], &["x"]])?;
                    let dy = read_number(log, element, timestamp, &[&["dy"], &["y"]])?;
                    let dz = read_number(log, element, timestamp, &[&["dz"], &["z"]])?;
                    Some([dx as f32, dy as f32, dz as f32])
                })
                .collect::<Vec<_>>();
            let vectors = twists
                .iter()
                .map(|vector| Vector3D::from(*vector))
                .collect::<Vec<_>>();
            arrows_3d(&vectors)
        }
        StructuredKind::ChassisSpeeds => {
            let speeds = elements
                .iter()
                .filter_map(|element| {
                    let vx = read_number(log, element, timestamp, &[&["vx"], &["x"]])?;
                    let vy = read_number(log, element, timestamp, &[&["vy"], &["y"]])?;
                    let omega = read_number(log, element, timestamp, &[&["omega"], &["dtheta"]])
                        .unwrap_or(0.0);
                    Some(([vx as f32, vy as f32], omega as f32))
                })
                .collect::<Vec<_>>();
            let vectors = speeds
                .iter()
                .map(|(vector, _)| Vector2D::from(*vector))
                .collect::<Vec<_>>();
            let angles = speeds
                .iter()
                .map(|(_, omega)| Scalar::from(f64::from(*omega)));
            let mut batches = arrows_2d(&vectors);
            batches.extend(scalars(angles));
            batches
        }
        StructuredKind::SwerveModuleState => {
            let states = elements
                .iter()
                .filter_map(|element| {
                    let speed = read_number(log, element, timestamp, &[&["speed"], &["velocity"]])?;
                    let angle = read_yaw(log, element, timestamp, Some("angle"))
                        .or_else(|| read_yaw(log, element, timestamp, Some("rotation")))
                        .unwrap_or(0.0);
                    Some((speed as f32, angle))
                })
                .collect::<Vec<_>>();
            let vectors = states
                .iter()
                .map(|(speed, angle)| Vector2D::from([*speed * angle.cos(), *speed * angle.sin()]))
                .collect::<Vec<_>>();
            arrows_2d(&vectors)
        }
        StructuredKind::SwerveModulePosition => {
            let positions = elements
                .iter()
                .filter_map(|element| {
                    let distance = read_number(log, element, timestamp, &[&["distance"], &["x"]])?;
                    let angle = read_yaw(log, element, timestamp, Some("angle"))
                        .or_else(|| read_yaw(log, element, timestamp, Some("rotation")))
                        .unwrap_or(0.0);
                    Some((distance as f32, angle))
                })
                .collect::<Vec<_>>();
            let points = positions
                .iter()
                .map(|(distance, angle)| {
                    Position2D::from([*distance * angle.cos(), *distance * angle.sin()])
                })
                .collect::<Vec<_>>();
            points_2d(&points)
        }
    }
}

/// Build the Rerun archetype batches for a primitive WPILog value.
pub(super) fn primitive_batches(value: &ArrayRef) -> Vec<SerializedComponentBatch> {
    match value.data_type() {
        DataType::Utf8 => latest_text(value).map_or_else(Vec::new, |text| {
            let document = TextDocument::new(Text::from(text));
            document.as_serialized_batches()
        }),
        DataType::Binary => blob(value).map_or_else(Vec::new, |batch| vec![batch]),
        // Booleans stay booleans: Rerun has no `Scalar`-style bool archetype, so log the column
        // as a dynamic component (the same mechanism as `AnyValues`). The viewer then shows
        // `true`/`false` instead of a float64 0/1.
        DataType::Boolean => AnyValues::default()
            .with_component_from_data("value", Arc::clone(value))
            .as_serialized_batches(),
        datatype if datatype.is_numeric() => numeric_batches(value),
        _ => Vec::new(),
    }
}

/// Scalars for a numeric column.
///
/// The arrays the decoder produces are read in place instead of being cast into a second Arrow
/// array; anything else (a decimal, say) still goes through Arrow's own cast.
fn numeric_batches(value: &ArrayRef) -> Vec<SerializedComponentBatch> {
    let any = value.as_any();
    macro_rules! read {
        ($array:ty, $conv:expr) => {
            if let Some(values) = any.downcast_ref::<$array>() {
                return scalars(values.iter().flatten().map($conv));
            }
        };
    }
    read!(Float64Array, Scalar::from);
    read!(Float32Array, |value: f32| Scalar::from(f64::from(value)));
    read!(Int64Array, |value: i64| Scalar::from(value as f64));
    read!(Int32Array, |value: i32| Scalar::from(f64::from(value)));
    read!(Int16Array, |value: i16| Scalar::from(f64::from(value)));
    read!(Int8Array, |value: i8| Scalar::from(f64::from(value)));
    read!(UInt64Array, |value: u64| Scalar::from(value as f64));
    read!(UInt32Array, |value: u32| Scalar::from(f64::from(value)));
    read!(UInt16Array, |value: u16| Scalar::from(f64::from(value)));
    read!(UInt8Array, |value: u8| Scalar::from(f64::from(value)));

    numeric_values(value).map_or_else(Vec::new, |values| {
        scalars(values.into_iter().map(Scalar::from))
    })
}

/// Reads all strings from an entry, if it is a string or string array.
pub(super) fn latest_strings(
    log: &EntryLog,
    root: &EntityPath,
    parts: &[&str],
    timestamp: Timestamp,
) -> Option<Vec<String>> {
    let (_, value) = log.get_latest_at(root, parts, timestamp)?;
    string_values(value)
}

fn latest_string(
    log: &EntryLog,
    root: &EntityPath,
    parts: &[&str],
    timestamp: Timestamp,
) -> Option<String> {
    latest_strings(log, root, parts, timestamp)?
        .into_iter()
        .next()
}

fn latest_text(value: &ArrayRef) -> Option<String> {
    let values = string_values(value)?;
    (!values.is_empty()).then(|| values.join("\n"))
}

/// Runs `read` on the column's strings, casting only when it is not already UTF-8.
fn with_strings<R>(value: &ArrayRef, read: impl FnOnce(&StringArray) -> R) -> Option<R> {
    if let Some(strings) = value.as_any().downcast_ref::<StringArray>() {
        return Some(read(strings));
    }
    let value = cast(value, &DataType::Utf8).ok()?;
    Some(read(value.as_any().downcast_ref::<StringArray>()?))
}

fn string_values(value: &ArrayRef) -> Option<Vec<String>> {
    with_strings(value, |values| {
        values.iter().flatten().map(str::to_owned).collect()
    })
}

/// The first value of a numeric column, without building a vector for it.
fn numeric_first(value: &ArrayRef) -> Option<f64> {
    let any = value.as_any();
    macro_rules! first {
        ($array:ty, $conv:expr) => {
            if let Some(values) = any.downcast_ref::<$array>() {
                return values.iter().flatten().next().map($conv);
            }
        };
    }
    first!(Float64Array, |value: f64| value);
    first!(Float32Array, |value: f32| f64::from(value));
    first!(Int64Array, |value: i64| value as f64);
    first!(Int32Array, |value: i32| f64::from(value));
    first!(Int16Array, |value: i16| f64::from(value));
    first!(Int8Array, |value: i8| f64::from(value));
    first!(UInt64Array, |value: u64| value as f64);
    first!(UInt32Array, |value: u32| f64::from(value));
    first!(UInt16Array, |value: u16| f64::from(value));
    first!(UInt8Array, |value: u8| f64::from(value));

    numeric_first_via_cast(value)
}

fn numeric_first_via_cast(value: &ArrayRef) -> Option<f64> {
    let value = cast(value, &DataType::Float64).ok()?;
    value
        .as_any()
        .downcast_ref::<Float64Array>()?
        .iter()
        .flatten()
        .next()
}

fn numeric_values(value: &ArrayRef) -> Option<Vec<f64>> {
    let any = value.as_any();
    macro_rules! read {
        ($array:ty, $conv:expr) => {
            if let Some(values) = any.downcast_ref::<$array>() {
                return Some(values.iter().flatten().map($conv).collect());
            }
        };
    }
    read!(Float64Array, |value: f64| value);
    read!(Float32Array, |value: f32| f64::from(value));
    read!(Int64Array, |value: i64| value as f64);
    read!(Int32Array, |value: i32| f64::from(value));
    read!(Int16Array, |value: i16| f64::from(value));
    read!(Int8Array, |value: i8| f64::from(value));
    read!(UInt64Array, |value: u64| value as f64);
    read!(UInt32Array, |value: u32| f64::from(value));
    read!(UInt16Array, |value: u16| f64::from(value));
    read!(UInt8Array, |value: u8| f64::from(value));

    let value = cast(value, &DataType::Float64).ok()?;
    let values = value.as_any().downcast_ref::<Float64Array>()?;
    Some(values.iter().flatten().collect())
}

fn blob(value: &ArrayRef) -> Option<SerializedComponentBatch> {
    let blobs = Blob::from_arrow(value).ok()?;
    let array = blobs.to_arrow().ok()?;
    Some(SerializedComponentBatch {
        descriptor: ComponentDescriptor {
            archetype: None,
            component: "rerun.components.Blob".into(),
            component_type: Some("rerun.components.Blob".into()),
        },
        array,
    })
}

fn array_len(log: &EntryLog, root: &EntityPath, timestamp: Timestamp) -> usize {
    if let Some(length) = read_number(log, root, timestamp, &[&["length"]]) {
        return length.max(0.0) as usize;
    }

    // Fall back to probing indexed elements. This also handles files that expand struct arrays
    // without writing a length entry.
    let mut count = 0;
    while log
        .get_latest_at(
            &root.join(&EntityPath::from_single_string(count.to_string())),
            &["x"],
            timestamp,
        )
        .is_some()
        || log
            .get_latest_at(
                &root.join(&EntityPath::from_single_string(count.to_string())),
                &["translation", "x"],
                timestamp,
            )
            .is_some()
        || log
            .get_latest_at(
                &root.join(&EntityPath::from_single_string(count.to_string())),
                &["value"],
                timestamp,
            )
            .is_some()
    {
        count += 1;
    }
    count
}

fn read_number(
    log: &EntryLog,
    root: &EntityPath,
    timestamp: Timestamp,
    paths: &[&[&str]],
) -> Option<f64> {
    paths.iter().find_map(|parts| {
        let (_, value) = log.get_latest_at(root, parts, timestamp)?;
        numeric_first(value)
    })
}

fn read_vec2(
    log: &EntryLog,
    root: &EntityPath,
    timestamp: Timestamp,
    field: Option<&str>,
) -> Option<[f32; 2]> {
    let x = match field {
        Some(field) => read_number(log, root, timestamp, &[&[field, "x"], &["x"]])?,
        None => read_number(log, root, timestamp, &[&["x"]])?,
    };
    let y = match field {
        Some(field) => read_number(log, root, timestamp, &[&[field, "y"], &["y"]])?,
        None => read_number(log, root, timestamp, &[&["y"]])?,
    };
    Some([x as f32, y as f32])
}

fn read_vec3(
    log: &EntryLog,
    root: &EntityPath,
    timestamp: Timestamp,
    field: Option<&str>,
) -> Option<[f32; 3]> {
    let x = match field {
        Some(field) => read_number(log, root, timestamp, &[&[field, "x"], &["x"]])?,
        None => read_number(log, root, timestamp, &[&["x"]])?,
    };
    let y = match field {
        Some(field) => read_number(log, root, timestamp, &[&[field, "y"], &["y"]])?,
        None => read_number(log, root, timestamp, &[&["y"]])?,
    };
    let z = match field {
        Some(field) => read_number(log, root, timestamp, &[&[field, "z"], &["z"], &["0"]])?,
        None => read_number(log, root, timestamp, &[&["z"], &["0"]])?,
    };
    Some([x as f32, y as f32, z as f32])
}

fn read_yaw(
    log: &EntryLog,
    root: &EntityPath,
    timestamp: Timestamp,
    field: Option<&str>,
) -> Option<f32> {
    let direct = match field {
        Some(field) => read_number(
            log,
            root,
            timestamp,
            &[
                &[field, "value"],
                &[field, "radians"],
                &["value"],
                &["radians"],
            ],
        ),
        None => read_number(log, root, timestamp, &[&["value"], &["radians"]]),
    };
    if let Some(value) = direct {
        return Some(value as f32);
    }

    // Some serializers persist Rotation2d as its cosine and sine.
    let cos = read_number(log, root, timestamp, &[&["cos"]]);
    let sin = read_number(log, root, timestamp, &[&["sin"]]);
    match (cos, sin) {
        (Some(cos), Some(sin)) => Some((sin as f32).atan2(cos as f32)),
        _ => None,
    }
}

fn read_quat(
    log: &EntryLog,
    root: &EntityPath,
    timestamp: Timestamp,
    field: Option<&str>,
) -> Option<Quaternion> {
    let bases: Vec<Vec<&str>> = match field {
        Some(field) => vec![vec![field, "q"], vec![field], vec!["q"], Vec::new()],
        None => vec![vec!["q"], Vec::new()],
    };

    for base in bases {
        let get = |name: &str, alias: &str| {
            let mut path = base.clone();
            path.push(name);
            let value = read_number(log, root, timestamp, &[&path]);
            if value.is_some() {
                return value;
            }
            if let Some(last) = path.last_mut() {
                *last = alias;
            }
            read_number(log, root, timestamp, &[&path])
        };

        let Some(w) = get("w", "qw") else {
            continue;
        };
        let Some(x) = get("x", "qx") else {
            continue;
        };
        let Some(y) = get("y", "qy") else {
            continue;
        };
        let Some(z) = get("z", "qz") else {
            continue;
        };

        return Some(Quaternion::from_xyzw([
            x as f32, y as f32, z as f32, w as f32,
        ]));
    }

    None
}

fn scalars(values: impl IntoIterator<Item = Scalar>) -> Vec<SerializedComponentBatch> {
    let values = values.into_iter().collect::<Vec<_>>();
    if values.is_empty() {
        return Vec::new();
    }
    Scalars::new(values.iter().copied()).as_serialized_batches()
}

fn points_2d(points: &[Position2D]) -> Vec<SerializedComponentBatch> {
    if points.is_empty() {
        return Vec::new();
    }
    Points2D::new(points.iter().copied()).as_serialized_batches()
}

fn points_3d(points: &[Position3D]) -> Vec<SerializedComponentBatch> {
    if points.is_empty() {
        return Vec::new();
    }
    Points3D::new(points.iter().copied()).as_serialized_batches()
}

fn arrows_2d(vectors: &[Vector2D]) -> Vec<SerializedComponentBatch> {
    if vectors.is_empty() {
        return Vec::new();
    }
    Arrows2D::from_vectors(vectors.iter().copied()).as_serialized_batches()
}

fn arrows_3d(vectors: &[Vector3D]) -> Vec<SerializedComponentBatch> {
    if vectors.is_empty() {
        return Vec::new();
    }
    Arrows3D::from_vectors(vectors.iter().copied()).as_serialized_batches()
}

fn rotations_batches(rotations: &[Rotation3D]) -> Vec<SerializedComponentBatch> {
    if rotations.is_empty() {
        return Vec::new();
    }

    if rotations.len() == 1 {
        let transform = Transform3D::from_rotation(rotations[0]);
        let mut batches = transform.as_serialized_batches();
        batches.extend(axes_batches());
        batches
    } else {
        let quaternions = rotations.iter().filter_map(|rotation| match rotation {
            Rotation3D::Quaternion(quaternion) => Some(*quaternion),
            Rotation3D::AxisAngle(_) => None,
        });
        InstancePoses3D::new()
            .with_quaternions(quaternions)
            .as_serialized_batches()
    }
}

fn poses_2d(poses: &[(f32, f32, f32)]) -> Vec<SerializedComponentBatch> {
    if poses.is_empty() {
        return Vec::new();
    }

    let points = poses
        .iter()
        .map(|(x, y, _)| Position2D::from([*x, *y]))
        .collect::<Vec<_>>();
    let mut batches = points_2d(&points);

    if poses.len() == 1 {
        let (x, y, yaw) = poses[0];
        let transform = Transform3D::from_translation_rotation(
            Translation3D::from([x, y, 0.0]),
            Rotation3D::AxisAngle(RotationAxisAngle::new(
                [0.0, 0.0, 1.0],
                Angle::from_radians(yaw),
            )),
        );
        batches.extend(transform.as_serialized_batches());
        batches.extend(axes_batches());
    }

    // For arrays we keep the point positions at the actual pose translations. Using
    // `InstancePoses3D` here *and* logging translated points would apply each pose twice.
    batches
}

fn poses_3d(poses: &[([f32; 3], Option<Quaternion>)]) -> Vec<SerializedComponentBatch> {
    if poses.is_empty() {
        return Vec::new();
    }

    let points = poses
        .iter()
        .map(|(translation, _)| Position3D::from(*translation))
        .collect::<Vec<_>>();
    let mut batches = points_3d(&points);

    if poses.len() == 1 {
        let (translation, rotation) = poses[0];
        let transform = match rotation {
            Some(rotation) => Transform3D::from_translation_rotation(
                Translation3D::from(translation),
                Rotation3D::Quaternion(rotation.into()),
            ),
            None => Transform3D::from_translation(Translation3D::from(translation)),
        };
        batches.extend(transform.as_serialized_batches());
        batches.extend(axes_batches());
    }

    batches
}

fn axes_batches() -> Vec<SerializedComponentBatch> {
    TransformAxes3D::new(0.5).as_serialized_batches()
}
