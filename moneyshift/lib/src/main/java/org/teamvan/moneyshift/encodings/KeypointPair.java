// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A connection between two {@link org.teamvan.moneyshift.encodings.KeypointId rerun::encodings::KeypointId}s.
 */
public record KeypointPair(
    /**
     * The first point of the pair.
     */
    org.teamvan.moneyshift.encodings.KeypointId keypoint0,
    /**
     * The second point of the pair.
     */
    org.teamvan.moneyshift.encodings.KeypointId keypoint1
) implements org.teamvan.moneyshift.NtEncoding<KeypointPair> {
    public KeypointPair {
        java.util.Objects.requireNonNull(keypoint0, "keypoint0");
        java.util.Objects.requireNonNull(keypoint1, "keypoint1");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public KeypointPair(int keypoint0, int keypoint1) {
        this(new org.teamvan.moneyshift.encodings.KeypointId(keypoint0), new org.teamvan.moneyshift.encodings.KeypointId(keypoint1));
    }

    public static final java.lang.String NAME = "rerun.encodings.KeypointPair";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<KeypointPair> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).keypoint0();
            var v1_1 = v1_0 == null ? null : v1_0.id();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/keypoint0", column1);
        long[] column2 = new long[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).keypoint1();
            var v2_1 = v2_0 == null ? null : v2_0.id();
            var value2 = v2_1;
            column2[i] = value2 == null ? 0L : value2;
        }
        w.longs(path + "/keypoint1", column2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<KeypointPair> values) {
        writeColumnsStatic(w, path, values);
    }
}
