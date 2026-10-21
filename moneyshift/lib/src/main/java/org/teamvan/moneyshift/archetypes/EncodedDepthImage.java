// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A depth image encoded with a codec (e.g. RVL, PNG, or TIFF).
 * 
 * Rerun also supports uncompressed depth images with the {@link org.teamvan.moneyshift.archetypes.DepthImage}.
 * 
 */
public record EncodedDepthImage(
    /**
     * The encoded depth payload.
     * 
     * Supported are:
     * * single channel PNG
     * * single channel TIFF with {@code U8}, {@code U16}, or {@code F32} samples
     * * RVL with ROS2 metadata (for details see &lt;https://github.com/ros-perception/image_transport_plugins/tree/jazzy&gt;)
     */
    org.teamvan.moneyshift.components.Blob blob,
    /**
     * Media type of the blob, e.g.:
     * 
     *  * {@code application/rvl} (RVL-compressed 16-bit)
     *  * {@code image/png}
     *  * {@code image/tiff}
     */
    org.teamvan.moneyshift.components.MediaType mediaType,
    /**
     * Conversion from native units to meters (e.g. {@code 0.001} for millimeters).
     * 
     * If omitted, the Viewer defaults to {@code 1.0} for floating-point depth formats and {@code 1000.0} for integer formats (millimeters).
     */
    org.teamvan.moneyshift.components.DepthMeter meter,
    /**
     * Optional colormap for visualization of decoded depth.
     */
    org.teamvan.moneyshift.components.Colormap colormap,
    /**
     * Optional visualization range for depth values.
     */
    org.teamvan.moneyshift.components.ValueRange depthRange,
    /**
     * Optional point fill ratio for point-cloud projection.
     */
    org.teamvan.moneyshift.components.FillRatio pointFillRatio,
    /**
     * Optional 2D draw order.
     */
    org.teamvan.moneyshift.components.DrawOrder drawOrder,
    /**
     * Optional filter used when a texel is magnified (displayed larger than a screen pixel) in 2D views.
     * 
     * The filter is applied to the scalar values *before* they are mapped to color via the colormap.
     * 
     * Has no effect in 3D views.
     */
    org.teamvan.moneyshift.components.MagnificationFilter magnificationFilter
) implements org.teamvan.moneyshift.Archetype {
    public EncodedDepthImage {
        java.util.Objects.requireNonNull(blob, "blob");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.EncodedDepthImage";
    public static final int NUM_COMPONENTS = 8;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_BLOB = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EncodedDepthImage:blob", "rerun.components.Blob");

    /**
     * The encoded depth payload.
     * 
     * Supported are:
     * * single channel PNG
     * * single channel TIFF with {@code U8}, {@code U16}, or {@code F32} samples
     * * RVL with ROS2 metadata (for details see &lt;https://github.com/ros-perception/image_transport_plugins/tree/jazzy&gt;)
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorBlob() {
        return DESCRIPTOR_BLOB;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MEDIA_TYPE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EncodedDepthImage:media_type", "rerun.components.MediaType");

    /**
     * Media type of the blob, e.g.:
     * 
     *  * {@code application/rvl} (RVL-compressed 16-bit)
     *  * {@code image/png}
     *  * {@code image/tiff}
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMediaType() {
        return DESCRIPTOR_MEDIA_TYPE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_METER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EncodedDepthImage:meter", "rerun.components.DepthMeter");

    /**
     * Conversion from native units to meters (e.g. {@code 0.001} for millimeters).
     * 
     * If omitted, the Viewer defaults to {@code 1.0} for floating-point depth formats and {@code 1000.0} for integer formats (millimeters).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMeter() {
        return DESCRIPTOR_METER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORMAP = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EncodedDepthImage:colormap", "rerun.components.Colormap");

    /**
     * Optional colormap for visualization of decoded depth.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColormap() {
        return DESCRIPTOR_COLORMAP;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DEPTH_RANGE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EncodedDepthImage:depth_range", "rerun.components.ValueRange");

    /**
     * Optional visualization range for depth values.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorDepthRange() {
        return DESCRIPTOR_DEPTH_RANGE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_POINT_FILL_RATIO = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EncodedDepthImage:point_fill_ratio", "rerun.components.FillRatio");

    /**
     * Optional point fill ratio for point-cloud projection.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorPointFillRatio() {
        return DESCRIPTOR_POINT_FILL_RATIO;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DRAW_ORDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EncodedDepthImage:draw_order", "rerun.components.DrawOrder");

    /**
     * Optional 2D draw order.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorDrawOrder() {
        return DESCRIPTOR_DRAW_ORDER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MAGNIFICATION_FILTER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EncodedDepthImage:magnification_filter", "rerun.components.MagnificationFilter");

    /**
     * Optional filter used when a texel is magnified (displayed larger than a screen pixel) in 2D views.
     * 
     * The filter is applied to the scalar values *before* they are mapped to color via the colormap.
     * 
     * Has no effect in 3D views.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMagnificationFilter() {
        return DESCRIPTOR_MAGNIFICATION_FILTER;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorBlob());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorMediaType(), descriptorMeter());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorColormap(), descriptorDepthRange(), descriptorPointFillRatio(), descriptorDrawOrder(), descriptorMagnificationFilter());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.Blob.batch(descriptorBlob(), allocator, blob));
        if (mediaType != null) {
            batches.add(org.teamvan.moneyshift.components.MediaType.batch(descriptorMediaType(), allocator, mediaType));
        }
        if (meter != null) {
            batches.add(org.teamvan.moneyshift.components.DepthMeter.batch(descriptorMeter(), allocator, meter));
        }
        if (colormap != null) {
            batches.add(org.teamvan.moneyshift.components.Colormap.batch(descriptorColormap(), allocator, colormap));
        }
        if (depthRange != null) {
            batches.add(org.teamvan.moneyshift.components.ValueRange.batch(descriptorDepthRange(), allocator, depthRange));
        }
        if (pointFillRatio != null) {
            batches.add(org.teamvan.moneyshift.components.FillRatio.batch(descriptorPointFillRatio(), allocator, pointFillRatio));
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
        private org.teamvan.moneyshift.components.DepthMeter meter;
        private org.teamvan.moneyshift.components.Colormap colormap;
        private org.teamvan.moneyshift.components.ValueRange depthRange;
        private org.teamvan.moneyshift.components.FillRatio pointFillRatio;
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

        public Builder withMeter(org.teamvan.moneyshift.components.DepthMeter value) {
            this.meter = value;
            return this;
        }

        public Builder withColormap(org.teamvan.moneyshift.components.Colormap value) {
            this.colormap = value;
            return this;
        }

        public Builder withDepthRange(org.teamvan.moneyshift.components.ValueRange value) {
            this.depthRange = value;
            return this;
        }

        public Builder withPointFillRatio(org.teamvan.moneyshift.components.FillRatio value) {
            this.pointFillRatio = value;
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

        public EncodedDepthImage build() {
            return new EncodedDepthImage(blob, mediaType, meter, colormap, depthRange, pointFillRatio, drawOrder, magnificationFilter);
        }
    }

    public static EncodedDepthImage of(
        org.teamvan.moneyshift.components.Blob blob
    ) {
        return new EncodedDepthImage(blob, null, null, null, null, null, null, null);
    }
}
