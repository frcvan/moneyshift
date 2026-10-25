// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A sparse 3D voxel grid map with grid indices and voxel dimensions.
 * 
 * This archetype is intended for 3D occupancy maps and other volumetric data
 * represented as a sparse grid of voxels with scene-unit dimensions along the local X/Y/Z axes.
 * 
 * The minimum corner of the voxel with {@code [0, 0, 0]} index is located at the origin of the entity's coordinate frame
 * and can have an additional offset from there through the optional translation and rotation fields.
 * 
 * A voxel center is at {@code (index + 0.5) * voxel_size} in local grid coordinates (i.e. relative to the minimum corner).
 * 
 */
public record VoxelGridMap(
    /**
     * Indices of the voxels within the grid volume.
     */
    java.util.List<org.teamvan.moneyshift.components.VoxelIndex> voxelIndices,
    /**
     * The scene-unit dimensions of a single voxel cell.
     * 
     * This defines the voxel size along the local grid X/Y/Z axes.
     * Each dimension must be finite and positive.
     */
    org.teamvan.moneyshift.components.VoxelSize voxelSize,
    /**
     * Optional scalar occupancy or value data for each voxel.
     * 
     * If explicit colors are not provided, values are mapped through {@code colormap} and {@code value_range}.
     */
    java.util.List<org.teamvan.moneyshift.components.VoxelValue> values,
    /**
     * Optional colors for each voxel.
     * 
     * If set, these colors take precedence over color-mapped scalar values.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * Translation of the minimum corner of voxel {@code [0, 0, 0]}.
     * 
     * Together with {@link org.teamvan.moneyshift.components.RotationAxisAngle rerun::components::RotationAxisAngle} or {@link org.teamvan.moneyshift.components.RotationQuat rerun::components::RotationQuat}, this defines the pose of the
     * grid relative to the map's parent coordinate frame.
     * 
     * If not set, the minimum corner is placed at the origin of the map's parent coordinate frame.
     */
    org.teamvan.moneyshift.components.Translation3D translation,
    /**
     * Rotation of the grid via axis + angle.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D rerun::components::Translation3D}, this defines the pose of the grid relative to the
     * map's parent coordinate frame.
     * 
     * Note: either this or {@link org.teamvan.moneyshift.components.RotationQuat rerun::components::RotationQuat} can be set to specify the grid's rotation, but not both.
     * If both this and {@link org.teamvan.moneyshift.components.RotationQuat rerun::components::RotationQuat} are set, this is ignored in favor of the quaternion.
     */
    org.teamvan.moneyshift.components.RotationAxisAngle rotationAxisAngle,
    /**
     * Rotation of the grid via quaternion.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D rerun::components::Translation3D}, this defines the pose of the grid relative to the
     * map's parent coordinate frame.
     */
    org.teamvan.moneyshift.components.RotationQuat quaternion,
    /**
     * Opacity of the voxels after color or colormap application.
     * 
     * Defaults to 1.0 (fully opaque).
     */
    org.teamvan.moneyshift.components.Opacity opacity,
    /**
     * Scalar value range for color-mapping.
     * 
     * Defaults to {@code [0.0, 1.0]}.
     */
    org.teamvan.moneyshift.components.ValueRange valueRange,
    /**
     * Colormap to use when {@code values} are present and explicit {@code colors} are not provided.
     * 
     * Defaults to Turbo.
     */
    org.teamvan.moneyshift.components.Colormap colormap
) implements org.teamvan.moneyshift.Archetype {
    public VoxelGridMap {
        java.util.Objects.requireNonNull(voxelIndices, "voxelIndices");
        voxelIndices = java.util.List.copyOf(voxelIndices);
        java.util.Objects.requireNonNull(voxelSize, "voxelSize");
        if (values != null) { values = java.util.List.copyOf(values); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.VoxelGridMap";
    public static final int NUM_COMPONENTS = 10;

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
            components.append("voxel_indices").append('=').append(org.teamvan.moneyshift.components.VoxelIndex.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("voxel_size").append('=').append(org.teamvan.moneyshift.components.VoxelSize.COMPONENT_TYPE);
        }
        if ((mask & 4L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("values").append('=').append(org.teamvan.moneyshift.components.VoxelValue.COMPONENT_TYPE);
        }
        if ((mask & 8L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if ((mask & 16L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("translation").append('=').append(org.teamvan.moneyshift.components.Translation3D.COMPONENT_TYPE);
        }
        if ((mask & 32L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("rotation_axis_angle").append('=').append(org.teamvan.moneyshift.components.RotationAxisAngle.COMPONENT_TYPE);
        }
        if ((mask & 64L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("quaternion").append('=').append(org.teamvan.moneyshift.components.RotationQuat.COMPONENT_TYPE);
        }
        if ((mask & 128L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("opacity").append('=').append(org.teamvan.moneyshift.components.Opacity.COMPONENT_TYPE);
        }
        if ((mask & 256L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("value_range").append('=').append(org.teamvan.moneyshift.components.ValueRange.COMPONENT_TYPE);
        }
        if ((mask & 512L) != 0) {
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
        if (!voxelIndices.isEmpty()) mask |= 1L;
        if (true) mask |= 2L;
        if (values != null && !values.isEmpty()) mask |= 4L;
        if (colors != null && !colors.isEmpty()) mask |= 8L;
        if (translation != null) mask |= 16L;
        if (rotationAxisAngle != null) mask |= 32L;
        if (quaternion != null) mask |= 64L;
        if (opacity != null) mask |= 128L;
        if (valueRange != null) mask |= 256L;
        if (colormap != null) mask |= 512L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (!voxelIndices.isEmpty()) {
            org.teamvan.moneyshift.components.VoxelIndex.writeColumnsStatic(w, "voxel_indices", voxelIndices);
        }
        if (true) {
            org.teamvan.moneyshift.components.VoxelSize.writeColumnsStatic(w, "voxel_size", java.util.List.of(voxelSize));
        }
        if (values != null && !values.isEmpty()) {
            org.teamvan.moneyshift.components.VoxelValue.writeColumnsStatic(w, "values", values);
        }
        if (colors != null && !colors.isEmpty()) {
            org.teamvan.moneyshift.components.Color.writeColumnsStatic(w, "colors", colors);
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
        if (valueRange != null) {
            org.teamvan.moneyshift.components.ValueRange.writeColumnsStatic(w, "value_range", java.util.List.of(valueRange));
        }
        if (colormap != null) {
            org.teamvan.moneyshift.components.Colormap.writeColumnsStatic(w, "colormap", java.util.List.of(colormap));
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.VoxelIndex> voxelIndices;
        private org.teamvan.moneyshift.components.VoxelSize voxelSize;
        private java.util.List<org.teamvan.moneyshift.components.VoxelValue> values;
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private org.teamvan.moneyshift.components.Translation3D translation;
        private org.teamvan.moneyshift.components.RotationAxisAngle rotationAxisAngle;
        private org.teamvan.moneyshift.components.RotationQuat quaternion;
        private org.teamvan.moneyshift.components.Opacity opacity;
        private org.teamvan.moneyshift.components.ValueRange valueRange;
        private org.teamvan.moneyshift.components.Colormap colormap;
        private Builder() {}

        public Builder withVoxelIndices(java.util.List<org.teamvan.moneyshift.components.VoxelIndex> value) {
            this.voxelIndices = java.util.List.copyOf(value);
            return this;
        }

        public Builder withVoxelIndices(org.teamvan.moneyshift.components.VoxelIndex... value) {
            return withVoxelIndices(java.util.List.of(value));
        }

        public Builder withVoxelSize(org.teamvan.moneyshift.components.VoxelSize value) {
            this.voxelSize = value;
            return this;
        }

        public Builder withValues(java.util.List<org.teamvan.moneyshift.components.VoxelValue> value) {
            this.values = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withValues(float... value) {
            java.util.List<org.teamvan.moneyshift.components.VoxelValue> values = new java.util.ArrayList<>(value.length);
            for (float v : value) {
                values.add(new org.teamvan.moneyshift.components.VoxelValue(v));
            }
            return withValues(values);
        }

        public Builder withValues(org.teamvan.moneyshift.components.VoxelValue... value) {
            return withValues(java.util.List.of(value));
        }

        public Builder withColors(java.util.List<org.teamvan.moneyshift.components.Color> value) {
            this.colors = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withColors(long... value) {
            java.util.List<org.teamvan.moneyshift.components.Color> values = new java.util.ArrayList<>(value.length);
            for (long v : value) {
                values.add(new org.teamvan.moneyshift.components.Color(v));
            }
            return withColors(values);
        }

        public Builder withColors(org.teamvan.moneyshift.components.Color... value) {
            return withColors(java.util.List.of(value));
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

        public Builder withValueRange(org.teamvan.moneyshift.components.ValueRange value) {
            this.valueRange = value;
            return this;
        }

        public Builder withColormap(org.teamvan.moneyshift.components.Colormap value) {
            this.colormap = value;
            return this;
        }

        public VoxelGridMap build() {
            return new VoxelGridMap(voxelIndices, voxelSize, values, colors, translation, rotationAxisAngle, quaternion, opacity, valueRange, colormap);
        }
    }

    public static VoxelGridMap of(
        java.util.List<org.teamvan.moneyshift.components.VoxelIndex> voxelIndices,
        org.teamvan.moneyshift.components.VoxelSize voxelSize
    ) {
        return new VoxelGridMap(voxelIndices, voxelSize, null, null, null, null, null, null, null, null);
    }
}
