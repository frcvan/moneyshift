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
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.keypoint0();
            var v1_1 = v1_0 == null ? null : v1_0.id();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/keypoint0", column1);
        long[] column2 = new long[n];
        for (int i = 0; i < n; i++) {
            var v2_root = values.get(i);
            var v2_0 = v2_root == null ? null : v2_root.keypoint1();
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
