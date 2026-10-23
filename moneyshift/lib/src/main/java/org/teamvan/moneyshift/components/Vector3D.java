// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A vector in 3D space.
 */
public record Vector3D(
    org.teamvan.moneyshift.encodings.Vec3D vector
) implements org.teamvan.moneyshift.NtComponent<Vector3D> {
    public Vector3D {
        java.util.Objects.requireNonNull(vector, "vector");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Vector3D(float[] xyz) {
        this(new org.teamvan.moneyshift.encodings.Vec3D(xyz));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Vector3D";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Vector3D> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).vector();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        double[] column1 = new double[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).vector();
            var v1_1 = v1_0 == null ? null : v1_0.xyz();
            var v1_2 = v1_1 == null ? null : v1_1[0];
            var value1 = v1_2;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).vector();
            var v2_1 = v2_0 == null ? null : v2_0.xyz();
            var v2_2 = v2_1 == null ? null : v2_1[1];
            var value2 = v2_2;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/1", column2);
        double[] column3 = new double[n];
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).vector();
            var v3_1 = v3_0 == null ? null : v3_0.xyz();
            var v3_2 = v3_1 == null ? null : v3_1[2];
            var value3 = v3_2;
            column3[i] = value3 == null ? 0.0 : value3;
        }
        w.doubles(path + "/2", column3);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Vector3D> values) {
        writeColumnsStatic(w, path, values);
    }

    public static Vector3D of(float... values) {
        if (values.length != 3) throw new java.lang.IllegalArgumentException("Vector3D needs exactly 3 values, got " + values.length);
        return new Vector3D(new org.teamvan.moneyshift.encodings.Vec3D(values));
    }
}
