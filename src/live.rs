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
use std::sync::atomic::{AtomicBool, AtomicI64, Ordering};
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
use crate::first;
use crate::log::{EntryLog, Timestamp};
use crate::mjpeg;
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
    /// The latest `/Firstrun/layout` declaration, and whether it still needs drawing.
    layout: Option<first::Layout>,
    layout_pending: bool,
    /// Robots by pose topic, so every pose update is a map lookup.
    pose_robots: HashMap<String, first::Robot>,
    pending_poses: Vec<(first::Robot, first::Pose)>,
    /// Robots by swerve-states topic.
    swerve_robots: HashMap<String, first::Robot>,
    pending_swerve: Vec<(first::Robot, Vec<first::SwerveModuleState>)>,
    /// Path names by the topic their poses arrive on.
    path_names: HashMap<String, String>,
    pending_paths: Vec<(String, Vec<first::Pose>)>,
    /// One MJPEG reader per camera stream in the declaration.
    streams: HashMap<String, mjpeg::CameraVideo>,
    /// The robot time camera frames are logged at, shared with the reader threads.
    robot_ns: Arc<AtomicI64>,
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
            layout: None,
            layout_pending: false,
            pose_robots: HashMap::new(),
            pending_poses: Vec::new(),
            swerve_robots: HashMap::new(),
            pending_swerve: Vec::new(),
            path_names: HashMap::new(),
            pending_paths: Vec::new(),
            streams: HashMap::new(),
            robot_ns: Arc::new(AtomicI64::new(0)),
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

    /// Shares the current robot time with the camera reader threads.
    fn publish_robot_time(&self) {
        self.robot_ns.store(self.robot_time_ns(), Ordering::Relaxed);
    }

    /// Starts one camera per declared stream, replacing any from a previous layout.
    ///
    /// Each camera reads the MJPEG URL over its own socket and logs every JPEG it parses on the
    /// camera's `image` entity. Frames are logged as soon as they arrive, with the robot time the
    /// bridge last saw. `Drop` stops the cameras when the layout changes or the bridge exits.
    fn start_streams(&mut self, stream: &RecordingStream, prefix: Option<&EntityPath>) {
        self.streams.clear();
        let Some(layout) = self.layout.clone() else {
            return;
        };
        for (url, (team, camera)) in layout.cameras_by_stream() {
            let robot_ns = Arc::clone(&self.robot_ns);
            let last_frame_ns = Arc::new(AtomicI64::new(0));

            let video_recording = stream.clone();
            let video_robot_ns = Arc::clone(&robot_ns);
            let video_last_ns = Arc::clone(&last_frame_ns);
            let video_prefix = prefix.cloned();
            let video_camera = camera.clone();

            let spawned = mjpeg::CameraVideo::spawn(url.clone(), move |frame| {
                let time = frame_time(&video_robot_ns, &video_last_ns);
                if let Err(error) = first::log_camera_frame(
                    &video_recording,
                    team,
                    &video_camera,
                    &frame.data,
                    time,
                    video_prefix.as_ref(),
                ) {
                    re_log::debug_once!(
                        "could not log a frame from camera {}: {error}",
                        video_camera.name
                    );
                }
            });
            match spawned {
                Ok(camera_video) => {
                    re_log::info!("recording the {} camera stream at {url}", camera.name);
                    self.streams.insert(url, camera_video);
                }
                Err(error) => re_log::warn!(
                    "could not start the {} camera stream at {url}: {error:#}",
                    camera.name
                ),
            }
        }
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
            self.publish_robot_time();
        }
        let name = topic.name();

        if self.args.robot_time_topic.as_deref() == Some(name) {
            self.robot_time_override_ns = robot_time_from_value(value);
            self.publish_robot_time();
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

        if name == first::TOPIC {
            match value.as_str().map(first::Layout::parse) {
                Some(Ok(layout)) => {
                    // Robot code may republish the same declaration every loop; only redraw
                    // when the declaration actually changed.
                    if self.layout.as_ref() != Some(&layout) {
                        re_log::info!("drawing FIRST data from the declaration on {name}");
                        self.pose_robots = layout.robots_by_pose_topic();
                        self.swerve_robots = layout.robots_by_swerve_topic();
                        self.path_names = layout.paths_by_topic();
                        self.layout = Some(layout);
                        self.layout_pending = true;
                        self.queue.schedule(now);
                    }
                }
                Some(Err(error)) => self.warn_once(
                    format!("{name}:parse"),
                    format!("ignoring the FIRST declaration on {name}: {error:#}"),
                ),
                None => {}
            }
            return;
        }

        if let Some(robot) = self.swerve_robots.get(name) {
            match first::swerve_states_from_nt(topic.r#type(), value) {
                Some(states) => {
                    self.pending_swerve.push((robot.clone(), states));
                    self.queue.schedule(now);
                }
                None => self.warn_once(
                    format!("{name}:swerve"),
                    format!("ignoring swerve states on {name} that are not SwerveModuleState[]"),
                ),
            }
            return;
        }

        if let Some(robot) = self.pose_robots.get(name) {
            match first::Pose::from_nt(topic.r#type(), value) {
                Some(pose) => {
                    self.pending_poses.push((robot.clone(), pose));
                    self.queue.schedule(now);
                }
                None => self.warn_once(
                    format!("{name}:pose"),
                    format!("ignoring a pose on {name} that is not a Pose2d/Pose3d"),
                ),
            }
            return;
        }

        if let Some(path) = self.path_names.get(name) {
            match first::poses_from_nt(topic.r#type(), value) {
                Some(poses) => {
                    self.pending_paths.push((path.clone(), poses));
                    self.queue.schedule(now);
                }
                None => self.warn_once(
                    format!("{name}:path"),
                    format!("ignoring path poses on {name} that are not Pose2d[]/Translation2d[]"),
                ),
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
        let layout_pending = std::mem::take(&mut self.layout_pending);
        if entities.is_empty()
            && self.native_updates.is_empty()
            && self.pending_poses.is_empty()
            && self.pending_swerve.is_empty()
            && self.pending_paths.is_empty()
            && !layout_pending
        {
            return;
        }

        let prefix = self
            .args
            .entity_path_prefix
            .as_deref()
            .map(EntityPath::from);
        if layout_pending {
            let models = first::Models {
                field: self.args.field_model.as_deref(),
                robot: self.args.robot_model.as_deref(),
            };
            let error = self.layout.as_ref().and_then(|layout| {
                let error = layout
                    .log_static_with_models(stream, prefix.as_ref(), models)
                    .err();
                if let Err(error) = first::log_blueprint(stream, layout, prefix.as_ref()) {
                    re_log::debug!("could not send the camera layout: {error:#}");
                }
                error
            });
            if let Some(error) = error {
                self.warn_once(
                    "first:static".to_owned(),
                    format!("could not draw the FIRST field: {error:#}"),
                );
            }
            self.start_streams(stream, prefix.as_ref());
        }

        let robot_ns = self.robot_time_ns();
        self.robot_ns.store(robot_ns, Ordering::Relaxed);
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

        if !self.pending_swerve.is_empty() {
            for (robot, states) in std::mem::take(&mut self.pending_swerve) {
                if let Err(error) =
                    first::log_swerve(stream, &robot, &states, robot_cell, prefix.as_ref())
                {
                    self.warn_once(
                        format!("first:swerve:{}", robot.team),
                        format!(
                            "could not draw team {}'s swerve states: {error:#}",
                            robot.team
                        ),
                    );
                }
            }
        }

        if !self.pending_paths.is_empty() {
            for (name, poses) in std::mem::take(&mut self.pending_paths) {
                if let Err(error) =
                    first::log_path(stream, &name, &poses, robot_cell, prefix.as_ref())
                {
                    self.warn_once(
                        format!("first:path:{name}"),
                        format!("could not draw path {name}: {error:#}"),
                    );
                }
            }
        }

        if !self.pending_poses.is_empty() {
            for (robot, pose) in std::mem::take(&mut self.pending_poses) {
                if let Err(error) =
                    first::log_pose(stream, &robot, pose, robot_cell, prefix.as_ref())
                {
                    self.warn_once(
                        format!("first:pose:{}", robot.team),
                        format!("could not draw team {}'s pose: {error:#}", robot.team),
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

/// A strictly increasing presentation time for camera frames.
///
/// The bridge's robot time comes from NetworkTables and can repeat or step back between frames;
/// Rerun discards a sample that belongs on a time it has already passed, so each frame is clamped
/// past the previous one.
fn frame_time(robot_ns: &AtomicI64, last_frame_ns: &AtomicI64) -> TimeCell {
    let target = robot_ns.load(Ordering::Relaxed).max(0);
    let mut previous = last_frame_ns.load(Ordering::Relaxed);
    let nanos = loop {
        let next = target.max(previous.saturating_add(1));
        match last_frame_ns.compare_exchange_weak(
            previous,
            next,
            Ordering::Relaxed,
            Ordering::Relaxed,
        ) {
            Ok(_) => break next,
            Err(actual) => previous = actual,
        }
    };
    TimeCell::from_duration_nanos(NonMinI64::new(nanos).unwrap_or_default())
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
    use super::{DEBOUNCE, FlushQueue, LiveState, MAX_DEBOUNCE, robot_time_from_value};
    use crate::cli::{LiveArgs, LiveSink};
    use crate::first::Layout;
    use re_log_types::EntityPath;
    use std::time::{Duration, Instant};

    /// The models a `--field-model`/`--robot-model` pair names replace the declaration's.
    #[test]
    fn custom_models_are_drawn_on_the_first_lane() {
        let dir = std::env::temp_dir().join(format!("firstrun-live-models-{}", std::process::id()));
        std::fs::create_dir_all(&dir).unwrap();
        let field = dir.join("field.glb");
        let robot = dir.join("robot.glb");
        std::fs::write(&field, b"glTF field").unwrap();
        std::fs::write(&robot, b"glTF robot").unwrap();

        let args = LiveArgs {
            team: None,
            nt_addr: None,
            robot_time_topic: None,
            sink: LiveSink::Stdout,
            quiet: true,
            entity_path_prefix: None,
            field_model: Some(field.clone()),
            robot_model: Some(robot.clone()),
        };
        let (stream, storage) = re_sdk::RecordingStreamBuilder::new("live_models_test")
            .memory()
            .unwrap();
        let mut state = LiveState::new(args);
        state.layout = Some(
            Layout::parse(
                r#"{"version":1,"field":{"carpet":false,"lines":[]},
                "robots":[{"team":254,"pose_topic":"/Pose"}]}"#,
            )
            .unwrap(),
        );
        state.layout_pending = true;
        state.flush(&stream, Instant::now(), true);
        let chunks = storage
            .take()
            .into_iter()
            .filter_map(|message| match message {
                re_log_msg::LogMsg::ArrowMsg(_, arrow) => {
                    re_chunk::Chunk::from_arrow_msg(&arrow).ok()
                }
                _ => None,
            })
            .collect::<Vec<_>>();
        let _ = std::fs::remove_dir_all(&dir);

        for entity in ["/first/field/model", "/first/robots/254/model"] {
            assert!(
                chunks.iter().any(|chunk| {
                    chunk.entity_path().to_string() == entity
                        && chunk.component_descriptors().any(|descriptor| {
                            descriptor
                                .archetype
                                .as_ref()
                                .is_some_and(|name| name == "rerun.archetypes.Asset3D")
                        })
                }),
                "{entity} should carry the custom model"
            );
        }
    }

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
    fn frame_times_never_go_backwards() {
        use std::sync::atomic::{AtomicI64, Ordering};

        let robot_ns = AtomicI64::new(100);
        let last_frame_ns = AtomicI64::new(0);
        let at = |nanos: i64| {
            re_log_types::TimeCell::from_duration_nanos(
                re_log_types::NonMinI64::new(nanos).unwrap(),
            )
        };

        assert_eq!(super::frame_time(&robot_ns, &last_frame_ns), at(100));
        // Two frames can share a robot time; the second still has to move forward.
        assert_eq!(super::frame_time(&robot_ns, &last_frame_ns), at(101));
        // A reconnect can make the robot clock step back; frames may not.
        robot_ns.store(50, Ordering::Relaxed);
        assert_eq!(super::frame_time(&robot_ns, &last_frame_ns), at(102));
        robot_ns.store(1_000, Ordering::Relaxed);
        assert_eq!(super::frame_time(&robot_ns, &last_frame_ns), at(1_000));
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
