// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * References a single video frame.
 * 
 * Used to display individual video frames from an {@link org.teamvan.moneyshift.archetypes.AssetVideo rerun::archetypes::AssetVideo} or {@link org.teamvan.moneyshift.archetypes.VideoStream rerun::archetypes::VideoStream}.
 * To show an entire video, a video frame reference for each frame of the video should be logged.
 * References to a {@link org.teamvan.moneyshift.archetypes.VideoStream rerun::archetypes::VideoStream} use the active Viewer timeline.
 * 
 * See &lt;https://rerun.io/docs/reference/video&gt; for details of what is and isn't supported.
 * 
 */
public record VideoFrameReference(
    /**
     * References the closest video frame to this timestamp.
     * 
     * Note that this uses the closest video frame instead of the latest at this timestamp
     * in order to be more forgiving of rounding errors for inprecise timestamp types.
     * 
     * Timestamps are relative to the start of the video, i.e. a timestamp of 0 always corresponds to the first frame.
     * This is oftentimes equivalent to presentation timestamps (known as PTS), but in the presence of B-frames
     * (bidirectionally predicted frames) there may be an offset on the first presentation timestamp in the video.
     */
    org.teamvan.moneyshift.components.VideoTimestamp timestamp,
    /**
     * Optional reference to an entity with an {@link org.teamvan.moneyshift.archetypes.AssetVideo rerun::archetypes::AssetVideo} or {@link org.teamvan.moneyshift.archetypes.VideoStream rerun::archetypes::VideoStream}.
     * 
     * If none is specified, the video is assumed to be at the same entity.
     * Note that blueprint overrides on the referenced video will be ignored regardless,
     * as this is always interpreted as a reference to the data store.
     * 
     * For a series of video frame references, it is recommended to specify this path only once
     * at the beginning of the series and then rely on latest-at query semantics to
     * keep the video reference active.
     */
    org.teamvan.moneyshift.components.EntityPath videoReference,
    /**
     * Opacity of the video, useful for layering several media.
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
    public VideoFrameReference {
        java.util.Objects.requireNonNull(timestamp, "timestamp");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.VideoFrameReference";
    public static final int NUM_COMPONENTS = 4;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("timestamp").append('=').append(org.teamvan.moneyshift.components.VideoTimestamp.COMPONENT_TYPE);
        }
        if (videoReference != null) {
            if (components.length() > 0) components.append(',');
            components.append("video_reference").append('=').append(org.teamvan.moneyshift.components.EntityPath.COMPONENT_TYPE);
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
            org.teamvan.moneyshift.components.VideoTimestamp.writeColumnsStatic(w, "timestamp", java.util.List.of(timestamp));
        }
        if (videoReference != null) {
            org.teamvan.moneyshift.components.EntityPath.writeColumnsStatic(w, "video_reference", java.util.List.of(videoReference));
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
        private org.teamvan.moneyshift.components.VideoTimestamp timestamp;
        private org.teamvan.moneyshift.components.EntityPath videoReference;
        private org.teamvan.moneyshift.components.Opacity opacity;
        private org.teamvan.moneyshift.components.DrawOrder drawOrder;
        private Builder() {}

        public Builder withTimestamp(org.teamvan.moneyshift.components.VideoTimestamp value) {
            this.timestamp = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withTimestamp(long value) {
            return withTimestamp(new org.teamvan.moneyshift.components.VideoTimestamp(value));
        }

        public Builder withVideoReference(org.teamvan.moneyshift.components.EntityPath value) {
            this.videoReference = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withVideoReference(java.lang.String value) {
            return withVideoReference(new org.teamvan.moneyshift.components.EntityPath(value));
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

        public VideoFrameReference build() {
            return new VideoFrameReference(timestamp, videoReference, opacity, drawOrder);
        }
    }

    public static VideoFrameReference of(
        org.teamvan.moneyshift.components.VideoTimestamp timestamp
    ) {
        return new VideoFrameReference(timestamp, null, null, null);
    }
}
