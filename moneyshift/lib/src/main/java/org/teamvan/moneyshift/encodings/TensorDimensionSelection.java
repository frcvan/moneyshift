// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Selection of a single tensor dimension.
 */
public record TensorDimensionSelection(
    /**
     * The dimension number to select.
     */
    long dimension,
    /**
     * Invert the direction of the dimension.
     */
    boolean invert
) implements org.teamvan.moneyshift.NtEncoding<TensorDimensionSelection> {
    public TensorDimensionSelection {
    }
    public static final java.lang.String NAME = "rerun.encodings.TensorDimensionSelection";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TensorDimensionSelection> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).dimension();
            var value1 = v1_0;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/dimension", column1);
        boolean[] column2 = new boolean[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).invert();
            var value2 = v2_0;
            column2[i] = value2 == null ? false : value2;
        }
        w.booleans(path + "/invert", column2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TensorDimensionSelection> values) {
        writeColumnsStatic(w, path, values);
    }
}
