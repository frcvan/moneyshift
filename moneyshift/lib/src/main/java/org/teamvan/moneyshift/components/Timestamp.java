// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * When the recording started.
 * 
 * Should be an absolute time, i.e. relative to Unix Epoch.
 */
public record Timestamp(
    org.teamvan.moneyshift.encodings.TimeInt timestamp
) implements org.teamvan.moneyshift.NtComponent<Timestamp> {
    public Timestamp {
        java.util.Objects.requireNonNull(timestamp, "timestamp");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Timestamp(long value) {
        this(new org.teamvan.moneyshift.encodings.TimeInt(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Timestamp";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Timestamp> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).timestamp();
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
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).timestamp();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Timestamp> values) {
        writeColumnsStatic(w, path, values);
    }
}
