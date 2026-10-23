// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * An infinite 3D plane represented by a unit normal vector and a distance.
 * 
 * Any point P on the plane fulfills the equation {@code dot(xyz, P) - d = 0},
 * where {@code xyz} is the plane's normal and {@code d} the distance of the plane from the origin.
 * This representation is also known as the Hesse normal form.
 * 
 * Note: although the normal will be passed through to the
 * datastore as provided, when used in the Viewer, planes will always be normalized.
 * I.e. the plane with xyz = (2, 0, 0), d = 1 is equivalent to xyz = (1, 0, 0), d = 0.5
 */
public record Plane3D(
    org.teamvan.moneyshift.encodings.Plane3D xyzd
) implements org.teamvan.moneyshift.NtComponent<Plane3D> {
    public Plane3D {
        java.util.Objects.requireNonNull(xyzd, "xyzd");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Plane3D(float[] xyzd) {
        this(new org.teamvan.moneyshift.encodings.Plane3D(xyzd));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Plane3D";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Plane3D> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).xyzd();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        double[] column1 = new double[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).xyzd();
            var v1_1 = v1_0 == null ? null : v1_0.xyzd();
            var v1_2 = v1_1 == null ? null : v1_1[0];
            var value1 = v1_2;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).xyzd();
            var v2_1 = v2_0 == null ? null : v2_0.xyzd();
            var v2_2 = v2_1 == null ? null : v2_1[1];
            var value2 = v2_2;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/1", column2);
        double[] column3 = new double[n];
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).xyzd();
            var v3_1 = v3_0 == null ? null : v3_0.xyzd();
            var v3_2 = v3_1 == null ? null : v3_1[2];
            var value3 = v3_2;
            column3[i] = value3 == null ? 0.0 : value3;
        }
        w.doubles(path + "/2", column3);
        double[] column4 = new double[n];
        for (int i = 0; i < n; i++) {
            var v4_0 = values.get(i) == null ? null : values.get(i).xyzd();
            var v4_1 = v4_0 == null ? null : v4_0.xyzd();
            var v4_2 = v4_1 == null ? null : v4_1[3];
            var value4 = v4_2;
            column4[i] = value4 == null ? 0.0 : value4;
        }
        w.doubles(path + "/3", column4);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Plane3D> values) {
        writeColumnsStatic(w, path, values);
    }

    public static Plane3D of(float... values) {
        if (values.length != 4) throw new java.lang.IllegalArgumentException("Plane3D needs exactly 4 values, got " + values.length);
        return new Plane3D(new org.teamvan.moneyshift.encodings.Plane3D(values));
    }
}
