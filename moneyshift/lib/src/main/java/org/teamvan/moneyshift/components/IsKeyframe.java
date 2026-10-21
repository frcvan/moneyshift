// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Whether a {@link org.teamvan.moneyshift.components.VideoSample} contains a keyframe (also known as a sync sample or IDR).
 * 
 * A keyframe in this sense must be _decoder re-entrant_: a decoder must be able to start
 * decoding the stream from this sample alone, with no prior decoder state.
 * Not every intra-coded frame qualifies. Some codecs have intra-only frames that may
 * still reference existing decoder state and are therefore not valid sync points.
 * See {@link org.teamvan.moneyshift.components.VideoCodec} for the codec-specific definition of a keyframe.
 */
public record IsKeyframe(
    org.teamvan.moneyshift.encodings.Bool isKeyframe
) implements org.teamvan.moneyshift.Component {
    public IsKeyframe {
        java.util.Objects.requireNonNull(isKeyframe, "isKeyframe");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.IsKeyframe";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Bool.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Bool.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<IsKeyframe> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, IsKeyframe::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, IsKeyframe value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, IsKeyframe value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<IsKeyframe> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            IsKeyframe value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.BitVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.BitVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, IsKeyframe value) {
        org.teamvan.moneyshift.encodings.Bool.writeOne(vector, index, value.isKeyframe());
    }
}
