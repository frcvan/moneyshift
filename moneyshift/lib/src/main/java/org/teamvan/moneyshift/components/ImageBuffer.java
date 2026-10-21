// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A buffer that is known to store image data.
 * 
 * To interpret the contents of this buffer, see, {@link org.teamvan.moneyshift.components.ImageFormat}.
 */
public record ImageBuffer(
    org.teamvan.moneyshift.encodings.Blob buffer
) implements org.teamvan.moneyshift.Component {
    public ImageBuffer {
        java.util.Objects.requireNonNull(buffer, "buffer");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ImageBuffer(java.util.List<java.lang.Short> data) {
        this(new org.teamvan.moneyshift.encodings.Blob(data));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ImageBuffer";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Blob.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Blob.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ImageBuffer> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ImageBuffer::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ImageBuffer value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ImageBuffer value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ImageBuffer> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ImageBuffer value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.ListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.ListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ImageBuffer value) {
        org.teamvan.moneyshift.encodings.Blob.writeOne(vector, index, value.buffer());
    }
}
