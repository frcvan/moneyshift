// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Specifies a concrete index on a tensor dimension.
 */
public record TensorDimensionIndexSelection(
    org.teamvan.moneyshift.encodings.TensorDimensionIndexSelection selection
) implements org.teamvan.moneyshift.Component {
    public TensorDimensionIndexSelection {
        java.util.Objects.requireNonNull(selection, "selection");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.TensorDimensionIndexSelection";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.TensorDimensionIndexSelection.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.TensorDimensionIndexSelection.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<TensorDimensionIndexSelection> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, TensorDimensionIndexSelection::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, TensorDimensionIndexSelection value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, TensorDimensionIndexSelection value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TensorDimensionIndexSelection> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TensorDimensionIndexSelection value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TensorDimensionIndexSelection value) {
        org.teamvan.moneyshift.encodings.TensorDimensionIndexSelection.writeOne(vector, index, value.selection());
    }
}
