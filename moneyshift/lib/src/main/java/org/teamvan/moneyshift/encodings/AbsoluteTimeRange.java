// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Two {@code rerun::encodings::TimeInt} describing a range of time.
 */
public record AbsoluteTimeRange(
    /**
     * Start of the range.
     */
    org.teamvan.moneyshift.encodings.TimeInt min,
    /**
     * End of the range (inclusive).
     */
    org.teamvan.moneyshift.encodings.TimeInt max
) implements org.teamvan.moneyshift.NtEncoding<AbsoluteTimeRange> {
    public AbsoluteTimeRange {
        java.util.Objects.requireNonNull(min, "min");
        java.util.Objects.requireNonNull(max, "max");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public AbsoluteTimeRange(long min, long max) {
        this(new org.teamvan.moneyshift.encodings.TimeInt(min), new org.teamvan.moneyshift.encodings.TimeInt(max));
    }

    public static final java.lang.String NAME = "rerun.encodings.AbsoluteTimeRange";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<AbsoluteTimeRange> values) {
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
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).min();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/min", column1);
        long[] column2 = new long[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).max();
            var v2_1 = v2_0 == null ? null : v2_0.value();
            var value2 = v2_1;
            column2[i] = value2 == null ? 0L : value2;
        }
        w.longs(path + "/max", column2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<AbsoluteTimeRange> values) {
        writeColumnsStatic(w, path, values);
    }
}
