// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Visible time range bounds for a specific timeline.
 * 
 */
public record VisibleTimeRange(
    /**
     * Name of the timeline this applies to.
     */
    org.teamvan.moneyshift.encodings.Utf8 timeline,
    /**
     * Time range to use for this timeline.
     */
    org.teamvan.moneyshift.encodings.TimeRange range
) implements org.teamvan.moneyshift.NtEncoding<VisibleTimeRange> {
    public VisibleTimeRange {
        java.util.Objects.requireNonNull(timeline, "timeline");
        java.util.Objects.requireNonNull(range, "range");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public VisibleTimeRange(java.lang.String timeline, org.teamvan.moneyshift.encodings.TimeRange range) {
        this(new org.teamvan.moneyshift.encodings.Utf8(timeline), range);
    }

    public static final java.lang.String NAME = "rerun.encodings.VisibleTimeRange";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<VisibleTimeRange> values) {
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
        java.lang.String[] column1 = new java.lang.String[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).timeline();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            java.lang.String value1 = v1_1;
            column1[i] = value1 == null ? "" : value1;
        }
        w.strings(path + "/timeline", column1);
        long[] variants2 = new long[n];
        java.util.ArrayList<org.teamvan.moneyshift.encodings.TimeInt> payload2_0 = new java.util.ArrayList<>();
        java.util.ArrayList<org.teamvan.moneyshift.encodings.TimeInt> payload2_1 = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).range();
            var v2_1 = v2_0 == null ? null : v2_0.start();
            org.teamvan.moneyshift.encodings.TimeRangeBoundary value2 = v2_1;
            if (value2 == null) {
                variants2[i] = -1L;
            } else if (value2 instanceof org.teamvan.moneyshift.encodings.TimeRangeBoundary.CursorRelative variant2_0) {
                variants2[i] = 0L;
                payload2_0.add(variant2_0.value());
            } else if (value2 instanceof org.teamvan.moneyshift.encodings.TimeRangeBoundary.Absolute variant2_1) {
                variants2[i] = 1L;
                payload2_1.add(variant2_1.value());
            } else if (value2 instanceof org.teamvan.moneyshift.encodings.TimeRangeBoundary.Infinite) {
                variants2[i] = 2L;
            } else {
                variants2[i] = -1L;
            }
        }
        w.longs(path + "/range/start" + "/.variant", variants2);
        if (!payload2_0.isEmpty()) {
            int payloadCount2_0 = payload2_0.size();
        long[] column3 = new long[payloadCount2_0];
        for (int k2_0 = 0; k2_0 < payloadCount2_0; k2_0++) {
            var v3_0 = payload2_0.get(k2_0).value();
            long value3 = v3_0;
            column3[k2_0] = value3;
        }
        w.longs(path + "/range/start/CursorRelative", column3);
        }
        if (!payload2_1.isEmpty()) {
            int payloadCount2_1 = payload2_1.size();
        long[] column4 = new long[payloadCount2_1];
        for (int k2_1 = 0; k2_1 < payloadCount2_1; k2_1++) {
            var v4_0 = payload2_1.get(k2_1).value();
            long value4 = v4_0;
            column4[k2_1] = value4;
        }
        w.longs(path + "/range/start/Absolute", column4);
        }
        long[] variants5 = new long[n];
        java.util.ArrayList<org.teamvan.moneyshift.encodings.TimeInt> payload5_0 = new java.util.ArrayList<>();
        java.util.ArrayList<org.teamvan.moneyshift.encodings.TimeInt> payload5_1 = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            var v5_0 = values.get(i) == null ? null : values.get(i).range();
            var v5_1 = v5_0 == null ? null : v5_0.end();
            org.teamvan.moneyshift.encodings.TimeRangeBoundary value5 = v5_1;
            if (value5 == null) {
                variants5[i] = -1L;
            } else if (value5 instanceof org.teamvan.moneyshift.encodings.TimeRangeBoundary.CursorRelative variant5_0) {
                variants5[i] = 0L;
                payload5_0.add(variant5_0.value());
            } else if (value5 instanceof org.teamvan.moneyshift.encodings.TimeRangeBoundary.Absolute variant5_1) {
                variants5[i] = 1L;
                payload5_1.add(variant5_1.value());
            } else if (value5 instanceof org.teamvan.moneyshift.encodings.TimeRangeBoundary.Infinite) {
                variants5[i] = 2L;
            } else {
                variants5[i] = -1L;
            }
        }
        w.longs(path + "/range/end" + "/.variant", variants5);
        if (!payload5_0.isEmpty()) {
            int payloadCount5_0 = payload5_0.size();
        long[] column6 = new long[payloadCount5_0];
        for (int k5_0 = 0; k5_0 < payloadCount5_0; k5_0++) {
            var v6_0 = payload5_0.get(k5_0).value();
            long value6 = v6_0;
            column6[k5_0] = value6;
        }
        w.longs(path + "/range/end/CursorRelative", column6);
        }
        if (!payload5_1.isEmpty()) {
            int payloadCount5_1 = payload5_1.size();
        long[] column7 = new long[payloadCount5_1];
        for (int k5_1 = 0; k5_1 < payloadCount5_1; k5_1++) {
            var v7_0 = payload5_1.get(k5_1).value();
            long value7 = v7_0;
            column7[k5_1] = value7;
        }
        w.longs(path + "/range/end/Absolute", column7);
        }
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<VisibleTimeRange> values) {
        writeColumnsStatic(w, path, values);
    }
}
