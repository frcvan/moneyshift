// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Range of expected or valid values, specifying a lower and upper bound.
 */
public record ValueRange(
    org.teamvan.moneyshift.encodings.Range1D range
) implements org.teamvan.moneyshift.Component {
    public ValueRange {
        java.util.Objects.requireNonNull(range, "range");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ValueRange";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Range1D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Range1D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ValueRange> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ValueRange::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ValueRange value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ValueRange value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ValueRange> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ValueRange value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ValueRange value) {
        org.teamvan.moneyshift.encodings.Range1D.writeOne(vector, index, value.range());
    }

    public static ValueRange of(double... values) {
        if (values.length != 2) throw new java.lang.IllegalArgumentException("ValueRange needs exactly 2 values, got " + values.length);
        return new ValueRange(new org.teamvan.moneyshift.encodings.Range1D(values));
    }
}
