// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A vector in 4D space.
 */
public record Vec4D(
    float[] xyzw
) implements org.teamvan.moneyshift.NtEncoding<Vec4D> {
    public Vec4D {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof Vec4D that)) return false;
        return java.util.Arrays.equals(xyzw, that.xyzw);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(xyzw));
    }
    public static final java.lang.String NAME = "rerun.encodings.Vec4D";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Vec4D> values) {
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
            var v1_0 = values.get(i) == null ? null : values.get(i).xyzw();
            var v1_1 = v1_0 == null ? null : v1_0[0];
            var value1 = v1_1;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).xyzw();
            var v2_1 = v2_0 == null ? null : v2_0[1];
            var value2 = v2_1;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/1", column2);
        double[] column3 = new double[n];
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).xyzw();
            var v3_1 = v3_0 == null ? null : v3_0[2];
            var value3 = v3_1;
            column3[i] = value3 == null ? 0.0 : value3;
        }
        w.doubles(path + "/2", column3);
        double[] column4 = new double[n];
        for (int i = 0; i < n; i++) {
            var v4_0 = values.get(i) == null ? null : values.get(i).xyzw();
            var v4_1 = v4_0 == null ? null : v4_0[3];
            var value4 = v4_1;
            column4[i] = value4 == null ? 0.0 : value4;
        }
        w.doubles(path + "/3", column4);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Vec4D> values) {
        writeColumnsStatic(w, path, values);
    }

    public static Vec4D of(float... values) {
        if (values.length != 4) throw new java.lang.IllegalArgumentException("Vec4D needs exactly 4 values, got " + values.length);
        return new Vec4D(values);
    }
}
