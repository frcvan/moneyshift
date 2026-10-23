// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A pair representing a channel ID and its associated message count.
 */
public record ChannelCountPair(
    /**
     * The channel ID.
     */
    org.teamvan.moneyshift.encodings.UInt16 channelId,
    /**
     * The message count for this channel.
     */
    org.teamvan.moneyshift.encodings.UInt64 messageCount
) implements org.teamvan.moneyshift.NtEncoding<ChannelCountPair> {
    public ChannelCountPair {
        java.util.Objects.requireNonNull(channelId, "channelId");
        java.util.Objects.requireNonNull(messageCount, "messageCount");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public ChannelCountPair(int channelId, long messageCount) {
        this(new org.teamvan.moneyshift.encodings.UInt16(channelId), new org.teamvan.moneyshift.encodings.UInt64(messageCount));
    }

    public static final java.lang.String NAME = "rerun.encodings.ChannelCountPair";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ChannelCountPair> values) {
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
            var v1_0 = values.get(i) == null ? null : values.get(i).channelId();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/channel_id", column1);
        long[] column2 = new long[n];
        boolean unsigned2 = false;
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).messageCount();
            var v2_1 = v2_0 == null ? null : v2_0.value();
            var value2 = v2_1;
            column2[i] = value2 == null ? 0L : value2;
            unsigned2 |= column2[i] < 0L;
        }
        w.u64(path + "/message_count", column2, unsigned2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ChannelCountPair> values) {
        writeColumnsStatic(w, path, values);
    }
}
