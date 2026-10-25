// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Whether the entity's {@link org.teamvan.moneyshift.components.Text rerun::components::Text} label is shown.
 * 
 * The main purpose of this component existing separately from the labels themselves
 * is to be overridden when desired, to allow hiding and showing from the viewer and
 * blueprints.
 */
public record ShowLabels(
    /**
     * Whether the entity's {@link org.teamvan.moneyshift.components.Text rerun::components::Text} label is shown.
     */
    org.teamvan.moneyshift.encodings.Bool showLabels
) implements org.teamvan.moneyshift.NtComponent<ShowLabels> {
    public ShowLabels {
        java.util.Objects.requireNonNull(showLabels, "showLabels");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ShowLabels(boolean value) {
        this(new org.teamvan.moneyshift.encodings.Bool(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ShowLabels";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ShowLabels> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).showLabels();
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
        boolean[] column1 = new boolean[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).showLabels();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? false : value1;
        }
        w.booleans(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ShowLabels> values) {
        writeColumnsStatic(w, path, values);
    }
}
