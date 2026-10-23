// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A generic count value.
 * 
 * Used for counting various entities like messages, schemas, channels, etc.
 */
public record Count(
    org.teamvan.moneyshift.encodings.UInt64 value
) implements org.teamvan.moneyshift.NtComponent<Count> {
    public Count {
        java.util.Objects.requireNonNull(value, "value");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Count(long value) {
        this(new org.teamvan.moneyshift.encodings.UInt64(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Count";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Count> values) {
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
        long[] column1 = new long[n];
        boolean unsigned1 = false;
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).value();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
            unsigned1 |= column1[i] < 0L;
        }
        w.u64(path, column1, unsigned1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Count> values) {
        writeColumnsStatic(w, path, values);
    }
}
