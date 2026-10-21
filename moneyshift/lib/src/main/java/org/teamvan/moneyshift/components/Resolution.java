// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Pixel resolution width &amp; height, e.g. of a camera sensor.
 * 
 * Typically in integer units, but for some use cases floating point may be used.
 */
public record Resolution(
    org.teamvan.moneyshift.encodings.Vec2D resolution
) implements org.teamvan.moneyshift.Component {
    public Resolution {
        java.util.Objects.requireNonNull(resolution, "resolution");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Resolution";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Vec2D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Vec2D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Resolution> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Resolution::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Resolution value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Resolution value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Resolution> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Resolution value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Resolution value) {
        org.teamvan.moneyshift.encodings.Vec2D.writeOne(vector, index, value.resolution());
    }

    public static Resolution of(float... values) {
        if (values.length != 2) throw new java.lang.IllegalArgumentException("Resolution needs exactly 2 values, got " + values.length);
        return new Resolution(new org.teamvan.moneyshift.encodings.Vec2D(values));
    }
}
