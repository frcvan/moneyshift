// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * Show a slider for the index of some dimension of a slider.
 */
public record TensorDimensionIndexSlider(
    org.teamvan.moneyshift.blueprint.encodings.TensorDimensionIndexSlider selection
) implements org.teamvan.moneyshift.Component {
    public TensorDimensionIndexSlider {
        java.util.Objects.requireNonNull(selection, "selection");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.TensorDimensionIndexSlider";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.blueprint.encodings.TensorDimensionIndexSlider.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.blueprint.encodings.TensorDimensionIndexSlider.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<TensorDimensionIndexSlider> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, TensorDimensionIndexSlider::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, TensorDimensionIndexSlider value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, TensorDimensionIndexSlider value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TensorDimensionIndexSlider> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TensorDimensionIndexSlider value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TensorDimensionIndexSlider value) {
        org.teamvan.moneyshift.blueprint.encodings.TensorDimensionIndexSlider.writeOne(vector, index, value.selection());
    }
}
