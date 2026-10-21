// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * The strength of a given force.
 * 
 * Allows to assign different weights to the individual forces, prioritizing one over the other.
 */
public record ForceStrength(
    org.teamvan.moneyshift.encodings.Float64 distance
) implements org.teamvan.moneyshift.Component {
    public ForceStrength {
        java.util.Objects.requireNonNull(distance, "distance");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.ForceStrength";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Float64.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Float64.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ForceStrength> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ForceStrength::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ForceStrength value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ForceStrength value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ForceStrength> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ForceStrength value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.Float8Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.Float8Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ForceStrength value) {
        org.teamvan.moneyshift.encodings.Float64.writeOne(vector, index, value.distance());
    }
}
