// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * One or more transforms applied on the current entity's transform frame.
 * 
 * Unlike {@link org.teamvan.moneyshift.archetypes.Transform3D}, it is *not* propagated in the transform hierarchy.
 * If {@link org.teamvan.moneyshift.archetypes.CoordinateFrame} is specified, it acts relative to that coordinate frame,
 * otherwise it is relative to the entity's implicit coordinate frame.
 * 
 * Whenever you log this archetype, the state of the resulting overall pose is fully reset to the new archetype.
 * This means that if you first log a pose with only a translation, and then log one with only a rotation,
 * it will be resolved to a pose with only a rotation.
 * (This is unlike how we usually apply latest-at semantics on an archetype where we take the latest state of any component independently)
 * 
 * From the point of view of the entity's coordinate system,
 * all components are applied in the inverse order they are listed here.
 * E.g. if both a translation and a mat3x3 transform are present,
 * the 3x3 matrix is applied first, followed by the translation.
 * 
 * Currently, many visualizers support only a single instance transform per entity.
 * Check archetype documentations for details - if not otherwise specified, only the first instance transform is applied.
 * Some visualizers like the mesh visualizer used for {@link org.teamvan.moneyshift.archetypes.Mesh3D},
 * will draw an object for every pose, a behavior also known as "instancing".
 * 
 */
public record InstancePoses3D(
    /**
     * Translation vectors.
     */
    java.util.List<org.teamvan.moneyshift.components.Translation3D> translations,
    /**
     * Rotations via axis + angle.
     */
    java.util.List<org.teamvan.moneyshift.components.RotationAxisAngle> rotationAxisAngles,
    /**
     * Rotations via quaternion.
     */
    java.util.List<org.teamvan.moneyshift.components.RotationQuat> quaternions,
    /**
     * Scaling factors.
     */
    java.util.List<org.teamvan.moneyshift.components.Scale3D> scales,
    /**
     * 3x3 transformation matrices.
     */
    java.util.List<org.teamvan.moneyshift.components.TransformMat3x3> mat3x3
) implements org.teamvan.moneyshift.Archetype {
    public InstancePoses3D {
        if (translations != null) { translations = java.util.List.copyOf(translations); }
        if (rotationAxisAngles != null) { rotationAxisAngles = java.util.List.copyOf(rotationAxisAngles); }
        if (quaternions != null) { quaternions = java.util.List.copyOf(quaternions); }
        if (scales != null) { scales = java.util.List.copyOf(scales); }
        if (mat3x3 != null) { mat3x3 = java.util.List.copyOf(mat3x3); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.InstancePoses3D";
    public static final int NUM_COMPONENTS = 5;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TRANSLATIONS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "InstancePoses3D:translations", "rerun.components.Translation3D");

    /**
     * Translation vectors.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTranslations() {
        return DESCRIPTOR_TRANSLATIONS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ROTATION_AXIS_ANGLES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "InstancePoses3D:rotation_axis_angles", "rerun.components.RotationAxisAngle");

    /**
     * Rotations via axis + angle.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRotationAxisAngles() {
        return DESCRIPTOR_ROTATION_AXIS_ANGLES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_QUATERNIONS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "InstancePoses3D:quaternions", "rerun.components.RotationQuat");

    /**
     * Rotations via quaternion.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorQuaternions() {
        return DESCRIPTOR_QUATERNIONS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SCALES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "InstancePoses3D:scales", "rerun.components.Scale3D");

    /**
     * Scaling factors.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorScales() {
        return DESCRIPTOR_SCALES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MAT3X3 = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "InstancePoses3D:mat3x3", "rerun.components.TransformMat3x3");

    /**
     * 3x3 transformation matrices.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMat3x3() {
        return DESCRIPTOR_MAT3X3;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorTranslations(), descriptorRotationAxisAngles(), descriptorQuaternions(), descriptorScales(), descriptorMat3x3());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (translations != null) {
            batches.add(org.teamvan.moneyshift.components.Translation3D.batch(descriptorTranslations(), allocator, translations));
        }
        if (rotationAxisAngles != null) {
            batches.add(org.teamvan.moneyshift.components.RotationAxisAngle.batch(descriptorRotationAxisAngles(), allocator, rotationAxisAngles));
        }
        if (quaternions != null) {
            batches.add(org.teamvan.moneyshift.components.RotationQuat.batch(descriptorQuaternions(), allocator, quaternions));
        }
        if (scales != null) {
            batches.add(org.teamvan.moneyshift.components.Scale3D.batch(descriptorScales(), allocator, scales));
        }
        if (mat3x3 != null) {
            batches.add(org.teamvan.moneyshift.components.TransformMat3x3.batch(descriptorMat3x3(), allocator, mat3x3));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.Translation3D> translations;
        private java.util.List<org.teamvan.moneyshift.components.RotationAxisAngle> rotationAxisAngles;
        private java.util.List<org.teamvan.moneyshift.components.RotationQuat> quaternions;
        private java.util.List<org.teamvan.moneyshift.components.Scale3D> scales;
        private java.util.List<org.teamvan.moneyshift.components.TransformMat3x3> mat3x3;
        private Builder() {}

        public Builder withTranslations(java.util.List<org.teamvan.moneyshift.components.Translation3D> value) {
            this.translations = java.util.List.copyOf(value);
            return this;
        }

        public Builder withTranslations(org.teamvan.moneyshift.components.Translation3D... value) {
            return withTranslations(java.util.List.of(value));
        }

        public Builder withRotationAxisAngles(java.util.List<org.teamvan.moneyshift.components.RotationAxisAngle> value) {
            this.rotationAxisAngles = java.util.List.copyOf(value);
            return this;
        }

        public Builder withRotationAxisAngles(org.teamvan.moneyshift.components.RotationAxisAngle... value) {
            return withRotationAxisAngles(java.util.List.of(value));
        }

        public Builder withQuaternions(java.util.List<org.teamvan.moneyshift.components.RotationQuat> value) {
            this.quaternions = java.util.List.copyOf(value);
            return this;
        }

        public Builder withQuaternions(org.teamvan.moneyshift.components.RotationQuat... value) {
            return withQuaternions(java.util.List.of(value));
        }

        public Builder withScales(java.util.List<org.teamvan.moneyshift.components.Scale3D> value) {
            this.scales = java.util.List.copyOf(value);
            return this;
        }

        public Builder withScales(org.teamvan.moneyshift.components.Scale3D... value) {
            return withScales(java.util.List.of(value));
        }

        public Builder withMat3x3(java.util.List<org.teamvan.moneyshift.components.TransformMat3x3> value) {
            this.mat3x3 = java.util.List.copyOf(value);
            return this;
        }

        public Builder withMat3x3(org.teamvan.moneyshift.components.TransformMat3x3... value) {
            return withMat3x3(java.util.List.of(value));
        }

        public InstancePoses3D build() {
            return new InstancePoses3D(translations, rotationAxisAngles, quaternions, scales, mat3x3);
        }
    }

    public static InstancePoses3D of(
    ) {
        return new InstancePoses3D(null, null, null, null, null);
    }
}
