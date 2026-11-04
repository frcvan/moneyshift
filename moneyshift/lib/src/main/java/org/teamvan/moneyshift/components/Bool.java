// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A boolean value.
 * 
 * The Viewer shows it as a checkbox.
 * Boolean table columns without a component type are shown with this component.
 */
public record Bool(
    org.teamvan.moneyshift.encodings.Bool value
) implements org.teamvan.moneyshift.NtComponent<Bool> {
    public Bool {
        java.util.Objects.requireNonNull(value, "value");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Bool(boolean value) {
        this(new org.teamvan.moneyshift.encodings.Bool(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Bool";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Bool> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_root = values.get(i);
            var v0_0 = v0_root == null ? null : v0_root.value();
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
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.value();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? false : value1;
        }
        w.booleans(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Bool> values) {
        writeColumnsStatic(w, path, values);
    }
}
