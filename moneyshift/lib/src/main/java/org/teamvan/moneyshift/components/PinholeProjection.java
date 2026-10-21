// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Camera projection, from image coordinates to view coordinates.
 * 
 * Child from parent.
 * Image coordinates from camera view coordinates.
 * 
 * Example:
 * {@code }`text
 * 1496.1     0.0  980.5
 *    0.0  1496.1  744.5
 *    0.0     0.0    1.0
 * {@code }`
 */
public record PinholeProjection(
    org.teamvan.moneyshift.encodings.Mat3x3 imageFromCamera
) implements org.teamvan.moneyshift.Component {
    public PinholeProjection {
        java.util.Objects.requireNonNull(imageFromCamera, "imageFromCamera");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.PinholeProjection";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Mat3x3.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Mat3x3.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<PinholeProjection> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, PinholeProjection::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, PinholeProjection value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, PinholeProjection value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<PinholeProjection> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            PinholeProjection value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, PinholeProjection value) {
        org.teamvan.moneyshift.encodings.Mat3x3.writeOne(vector, index, value.imageFromCamera());
    }

    public static PinholeProjection of(float... values) {
        if (values.length != 9) throw new java.lang.IllegalArgumentException("PinholeProjection needs exactly 9 values, got " + values.length);
        return new PinholeProjection(new org.teamvan.moneyshift.encodings.Mat3x3(values));
    }
}
