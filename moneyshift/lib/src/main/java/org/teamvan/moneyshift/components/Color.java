// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * An RGBA color with unmultiplied/separate alpha, in sRGB gamma space with linear alpha.
 * 
 * The color is stored as a 32-bit integer, where the most significant
 * byte is {@code R} and the least significant byte is {@code A}.
 * 
 */
public record Color(
    org.teamvan.moneyshift.encodings.Rgba32 rgba
) implements org.teamvan.moneyshift.Component {
    public Color {
        java.util.Objects.requireNonNull(rgba, "rgba");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Color(long rgba) {
        this(new org.teamvan.moneyshift.encodings.Rgba32(rgba));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Color";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Rgba32.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Rgba32.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Color> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Color::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Color value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Color value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Color> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Color value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.UInt4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Color value) {
        org.teamvan.moneyshift.encodings.Rgba32.writeOne(vector, index, value.rgba());
    }
}
