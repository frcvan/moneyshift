// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Specifies which dimension to use for width.
 */
public record TensorWidthDimension(
    org.teamvan.moneyshift.encodings.TensorDimensionSelection dimension
) implements org.teamvan.moneyshift.NtComponent<TensorWidthDimension> {
    public TensorWidthDimension {
        java.util.Objects.requireNonNull(dimension, "dimension");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public TensorWidthDimension(long dimension, boolean invert) {
        this(new org.teamvan.moneyshift.encodings.TensorDimensionSelection(dimension, invert));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.TensorWidthDimension";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TensorWidthDimension> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).dimension();
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
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).dimension();
            var v1_1 = v1_0 == null ? null : v1_0.dimension();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/dimension", column1);
        boolean[] column2 = new boolean[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).dimension();
            var v2_1 = v2_0 == null ? null : v2_0.invert();
            var value2 = v2_1;
            column2[i] = value2 == null ? false : value2;
        }
        w.booleans(path + "/invert", column2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TensorWidthDimension> values) {
        writeColumnsStatic(w, path, values);
    }
}
