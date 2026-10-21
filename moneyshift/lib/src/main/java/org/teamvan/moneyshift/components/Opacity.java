// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Degree of transparency ranging from 0.0 (fully transparent) to 1.0 (fully opaque).
 * 
 * The final opacity value may be a result of multiplication with alpha values as specified by other color sources.
 * Unless otherwise specified, the default value is 1.
 */
public record Opacity(
    org.teamvan.moneyshift.encodings.Float32 opacity
) implements org.teamvan.moneyshift.Component {
    public Opacity {
        java.util.Objects.requireNonNull(opacity, "opacity");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Opacity(float value) {
        this(new org.teamvan.moneyshift.encodings.Float32(value));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Opacity";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Float32.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Float32.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Opacity> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Opacity::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Opacity value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Opacity value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Opacity> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Opacity value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.Float4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.Float4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Opacity value) {
        org.teamvan.moneyshift.encodings.Float32.writeOne(vector, index, value.opacity());
    }
}
