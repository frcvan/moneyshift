// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Half-size (radius) of a 2D box.
 * 
 * Measured in its local coordinate system.
 * 
 * The box extends both in negative and positive direction along each axis.
 * Negative sizes indicate that the box is flipped along the respective axis, but this has no effect on how it is displayed.
 */
public record HalfSize2D(
    org.teamvan.moneyshift.encodings.Vec2D xy
) implements org.teamvan.moneyshift.Component {
    public HalfSize2D {
        java.util.Objects.requireNonNull(xy, "xy");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.HalfSize2D";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Vec2D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Vec2D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<HalfSize2D> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, HalfSize2D::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, HalfSize2D value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, HalfSize2D value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<HalfSize2D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            HalfSize2D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, HalfSize2D value) {
        org.teamvan.moneyshift.encodings.Vec2D.writeOne(vector, index, value.xy());
    }

    public static HalfSize2D of(float... values) {
        if (values.length != 2) throw new java.lang.IllegalArgumentException("HalfSize2D needs exactly 2 values, got " + values.length);
        return new HalfSize2D(new org.teamvan.moneyshift.encodings.Vec2D(values));
    }
}
