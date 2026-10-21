// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * The type of the visualizer.
 */
public record VisualizerType(
    /**
     * The type of the visualizer.
     */
    org.teamvan.moneyshift.encodings.Utf8 visualizerType
) implements org.teamvan.moneyshift.Component {
    public VisualizerType {
        java.util.Objects.requireNonNull(visualizerType, "visualizerType");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public VisualizerType(java.lang.String value) {
        this(new org.teamvan.moneyshift.encodings.Utf8(value));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.VisualizerType";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Utf8.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Utf8.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<VisualizerType> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, VisualizerType::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, VisualizerType value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, VisualizerType value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<VisualizerType> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            VisualizerType value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.VarCharVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.VarCharVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, VisualizerType value) {
        org.teamvan.moneyshift.encodings.Utf8.writeOne(vector, index, value.visualizerType());
    }
}
