// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Configures how a clear operation should behave - recursive or not.
 */
public record ClearIsRecursive(
    /**
     * If true, also clears all recursive children entities.
     */
    org.teamvan.moneyshift.encodings.Bool recursive
) implements org.teamvan.moneyshift.NtComponent<ClearIsRecursive> {
    public ClearIsRecursive {
        java.util.Objects.requireNonNull(recursive, "recursive");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ClearIsRecursive(boolean value) {
        this(new org.teamvan.moneyshift.encodings.Bool(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ClearIsRecursive";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ClearIsRecursive> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).recursive();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        boolean[] column1 = new boolean[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).recursive();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? false : value1;
        }
        w.booleans(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ClearIsRecursive> values) {
        writeColumnsStatic(w, path, values);
    }
}
