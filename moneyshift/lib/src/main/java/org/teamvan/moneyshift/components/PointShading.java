// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Defines how points are shaded.
 */
public enum PointShading implements org.teamvan.moneyshift.NtComponent<PointShading> {
    /**
     * Radial gradient for a spherical shadow effect.
     */
    Gradient(1L),
    /**
     * Flat shading.
     */
    Flat(2L);

    private final long value;

    PointShading(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static PointShading fromValue(long value) {
        for (PointShading candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown PointShading value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.PointShading";

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<PointShading> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = new boolean[n];
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            PointShading value = values.get(i);
            present[i] = value != null;
            anyNull |= value == null;
            column[i] = value == null ? 0L : value.value();
        }
        w.present(path, present, anyNull);
        w.longs(path, column);
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<PointShading> values) {
        writeColumnsStatic(w, path, values);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.PointShading";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }
}
