// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Specifies how values between data points are interpolated in time series.
 */
public enum InterpolationMode implements org.teamvan.moneyshift.NtComponent<InterpolationMode> {
    /**
     * Connect data points with straight line segments.
     */
    Linear(1L),
    /**
     * Hold the previous value until the next data point, then jump.
     * 
     * The step occurs at the end of the interval.
     */
    StepAfter(2L),
    /**
     * Jump to the new value immediately, then hold until the next data point.
     * 
     * The step occurs at the beginning of the interval.
     */
    StepBefore(3L),
    /**
     * Hold the previous value until the midpoint between data points, then jump to the new value.
     * 
     * The step occurs at the midpoint of the interval.
     */
    StepMid(4L);

    private final long value;

    InterpolationMode(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static InterpolationMode fromValue(long value) {
        for (InterpolationMode candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown InterpolationMode value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.InterpolationMode";

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<InterpolationMode> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = new boolean[n];
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            InterpolationMode value = values.get(i);
            present[i] = value != null;
            anyNull |= value == null;
            column[i] = value == null ? 0L : value.value();
        }
        w.present(path, present, anyNull);
        w.longs(path, column);
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<InterpolationMode> values) {
        writeColumnsStatic(w, path, values);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.InterpolationMode";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }
}
