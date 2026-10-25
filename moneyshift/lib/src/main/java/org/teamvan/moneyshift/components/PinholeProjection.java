// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Camera projection, from image coordinates to view coordinates.
 * 
 * Child from parent.
 * Image coordinates from camera view coordinates.
 * 
 * Example:
 * {@code }`text
 * 1496.1     0.0  980.5
 *    0.0  1496.1  744.5
 *    0.0     0.0    1.0
 * {@code }`
 */
public record PinholeProjection(
    org.teamvan.moneyshift.encodings.Mat3x3 imageFromCamera
) implements org.teamvan.moneyshift.NtComponent<PinholeProjection> {
    public PinholeProjection {
        java.util.Objects.requireNonNull(imageFromCamera, "imageFromCamera");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public PinholeProjection(float[] flatColumns) {
        this(new org.teamvan.moneyshift.encodings.Mat3x3(flatColumns));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.PinholeProjection";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<PinholeProjection> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).imageFromCamera();
            boolean ok0 = (v0_0) != null;
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
            var v1_0 = values.get(i) == null ? null : values.get(i).imageFromCamera();
            var v1_1 = v1_0 == null ? null : v1_0.flatColumns();
            var v1_2 = v1_1 == null ? null : v1_1[0];
            var value1 = v1_2;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).imageFromCamera();
            var v2_1 = v2_0 == null ? null : v2_0.flatColumns();
            var v2_2 = v2_1 == null ? null : v2_1[1];
            var value2 = v2_2;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/1", column2);
        double[] column3 = new double[n];
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).imageFromCamera();
            var v3_1 = v3_0 == null ? null : v3_0.flatColumns();
            var v3_2 = v3_1 == null ? null : v3_1[2];
            var value3 = v3_2;
            column3[i] = value3 == null ? 0.0 : value3;
        }
        w.doubles(path + "/2", column3);
        double[] column4 = new double[n];
        for (int i = 0; i < n; i++) {
            var v4_0 = values.get(i) == null ? null : values.get(i).imageFromCamera();
            var v4_1 = v4_0 == null ? null : v4_0.flatColumns();
            var v4_2 = v4_1 == null ? null : v4_1[3];
            var value4 = v4_2;
            column4[i] = value4 == null ? 0.0 : value4;
        }
        w.doubles(path + "/3", column4);
        double[] column5 = new double[n];
        for (int i = 0; i < n; i++) {
            var v5_0 = values.get(i) == null ? null : values.get(i).imageFromCamera();
            var v5_1 = v5_0 == null ? null : v5_0.flatColumns();
            var v5_2 = v5_1 == null ? null : v5_1[4];
            var value5 = v5_2;
            column5[i] = value5 == null ? 0.0 : value5;
        }
        w.doubles(path + "/4", column5);
        double[] column6 = new double[n];
        for (int i = 0; i < n; i++) {
            var v6_0 = values.get(i) == null ? null : values.get(i).imageFromCamera();
            var v6_1 = v6_0 == null ? null : v6_0.flatColumns();
            var v6_2 = v6_1 == null ? null : v6_1[5];
            var value6 = v6_2;
            column6[i] = value6 == null ? 0.0 : value6;
        }
        w.doubles(path + "/5", column6);
        double[] column7 = new double[n];
        for (int i = 0; i < n; i++) {
            var v7_0 = values.get(i) == null ? null : values.get(i).imageFromCamera();
            var v7_1 = v7_0 == null ? null : v7_0.flatColumns();
            var v7_2 = v7_1 == null ? null : v7_1[6];
            var value7 = v7_2;
            column7[i] = value7 == null ? 0.0 : value7;
        }
        w.doubles(path + "/6", column7);
        double[] column8 = new double[n];
        for (int i = 0; i < n; i++) {
            var v8_0 = values.get(i) == null ? null : values.get(i).imageFromCamera();
            var v8_1 = v8_0 == null ? null : v8_0.flatColumns();
            var v8_2 = v8_1 == null ? null : v8_1[7];
            var value8 = v8_2;
            column8[i] = value8 == null ? 0.0 : value8;
        }
        w.doubles(path + "/7", column8);
        double[] column9 = new double[n];
        for (int i = 0; i < n; i++) {
            var v9_0 = values.get(i) == null ? null : values.get(i).imageFromCamera();
            var v9_1 = v9_0 == null ? null : v9_0.flatColumns();
            var v9_2 = v9_1 == null ? null : v9_1[8];
            var value9 = v9_2;
            column9[i] = value9 == null ? 0.0 : value9;
        }
        w.doubles(path + "/8", column9);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<PinholeProjection> values) {
        writeColumnsStatic(w, path, values);
    }

    public static PinholeProjection of(float... values) {
        if (values.length != 9) throw new java.lang.IllegalArgumentException("PinholeProjection needs exactly 9 values, got " + values.length);
        return new PinholeProjection(new org.teamvan.moneyshift.encodings.Mat3x3(values));
    }
}
