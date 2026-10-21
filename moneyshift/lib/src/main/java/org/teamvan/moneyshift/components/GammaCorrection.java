// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A gamma correction value to be used with a scalar value or color.
 * 
 * Used to adjust the gamma of a color or scalar value between 0 and 1 before rendering.
 * {@code new_value = old_value ^ gamma}
 * 
 * Must be a positive number.
 * Defaults to 1.0 unless otherwise specified.
 */
public record GammaCorrection(
    org.teamvan.moneyshift.encodings.Float32 gamma
) implements org.teamvan.moneyshift.Component {
    public GammaCorrection {
        java.util.Objects.requireNonNull(gamma, "gamma");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.GammaCorrection";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Float32.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Float32.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<GammaCorrection> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, GammaCorrection::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, GammaCorrection value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, GammaCorrection value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<GammaCorrection> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            GammaCorrection value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.Float4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.Float4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, GammaCorrection value) {
        org.teamvan.moneyshift.encodings.Float32.writeOne(vector, index, value.gamma());
    }
}
