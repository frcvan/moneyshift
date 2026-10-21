// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Variance of a scalar measurement, i.e. σ², in the units of the value squared.
 * 
 * A value of {@code 0} is a perfectly known value and draws no error band.
 */
public record Variance(
    org.teamvan.moneyshift.encodings.Float64 value
) implements org.teamvan.moneyshift.Component {
    public Variance {
        java.util.Objects.requireNonNull(value, "value");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Variance";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Float64.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Float64.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Variance> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Variance::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Variance value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Variance value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Variance> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Variance value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.Float8Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.Float8Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Variance value) {
        org.teamvan.moneyshift.encodings.Float64.writeOne(vector, index, value.value());
    }
}
