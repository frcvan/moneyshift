// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Presentation timestamp within a {@link org.teamvan.moneyshift.archetypes.AssetVideo rerun::archetypes::AssetVideo}.
 * 
 * Specified in nanoseconds.
 * Presentation timestamps are typically measured as time since video start.
 */
public record VideoTimestamp(
    /**
     * Presentation timestamp value in nanoseconds.
     */
    long timestampNs
) implements org.teamvan.moneyshift.NtEncoding<VideoTimestamp> {
    public VideoTimestamp {
    }
    public static final java.lang.String NAME = "rerun.encodings.VideoTimestamp";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<VideoTimestamp> values) {
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
            var v1_0 = values.get(i) == null ? null : values.get(i).timestampNs();
            var value1 = v1_0;
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
