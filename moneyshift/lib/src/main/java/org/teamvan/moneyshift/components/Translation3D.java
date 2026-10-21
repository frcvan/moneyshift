// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A translation vector in 3D space.
 */
public record Translation3D(
    org.teamvan.moneyshift.encodings.Vec3D vector
) implements org.teamvan.moneyshift.Component {
    public Translation3D {
        java.util.Objects.requireNonNull(vector, "vector");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Translation3D(float[] xyz) {
        this(new org.teamvan.moneyshift.encodings.Vec3D(xyz));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Translation3D";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Vec3D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Vec3D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Translation3D> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Translation3D::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Translation3D value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Translation3D value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Translation3D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Translation3D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Translation3D value) {
        org.teamvan.moneyshift.encodings.Vec3D.writeOne(vector, index, value.vector());
    }

    public static Translation3D of(float... values) {
        if (values.length != 3) throw new java.lang.IllegalArgumentException("Translation3D needs exactly 3 values, got " + values.length);
        return new Translation3D(new org.teamvan.moneyshift.encodings.Vec3D(values));
    }
}
