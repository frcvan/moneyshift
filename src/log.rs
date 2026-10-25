use std::{
    collections::{BTreeMap, BTreeSet},
    num::{NonZero, TryFromIntError},
    path::Path,
    sync::Arc,
};

use arrow::array::{ArrayRef, Int64Array};
use hashbrown::HashMap;
use nohash_hasher::IntMap;
use re_log;
use re_log_types::{EntityPath, NonMinI64, TimeInt};

use crate::values::{
    EntryValue, EntryValueParseError, StructValue,
    parse::wpistruct::{
        ResolveError, StructSchemas, UnresolvedWpiLibStructType, WpiLibStructData,
        WpiLibStructSchema, WpiLibStructType,
    },
};

/// Upper bound on struct values buffered while waiting for their schema.
///
/// Values of an unknown struct type are queued until the schema arrives, which never happens for
/// a type whose producer never publishes one (a live session that connects after the topics were
/// created, for example). Cap the queue so such a session cannot grow without bound.
const MAX_QUEUED_STRUCTS: usize = 4096;

#[derive(Clone, Copy, Debug, Hash, PartialEq, Eq, PartialOrd, Ord)]
/// The timestamp of an entry in the log.
///
/// Measured in microseconds since the RIO was enabled.
pub struct Timestamp(pub u64);

impl TryInto<TimeInt> for Timestamp {
    type Error = TryFromIntError;
    fn try_into(self) -> Result<TimeInt, Self::Error> {
        let nanos: i64 = (i128::from(self.0) * 1000).try_into()?;
        Ok(TimeInt::from_nanos(
            NonMinI64::new(nanos).unwrap_or_default(),
        ))
    }
}

/// A queued value whose struct type had no schema yet.
struct QueuedStruct {
    key: EntityPath,
    timestamp: Timestamp,
    ty: String,
    data: Vec<u8>,
}

/// Cache of the child paths of one parent entity.
///
/// Struct fields and array elements repeat for every sample, so the joined [`EntityPath`] is
/// computed once per `(parent, segment)` pair instead of once per sample.
#[derive(Default)]
struct PathCache {
    fields: HashMap<EntityPath, HashMap<Box<str>, EntityPath>>,
    indices: HashMap<EntityPath, HashMap<usize, EntityPath>>,
}

impl PathCache {
    fn field(&mut self, parent: &EntityPath, name: &str) -> EntityPath {
        if let Some(children) = self.fields.get(parent)
            && let Some(path) = children.get(name)
        {
            return path.clone();
        }

        let path = parent.join(&EntityPath::from_file_path(Path::new(name)));
        self.fields
            .entry(parent.clone())
            .or_default()
            .insert(name.into(), path.clone());
        path
    }

    fn index(&mut self, parent: &EntityPath, index: usize) -> EntityPath {
        if let Some(children) = self.indices.get(parent)
            && let Some(path) = children.get(&index)
        {
            return path.clone();
        }

        let path = parent.join(&EntityPath::from_single_string(index.to_string()));
        self.indices
            .entry(parent.clone())
            .or_default()
            .insert(index, path.clone());
        path
    }
}

pub struct EntryLog {
    entries: IntMap<EntityPath, BTreeMap<Timestamp, ArrayRef>>,
    /// The WPILog type string for entries that were logged through [`EntryLog::add_entry`].
    ///
    /// This is what tells us whether a value is a plain primitive, an array, or one of
    /// WPILib's native structs (e.g. `struct:Pose2d[]`).
    types: IntMap<EntityPath, String>,
    changed: BTreeSet<(Timestamp, EntityPath)>,
    schemas: StructSchemas,
    queued_structs: HashMap<String, Vec<QueuedStruct>>,
    queued_count: usize,
    paths: PathCache,
}

impl Default for EntryLog {
    fn default() -> Self {
        Self::new()
    }
}

impl EntryLog {
    #[must_use]
    pub fn new() -> Self {
        Self {
            entries: IntMap::default(),
            types: IntMap::default(),
            changed: BTreeSet::new(),
            schemas: StructSchemas::new(),
            queued_structs: HashMap::new(),
            queued_count: 0,
            paths: PathCache::default(),
        }
    }

    /// The raw WPILog type of an entry, if it was added through [`Self::add_entry`].
    #[must_use]
    pub fn type_of(&self, key: &EntityPath) -> Option<&str> {
        self.types.get(key).map(String::as_str)
    }

    #[must_use]
    pub fn struct_schema(
        &self,
        name: &str,
    ) -> Option<&WpiLibStructSchema<UnresolvedWpiLibStructType>> {
        self.schemas.raw(name)
    }

    /// Registers a struct schema, invalidating any cached resolved layout for it.
    pub fn add_struct(
        &mut self,
        name: impl Into<String>,
        schema: WpiLibStructSchema<UnresolvedWpiLibStructType>,
    ) {
        self.schemas.insert(name, schema);
    }

    /// The number of values currently waiting for their struct schema.
    #[must_use]
    pub fn queued_struct_len(&self) -> usize {
        self.queued_count
    }

    pub fn add_entry(
        &mut self,
        key: EntityPath,
        timestamp: Timestamp,
        ty: &str,
        value: &[u8],
    ) -> Result<(), anyhow::Error> {
        // Writing the type on every sample would allocate a string per value for data that
        // almost never changes.
        if self.types.get(&key).map(String::as_str) != Some(ty) {
            self.types.insert(key.clone(), ty.to_owned());
        }

        // Structs are parsed straight into the log. Building an `EntryValue` tree first would
        // allocate one `Vec` per struct (and per struct array element) only to flatten it again
        // into the per-field entries below.
        if EntryValue::is_struct_type(ty) {
            let (name, is_array) = match ty.strip_suffix("[]") {
                Some(name) => (name, true),
                None => (ty, false),
            };
            return match self.schemas.resolve(name) {
                Ok(schema) => self.add_struct_bytes(key, timestamp, value, &schema, is_array),
                Err(ResolveError::NotFound(name)) => {
                    self.queue_struct(key, timestamp, ty, value, &name);
                    Ok(())
                }
                Err(err) => Err(anyhow::anyhow!("{err}")),
            };
        }

        match EntryValue::parse_from_wpilog(ty, value, &mut self.schemas) {
            Ok(v) => self.add_entryvalue(key, timestamp, v),
            Err(EntryValueParseError::StructNotFound(name)) => {
                self.queue_struct(key, timestamp, ty, value, &name);
                Ok(())
            }
            Err(EntryValueParseError::Other(e)) => Err(e),
        }
    }

    /// Parses one struct value (or array of them) directly into the per-field entries.
    fn add_struct_bytes(
        &mut self,
        key: EntityPath,
        timestamp: Timestamp,
        data: &[u8],
        schema: &Arc<WpiLibStructSchema<WpiLibStructType>>,
        is_array: bool,
    ) -> Result<(), anyhow::Error> {
        if !is_array {
            let remaining = self.parse_struct_fields(&key, timestamp, data, schema)?;
            anyhow::ensure!(remaining.is_empty(), "struct payload has trailing bytes");
            return Ok(());
        }

        let size = schema.size();
        anyhow::ensure!(size != 0, "cannot parse an array of zero-sized structs");
        let chunks = data.chunks_exact(size);
        anyhow::ensure!(
            chunks.remainder().is_empty(),
            "struct array payload has {} trailing bytes",
            chunks.remainder().len()
        );

        let length = self.paths.field(&key, "length");
        self.add_entryvalue(
            length,
            timestamp,
            EntryValue::Arrow(Arc::new(Int64Array::from_iter_values([
                (data.len() / size) as i64,
            ]))),
        )?;
        for (index, chunk) in chunks.enumerate() {
            let element = self.paths.index(&key, index);
            let remaining = self.parse_struct_fields(&element, timestamp, chunk, schema)?;
            debug_assert!(remaining.is_empty(), "chunked struct parse left bytes");
        }
        Ok(())
    }

    /// Parses the fields of one struct instance under `key`, in schema order.
    fn parse_struct_fields<'d>(
        &mut self,
        key: &EntityPath,
        timestamp: Timestamp,
        data: &'d [u8],
        schema: &Arc<WpiLibStructSchema<WpiLibStructType>>,
    ) -> Result<&'d [u8], anyhow::Error> {
        let mut data = data;
        for (name, field) in &schema.fields {
            let child = self.paths.field(key, name);
            data = self.parse_struct_field(&child, timestamp, data, field)?;
        }
        Ok(data)
    }

    fn parse_struct_field<'d>(
        &mut self,
        key: &EntityPath,
        timestamp: Timestamp,
        data: &'d [u8],
        field: &WpiLibStructData<WpiLibStructType>,
    ) -> Result<&'d [u8], anyhow::Error> {
        let count = field.count.map_or(1, NonZero::get);

        match &field.ty {
            WpiLibStructType::Primitive(primitive) => {
                let len = primitive.size() * count;
                let Some((bytes, remaining)) = data.split_at_checked(len) else {
                    anyhow::bail!("not enough data for a {primitive:?}[{count}] field");
                };
                let array = EntryValue::parse_struct_leaf(bytes, *primitive, field.count)?;
                self.add_entryvalue(key.clone(), timestamp, EntryValue::Arrow(array))?;
                Ok(remaining)
            }
            WpiLibStructType::Custom(nested) => {
                if field.count.is_none() {
                    return self.parse_struct_fields(key, timestamp, data, nested);
                }

                let size = nested.size();
                anyhow::ensure!(size != 0, "cannot parse an array of zero-sized structs");
                let len = size
                    .checked_mul(count)
                    .ok_or_else(|| anyhow::anyhow!("struct array size overflow"))?;
                let Some((bytes, remaining)) = data.split_at_checked(len) else {
                    anyhow::bail!("not enough data for a struct[{count}] field");
                };

                let length = self.paths.field(key, "length");
                self.add_entryvalue(
                    length,
                    timestamp,
                    EntryValue::Arrow(Arc::new(Int64Array::from_iter_values([count as i64]))),
                )?;
                for (index, chunk) in bytes.chunks_exact(size).enumerate() {
                    let element = self.paths.index(key, index);
                    let leftover = self.parse_struct_fields(&element, timestamp, chunk, nested)?;
                    debug_assert!(leftover.is_empty(), "chunked struct parse left bytes");
                }
                Ok(remaining)
            }
        }
    }

    fn queue_struct(
        &mut self,
        key: EntityPath,
        timestamp: Timestamp,
        ty: &str,
        value: &[u8],
        missing: &str,
    ) {
        if self.queued_count >= MAX_QUEUED_STRUCTS {
            re_log::warn_once!(
                "dropping values of unknown struct type {missing}: {} values are already queued",
                self.queued_count
            );
            return;
        }

        re_log::info_once!(
            "struct not found: {missing} for key {key} at {}",
            timestamp.0
        );

        // Queue under the bare name so it matches a later `.schema/struct:Name` entry
        // regardless of whether the producer used the `struct:` prefix.
        let queued_name = missing
            .strip_prefix("struct:")
            .unwrap_or(missing)
            .to_owned();
        self.queued_structs
            .entry(queued_name)
            .or_default()
            .push(QueuedStruct {
                key,
                timestamp,
                ty: ty.to_owned(),
                data: value.to_vec(),
            });
        self.queued_count += 1;
    }

    pub fn add_entryvalue(
        &mut self,
        key: EntityPath,
        timestamp: Timestamp,
        value: EntryValue,
    ) -> Result<(), anyhow::Error> {
        match value {
            EntryValue::Arrow(array) => {
                let entry = self.entries.entry(key.clone()).or_default();
                entry.insert(timestamp, array);

                self.changed.insert((timestamp, key));
            }
            EntryValue::StructSchema(s) => {
                let name = key.last().map_or("struct:Unknown", |s| s.unescaped_str());
                self.add_struct(name, s);

                re_log::info!("new struct schema {name} at {}", timestamp.0);

                let bare = name.strip_prefix("struct:").unwrap_or(name).to_owned();
                let prefixed = format!("struct:{name}");
                for candidate in [name.to_owned(), bare, prefixed] {
                    if let Some(queued) = self.queued_structs.remove(&candidate) {
                        self.queued_count = self.queued_count.saturating_sub(queued.len());
                        for queued in queued {
                            re_log::debug!(
                                "unqueued struct {name} for {} at {}",
                                queued.key,
                                queued.timestamp.0
                            );
                            self.add_entry(queued.key, queued.timestamp, &queued.ty, &queued.data)?;
                        }
                    }
                }
            }
            // A struct expands into one entry per field under the struct's path.
            EntryValue::Struct(StructValue { schema, fields }) => {
                for (name, field) in schema.fields.keys().map(String::as_str).zip(fields) {
                    let child = self.paths.field(&key, name);
                    self.add_entryvalue(child, timestamp, field)?;
                }
            }

            EntryValue::StructArray(values) => {
                self.handle_array(&key, timestamp, values.into_iter().map(EntryValue::Struct))?;
            }
            EntryValue::ArrayArrow(a) => {
                // Keep primitive arrays as a single entry. Rerun components such as
                // `Scalars` can represent many values at one timestamp, and this keeps
                // the original shape of the logging data intact.
                if a.is_empty() {
                    return Ok(());
                }
                let arrays = a.iter().map(|array| &**array).collect::<Vec<_>>();
                let array = arrow::compute::concat(&arrays)?;
                self.add_entryvalue(key, timestamp, EntryValue::Arrow(array))?;
            }
        }

        Ok(())
    }

    fn handle_array(
        &mut self,
        path: &EntityPath,
        timestamp: Timestamp,
        arr: impl ExactSizeIterator<Item = EntryValue>,
    ) -> Result<(), anyhow::Error> {
        let count = arr.len();
        let length = self.paths.field(path, "length");
        self.add_entryvalue(
            length,
            timestamp,
            EntryValue::Arrow(Arc::new(Int64Array::from_iter_values([count as i64]))),
        )?;

        for (i, value) in arr.enumerate() {
            let element = self.paths.index(path, i);
            self.add_entryvalue(element, timestamp, value)?;
        }

        Ok(())
    }

    /// Gets the changed entries with their values and clears the changed set.
    pub fn get_changed(&mut self) -> Vec<(EntityPath, Timestamp, ArrayRef)> {
        std::mem::take(&mut self.changed)
            .into_iter()
            .filter_map(|(time, key)| {
                self.entries
                    .get(&key)
                    .and_then(|entry| entry.get(&time))
                    .map(|value| (key, time, value.clone()))
            })
            .collect()
    }

    #[must_use]
    pub fn get_entry(&self, key: &EntityPath) -> Option<&BTreeMap<Timestamp, ArrayRef>> {
        self.entries.get(key)
    }

    pub fn get_latest_entry(&self, key: &EntityPath) -> Option<(&Timestamp, &ArrayRef)> {
        self.entries.get(key).and_then(BTreeMap::last_key_value)
    }

    /// Drops history older than `cutoff`, always keeping the latest value of every entry.
    ///
    /// Live sessions run for a whole match, so the log must not grow without bound. Metadata
    /// (types and struct schemas) is retained. Call this after a flush, when `changed` is empty.
    pub fn prune_older_than(&mut self, cutoff: Timestamp) {
        for entry in self.entries.values_mut() {
            let Some(latest) = entry.keys().next_back().copied() else {
                continue;
            };
            entry.retain(|timestamp, _| *timestamp >= cutoff || *timestamp == latest);
        }
    }

    /// Returns the latest value at or before `time`, for a path built from `root` and `parts`.
    #[must_use]
    pub fn get_latest_at(
        &self,
        root: &EntityPath,
        parts: &[&str],
        time: Timestamp,
    ) -> Option<(&Timestamp, &ArrayRef)> {
        let mut path = root.clone();
        for part in parts {
            path = path.join(&EntityPath::from_file_path(Path::new(part)));
        }
        self.get_latest_from(&path, time)
    }
    #[must_use]
    pub fn get_latest_from(
        &self,
        key: &EntityPath,
        time: Timestamp,
    ) -> Option<(&Timestamp, &ArrayRef)> {
        self.entries
            .get(key)
            .and_then(|entry| entry.range(..=time).last())
    }
}

#[cfg(test)]
mod tests {
    use super::{EntryLog, Timestamp};
    use crate::values::parse::wpistruct::WpiLibStructSchema;
    use arrow::array::{Array, Float64Array, Int64Array, StringArray};
    use re_log_types::EntityPath;

    #[test]
    fn keeps_primitive_arrays_as_one_changed_entry() {
        let mut log = EntryLog::new();
        let payload = [
            1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0,
        ];
        let key = EntityPath::from_single_string("values");

        log.add_entry(key.clone(), Timestamp(42), "int64[]", &payload)
            .unwrap();

        let changed = log.get_changed();
        assert_eq!(changed.len(), 1);
        assert_eq!(changed[0].0, key);
        let values = changed[0].2.as_any().downcast_ref::<Int64Array>().unwrap();
        assert_eq!(values.values(), &[1, 2, 3]);
    }

    #[test]
    fn keeps_primitive_value_arrays_at_their_root() {
        let mut log = EntryLog::new();
        let key = EntityPath::from_single_string("values");
        let payload = [
            0x01, 0x00, 0x00, 0x00, // count
            0x06, 0x00, 0x00, 0x00, b'S', b'c', b'a', b'l', b'a', b'r',
        ];

        log.add_entry(key.clone(), Timestamp(42), "string[]", &payload)
            .unwrap();

        let (_, value) = log.get_latest_entry(&key).unwrap();
        let values = value.as_any().downcast_ref::<StringArray>().unwrap();
        assert_eq!(values.value(0), "Scalar");
    }

    #[test]
    fn selects_the_latest_value_at_or_before_a_timestamp() {
        let mut log = EntryLog::new();
        let key = EntityPath::from_single_string("value");
        log.add_entry(key.clone(), Timestamp(10), "double", &1.0f64.to_le_bytes())
            .unwrap();
        log.add_entry(key.clone(), Timestamp(20), "double", &2.0f64.to_le_bytes())
            .unwrap();

        let (_, value) = log.get_latest_from(&key, Timestamp(15)).unwrap();
        assert_eq!(
            value
                .as_any()
                .downcast_ref::<Float64Array>()
                .unwrap()
                .value(0),
            1.0
        );
        let (_, value) = log.get_latest_from(&key, Timestamp(20)).unwrap();
        assert_eq!(
            value
                .as_any()
                .downcast_ref::<Float64Array>()
                .unwrap()
                .value(0),
            2.0
        );
    }

    #[test]
    fn replacing_a_value_at_one_timestamp_creates_one_change() {
        let mut log = EntryLog::new();
        let key = EntityPath::from_single_string("value");
        log.add_entry(key.clone(), Timestamp(10), "double", &1.0f64.to_le_bytes())
            .unwrap();
        log.add_entry(key.clone(), Timestamp(10), "double", &2.0f64.to_le_bytes())
            .unwrap();

        let changed = log.get_changed();
        assert_eq!(changed.len(), 1);
        assert_eq!(changed[0].0, key);
        assert_eq!(
            changed[0]
                .2
                .as_any()
                .downcast_ref::<Float64Array>()
                .unwrap()
                .value(0),
            2.0
        );
    }

    #[test]
    fn rejects_timestamps_that_do_not_fit_rerun_time() {
        let result: Result<re_log_types::TimeInt, _> = Timestamp(u64::MAX).try_into();
        assert!(result.is_err());
    }

    #[test]
    fn pruning_keeps_the_latest_value_for_every_entry() {
        let mut log = EntryLog::new();
        let key = EntityPath::from_single_string("value");
        for (timestamp, value) in [(10u64, 1.0f64), (20, 2.0), (30, 3.0)] {
            log.add_entry(
                key.clone(),
                Timestamp(timestamp),
                "double",
                &value.to_le_bytes(),
            )
            .unwrap();
        }
        log.get_changed();

        log.prune_older_than(Timestamp(25));

        let entries = log.get_entry(&key).unwrap();
        assert_eq!(entries.len(), 1);
        assert_eq!(entries.keys().next().copied(), Some(Timestamp(30)));
    }

    #[test]
    fn pruning_keeps_an_old_latest_value() {
        let mut log = EntryLog::new();
        let key = EntityPath::from_single_string("stale");
        log.add_entry(key.clone(), Timestamp(1), "double", &1.0f64.to_le_bytes())
            .unwrap();
        log.get_changed();

        log.prune_older_than(Timestamp(1_000));

        assert!(log.get_latest_entry(&key).is_some());
    }

    #[test]
    fn returns_changes_in_timestamp_order() {
        let mut log = EntryLog::new();
        log.add_entry(
            EntityPath::from_single_string("later"),
            Timestamp(20),
            "int64",
            &2i64.to_le_bytes(),
        )
        .unwrap();
        log.add_entry(
            EntityPath::from_single_string("earlier"),
            Timestamp(10),
            "int64",
            &1i64.to_le_bytes(),
        )
        .unwrap();

        let changed = log.get_changed();
        assert_eq!(
            changed
                .iter()
                .map(|(_, time, _)| time.0)
                .collect::<Vec<_>>(),
            [10, 20]
        );
    }

    #[test]
    fn expands_struct_array_elements_under_stable_indices() {
        let mut log = EntryLog::new();
        log.add_struct(
            "struct:Translation2d",
            WpiLibStructSchema::parse(b"double x; double y;").unwrap(),
        );
        let payload = [
            1.0f64.to_le_bytes(),
            2.0f64.to_le_bytes(),
            3.0f64.to_le_bytes(),
            4.0f64.to_le_bytes(),
        ]
        .into_iter()
        .flatten()
        .collect::<Vec<_>>();
        let key = EntityPath::from_single_string("translations");

        log.add_entry(
            key.clone(),
            Timestamp(10),
            "struct:Translation2d[]",
            &payload,
        )
        .unwrap();

        for (index, values) in [(0, [1.0, 2.0]), (1, [3.0, 4.0])] {
            for (field, expected) in [("x", values[0]), ("y", values[1])] {
                let path = key
                    .join(&EntityPath::from_single_string(index.to_string()))
                    .join(&EntityPath::from_single_string(field));
                let (_, value) = log.get_latest_entry(&path).unwrap();
                assert_eq!(
                    value
                        .as_any()
                        .downcast_ref::<Float64Array>()
                        .unwrap()
                        .value(0),
                    expected
                );
            }
        }
        assert!(
            log.get_entry(&key.join(&EntityPath::from_single_string("length")))
                .is_some()
        );
    }

    #[test]
    fn struct_fast_path_matches_the_entry_value_tree() {
        // `add_entry` decodes structs straight into the log; the tree parser is the reference
        // implementation, so both must produce byte-identical entries.
        let mut fast = EntryLog::new();
        let mut tree = EntryLog::new();
        let mut payload = Vec::new();
        for value in [1.0f64, 2.0, 3.0] {
            payload.extend_from_slice(&value.to_le_bytes());
        }
        let key = EntityPath::from_single_string("pose");

        for ty in ["struct:Pose2d", "struct:Pose2d[]"] {
            fast.add_entry(key.clone(), Timestamp(7), ty, &payload)
                .unwrap();
            let value =
                crate::values::EntryValue::parse_from_wpilog(ty, &payload, &mut tree.schemas)
                    .unwrap();
            tree.add_entryvalue(key.clone(), Timestamp(7), value)
                .unwrap();
            assert_eq!(fast.get_changed(), tree.get_changed(), "{ty}");
        }
    }

    #[test]
    fn expands_nested_struct_arrays_under_their_field() {
        let mut log = EntryLog::new();
        log.add_struct(
            "struct:Holder",
            WpiLibStructSchema::parse(b"struct:Translation2d pts[2]; double z;").unwrap(),
        );
        let mut payload = Vec::new();
        for value in [1.0f64, 2.0, 3.0, 4.0, 9.0] {
            payload.extend_from_slice(&value.to_le_bytes());
        }
        let key = EntityPath::from_single_string("holder");
        log.add_entry(key.clone(), Timestamp(10), "struct:Holder", &payload)
            .unwrap();

        let value_at = |parts: &[&str]| {
            let (_, value) = log.get_latest_at(&key, parts, Timestamp(10)).unwrap();
            value
                .as_any()
                .downcast_ref::<Float64Array>()
                .unwrap()
                .value(0)
        };
        assert_eq!(value_at(&["pts", "0", "x"]), 1.0);
        assert_eq!(value_at(&["pts", "1", "y"]), 4.0);
        assert_eq!(value_at(&["z"]), 9.0);
        let length = log
            .get_latest_at(&key, &["pts", "length"], Timestamp(10))
            .unwrap()
            .1;
        assert_eq!(
            length
                .as_any()
                .downcast_ref::<Int64Array>()
                .unwrap()
                .value(0),
            2
        );
    }

    #[test]
    fn replays_values_queued_before_their_schema() {
        let mut log = EntryLog::new();
        let key = EntityPath::from_single_string("custom");
        let mut payload = Vec::new();
        payload.extend_from_slice(&3.5f64.to_le_bytes());

        log.add_entry(key.clone(), Timestamp(1), "struct:Custom", &payload)
            .unwrap();
        assert_eq!(log.queued_struct_len(), 1);
        assert!(log.get_latest_entry(&key).is_none());

        log.add_entry(
            EntityPath::from(".schema/struct:Custom"),
            Timestamp(2),
            "structschema",
            b"double value;",
        )
        .unwrap();

        assert_eq!(log.queued_struct_len(), 0);
        let (_, value) = log
            .get_latest_at(&key, &["value"], Timestamp(1))
            .expect("the queued value was replayed");
        assert_eq!(
            value
                .as_any()
                .downcast_ref::<Float64Array>()
                .unwrap()
                .value(0),
            3.5
        );
    }
}
