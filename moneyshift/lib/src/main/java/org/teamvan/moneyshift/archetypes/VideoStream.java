// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Video stream consisting of raw video chunks.
 * 
 * For logging video containers like mp4, refer to {@link org.teamvan.moneyshift.archetypes.AssetVideo} and {@link org.teamvan.moneyshift.archetypes.VideoFrameReference}.
 * To learn more about video support in Rerun, check the <a href="https://rerun.io/docs/reference/video">video reference</a>.
 * 
 * All components except {@code sample} are typically logged statically once per entity.
 * {@code sample} is then logged repeatedly for each frame on the timeline.
 * Individual frames can be displayed with {@link org.teamvan.moneyshift.archetypes.VideoFrameReference}, which uses the active Viewer timeline.
 * 
 */
public record VideoStream(
    /**
     * The codec used to encode the video chunks.
     * 
     * This property is expected to be constant over time and is ideally logged statically once per stream.
     */
    org.teamvan.moneyshift.components.VideoCodec codec,
    /**
     * Video sample data (also known as "video chunk").
     * 
     * The current timestamp is used as presentation timestamp (PTS) for all data in this sample.
     * There is currently no way to log differing decoding timestamps, meaning
     * that there is no support for B-frames.
     * See <https://github.com/rerun-io/rerun/issues/10090> for more details.
     * 
     * Rerun chunks containing frames (i.e. bundles of sample data) may arrive out of order,
     * but may cause the video playback in the Viewer to reset.
     * It is recommended to have all chunks for a video stream to be ordered temporally order.
     * 
     * Logging separate videos on the same entity is allowed iff they share the exact same
     * codec parameters & resolution.
     * 
     * The samples are expected to be encoded using the {@code codec} field.
     * Each video sample must contain enough data for exactly one video frame
     * (this restriction may be relaxed in the future for some codecs).
     * 
     * Unless your stream consists entirely of key-frames (in which case you should consider {@link org.teamvan.moneyshift.archetypes.EncodedImage})
     * never log this component as static data as this means that you loose all information of
     * previous samples which may be required to decode an image.
     * 
     * See {@link org.teamvan.moneyshift.components.VideoCodec} for codec specific requirements.
     */
    org.teamvan.moneyshift.components.VideoSample sample,
    /**
     * Whether the corresponding {@link org.teamvan.moneyshift.components.VideoSample} contains a keyframe.
     * 
     * A keyframe (also known as a sync sample or IDR) is a frame from which a decoder can
     * start decoding the stream with no prior decoder state. See {@link org.teamvan.moneyshift.components.IsKeyframe}
     * and {@link org.teamvan.moneyshift.components.VideoCodec} for the codec-specific definition.
     * 
     * This field is optional. It does not change how the stream itself is decoded: it is
     * metadata that travels with the sample and can be inspected when querying the data
     * back, for example to locate sync points or build a frame index.
     */
    org.teamvan.moneyshift.components.IsKeyframe isKeyframe,
    /**
     * Opacity of the video stream, useful for layering several media.
     * 
     * Defaults to 1.0 (fully opaque).
     */
    org.teamvan.moneyshift.components.Opacity opacity,
    /**
     * An optional floating point value that specifies the 2D drawing order.
     * 
     * Objects with higher values are drawn on top of those with lower values.
     * Defaults to {@code -15.0}.
     */
    org.teamvan.moneyshift.components.DrawOrder drawOrder
) implements org.teamvan.moneyshift.Archetype {
    public VideoStream {
        java.util.Objects.requireNonNull(codec, "codec");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.VideoStream";
    public static final int NUM_COMPONENTS = 5;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CODEC = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VideoStream:codec", "rerun.components.VideoCodec");

    /**
     * The codec used to encode the video chunks.
     * 
     * This property is expected to be constant over time and is ideally logged statically once per stream.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorCodec() {
        return DESCRIPTOR_CODEC;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SAMPLE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VideoStream:sample", "rerun.components.VideoSample");

    /**
     * Video sample data (also known as "video chunk").
     * 
     * The current timestamp is used as presentation timestamp (PTS) for all data in this sample.
     * There is currently no way to log differing decoding timestamps, meaning
     * that there is no support for B-frames.
     * See <https://github.com/rerun-io/rerun/issues/10090> for more details.
     * 
     * Rerun chunks containing frames (i.e. bundles of sample data) may arrive out of order,
     * but may cause the video playback in the Viewer to reset.
     * It is recommended to have all chunks for a video stream to be ordered temporally order.
     * 
     * Logging separate videos on the same entity is allowed iff they share the exact same
     * codec parameters & resolution.
     * 
     * The samples are expected to be encoded using the {@code codec} field.
     * Each video sample must contain enough data for exactly one video frame
     * (this restriction may be relaxed in the future for some codecs).
     * 
     * Unless your stream consists entirely of key-frames (in which case you should consider {@link org.teamvan.moneyshift.archetypes.EncodedImage})
     * never log this component as static data as this means that you loose all information of
     * previous samples which may be required to decode an image.
     * 
     * See {@link org.teamvan.moneyshift.components.VideoCodec} for codec specific requirements.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorSample() {
        return DESCRIPTOR_SAMPLE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_IS_KEYFRAME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VideoStream:is_keyframe", "rerun.components.IsKeyframe");

    /**
     * Whether the corresponding {@link org.teamvan.moneyshift.components.VideoSample} contains a keyframe.
     * 
     * A keyframe (also known as a sync sample or IDR) is a frame from which a decoder can
     * start decoding the stream with no prior decoder state. See {@link org.teamvan.moneyshift.components.IsKeyframe}
     * and {@link org.teamvan.moneyshift.components.VideoCodec} for the codec-specific definition.
     * 
     * This field is optional. It does not change how the stream itself is decoded: it is
     * metadata that travels with the sample and can be inspected when querying the data
     * back, for example to locate sync points or build a frame index.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorIsKeyframe() {
        return DESCRIPTOR_IS_KEYFRAME;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_OPACITY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VideoStream:opacity", "rerun.components.Opacity");

    /**
     * Opacity of the video stream, useful for layering several media.
     * 
     * Defaults to 1.0 (fully opaque).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorOpacity() {
        return DESCRIPTOR_OPACITY;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DRAW_ORDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VideoStream:draw_order", "rerun.components.DrawOrder");

    /**
     * An optional floating point value that specifies the 2D drawing order.
     * 
     * Objects with higher values are drawn on top of those with lower values.
     * Defaults to {@code -15.0}.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorDrawOrder() {
        return DESCRIPTOR_DRAW_ORDER;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorCodec());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorSample());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorIsKeyframe(), descriptorOpacity(), descriptorDrawOrder());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.VideoCodec.batch(descriptorCodec(), allocator, codec));
        if (sample != null) {
            batches.add(org.teamvan.moneyshift.components.VideoSample.batch(descriptorSample(), allocator, sample));
        }
        if (isKeyframe != null) {
            batches.add(org.teamvan.moneyshift.components.IsKeyframe.batch(descriptorIsKeyframe(), allocator, isKeyframe));
        }
        if (opacity != null) {
            batches.add(org.teamvan.moneyshift.components.Opacity.batch(descriptorOpacity(), allocator, opacity));
        }
        if (drawOrder != null) {
            batches.add(org.teamvan.moneyshift.components.DrawOrder.batch(descriptorDrawOrder(), allocator, drawOrder));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.VideoCodec codec;
        private org.teamvan.moneyshift.components.VideoSample sample;
        private org.teamvan.moneyshift.components.IsKeyframe isKeyframe;
        private org.teamvan.moneyshift.components.Opacity opacity;
        private org.teamvan.moneyshift.components.DrawOrder drawOrder;
        private Builder() {}

        public Builder withCodec(org.teamvan.moneyshift.components.VideoCodec value) {
            this.codec = value;
            return this;
        }

        public Builder withSample(org.teamvan.moneyshift.components.VideoSample value) {
            this.sample = value;
            return this;
        }

        public Builder withIsKeyframe(org.teamvan.moneyshift.components.IsKeyframe value) {
            this.isKeyframe = value;
            return this;
        }

        public Builder withOpacity(org.teamvan.moneyshift.components.Opacity value) {
            this.opacity = value;
            return this;
        }

        public Builder withDrawOrder(org.teamvan.moneyshift.components.DrawOrder value) {
            this.drawOrder = value;
            return this;
        }

        public VideoStream build() {
            return new VideoStream(codec, sample, isKeyframe, opacity, drawOrder);
        }
    }

    public static VideoStream of(
        org.teamvan.moneyshift.components.VideoCodec codec
    ) {
        return new VideoStream(codec, null, null, null, null);
    }
}
