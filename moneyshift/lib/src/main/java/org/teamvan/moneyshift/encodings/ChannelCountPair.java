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
) {
    public ChannelCountPair {
        java.util.Objects.requireNonNull(channelId, "channelId");
        java.util.Objects.requireNonNull(messageCount, "messageCount");
    }
    public static final java.lang.String NAME = "rerun.encodings.ChannelCountPair";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.UInt16.arrowField("channel_id", false), org.teamvan.moneyshift.encodings.UInt64.arrowField("message_count", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ChannelCountPair> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ChannelCountPair value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ChannelCountPair value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.UInt16 f0 = value.channelId();
        org.teamvan.moneyshift.encodings.UInt16.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.encodings.UInt64 f1 = value.messageCount();
        org.teamvan.moneyshift.encodings.UInt64.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
