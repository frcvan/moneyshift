// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Whether the entity can be interacted with.
 * 
 * Non interactive components are still visible, but mouse interactions in the view are disabled.
 */
public record Interactive(
    org.teamvan.moneyshift.encodings.Bool interactive
) implements org.teamvan.moneyshift.Component {
    public Interactive {
        java.util.Objects.requireNonNull(interactive, "interactive");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Interactive(boolean value) {
        this(new org.teamvan.moneyshift.encodings.Bool(value));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Interactive";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Bool.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Bool.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Interactive> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Interactive::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Interactive value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Interactive value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Interactive> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Interactive value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.BitVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.BitVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Interactive value) {
        org.teamvan.moneyshift.encodings.Bool.writeOne(vector, index, value.interactive());
    }
}
