// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Angle in radians.
 */
public record Angle(
    /**
     * Angle in radians. One turn is equal to 2π (or τ) radians.
     */
    float radians
) implements org.teamvan.moneyshift.NtEncoding<Angle> {
    public Angle {
    }
    public static final java.lang.String NAME = "rerun.encodings.Angle";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Angle> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        double[] column1 = new double[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).radians();
            var value1 = v1_0;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Angle> values) {
        writeColumnsStatic(w, path, values);
    }
}
