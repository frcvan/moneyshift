// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A 3D rotation expressed as a quaternion.
 * 
 * Note: although the x,y,z,w components of the quaternion will be passed through to the
 * datastore as provided, when used in the Viewer, quaternions will always be normalized.
 * If normalization fails the rotation is treated as an invalid transform.
 */
public record RotationQuat(
    org.teamvan.moneyshift.encodings.Quaternion quaternion
) implements org.teamvan.moneyshift.NtComponent<RotationQuat> {
    public RotationQuat {
        java.util.Objects.requireNonNull(quaternion, "quaternion");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public RotationQuat(float[] xyzw) {
        this(new org.teamvan.moneyshift.encodings.Quaternion(xyzw));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.RotationQuat";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<RotationQuat> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_root = values.get(i);
            var v0_0 = v0_root == null ? null : v0_root.quaternion();
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
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.quaternion();
            var v1_1 = v1_0 == null ? null : v1_0.xyzw();
            var v1_2 = v1_1 == null ? null : v1_1[0];
            var value1 = v1_2;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_root = values.get(i);
            var v2_0 = v2_root == null ? null : v2_root.quaternion();
            var v2_1 = v2_0 == null ? null : v2_0.xyzw();
            var v2_2 = v2_1 == null ? null : v2_1[1];
            var value2 = v2_2;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/1", column2);
        double[] column3 = new double[n];
        for (int i = 0; i < n; i++) {
            var v3_root = values.get(i);
            var v3_0 = v3_root == null ? null : v3_root.quaternion();
            var v3_1 = v3_0 == null ? null : v3_0.xyzw();
            var v3_2 = v3_1 == null ? null : v3_1[2];
            var value3 = v3_2;
            column3[i] = value3 == null ? 0.0 : value3;
        }
        w.doubles(path + "/2", column3);
        double[] column4 = new double[n];
        for (int i = 0; i < n; i++) {
            var v4_root = values.get(i);
            var v4_0 = v4_root == null ? null : v4_root.quaternion();
            var v4_1 = v4_0 == null ? null : v4_0.xyzw();
            var v4_2 = v4_1 == null ? null : v4_1[3];
            var value4 = v4_2;
            column4[i] = value4 == null ? 0.0 : value4;
        }
        w.doubles(path + "/3", column4);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<RotationQuat> values) {
        writeColumnsStatic(w, path, values);
    }

    public static RotationQuat of(float... values) {
        if (values.length != 4) throw new java.lang.IllegalArgumentException("RotationQuat needs exactly 4 values, got " + values.length);
        return new RotationQuat(new org.teamvan.moneyshift.encodings.Quaternion(values));
    }
}
