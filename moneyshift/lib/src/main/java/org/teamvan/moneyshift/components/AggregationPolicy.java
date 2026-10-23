// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Policy for aggregation of multiple scalar plot values.
 * 
 * This is used for lines in plots when the X axis distance of individual points goes below a single pixel,
 * i.e. a single pixel covers more than one tick worth of data. It can greatly improve performance
 * (and readability) in such situations as it prevents overdraw.
 */
public enum AggregationPolicy implements org.teamvan.moneyshift.NtComponent<AggregationPolicy> {
    /**
     * No aggregation.
     */
    Off(1L),
    /**
     * Average all points in the range together.
     * 
     * This can wash out outliers (spikes).
     */
    Average(2L),
    /**
     * Keep only the maximum values in the range.
     */
    Max(3L),
    /**
     * Keep only the minimum values in the range.
     */
    Min(4L),
    /**
     * Keep both the minimum and maximum values in the range.
     * 
     * This will yield two aggregated points instead of one, effectively creating a vertical line.
     * In practice this often leads to a rather ugly zig-zag look.
     */
    MinMax(5L),
    /**
     * Find both the minimum and maximum values in the range, then use the average of those.
     * 
     * This yields a single point per range, so it does not draw a vertical line per pixel,
     * while still letting a lone outlier move the plotted value, which averaging all the
     * points in the range would wash out.
     */
    MinMaxAverage(6L);

    private final long value;

    AggregationPolicy(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static AggregationPolicy fromValue(long value) {
        for (AggregationPolicy candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown AggregationPolicy value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.AggregationPolicy";

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<AggregationPolicy> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = new boolean[n];
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            AggregationPolicy value = values.get(i);
            present[i] = value != null;
            anyNull |= value == null;
            column[i] = value == null ? 0L : value.value();
        }
        w.present(path, present, anyNull);
        w.longs(path, column);
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<AggregationPolicy> values) {
        writeColumnsStatic(w, path, values);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.AggregationPolicy";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }
}
