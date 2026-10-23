// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * An image encoded as e.g. a JPEG or PNG.
 * 
 * Rerun also supports uncompressed images with the {@link org.teamvan.moneyshift.archetypes.Image rerun::archetypes::Image}.
 * For images that refer to video frames see {@link org.teamvan.moneyshift.archetypes.VideoFrameReference rerun::archetypes::VideoFrameReference}.
 * 
 * 
 */
public record EncodedImage(
    /**
     * The encoded content of some image file, e.g. a PNG or JPEG.
     */
    org.teamvan.moneyshift.components.Blob blob,
    /**
     * The Media Type of the asset.
     * 
     * Supported values:
     * * {@code image/jpeg}
     * * {@code image/png}
     * 
     * If omitted, the viewer will try to guess from the data blob.
     * If it cannot guess, it won't be able to render the asset.
     */
    org.teamvan.moneyshift.components.MediaType mediaType,
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
     */
    org.teamvan.moneyshift.components.DrawOrder drawOrder,
    /**
     * Optional filter used when a texel is magnified (displayed larger than a screen pixel).
     */
    org.teamvan.moneyshift.components.MagnificationFilter magnificationFilter
) implements org.teamvan.moneyshift.Archetype {
    public EncodedImage {
        java.util.Objects.requireNonNull(blob, "blob");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.EncodedImage";
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
            components.append("blob").append('=').append(org.teamvan.moneyshift.components.Blob.COMPONENT_TYPE);
        }
        if (mediaType != null) {
            if (components.length() > 0) components.append(',');
            components.append("media_type").append('=').append(org.teamvan.moneyshift.components.MediaType.COMPONENT_TYPE);
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
            org.teamvan.moneyshift.components.Blob.writeColumnsStatic(w, "blob", java.util.List.of(blob));
        }
        if (mediaType != null) {
            org.teamvan.moneyshift.components.MediaType.writeColumnsStatic(w, "media_type", java.util.List.of(mediaType));
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
        private org.teamvan.moneyshift.components.Blob blob;
        private org.teamvan.moneyshift.components.MediaType mediaType;
        private org.teamvan.moneyshift.components.Opacity opacity;
        private org.teamvan.moneyshift.components.DrawOrder drawOrder;
        private org.teamvan.moneyshift.components.MagnificationFilter magnificationFilter;
        private Builder() {}

        public Builder withBlob(org.teamvan.moneyshift.components.Blob value) {
            this.blob = value;
            return this;
        }

        public Builder withMediaType(org.teamvan.moneyshift.components.MediaType value) {
            this.mediaType = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withMediaType(java.lang.String value) {
            return withMediaType(new org.teamvan.moneyshift.components.MediaType(value));
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

        public EncodedImage build() {
            return new EncodedImage(blob, mediaType, opacity, drawOrder, magnificationFilter);
        }
    }

    public static EncodedImage of(
        org.teamvan.moneyshift.components.Blob blob
    ) {
        return new EncodedImage(blob, null, null, null, null);
    }
}
