package org.teamvan.moneyshift;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wpilib.networktables.NetworkTableInstance;

import org.teamvan.moneyshift.components.AnnotationContext;
import org.teamvan.moneyshift.components.Count;
import org.teamvan.moneyshift.components.Position2D;
import org.teamvan.moneyshift.components.Scalar;
import org.teamvan.moneyshift.components.Text;
import org.teamvan.moneyshift.encodings.AnnotationInfo;
import org.teamvan.moneyshift.encodings.ClassDescription;
import org.teamvan.moneyshift.encodings.ClassDescriptionMapElem;
import org.teamvan.moneyshift.encodings.TimeInt;
import org.teamvan.moneyshift.encodings.TimeRange;
import org.teamvan.moneyshift.encodings.TimeRangeBoundary;

/** One golden test per §4.2 layout case: each asserts the exact topic tree. */
class ColumnLayoutTest {
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
    void primitivesUseNativeArrays() {
        recorder.logComponent("l/scalar", "value", List.of(new Scalar(1.5), new Scalar(2.5)));

        assertArrayEquals(new double[] {1.5, 2.5}, doubles("/Rerun/l/scalar/value"));

        recorder.logComponent(
                "l/position", "value", List.of(new Position2D(new float[] {1, 2})));
        assertArrayEquals(new double[] {1}, doubles("/Rerun/l/position/value/0"), "x");
        assertArrayEquals(new double[] {2}, doubles("/Rerun/l/position/value/1"), "y");
    }

    @Test
    void stringsUseStringArrays() {
        recorder.logComponent("l/text", "value", List.of(new Text("hello"), new Text("world")));
        assertArrayEquals(
                new String[] {"hello", "world"}, strings("/Rerun/l/text/value"));
    }

    @Test
    void uInt64KeepsNativeArraysAndAddsADecimalSidecar() {
        recorder.logComponent("l/count", "value", List.of(new Count(5)));
        assertArrayEquals(new long[] {5}, integers("/Rerun/l/count/value"));
        assertFalse(instance.getEntry("/Rerun/l/count/value/.u64").exists());

        // `Long.MIN_VALUE` is 2^63 as an unsigned 64-bit integer. The native column keeps the
        // raw bits; the decimal sidecar carries the value that does not fit in a signed long.
        recorder.logComponent("l/big", "value", List.of(new Count(Long.MIN_VALUE)));
        assertArrayEquals(
                new long[] {Long.MIN_VALUE}, integers("/Rerun/l/big/value"), "raw bits");
        assertArrayEquals(
                new String[] {"9223372036854775808"},
                strings("/Rerun/l/big/value/.u64"));

        // The sidecar is sticky: a later batch of small values must not hide it.
        recorder.logComponent("l/big", "value", List.of(new Count(7)));
        assertArrayEquals(new long[] {7}, integers("/Rerun/l/big/value"));
        assertArrayEquals(
                new String[] {"7"}, strings("/Rerun/l/big/value/.u64"));
    }

    @Test
    void listsUsePerInstanceCountsAndFlattenedItems() {
        ClassDescription description =
                new ClassDescription(
                        new AnnotationInfo(1, "thing", 0xFF00E5FFL),
                        List.of(new AnnotationInfo(2, "joint", 0xFF00FF00L)),
                        List.of());
        recorder.logComponent(
                "l/annotations",
                "class_map",
                List.of(new AnnotationContext(List.of(new ClassDescriptionMapElem(7, description)))));

        assertArrayEquals(
                new long[] {1}, integers("/Rerun/l/annotations/class_map/.count"));
        assertArrayEquals(
                new long[] {7}, integers("/Rerun/l/annotations/class_map/.items/class_id"));
        assertArrayEquals(
                new long[] {1},
                integers(
                        "/Rerun/l/annotations/class_map/.items/class_description/info/id"));
        assertArrayEquals(
                new String[] {"thing"},
                strings(
                        "/Rerun/l/annotations/class_map/.items/class_description/info/label"));
        assertArrayEquals(
                new long[] {0xFF00E5FFL},
                integers(
                        "/Rerun/l/annotations/class_map/.items/class_description/info/color"));
        assertArrayEquals(
                new long[] {1},
                integers(
                        "/Rerun/l/annotations/class_map/.items/class_description/keypoint_annotations/.count"));
        assertArrayEquals(
                new long[] {2},
                integers(
                        "/Rerun/l/annotations/class_map/.items/class_description/keypoint_annotations/.items/id"));
    }

    @Test
    void nullableLeavesPublishPresenceOnlyWhenNeeded() {
        ClassDescription complete =
                new ClassDescription(
                        new AnnotationInfo(1, "thing", 0xFF00E5FFL), List.of(), List.of());
        recorder.logComponent(
                "l/complete",
                "class_map",
                List.of(new AnnotationContext(List.of(new ClassDescriptionMapElem(1, complete)))));

        String label =
                "/Rerun/l/complete/class_map/.items/class_description/info/label";
        assertArrayEquals(new String[] {"thing"}, strings(label));
        assertEquals(
                0,
                instance.getTopicInfo(label + "/.present").length,
                "all-present nullable leaves do not need a mask");

        ClassDescription missing =
                new ClassDescription(new AnnotationInfo(1, null, null), List.of(), List.of());
        recorder.logComponent(
                "l/missing",
                "class_map",
                List.of(new AnnotationContext(List.of(new ClassDescriptionMapElem(1, missing)))));

        String missingLabel =
                "/Rerun/l/missing/class_map/.items/class_description/info/label";
        assertArrayEquals(new String[] {""}, strings(missingLabel));
        assertArrayEquals(
                new boolean[] {false}, booleans(missingLabel + "/.present"), "label presence");
        assertArrayEquals(
                new boolean[] {false},
                booleans(
                        "/Rerun/l/missing/class_map/.items/class_description/info/color/.present"),
                "color presence");
    }

    @Test
    void unionsUseVariantOrdinalsAndCompressPayloads() {
        TimeRange range =
                new TimeRange(
                        new TimeRangeBoundary.Absolute(new TimeInt(5)),
                        new TimeRangeBoundary.Infinite());
        range.writeColumns(recorderWriter("l/range"), "range", List.of(range));

        assertArrayEquals(
                new long[] {1}, integers("/Rerun/l/range/range/start/.variant"));
        assertArrayEquals(
                new long[] {5}, integers("/Rerun/l/range/range/start/Absolute"));
        assertArrayEquals(
                new long[] {2}, integers("/Rerun/l/range/range/end/.variant"));
        assertEquals(
                0,
                instance.getTopicInfo("/Rerun/l/range/range/end/Infinite").length,
                "unit variants carry no payload columns");
    }

    @Test
    void emptyComponentsAreNotWritten() {
        recorder.logComponent("l/empty", "value", List.<Scalar>of());
        assertEquals(0, instance.getTopicInfo("/Rerun/l/empty").length);
    }

    private NtEntityWriter recorderWriter(String entity) {
        return new NtEntityWriter(recorder, entity);
    }

    private double[] doubles(String topic) {
        return instance.getEntry(topic).getDoubleArray(new double[0]);
    }

    private long[] integers(String topic) {
        return instance.getEntry(topic).getIntegerArray(new long[0]);
    }

    private boolean[] booleans(String topic) {
        return instance.getEntry(topic).getBooleanArray(new boolean[0]);
    }

    private String[] strings(String topic) {
        return instance.getEntry(topic).getStringArray(new String[0]);
    }
}
