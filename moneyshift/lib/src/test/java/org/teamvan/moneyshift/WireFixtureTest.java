package org.teamvan.moneyshift;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wpilib.networktables.NetworkTableEntry;
import org.wpilib.networktables.NetworkTableInstance;
import org.wpilib.networktables.NetworkTableType;
import org.wpilib.networktables.NetworkTableValue;
import org.wpilib.networktables.Topic;

import org.teamvan.moneyshift.archetypes.Points3D;
import org.teamvan.moneyshift.archetypes.Tensor;
import org.teamvan.moneyshift.components.AnnotationContext;
import org.teamvan.moneyshift.components.Position3D;
import org.teamvan.moneyshift.components.TensorData;
import org.teamvan.moneyshift.encodings.AnnotationInfo;
import org.teamvan.moneyshift.encodings.ClassDescription;
import org.teamvan.moneyshift.encodings.ClassDescriptionMapElem;
import org.teamvan.moneyshift.encodings.ClassId;
import org.teamvan.moneyshift.encodings.Rgba32;
import org.teamvan.moneyshift.encodings.TensorBuffer;
import org.teamvan.moneyshift.encodings.Utf8;

/**
 * Golden test for the cross-language {@code /Rerun} wire format.
 *
 * <p>Publishes a fixed set of archetypes through a real NetworkTables instance and dumps every
 * topic to {@code src/test/resources/rerun_wire_fixture.json}. The Rust test
 * {@code tests/wire_fixture.rs} decodes that same file with Rerun's reflection datatypes, so a
 * layout change on either side fails one of the two suites.
 *
 * <p>Regenerate after an intentional layout change:
 *
 * <pre>{@code
 * ./gradlew :lib:test --tests '*WireFixtureTest' -Dfirstrun.writeFixture=true
 * }</pre>
 */
class WireFixtureTest {
    private static final String WRITE_PROPERTY = "firstrun.writeFixture";

    private NetworkTableInstance instance;
    private NtRecorder recorder;

    @BeforeEach
    void setUp() {
        instance = NetworkTableInstance.create();
        NtRecorder.setInstance(instance);
        recorder = NtRecorder.getInstance();
    }

    @AfterEach
    void tearDown() {
        NtRecorder.setInstance(null);
        instance.close();
    }

    @Test
    void matchesTheCheckedInFixture() throws IOException {
        publishFixture();
        recorder.flush();

        String actual = renderFixture();
        Path fixture = fixturePath();
        if (Boolean.getBoolean(WRITE_PROPERTY)) {
            Files.createDirectories(fixture.getParent());
            Files.writeString(fixture, actual, StandardCharsets.UTF_8);
            return;
        }
        if (!Files.exists(fixture)) {
            fail(
                    "missing " + fixture + "; run with -D" + WRITE_PROPERTY + "=true to create it");
        }
        String expected = Files.readString(fixture, StandardCharsets.UTF_8);
        if (!expected.equals(actual)) {
            Files.writeString(fixture.resolveSibling("rerun_wire_fixture.actual.json"), actual);
            fail(
                    "the wire layout changed; inspect "
                            + fixture.resolveSibling("rerun_wire_fixture.actual.json")
                            + " and regenerate with -D"
                            + WRITE_PROPERTY
                            + "=true");
        }
    }

    private void publishFixture() {
        recorder.log(
                "world/points",
                Points3D.builder()
                        .withPositions(Position3D.of(1f, 2f, 3f), Position3D.of(-4f, 5.5f, 6f))
                        .withRadii(0.25f, 0.5f)
                        .withColors(0xFFFF0000L, 0xFF00FF00L)
                        .withLabels("alpha", "beta")
                        .withClassIds(1, 2)
                        .withKeypointIds(7, 8)
                        .build());

        recorder.log(
                "world/annotations",
                org.teamvan.moneyshift.archetypes.AnnotationContext.of(
                        new AnnotationContext(List.of(
                                new ClassDescriptionMapElem(
                                        new ClassId(1),
                                        new ClassDescription(
                                                new AnnotationInfo(
                                                        1, new Utf8("person"), new Rgba32(0xFF112233L)),
                                                List.of(),
                                                List.of())),
                                new ClassDescriptionMapElem(
                                        new ClassId(2),
                                        new ClassDescription(
                                                // A null label exercises the nested `.present` mask.
                                                new AnnotationInfo(2, null, null),
                                                List.of(new AnnotationInfo(3, null, null)),
                                                List.of()))))));

        recorder.log(
                "world/tensor",
                Tensor.of(
                        new TensorData(
                                List.of(2L, 2L),
                                List.of("height", "width"),
                                // The first value only fits in an unsigned 64-bit column.
                                new TensorBuffer.U64(
                                        List.of(Long.MIN_VALUE, 0L, 7L, 13L)))));
    }

    /// Renders the fixture JSON. Kept stable (sorted topics, fixed key order) so diffs are useful.
    private String renderFixture() {
        StringBuilder json = new StringBuilder();
        json.append("{\n  \"version\": 1,\n  \"entities\": [\n");
        List<String> entities = List.of("world/points", "world/annotations", "world/tensor");
        for (int index = 0; index < entities.size(); index++) {
            String entity = entities.get(index);
            json.append("    {\n");
            String prefix = NtPath.ROOT + NtPath.encodeEntityPath(entity);
            json.append("      \"entity\": ").append(quote(prefix)).append(",\n");
            json.append("      \"archetype\": ")
                    .append(quote(string(prefix + "/.rerun/archetype")))
                    .append(",\n");
            json.append("      \"components\": ")
                    .append(quote(string(prefix + "/.rerun/components")))
                    .append(",\n");
            json.append("      \"static\": ")
                    .append(instance.getEntry(prefix + "/.rerun/static").getBoolean(false))
                    .append(",\n");
            json.append("      \"columns\": {\n");

            List<Topic> topics = new ArrayList<>(List.of(instance.getTopics(prefix)));
            topics.sort(Comparator.comparing(Topic::getName));
            List<String> rendered = new ArrayList<>();
            for (Topic topic : topics) {
                String name = topic.getName();
                if (!name.startsWith(prefix + "/") || name.contains("/.rerun/")) {
                    continue;
                }
                String column = name.substring(prefix.length() + 1);
                NetworkTableEntry entry = instance.getEntry(name);
                rendered.add(
                        "        "
                                + quote(column)
                                + ": {\"type\": "
                                + quote(topic.getTypeString())
                                + ", \"value\": "
                                + renderValue(entry.getValue())
                                + "}");
            }
            json.append(String.join(",\n", rendered)).append('\n');
            json.append("      }\n");
            json.append(index + 1 == entities.size() ? "    }\n" : "    },\n");
        }
        json.append("  ]\n}\n");
        return json.toString();
    }

    private static String renderValue(NetworkTableValue value) {
        NetworkTableType type = value.getType();
        switch (type) {
            case BOOLEAN:
                return Boolean.toString(value.getBoolean());
            case DOUBLE:
                return renderDouble(value.getDouble());
            case INTEGER:
                return Long.toString(value.getInteger());
            case STRING:
                return quote(value.getString());
            case RAW:
                return '"' + hex(value.getRaw()) + '"';
            case BOOLEAN_ARRAY:
                return renderBooleans(value.getBooleanArray());
            case DOUBLE_ARRAY:
                return renderDoubles(value.getDoubleArray());
            case INTEGER_ARRAY:
                return renderLongs(value.getIntegerArray());
            case STRING_ARRAY:
                return renderStrings(value.getStringArray());
            default:
                throw new IllegalArgumentException("unexpected value type " + type);
        }
    }

    private static String renderBooleans(boolean[] values) {
        StringBuilder json = new StringBuilder("[");
        for (int index = 0; index < values.length; index++) {
            if (index > 0) {
                json.append(", ");
            }
            json.append(values[index]);
        }
        return json.append(']').toString();
    }

    private static String renderLongs(long[] values) {
        StringBuilder json = new StringBuilder("[");
        for (int index = 0; index < values.length; index++) {
            if (index > 0) {
                json.append(", ");
            }
            json.append(values[index]);
        }
        return json.append(']').toString();
    }

    private static String renderDoubles(double[] values) {
        StringBuilder json = new StringBuilder("[");
        for (int index = 0; index < values.length; index++) {
            if (index > 0) {
                json.append(", ");
            }
            json.append(renderDouble(values[index]));
        }
        return json.append(']').toString();
    }

    private static String renderStrings(String[] values) {
        StringBuilder json = new StringBuilder("[");
        for (int index = 0; index < values.length; index++) {
            if (index > 0) {
                json.append(", ");
            }
            json.append(quote(values[index]));
        }
        return json.append(']').toString();
    }

    private static String renderDouble(double value) {
        if (value == Math.rint(value) && Math.abs(value) < 1e15) {
            return Long.toString((long) value);
        }
        return Double.toString(value);
    }

    private static String hex(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte value : bytes) {
            hex.append(Character.forDigit((value >> 4) & 0xF, 16))
                    .append(Character.forDigit(value & 0xF, 16));
        }
        return hex.toString();
    }

    private static String quote(String value) {
        StringBuilder quoted = new StringBuilder(value.length() + 2);
        quoted.append('"');
        for (int index = 0; index < value.length(); index++) {
            char character = value.charAt(index);
            switch (character) {
                case '"' -> quoted.append("\\\"");
                case '\\' -> quoted.append("\\\\");
                case '\n' -> quoted.append("\\n");
                case '\r' -> quoted.append("\\r");
                case '\t' -> quoted.append("\\t");
                default -> {
                    if (character < 0x20) {
                        quoted.append(String.format("\\u%04x", (int) character));
                    } else {
                        quoted.append(character);
                    }
                }
            }
        }
        return quoted.append('"').toString();
    }

    private String string(String topic) {
        return instance.getEntry(topic).getString("");
    }

    /// Relative to the module directory, which is the Gradle test working directory.
    private static Path fixturePath() {
        return Path.of("src", "test", "resources", "rerun_wire_fixture.json");
    }
}
