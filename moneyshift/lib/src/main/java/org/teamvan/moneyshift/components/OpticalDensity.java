// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Dimensionless natural-log optical depth.
 * 
 * High values lead to more opaque surfaces, lower to more transparent ones.
 * More accurately, a value of 1 attenuates light to {@code exp(-1)} over the reference distance.
 */
public record OpticalDensity(
    org.teamvan.moneyshift.encodings.Float32 opticalDensity
) implements org.teamvan.moneyshift.NtComponent<OpticalDensity> {
    public OpticalDensity {
        java.util.Objects.requireNonNull(opticalDensity, "opticalDensity");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public OpticalDensity(float value) {
        this(new org.teamvan.moneyshift.encodings.Float32(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.OpticalDensity";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<OpticalDensity> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).opticalDensity();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        double[] column1 = new double[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).opticalDensity();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<OpticalDensity> values) {
        writeColumnsStatic(w, path, values);
    }
}
