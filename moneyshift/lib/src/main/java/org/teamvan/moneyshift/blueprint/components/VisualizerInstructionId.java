// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * ID for a visualizer instruction.
 * 
 * IDs are only guaranteed to be unique in the scope of a view.
 * For details see {@link org.teamvan.moneyshift.blueprint.archetypes.ActiveVisualizers}.
 */
public record VisualizerInstructionId(
    /**
     * IDs of a single visualizer instruction.
     */
    org.teamvan.moneyshift.encodings.Uuid visualizer
) implements org.teamvan.moneyshift.Component {
    public VisualizerInstructionId {
        java.util.Objects.requireNonNull(visualizer, "visualizer");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.VisualizerInstructionId";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Uuid.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Uuid.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<VisualizerInstructionId> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, VisualizerInstructionId::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, VisualizerInstructionId value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, VisualizerInstructionId value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<VisualizerInstructionId> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            VisualizerInstructionId value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, VisualizerInstructionId value) {
        org.teamvan.moneyshift.encodings.Uuid.writeOne(vector, index, value.visualizer());
    }

    public static VisualizerInstructionId of(short... values) {
        if (values.length != 16) throw new java.lang.IllegalArgumentException("VisualizerInstructionId needs exactly 16 values, got " + values.length);
        return new VisualizerInstructionId(new org.teamvan.moneyshift.encodings.Uuid(values));
    }
}
