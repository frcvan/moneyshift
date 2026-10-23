// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A single boolean.
 */
public record Bool(
    boolean value
) implements org.teamvan.moneyshift.NtEncoding<Bool> {
    public Bool {
    }
    public static final java.lang.String NAME = "rerun.encodings.Bool";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Bool> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        boolean[] column1 = new boolean[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).value();
            var value1 = v1_0;
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
