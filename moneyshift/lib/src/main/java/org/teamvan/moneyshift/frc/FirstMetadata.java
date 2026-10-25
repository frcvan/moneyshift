package org.teamvan.moneyshift.frc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.teamvan.moneyshift.NtRecorder;

/**
 * What the bridge needs to draw FIRST data around the robot.
 *
 * <p>The declaration answers two questions: which plain NetworkTables topics carry the robot's
 * pose, swerve states and planned paths, and what the field and cameras look like. Robot code
 * publishes it once at {@value #TOPIC}; the {@code rerun-importer-firstrun --nt} bridge reads it
 * and injects the carpet, field lines, tags, robot bounding boxes, swerve module vectors, camera
 * frustums and paths into the Rerun recording. Nothing here is Rerun protocol data, so it works
 * even when robot code publishes no {@code /Rerun} topics at all.
 *
 * <pre>{@code
 * FirstMetadata.builder()
 *         .robot(254, "/AdvantageKit/RealOutputs/Drive/Pose2d")
 *         .swerve(254, "/AdvantageKit/RealOutputs/SwerveStates/Measured")
 *         .camera(254, "front", 0.30, 0.0, 0.50, 0.0, -10.0, 0.0, 70.0,
 *                 "/Camera/front/stream")
 *         .path("auto", "/AdvantageKit/RealOutputs/PathPlanner/ActivePath")
 *         .build()
 *         .publish(NtRecorder.getInstance());
 * }</pre>
 *
 * <p>Pose topics may publish {@code struct:Pose2d}, {@code struct:Pose3d}, or a {@code double[]}
 * of {@code [x, y, yaw]}. Swerve topics publish {@code struct:SwerveModuleState[]}. Path topics
 * publish {@code struct:Pose2d[]} or {@code struct:Translation2d[]}. A camera may name a
 * {@code raw} topic that publishes one encoded frame per update: H.264/H.265 samples are logged
 * with Rerun's {@code VideoStream} archetype and JPEG/PNG frames with {@code EncodedImage}, on the
 * camera's own entity. The bridge maps each update onto {@code first/...} and its 2D mirror.
 */
public final class FirstMetadata {
    /** The plain NetworkTables topic the bridge reads the declaration from. */
    public static final String TOPIC = "/Firstrun/layout";

    /** Bumped when the JSON shape changes incompatibly. */
    public static final int VERSION = 1;

    /** Half-extents of an FRC robot's frame: 0.9 m square, 0.6 m tall. */
    private static final double[] DEFAULT_HALF_EXTENTS = {0.45, 0.45, 0.30};

    private final FieldGeometry field;
    private final List<Robot> robots;
    private final String fieldModel;
    private final Map<Integer, Swerve> swerve;
    private final Map<Integer, List<Camera>> cameras;
    private final List<Path> paths;

    private FirstMetadata(Builder builder) {
        this.field = builder.field;
        this.robots = List.copyOf(builder.robots);
        this.fieldModel = builder.fieldModel;
        this.swerve = Map.copyOf(builder.swerve);
        Map<Integer, List<Camera>> mounted = new LinkedHashMap<>();
        for (Map.Entry<Integer, List<Camera>> entry : builder.cameras.entrySet()) {
            mounted.put(entry.getKey(), List.copyOf(entry.getValue()));
        }
        this.cameras = Map.copyOf(mounted);
        this.paths = List.copyOf(builder.paths);
    }

    /** A declaration with the standard field and no robots, cameras or paths. */
    public static Builder builder() {
        return new Builder();
    }

    /** The field geometry the bridge draws. */
    public FieldGeometry field() {
        return field;
    }

    /** The declared robots, in declaration order. */
    public List<Robot> robots() {
        return robots;
    }

    /** The JSON the bridge parses; also handy in tests and logs. */
    public String json() {
        StringBuilder json = new StringBuilder(512);
        json.append("{\"version\":").append(VERSION).append(",\"field\":{");
        json.append("\"carpet\":").append(field.carpet());
        json.append(",\"lines\":[");
        for (int i = 0; i < field.lines().size(); i++) {
            if (i > 0) {
                json.append(',');
            }
            FieldGeometry.Line line = field.lines().get(i);
            json.append("{\"points\":[");
            for (int j = 0; j < line.points().size(); j++) {
                if (j > 0) {
                    json.append(',');
                }
                FieldGeometry.Point point = line.points().get(j);
                json.append('[').append(number(point.x())).append(',').append(number(point.y()));
                json.append(']');
            }
            json.append("],\"closed\":").append(line.closed()).append('}');
        }
        json.append("],\"tags\":[");
        for (int i = 0; i < field.tags().size(); i++) {
            if (i > 0) {
                json.append(',');
            }
            FieldGeometry.Tag tag = field.tags().get(i);
            json.append("{\"id\":").append(tag.id());
            json.append(",\"x\":").append(number(tag.x()));
            json.append(",\"y\":").append(number(tag.y()));
            json.append(",\"z\":").append(number(tag.z()));
            json.append(",\"yaw\":").append(number(tag.yawRadians()));
            json.append(",\"size\":").append(number(tag.sizeMeters())).append('}');
        }
        json.append(']');
        if (fieldModel != null) {
            json.append(",\"model\":").append(quoted(fieldModel));
        }
        json.append("},\"robots\":[");
        for (int i = 0; i < robots.size(); i++) {
            if (i > 0) {
                json.append(',');
            }
            Robot robot = robots.get(i);
            json.append("{\"team\":").append(robot.team());
            json.append(",\"pose_topic\":").append(quoted(robot.poseTopic()));
            json.append(",\"half_extents\":[");
            double[] extents = robot.halfExtents();
            for (int j = 0; j < extents.length; j++) {
                if (j > 0) {
                    json.append(',');
                }
                json.append(number(extents[j]));
            }
            json.append(']');
            Swerve states = swerve.get(robot.team());
            if (states != null) {
                json.append(",\"swerve\":{\"states_topic\":").append(quoted(states.statesTopic()));
                if (!states.moduleOffsets().isEmpty()) {
                    json.append(",\"module_offsets\":[");
                    List<double[]> offsets = states.moduleOffsets();
                    for (int j = 0; j < offsets.size(); j++) {
                        if (j > 0) {
                            json.append(',');
                        }
                        double[] offset = offsets.get(j);
                        json.append('[')
                                .append(number(offset[0]))
                                .append(',')
                                .append(number(offset[1]))
                                .append(']');
                    }
                    json.append(']');
                }
                json.append('}');
            }
            List<Camera> mounted = cameras.getOrDefault(robot.team(), List.of());
            if (!mounted.isEmpty()) {
                json.append(",\"cameras\":[");
                for (int j = 0; j < mounted.size(); j++) {
                    if (j > 0) {
                        json.append(',');
                    }
                    Camera camera = mounted.get(j);
                    json.append("{\"name\":").append(quoted(camera.name()));
                    json.append(",\"x\":").append(number(camera.x()));
                    json.append(",\"y\":").append(number(camera.y()));
                    json.append(",\"z\":").append(number(camera.z()));
                    json.append(",\"roll\":").append(number(camera.rollDegrees()));
                    json.append(",\"pitch\":").append(number(camera.pitchDegrees()));
                    json.append(",\"yaw\":").append(number(camera.yawDegrees()));
                    json.append(",\"fov\":").append(number(camera.horizontalFovDegrees()));
                    if (camera.videoTopic() != null) {
                        json.append(",\"video_topic\":").append(quoted(camera.videoTopic()));
                    }
                    json.append('}');
                }
                json.append(']');
            }
            json.append('}');
        }
        json.append(']');
        if (!paths.isEmpty()) {
            json.append(",\"paths\":[");
            for (int i = 0; i < paths.size(); i++) {
                if (i > 0) {
                    json.append(',');
                }
                Path path = paths.get(i);
                json.append("{\"name\":").append(quoted(path.name()));
                json.append(",\"topic\":").append(quoted(path.topic())).append('}');
            }
            json.append(']');
        }
        json.append('}');
        return json.toString();
    }

    /**
     * Publishes the declaration at {@value #TOPIC}.
     *
     * <p>Call it after {@link NtRecorder#getInstance()} and before or after logging; the bridge
     * picks it up whenever it appears, and the server keeps the last value for late connections.
     */
    public void publish(NtRecorder recorder) {
        Objects.requireNonNull(recorder, "recorder");
        recorder.instance().getEntry(TOPIC).setString(json());
    }

    /** One robot the bridge draws: its pose source and its bounding box. */
    public record Robot(int team, String poseTopic, double[] halfExtents) {
        public Robot {
            if (team < 1) {
                throw new IllegalArgumentException("team numbers are positive, got " + team);
            }
            requireTopic(poseTopic, "poseTopic");
            Objects.requireNonNull(halfExtents, "halfExtents");
            if (halfExtents.length != 3) {
                throw new IllegalArgumentException(
                        "halfExtents needs three values (x, y, z), got " + halfExtents.length);
            }
            halfExtents = halfExtents.clone();
            for (double extent : halfExtents) {
                if (!(extent > 0.0) || !Double.isFinite(extent)) {
                    throw new IllegalArgumentException(
                            "half extents must be positive and finite, got " + extent);
                }
            }
        }

        /** A copy of the robot's half-extents, so callers cannot mutate the declaration. */
        @Override
        public double[] halfExtents() {
            return halfExtents.clone();
        }
    }

    /**
     * A swerve drive source.
     *
     * <p>{@code module_offsets} are robot-relative module centers in meters, in the order the
     * states topic publishes them (WPILib's FL, FR, BL, BR by default: {@code +x} forward,
     * {@code +y} left). An empty list lets the bridge derive them from the robot's half-extents.
     */
    public record Swerve(String statesTopic, List<double[]> moduleOffsets) {
        public Swerve {
            requireTopic(statesTopic, "statesTopic");
            List<double[]> copied = new ArrayList<>();
            for (double[] offset : moduleOffsets) {
                if (offset.length != 2
                        || !Double.isFinite(offset[0])
                        || !Double.isFinite(offset[1])) {
                    throw new IllegalArgumentException(
                            "module offsets are [x, y] pairs of finite meters");
                }
                copied.add(offset.clone());
            }
            if (!copied.isEmpty() && copied.size() != 4) {
                throw new IllegalArgumentException(
                        "declare four module offsets, or none to derive them, got "
                                + copied.size());
            }
            moduleOffsets = List.copyOf(copied);
        }

        /** Copies of the module offsets, so callers cannot mutate the declaration. */
        @Override
        public List<double[]> moduleOffsets() {
            List<double[]> copies = new ArrayList<>(moduleOffsets.size());
            for (double[] offset : moduleOffsets) {
                copies.add(offset.clone());
            }
            return List.copyOf(copies);
        }
    }

    /**
     * A camera mounted on the robot, in the robot frame.
     *
     * <p>Angles are degrees; {@code roll}/{@code pitch}/{@code yaw} rotate the camera out of the
     * robot frame and {@code horizontalFovDegrees} sizes the drawn frustum.
     *
     * <p>{@code videoTopic} optionally names a plain {@code raw} NetworkTables topic that
     * publishes one encoded frame per update. The bridge logs those frames on the camera entity
     * with Rerun's {@code VideoStream} archetype (H.264/H.265), so a recording scrubs through the
     * camera footage next to the 3D field. JPEG or PNG frames are logged as {@code EncodedImage}
     * instead, which is what MJPEG camera topics carry.
     */
    public record Camera(
            String name,
            double x,
            double y,
            double z,
            double rollDegrees,
            double pitchDegrees,
            double yawDegrees,
            double horizontalFovDegrees,
            String videoTopic) {
        public Camera {
            requireSegment(name, "camera name");
            requireFinite(x, "x");
            requireFinite(y, "y");
            requireFinite(z, "z");
            requireFinite(rollDegrees, "rollDegrees");
            requireFinite(pitchDegrees, "pitchDegrees");
            requireFinite(yawDegrees, "yawDegrees");
            requireFinite(horizontalFovDegrees, "horizontalFovDegrees");
            if (horizontalFovDegrees <= 0.0 || horizontalFovDegrees >= 180.0) {
                throw new IllegalArgumentException(
                        "horizontalFovDegrees must be in (0, 180), got " + horizontalFovDegrees);
            }
            if (videoTopic != null) {
                requireTopic(videoTopic, "videoTopic");
            }
        }
    }

    /** A planned path published as a {@code Pose2d[]} or {@code Translation2d[]} topic. */
    public record Path(String name, String topic) {
        public Path {
            requireSegment(name, "path name");
            requireTopic(topic, "path topic");
        }
    }

    /** Fluent construction of a {@link FirstMetadata}. */
    public static final class Builder {
        private FieldGeometry field = FieldGeometry.standard();
        private final List<Robot> robots = new ArrayList<>();
        private String fieldModel;
        private final Map<Integer, Swerve> swerve = new LinkedHashMap<>();
        private final Map<Integer, List<Camera>> cameras = new LinkedHashMap<>();
        private final List<Path> paths = new ArrayList<>();

        private Builder() {}

        /** Replaces the standard field with caller geometry; the size is fixed. */
        public Builder field(FieldGeometry field) {
            this.field = Objects.requireNonNull(field, "field");
            return this;
        }

        /** Declares a robot with the default 0.9 m by 0.9 m footprint and 0.6 m height. */
        public Builder robot(int team, String poseTopic) {
            return robot(team, poseTopic, DEFAULT_HALF_EXTENTS);
        }

        /** Declares a robot with explicit half-extents in meters. */
        public Builder robot(int team, String poseTopic, double... halfExtents) {
            Robot robot = new Robot(team, poseTopic, halfExtents);
            for (Robot existing : robots) {
                if (existing.team() == team) {
                    throw new IllegalArgumentException("team " + team + " is declared twice");
                }
            }
            robots.add(robot);
            return this;
        }

        /** A glTF/GLB file the bridge loads as the field's 3D model. */
        public Builder fieldModel(String path) {
            this.fieldModel = Objects.requireNonNull(path, "path");
            return this;
        }

        /** Declares the SwerveModuleState[] topic for {@code team}. */
        public Builder swerve(int team, String statesTopic) {
            return swerve(team, statesTopic, List.of());
        }

        /**
         * Declares the swerve states topic and the robot-relative module centers, in publishing
         * order.
         */
        public Builder swerve(int team, String statesTopic, double[]... moduleOffsets) {
            List<double[]> offsets = new ArrayList<>(moduleOffsets.length);
            for (double[] offset : moduleOffsets) {
                offsets.add(offset);
            }
            return swerve(team, statesTopic, offsets);
        }

        private Builder swerve(int team, String statesTopic, List<double[]> moduleOffsets) {
            requireTeam(team);
            if (swerve.putIfAbsent(team, new Swerve(statesTopic, moduleOffsets)) != null) {
                throw new IllegalArgumentException("team " + team + " already has swerve states");
            }
            return this;
        }

        /** Declares a camera mounted on {@code team}, in the robot frame; angles are degrees. */
        public Builder camera(
                int team,
                String name,
                double x,
                double y,
                double z,
                double rollDegrees,
                double pitchDegrees,
                double yawDegrees,
                double horizontalFovDegrees) {
            return camera(
                    team,
                    name,
                    x,
                    y,
                    z,
                    rollDegrees,
                    pitchDegrees,
                    yawDegrees,
                    horizontalFovDegrees,
                    null);
        }

        /**
         * Declares a camera and the {@code raw} topic its encoded frames arrive on.
         *
         * <p>The bridge logs the frames with Rerun's {@code VideoStream} archetype (H.264/H.265)
         * or {@code EncodedImage} (JPEG/PNG), on the camera's entity.
         */
        public Builder camera(
                int team,
                String name,
                double x,
                double y,
                double z,
                double rollDegrees,
                double pitchDegrees,
                double yawDegrees,
                double horizontalFovDegrees,
                String videoTopic) {
            requireTeam(team);
            List<Camera> mounted = cameras.computeIfAbsent(team, ignored -> new ArrayList<>());
            Camera camera =
                    new Camera(
                            name,
                            x,
                            y,
                            z,
                            rollDegrees,
                            pitchDegrees,
                            yawDegrees,
                            horizontalFovDegrees,
                            videoTopic);
            for (Camera existing : mounted) {
                if (existing.name().equals(name)) {
                    throw new IllegalArgumentException(
                            "team " + team + " already has a camera named \"" + name + "\"");
                }
            }
            mounted.add(camera);
            return this;
        }

        /** Declares a planned path, published as poses on {@code topic}. */
        public Builder path(String name, String topic) {
            for (Path existing : paths) {
                if (existing.name().equals(name)) {
                    throw new IllegalArgumentException("path \"" + name + "\" is declared twice");
                }
            }
            paths.add(new Path(name, topic));
            return this;
        }

        public FirstMetadata build() {
            for (int team : swerve.keySet()) {
                requireRobot(team);
            }
            for (int team : cameras.keySet()) {
                requireRobot(team);
            }
            return new FirstMetadata(this);
        }

        private void requireTeam(int team) {
            if (team < 1) {
                throw new IllegalArgumentException("team numbers are positive, got " + team);
            }
        }

        private void requireRobot(int team) {
            for (Robot robot : robots) {
                if (robot.team() == team) {
                    return;
                }
            }
            throw new IllegalArgumentException(
                    "team " + team + " has swerve or camera data but is not declared");
        }
    }

    private static String requireTopic(String topic, String name) {
        Objects.requireNonNull(topic, name);
        if (!topic.startsWith("/") || topic.length() < 2) {
            throw new IllegalArgumentException(
                    name + " must be an absolute NetworkTables topic, got \"" + topic + "\"");
        }
        return topic;
    }

    private static void requireSegment(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank() || value.contains("/")) {
            throw new IllegalArgumentException(
                    name + " must be a single non-empty path segment, got \"" + value + "\"");
        }
    }

    private static void requireFinite(double value, String name) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite, got " + value);
        }
    }

    /** JSON number formatting that never emits NaN or infinities. */
    private static String number(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalStateException("refusing to serialize non-finite value " + value);
        }
        return Double.toString(value);
    }

    private static String quoted(String value) {
        StringBuilder quoted = new StringBuilder(value.length() + 2);
        quoted.append('"');
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '"' -> quoted.append("\\\"");
                case '\\' -> quoted.append("\\\\");
                case '\n' -> quoted.append("\\n");
                case '\r' -> quoted.append("\\r");
                case '\t' -> quoted.append("\\t");
                default -> {
                    if (c < 0x20) {
                        quoted.append(String.format("\\u%04x", (int) c));
                    } else {
                        quoted.append(c);
                    }
                }
            }
        }
        return quoted.append('"').toString();
    }
}
