// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * The layout share of a column in the container.
 */
public record ColumnShare(
    /**
     * The layout shares of a column in the container.
     */
    org.teamvan.moneyshift.encodings.Float32 share
) implements org.teamvan.moneyshift.Component {
    public ColumnShare {
        java.util.Objects.requireNonNull(share, "share");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ColumnShare(float value) {
        this(new org.teamvan.moneyshift.encodings.Float32(value));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.ColumnShare";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Float32.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Float32.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ColumnShare> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ColumnShare::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ColumnShare value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ColumnShare value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ColumnShare> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ColumnShare value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.Float4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.Float4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ColumnShare value) {
        org.teamvan.moneyshift.encodings.Float32.writeOne(vector, index, value.share());
    }
}
