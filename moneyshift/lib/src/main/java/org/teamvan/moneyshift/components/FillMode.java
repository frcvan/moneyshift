// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * How a geometric shape is drawn and colored.
 */
public enum FillMode implements org.teamvan.moneyshift.NtComponent<FillMode> {
    /**
     * Lines are drawn around the parts of the shape which directly correspond to the logged data.
     * 
     * Examples of what this means:
     * 
     * * An {@link org.teamvan.moneyshift.archetypes.Ellipsoids3D rerun::archetypes::Ellipsoids3D} will draw three axis-aligned ellipses that are cross-sections
     *   of each ellipsoid, each of which displays two out of three of the sizes of the ellipsoid.
     * * For {@link org.teamvan.moneyshift.archetypes.Boxes3D rerun::archetypes::Boxes3D}, it is the edges of the box, identical to {@link org.teamvan.moneyshift.components.FillMode#DenseWireframe rerun::components::FillMode::DenseWireframe}.
     */
    MajorWireframe(1L),
    /**
     * Many lines are drawn to represent the surface of the shape in a see-through fashion.
     * 
     * Examples of what this means:
     * 
     * * An {@link org.teamvan.moneyshift.archetypes.Ellipsoids3D rerun::archetypes::Ellipsoids3D} will draw a wireframe triangle mesh that approximates each
     *   ellipsoid.
     * * For {@link org.teamvan.moneyshift.archetypes.Boxes3D rerun::archetypes::Boxes3D}, it is the edges of the box, identical to {@link org.teamvan.moneyshift.components.FillMode#MajorWireframe rerun::components::FillMode::MajorWireframe}.
     */
    DenseWireframe(2L),
    /**
     * The surface of the shape is filled in with a solid color. No lines are drawn.
     */
    Solid(3L),
    /**
     * The surface of the shape is filled in with a transparent color, with major wireframe lines on top.
     * 
     * This gives a good default appearance that shows both the shape's surface and its structure.
     */
    TransparentFillMajorWireframe(4L);

    private final long value;

    FillMode(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static FillMode fromValue(long value) {
        for (FillMode candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown FillMode value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.FillMode";

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<FillMode> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = null;
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            FillMode value = values.get(i);
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
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<FillMode> values) {
        writeColumnsStatic(w, path, values);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.FillMode";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }
}
