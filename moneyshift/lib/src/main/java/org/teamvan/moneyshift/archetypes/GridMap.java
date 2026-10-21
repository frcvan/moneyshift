// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A 2D grid map stored as raster data in an image buffer, with a cell size in scene units and pose.
 * 
 * This archetype is intended for robotics applications like occupancy maps or navigation costmaps.
 * 
 */
public record GridMap(
    /**
     * The raw grid data.
     */
    org.teamvan.moneyshift.components.ImageBuffer data,
    /**
     * The format of the grid's image data.
     */
    org.teamvan.moneyshift.components.ImageFormat format,
    /**
     * The scene unit size of a single grid cell (e.g. m / pixel).
     * 
     * Defaults to 0.01 scene units per pixel.
     */
    org.teamvan.moneyshift.components.CellSize cellSize,
    /**
     * Translation of the lower-left corner of the grid map in space.
     * 
     * Together with {@link org.teamvan.moneyshift.components.RotationAxisAngle} or {@link org.teamvan.moneyshift.components.RotationQuat}, this defines the pose of the
     * lower-left image corner relative to the map's parent coordinate frame.
     * 
     * If not set, the lower-left image corner is placed at origin of the map's parent coordinate frame.
     */
    org.teamvan.moneyshift.components.Translation3D translation,
    /**
     * Rotation of the lower-left corner of the grid map in space via axis + angle.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D}, this defines the pose of the
     * lower-left image corner relative to the map's parent coordinate frame.
     * 
     * Note: either this or {@link org.teamvan.moneyshift.components.RotationQuat} can be set to specify the grid map's rotation, but not both.
     * If both this and {@link org.teamvan.moneyshift.components.RotationQuat} are set, this is ignored in favor of the quaternion.
     */
    org.teamvan.moneyshift.components.RotationAxisAngle rotationAxisAngle,
    /**
     * Rotation of the lower-left corner of the grid map in space via quaternion.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D}, this defines the pose of the
     * lower-left image corner relative to the map's parent coordinate frame.
     */
    org.teamvan.moneyshift.components.RotationQuat quaternion,
    /**
     * Opacity of the grid map texture after all image decoding and colormap application.
     * 
     * Defaults to 1.0 (fully opaque).
     */
    org.teamvan.moneyshift.components.Opacity opacity,
    /**
     * Optional draw order for layering multiple grid maps that overlap in space.
     * 
     * Higher values are drawn on top of lower values.
     */
    org.teamvan.moneyshift.components.DrawOrder drawOrder,
    /**
     * Colormap to use for rendering single-channel grid maps.
     * 
     * If not set, the grid map is shown using the underlying {@link org.teamvan.moneyshift.components.ImageFormat}
     * interpretation.
     */
    org.teamvan.moneyshift.components.Colormap colormap
) implements org.teamvan.moneyshift.Archetype {
    public GridMap {
        java.util.Objects.requireNonNull(data, "data");
        java.util.Objects.requireNonNull(format, "format");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.GridMap";
    public static final int NUM_COMPONENTS = 9;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DATA = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GridMap:data", "rerun.components.ImageBuffer");

    /**
     * The raw grid data.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorData() {
        return DESCRIPTOR_DATA;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FORMAT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GridMap:format", "rerun.components.ImageFormat");

    /**
     * The format of the grid's image data.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFormat() {
        return DESCRIPTOR_FORMAT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CELL_SIZE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GridMap:cell_size", "rerun.components.CellSize");

    /**
     * The scene unit size of a single grid cell (e.g. m / pixel).
     * 
     * Defaults to 0.01 scene units per pixel.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorCellSize() {
        return DESCRIPTOR_CELL_SIZE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TRANSLATION = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GridMap:translation", "rerun.components.Translation3D");

    /**
     * Translation of the lower-left corner of the grid map in space.
     * 
     * Together with {@link org.teamvan.moneyshift.components.RotationAxisAngle} or {@link org.teamvan.moneyshift.components.RotationQuat}, this defines the pose of the
     * lower-left image corner relative to the map's parent coordinate frame.
     * 
     * If not set, the lower-left image corner is placed at origin of the map's parent coordinate frame.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTranslation() {
        return DESCRIPTOR_TRANSLATION;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ROTATION_AXIS_ANGLE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GridMap:rotation_axis_angle", "rerun.components.RotationAxisAngle");

    /**
     * Rotation of the lower-left corner of the grid map in space via axis + angle.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D}, this defines the pose of the
     * lower-left image corner relative to the map's parent coordinate frame.
     * 
     * Note: either this or {@link org.teamvan.moneyshift.components.RotationQuat} can be set to specify the grid map's rotation, but not both.
     * If both this and {@link org.teamvan.moneyshift.components.RotationQuat} are set, this is ignored in favor of the quaternion.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRotationAxisAngle() {
        return DESCRIPTOR_ROTATION_AXIS_ANGLE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_QUATERNION = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GridMap:quaternion", "rerun.components.RotationQuat");

    /**
     * Rotation of the lower-left corner of the grid map in space via quaternion.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D}, this defines the pose of the
     * lower-left image corner relative to the map's parent coordinate frame.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorQuaternion() {
        return DESCRIPTOR_QUATERNION;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_OPACITY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GridMap:opacity", "rerun.components.Opacity");

    /**
     * Opacity of the grid map texture after all image decoding and colormap application.
     * 
     * Defaults to 1.0 (fully opaque).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorOpacity() {
        return DESCRIPTOR_OPACITY;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DRAW_ORDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GridMap:draw_order", "rerun.components.DrawOrder");

    /**
     * Optional draw order for layering multiple grid maps that overlap in space.
     * 
     * Higher values are drawn on top of lower values.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorDrawOrder() {
        return DESCRIPTOR_DRAW_ORDER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORMAP = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GridMap:colormap", "rerun.components.Colormap");

    /**
     * Colormap to use for rendering single-channel grid maps.
     * 
     * If not set, the grid map is shown using the underlying {@link org.teamvan.moneyshift.components.ImageFormat}
     * interpretation.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColormap() {
        return DESCRIPTOR_COLORMAP;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorData(), descriptorFormat());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorCellSize(), descriptorTranslation(), descriptorRotationAxisAngle(), descriptorQuaternion(), descriptorOpacity(), descriptorDrawOrder(), descriptorColormap());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.ImageBuffer.batch(descriptorData(), allocator, data));
        batches.add(org.teamvan.moneyshift.components.ImageFormat.batch(descriptorFormat(), allocator, format));
        if (cellSize != null) {
            batches.add(org.teamvan.moneyshift.components.CellSize.batch(descriptorCellSize(), allocator, cellSize));
        }
        if (translation != null) {
            batches.add(org.teamvan.moneyshift.components.Translation3D.batch(descriptorTranslation(), allocator, translation));
        }
        if (rotationAxisAngle != null) {
            batches.add(org.teamvan.moneyshift.components.RotationAxisAngle.batch(descriptorRotationAxisAngle(), allocator, rotationAxisAngle));
        }
        if (quaternion != null) {
            batches.add(org.teamvan.moneyshift.components.RotationQuat.batch(descriptorQuaternion(), allocator, quaternion));
        }
        if (opacity != null) {
            batches.add(org.teamvan.moneyshift.components.Opacity.batch(descriptorOpacity(), allocator, opacity));
        }
        if (drawOrder != null) {
            batches.add(org.teamvan.moneyshift.components.DrawOrder.batch(descriptorDrawOrder(), allocator, drawOrder));
        }
        if (colormap != null) {
            batches.add(org.teamvan.moneyshift.components.Colormap.batch(descriptorColormap(), allocator, colormap));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.ImageBuffer data;
        private org.teamvan.moneyshift.components.ImageFormat format;
        private org.teamvan.moneyshift.components.CellSize cellSize;
        private org.teamvan.moneyshift.components.Translation3D translation;
        private org.teamvan.moneyshift.components.RotationAxisAngle rotationAxisAngle;
        private org.teamvan.moneyshift.components.RotationQuat quaternion;
        private org.teamvan.moneyshift.components.Opacity opacity;
        private org.teamvan.moneyshift.components.DrawOrder drawOrder;
        private org.teamvan.moneyshift.components.Colormap colormap;
        private Builder() {}

        public Builder withData(org.teamvan.moneyshift.components.ImageBuffer value) {
            this.data = value;
            return this;
        }

        public Builder withFormat(org.teamvan.moneyshift.components.ImageFormat value) {
            this.format = value;
            return this;
        }

        public Builder withCellSize(org.teamvan.moneyshift.components.CellSize value) {
            this.cellSize = value;
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withCellSize(float value) {
            return withCellSize(new org.teamvan.moneyshift.components.CellSize(value));
        }

        public Builder withTranslation(org.teamvan.moneyshift.components.Translation3D value) {
            this.translation = value;
            return this;
        }

        public Builder withRotationAxisAngle(org.teamvan.moneyshift.components.RotationAxisAngle value) {
            this.rotationAxisAngle = value;
            return this;
        }

        public Builder withQuaternion(org.teamvan.moneyshift.components.RotationQuat value) {
            this.quaternion = value;
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

        public Builder withColormap(org.teamvan.moneyshift.components.Colormap value) {
            this.colormap = value;
            return this;
        }

        public GridMap build() {
            return new GridMap(data, format, cellSize, translation, rotationAxisAngle, quaternion, opacity, drawOrder, colormap);
        }
    }

    public static GridMap of(
        org.teamvan.moneyshift.components.ImageBuffer data,
        org.teamvan.moneyshift.components.ImageFormat format
    ) {
        return new GridMap(data, format, null, null, null, null, null, null, null);
    }
}
