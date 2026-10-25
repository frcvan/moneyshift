// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Timestamp inside a {@link org.teamvan.moneyshift.archetypes.AssetVideo rerun::archetypes::AssetVideo}.
 */
public record VideoTimestamp(
    org.teamvan.moneyshift.encodings.VideoTimestamp timestamp
) implements org.teamvan.moneyshift.NtComponent<VideoTimestamp> {
    public VideoTimestamp {
        java.util.Objects.requireNonNull(timestamp, "timestamp");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public VideoTimestamp(long timestampNs) {
        this(new org.teamvan.moneyshift.encodings.VideoTimestamp(timestampNs));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.VideoTimestamp";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<VideoTimestamp> values) {
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
            var v1_1 = v1_0 == null ? null : v1_0.timestampNs();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<VideoTimestamp> values) {
        writeColumnsStatic(w, path, values);
    }
}
