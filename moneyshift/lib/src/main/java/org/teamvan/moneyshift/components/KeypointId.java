// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A 16-bit ID representing a type of semantic keypoint within a class.
 * 
 * 
 */
public record KeypointId(
    org.teamvan.moneyshift.encodings.KeypointId id
) implements org.teamvan.moneyshift.Component {
    public KeypointId {
        java.util.Objects.requireNonNull(id, "id");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public KeypointId(int id) {
        this(new org.teamvan.moneyshift.encodings.KeypointId(id));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.KeypointId";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.KeypointId.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.KeypointId.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<KeypointId> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, KeypointId::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, KeypointId value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, KeypointId value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<KeypointId> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            KeypointId value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt2Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.UInt2Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, KeypointId value) {
        org.teamvan.moneyshift.encodings.KeypointId.writeOne(vector, index, value.id());
    }
}
