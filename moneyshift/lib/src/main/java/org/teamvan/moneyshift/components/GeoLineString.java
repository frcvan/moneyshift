// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A geospatial line string expressed in <a href="https://epsg.io/4326">EPSG:4326</a> latitude and longitude (North/East-positive degrees).
 */
public record GeoLineString(
    java.util.List<org.teamvan.moneyshift.encodings.DVec2D> latLon
) implements org.teamvan.moneyshift.NtComponent<GeoLineString> {
    public GeoLineString {
        latLon = java.util.List.copyOf(latLon);
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.GeoLineString";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<GeoLineString> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_root = values.get(i);
            var v0_0 = v0_root == null ? null : v0_root.latLon();
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
        long[] offsets1 = new long[n + 1];
        int total1 = 0;
        for (int i = 0; i < n; i++) {
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.latLon();
            java.util.List<org.teamvan.moneyshift.encodings.DVec2D> list1 = v1_0;
            total1 += list1 == null ? 0 : list1.size();
            offsets1[i + 1] = total1;
        }
        w.longs(path + "/.offsets", offsets1);
        java.util.ArrayList<org.teamvan.moneyshift.encodings.DVec2D> items1 = new java.util.ArrayList<>(total1);
        for (int i = 0; i < n; i++) {
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.latLon();
            java.util.List<org.teamvan.moneyshift.encodings.DVec2D> list1 = v1_0;
            if (list1 != null) items1.addAll(list1);
        }
        int itemCount1 = items1.size();
        double[] column2 = new double[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v2_root = items1.get(j1);
            var v2_0 = v2_root.xy();
            var v2_1 = v2_0[0];
            double value2 = v2_1;
            column2[j1] = value2;
        }
        w.doubles(path + "/.items/0", column2);
        double[] column3 = new double[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v3_root = items1.get(j1);
            var v3_0 = v3_root.xy();
            var v3_1 = v3_0[1];
            double value3 = v3_1;
            column3[j1] = value3;
        }
        w.doubles(path + "/.items/1", column3);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<GeoLineString> values) {
        writeColumnsStatic(w, path, values);
    }
}
