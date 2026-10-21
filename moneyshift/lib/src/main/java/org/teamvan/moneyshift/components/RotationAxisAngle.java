// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * 3D rotation represented by a rotation around a given axis.
 * 
 * If normalization of the rotation axis fails the rotation is treated as an invalid transform, unless the
 * angle is zero in which case it is treated as an identity.
 */
public record RotationAxisAngle(
    org.teamvan.moneyshift.encodings.RotationAxisAngle rotation
) implements org.teamvan.moneyshift.Component {
    public RotationAxisAngle {
        java.util.Objects.requireNonNull(rotation, "rotation");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.RotationAxisAngle";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.RotationAxisAngle.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.RotationAxisAngle.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<RotationAxisAngle> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, RotationAxisAngle::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, RotationAxisAngle value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, RotationAxisAngle value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<RotationAxisAngle> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            RotationAxisAngle value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, RotationAxisAngle value) {
        org.teamvan.moneyshift.encodings.RotationAxisAngle.writeOne(vector, index, value.rotation());
    }
}
