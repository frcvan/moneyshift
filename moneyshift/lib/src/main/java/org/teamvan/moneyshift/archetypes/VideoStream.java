// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Video stream consisting of raw video chunks.
 * 
 * For logging video containers like mp4, refer to {@link org.teamvan.moneyshift.archetypes.AssetVideo rerun::archetypes::AssetVideo} and {@link org.teamvan.moneyshift.archetypes.VideoFrameReference rerun::archetypes::VideoFrameReference}.
 * To learn more about video support in Rerun, check the <a href="https://rerun.io/docs/reference/video">video reference</a>.
 * 
 * All components except {@code sample} are typically logged statically once per entity.
 * {@code sample} is then logged repeatedly for each frame on the timeline.
 * Individual frames can be displayed with {@link org.teamvan.moneyshift.archetypes.VideoFrameReference rerun::archetypes::VideoFrameReference}, which uses the active Viewer timeline.
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
     * See &lt;https://github.com/rerun-io/rerun/issues/10090&gt; for more details.
     * 
     * Rerun chunks containing frames (i.e. bundles of sample data) may arrive out of order,
     * but may cause the video playback in the Viewer to reset.
     * It is recommended to have all chunks for a video stream to be ordered temporally order.
     * 
     * Logging separate videos on the same entity is allowed iff they share the exact same
     * codec parameters &amp; resolution.
     * 
     * The samples are expected to be encoded using the {@code codec} field.
     * Each video sample must contain enough data for exactly one video frame
     * (this restriction may be relaxed in the future for some codecs).
     * 
     * Unless your stream consists entirely of key-frames (in which case you should consider {@link org.teamvan.moneyshift.archetypes.EncodedImage rerun::archetypes::EncodedImage})
     * never log this component as static data as this means that you loose all information of
     * previous samples which may be required to decode an image.
     * 
     * See {@link org.teamvan.moneyshift.components.VideoCodec rerun::components::VideoCodec} for codec specific requirements.
     */
    org.teamvan.moneyshift.components.VideoSample sample,
    /**
     * Whether the corresponding {@link org.teamvan.moneyshift.components.VideoSample rerun::components::VideoSample} contains a keyframe.
     * 
     * A keyframe (also known as a sync sample or IDR) is a frame from which a decoder can
     * start decoding the stream with no prior decoder state. See {@link org.teamvan.moneyshift.components.IsKeyframe rerun::components::IsKeyframe}
     * and {@link org.teamvan.moneyshift.components.VideoCodec rerun::components::VideoCodec} for the codec-specific definition.
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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("codec").append('=').append(org.teamvan.moneyshift.components.VideoCodec.COMPONENT_TYPE);
        }
        if (sample != null) {
            if (components.length() > 0) components.append(',');
            components.append("sample").append('=').append(org.teamvan.moneyshift.components.VideoSample.COMPONENT_TYPE);
        }
        if (isKeyframe != null) {
            if (components.length() > 0) components.append(',');
            components.append("is_keyframe").append('=').append(org.teamvan.moneyshift.components.IsKeyframe.COMPONENT_TYPE);
        }
        if (opacity != null) {
            if (components.length() > 0) components.append(',');
            components.append("opacity").append('=').append(org.teamvan.moneyshift.components.Opacity.COMPONENT_TYPE);
        }
        if (drawOrder != null) {
            if (components.length() > 0) components.append(',');
            components.append("draw_order").append('=').append(org.teamvan.moneyshift.components.DrawOrder.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (true) {
            org.teamvan.moneyshift.components.VideoCodec.writeColumnsStatic(w, "codec", java.util.List.of(codec));
        }
        if (sample != null) {
            org.teamvan.moneyshift.components.VideoSample.writeColumnsStatic(w, "sample", java.util.List.of(sample));
        }
        if (isKeyframe != null) {
            org.teamvan.moneyshift.components.IsKeyframe.writeColumnsStatic(w, "is_keyframe", java.util.List.of(isKeyframe));
        }
        if (opacity != null) {
            org.teamvan.moneyshift.components.Opacity.writeColumnsStatic(w, "opacity", java.util.List.of(opacity));
        }
        if (drawOrder != null) {
            org.teamvan.moneyshift.components.DrawOrder.writeColumnsStatic(w, "draw_order", java.util.List.of(drawOrder));
        }
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

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withIsKeyframe(boolean value) {
            return withIsKeyframe(new org.teamvan.moneyshift.components.IsKeyframe(value));
        }

        public Builder withOpacity(org.teamvan.moneyshift.components.Opacity value) {
            this.opacity = value;
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withOpacity(float value) {
            return withOpacity(new org.teamvan.moneyshift.components.Opacity(value));
        }

        public Builder withDrawOrder(org.teamvan.moneyshift.components.DrawOrder value) {
            this.drawOrder = value;
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withDrawOrder(float value) {
            return withDrawOrder(new org.teamvan.moneyshift.components.DrawOrder(value));
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
