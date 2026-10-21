// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * Whether the view should auto-scroll to follow the time cursor.
 */
public record AutoScroll(
    org.teamvan.moneyshift.encodings.Bool autoScroll
) implements org.teamvan.moneyshift.Component {
    public AutoScroll {
        java.util.Objects.requireNonNull(autoScroll, "autoScroll");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.AutoScroll";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Bool.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Bool.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<AutoScroll> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, AutoScroll::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, AutoScroll value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, AutoScroll value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<AutoScroll> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            AutoScroll value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.BitVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.BitVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, AutoScroll value) {
        org.teamvan.moneyshift.encodings.Bool.writeOne(vector, index, value.autoScroll());
    }
}
