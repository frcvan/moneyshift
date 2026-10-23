// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A string of text, e.g. for labels and text documents.
 */
public record Text(
    org.teamvan.moneyshift.encodings.Utf8 value
) implements org.teamvan.moneyshift.NtComponent<Text> {
    public Text {
        java.util.Objects.requireNonNull(value, "value");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Text(java.lang.String value) {
        this(new org.teamvan.moneyshift.encodings.Utf8(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Text";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Text> values) {
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
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Text> values) {
        writeColumnsStatic(w, path, values);
    }
}
