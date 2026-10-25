// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Range of expected or valid values, specifying a lower and upper bound.
 */
public record ValueRange(
    org.teamvan.moneyshift.encodings.Range1D range
) implements org.teamvan.moneyshift.NtComponent<ValueRange> {
    public ValueRange {
        java.util.Objects.requireNonNull(range, "range");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ValueRange(double[] range) {
        this(new org.teamvan.moneyshift.encodings.Range1D(range));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ValueRange";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ValueRange> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_root = values.get(i);
            var v0_0 = v0_root == null ? null : v0_root.range();
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
            var v1_0 = v1_root == null ? null : v1_root.range();
            var v1_1 = v1_0 == null ? null : v1_0.range();
            var v1_2 = v1_1 == null ? null : v1_1[0];
            var value1 = v1_2;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_root = values.get(i);
            var v2_0 = v2_root == null ? null : v2_root.range();
            var v2_1 = v2_0 == null ? null : v2_0.range();
            var v2_2 = v2_1 == null ? null : v2_1[1];
            var value2 = v2_2;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/1", column2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ValueRange> values) {
        writeColumnsStatic(w, path, values);
    }

    public static ValueRange of(double... values) {
        if (values.length != 2) throw new java.lang.IllegalArgumentException("ValueRange needs exactly 2 values, got " + values.length);
        return new ValueRange(new org.teamvan.moneyshift.encodings.Range1D(values));
    }
}
