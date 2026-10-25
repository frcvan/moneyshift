// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Whether the container, view, entity or instance is currently visible.
 */
public record Visible(
    org.teamvan.moneyshift.encodings.Bool visible
) implements org.teamvan.moneyshift.NtComponent<Visible> {
    public Visible {
        java.util.Objects.requireNonNull(visible, "visible");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Visible(boolean value) {
        this(new org.teamvan.moneyshift.encodings.Bool(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Visible";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Visible> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).visible();
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
            var v1_0 = values.get(i) == null ? null : values.get(i).visible();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? false : value1;
        }
        w.booleans(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Visible> values) {
        writeColumnsStatic(w, path, values);
    }
}
