// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Timestamp inside a {@link org.teamvan.moneyshift.archetypes.AssetVideo}.
 */
public record VideoTimestamp(
    org.teamvan.moneyshift.encodings.VideoTimestamp timestamp
) implements org.teamvan.moneyshift.Component {
    public VideoTimestamp {
        java.util.Objects.requireNonNull(timestamp, "timestamp");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.VideoTimestamp";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.VideoTimestamp.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.VideoTimestamp.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<VideoTimestamp> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, VideoTimestamp::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, VideoTimestamp value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, VideoTimestamp value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<VideoTimestamp> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            VideoTimestamp value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.BigIntVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.BigIntVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, VideoTimestamp value) {
        org.teamvan.moneyshift.encodings.VideoTimestamp.writeOne(vector, index, value.timestamp());
    }
}
