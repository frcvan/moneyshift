// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * Indicate whether the range should be locked when zooming in on the data.
 * 
 * Default is {@code false}, i.e. zoom will change the visualized range.
 */
public record LockRangeDuringZoom(
    org.teamvan.moneyshift.encodings.Bool lockRange
) implements org.teamvan.moneyshift.Component {
    public LockRangeDuringZoom {
        java.util.Objects.requireNonNull(lockRange, "lockRange");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.LockRangeDuringZoom";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Bool.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Bool.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<LockRangeDuringZoom> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, LockRangeDuringZoom::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, LockRangeDuringZoom value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, LockRangeDuringZoom value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<LockRangeDuringZoom> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            LockRangeDuringZoom value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.BitVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.BitVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, LockRangeDuringZoom value) {
        org.teamvan.moneyshift.encodings.Bool.writeOne(vector, index, value.lockRange());
    }
}
