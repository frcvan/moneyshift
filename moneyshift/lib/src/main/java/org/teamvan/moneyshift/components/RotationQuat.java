// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A 3D rotation expressed as a quaternion.
 * 
 * Note: although the x,y,z,w components of the quaternion will be passed through to the
 * datastore as provided, when used in the Viewer, quaternions will always be normalized.
 * If normalization fails the rotation is treated as an invalid transform.
 */
public record RotationQuat(
    org.teamvan.moneyshift.encodings.Quaternion quaternion
) implements org.teamvan.moneyshift.Component {
    public RotationQuat {
        java.util.Objects.requireNonNull(quaternion, "quaternion");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public RotationQuat(float[] xyzw) {
        this(new org.teamvan.moneyshift.encodings.Quaternion(xyzw));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.RotationQuat";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Quaternion.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Quaternion.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<RotationQuat> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, RotationQuat::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, RotationQuat value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, RotationQuat value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<RotationQuat> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            RotationQuat value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, RotationQuat value) {
        org.teamvan.moneyshift.encodings.Quaternion.writeOne(vector, index, value.quaternion());
    }

    public static RotationQuat of(float... values) {
        if (values.length != 4) throw new java.lang.IllegalArgumentException("RotationQuat needs exactly 4 values, got " + values.length);
        return new RotationQuat(new org.teamvan.moneyshift.encodings.Quaternion(values));
    }
}
