// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A depth image, i.e. as captured by a depth camera.
 * 
 * Each pixel corresponds to a depth value in units specified by {@link org.teamvan.moneyshift.components.DepthMeter rerun::components::DepthMeter}.
 * 
 * 
 */
public record DepthImage(
    /**
     * The raw depth image data.
     */
    org.teamvan.moneyshift.components.ImageBuffer buffer,
    /**
     * The format of the image.
     */
    org.teamvan.moneyshift.components.ImageFormat format,
    /**
     * An optional floating point value that specifies how long a meter is in the native depth units.
     * 
     * For instance: with uint16, perhaps meter=1000 which would mean you have millimeter precision
     * and a range of up to ~65 meters (2^16 / 1000).
     * 
     * If omitted, the Viewer defaults to {@code 1.0} for floating-point depth formats and {@code 1000.0} for integer formats (millimeters).
     * 
     * Note that the only effect on 2D views is the physical depth values shown when hovering the image.
     * In 3D views on the other hand, this affects where the points of the point cloud are placed.
     */
    org.teamvan.moneyshift.components.DepthMeter meter,
    /**
     * Colormap to use for rendering the depth image.
     * 
     * If not set, the depth image will be rendered using the Turbo colormap.
     */
    org.teamvan.moneyshift.components.Colormap colormap,
    /**
     * The expected range of depth values.
     * 
     * This is typically the expected range of valid values.
     * Everything outside of the range is clamped to the range for the purpose of colormpaping.
     * Note that point clouds generated from this image will still display all points, regardless of this range.
     * 
     * If not specified, the range will be automatically estimated from the data.
     * Note that the Viewer may try to guess a wider range than the minimum/maximum of values
     * in the contents of the depth image.
     * E.g. if all values are positive, some bigger than 1.0 and all smaller than 255.0,
     * the Viewer will guess that the data likely came from an 8bit image, thus assuming a range of 0-255.
     */
    org.teamvan.moneyshift.components.ValueRange depthRange,
    /**
     * Scale the radii of the points in the point cloud generated from this image.
     * 
     * A fill ratio of 1.0 (the default) means that each point is as big as to touch the center of its neighbor
     * if it is at the same depth, leaving no gaps.
     * A fill ratio of 0.5 means that each point touches the edge of its neighbor if it has the same depth.
     * 
     * TODO(#6744): This applies only to 3D views!
     */
    org.teamvan.moneyshift.components.FillRatio pointFillRatio,
    /**
     * An optional floating point value that specifies the 2D drawing order, used only if the depth image is shown as a 2D image.
     * 
     * Objects with higher values are drawn on top of those with lower values.
     * Defaults to {@code -20.0}.
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
    public DepthImage {
        java.util.Objects.requireNonNull(buffer, "buffer");
        java.util.Objects.requireNonNull(format, "format");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.DepthImage";
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
            components.append("buffer").append('=').append(org.teamvan.moneyshift.components.ImageBuffer.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("format").append('=').append(org.teamvan.moneyshift.components.ImageFormat.COMPONENT_TYPE);
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
        if (true) mask |= 2L;
        if (meter != null) mask |= 4L;
        if (colormap != null) mask |= 8L;
        if (depthRange != null) mask |= 16L;
        if (pointFillRatio != null) mask |= 32L;
        if (drawOrder != null) mask |= 64L;
        if (magnificationFilter != null) mask |= 128L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (true) {
            org.teamvan.moneyshift.components.ImageBuffer.writeColumnsStatic(w, "buffer", java.util.List.of(buffer));
        }
        if (true) {
            org.teamvan.moneyshift.components.ImageFormat.writeColumnsStatic(w, "format", java.util.List.of(format));
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
        private org.teamvan.moneyshift.components.ImageBuffer buffer;
        private org.teamvan.moneyshift.components.ImageFormat format;
        private org.teamvan.moneyshift.components.DepthMeter meter;
        private org.teamvan.moneyshift.components.Colormap colormap;
        private org.teamvan.moneyshift.components.ValueRange depthRange;
        private org.teamvan.moneyshift.components.FillRatio pointFillRatio;
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

        public DepthImage build() {
            return new DepthImage(buffer, format, meter, colormap, depthRange, pointFillRatio, drawOrder, magnificationFilter);
        }
    }

    public static DepthImage of(
        org.teamvan.moneyshift.components.ImageBuffer buffer,
        org.teamvan.moneyshift.components.ImageFormat format
    ) {
        return new DepthImage(buffer, format, null, null, null, null, null, null);
    }
}
