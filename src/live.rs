//! Live NetworkTables → Rerun bridge.
//!
//! Subscribes to every topic on the team's NT server and maps them in two lanes:
//!
//! * `/Rerun/...` topics published by the Java `NtRecorder` go through [`crate::nt::protocol`]
//!   (reflection-driven Arrow reconstruction);
//! * every other topic goes through [`crate::nt::native`] and the WPILog conversion heuristics,
//!   so plain robot code, AdvantageKit and dashboard topics are graphed without extra work.
//!
//! Batches are debounced by ~2 ms so a whole `log` call (metadata plus every leaf topic) is
//! decoded as one update, and every entity in a flush shares one `robot_time`.

use std::collections::{HashMap, HashSet};
use std::sync::atomic::{AtomicBool, Ordering};
use std::sync::{Arc, Mutex};
use std::time::{Duration, Instant};

use nt_client::ClientHandle;
use nt_client::data::DataType;
use nt_client::subscribe::{ReceivedMessage, SubscriptionOptions};
use nt_client::topic::AnnouncedTopic;
use nt_client::{Client, NTAddr, NewClientOptions};
use re_log_types::{EntityPath, NonMinI64, TimeCell, TimePoint, Timeline};
use re_sdk::{RecordingStream, RecordingStreamBuilder};

use crate::cli::{LiveArgs, LiveSink};
use crate::conv::{self, ConversionOptions};
use crate::log::{EntryLog, Timestamp};
use crate::nt::{native, protocol};

/// How long to wait for the rest of a batch before decoding an entity.
const DEBOUNCE: Duration = Duration::from_millis(2);
/// Upper bound on the debounce window, so a continuously busy subscription still flushes.
const MAX_DEBOUNCE: Duration = Duration::from_millis(10);
/// Keep this much plain-topic history in memory during a long session.
const NATIVE_HISTORY: Duration = Duration::from_secs(600);
/// Upper bound on value topics buffered before their entity's metadata arrives.
const MAX_ORPHANS: usize = 10_000;

/// Runs the live bridge on a dedicated Tokio runtime.
pub fn run(args: &LiveArgs) -> anyhow::Result<()> {
    let runtime = tokio::runtime::Builder::new_multi_thread()
        .enable_all()
        .build()?;
    runtime.block_on(run_async(args))
}

async fn run_async(args: &LiveArgs) -> anyhow::Result<()> {
    let stream = build_stream(args)?;
    let options = NewClientOptions {
        addr: match (args.team, args.nt_addr) {
            (Some(team), _) => NTAddr::TeamNumber(team),
            (_, Some(addr)) => NTAddr::Custom(addr),
            _ => NTAddr::Local,
        },
        name: "rerun-firstrun".to_owned(),
        ..NewClientOptions::default()
    };

    let addr = options.addr.clone().into_addr()?;
    if !args.quiet {
        re_log::info!("connecting to the NetworkTables server at {addr}");
        match &args.sink {
            LiveSink::Spawn => re_log::info!("streaming to a spawned Rerun viewer"),
            LiveSink::Connect(url) => re_log::info!("streaming to {url}"),
            LiveSink::Save(path) => re_log::info!("saving to {}", path.display()),
            LiveSink::Stdout => re_log::info!("writing RRD to stdout"),
        }
    }

    let state = Arc::new(Mutex::new(LiveState::new(args.clone())));
    let stop = Arc::new(AtomicBool::new(false));
    let mut backoff = Duration::from_millis(250);

    while !stop.load(Ordering::Relaxed) {
        let client = Client::new(options.clone());
        let (disconnected_tx, disconnected_rx) = tokio::sync::oneshot::channel();
        let task_state = Arc::clone(&state);
        let task_stream = stream.clone();
        let task_stop = Arc::clone(&stop);
        let connection = tokio::spawn(async move {
            let result = client
                .connect_setup(|client| {
                    let handle: ClientHandle = client.as_ref().clone();
                    tokio::spawn(subscription_loop(
                        handle,
                        task_state,
                        task_stream,
                        task_stop,
                    ));
                })
                .await;
            let _ = disconnected_tx.send(result);
        });

        let reason = tokio::select! {
            result = disconnected_rx => result.ok().and_then(Result::err).map(|error| error.to_string()),
            _ = wait_for_stop(Arc::clone(&stop)) => {
                connection.abort();
                break;
            }
        };

        connection.abort();

        if stop.load(Ordering::Relaxed) {
            break;
        }
        re_log::warn!(
            "NetworkTables disconnected ({}); retrying in {backoff:?}",
            reason.as_deref().unwrap_or("connection closed")
        );
        tokio::time::sleep(backoff).await;
        backoff = (backoff * 2).min(Duration::from_secs(5));
    }

    stream.flush_blocking()?;
    Ok(())
}

async fn wait_for_stop(stop: Arc<AtomicBool>) {
    while !stop.load(Ordering::Relaxed) {
        tokio::time::sleep(Duration::from_millis(50)).await;
    }
}

fn build_stream(args: &LiveArgs) -> anyhow::Result<RecordingStream> {
    let builder = RecordingStreamBuilder::new("FIRSTrun")
        .recording_name("FIRSTrun live")
        .recording_id("firstrun_live".to_owned());
    let stream = match &args.sink {
        LiveSink::Spawn => builder.spawn().map_err(|error| {
            anyhow::anyhow!(
                "could not start the Rerun viewer ({error}); install `rerun` on PATH or use \
                 --save/--connect/--stdout"
            )
        })?,
        LiveSink::Connect(url) => builder.connect_grpc_opts(url.clone())?,
        LiveSink::Save(path) => builder.save(path.clone())?,
        LiveSink::Stdout => builder.stdout()?,
    };
    Ok(stream)
}

/// Receives updates for one connection until it closes, the user stops us, or the socket dies.
async fn subscription_loop(
    client: ClientHandle,
    state: Arc<Mutex<LiveState>>,
    stream: RecordingStream,
    stop: Arc<AtomicBool>,
) {
    let mut subscriber = match client
        .topic("/")
        .subscribe(SubscriptionOptions {
            prefix: Some(true),
            ..SubscriptionOptions::default()
        })
        .await
    {
        Ok(subscriber) => subscriber,
        Err(error) => {
            re_log::warn!("failed to subscribe to NetworkTables: {error}");
            return;
        }
    };

    loop {
        let deadline = {
            let state = state.lock().expect("live state poisoned");
            state
                .queue
                .deadline()
                .unwrap_or_else(|| Instant::now() + Duration::from_secs(3600))
        };

        tokio::select! {
            message = subscriber.recv() => match message {
                Ok(ReceivedMessage::Updated((topic, value))) => {
                    let now = Instant::now();
                    state
                        .lock()
                        .expect("live state poisoned")
                        .update(&topic, &value, now);
                }
                Ok(ReceivedMessage::Announced(_)
                    | ReceivedMessage::UpdateProperties(_)
                    | ReceivedMessage::Unannounced { .. }) => {}
                Err(error) => {
                    re_log::debug!("NetworkTables subscription ended: {error}");
                    break;
                }
            },
            _ = tokio::time::sleep_until(tokio::time::Instant::from_std(deadline)) => {
                let now = Instant::now();
                state
                    .lock()
                    .expect("live state poisoned")
                    .flush(&stream, now, false);
            }
            _ = tokio::signal::ctrl_c() => {
                re_log::info!("stopping the live bridge");
                let now = Instant::now();
                state
                    .lock()
                    .expect("live state poisoned")
                    .flush(&stream, now, true);
                let _ = stream.flush_blocking();
                stop.store(true, Ordering::Relaxed);
                break;
            }
        }
    }
}

/// One entity published through the `/Rerun/` protocol.
struct EntityState {
    meta: protocol::EntityMeta,
    /// Reflection data for `meta`, rebuilt only when the metadata changes.
    plan: Option<protocol::EntityPlan>,
    /// The metadata changed since `plan` was built.
    plan_dirty: bool,
    columns: HashMap<String, protocol::NtValue>,
}

/// A plain-topic update waiting for the next flush.
struct PendingUpdate {
    key: EntityPath,
    ty: DataType,
    value: rmpv::Value,
}

/// Pending-entity bookkeeping for one flush.
///
/// This is deliberately synchronous and time-injectable so the debounce logic is unit-testable
/// without a NetworkTables server or a Tokio timer.
struct FlushQueue {
    pending: HashSet<EntityPath>,
    deadline: Option<Instant>,
    /// When the current batch started, used to cap how long the debounce can stretch.
    oldest: Option<Instant>,
}

impl FlushQueue {
    fn new() -> Self {
        Self {
            pending: HashSet::new(),
            deadline: None,
            oldest: None,
        }
    }

    fn touch(&mut self, entity: EntityPath, now: Instant) {
        self.pending.insert(entity);
        self.deadline = Some(self.next_deadline(now));
    }

    /// Schedules a flush without a `/Rerun` entity (plain topics only).
    fn schedule(&mut self, now: Instant) {
        self.deadline = Some(self.next_deadline(now));
    }

    /// The debounce deadline, capped so constant updates cannot postpone it forever.
    fn next_deadline(&mut self, now: Instant) -> Instant {
        let oldest = *self.oldest.get_or_insert(now);
        (now + DEBOUNCE).min(oldest + MAX_DEBOUNCE)
    }

    fn deadline(&self) -> Option<Instant> {
        self.deadline
    }

    fn take_due(&mut self, now: Instant) -> Vec<EntityPath> {
        if self.deadline.is_some_and(|deadline| now >= deadline) {
            self.deadline = None;
            self.oldest = None;
            self.pending.drain().collect()
        } else {
            Vec::new()
        }
    }

    fn take_all(&mut self) -> Vec<EntityPath> {
        self.deadline = None;
        self.oldest = None;
        self.pending.drain().collect()
    }
}

struct LiveState {
    args: LiveArgs,
    entities: HashMap<EntityPath, EntityState>,
    entities_by_topic: HashMap<String, EntityPath>,
    /// Value topics seen before their entity's metadata; claimed when the entity appears.
    orphans: HashMap<String, protocol::NtValue>,
    queue: FlushQueue,
    native: EntryLog,
    native_updates: Vec<PendingUpdate>,
    robot_time_override_ns: Option<i64>,
    latest_server_ns: i64,
    last_prune: Instant,
    warned: HashSet<String>,
}

impl LiveState {
    fn new(args: LiveArgs) -> Self {
        Self {
            args,
            entities: HashMap::new(),
            entities_by_topic: HashMap::new(),
            orphans: HashMap::new(),
            queue: FlushQueue::new(),
            native: EntryLog::new(),
            native_updates: Vec::new(),
            robot_time_override_ns: None,
            latest_server_ns: 0,
            last_prune: Instant::now(),
            warned: HashSet::new(),
        }
    }

    fn robot_time_ns(&self) -> i64 {
        self.robot_time_override_ns
            .unwrap_or(self.latest_server_ns)
            .max(0)
    }

    fn warn_once(&mut self, key: String, message: String) {
        if self.warned.insert(key) {
            re_log::warn!("{message}");
        }
    }

    /// Handles one value update.
    fn update(&mut self, topic: &AnnouncedTopic, value: &rmpv::Value, now: Instant) {
        if let Some(updated) = topic.last_updated() {
            self.latest_server_ns = duration_nanos(*updated);
        }
        let name = topic.name();

        if self.args.robot_time_topic.as_deref() == Some(name) {
            self.robot_time_override_ns = robot_time_from_value(value);
        }

        if let Some(rest) = name.strip_prefix("/Rerun/") {
            if let Some((entity_topic, suffix)) = rest.split_once("/.rerun/") {
                self.metadata(&format!("/Rerun/{entity_topic}"), suffix, value);
            } else if let Some((entity, offset)) = self.claim(name) {
                let Some(column) = column_from_topic(topic.r#type(), value) else {
                    self.warn_once(
                        format!("{name}:value"),
                        format!("ignoring unsupported value on {name}"),
                    );
                    return;
                };
                let suffix = &name[offset..];
                if let Some(state) = self.entities.get_mut(&entity) {
                    // Steady-state updates hit a key that already exists, so the suffix string is
                    // only allocated the first time a column appears.
                    match state.columns.get_mut(suffix) {
                        Some(existing) => *existing = column,
                        None => {
                            state.columns.insert(suffix.to_owned(), column);
                        }
                    }
                    self.queue.touch(entity, now);
                }
            } else {
                // Metadata may not have arrived yet; hold the value until it does. This is the
                // only unbounded map in the bridge, so cap it defensively.
                if let Some(column) = column_from_topic(topic.r#type(), value) {
                    if self.orphans.len() >= MAX_ORPHANS {
                        self.orphans.clear();
                    }
                    self.orphans.insert(name.to_owned(), column);
                }
            }
            return;
        }

        // Struct schemas must reach the log even though they live under a NetworkTables-internal
        // prefix; everything else under those prefixes is machinery, not robot data.
        if !name.starts_with("/.schema/") && native::is_internal_topic(name) {
            return;
        }
        self.native_updates.push(PendingUpdate {
            key: native::entity_path(name),
            ty: topic.r#type().clone(),
            value: value.clone(),
        });
        self.queue.schedule(now);
    }

    /// Registers or updates an entity's `/Rerun/` metadata.
    fn metadata(&mut self, entity_topic: &str, suffix: &str, value: &rmpv::Value) {
        let Some(entity) = protocol::entity_path_from_topic(entity_topic) else {
            self.warn_once(
                format!("{entity_topic}:path"),
                format!("skipping entity with an invalid escaped path: {entity_topic}"),
            );
            return;
        };
        let state = self
            .entities
            .entry(entity.clone())
            .or_insert_with(|| EntityState {
                meta: protocol::EntityMeta::default(),
                plan: None,
                plan_dirty: true,
                columns: HashMap::new(),
            });
        state.plan_dirty = true;

        match suffix {
            "archetype" => {
                state.meta.archetype = value
                    .as_str()
                    .filter(|name| !name.is_empty())
                    .map(ToOwned::to_owned);
            }
            "components" => {
                if let Some(value) = value.as_str() {
                    state.meta.components = protocol::EntityMeta::parse_components(value);
                }
            }
            "static" => state.meta.static_ = value.as_bool().unwrap_or(false),
            // `layout` is a version marker; anything else is reserved for a newer protocol.
            _ => {}
        }

        self.entities_by_topic
            .insert(entity_topic.to_owned(), entity.clone());

        // Values that arrived before the metadata are still useful.
        let prefix = format!("{entity_topic}/");
        let orphans = self
            .orphans
            .keys()
            .filter(|topic| topic.starts_with(&prefix))
            .cloned()
            .collect::<Vec<_>>();
        for orphan in orphans {
            if let Some(column) = self.orphans.remove(&orphan)
                && let Some(state) = self.entities.get_mut(&entity)
            {
                state
                    .columns
                    .insert(orphan[prefix.len()..].to_owned(), column);
            }
        }
    }

    /// Finds the registered entity a `/Rerun` value topic belongs to.
    ///
    /// Returns the entity and the offset where its column path starts, so the caller can slice
    /// the suffix straight out of the topic name instead of allocating a copy per update.
    fn claim(&self, topic: &str) -> Option<(EntityPath, usize)> {
        let mut cursor = topic;
        while let Some(index) = cursor.rfind('/') {
            cursor = &cursor[..index];
            if let Some(entity) = self.entities_by_topic.get(cursor) {
                return Some((entity.clone(), cursor.len() + 1));
            }
        }
        None
    }

    /// Emits everything pending: `/Rerun` entities first, then native topics.
    fn flush(&mut self, stream: &RecordingStream, now: Instant, force: bool) {
        let entities = if force {
            self.queue.take_all()
        } else {
            self.queue.take_due(now)
        };
        if entities.is_empty() && self.native_updates.is_empty() {
            return;
        }

        let robot_ns = self.robot_time_ns();
        let robot_cell =
            TimeCell::from_duration_nanos(NonMinI64::new(robot_ns).unwrap_or_default());

        for entity in entities {
            // Build the reflection plan once per metadata change instead of once per flush.
            let plan_error = {
                let Some(state) = self.entities.get_mut(&entity) else {
                    continue;
                };
                if state.plan.is_some() && !state.plan_dirty {
                    None
                } else {
                    state.plan_dirty = false;
                    match protocol::plan_entity(&state.meta) {
                        Ok(plan) => {
                            state.plan = Some(plan);
                            None
                        }
                        Err(error) => {
                            state.plan = None;
                            Some(error)
                        }
                    }
                }
            };
            if let Some(error) = plan_error {
                self.warn_once(
                    format!("{entity}:plan"),
                    format!("failed to resolve {entity}: {error}"),
                );
                continue;
            }

            let Some(state) = self.entities.get(&entity) else {
                continue;
            };
            if state.meta.components.is_empty() {
                continue;
            }
            let static_ = state.meta.static_;
            let Some(plan) = state.plan.as_ref() else {
                continue;
            };

            match protocol::decode_planned(plan, &state.columns) {
                Ok((batches, warnings)) => {
                    for warning in warnings {
                        self.warn_once(
                            format!("{entity}:wire:{warning}"),
                            format!("{entity}: {warning}"),
                        );
                    }
                    if batches.is_empty() {
                        continue;
                    }
                    let target = match &self.args.entity_path_prefix {
                        Some(prefix) => EntityPath::from(prefix.as_str()).join(&entity),
                        None => entity.clone(),
                    };
                    // `log_serialized_batches` adds the SDK's wall-clock `log_time`.
                    stream.set_time("robot_time", robot_cell);
                    if let Err(error) = stream.log_serialized_batches(target, static_, batches) {
                        self.warn_once(
                            format!("{entity}:log"),
                            format!("failed to log {entity}: {error}"),
                        );
                    }
                }
                Err(error) => {
                    self.warn_once(
                        format!("{entity}:decode"),
                        format!("failed to decode {entity}: {error}"),
                    );
                }
            }
        }

        if !self.native_updates.is_empty() {
            let timestamp = Timestamp(u64::try_from(robot_ns).unwrap_or(0) / 1_000);
            for update in self.native_updates.drain(..) {
                if let Err(error) = native::ingest(
                    &mut self.native,
                    update.key.clone(),
                    timestamp,
                    &update.ty,
                    &update.value,
                ) {
                    re_log::debug!("failed to ingest a NetworkTables value: {error}");
                }
            }

            let mut timepoint = TimePoint::default();
            timepoint.insert(Timeline::log_time(), TimeCell::timestamp_now());
            let options = ConversionOptions {
                timepoint: Some(timepoint),
                entity_path_prefix: self
                    .args
                    .entity_path_prefix
                    .as_deref()
                    .map(EntityPath::from),
                ..ConversionOptions::default()
            };
            let chunks = conv::log_changes_to_chunks_with_options(
                Timeline::new_duration("robot_time"),
                &mut self.native,
                &options,
            );
            for chunk in chunks {
                stream.log_chunk(chunk);
            }
        }

        if now.duration_since(self.last_prune) >= Duration::from_secs(10) {
            self.last_prune = now;
            let micros = u64::try_from(robot_ns).unwrap_or(0) / 1_000;
            let cutoff = Timestamp(
                micros.saturating_sub(NATIVE_HISTORY.as_secs().saturating_mul(1_000_000)),
            );
            self.native.prune_older_than(cutoff);
        }
    }
}

/// Converts one `/Rerun` value topic into a column.
///
/// Empty arrays are valid data (an archetype field with an empty list); the msgpack value alone
/// cannot tell us the element type, so use the announced topic type for that case.
fn column_from_topic(ty: &DataType, value: &rmpv::Value) -> Option<protocol::NtValue> {
    if matches!(value, rmpv::Value::Array(values) if values.is_empty()) {
        return match ty {
            DataType::BooleanArray => Some(protocol::NtValue::Booleans(Vec::new())),
            DataType::DoubleArray | DataType::FloatArray => {
                Some(protocol::NtValue::Doubles(Vec::new()))
            }
            DataType::IntArray => Some(protocol::NtValue::Longs(Vec::new())),
            DataType::StringArray => Some(protocol::NtValue::Strings(Vec::new())),
            DataType::Raw => Some(protocol::NtValue::Raw(Vec::new())),
            _ => None,
        };
    }
    protocol::NtValue::from_msgpack(value)
}

fn duration_nanos(duration: Duration) -> i64 {
    i64::try_from(duration.as_nanos()).unwrap_or(i64::MAX)
}

/// `double` means seconds and `int` means microseconds, matching WPILib's common conventions.
fn robot_time_from_value(value: &rmpv::Value) -> Option<i64> {
    match value {
        rmpv::Value::F64(seconds) => Some((seconds * 1e9) as i64),
        rmpv::Value::F32(seconds) => Some((f64::from(*seconds) * 1e9) as i64),
        rmpv::Value::Integer(micros) => micros.as_i64().map(|micros| micros.saturating_mul(1_000)),
        _ => None,
    }
}

#[cfg(test)]
mod tests {
    use super::{DEBOUNCE, FlushQueue, MAX_DEBOUNCE, robot_time_from_value};
    use re_log_types::EntityPath;
    use std::time::{Duration, Instant};

    #[test]
    fn debounces_until_the_batch_is_quiet() {
        let mut queue = FlushQueue::new();
        let start = Instant::now();
        queue.touch(EntityPath::from("a"), start);
        queue.touch(EntityPath::from("b"), start + Duration::from_millis(1));

        assert!(queue.take_due(start + Duration::from_millis(1)).is_empty());

        let due = queue.take_due(start + Duration::from_millis(1) + DEBOUNCE);
        assert_eq!(due.len(), 2);
        assert!(queue.deadline().is_none());
        assert!(
            queue
                .take_due(start + Duration::from_millis(100))
                .is_empty()
        );
    }

    #[test]
    fn a_new_update_moves_the_deadline() {
        let mut queue = FlushQueue::new();
        let start = Instant::now();
        queue.touch(EntityPath::from("a"), start);
        queue.touch(EntityPath::from("a"), start + Duration::from_millis(2));
        assert!(queue.take_due(start + Duration::from_millis(3)).is_empty());
        assert_eq!(
            queue
                .take_due(start + Duration::from_millis(2) + DEBOUNCE)
                .len(),
            1
        );
    }

    #[test]
    fn plain_topics_schedule_a_flush_without_an_entity() {
        let mut queue = FlushQueue::new();
        let start = Instant::now();
        queue.schedule(start);
        assert!(queue.take_due(start).is_empty());
        assert!(queue.take_due(start + DEBOUNCE).is_empty());
        assert!(queue.deadline().is_none());
        // Even with no entity pending, the caller still drains native updates because the
        // deadline fired; `take_due` returning an empty list is the signal for that.
    }

    #[test]
    fn continuous_updates_still_flush_within_the_maximum_wait() {
        let mut queue = FlushQueue::new();
        let start = Instant::now();
        // A touch every millisecond would keep pushing a plain debounce deadline past 10 ms.
        for step in 0..10 {
            queue.touch(EntityPath::from("a"), start + Duration::from_millis(step));
        }
        assert!((0..10).any(|ms| MAX_DEBOUNCE >= Duration::from_millis(ms)));
        assert!(
            queue.take_due(start + Duration::from_millis(9)).is_empty(),
            "the capped deadline has not been reached yet"
        );
        assert_eq!(
            queue.take_due(start + MAX_DEBOUNCE).len(),
            1,
            "the cap forces a flush instead of starving"
        );
        assert!(queue.deadline().is_none());
    }

    #[test]
    fn force_flushes_everything() {
        let mut queue = FlushQueue::new();
        let start = Instant::now();
        queue.touch(EntityPath::from("a"), start);
        assert_eq!(queue.take_all().len(), 1);
    }

    #[test]
    fn empty_arrays_keep_their_element_type() {
        let empty = rmpv::Value::Array(Vec::new());
        assert_eq!(
            super::column_from_topic(&nt_client::data::DataType::IntArray, &empty),
            Some(super::protocol::NtValue::Longs(Vec::new()))
        );
        assert_eq!(
            super::column_from_topic(&nt_client::data::DataType::StringArray, &empty),
            Some(super::protocol::NtValue::Strings(Vec::new()))
        );
        assert_eq!(
            super::column_from_topic(&nt_client::data::DataType::DoubleArray, &empty),
            Some(super::protocol::NtValue::Doubles(Vec::new()))
        );
        assert!(super::column_from_topic(&nt_client::data::DataType::String, &empty).is_none());
    }

    #[test]
    fn robot_time_accepts_seconds_and_microseconds() {
        assert_eq!(
            robot_time_from_value(&rmpv::Value::F64(1.5)),
            Some(1_500_000_000)
        );
        assert_eq!(
            robot_time_from_value(&rmpv::Value::Integer(2_000_000.into())),
            Some(2_000_000_000)
        );
        assert_eq!(robot_time_from_value(&rmpv::Value::Boolean(true)), None);
    }
}
