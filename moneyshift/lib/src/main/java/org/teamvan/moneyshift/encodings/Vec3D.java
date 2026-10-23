// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A vector in 3D space.
 */
public record Vec3D(
    float[] xyz
) implements org.teamvan.moneyshift.NtEncoding<Vec3D> {
    public Vec3D {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof Vec3D that)) return false;
        return java.util.Arrays.equals(xyz, that.xyz);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(xyz));
    }
    public static final java.lang.String NAME = "rerun.encodings.Vec3D";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Vec3D> values) {
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
            var v1_0 = values.get(i) == null ? null : values.get(i).xyz();
            var v1_1 = v1_0 == null ? null : v1_0[0];
            var value1 = v1_1;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).xyz();
            var v2_1 = v2_0 == null ? null : v2_0[1];
            var value2 = v2_1;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/1", column2);
        double[] column3 = new double[n];
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).xyz();
            var v3_1 = v3_0 == null ? null : v3_0[2];
            var value3 = v3_1;
            column3[i] = value3 == null ? 0.0 : value3;
        }
        w.doubles(path + "/2", column3);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Vec3D> values) {
        writeColumnsStatic(w, path, values);
    }

    public static Vec3D of(float... values) {
        if (values.length != 3) throw new java.lang.IllegalArgumentException("Vec3D needs exactly 3 values, got " + values.length);
        return new Vec3D(values);
    }
}
