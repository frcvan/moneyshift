// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A 16-bit ID representing a type of semantic keypoint within a class.
 * 
 * 
 */
public record KeypointId(
    org.teamvan.moneyshift.encodings.KeypointId id
) implements org.teamvan.moneyshift.NtComponent<KeypointId> {
    public KeypointId {
        java.util.Objects.requireNonNull(id, "id");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public KeypointId(int id) {
        this(new org.teamvan.moneyshift.encodings.KeypointId(id));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.KeypointId";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<KeypointId> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).id();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).id();
            var v1_1 = v1_0 == null ? null : v1_0.id();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<KeypointId> values) {
        writeColumnsStatic(w, path, values);
    }
}
