// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A monochrome or color image.
 * 
 * See also {@link org.teamvan.moneyshift.archetypes.DepthImage} and {@link org.teamvan.moneyshift.archetypes.SegmentationImage}.
 * 
 * Rerun also supports compressed images (JPEG, PNG, …), using {@link org.teamvan.moneyshift.archetypes.EncodedImage}.
 * For images that refer to video frames see {@link org.teamvan.moneyshift.archetypes.VideoFrameReference}.
 * Compressing images or using video data instead can save a lot of bandwidth and memory.
 * 
 * The raw image data is stored as a single buffer of bytes in a {@link org.teamvan.moneyshift.components.Blob}.
 * The meaning of these bytes is determined by the {@link org.teamvan.moneyshift.components.ImageFormat} which specifies the resolution
 * and the pixel format (e.g. RGB, RGBA, …).
 * 
 * The order of dimensions in the underlying {@link org.teamvan.moneyshift.components.Blob} follows the typical
 * row-major, interleaved-pixel image format.
 * 
 * 
 */
public record Image(
    /**
     * The raw image data.
     */
    org.teamvan.moneyshift.components.ImageBuffer buffer,
    /**
     * The format of the image.
     */
    org.teamvan.moneyshift.components.ImageFormat format,
    /**
     * Opacity of the image, useful for layering several media.
     * 
     * Defaults to 1.0 (fully opaque).
     */
    org.teamvan.moneyshift.components.Opacity opacity,
    /**
     * An optional floating point value that specifies the 2D drawing order.
     * 
     * Objects with higher values are drawn on top of those with lower values.
     * Defaults to {@code -10.0}.
     */
    org.teamvan.moneyshift.components.DrawOrder drawOrder,
    /**
     * Optional filter used when a texel is magnified (displayed larger than a screen pixel).
     */
    org.teamvan.moneyshift.components.MagnificationFilter magnificationFilter
) implements org.teamvan.moneyshift.Archetype {
    public Image {
        java.util.Objects.requireNonNull(buffer, "buffer");
        java.util.Objects.requireNonNull(format, "format");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Image";
    public static final int NUM_COMPONENTS = 5;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_BUFFER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Image:buffer", "rerun.components.ImageBuffer");

    /**
     * The raw image data.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorBuffer() {
        return DESCRIPTOR_BUFFER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FORMAT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Image:format", "rerun.components.ImageFormat");

    /**
     * The format of the image.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFormat() {
        return DESCRIPTOR_FORMAT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_OPACITY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Image:opacity", "rerun.components.Opacity");

    /**
     * Opacity of the image, useful for layering several media.
     * 
     * Defaults to 1.0 (fully opaque).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorOpacity() {
        return DESCRIPTOR_OPACITY;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DRAW_ORDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Image:draw_order", "rerun.components.DrawOrder");

    /**
     * An optional floating point value that specifies the 2D drawing order.
     * 
     * Objects with higher values are drawn on top of those with lower values.
     * Defaults to {@code -10.0}.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorDrawOrder() {
        return DESCRIPTOR_DRAW_ORDER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MAGNIFICATION_FILTER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Image:magnification_filter", "rerun.components.MagnificationFilter");

    /**
     * Optional filter used when a texel is magnified (displayed larger than a screen pixel).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMagnificationFilter() {
        return DESCRIPTOR_MAGNIFICATION_FILTER;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorBuffer(), descriptorFormat());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorOpacity(), descriptorDrawOrder(), descriptorMagnificationFilter());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.ImageBuffer.batch(descriptorBuffer(), allocator, buffer));
        batches.add(org.teamvan.moneyshift.components.ImageFormat.batch(descriptorFormat(), allocator, format));
        if (opacity != null) {
            batches.add(org.teamvan.moneyshift.components.Opacity.batch(descriptorOpacity(), allocator, opacity));
        }
        if (drawOrder != null) {
            batches.add(org.teamvan.moneyshift.components.DrawOrder.batch(descriptorDrawOrder(), allocator, drawOrder));
        }
        if (magnificationFilter != null) {
            batches.add(org.teamvan.moneyshift.components.MagnificationFilter.batch(descriptorMagnificationFilter(), allocator, magnificationFilter));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.ImageBuffer buffer;
        private org.teamvan.moneyshift.components.ImageFormat format;
        private org.teamvan.moneyshift.components.Opacity opacity;
        private org.teamvan.moneyshift.components.DrawOrder drawOrder;
        private org.teamvan.moneyshift.components.MagnificationFilter magnificationFilter;
        private Builder() {}

        public Builder withBuffer(org.teamvan.moneyshift.components.ImageBuffer value) {
            this.buffer = value;
            return this;
        }

        public Builder withFormat(org.teamvan.moneyshift.components.ImageFormat value) {
            this.format = value;
            return this;
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

        public Builder withMagnificationFilter(org.teamvan.moneyshift.components.MagnificationFilter value) {
            this.magnificationFilter = value;
            return this;
        }

        public Image build() {
            return new Image(buffer, format, opacity, drawOrder, magnificationFilter);
        }
    }

    public static Image of(
        org.teamvan.moneyshift.components.ImageBuffer buffer,
        org.teamvan.moneyshift.components.ImageFormat format
    ) {
        return new Image(buffer, format, null, null, null);
    }
}
