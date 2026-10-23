// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * One or more transforms applied on the current entity's transform frame.
 * 
 * Unlike {@link org.teamvan.moneyshift.archetypes.Transform3D rerun::archetypes::Transform3D}, it is *not* propagated in the transform hierarchy.
 * If {@link org.teamvan.moneyshift.archetypes.CoordinateFrame rerun::archetypes::CoordinateFrame} is specified, it acts relative to that coordinate frame,
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
 * Some visualizers like the mesh visualizer used for {@link org.teamvan.moneyshift.archetypes.Mesh3D rerun::archetypes::Mesh3D},
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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (translations != null && !translations.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("translations").append('=').append(org.teamvan.moneyshift.components.Translation3D.COMPONENT_TYPE);
        }
        if (rotationAxisAngles != null && !rotationAxisAngles.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("rotation_axis_angles").append('=').append(org.teamvan.moneyshift.components.RotationAxisAngle.COMPONENT_TYPE);
        }
        if (quaternions != null && !quaternions.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("quaternions").append('=').append(org.teamvan.moneyshift.components.RotationQuat.COMPONENT_TYPE);
        }
        if (scales != null && !scales.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("scales").append('=').append(org.teamvan.moneyshift.components.Scale3D.COMPONENT_TYPE);
        }
        if (mat3x3 != null && !mat3x3.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("mat3x3").append('=').append(org.teamvan.moneyshift.components.TransformMat3x3.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (translations != null && !translations.isEmpty()) {
            org.teamvan.moneyshift.components.Translation3D.writeColumnsStatic(w, "translations", translations);
        }
        if (rotationAxisAngles != null && !rotationAxisAngles.isEmpty()) {
            org.teamvan.moneyshift.components.RotationAxisAngle.writeColumnsStatic(w, "rotation_axis_angles", rotationAxisAngles);
        }
        if (quaternions != null && !quaternions.isEmpty()) {
            org.teamvan.moneyshift.components.RotationQuat.writeColumnsStatic(w, "quaternions", quaternions);
        }
        if (scales != null && !scales.isEmpty()) {
            org.teamvan.moneyshift.components.Scale3D.writeColumnsStatic(w, "scales", scales);
        }
        if (mat3x3 != null && !mat3x3.isEmpty()) {
            org.teamvan.moneyshift.components.TransformMat3x3.writeColumnsStatic(w, "mat3x3", mat3x3);
        }
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
