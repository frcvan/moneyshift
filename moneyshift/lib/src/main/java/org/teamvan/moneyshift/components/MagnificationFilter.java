// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Filter used when a single texel/pixel of an image is displayed larger than a single screen pixel.
 * 
 * This happens when zooming into an image, when displaying a low-resolution image in a large area,
 * or when viewing an image up close in 3D space.
 */
public enum MagnificationFilter implements org.teamvan.moneyshift.NtComponent<MagnificationFilter> {
    /**
     * Show the nearest pixel value.
     * 
     * This will give a blocky appearance when the image is scaled up.
     * Used as default when rendering 2D images.
     */
    Nearest(1L),
    /**
     * Linearly interpolate the nearest neighbors, creating a smoother look when the image is scaled up.
     * 
     * Used as default for mesh rendering.
     */
    Linear(2L),
    /**
     * Bicubic interpolation using a Catmull-Rom spline, creating the smoothest look when the image is scaled up.
     * 
     * This is computationally more expensive than linear filtering but produces sharper results with less blurring.
     * Unlike bilinear filtering, this avoids cross-shaped artifacts at texel boundaries.
     */
    Bicubic(3L);

    private final long value;

    MagnificationFilter(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static MagnificationFilter fromValue(long value) {
        for (MagnificationFilter candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown MagnificationFilter value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.MagnificationFilter";

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<MagnificationFilter> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = null;
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            MagnificationFilter value = values.get(i);
            if (value != null) {
                if (present != null) present[i] = true;
            } else {
                if (present == null) {
                    present = new boolean[n];
                    java.util.Arrays.fill(present, 0, i, true);
                }
                anyNull = true;
            }
            column[i] = value == null ? 0L : value.value();
        }
        w.present(path, present, anyNull, n);
        w.longs(path, column);
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<MagnificationFilter> values) {
        writeColumnsStatic(w, path, values);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.MagnificationFilter";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }
}
