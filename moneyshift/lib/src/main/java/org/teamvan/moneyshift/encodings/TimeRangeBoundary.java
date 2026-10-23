// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Left or right boundary of a time range.
 */
public sealed interface TimeRangeBoundary extends org.teamvan.moneyshift.NtEncoding<TimeRangeBoundary> permits
    TimeRangeBoundary.CursorRelative,
    TimeRangeBoundary.Absolute,
    TimeRangeBoundary.Infinite {
    /**
     * Boundary is a value relative to the time cursor.
     */
    record CursorRelative(org.teamvan.moneyshift.encodings.TimeInt value) implements TimeRangeBoundary {
        public CursorRelative { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * Boundary is an absolute value.
     */
    record Absolute(org.teamvan.moneyshift.encodings.TimeInt value) implements TimeRangeBoundary {
        public Absolute { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * The boundary extends to infinity.
     */
    record Infinite() implements TimeRangeBoundary {}

    java.lang.String NAME = "rerun.encodings.TimeRangeBoundary";

    @Override
    default void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TimeRangeBoundary> values) {
        writeColumnsStatic(w, path, values);
    }

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TimeRangeBoundary> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        long[] variants1 = new long[n];
        java.util.ArrayList<org.teamvan.moneyshift.encodings.TimeInt> payload1_0 = new java.util.ArrayList<>();
        java.util.ArrayList<org.teamvan.moneyshift.encodings.TimeInt> payload1_1 = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            org.teamvan.moneyshift.encodings.TimeRangeBoundary value1 = values.get(i);
            if (value1 == null) {
                variants1[i] = -1L;
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TimeRangeBoundary.CursorRelative variant1_0) {
                variants1[i] = 0L;
                payload1_0.add(variant1_0.value());
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TimeRangeBoundary.Absolute variant1_1) {
                variants1[i] = 1L;
                payload1_1.add(variant1_1.value());
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TimeRangeBoundary.Infinite) {
                variants1[i] = 2L;
            } else {
                variants1[i] = -1L;
            }
        }
        w.longs(path + "/.variant", variants1);
        if (!payload1_0.isEmpty()) {
            int payloadCount1_0 = payload1_0.size();
        long[] column2 = new long[payloadCount1_0];
        for (int k1_0 = 0; k1_0 < payloadCount1_0; k1_0++) {
            var v2_0 = payload1_0.get(k1_0).value();
            long value2 = v2_0;
            column2[k1_0] = value2;
        }
        w.longs(path + "/CursorRelative", column2);
        }
        if (!payload1_1.isEmpty()) {
            int payloadCount1_1 = payload1_1.size();
        long[] column3 = new long[payloadCount1_1];
        for (int k1_1 = 0; k1_1 < payloadCount1_1; k1_1++) {
            var v3_0 = payload1_1.get(k1_1).value();
            long value3 = v3_0;
            column3[k1_1] = value3;
        }
        w.longs(path + "/Absolute", column3);
        }
    }
}
