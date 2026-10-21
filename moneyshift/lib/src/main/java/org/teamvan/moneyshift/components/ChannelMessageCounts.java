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
) implements org.teamvan.moneyshift.Component {
    public ChannelMessageCounts {
        counts = java.util.List.copyOf(counts);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ChannelMessageCounts";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.List();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.encodings.ChannelCountPair.arrowField("item", false)));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ChannelMessageCounts> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ChannelMessageCounts::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ChannelMessageCounts value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ChannelMessageCounts value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ChannelMessageCounts> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ChannelMessageCounts value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.ListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.ListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ChannelMessageCounts value) {
        {
            org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            java.util.List<org.teamvan.moneyshift.encodings.ChannelCountPair> values0 = value.counts();
            for (int j0 = 0; j0 < values0.size(); j0++) {
                org.teamvan.moneyshift.encodings.ChannelCountPair.writeOne(child0, start0 + j0, values0.get(j0));
            }
            list0.endValue(index, values0.size());
        }
    }
}
