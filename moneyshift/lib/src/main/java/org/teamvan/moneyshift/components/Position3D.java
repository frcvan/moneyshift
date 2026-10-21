// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A position in 3D space.
 */
public record Position3D(
    org.teamvan.moneyshift.encodings.Vec3D xyz
) implements org.teamvan.moneyshift.Component {
    public Position3D {
        java.util.Objects.requireNonNull(xyz, "xyz");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Position3D";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Vec3D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Vec3D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Position3D> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Position3D::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Position3D value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Position3D value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Position3D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Position3D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Position3D value) {
        org.teamvan.moneyshift.encodings.Vec3D.writeOne(vector, index, value.xyz());
    }

    public static Position3D of(float... values) {
        if (values.length != 3) throw new java.lang.IllegalArgumentException("Position3D needs exactly 3 values, got " + values.length);
        return new Position3D(new org.teamvan.moneyshift.encodings.Vec3D(values));
    }
}
