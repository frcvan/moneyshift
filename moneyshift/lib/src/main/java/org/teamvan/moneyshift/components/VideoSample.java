// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Video sample data (also known as "video chunk").
 * 
 * Each video sample must contain enough data for exactly one video frame
 * (this restriction may be relaxed in the future for some codecs).
 * 
 * Keyframes may require additional data, for details see {@link org.teamvan.moneyshift.components.VideoCodec}.
 */
public record VideoSample(
    org.teamvan.moneyshift.encodings.Blob buffer
) implements org.teamvan.moneyshift.Component {
    public VideoSample {
        java.util.Objects.requireNonNull(buffer, "buffer");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.VideoSample";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Blob.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Blob.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<VideoSample> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, VideoSample::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, VideoSample value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, VideoSample value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<VideoSample> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            VideoSample value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.ListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.ListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, VideoSample value) {
        org.teamvan.moneyshift.encodings.Blob.writeOne(vector, index, value.buffer());
    }
}
