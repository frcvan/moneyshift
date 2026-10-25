// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * 3D rotation represented by a rotation around a given axis.
 * 
 * If normalization of the rotation axis fails the rotation is treated as an invalid transform, unless the
 * angle is zero in which case it is treated as an identity.
 */
public record RotationAxisAngle(
    org.teamvan.moneyshift.encodings.RotationAxisAngle rotation
) implements org.teamvan.moneyshift.NtComponent<RotationAxisAngle> {
    public RotationAxisAngle {
        java.util.Objects.requireNonNull(rotation, "rotation");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public RotationAxisAngle(org.teamvan.moneyshift.encodings.Vec3D axis, float angle) {
        this(new org.teamvan.moneyshift.encodings.RotationAxisAngle(axis, new org.teamvan.moneyshift.encodings.Angle(angle)));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.RotationAxisAngle";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<RotationAxisAngle> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_root = values.get(i);
            var v0_0 = v0_root == null ? null : v0_root.rotation();
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
            var v1_0 = v1_root == null ? null : v1_root.rotation();
            var v1_1 = v1_0 == null ? null : v1_0.axis();
            var v1_2 = v1_1 == null ? null : v1_1.xyz();
            var v1_3 = v1_2 == null ? null : v1_2[0];
            var value1 = v1_3;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/axis/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_root = values.get(i);
            var v2_0 = v2_root == null ? null : v2_root.rotation();
            var v2_1 = v2_0 == null ? null : v2_0.axis();
            var v2_2 = v2_1 == null ? null : v2_1.xyz();
            var v2_3 = v2_2 == null ? null : v2_2[1];
            var value2 = v2_3;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/axis/1", column2);
        double[] column3 = new double[n];
        for (int i = 0; i < n; i++) {
            var v3_root = values.get(i);
            var v3_0 = v3_root == null ? null : v3_root.rotation();
            var v3_1 = v3_0 == null ? null : v3_0.axis();
            var v3_2 = v3_1 == null ? null : v3_1.xyz();
            var v3_3 = v3_2 == null ? null : v3_2[2];
            var value3 = v3_3;
            column3[i] = value3 == null ? 0.0 : value3;
        }
        w.doubles(path + "/axis/2", column3);
        double[] column4 = new double[n];
        for (int i = 0; i < n; i++) {
            var v4_root = values.get(i);
            var v4_0 = v4_root == null ? null : v4_root.rotation();
            var v4_1 = v4_0 == null ? null : v4_0.angle();
            var v4_2 = v4_1 == null ? null : v4_1.radians();
            var value4 = v4_2;
            column4[i] = value4 == null ? 0.0 : value4;
        }
        w.doubles(path + "/angle", column4);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<RotationAxisAngle> values) {
        writeColumnsStatic(w, path, values);
    }
}
