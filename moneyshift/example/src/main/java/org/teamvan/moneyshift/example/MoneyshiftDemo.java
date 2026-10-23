package org.teamvan.moneyshift.example;

import java.util.ArrayList;
import java.util.List;

import org.teamvan.moneyshift.NtRecorder;
import org.teamvan.moneyshift.archetypes.AnnotationContext;
import org.teamvan.moneyshift.archetypes.Boxes3D;
import org.teamvan.moneyshift.archetypes.LineStrips3D;
import org.teamvan.moneyshift.archetypes.Points3D;
import org.teamvan.moneyshift.archetypes.Scalars;
import org.teamvan.moneyshift.archetypes.SeriesLines;
import org.teamvan.moneyshift.archetypes.TextLog;
import org.teamvan.moneyshift.archetypes.Transform3D;
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
import org.wpilib.networktables.NetworkTableInstance;

/**
 * A small "sensor sweep" simulation that publishes Rerun data through NetworkTables.
 *
 * <p>By default it starts an embedded NT4 server, publishes the scene and a number of frames, and
 * prints the command that connects the Rust bridge:
 *
 * <pre>{@code
 * cargo run --bin rerun-importer-firstrun -- --nt --save /tmp/live.rrd
 * }</pre>
 *
 * <p>Use {@code --client <host>} to publish to a server that already exists (for example the one
 * the robot runs), or {@code --linger <seconds>} to keep the embedded server alive for inspection.
 */
public final class MoneyshiftDemo {
    private static final int DEFAULT_FRAMES = 60;
    private static final int DEFAULT_PORT = 5810;
    private static final int POINT_COUNT = 96;
    private static final int SCAN_POINTS = 64;
    private static final float TAU = (float) (Math.PI * 2.0);

    private static final long CYAN = 0xFF00E5FFL;
    private static final long MAGENTA = 0xFFFF2D95L;
    private static final long ORANGE = 0xFFFF9F0AL;
    private static final long PURPLE = 0xFFBF5AF2L;
    private static final long GREEN = 0xFF32D74BL;

    private MoneyshiftDemo() {}

    public static void main(String[] args) throws Exception {
        int frames = DEFAULT_FRAMES;
        int port = DEFAULT_PORT;
        int lingerSeconds = 0;
        String clientHost = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--frames" -> frames = Integer.parseInt(requireValue(args, ++i, "--frames"));
                case "--port" -> port = Integer.parseInt(requireValue(args, ++i, "--port"));
                case "--linger" -> lingerSeconds = Integer.parseInt(requireValue(args, ++i, "--linger"));
                case "--client" -> clientHost = requireValue(args, ++i, "--client");
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

        NetworkTableInstance instance = NetworkTableInstance.create();
        if (clientHost == null) {
            instance.startServer("", "", "", port);
        } else {
            instance.setServer(clientHost);
        }

        try {
            NtRecorder.setInstance(instance);
            publish(NtRecorder.getInstance(), frames);
            NtRecorder.getInstance().flush();
        } finally {
            NtRecorder.setInstance(null);
        }

        System.out.printf(
                "published %d frames to %s%n",
                frames, clientHost == null ? "the embedded NT server on port " + port : clientHost);
        System.out.println("connect the Rust bridge with:");
        System.out.println("  cargo run --bin rerun-importer-firstrun -- --nt --save /tmp/live.rrd");
        System.out.println("then open the recording with: rerun --load /tmp/live.rrd");

        if (lingerSeconds > 0) {
            System.out.println("keeping the server alive for " + lingerSeconds + "s");
            Thread.sleep(lingerSeconds * 1000L);
        }
        instance.close();
    }

    /** Publishes the static scene and `frames` dynamic frames through the given recorder. */
    public static void publish(NtRecorder recorder, int frames) {
        if (frames < 1) {
            throw new IllegalArgumentException("frames must be at least 1");
        }
        logStaticScene(recorder);
        for (int frame = 0; frame < frames; frame++) {
            logFrame(recorder, frame, frames);
        }
        recorder.log(
                "logs/simulation",
                TextLog.builder()
                        .withText("simulation finished after " + frames + " frames")
                        .withLevel("DEBUG")
                        .build());
    }

    private static void logStaticScene(NtRecorder recorder) {
        recorder.logStatic(
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

        recorder.logStatic(
                "plots/style/amplitude",
                SeriesLines.builder().withNames("amplitude").withColors(CYAN).withWidths(2.0f).build());
        recorder.logStatic(
                "plots/style/phase",
                SeriesLines.builder().withNames("phase").withColors(MAGENTA).withWidths(2.0f).build());
    }

    private static void logFrame(NtRecorder recorder, int frame, int frames) {
        float phase = TAU * frame / frames;

        // The robot moves in a circle; everything below "world/robot" inherits this transform.
        recorder.log(
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
        recorder.log(
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
        recorder.log(
                "world/robot/sensor/scan",
                LineStrips3D.builder()
                        .withStrips(new LineStrip3D(scan))
                        .withColors(GREEN)
                        .withRadii(0.01f)
                        .build());

        recorder.log(
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
        recorder.log("plots/amplitude", Scalars.builder().withScalars(amplitude).build());
        recorder.log("plots/phase", Scalars.builder().withScalars(phase).build());

        // Plain NT topics are not part of the Rerun protocol; the Rust bridge maps them with the
        // same heuristics it uses for AdvantageKit logs, so robot code can keep publishing
        // normally. This one shows up as a scalar graph while the bridge runs.
        recorder.instance()
                .getEntry("/Demo/Drive/Speed")
                .setDouble(Math.cos(phase));

        if (frame % 10 == 0) {
            recorder.log(
                    "logs/simulation",
                    TextLog.builder()
                            .withText(String.format(
                                    "frame %02d/%d  amplitude=%.3f", frame, frames, amplitude))
                            .withLevel("INFO")
                            .withColor(GREEN)
                            .build());
        }
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
        System.out.println("usage: moneyshift-demo [--frames <n>] [--port <n>] [--linger <seconds>] [--client <host>]");
        System.out.println("  --frames   number of frames to publish (default: " + DEFAULT_FRAMES + ")");
        System.out.println("  --port     port of the embedded NT server (default: " + DEFAULT_PORT + ")");
        System.out.println("  --linger   keep the embedded server alive after publishing");
        System.out.println("  --client   publish to an existing NT server instead of starting one");
    }
}
