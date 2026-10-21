// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A 3D scale factor.
 * 
 * A scale of 1.0 means no scaling.
 * A scale of 2.0 means doubling the size.
 * Each component scales along the corresponding axis.
 */
public record Scale3D(
    org.teamvan.moneyshift.encodings.Vec3D scale
) implements org.teamvan.moneyshift.Component {
    public Scale3D {
        java.util.Objects.requireNonNull(scale, "scale");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Scale3D";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Vec3D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Vec3D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Scale3D> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Scale3D::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Scale3D value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Scale3D value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Scale3D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Scale3D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Scale3D value) {
        org.teamvan.moneyshift.encodings.Vec3D.writeOne(vector, index, value.scale());
    }

    public static Scale3D of(float... values) {
        if (values.length != 3) throw new java.lang.IllegalArgumentException("Scale3D needs exactly 3 values, got " + values.length);
        return new Scale3D(new org.teamvan.moneyshift.encodings.Vec3D(values));
    }
}
