// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Visible time range bounds for a specific timeline.
 */
public record TimeRange(
    /**
     * Low time boundary for sequence timeline.
     */
    org.teamvan.moneyshift.encodings.TimeRangeBoundary start,
    /**
     * High time boundary for sequence timeline.
     */
    org.teamvan.moneyshift.encodings.TimeRangeBoundary end
) implements org.teamvan.moneyshift.NtEncoding<TimeRange> {
    public TimeRange {
        java.util.Objects.requireNonNull(start, "start");
        java.util.Objects.requireNonNull(end, "end");
    }
    public static final java.lang.String NAME = "rerun.encodings.TimeRange";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TimeRange> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
            if (ok0) {
                if (present0 != null) present0[i] = true;
            } else {
                if (present0 == null) {
                    present0 = new boolean[n];
                    java.util.Arrays.fill(present0, 0, i, true);
                }
                anyNull0 = true;
            }
        }
        w.present(path, present0, anyNull0, n);
        long[] variants1 = new long[n];
        java.util.ArrayList<org.teamvan.moneyshift.encodings.TimeInt> payload1_0 = new java.util.ArrayList<>();
        java.util.ArrayList<org.teamvan.moneyshift.encodings.TimeInt> payload1_1 = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).start();
            org.teamvan.moneyshift.encodings.TimeRangeBoundary value1 = v1_0;
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
        w.longs(path + "/start" + "/.variant", variants1);
        if (!payload1_0.isEmpty()) {
            int payloadCount1_0 = payload1_0.size();
        long[] column2 = new long[payloadCount1_0];
        for (int k1_0 = 0; k1_0 < payloadCount1_0; k1_0++) {
            var v2_0 = payload1_0.get(k1_0).value();
            long value2 = v2_0;
            column2[k1_0] = value2;
        }
        w.longs(path + "/start/CursorRelative", column2);
        }
        if (!payload1_1.isEmpty()) {
            int payloadCount1_1 = payload1_1.size();
        long[] column3 = new long[payloadCount1_1];
        for (int k1_1 = 0; k1_1 < payloadCount1_1; k1_1++) {
            var v3_0 = payload1_1.get(k1_1).value();
            long value3 = v3_0;
            column3[k1_1] = value3;
        }
        w.longs(path + "/start/Absolute", column3);
        }
        long[] variants4 = new long[n];
        java.util.ArrayList<org.teamvan.moneyshift.encodings.TimeInt> payload4_0 = new java.util.ArrayList<>();
        java.util.ArrayList<org.teamvan.moneyshift.encodings.TimeInt> payload4_1 = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            var v4_0 = values.get(i) == null ? null : values.get(i).end();
            org.teamvan.moneyshift.encodings.TimeRangeBoundary value4 = v4_0;
            if (value4 == null) {
                variants4[i] = -1L;
            } else if (value4 instanceof org.teamvan.moneyshift.encodings.TimeRangeBoundary.CursorRelative variant4_0) {
                variants4[i] = 0L;
                payload4_0.add(variant4_0.value());
            } else if (value4 instanceof org.teamvan.moneyshift.encodings.TimeRangeBoundary.Absolute variant4_1) {
                variants4[i] = 1L;
                payload4_1.add(variant4_1.value());
            } else if (value4 instanceof org.teamvan.moneyshift.encodings.TimeRangeBoundary.Infinite) {
                variants4[i] = 2L;
            } else {
                variants4[i] = -1L;
            }
        }
        w.longs(path + "/end" + "/.variant", variants4);
        if (!payload4_0.isEmpty()) {
            int payloadCount4_0 = payload4_0.size();
        long[] column5 = new long[payloadCount4_0];
        for (int k4_0 = 0; k4_0 < payloadCount4_0; k4_0++) {
            var v5_0 = payload4_0.get(k4_0).value();
            long value5 = v5_0;
            column5[k4_0] = value5;
        }
        w.longs(path + "/end/CursorRelative", column5);
        }
        if (!payload4_1.isEmpty()) {
            int payloadCount4_1 = payload4_1.size();
        long[] column6 = new long[payloadCount4_1];
        for (int k4_1 = 0; k4_1 < payloadCount4_1; k4_1++) {
            var v6_0 = payload4_1.get(k4_1).value();
            long value6 = v6_0;
            column6[k4_1] = value6;
        }
        w.longs(path + "/end/Absolute", column6);
        }
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TimeRange> values) {
        writeColumnsStatic(w, path, values);
    }
}
