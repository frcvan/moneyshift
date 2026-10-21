// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * An image encoded as e.g. a JPEG or PNG.
 * 
 * Rerun also supports uncompressed images with the {@link org.teamvan.moneyshift.archetypes.Image}.
 * For images that refer to video frames see {@link org.teamvan.moneyshift.archetypes.VideoFrameReference}.
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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_BLOB = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EncodedImage:blob", "rerun.components.Blob");

    /**
     * The encoded content of some image file, e.g. a PNG or JPEG.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorBlob() {
        return DESCRIPTOR_BLOB;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MEDIA_TYPE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EncodedImage:media_type", "rerun.components.MediaType");

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
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMediaType() {
        return DESCRIPTOR_MEDIA_TYPE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_OPACITY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EncodedImage:opacity", "rerun.components.Opacity");

    /**
     * Opacity of the image, useful for layering several media.
     * 
     * Defaults to 1.0 (fully opaque).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorOpacity() {
        return DESCRIPTOR_OPACITY;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DRAW_ORDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EncodedImage:draw_order", "rerun.components.DrawOrder");

    /**
     * An optional floating point value that specifies the 2D drawing order.
     * 
     * Objects with higher values are drawn on top of those with lower values.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorDrawOrder() {
        return DESCRIPTOR_DRAW_ORDER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MAGNIFICATION_FILTER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EncodedImage:magnification_filter", "rerun.components.MagnificationFilter");

    /**
     * Optional filter used when a texel is magnified (displayed larger than a screen pixel).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMagnificationFilter() {
        return DESCRIPTOR_MAGNIFICATION_FILTER;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorBlob());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorMediaType());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorOpacity(), descriptorDrawOrder(), descriptorMagnificationFilter());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.Blob.batch(descriptorBlob(), allocator, blob));
        if (mediaType != null) {
            batches.add(org.teamvan.moneyshift.components.MediaType.batch(descriptorMediaType(), allocator, mediaType));
        }
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

        public Builder withOpacity(org.teamvan.moneyshift.components.Opacity value) {
            this.opacity = value;
            return this;
        }

        public Builder withDrawOrder(org.teamvan.moneyshift.components.DrawOrder value) {
            this.drawOrder = value;
            return this;
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
