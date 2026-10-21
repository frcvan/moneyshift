// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Whether the entity's {@link org.teamvan.moneyshift.components.Text} label is shown.
 * 
 * The main purpose of this component existing separately from the labels themselves
 * is to be overridden when desired, to allow hiding and showing from the viewer and
 * blueprints.
 */
public record ShowLabels(
    /**
     * Whether the entity's {@link org.teamvan.moneyshift.components.Text} label is shown.
     */
    org.teamvan.moneyshift.encodings.Bool showLabels
) implements org.teamvan.moneyshift.Component {
    public ShowLabels {
        java.util.Objects.requireNonNull(showLabels, "showLabels");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ShowLabels";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Bool.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Bool.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ShowLabels> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ShowLabels::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ShowLabels value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ShowLabels value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ShowLabels> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ShowLabels value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.BitVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.BitVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ShowLabels value) {
        org.teamvan.moneyshift.encodings.Bool.writeOne(vector, index, value.showLabels());
    }
}
