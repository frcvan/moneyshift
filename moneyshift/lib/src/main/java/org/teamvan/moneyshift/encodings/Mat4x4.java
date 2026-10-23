// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A 4x4 Matrix.
 * 
 * Matrices in Rerun are stored as flat list of coefficients in column-major order:
 * {@code }`text
 *            column 0         column 1         column 2         column 3
 *        --------------------------------------------------------------------
 * row 0 | flat_columnsrow 0 | flat_columns[0]  flat_columns[4]  flat_columns[8]  flat_columns[12]
 * row 1 | flat_columnsrow 1 | flat_columns[1]  flat_columns[5]  flat_columns[9]  flat_columns[13]
 * row 2 | flat_columnsrow 2 | flat_columns[2]  flat_columns[6]  flat_columns[10] flat_columns[14]
 * row 3 | flat_columnsrow 3 | flat_columns[3]  flat_columns[7]  flat_columns[11] flat_columns[15]
 * {@code }`
 * 
 */
public record Mat4x4(
    /**
     * Flat list of matrix coefficients in column-major order.
     */
    float[] flatColumns
) implements org.teamvan.moneyshift.NtEncoding<Mat4x4> {
    public Mat4x4 {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof Mat4x4 that)) return false;
        return java.util.Arrays.equals(flatColumns, that.flatColumns);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(flatColumns));
    }
    public static final java.lang.String NAME = "rerun.encodings.Mat4x4";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Mat4x4> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        double[] column1 = new double[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v1_1 = v1_0 == null ? null : v1_0[0];
            var value1 = v1_1;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v2_1 = v2_0 == null ? null : v2_0[1];
            var value2 = v2_1;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/1", column2);
        double[] column3 = new double[n];
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v3_1 = v3_0 == null ? null : v3_0[2];
            var value3 = v3_1;
            column3[i] = value3 == null ? 0.0 : value3;
        }
        w.doubles(path + "/2", column3);
        double[] column4 = new double[n];
        for (int i = 0; i < n; i++) {
            var v4_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v4_1 = v4_0 == null ? null : v4_0[3];
            var value4 = v4_1;
            column4[i] = value4 == null ? 0.0 : value4;
        }
        w.doubles(path + "/3", column4);
        double[] column5 = new double[n];
        for (int i = 0; i < n; i++) {
            var v5_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v5_1 = v5_0 == null ? null : v5_0[4];
            var value5 = v5_1;
            column5[i] = value5 == null ? 0.0 : value5;
        }
        w.doubles(path + "/4", column5);
        double[] column6 = new double[n];
        for (int i = 0; i < n; i++) {
            var v6_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v6_1 = v6_0 == null ? null : v6_0[5];
            var value6 = v6_1;
            column6[i] = value6 == null ? 0.0 : value6;
        }
        w.doubles(path + "/5", column6);
        double[] column7 = new double[n];
        for (int i = 0; i < n; i++) {
            var v7_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v7_1 = v7_0 == null ? null : v7_0[6];
            var value7 = v7_1;
            column7[i] = value7 == null ? 0.0 : value7;
        }
        w.doubles(path + "/6", column7);
        double[] column8 = new double[n];
        for (int i = 0; i < n; i++) {
            var v8_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v8_1 = v8_0 == null ? null : v8_0[7];
            var value8 = v8_1;
            column8[i] = value8 == null ? 0.0 : value8;
        }
        w.doubles(path + "/7", column8);
        double[] column9 = new double[n];
        for (int i = 0; i < n; i++) {
            var v9_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v9_1 = v9_0 == null ? null : v9_0[8];
            var value9 = v9_1;
            column9[i] = value9 == null ? 0.0 : value9;
        }
        w.doubles(path + "/8", column9);
        double[] column10 = new double[n];
        for (int i = 0; i < n; i++) {
            var v10_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v10_1 = v10_0 == null ? null : v10_0[9];
            var value10 = v10_1;
            column10[i] = value10 == null ? 0.0 : value10;
        }
        w.doubles(path + "/9", column10);
        double[] column11 = new double[n];
        for (int i = 0; i < n; i++) {
            var v11_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v11_1 = v11_0 == null ? null : v11_0[10];
            var value11 = v11_1;
            column11[i] = value11 == null ? 0.0 : value11;
        }
        w.doubles(path + "/10", column11);
        double[] column12 = new double[n];
        for (int i = 0; i < n; i++) {
            var v12_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v12_1 = v12_0 == null ? null : v12_0[11];
            var value12 = v12_1;
            column12[i] = value12 == null ? 0.0 : value12;
        }
        w.doubles(path + "/11", column12);
        double[] column13 = new double[n];
        for (int i = 0; i < n; i++) {
            var v13_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v13_1 = v13_0 == null ? null : v13_0[12];
            var value13 = v13_1;
            column13[i] = value13 == null ? 0.0 : value13;
        }
        w.doubles(path + "/12", column13);
        double[] column14 = new double[n];
        for (int i = 0; i < n; i++) {
            var v14_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v14_1 = v14_0 == null ? null : v14_0[13];
            var value14 = v14_1;
            column14[i] = value14 == null ? 0.0 : value14;
        }
        w.doubles(path + "/13", column14);
        double[] column15 = new double[n];
        for (int i = 0; i < n; i++) {
            var v15_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v15_1 = v15_0 == null ? null : v15_0[14];
            var value15 = v15_1;
            column15[i] = value15 == null ? 0.0 : value15;
        }
        w.doubles(path + "/14", column15);
        double[] column16 = new double[n];
        for (int i = 0; i < n; i++) {
            var v16_0 = values.get(i) == null ? null : values.get(i).flatColumns();
            var v16_1 = v16_0 == null ? null : v16_0[15];
            var value16 = v16_1;
            column16[i] = value16 == null ? 0.0 : value16;
        }
        w.doubles(path + "/15", column16);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Mat4x4> values) {
        writeColumnsStatic(w, path, values);
    }

    public static Mat4x4 of(float... values) {
        if (values.length != 16) throw new java.lang.IllegalArgumentException("Mat4x4 needs exactly 16 values, got " + values.length);
        return new Mat4x4(values);
    }
}
