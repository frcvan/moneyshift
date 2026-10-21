// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A 16-bit ID representing an MCAP channel.
 * 
 * Used to identify specific channels within an MCAP file.
 */
public record ChannelId(
    org.teamvan.moneyshift.encodings.UInt16 id
) implements org.teamvan.moneyshift.Component {
    public ChannelId {
        java.util.Objects.requireNonNull(id, "id");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ChannelId";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.UInt16.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.UInt16.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ChannelId> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ChannelId::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ChannelId value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ChannelId value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ChannelId> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ChannelId value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt2Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.UInt2Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ChannelId value) {
        org.teamvan.moneyshift.encodings.UInt16.writeOne(vector, index, value.id());
    }
}
