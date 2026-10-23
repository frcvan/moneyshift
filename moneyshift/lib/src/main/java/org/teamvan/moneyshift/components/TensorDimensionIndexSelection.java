// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Specifies a concrete index on a tensor dimension.
 */
public record TensorDimensionIndexSelection(
    org.teamvan.moneyshift.encodings.TensorDimensionIndexSelection selection
) implements org.teamvan.moneyshift.NtComponent<TensorDimensionIndexSelection> {
    public TensorDimensionIndexSelection {
        java.util.Objects.requireNonNull(selection, "selection");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public TensorDimensionIndexSelection(long dimension, long index) {
        this(new org.teamvan.moneyshift.encodings.TensorDimensionIndexSelection(dimension, index));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.TensorDimensionIndexSelection";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TensorDimensionIndexSelection> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).selection();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).selection();
            var v1_1 = v1_0 == null ? null : v1_0.dimension();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/dimension", column1);
        long[] column2 = new long[n];
        boolean unsigned2 = false;
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).selection();
            var v2_1 = v2_0 == null ? null : v2_0.index();
            var value2 = v2_1;
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
