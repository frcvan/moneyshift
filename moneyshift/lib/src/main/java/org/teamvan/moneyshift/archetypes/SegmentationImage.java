// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * An image made up of integer {@link org.teamvan.moneyshift.components.ClassId}s.
 * 
 * Each pixel corresponds to a {@link org.teamvan.moneyshift.components.ClassId} that will be mapped to a color based on {@link org.teamvan.moneyshift.archetypes.AnnotationContext}.
 * 
 * In the case of floating point images, the label will be looked up based on rounding to the nearest
 * integer value.
 * 
 * Use {@link org.teamvan.moneyshift.archetypes.AnnotationContext} to associate each class with a color and a label.
 * 
 * 
 */
public record SegmentationImage(
    /**
     * The raw image data.
     */
    org.teamvan.moneyshift.components.ImageBuffer buffer,
    /**
     * The format of the image.
     */
    org.teamvan.moneyshift.components.ImageFormat format,
    /**
     * Opacity of the image, useful for layering the segmentation image on top of another image.
     * 
     * Defaults to 0.5 if there's any other images in the scene, otherwise 1.0.
     */
    org.teamvan.moneyshift.components.Opacity opacity,
    /**
     * An optional floating point value that specifies the 2D drawing order.
     * 
     * Objects with higher values are drawn on top of those with lower values.
     * Defaults to {@code 0.0}.
     */
    org.teamvan.moneyshift.components.DrawOrder drawOrder
) implements org.teamvan.moneyshift.Archetype {
    public SegmentationImage {
        java.util.Objects.requireNonNull(buffer, "buffer");
        java.util.Objects.requireNonNull(format, "format");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.SegmentationImage";
    public static final int NUM_COMPONENTS = 4;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_BUFFER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SegmentationImage:buffer", "rerun.components.ImageBuffer");

    /**
     * The raw image data.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorBuffer() {
        return DESCRIPTOR_BUFFER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FORMAT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SegmentationImage:format", "rerun.components.ImageFormat");

    /**
     * The format of the image.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFormat() {
        return DESCRIPTOR_FORMAT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_OPACITY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SegmentationImage:opacity", "rerun.components.Opacity");

    /**
     * Opacity of the image, useful for layering the segmentation image on top of another image.
     * 
     * Defaults to 0.5 if there's any other images in the scene, otherwise 1.0.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorOpacity() {
        return DESCRIPTOR_OPACITY;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DRAW_ORDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SegmentationImage:draw_order", "rerun.components.DrawOrder");

    /**
     * An optional floating point value that specifies the 2D drawing order.
     * 
     * Objects with higher values are drawn on top of those with lower values.
     * Defaults to {@code 0.0}.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorDrawOrder() {
        return DESCRIPTOR_DRAW_ORDER;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorBuffer(), descriptorFormat());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorOpacity(), descriptorDrawOrder());
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
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.ImageBuffer buffer;
        private org.teamvan.moneyshift.components.ImageFormat format;
        private org.teamvan.moneyshift.components.Opacity opacity;
        private org.teamvan.moneyshift.components.DrawOrder drawOrder;
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

        public Builder withDrawOrder(org.teamvan.moneyshift.components.DrawOrder value) {
            this.drawOrder = value;
            return this;
        }

        public SegmentationImage build() {
            return new SegmentationImage(buffer, format, opacity, drawOrder);
        }
    }

    public static SegmentationImage of(
        org.teamvan.moneyshift.components.ImageBuffer buffer,
        org.teamvan.moneyshift.components.ImageFormat format
    ) {
        return new SegmentationImage(buffer, format, null, null);
    }
}
