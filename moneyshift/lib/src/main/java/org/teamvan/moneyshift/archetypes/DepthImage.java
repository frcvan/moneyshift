// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A depth image, i.e. as captured by a depth camera.
 * 
 * Each pixel corresponds to a depth value in units specified by {@link org.teamvan.moneyshift.components.DepthMeter}.
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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_BUFFER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DepthImage:buffer", "rerun.components.ImageBuffer");

    /**
     * The raw depth image data.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorBuffer() {
        return DESCRIPTOR_BUFFER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FORMAT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DepthImage:format", "rerun.components.ImageFormat");

    /**
     * The format of the image.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFormat() {
        return DESCRIPTOR_FORMAT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_METER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DepthImage:meter", "rerun.components.DepthMeter");

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
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMeter() {
        return DESCRIPTOR_METER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORMAP = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DepthImage:colormap", "rerun.components.Colormap");

    /**
     * Colormap to use for rendering the depth image.
     * 
     * If not set, the depth image will be rendered using the Turbo colormap.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColormap() {
        return DESCRIPTOR_COLORMAP;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DEPTH_RANGE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DepthImage:depth_range", "rerun.components.ValueRange");

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
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorDepthRange() {
        return DESCRIPTOR_DEPTH_RANGE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_POINT_FILL_RATIO = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DepthImage:point_fill_ratio", "rerun.components.FillRatio");

    /**
     * Scale the radii of the points in the point cloud generated from this image.
     * 
     * A fill ratio of 1.0 (the default) means that each point is as big as to touch the center of its neighbor
     * if it is at the same depth, leaving no gaps.
     * A fill ratio of 0.5 means that each point touches the edge of its neighbor if it has the same depth.
     * 
     * TODO(#6744): This applies only to 3D views!
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorPointFillRatio() {
        return DESCRIPTOR_POINT_FILL_RATIO;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DRAW_ORDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DepthImage:draw_order", "rerun.components.DrawOrder");

    /**
     * An optional floating point value that specifies the 2D drawing order, used only if the depth image is shown as a 2D image.
     * 
     * Objects with higher values are drawn on top of those with lower values.
     * Defaults to {@code -20.0}.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorDrawOrder() {
        return DESCRIPTOR_DRAW_ORDER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MAGNIFICATION_FILTER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DepthImage:magnification_filter", "rerun.components.MagnificationFilter");

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
        return java.util.List.of(descriptorBuffer(), descriptorFormat());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorMeter(), descriptorColormap(), descriptorDepthRange(), descriptorPointFillRatio(), descriptorDrawOrder(), descriptorMagnificationFilter());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.ImageBuffer.batch(descriptorBuffer(), allocator, buffer));
        batches.add(org.teamvan.moneyshift.components.ImageFormat.batch(descriptorFormat(), allocator, format));
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
