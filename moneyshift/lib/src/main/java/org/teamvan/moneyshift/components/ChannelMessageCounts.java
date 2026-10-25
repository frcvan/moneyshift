// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A mapping of channel IDs to their respective message counts.
 * 
 * Used in MCAP statistics to track how many messages were recorded per channel.
 */
public record ChannelMessageCounts(
    /**
     * The channel ID to message count pairs.
     */
    java.util.List<org.teamvan.moneyshift.encodings.ChannelCountPair> counts
) implements org.teamvan.moneyshift.NtComponent<ChannelMessageCounts> {
    public ChannelMessageCounts {
        counts = java.util.List.copyOf(counts);
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ChannelMessageCounts";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ChannelMessageCounts> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_root = values.get(i);
            var v0_0 = v0_root == null ? null : v0_root.counts();
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
        long[] offsets1 = new long[n + 1];
        int total1 = 0;
        for (int i = 0; i < n; i++) {
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.counts();
            java.util.List<org.teamvan.moneyshift.encodings.ChannelCountPair> list1 = v1_0;
            total1 += list1 == null ? 0 : list1.size();
            offsets1[i + 1] = total1;
        }
        w.longs(path + "/.offsets", offsets1);
        java.util.ArrayList<org.teamvan.moneyshift.encodings.ChannelCountPair> items1 = new java.util.ArrayList<>(total1);
        for (int i = 0; i < n; i++) {
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.counts();
            java.util.List<org.teamvan.moneyshift.encodings.ChannelCountPair> list1 = v1_0;
            if (list1 != null) items1.addAll(list1);
        }
        int itemCount1 = items1.size();
        long[] column2 = new long[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v2_root = items1.get(j1);
            var v2_0 = v2_root.channelId();
            var v2_1 = v2_0.value();
            long value2 = v2_1;
            column2[j1] = value2;
        }
        w.longs(path + "/.items/channel_id", column2);
        long[] column3 = new long[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v3_root = items1.get(j1);
            var v3_0 = v3_root.messageCount();
            var v3_1 = v3_0.value();
            long value3 = v3_1;
            column3[j1] = value3;
        }
        w.longs(path + "/.items/message_count", column3);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ChannelMessageCounts> values) {
        writeColumnsStatic(w, path, values);
    }
}
