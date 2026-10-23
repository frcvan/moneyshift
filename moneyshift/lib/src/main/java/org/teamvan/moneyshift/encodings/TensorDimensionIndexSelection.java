// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Indexing a specific tensor dimension.
 * 
 * Selecting {@code dimension=2} and {@code index=42} is similar to doing {@code tensor[:, :, 42, :, :, …]} in numpy.
 */
public record TensorDimensionIndexSelection(
    /**
     * The dimension number to select.
     */
    long dimension,
    /**
     * The index along the dimension to use.
     */
    long index
) implements org.teamvan.moneyshift.NtEncoding<TensorDimensionIndexSelection> {
    public TensorDimensionIndexSelection {
    }
    public static final java.lang.String NAME = "rerun.encodings.TensorDimensionIndexSelection";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TensorDimensionIndexSelection> values) {
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
        long[] column2 = new long[n];
        boolean unsigned2 = false;
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).index();
            var value2 = v2_0;
            column2[i] = value2 == null ? 0L : value2;
            unsigned2 |= column2[i] < 0L;
        }
        w.u64(path + "/index", column2, unsigned2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TensorDimensionIndexSelection> values) {
        writeColumnsStatic(w, path, values);
    }
}
