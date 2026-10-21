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
 * {@link org.teamvan.moneyshift.archetypes.Image}, with slices stacked along the local Z axis.
 * The tensor element at {@code [k, j, i]} is thus the voxel with grid index {@code [i, j, k]}.
 * 
 * Voxels are positioned exactly like those of {@link org.teamvan.moneyshift.archetypes.VoxelGridMap}:
 * the minimum corner of the voxel with {@code [0, 0, 0]} index is located at the origin of the entity's
 * coordinate frame and can have an additional offset from there through the optional translation
 * and rotation fields, and a voxel center is at {@code (index + 0.5) * voxel_size} in local grid
 * coordinates (i.e. relative to the minimum corner).
 * This archetype and a {@link org.teamvan.moneyshift.archetypes.VoxelGridMap} with the same {@code voxel_size} and pose
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
     * Together with {@link org.teamvan.moneyshift.components.RotationQuat}, this defines the pose of the volume
     * relative to the entity's coordinate frame.
     * 
     * If not set, the minimum corner is placed at the origin of the entity's coordinate frame.
     */
    org.teamvan.moneyshift.components.Translation3D translation,
    /**
     * Rotation of the volume via quaternion.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D}, this defines the pose of the volume
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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VALUES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Volume3D:values", "rerun.components.TensorData");

    /**
     * The scalar value of each voxel, as a 3D tensor with dimensions ordered {@code [z, y, x]}.
     * 
     * Currently only {@code f16} are supported.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorValues() {
        return DESCRIPTOR_VALUES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VOXEL_SIZE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Volume3D:voxel_size", "rerun.components.VoxelSize");

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
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVoxelSize() {
        return DESCRIPTOR_VOXEL_SIZE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TRANSLATION = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Volume3D:translation", "rerun.components.Translation3D");

    /**
     * Translation of the minimum corner of voxel {@code [0, 0, 0]}.
     * 
     * Together with {@link org.teamvan.moneyshift.components.RotationQuat}, this defines the pose of the volume
     * relative to the entity's coordinate frame.
     * 
     * If not set, the minimum corner is placed at the origin of the entity's coordinate frame.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTranslation() {
        return DESCRIPTOR_TRANSLATION;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_QUATERNION = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Volume3D:quaternion", "rerun.components.RotationQuat");

    /**
     * Rotation of the volume via quaternion.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D}, this defines the pose of the volume
     * relative to the entity's coordinate frame.
     * The rotation is around the minimum corner of voxel {@code [0, 0, 0]}, and is applied before the
     * translation.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorQuaternion() {
        return DESCRIPTOR_QUATERNION;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VALUE_RANGE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Volume3D:value_range", "rerun.components.ValueRange");

    /**
     * How to map {@code values} to opacity and color.
     * 
     * If not specified, the range is estimated from the data.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorValueRange() {
        return DESCRIPTOR_VALUE_RANGE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORMAP = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Volume3D:colormap", "rerun.components.Colormap");

    /**
     * Colormap applied to the values after mapping them through {@code value_range}.
     * 
     * Defaults to Turbo.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColormap() {
        return DESCRIPTOR_COLORMAP;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_OPACITY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Volume3D:opacity", "rerun.components.Opacity");

    /**
     * Overall opacity of the volume.
     * 
     * The opacity of a single voxel is its value (normalized through {@code value_range}) scaled by
     * this, i.e. a linear ramp: low values are transparent, high values are opaque.
     * Lowering this makes the interior of the volume visible.
     * 
     * Defaults to 1.0.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorOpacity() {
        return DESCRIPTOR_OPACITY;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorValues());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorVoxelSize());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorTranslation(), descriptorQuaternion(), descriptorValueRange(), descriptorColormap(), descriptorOpacity());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.TensorData.batch(descriptorValues(), allocator, values));
        if (voxelSize != null) {
            batches.add(org.teamvan.moneyshift.components.VoxelSize.batch(descriptorVoxelSize(), allocator, voxelSize));
        }
        if (translation != null) {
            batches.add(org.teamvan.moneyshift.components.Translation3D.batch(descriptorTranslation(), allocator, translation));
        }
        if (quaternion != null) {
            batches.add(org.teamvan.moneyshift.components.RotationQuat.batch(descriptorQuaternion(), allocator, quaternion));
        }
        if (valueRange != null) {
            batches.add(org.teamvan.moneyshift.components.ValueRange.batch(descriptorValueRange(), allocator, valueRange));
        }
        if (colormap != null) {
            batches.add(org.teamvan.moneyshift.components.Colormap.batch(descriptorColormap(), allocator, colormap));
        }
        if (opacity != null) {
            batches.add(org.teamvan.moneyshift.components.Opacity.batch(descriptorOpacity(), allocator, opacity));
        }
        return batches;
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
