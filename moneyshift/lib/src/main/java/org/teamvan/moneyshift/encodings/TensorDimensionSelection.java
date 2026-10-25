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
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
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
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.dimension();
            var value1 = v1_0;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/dimension", column1);
        boolean[] column2 = new boolean[n];
        for (int i = 0; i < n; i++) {
            var v2_root = values.get(i);
            var v2_0 = v2_root == null ? null : v2_root.invert();
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
