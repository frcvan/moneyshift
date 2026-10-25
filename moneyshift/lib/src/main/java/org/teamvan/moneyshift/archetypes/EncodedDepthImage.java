// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A depth image encoded with a codec (e.g. RVL, PNG, or TIFF).
 * 
 * Rerun also supports uncompressed depth images with the {@link org.teamvan.moneyshift.archetypes.DepthImage archetypes.DepthImage}.
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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    private static final class ComponentsCache {
        final long mask;
        final java.lang.String components;

        ComponentsCache(long mask, java.lang.String components) {
            this.mask = mask;
            this.components = components;
        }
    }

    private static volatile ComponentsCache cachedComponents;

    private static java.lang.String componentsFor(long mask) {
        ComponentsCache cached = cachedComponents;
        if (cached != null && cached.mask == mask) {
            return cached.components;
        }
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if ((mask & 1L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("blob").append('=').append(org.teamvan.moneyshift.components.Blob.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("media_type").append('=').append(org.teamvan.moneyshift.components.MediaType.COMPONENT_TYPE);
        }
        if ((mask & 4L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("meter").append('=').append(org.teamvan.moneyshift.components.DepthMeter.COMPONENT_TYPE);
        }
        if ((mask & 8L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("colormap").append('=').append(org.teamvan.moneyshift.components.Colormap.COMPONENT_TYPE);
        }
        if ((mask & 16L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("depth_range").append('=').append(org.teamvan.moneyshift.components.ValueRange.COMPONENT_TYPE);
        }
        if ((mask & 32L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("point_fill_ratio").append('=').append(org.teamvan.moneyshift.components.FillRatio.COMPONENT_TYPE);
        }
        if ((mask & 64L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("draw_order").append('=').append(org.teamvan.moneyshift.components.DrawOrder.COMPONENT_TYPE);
        }
        if ((mask & 128L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("magnification_filter").append('=').append(org.teamvan.moneyshift.components.MagnificationFilter.COMPONENT_TYPE);
        }
        java.lang.String joined = components.toString();
        cachedComponents = new ComponentsCache(mask, joined);
        return joined;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        long mask = 0L;
        if (true) mask |= 1L;
        if (mediaType != null) mask |= 2L;
        if (meter != null) mask |= 4L;
        if (colormap != null) mask |= 8L;
        if (depthRange != null) mask |= 16L;
        if (pointFillRatio != null) mask |= 32L;
        if (drawOrder != null) mask |= 64L;
        if (magnificationFilter != null) mask |= 128L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (true) {
            org.teamvan.moneyshift.components.Blob.writeColumnsStatic(w, "blob", java.util.List.of(blob));
        }
        if (mediaType != null) {
            org.teamvan.moneyshift.components.MediaType.writeColumnsStatic(w, "media_type", java.util.List.of(mediaType));
        }
        if (meter != null) {
            org.teamvan.moneyshift.components.DepthMeter.writeColumnsStatic(w, "meter", java.util.List.of(meter));
        }
        if (colormap != null) {
            org.teamvan.moneyshift.components.Colormap.writeColumnsStatic(w, "colormap", java.util.List.of(colormap));
        }
        if (depthRange != null) {
            org.teamvan.moneyshift.components.ValueRange.writeColumnsStatic(w, "depth_range", java.util.List.of(depthRange));
        }
        if (pointFillRatio != null) {
            org.teamvan.moneyshift.components.FillRatio.writeColumnsStatic(w, "point_fill_ratio", java.util.List.of(pointFillRatio));
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

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withMediaType(java.lang.String value) {
            return withMediaType(new org.teamvan.moneyshift.components.MediaType(value));
        }

        public Builder withMeter(org.teamvan.moneyshift.components.DepthMeter value) {
            this.meter = value;
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withMeter(float value) {
            return withMeter(new org.teamvan.moneyshift.components.DepthMeter(value));
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

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withPointFillRatio(float value) {
            return withPointFillRatio(new org.teamvan.moneyshift.components.FillRatio(value));
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
