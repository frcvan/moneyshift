// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * An Axis-Aligned Bounding Box in 2D space, implemented as the minimum and maximum corners.
 */
public record Range2D(
    /**
     * The range of the X-axis (usually left and right bounds).
     */
    org.teamvan.moneyshift.encodings.Range1D xRange,
    /**
     * The range of the Y-axis (usually top and bottom bounds).
     */
    org.teamvan.moneyshift.encodings.Range1D yRange
) implements org.teamvan.moneyshift.NtEncoding<Range2D> {
    public Range2D {
        java.util.Objects.requireNonNull(xRange, "xRange");
        java.util.Objects.requireNonNull(yRange, "yRange");
    }
    public static final java.lang.String NAME = "rerun.encodings.Range2D";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Range2D> values) {
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
        double[] column1 = new double[n];
        for (int i = 0; i < n; i++) {
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.xRange();
            var v1_1 = v1_0 == null ? null : v1_0.range();
            var v1_2 = v1_1 == null ? null : v1_1[0];
            var value1 = v1_2;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/x_range/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_root = values.get(i);
            var v2_0 = v2_root == null ? null : v2_root.xRange();
            var v2_1 = v2_0 == null ? null : v2_0.range();
            var v2_2 = v2_1 == null ? null : v2_1[1];
            var value2 = v2_2;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/x_range/1", column2);
        double[] column3 = new double[n];
        for (int i = 0; i < n; i++) {
            var v3_root = values.get(i);
            var v3_0 = v3_root == null ? null : v3_root.yRange();
            var v3_1 = v3_0 == null ? null : v3_0.range();
            var v3_2 = v3_1 == null ? null : v3_1[0];
            var value3 = v3_2;
            column3[i] = value3 == null ? 0.0 : value3;
        }
        w.doubles(path + "/y_range/0", column3);
        double[] column4 = new double[n];
        for (int i = 0; i < n; i++) {
            var v4_root = values.get(i);
            var v4_0 = v4_root == null ? null : v4_root.yRange();
            var v4_1 = v4_0 == null ? null : v4_0.range();
            var v4_2 = v4_1 == null ? null : v4_1[1];
            var value4 = v4_2;
            column4[i] = value4 == null ? 0.0 : value4;
        }
        w.doubles(path + "/y_range/1", column4);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Range2D> values) {
        writeColumnsStatic(w, path, values);
    }
}
