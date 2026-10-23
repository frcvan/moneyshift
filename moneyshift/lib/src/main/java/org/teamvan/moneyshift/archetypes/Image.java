// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A monochrome or color image.
 * 
 * See also {@link org.teamvan.moneyshift.archetypes.DepthImage rerun::archetypes::DepthImage} and {@link org.teamvan.moneyshift.archetypes.SegmentationImage rerun::archetypes::SegmentationImage}.
 * 
 * Rerun also supports compressed images (JPEG, PNG, …), using {@link org.teamvan.moneyshift.archetypes.EncodedImage rerun::archetypes::EncodedImage}.
 * For images that refer to video frames see {@link org.teamvan.moneyshift.archetypes.VideoFrameReference rerun::archetypes::VideoFrameReference}.
 * Compressing images or using video data instead can save a lot of bandwidth and memory.
 * 
 * The raw image data is stored as a single buffer of bytes in a {@link org.teamvan.moneyshift.components.Blob rerun::components::Blob}.
 * The meaning of these bytes is determined by the {@link org.teamvan.moneyshift.components.ImageFormat rerun::components::ImageFormat} which specifies the resolution
 * and the pixel format (e.g. RGB, RGBA, …).
 * 
 * The order of dimensions in the underlying {@link org.teamvan.moneyshift.components.Blob rerun::components::Blob} follows the typical
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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("buffer").append('=').append(org.teamvan.moneyshift.components.ImageBuffer.COMPONENT_TYPE);
        }
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("format").append('=').append(org.teamvan.moneyshift.components.ImageFormat.COMPONENT_TYPE);
        }
        if (opacity != null) {
            if (components.length() > 0) components.append(',');
            components.append("opacity").append('=').append(org.teamvan.moneyshift.components.Opacity.COMPONENT_TYPE);
        }
        if (drawOrder != null) {
            if (components.length() > 0) components.append(',');
            components.append("draw_order").append('=').append(org.teamvan.moneyshift.components.DrawOrder.COMPONENT_TYPE);
        }
        if (magnificationFilter != null) {
            if (components.length() > 0) components.append(',');
            components.append("magnification_filter").append('=').append(org.teamvan.moneyshift.components.MagnificationFilter.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (true) {
            org.teamvan.moneyshift.components.ImageBuffer.writeColumnsStatic(w, "buffer", java.util.List.of(buffer));
        }
        if (true) {
            org.teamvan.moneyshift.components.ImageFormat.writeColumnsStatic(w, "format", java.util.List.of(format));
        }
        if (opacity != null) {
            org.teamvan.moneyshift.components.Opacity.writeColumnsStatic(w, "opacity", java.util.List.of(opacity));
        }
        if (drawOrder != null) {
            org.teamvan.moneyshift.components.DrawOrder.writeColumnsStatic(w, "draw_order", java.util.List.of(drawOrder));
        }
        if (magnificationFilter != null) {
            org.teamvan.moneyshift.components.MagnificationFilter.writeColumnsStatic(w, "magnification_filter", java.util.List.of(magnificationFilter));
        }
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
