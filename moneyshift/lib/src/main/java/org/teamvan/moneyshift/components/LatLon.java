// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A geospatial position expressed in <a href="https://epsg.io/4326">EPSG:4326</a> latitude and longitude (North/East-positive degrees).
 */
public record LatLon(
    org.teamvan.moneyshift.encodings.DVec2D latLon
) implements org.teamvan.moneyshift.NtComponent<LatLon> {
    public LatLon {
        java.util.Objects.requireNonNull(latLon, "latLon");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public LatLon(double[] xy) {
        this(new org.teamvan.moneyshift.encodings.DVec2D(xy));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.LatLon";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<LatLon> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).latLon();
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
            var v1_0 = values.get(i) == null ? null : values.get(i).latLon();
            var v1_1 = v1_0 == null ? null : v1_0.xy();
            var v1_2 = v1_1 == null ? null : v1_1[0];
            var value1 = v1_2;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).latLon();
            var v2_1 = v2_0 == null ? null : v2_0.xy();
            var v2_2 = v2_1 == null ? null : v2_1[1];
            var value2 = v2_2;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/1", column2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<LatLon> values) {
        writeColumnsStatic(w, path, values);
    }

    public static LatLon of(double... values) {
        if (values.length != 2) throw new java.lang.IllegalArgumentException("LatLon needs exactly 2 values, got " + values.length);
        return new LatLon(new org.teamvan.moneyshift.encodings.DVec2D(values));
    }
}
