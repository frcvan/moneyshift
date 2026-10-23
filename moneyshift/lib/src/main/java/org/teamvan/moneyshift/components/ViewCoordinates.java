// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * An orientation convention for a camera or 3D view.
 * 
 * On {@link org.teamvan.moneyshift.archetypes.Pinhole rerun::archetypes::Pinhole}, this component controls the camera orientation and projection direction.
 * On <a href="https://rerun.io/docs/reference/types/views/spatial3d_view">SpatialInformation</a>, it controls the 3D view's eye orientation, navigation, and default grid plane.
 * A logged {@link org.teamvan.moneyshift.archetypes.ViewCoordinates rerun::archetypes::ViewCoordinates} provides the default for <a href="https://rerun.io/docs/reference/types/views/spatial3d_view">SpatialInformation</a>.
 * 
 * The three directions are always ordered as The three directions are always ordered as [x, y, z] and specify where each positive axis points.
 * For example, For example, [Right, Down, Forward] means that +X points right, +Y points down, and +Z points forward.
 * 
 * ⚠ <a href="https://github.com/rerun-io/rerun/issues/5032">Rerun does not yet support left-handed coordinate systems</a>.
 */
public record ViewCoordinates(
    /**
     * The directions of the The directions of the [x, y, z] axes.
     */
    org.teamvan.moneyshift.encodings.ViewCoordinates coordinates
) implements org.teamvan.moneyshift.NtComponent<ViewCoordinates> {
    public ViewCoordinates {
        java.util.Objects.requireNonNull(coordinates, "coordinates");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ViewCoordinates(org.teamvan.moneyshift.encodings.ViewDir[] coordinates) {
        this(new org.teamvan.moneyshift.encodings.ViewCoordinates(coordinates));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ViewCoordinates";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ViewCoordinates> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).coordinates();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).coordinates();
            var v1_1 = v1_0 == null ? null : v1_0.coordinates();
            var v1_2 = v1_1 == null ? null : v1_1[0];
            org.teamvan.moneyshift.encodings.ViewDir value1 = v1_2;
            column1[i] = value1 == null ? 0L : value1.value();
        }
        w.longs(path + "/0", column1);
        long[] column2 = new long[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).coordinates();
            var v2_1 = v2_0 == null ? null : v2_0.coordinates();
            var v2_2 = v2_1 == null ? null : v2_1[1];
            org.teamvan.moneyshift.encodings.ViewDir value2 = v2_2;
            column2[i] = value2 == null ? 0L : value2.value();
        }
        w.longs(path + "/1", column2);
        long[] column3 = new long[n];
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).coordinates();
            var v3_1 = v3_0 == null ? null : v3_0.coordinates();
            var v3_2 = v3_1 == null ? null : v3_1[2];
            org.teamvan.moneyshift.encodings.ViewDir value3 = v3_2;
            column3[i] = value3 == null ? 0L : value3.value();
        }
        w.longs(path + "/2", column3);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ViewCoordinates> values) {
        writeColumnsStatic(w, path, values);
    }
}
