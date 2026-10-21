package org.teamvan.moneyshift.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.teamvan.moneyshift.FileTransport;
import org.teamvan.moneyshift.RecordingStream;
import org.teamvan.moneyshift.RecordingStreamBuilder;
import org.teamvan.moneyshift.Transport;
import org.teamvan.moneyshift.archetypes.AnnotationContext;
import org.teamvan.moneyshift.archetypes.Boxes3D;
import org.teamvan.moneyshift.archetypes.LineStrips3D;
import org.teamvan.moneyshift.archetypes.Points3D;
import org.teamvan.moneyshift.archetypes.Scalars;
import org.teamvan.moneyshift.archetypes.SeriesLines;
import org.teamvan.moneyshift.archetypes.TextLog;
import org.teamvan.moneyshift.archetypes.Transform3D;
import org.teamvan.moneyshift.blueprint.archetypes.ViewBlueprint;
import org.teamvan.moneyshift.components.HalfSize3D;
import org.teamvan.moneyshift.components.LineStrip3D;
import org.teamvan.moneyshift.components.Position3D;
import org.teamvan.moneyshift.components.RotationAxisAngle;
import org.teamvan.moneyshift.components.Scale3D;
import org.teamvan.moneyshift.components.TransformRelation;
import org.teamvan.moneyshift.components.Translation3D;
import org.teamvan.moneyshift.encodings.AnnotationInfo;
import org.teamvan.moneyshift.encodings.ClassDescription;
import org.teamvan.moneyshift.encodings.ClassDescriptionMapElem;
import org.teamvan.moneyshift.encodings.Vec3D;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.LogMsg;

/**
 * A small "sensor sweep" simulation that exercises the moneyshift public API.
 *
 * <p>It logs moving points, line strips, boxes, transforms, time series and text logs on three
 * timelines, ships a blueprint alongside the recording, and writes the same kind of data to
 * three different sinks: a shared RRD file, a plain file sink, and an in-memory sink.
 */
public final class MoneyshiftDemo {
    private static final String APP_ID = "moneyshift-example";
    private static final int DEFAULT_FRAMES = 60;
    private static final int POINT_COUNT = 96;
    private static final int SCAN_POINTS = 64;
    private static final float TAU = (float) (Math.PI * 2.0);
    private static final Instant START = Instant.parse("2026-01-01T00:00:00Z");

    private static final long CYAN = 0xFF00E5FFL;
    private static final long MAGENTA = 0xFFFF2D95L;
    private static final long ORANGE = 0xFFFF9F0AL;
    private static final long PURPLE = 0xFFBF5AF2L;
    private static final long GREEN = 0xFF32D74BL;

    private MoneyshiftDemo() {}

    /** Paths and counts produced by {@link #writeAll(Path, int)}. */
    public record Artifacts(
            Path demoRecording,
            Path fileSinkRecording,
            Path memoryRecording,
            int frames,
            int memoryBytes) {}

    public static void main(String[] args) throws Exception {
        Path outDir = Path.of("build", "moneyshift-example");
        String connectEndpoint = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--out" -> outDir = Path.of(requireValue(args, ++i, "--out"));
                case "--connect" -> connectEndpoint = requireValue(args, ++i, "--connect");
                case "--help", "-h" -> {
                    printUsage();
                    return;
                }
                default -> {
                    System.err.println("unknown argument: " + args[i]);
                    printUsage();
                    System.exit(2);
                }
            }
        }

        Artifacts artifacts = writeAll(outDir);
        System.out.printf(
                "wrote %s (%d frames, %d bytes), %s (%d bytes) and %s (%d bytes)%n",
                artifacts.demoRecording(),
                artifacts.frames(),
                Files.size(artifacts.demoRecording()),
                artifacts.fileSinkRecording(),
                Files.size(artifacts.fileSinkRecording()),
                artifacts.memoryRecording(),
                artifacts.memoryBytes());
        System.out.println("open the recording with: rerun " + artifacts.demoRecording());

        if (connectEndpoint != null) {
            streamToViewer(connectEndpoint);
        }
    }

    /**
     * Writes the demo recordings into {@code outDir}.
     *
     * <p>The first file contains both a blueprint store and a recording store, followed by a
     * blueprint activation command. The second uses {@code save(Path)} directly, and the third is
     * produced entirely in memory with {@code memory()} plus {@code toRrdBytes()}.
     */
    public static Artifacts writeAll(Path outDir) throws IOException {
        return writeAll(outDir, DEFAULT_FRAMES);
    }

    public static Artifacts writeAll(Path outDir, int frames) throws IOException {
        Objects.requireNonNull(outDir, "outDir");
        if (frames < 1) {
            throw new IllegalArgumentException("frames must be at least 1");
        }
        Files.createDirectories(outDir);

        Path demoPath = outDir.resolve("demo.rrd");
        Path fileSinkPath = outDir.resolve("recording.rrd");
        Path memoryPath = outDir.resolve("memory.rrd");

        writeBlueprintAndRecording(demoPath, frames);
        writeFileSink(fileSinkPath, Math.min(frames, 12));

        byte[] memoryBytes = writeMemorySink(Math.min(frames, 8));
        Files.write(memoryPath, memoryBytes);

        return new Artifacts(demoPath, fileSinkPath, memoryPath, frames, memoryBytes.length);
    }

    /** A blueprint and a recording in one RRD file, using a custom shared {@link Transport}. */
    private static void writeBlueprintAndRecording(Path path, int frames) {
        SharedFileTransport transport = new SharedFileTransport(path);
        try {
            try (RecordingStream blueprint = RecordingStreamBuilder.newBlueprint(APP_ID)
                    .recordingId("bp_sensor_sweep")
                    .transport(transport)
                    .build()) {
                logBlueprint(blueprint);

                try (RecordingStream recording = RecordingStreamBuilder.newRecording(APP_ID)
                        .recordingId("rec_sensor_sweep")
                        .transport(transport)
                        .build()) {
                    logStaticScene(recording);
                    for (int frame = 0; frame < frames; frame++) {
                        logFrame(recording, frame, frames);
                    }
                    logSimulationFinished(recording, frames);
                    // Ask the viewer to use the blueprint store that precedes this recording.
                    recording.activateBlueprint(blueprint, true, false);
                }
            }
        } finally {
            transport.finish();
        }
    }

    private static void writeFileSink(Path path, int frames) {
        try (RecordingStream recording = RecordingStreamBuilder.newRecording(APP_ID)
                .recordingId("rec_file_sink")
                .save(path)
                .build()) {
            logStaticScene(recording);
            for (int frame = 0; frame < frames; frame++) {
                logFrame(recording, frame, frames);
            }
        }
    }

    private static byte[] writeMemorySink(int frames) {
        try (RecordingStream recording = RecordingStreamBuilder.newRecording(APP_ID)
                .recordingId("rec_memory_sink")
                .memory()
                .build()) {
            logStaticScene(recording);
            for (int frame = 0; frame < frames; frame++) {
                logFrame(recording, frame, frames);
            }
            return recording.toRrdBytes();
        }
    }

    /** Optional live path: stream the same simulation to a running viewer over gRPC. */
    private static void streamToViewer(String endpoint) throws InterruptedException {
        System.out.println("streaming live to " + endpoint + " (Ctrl-C to stop)");
        try (RecordingStream recording = RecordingStreamBuilder.newRecording(APP_ID)
                .recordingId("rec_live")
                .connectGrpc(endpoint)
                .build()) {
            logStaticScene(recording);
            for (int frame = 0; frame < 120; frame++) {
                logFrame(recording, frame, 120);
                Thread.sleep(50);
            }
        }
    }

    private static void logBlueprint(RecordingStream blueprint) {
        // The class identifiers match the viewer's built-in view names.
        blueprint.logStatic(
                "view/0",
                ViewBlueprint.builder()
                        .withClassIdentifier("3D")
                        .withDisplayName("Sensor sweep")
                        .build());
        blueprint.logStatic(
                "view/1",
                ViewBlueprint.builder()
                        .withClassIdentifier("TimeSeries")
                        .withDisplayName("Signals")
                        .build());
        blueprint.logStatic(
                "view/2",
                ViewBlueprint.builder()
                        .withClassIdentifier("TextLog")
                        .withDisplayName("Simulation log")
                        .build());
        blueprint.flushBlocking();
    }

    private static void logStaticScene(RecordingStream recording) {
        recording.logStatic(
                "world/annotations",
                AnnotationContext.builder()
                        .withContext(
                                new ClassDescriptionMapElem(
                                        1,
                                        new ClassDescription(
                                                new AnnotationInfo(1, "sensor return", CYAN),
                                                List.of(),
                                                List.of())),
                                new ClassDescriptionMapElem(
                                        2,
                                        new ClassDescription(
                                                new AnnotationInfo(2, "obstacle", ORANGE),
                                                List.of(),
                                                List.of())))
                        .build());

        recording.logStatic(
                "plots/amplitude",
                SeriesLines.builder()
                        .withNames("amplitude")
                        .withColors(CYAN)
                        .withWidths(2.0f)
                        .build());
        recording.logStatic(
                "plots/phase",
                SeriesLines.builder()
                        .withNames("phase")
                        .withColors(MAGENTA)
                        .withWidths(2.0f)
                        .build());
    }

    private static void logFrame(RecordingStream recording, int frame, int frames) {
        float phase = TAU * frame / frames;

        // Three different timeline types on the same log calls.
        recording.setTimeSequence("frame", frame);
        recording.setTimeDuration("sim_time", Duration.ofMillis(frame * 50L));
        recording.setTimeTimestamp("wall_time", START.plusMillis(frame * 50L));

        // The robot moves in a circle; everything below "world/robot" inherits this transform.
        recording.log(
                "world/robot",
                Transform3D.builder()
                        .withTranslation(Translation3D.of(
                                1.8f * (float) Math.cos(phase),
                                1.8f * (float) Math.sin(phase),
                                0.4f))
                        .withRotationAxisAngle(new RotationAxisAngle(Vec3D.of(0.0f, 0.0f, 1.0f), phase))
                        .withScale(Scale3D.of(1.0f, 1.0f, 1.0f))
                        .withRelation(TransformRelation.ChildFromParent)
                        .build());

        List<Position3D> positions = new ArrayList<>(POINT_COUNT);
        long[] colors = new long[POINT_COUNT];
        for (int i = 0; i < POINT_COUNT; i++) {
            float t = i / (float) (POINT_COUNT - 1);
            float angle = TAU * t;
            float radius = 0.9f + 0.2f * (float) Math.sin(3.0f * angle + phase);
            float z = 0.25f * (float) Math.sin(2.0f * phase + angle);
            positions.add(Position3D.of(radius * (float) Math.cos(angle), radius * (float) Math.sin(angle), z));
            colors[i] = lerpRgba(CYAN, MAGENTA, t);
        }
        recording.log(
                "world/robot/sensor/returns",
                Points3D.builder()
                        .withPositions(positions)
                        .withColors(colors)
                        .withRadii(0.03f)
                        .withClassIds(1)
                        .build());

        List<Vec3D> scan = new ArrayList<>(SCAN_POINTS);
        for (int i = 0; i < SCAN_POINTS; i++) {
            float t = i / (float) (SCAN_POINTS - 1);
            float angle = phase + t * TAU;
            float radius = 0.2f + 1.6f * t;
            scan.add(Vec3D.of(
                    radius * (float) Math.cos(angle),
                    radius * (float) Math.sin(angle),
                    0.5f * t * (float) Math.sin(2.0f * phase)));
        }
        recording.log(
                "world/robot/sensor/scan",
                LineStrips3D.builder()
                        .withStrips(new LineStrip3D(scan))
                        .withColors(GREEN)
                        .withRadii(0.01f)
                        .build());

        recording.log(
                "world/obstacles",
                Boxes3D.builder()
                        .withHalfSizes(HalfSize3D.of(0.3f, 0.3f, 0.5f), HalfSize3D.of(0.2f, 0.4f, 0.2f))
                        .withCenters(
                                Translation3D.of(-2.0f, 0.0f, 0.0f),
                                Translation3D.of(2.0f, 0.0f, 0.2f + 0.3f * (float) Math.sin(phase)))
                        .withColors(ORANGE, PURPLE)
                        .withClassIds(2, 2)
                        .build());

        double amplitude = Math.sin(3.0 * phase);
        recording.log("plots/amplitude", Scalars.builder().withScalars(amplitude).build());
        recording.log("plots/phase", Scalars.builder().withScalars(phase).build());

        if (frame % 10 == 0) {
            recording.log(
                    "logs/simulation",
                    TextLog.builder()
                            .withText(String.format(
                                    "frame %02d/%d  amplitude=%.3f", frame, frames, amplitude))
                            .withLevel("INFO")
                            .withColor(GREEN)
                            .build());
        }
    }

    private static void logSimulationFinished(RecordingStream recording, int frames) {
        recording.log(
                "logs/simulation",
                TextLog.builder()
                        .withText("simulation finished after " + frames + " frames")
                        .withLevel("DEBUG")
                        .build());
    }

    /** Linearly blends two packed RGBA colors. */
    private static long lerpRgba(long from, long to, float t) {
        int r = lerp((int) (from >> 24) & 0xFF, (int) (to >> 24) & 0xFF, t);
        int g = lerp((int) (from >> 16) & 0xFF, (int) (to >> 16) & 0xFF, t);
        int b = lerp((int) (from >> 8) & 0xFF, (int) (to >> 8) & 0xFF, t);
        int a = lerp((int) from & 0xFF, (int) to & 0xFF, t);
        return ((long) r << 24) | ((long) g << 16) | ((long) b << 8) | a;
    }

    private static int lerp(int from, int to, float t) {
        return Math.round(from + (to - from) * t);
    }

    private static String requireValue(String[] args, int index, String flag) {
        if (index >= args.length) {
            throw new IllegalArgumentException(flag + " needs a value");
        }
        return args[index];
    }

    private static void printUsage() {
        System.out.println("usage: moneyshift-demo [--out <dir>] [--connect <rerun-url>]");
        System.out.println("  --out      directory for the generated .rrd files (default: build/moneyshift-example)");
        System.out.println("  --connect  also stream live to a viewer, e.g. rerun+http://127.0.0.1:9876/proxy");
    }

    /**
     * A transport shared by two recording streams (blueprint and recording) that writes both
     * stores into a single RRD stream. {@link RecordingStream#close()} closes its transport, so
     * this wrapper defers the real close until both streams are done.
     */
    private static final class SharedFileTransport implements Transport {
        private final FileTransport file;
        private boolean finished;

        SharedFileTransport(Path path) {
            this.file = new FileTransport(path);
        }

        @Override
        public synchronized void send(LogMsg msg) {
            file.send(msg);
        }

        @Override
        public synchronized void flush(long timeoutMillis) {
            file.flush(timeoutMillis);
        }

        @Override
        public synchronized void close() {
            // One of the two streams finished; the owner calls finish() after both are closed.
        }

        synchronized void finish() {
            if (!finished) {
                finished = true;
                file.close();
            }
        }
    }
}
