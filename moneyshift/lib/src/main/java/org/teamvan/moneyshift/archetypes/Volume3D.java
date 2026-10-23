// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A dense 3D scalar field, rendered by ray marching.
 * 
 * This archetype is intended for volumetric scans and simulations, e.g. CT/MRI scans,
 * signed distance fields, or occupancy probabilities sampled on a regular grid.
 * 
 * The values are a 3D tensor with dimensions ordered {@code [z, y, x]}, i.e. the last dimension varies
 * fastest and runs along the local X axis. This matches the row-major layout of
 * {@link org.teamvan.moneyshift.archetypes.Image rerun::archetypes::Image}, with slices stacked along the local Z axis.
 * The tensor element at {@code [k, j, i]} is thus the voxel with grid index {@code [i, j, k]}.
 * 
 * Voxels are positioned exactly like those of {@link org.teamvan.moneyshift.archetypes.VoxelGridMap rerun::archetypes::VoxelGridMap}:
 * the minimum corner of the voxel with {@code [0, 0, 0]} index is located at the origin of the entity's
 * coordinate frame and can have an additional offset from there through the optional translation
 * and rotation fields, and a voxel center is at {@code (index + 0.5) * voxel_size} in local grid
 * coordinates (i.e. relative to the minimum corner).
 * This archetype and a {@link org.teamvan.moneyshift.archetypes.VoxelGridMap rerun::archetypes::VoxelGridMap} with the same {@code voxel_size} and pose
 * therefore agree voxel for voxel, the dense volume covering indices {@code [0, 0, 0]} up to
 * {@code [width - 1, height - 1, depth - 1]}.
 * 
 */
public record Volume3D(
    /**
     * The scalar value of each voxel, as a 3D tensor with dimensions ordered {@code [z, y, x]}.
     * 
     * Currently only {@code f16} are supported.
     */
    org.teamvan.moneyshift.components.TensorData values,
    /**
     * The scene-unit dimensions of a single voxel cell.
     * 
     * This defines the voxel size along the local grid X/Y/Z axes, and thus the total extent of the
     * volume: {@code [width, height, depth] * voxel_size}.
     * Anisotropic spacing (as is common for medical scans) is expressed here.
     * Each dimension must be finite and positive.
     * 
     * Defaults to {@code [1.0, 1.0, 1.0]}.
     */
    org.teamvan.moneyshift.components.VoxelSize voxelSize,
    /**
     * Translation of the minimum corner of voxel {@code [0, 0, 0]}.
     * 
     * Together with {@link org.teamvan.moneyshift.components.RotationQuat rerun::components::RotationQuat}, this defines the pose of the volume
     * relative to the entity's coordinate frame.
     * 
     * If not set, the minimum corner is placed at the origin of the entity's coordinate frame.
     */
    org.teamvan.moneyshift.components.Translation3D translation,
    /**
     * Rotation of the volume via quaternion.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D rerun::components::Translation3D}, this defines the pose of the volume
     * relative to the entity's coordinate frame.
     * The rotation is around the minimum corner of voxel {@code [0, 0, 0]}, and is applied before the
     * translation.
     */
    org.teamvan.moneyshift.components.RotationQuat quaternion,
    /**
     * How to map {@code values} to opacity and color.
     * 
     * If not specified, the range is estimated from the data.
     */
    org.teamvan.moneyshift.components.ValueRange valueRange,
    /**
     * Colormap applied to the values after mapping them through {@code value_range}.
     * 
     * Defaults to Turbo.
     */
    org.teamvan.moneyshift.components.Colormap colormap,
    /**
     * Overall opacity of the volume.
     * 
     * The opacity of a single voxel is its value (normalized through {@code value_range}) scaled by
     * this, i.e. a linear ramp: low values are transparent, high values are opaque.
     * Lowering this makes the interior of the volume visible.
     * 
     * Defaults to 1.0.
     */
    org.teamvan.moneyshift.components.Opacity opacity
) implements org.teamvan.moneyshift.Archetype {
    public Volume3D {
        java.util.Objects.requireNonNull(values, "values");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Volume3D";
    public static final int NUM_COMPONENTS = 7;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("values").append('=').append(org.teamvan.moneyshift.components.TensorData.COMPONENT_TYPE);
        }
        if (voxelSize != null) {
            if (components.length() > 0) components.append(',');
            components.append("voxel_size").append('=').append(org.teamvan.moneyshift.components.VoxelSize.COMPONENT_TYPE);
        }
        if (translation != null) {
            if (components.length() > 0) components.append(',');
            components.append("translation").append('=').append(org.teamvan.moneyshift.components.Translation3D.COMPONENT_TYPE);
        }
        if (quaternion != null) {
            if (components.length() > 0) components.append(',');
            components.append("quaternion").append('=').append(org.teamvan.moneyshift.components.RotationQuat.COMPONENT_TYPE);
        }
        if (valueRange != null) {
            if (components.length() > 0) components.append(',');
            components.append("value_range").append('=').append(org.teamvan.moneyshift.components.ValueRange.COMPONENT_TYPE);
        }
        if (colormap != null) {
            if (components.length() > 0) components.append(',');
            components.append("colormap").append('=').append(org.teamvan.moneyshift.components.Colormap.COMPONENT_TYPE);
        }
        if (opacity != null) {
            if (components.length() > 0) components.append(',');
            components.append("opacity").append('=').append(org.teamvan.moneyshift.components.Opacity.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (true) {
            org.teamvan.moneyshift.components.TensorData.writeColumnsStatic(w, "values", java.util.List.of(values));
        }
        if (voxelSize != null) {
            org.teamvan.moneyshift.components.VoxelSize.writeColumnsStatic(w, "voxel_size", java.util.List.of(voxelSize));
        }
        if (translation != null) {
            org.teamvan.moneyshift.components.Translation3D.writeColumnsStatic(w, "translation", java.util.List.of(translation));
        }
        if (quaternion != null) {
            org.teamvan.moneyshift.components.RotationQuat.writeColumnsStatic(w, "quaternion", java.util.List.of(quaternion));
        }
        if (valueRange != null) {
            org.teamvan.moneyshift.components.ValueRange.writeColumnsStatic(w, "value_range", java.util.List.of(valueRange));
        }
        if (colormap != null) {
            org.teamvan.moneyshift.components.Colormap.writeColumnsStatic(w, "colormap", java.util.List.of(colormap));
        }
        if (opacity != null) {
            org.teamvan.moneyshift.components.Opacity.writeColumnsStatic(w, "opacity", java.util.List.of(opacity));
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.TensorData values;
        private org.teamvan.moneyshift.components.VoxelSize voxelSize;
        private org.teamvan.moneyshift.components.Translation3D translation;
        private org.teamvan.moneyshift.components.RotationQuat quaternion;
        private org.teamvan.moneyshift.components.ValueRange valueRange;
        private org.teamvan.moneyshift.components.Colormap colormap;
        private org.teamvan.moneyshift.components.Opacity opacity;
        private Builder() {}

        public Builder withValues(org.teamvan.moneyshift.components.TensorData value) {
            this.values = value;
            return this;
        }

        public Builder withVoxelSize(org.teamvan.moneyshift.components.VoxelSize value) {
            this.voxelSize = value;
            return this;
        }

        public Builder withTranslation(org.teamvan.moneyshift.components.Translation3D value) {
            this.translation = value;
            return this;
        }

        public Builder withQuaternion(org.teamvan.moneyshift.components.RotationQuat value) {
            this.quaternion = value;
            return this;
        }

        public Builder withValueRange(org.teamvan.moneyshift.components.ValueRange value) {
            this.valueRange = value;
            return this;
        }

        public Builder withColormap(org.teamvan.moneyshift.components.Colormap value) {
            this.colormap = value;
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

        public Volume3D build() {
            return new Volume3D(values, voxelSize, translation, quaternion, valueRange, colormap, opacity);
        }
    }

    public static Volume3D of(
        org.teamvan.moneyshift.components.TensorData values
    ) {
        return new Volume3D(values, null, null, null, null, null, null);
    }
}
