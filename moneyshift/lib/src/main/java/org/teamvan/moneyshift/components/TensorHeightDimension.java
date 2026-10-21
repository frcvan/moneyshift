// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Specifies which dimension to use for height.
 */
public record TensorHeightDimension(
    org.teamvan.moneyshift.encodings.TensorDimensionSelection dimension
) implements org.teamvan.moneyshift.Component {
    public TensorHeightDimension {
        java.util.Objects.requireNonNull(dimension, "dimension");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public TensorHeightDimension(long dimension, boolean invert) {
        this(new org.teamvan.moneyshift.encodings.TensorDimensionSelection(dimension, invert));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.TensorHeightDimension";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.TensorDimensionSelection.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.TensorDimensionSelection.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<TensorHeightDimension> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, TensorHeightDimension::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, TensorHeightDimension value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, TensorHeightDimension value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TensorHeightDimension> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TensorHeightDimension value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TensorHeightDimension value) {
        org.teamvan.moneyshift.encodings.TensorDimensionSelection.writeOne(vector, index, value.dimension());
    }
}
