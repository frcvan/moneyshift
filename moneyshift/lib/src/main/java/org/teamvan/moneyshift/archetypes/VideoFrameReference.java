// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * References a single video frame.
 * 
 * Used to display individual video frames from an {@link org.teamvan.moneyshift.archetypes.AssetVideo} or {@link org.teamvan.moneyshift.archetypes.VideoStream}.
 * To show an entire video, a video frame reference for each frame of the video should be logged.
 * References to a {@link org.teamvan.moneyshift.archetypes.VideoStream} use the active Viewer timeline.
 * 
 * See <https://rerun.io/docs/reference/video> for details of what is and isn't supported.
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
     * Optional reference to an entity with an {@link org.teamvan.moneyshift.archetypes.AssetVideo} or {@link org.teamvan.moneyshift.archetypes.VideoStream}.
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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TIMESTAMP = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VideoFrameReference:timestamp", "rerun.components.VideoTimestamp");

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
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTimestamp() {
        return DESCRIPTOR_TIMESTAMP;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VIDEO_REFERENCE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VideoFrameReference:video_reference", "rerun.components.EntityPath");

    /**
     * Optional reference to an entity with an {@link org.teamvan.moneyshift.archetypes.AssetVideo} or {@link org.teamvan.moneyshift.archetypes.VideoStream}.
     * 
     * If none is specified, the video is assumed to be at the same entity.
     * Note that blueprint overrides on the referenced video will be ignored regardless,
     * as this is always interpreted as a reference to the data store.
     * 
     * For a series of video frame references, it is recommended to specify this path only once
     * at the beginning of the series and then rely on latest-at query semantics to
     * keep the video reference active.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVideoReference() {
        return DESCRIPTOR_VIDEO_REFERENCE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_OPACITY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VideoFrameReference:opacity", "rerun.components.Opacity");

    /**
     * Opacity of the video, useful for layering several media.
     * 
     * Defaults to 1.0 (fully opaque).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorOpacity() {
        return DESCRIPTOR_OPACITY;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DRAW_ORDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VideoFrameReference:draw_order", "rerun.components.DrawOrder");

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
        return java.util.List.of(descriptorTimestamp());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorVideoReference(), descriptorOpacity(), descriptorDrawOrder());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.VideoTimestamp.batch(descriptorTimestamp(), allocator, timestamp));
        if (videoReference != null) {
            batches.add(org.teamvan.moneyshift.components.EntityPath.batch(descriptorVideoReference(), allocator, videoReference));
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
        private org.teamvan.moneyshift.components.VideoTimestamp timestamp;
        private org.teamvan.moneyshift.components.EntityPath videoReference;
        private org.teamvan.moneyshift.components.Opacity opacity;
        private org.teamvan.moneyshift.components.DrawOrder drawOrder;
        private Builder() {}

        public Builder withTimestamp(org.teamvan.moneyshift.components.VideoTimestamp value) {
            this.timestamp = value;
            return this;
        }

        public Builder withVideoReference(org.teamvan.moneyshift.components.EntityPath value) {
            this.videoReference = value;
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
