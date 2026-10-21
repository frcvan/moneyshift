// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * Associates components of an entity to components of a visualizer.
 */
public record VisualizerComponentMapping(
    /**
     * The component mapping pairs.
     */
    org.teamvan.moneyshift.blueprint.encodings.VisualizerComponentMapping mapping
) implements org.teamvan.moneyshift.Component {
    public VisualizerComponentMapping {
        java.util.Objects.requireNonNull(mapping, "mapping");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public VisualizerComponentMapping(java.lang.String target, org.teamvan.moneyshift.blueprint.encodings.ComponentSourceKind sourceKind, java.lang.String sourceComponent, java.lang.String selector) {
        this(new org.teamvan.moneyshift.blueprint.encodings.VisualizerComponentMapping(new org.teamvan.moneyshift.encodings.Utf8(target), sourceKind, sourceComponent, selector));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.VisualizerComponentMapping";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.blueprint.encodings.VisualizerComponentMapping.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.blueprint.encodings.VisualizerComponentMapping.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<VisualizerComponentMapping> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, VisualizerComponentMapping::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, VisualizerComponentMapping value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, VisualizerComponentMapping value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<VisualizerComponentMapping> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            VisualizerComponentMapping value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, VisualizerComponentMapping value) {
        org.teamvan.moneyshift.blueprint.encodings.VisualizerComponentMapping.writeOne(vector, index, value.mapping());
    }
}
