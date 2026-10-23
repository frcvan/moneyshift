// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A 1D range, specifying a lower and upper bound.
 */
public record Range1D(
    org.teamvan.moneyshift.encodings.Range1D range
) implements org.teamvan.moneyshift.NtComponent<Range1D> {
    public Range1D {
        java.util.Objects.requireNonNull(range, "range");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Range1D(double[] range) {
        this(new org.teamvan.moneyshift.encodings.Range1D(range));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Range1D";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Range1D> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).range();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        double[] column1 = new double[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).range();
            var v1_1 = v1_0 == null ? null : v1_0.range();
            var v1_2 = v1_1 == null ? null : v1_1[0];
            var value1 = v1_2;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).range();
            var v2_1 = v2_0 == null ? null : v2_0.range();
            var v2_2 = v2_1 == null ? null : v2_1[1];
            var value2 = v2_2;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/1", column2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Range1D> values) {
        writeColumnsStatic(w, path, values);
    }

    public static Range1D of(double... values) {
        if (values.length != 2) throw new java.lang.IllegalArgumentException("Range1D needs exactly 2 values, got " + values.length);
        return new Range1D(new org.teamvan.moneyshift.encodings.Range1D(values));
    }
}
