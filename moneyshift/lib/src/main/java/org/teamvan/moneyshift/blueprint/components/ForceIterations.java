// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * Specifies how often this force should be applied per iteration.
 * 
 * Increasing this parameter can lead to better results at the cost of longer computation time.
 */
public record ForceIterations(
    org.teamvan.moneyshift.encodings.UInt64 distance
) implements org.teamvan.moneyshift.Component {
    public ForceIterations {
        java.util.Objects.requireNonNull(distance, "distance");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ForceIterations(long value) {
        this(new org.teamvan.moneyshift.encodings.UInt64(value));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.ForceIterations";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.UInt64.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.UInt64.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ForceIterations> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ForceIterations::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ForceIterations value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ForceIterations value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ForceIterations> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ForceIterations value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt8Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.UInt8Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ForceIterations value) {
        org.teamvan.moneyshift.encodings.UInt64.writeOne(vector, index, value.distance());
    }
}
