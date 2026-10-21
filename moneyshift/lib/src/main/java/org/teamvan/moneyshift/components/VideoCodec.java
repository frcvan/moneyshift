// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The codec used to encode video stored in {@link org.teamvan.moneyshift.components.VideoSample}.
 * 
 * Support of these codecs by the Rerun Viewer is platform dependent.
 * For more details see check the <a href="https://rerun.io/docs/reference/video">video reference</a>.
 */
public enum VideoCodec implements org.teamvan.moneyshift.Component {
    /**
     * AOMedia Video 1 (AV1)
     * 
     * See <https://en.wikipedia.org/wiki/AV1>
     * 
     * {@link org.teamvan.moneyshift.components.VideoSample}s using this codec should be formatted according the "Low overhead bitstream format",
     * as specified in Section 5.2 of the <a href="https://aomediacodec.github.io/av1-spec/#low-overhead-bitstream-format">AV1 specification</a>.
     * Each sample should be formatted as a sequence of OBUs (Open Bitstream Units) long enough to decode at least one video frame.
     * Samples containing keyframes must include a sequence header OBU before the {@code KEY_FRAME} OBU to enable
     * extraction of frame dimensions, bit depth, and color information. {@code INTRA_ONLY} frames are not treated
     * as keyframes since they may reference existing decoder state.
     * 
     * Enum value is the fourcc for 'av01' (the WebCodec string assigned to this codec) in big endian.
     */
    AV1(1635135537L),
    /**
     * Advanced Video Coding (AVC/H.264)
     * 
     * See <https://en.wikipedia.org/wiki/Advanced_Video_Coding>
     * 
     * {@link org.teamvan.moneyshift.components.VideoSample}s using this codec should be formatted according to Annex B specification.
     * (Note that this is different from AVCC format found in MP4 files.
     * To learn more about Annex B, check for instance <https://membrane.stream/learn/h264/3>)
     * Key frames (IDR) require inclusion of a SPS (Sequence Parameter Set)
     * 
     * Enum value is the fourcc for 'avc1' (the WebCodec string assigned to this codec) in big endian.
     */
    H264(1635148593L),
    /**
     * High Efficiency Video Coding (HEVC/H.265)
     * 
     * See <https://en.wikipedia.org/wiki/High_Efficiency_Video_Coding>
     * 
     * {@link org.teamvan.moneyshift.components.VideoSample}s using this codec should be formatted according to Annex B specification.
     * (Note that this is different from AVCC format found in MP4 files.
     * To learn more about Annex B, check for instance <https://membrane.stream/learn/h264/3>)
     * Key frames (IRAP) require inclusion of a SPS (Sequence Parameter Set)
     * 
     * Enum value is the fourcc for 'hev1' (the WebCodec string assigned to this codec) in big endian.
     */
    H265(1751479857L),
    /**
     * VP8
     * 
     * See <https://en.wikipedia.org/wiki/VP8>
     * 
     * Enum value is the fourcc for 'vp08' (the WebCodec string assigned to this codec) in big endian.
     */
    VP8(1987063864L),
    /**
     * VP9
     * 
     * See <https://en.wikipedia.org/wiki/VP9>
     * 
     * Enum value is the fourcc for 'vp09' (the WebCodec string assigned to this codec) in big endian.
     */
    VP9(1987063865L);

    private final long value;

    VideoCodec(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static VideoCodec fromValue(long value) {
        for (VideoCodec candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown VideoCodec value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.VideoCodec";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT32;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.VideoCodec";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<VideoCodec> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, VideoCodec::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, VideoCodec value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, VideoCodec value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<VideoCodec> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            VideoCodec value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt4Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt4Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, VideoCodec value) {
        ((org.apache.arrow.vector.UInt4Vector) vector).setSafe(index, (int) value.value());
    }
}
