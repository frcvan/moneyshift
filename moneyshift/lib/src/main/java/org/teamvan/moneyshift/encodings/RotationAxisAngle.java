// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * 3D rotation represented by a rotation around a given axis.
 */
public record RotationAxisAngle(
    /**
     * Axis to rotate around.
     * 
     * This is not required to be normalized.
     * However, if normalization of the rotation axis fails (typically due to a zero vector)
     * the rotation is treated as an invalid transform, unless the angle is zero in which case
     * it is treated as an identity.
     */
    org.teamvan.moneyshift.encodings.Vec3D axis,
    /**
     * How much to rotate around the axis.
     */
    org.teamvan.moneyshift.encodings.Angle angle
) implements org.teamvan.moneyshift.NtEncoding<RotationAxisAngle> {
    public RotationAxisAngle {
        java.util.Objects.requireNonNull(axis, "axis");
        java.util.Objects.requireNonNull(angle, "angle");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public RotationAxisAngle(org.teamvan.moneyshift.encodings.Vec3D axis, float angle) {
        this(axis, new org.teamvan.moneyshift.encodings.Angle(angle));
    }

    public static final java.lang.String NAME = "rerun.encodings.RotationAxisAngle";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<RotationAxisAngle> values) {
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
            var v1_0 = values.get(i) == null ? null : values.get(i).axis();
            var v1_1 = v1_0 == null ? null : v1_0.xyz();
            var v1_2 = v1_1 == null ? null : v1_1[0];
            var value1 = v1_2;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/axis/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).axis();
            var v2_1 = v2_0 == null ? null : v2_0.xyz();
            var v2_2 = v2_1 == null ? null : v2_1[1];
            var value2 = v2_2;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/axis/1", column2);
        double[] column3 = new double[n];
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).axis();
            var v3_1 = v3_0 == null ? null : v3_0.xyz();
            var v3_2 = v3_1 == null ? null : v3_1[2];
            var value3 = v3_2;
            column3[i] = value3 == null ? 0.0 : value3;
        }
        w.doubles(path + "/axis/2", column3);
        double[] column4 = new double[n];
        for (int i = 0; i < n; i++) {
            var v4_0 = values.get(i) == null ? null : values.get(i).angle();
            var v4_1 = v4_0 == null ? null : v4_0.radians();
            var value4 = v4_1;
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
