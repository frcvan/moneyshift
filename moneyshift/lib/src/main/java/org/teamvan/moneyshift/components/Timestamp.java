// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * When the recording started.
 * 
 * Should be an absolute time, i.e. relative to Unix Epoch.
 */
public record Timestamp(
    org.teamvan.moneyshift.encodings.TimeInt timestamp
) implements org.teamvan.moneyshift.Component {
    public Timestamp {
        java.util.Objects.requireNonNull(timestamp, "timestamp");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Timestamp";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.TimeInt.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.TimeInt.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Timestamp> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Timestamp::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Timestamp value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Timestamp value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Timestamp> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Timestamp value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.BigIntVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.BigIntVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Timestamp value) {
        org.teamvan.moneyshift.encodings.TimeInt.writeOne(vector, index, value.timestamp());
    }
}
