// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The three indices of a triangle in a triangle mesh.
 */
public record TriangleIndices(
    org.teamvan.moneyshift.encodings.UVec3D indices
) implements org.teamvan.moneyshift.NtComponent<TriangleIndices> {
    public TriangleIndices {
        java.util.Objects.requireNonNull(indices, "indices");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public TriangleIndices(long[] xyz) {
        this(new org.teamvan.moneyshift.encodings.UVec3D(xyz));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.TriangleIndices";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TriangleIndices> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).indices();
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
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).indices();
            var v1_1 = v1_0 == null ? null : v1_0.xyz();
            var v1_2 = v1_1 == null ? null : v1_1[0];
            var value1 = v1_2;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/0", column1);
        long[] column2 = new long[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).indices();
            var v2_1 = v2_0 == null ? null : v2_0.xyz();
            var v2_2 = v2_1 == null ? null : v2_1[1];
            var value2 = v2_2;
            column2[i] = value2 == null ? 0L : value2;
        }
        w.longs(path + "/1", column2);
        long[] column3 = new long[n];
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).indices();
            var v3_1 = v3_0 == null ? null : v3_0.xyz();
            var v3_2 = v3_1 == null ? null : v3_1[2];
            var value3 = v3_2;
            column3[i] = value3 == null ? 0L : value3;
        }
        w.longs(path + "/2", column3);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TriangleIndices> values) {
        writeColumnsStatic(w, path, values);
    }

    public static TriangleIndices of(long... values) {
        if (values.length != 3) throw new java.lang.IllegalArgumentException("TriangleIndices needs exactly 3 values, got " + values.length);
        return new TriangleIndices(new org.teamvan.moneyshift.encodings.UVec3D(values));
    }
}
