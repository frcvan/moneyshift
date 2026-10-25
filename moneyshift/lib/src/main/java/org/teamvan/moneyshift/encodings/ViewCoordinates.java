// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * An orientation convention for three-dimensional coordinates.
 * 
 * The three directions are always ordered as The three directions are always ordered as [x, y, z] and specify where each positive axis points.
 * For example, For example, [Right, Down, Forward] means that +X points right, +Y points down, and +Z points forward.
 * 
 * ⚠ <a href="https://github.com/rerun-io/rerun/issues/5032">Rerun does not yet support left-handed coordinate systems</a>.
 * 
 */
public record ViewCoordinates(
    /**
     * The directions of the The directions of the [x, y, z] axes.
     */
    org.teamvan.moneyshift.encodings.ViewDir[] coordinates
) implements org.teamvan.moneyshift.NtEncoding<ViewCoordinates> {
    public ViewCoordinates {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof ViewCoordinates that)) return false;
        return java.util.Arrays.equals(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(coordinates));
    }
    public static final java.lang.String NAME = "rerun.encodings.ViewCoordinates";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ViewCoordinates> values) {
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
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.coordinates();
            var v1_1 = v1_0 == null ? null : v1_0[0];
            org.teamvan.moneyshift.encodings.ViewDir value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1.value();
        }
        w.longs(path + "/0", column1);
        long[] column2 = new long[n];
        for (int i = 0; i < n; i++) {
            var v2_root = values.get(i);
            var v2_0 = v2_root == null ? null : v2_root.coordinates();
            var v2_1 = v2_0 == null ? null : v2_0[1];
            org.teamvan.moneyshift.encodings.ViewDir value2 = v2_1;
            column2[i] = value2 == null ? 0L : value2.value();
        }
        w.longs(path + "/1", column2);
        long[] column3 = new long[n];
        for (int i = 0; i < n; i++) {
            var v3_root = values.get(i);
            var v3_0 = v3_root == null ? null : v3_root.coordinates();
            var v3_1 = v3_0 == null ? null : v3_0[2];
            org.teamvan.moneyshift.encodings.ViewDir value3 = v3_1;
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
