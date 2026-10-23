// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A line strip in 3D space.
 * 
 * A line strip is a list of points connected by line segments. It can be used to draw
 * approximations of smooth curves.
 * 
 * The points will be connected in order, like so:
 * {@code }`text
 *        2------3     5
 *       /        \   /
 * 0----1          \ /
 *                  4
 * {@code }`
 */
public record LineStrip3D(
    java.util.List<org.teamvan.moneyshift.encodings.Vec3D> points
) implements org.teamvan.moneyshift.NtComponent<LineStrip3D> {
    public LineStrip3D {
        points = java.util.List.copyOf(points);
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.LineStrip3D";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<LineStrip3D> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).points();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        long[] counts1 = new long[n];
        int total1 = 0;
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).points();
            java.util.List<org.teamvan.moneyshift.encodings.Vec3D> list1 = v1_0;
            counts1[i] = list1 == null ? 0L : list1.size();
            total1 += (int) counts1[i];
        }
        w.longs(path + "/.count", counts1);
        java.util.ArrayList<org.teamvan.moneyshift.encodings.Vec3D> items1 = new java.util.ArrayList<>(total1);
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).points();
            java.util.List<org.teamvan.moneyshift.encodings.Vec3D> list1 = v1_0;
            if (list1 != null) items1.addAll(list1);
        }
        int itemCount1 = items1.size();
        double[] column2 = new double[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v2_0 = items1.get(j1).xyz();
            var v2_1 = v2_0[0];
            double value2 = v2_1;
            column2[j1] = value2;
        }
        w.doubles(path + "/.items/0", column2);
        double[] column3 = new double[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v3_0 = items1.get(j1).xyz();
            var v3_1 = v3_0[1];
            double value3 = v3_1;
            column3[j1] = value3;
        }
        w.doubles(path + "/.items/1", column3);
        double[] column4 = new double[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v4_0 = items1.get(j1).xyz();
            var v4_1 = v4_0[2];
            double value4 = v4_1;
            column4[j1] = value4;
        }
        w.doubles(path + "/.items/2", column4);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<LineStrip3D> values) {
        writeColumnsStatic(w, path, values);
    }
}
