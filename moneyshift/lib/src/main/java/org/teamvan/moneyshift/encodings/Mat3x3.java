// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A 3x3 Matrix.
 * 
 * Matrices in Rerun are stored as flat list of coefficients in column-major order:
 * {@code }`text
 *             column 0       column 1       column 2
 *        -------------------------------------------------
 * row 0 | flat_columnsrow 0 | flat_columns[0] flat_columns[3] flat_columns[6]
 * row 1 | flat_columnsrow 1 | flat_columns[1] flat_columns[4] flat_columns[7]
 * row 2 | flat_columnsrow 2 | flat_columns[2] flat_columns[5] flat_columns[8]
 * {@code }`
 * 
 */
public record Mat3x3(
    /**
     * Flat list of matrix coefficients in column-major order.
     */
    float[] flatColumns
) implements org.teamvan.moneyshift.NtEncoding<Mat3x3> {
    public Mat3x3 {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof Mat3x3 that)) return false;
        return java.util.Arrays.equals(flatColumns, that.flatColumns);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(flatColumns));
    }
    public static final java.lang.String NAME = "rerun.encodings.Mat3x3";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Mat3x3> values) {
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
            var v1_0 = v1_root == null ? null : v1_root.flatColumns();
            var v1_1 = v1_0 == null ? null : v1_0[0];
            var value1 = v1_1;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_root = values.get(i);
            var v2_0 = v2_root == null ? null : v2_root.flatColumns();
            var v2_1 = v2_0 == null ? null : v2_0[1];
            var value2 = v2_1;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/1", column2);
        double[] column3 = new double[n];
        for (int i = 0; i < n; i++) {
            var v3_root = values.get(i);
            var v3_0 = v3_root == null ? null : v3_root.flatColumns();
            var v3_1 = v3_0 == null ? null : v3_0[2];
            var value3 = v3_1;
            column3[i] = value3 == null ? 0.0 : value3;
        }
        w.doubles(path + "/2", column3);
        double[] column4 = new double[n];
        for (int i = 0; i < n; i++) {
            var v4_root = values.get(i);
            var v4_0 = v4_root == null ? null : v4_root.flatColumns();
            var v4_1 = v4_0 == null ? null : v4_0[3];
            var value4 = v4_1;
            column4[i] = value4 == null ? 0.0 : value4;
        }
        w.doubles(path + "/3", column4);
        double[] column5 = new double[n];
        for (int i = 0; i < n; i++) {
            var v5_root = values.get(i);
            var v5_0 = v5_root == null ? null : v5_root.flatColumns();
            var v5_1 = v5_0 == null ? null : v5_0[4];
            var value5 = v5_1;
            column5[i] = value5 == null ? 0.0 : value5;
        }
        w.doubles(path + "/4", column5);
        double[] column6 = new double[n];
        for (int i = 0; i < n; i++) {
            var v6_root = values.get(i);
            var v6_0 = v6_root == null ? null : v6_root.flatColumns();
            var v6_1 = v6_0 == null ? null : v6_0[5];
            var value6 = v6_1;
            column6[i] = value6 == null ? 0.0 : value6;
        }
        w.doubles(path + "/5", column6);
        double[] column7 = new double[n];
        for (int i = 0; i < n; i++) {
            var v7_root = values.get(i);
            var v7_0 = v7_root == null ? null : v7_root.flatColumns();
            var v7_1 = v7_0 == null ? null : v7_0[6];
            var value7 = v7_1;
            column7[i] = value7 == null ? 0.0 : value7;
        }
        w.doubles(path + "/6", column7);
        double[] column8 = new double[n];
        for (int i = 0; i < n; i++) {
            var v8_root = values.get(i);
            var v8_0 = v8_root == null ? null : v8_root.flatColumns();
            var v8_1 = v8_0 == null ? null : v8_0[7];
            var value8 = v8_1;
            column8[i] = value8 == null ? 0.0 : value8;
        }
        w.doubles(path + "/7", column8);
        double[] column9 = new double[n];
        for (int i = 0; i < n; i++) {
            var v9_root = values.get(i);
            var v9_0 = v9_root == null ? null : v9_root.flatColumns();
            var v9_1 = v9_0 == null ? null : v9_0[8];
            var value9 = v9_1;
            column9[i] = value9 == null ? 0.0 : value9;
        }
        w.doubles(path + "/8", column9);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Mat3x3> values) {
        writeColumnsStatic(w, path, values);
    }

    public static Mat3x3 of(float... values) {
        if (values.length != 9) throw new java.lang.IllegalArgumentException("Mat3x3 needs exactly 9 values, got " + values.length);
        return new Mat3x3(values);
    }
}
