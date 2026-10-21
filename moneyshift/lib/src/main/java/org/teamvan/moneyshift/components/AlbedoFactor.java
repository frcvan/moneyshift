// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A color multiplier, usually applied to a whole entity, e.g. a mesh.
 */
public record AlbedoFactor(
    org.teamvan.moneyshift.encodings.Rgba32 albedoFactor
) implements org.teamvan.moneyshift.Component {
    public AlbedoFactor {
        java.util.Objects.requireNonNull(albedoFactor, "albedoFactor");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.AlbedoFactor";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Rgba32.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Rgba32.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<AlbedoFactor> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, AlbedoFactor::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, AlbedoFactor value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, AlbedoFactor value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<AlbedoFactor> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            AlbedoFactor value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.UInt4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, AlbedoFactor value) {
        org.teamvan.moneyshift.encodings.Rgba32.writeOne(vector, index, value.albedoFactor());
    }
}
