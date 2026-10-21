// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * A reference to a range of time.
 */
public record AbsoluteTimeRange(
    org.teamvan.moneyshift.encodings.AbsoluteTimeRange range
) implements org.teamvan.moneyshift.Component {
    public AbsoluteTimeRange {
        java.util.Objects.requireNonNull(range, "range");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.AbsoluteTimeRange";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.AbsoluteTimeRange.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.AbsoluteTimeRange.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<AbsoluteTimeRange> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, AbsoluteTimeRange::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, AbsoluteTimeRange value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, AbsoluteTimeRange value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<AbsoluteTimeRange> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            AbsoluteTimeRange value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, AbsoluteTimeRange value) {
        org.teamvan.moneyshift.encodings.AbsoluteTimeRange.writeOne(vector, index, value.range());
    }
}
