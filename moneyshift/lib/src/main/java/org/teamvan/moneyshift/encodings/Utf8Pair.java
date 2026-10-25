// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Stores a tuple of UTF-8 strings.
 */
public record Utf8Pair(
    /**
     * The first string.
     */
    org.teamvan.moneyshift.encodings.Utf8 first,
    /**
     * The second string.
     */
    org.teamvan.moneyshift.encodings.Utf8 second
) implements org.teamvan.moneyshift.NtEncoding<Utf8Pair> {
    public Utf8Pair {
        java.util.Objects.requireNonNull(first, "first");
        java.util.Objects.requireNonNull(second, "second");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public Utf8Pair(java.lang.String first, java.lang.String second) {
        this(new org.teamvan.moneyshift.encodings.Utf8(first), new org.teamvan.moneyshift.encodings.Utf8(second));
    }

    public static final java.lang.String NAME = "rerun.encodings.Utf8Pair";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Utf8Pair> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
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
        java.lang.String[] column1 = new java.lang.String[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).first();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            java.lang.String value1 = v1_1;
            column1[i] = value1 == null ? "" : value1;
        }
        w.strings(path + "/first", column1);
        java.lang.String[] column2 = new java.lang.String[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).second();
            var v2_1 = v2_0 == null ? null : v2_0.value();
            java.lang.String value2 = v2_1;
            column2[i] = value2 == null ? "" : value2;
        }
        w.strings(path + "/second", column2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Utf8Pair> values) {
        writeColumnsStatic(w, path, values);
    }
}
