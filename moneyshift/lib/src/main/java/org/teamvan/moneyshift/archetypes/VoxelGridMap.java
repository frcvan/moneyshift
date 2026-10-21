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
     * Together with {@link org.teamvan.moneyshift.components.RotationAxisAngle} or {@link org.teamvan.moneyshift.components.RotationQuat}, this defines the pose of the
     * grid relative to the map's parent coordinate frame.
     * 
     * If not set, the minimum corner is placed at the origin of the map's parent coordinate frame.
     */
    org.teamvan.moneyshift.components.Translation3D translation,
    /**
     * Rotation of the grid via axis + angle.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D}, this defines the pose of the grid relative to the
     * map's parent coordinate frame.
     * 
     * Note: either this or {@link org.teamvan.moneyshift.components.RotationQuat} can be set to specify the grid's rotation, but not both.
     * If both this and {@link org.teamvan.moneyshift.components.RotationQuat} are set, this is ignored in favor of the quaternion.
     */
    org.teamvan.moneyshift.components.RotationAxisAngle rotationAxisAngle,
    /**
     * Rotation of the grid via quaternion.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D}, this defines the pose of the grid relative to the
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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VOXEL_INDICES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VoxelGridMap:voxel_indices", "rerun.components.VoxelIndex");

    /**
     * Indices of the voxels within the grid volume.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVoxelIndices() {
        return DESCRIPTOR_VOXEL_INDICES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VOXEL_SIZE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VoxelGridMap:voxel_size", "rerun.components.VoxelSize");

    /**
     * The scene-unit dimensions of a single voxel cell.
     * 
     * This defines the voxel size along the local grid X/Y/Z axes.
     * Each dimension must be finite and positive.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVoxelSize() {
        return DESCRIPTOR_VOXEL_SIZE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VALUES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VoxelGridMap:values", "rerun.components.VoxelValue");

    /**
     * Optional scalar occupancy or value data for each voxel.
     * 
     * If explicit colors are not provided, values are mapped through {@code colormap} and {@code value_range}.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorValues() {
        return DESCRIPTOR_VALUES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VoxelGridMap:colors", "rerun.components.Color");

    /**
     * Optional colors for each voxel.
     * 
     * If set, these colors take precedence over color-mapped scalar values.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColors() {
        return DESCRIPTOR_COLORS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TRANSLATION = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VoxelGridMap:translation", "rerun.components.Translation3D");

    /**
     * Translation of the minimum corner of voxel {@code [0, 0, 0]}.
     * 
     * Together with {@link org.teamvan.moneyshift.components.RotationAxisAngle} or {@link org.teamvan.moneyshift.components.RotationQuat}, this defines the pose of the
     * grid relative to the map's parent coordinate frame.
     * 
     * If not set, the minimum corner is placed at the origin of the map's parent coordinate frame.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTranslation() {
        return DESCRIPTOR_TRANSLATION;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ROTATION_AXIS_ANGLE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VoxelGridMap:rotation_axis_angle", "rerun.components.RotationAxisAngle");

    /**
     * Rotation of the grid via axis + angle.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D}, this defines the pose of the grid relative to the
     * map's parent coordinate frame.
     * 
     * Note: either this or {@link org.teamvan.moneyshift.components.RotationQuat} can be set to specify the grid's rotation, but not both.
     * If both this and {@link org.teamvan.moneyshift.components.RotationQuat} are set, this is ignored in favor of the quaternion.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRotationAxisAngle() {
        return DESCRIPTOR_ROTATION_AXIS_ANGLE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_QUATERNION = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VoxelGridMap:quaternion", "rerun.components.RotationQuat");

    /**
     * Rotation of the grid via quaternion.
     * 
     * Together with {@link org.teamvan.moneyshift.components.Translation3D}, this defines the pose of the grid relative to the
     * map's parent coordinate frame.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorQuaternion() {
        return DESCRIPTOR_QUATERNION;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_OPACITY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VoxelGridMap:opacity", "rerun.components.Opacity");

    /**
     * Opacity of the voxels after color or colormap application.
     * 
     * Defaults to 1.0 (fully opaque).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorOpacity() {
        return DESCRIPTOR_OPACITY;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VALUE_RANGE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VoxelGridMap:value_range", "rerun.components.ValueRange");

    /**
     * Scalar value range for color-mapping.
     * 
     * Defaults to {@code [0.0, 1.0]}.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorValueRange() {
        return DESCRIPTOR_VALUE_RANGE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORMAP = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VoxelGridMap:colormap", "rerun.components.Colormap");

    /**
     * Colormap to use when {@code values} are present and explicit {@code colors} are not provided.
     * 
     * Defaults to Turbo.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColormap() {
        return DESCRIPTOR_COLORMAP;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorVoxelIndices(), descriptorVoxelSize());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorValues(), descriptorColors(), descriptorTranslation(), descriptorRotationAxisAngle(), descriptorQuaternion(), descriptorOpacity(), descriptorValueRange(), descriptorColormap());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.VoxelIndex.batch(descriptorVoxelIndices(), allocator, voxelIndices));
        batches.add(org.teamvan.moneyshift.components.VoxelSize.batch(descriptorVoxelSize(), allocator, voxelSize));
        if (values != null) {
            batches.add(org.teamvan.moneyshift.components.VoxelValue.batch(descriptorValues(), allocator, values));
        }
        if (colors != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColors(), allocator, colors));
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
        if (valueRange != null) {
            batches.add(org.teamvan.moneyshift.components.ValueRange.batch(descriptorValueRange(), allocator, valueRange));
        }
        if (colormap != null) {
            batches.add(org.teamvan.moneyshift.components.Colormap.batch(descriptorColormap(), allocator, colormap));
        }
        return batches;
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

        public Builder withValues(org.teamvan.moneyshift.components.VoxelValue... value) {
            return withValues(java.util.List.of(value));
        }

        public Builder withColors(java.util.List<org.teamvan.moneyshift.components.Color> value) {
            this.colors = java.util.List.copyOf(value);
            return this;
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
