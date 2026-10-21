// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * An N-dimensional array of numbers.
 * 
 * The number of dimensions and their respective lengths is specified by the {@code shape} field.
 * The dimensions are ordered from outermost to innermost. For example, in the common case of
 * a 2D RGB Image, the shape would be {@code [height, width, channel]}.
 * 
 * These dimensions are combined with an index to look up values from the {@code buffer} field,
 * which stores a contiguous array of typed values.
 */
public record TensorData(
    org.teamvan.moneyshift.encodings.TensorData data
) implements org.teamvan.moneyshift.Component {
    public TensorData {
        java.util.Objects.requireNonNull(data, "data");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public TensorData(java.util.List<java.lang.Long> shape, java.util.List<java.lang.String> names, org.teamvan.moneyshift.encodings.TensorBuffer buffer) {
        this(new org.teamvan.moneyshift.encodings.TensorData(shape, names, buffer));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.TensorData";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.TensorData.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.TensorData.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<TensorData> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, TensorData::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, TensorData value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, TensorData value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TensorData> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TensorData value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TensorData value) {
        org.teamvan.moneyshift.encodings.TensorData.writeOne(vector, index, value.data());
    }
}
