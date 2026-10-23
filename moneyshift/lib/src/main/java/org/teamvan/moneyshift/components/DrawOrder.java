// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Draw order of 2D elements. Higher values are drawn on top of lower values.
 * 
 * An entity can have only a single draw order component.
 * Within an entity draw order is governed by the order of the components.
 * 
 * Draw order for entities with the same draw order is generally undefined.
 */
public record DrawOrder(
    org.teamvan.moneyshift.encodings.Float32 value
) implements org.teamvan.moneyshift.NtComponent<DrawOrder> {
    public DrawOrder {
        java.util.Objects.requireNonNull(value, "value");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public DrawOrder(float value) {
        this(new org.teamvan.moneyshift.encodings.Float32(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.DrawOrder";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<DrawOrder> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).value();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        double[] column1 = new double[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).value();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<DrawOrder> values) {
        writeColumnsStatic(w, path, values);
    }
}
