// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The width of a stroke specified in UI points.
 */
public record StrokeWidth(
    org.teamvan.moneyshift.encodings.Float32 width
) implements org.teamvan.moneyshift.NtComponent<StrokeWidth> {
    public StrokeWidth {
        java.util.Objects.requireNonNull(width, "width");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public StrokeWidth(float value) {
        this(new org.teamvan.moneyshift.encodings.Float32(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.StrokeWidth";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<StrokeWidth> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).width();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        double[] column1 = new double[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).width();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<StrokeWidth> values) {
        writeColumnsStatic(w, path, values);
    }
}
