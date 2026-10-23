// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A display name, typically for an entity or a item like a plot series.
 * 
 * This name is only a display label, never an identifier: it is not used to look anything
 * up, and two items may share the same name.
 */
public record Name(
    org.teamvan.moneyshift.encodings.Utf8 value
) implements org.teamvan.moneyshift.NtComponent<Name> {
    public Name {
        java.util.Objects.requireNonNull(value, "value");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Name(java.lang.String value) {
        this(new org.teamvan.moneyshift.encodings.Utf8(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Name";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Name> values) {
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
        java.lang.String[] column1 = new java.lang.String[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).value();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            java.lang.String value1 = v1_1;
            column1[i] = value1 == null ? "" : value1;
        }
        w.strings(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Name> values) {
        writeColumnsStatic(w, path, values);
    }
}
