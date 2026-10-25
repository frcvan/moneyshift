// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A 2D grid map stored as raster data in an image buffer, with a cell size in scene units and pose.
 * 
 * This archetype is intended for robotics applications like occupancy maps or navigation costmaps,
 * but can also be useful for other use cases where an image buffer needs to be placed in a scene.
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
     * Together with {@link org.teamvan.moneyshift.components.RotationAxisAngle rerun::components::RotationAxisAngle} or {@link org.teamvan.moneyshift.components.RotationQuat rerun::components::RotationQuat}, this defines the pose of the
     * lower-left image corner relative to the map's parent coordinate frame.
     * 
     * If not set, the lower-left image corner is placed at origin of the map's parent coordinate frame.
     */
    org.teamvan.moneyshift.components.Translation3D translation,
    /**
     * Rotation of the lower-left corner of the grid map in space via axis + angle.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D rerun::components::Translation3D}, this defines the pose of the
     * lower-left image corner relative to the map's parent coordinate frame.
     * 
     * Note: either this or {@link org.teamvan.moneyshift.components.RotationQuat rerun::components::RotationQuat} can be set to specify the grid map's rotation, but not both.
     * If both this and {@link org.teamvan.moneyshift.components.RotationQuat rerun::components::RotationQuat} are set, this is ignored in favor of the quaternion.
     */
    org.teamvan.moneyshift.components.RotationAxisAngle rotationAxisAngle,
    /**
     * Rotation of the lower-left corner of the grid map in space via quaternion.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D rerun::components::Translation3D}, this defines the pose of the
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
     * If not set, the grid map is shown using the underlying {@link org.teamvan.moneyshift.components.ImageFormat rerun::components::ImageFormat}
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
            components.append("data").append('=').append(org.teamvan.moneyshift.components.ImageBuffer.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("format").append('=').append(org.teamvan.moneyshift.components.ImageFormat.COMPONENT_TYPE);
        }
        if ((mask & 4L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("cell_size").append('=').append(org.teamvan.moneyshift.components.CellSize.COMPONENT_TYPE);
        }
        if ((mask & 8L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("translation").append('=').append(org.teamvan.moneyshift.components.Translation3D.COMPONENT_TYPE);
        }
        if ((mask & 16L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("rotation_axis_angle").append('=').append(org.teamvan.moneyshift.components.RotationAxisAngle.COMPONENT_TYPE);
        }
        if ((mask & 32L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("quaternion").append('=').append(org.teamvan.moneyshift.components.RotationQuat.COMPONENT_TYPE);
        }
        if ((mask & 64L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("opacity").append('=').append(org.teamvan.moneyshift.components.Opacity.COMPONENT_TYPE);
        }
        if ((mask & 128L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("draw_order").append('=').append(org.teamvan.moneyshift.components.DrawOrder.COMPONENT_TYPE);
        }
        if ((mask & 256L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("colormap").append('=').append(org.teamvan.moneyshift.components.Colormap.COMPONENT_TYPE);
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
        if (cellSize != null) mask |= 4L;
        if (translation != null) mask |= 8L;
        if (rotationAxisAngle != null) mask |= 16L;
        if (quaternion != null) mask |= 32L;
        if (opacity != null) mask |= 64L;
        if (drawOrder != null) mask |= 128L;
        if (colormap != null) mask |= 256L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (true) {
            org.teamvan.moneyshift.components.ImageBuffer.writeColumnsStatic(w, "data", java.util.List.of(data));
        }
        if (true) {
            org.teamvan.moneyshift.components.ImageFormat.writeColumnsStatic(w, "format", java.util.List.of(format));
        }
        if (cellSize != null) {
            org.teamvan.moneyshift.components.CellSize.writeColumnsStatic(w, "cell_size", java.util.List.of(cellSize));
        }
        if (translation != null) {
            org.teamvan.moneyshift.components.Translation3D.writeColumnsStatic(w, "translation", java.util.List.of(translation));
        }
        if (rotationAxisAngle != null) {
            org.teamvan.moneyshift.components.RotationAxisAngle.writeColumnsStatic(w, "rotation_axis_angle", java.util.List.of(rotationAxisAngle));
        }
        if (quaternion != null) {
            org.teamvan.moneyshift.components.RotationQuat.writeColumnsStatic(w, "quaternion", java.util.List.of(quaternion));
        }
        if (opacity != null) {
            org.teamvan.moneyshift.components.Opacity.writeColumnsStatic(w, "opacity", java.util.List.of(opacity));
        }
        if (drawOrder != null) {
            org.teamvan.moneyshift.components.DrawOrder.writeColumnsStatic(w, "draw_order", java.util.List.of(drawOrder));
        }
        if (colormap != null) {
            org.teamvan.moneyshift.components.Colormap.writeColumnsStatic(w, "colormap", java.util.List.of(colormap));
        }
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
