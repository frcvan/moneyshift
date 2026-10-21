// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The metadata describing the contents of a {@link org.teamvan.moneyshift.components.ImageBuffer}.
 */
public record ImageFormat(
    org.teamvan.moneyshift.encodings.ImageFormat imageFormat
) implements org.teamvan.moneyshift.Component {
    public ImageFormat {
        java.util.Objects.requireNonNull(imageFormat, "imageFormat");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ImageFormat(long width, long height, org.teamvan.moneyshift.encodings.PixelFormat pixelFormat, org.teamvan.moneyshift.encodings.ColorModel colorModel, org.teamvan.moneyshift.encodings.ChannelDatatype channelDatatype) {
        this(new org.teamvan.moneyshift.encodings.ImageFormat(width, height, pixelFormat, colorModel, channelDatatype));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ImageFormat";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.ImageFormat.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.ImageFormat.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ImageFormat> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ImageFormat::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ImageFormat value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ImageFormat value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ImageFormat> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ImageFormat value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ImageFormat value) {
        org.teamvan.moneyshift.encodings.ImageFormat.writeOne(vector, index, value.imageFormat());
    }
}
