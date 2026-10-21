// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The distance from the camera origin to the image plane when the projection is shown in a 3D viewer.
 * 
 * This is only used for visualization purposes, and does not affect the projection itself.
 */
public record ImagePlaneDistance(
    org.teamvan.moneyshift.encodings.Float32 imageFromCamera
) implements org.teamvan.moneyshift.Component {
    public ImagePlaneDistance {
        java.util.Objects.requireNonNull(imageFromCamera, "imageFromCamera");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ImagePlaneDistance(float value) {
        this(new org.teamvan.moneyshift.encodings.Float32(value));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ImagePlaneDistance";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Float32.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Float32.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ImagePlaneDistance> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ImagePlaneDistance::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ImagePlaneDistance value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ImagePlaneDistance value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ImagePlaneDistance> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ImagePlaneDistance value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.Float4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.Float4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ImagePlaneDistance value) {
        org.teamvan.moneyshift.encodings.Float32.writeOne(vector, index, value.imageFromCamera());
    }
}
