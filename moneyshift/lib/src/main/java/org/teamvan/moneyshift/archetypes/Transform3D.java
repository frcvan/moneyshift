// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A transform between two 3D spaces, i.e. a pose.
 * 
 * From the point of view of the entity's coordinate system,
 * all components are applied in the inverse order they are listed here.
 * E.g. if both a translation and a mat3x3 transform are present,
 * the 3x3 matrix is applied first, followed by the translation.
 * 
 * Whenever you log this archetype, the state of the resulting transform relationship is fully reset to the new archetype.
 * This means that if you first log a transform with only a translation, and then log one with only a rotation,
 * it will be resolved to a transform with only a rotation.
 * (This is unlike how we usually apply latest-at semantics on an archetype where we take the latest state of any component independently)
 * 
 * For transforms that affect only a single entity and do not propagate along the entity tree refer to {@link org.teamvan.moneyshift.archetypes.InstancePoses3D rerun::archetypes::InstancePoses3D}.
 * 
 */
public record Transform3D(
    /**
     * Translation vector.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    org.teamvan.moneyshift.components.Translation3D translation,
    /**
     * Rotation via axis + angle.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    org.teamvan.moneyshift.components.RotationAxisAngle rotationAxisAngle,
    /**
     * Rotation via quaternion.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    org.teamvan.moneyshift.components.RotationQuat quaternion,
    /**
     * Scaling factor.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    org.teamvan.moneyshift.components.Scale3D scale,
    /**
     * 3x3 transformation matrix.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    org.teamvan.moneyshift.components.TransformMat3x3 mat3x3,
    /**
     * Specifies the relation this transform establishes between this entity and its parent.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    org.teamvan.moneyshift.components.TransformRelation relation,
    /**
     * The child frame this transform transforms from.
     * 
     * The entity at which the transform relationship of any given child frame is specified mustn't change over time, but is allowed to be different for static time.
     * E.g. if you specified the child frame {@code "robot_arm"} on an entity named {@code "my_transforms"}, you may not log transforms
     * with the child frame {@code "robot_arm"} on any other entity than {@code "my_transforms"} unless one of them was logged with static time.
     * 
     * If not specified, this is set to the implicit transform frame of the current entity path.
     * This means that if a {@link org.teamvan.moneyshift.archetypes.Transform3D rerun::archetypes::Transform3D} is set on an entity called {@code /my/entity/path} then this will default to {@code tf#/my/entity/path}.
     * 
     * To set the frame an entity is part of see {@link org.teamvan.moneyshift.archetypes.CoordinateFrame rerun::archetypes::CoordinateFrame}.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    org.teamvan.moneyshift.components.TransformFrameId childFrame,
    /**
     * The parent frame this transform transforms into.
     * 
     * Any child frame has exactly one parent frame at any point in time, so logging a different {@code parent_frame} for a known {@code child_frame} replaces the previous relationship.
     * 
     * If not specified, this is set to the implicit transform frame of the current entity path's parent.
     * This means that if a {@link org.teamvan.moneyshift.archetypes.Transform3D rerun::archetypes::Transform3D} is set on an entity called {@code /my/entity/path} then this will default to {@code tf#/my/entity}.
     * 
     * To set the frame an entity is part of see {@link org.teamvan.moneyshift.archetypes.CoordinateFrame rerun::archetypes::CoordinateFrame}.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    org.teamvan.moneyshift.components.TransformFrameId parentFrame
) implements org.teamvan.moneyshift.Archetype {
    public Transform3D {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Transform3D";
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
            components.append("translation").append('=').append(org.teamvan.moneyshift.components.Translation3D.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("rotation_axis_angle").append('=').append(org.teamvan.moneyshift.components.RotationAxisAngle.COMPONENT_TYPE);
        }
        if ((mask & 4L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("quaternion").append('=').append(org.teamvan.moneyshift.components.RotationQuat.COMPONENT_TYPE);
        }
        if ((mask & 8L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("scale").append('=').append(org.teamvan.moneyshift.components.Scale3D.COMPONENT_TYPE);
        }
        if ((mask & 16L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("mat3x3").append('=').append(org.teamvan.moneyshift.components.TransformMat3x3.COMPONENT_TYPE);
        }
        if ((mask & 32L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("relation").append('=').append(org.teamvan.moneyshift.components.TransformRelation.COMPONENT_TYPE);
        }
        if ((mask & 64L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("child_frame").append('=').append(org.teamvan.moneyshift.components.TransformFrameId.COMPONENT_TYPE);
        }
        if ((mask & 128L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("parent_frame").append('=').append(org.teamvan.moneyshift.components.TransformFrameId.COMPONENT_TYPE);
        }
        java.lang.String joined = components.toString();
        cachedComponents = new ComponentsCache(mask, joined);
        return joined;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        long mask = 0L;
        if (translation != null) mask |= 1L;
        if (rotationAxisAngle != null) mask |= 2L;
        if (quaternion != null) mask |= 4L;
        if (scale != null) mask |= 8L;
        if (mat3x3 != null) mask |= 16L;
        if (relation != null) mask |= 32L;
        if (childFrame != null) mask |= 64L;
        if (parentFrame != null) mask |= 128L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (translation != null) {
            org.teamvan.moneyshift.components.Translation3D.writeColumnsStatic(w, "translation", java.util.List.of(translation));
        }
        if (rotationAxisAngle != null) {
            org.teamvan.moneyshift.components.RotationAxisAngle.writeColumnsStatic(w, "rotation_axis_angle", java.util.List.of(rotationAxisAngle));
        }
        if (quaternion != null) {
            org.teamvan.moneyshift.components.RotationQuat.writeColumnsStatic(w, "quaternion", java.util.List.of(quaternion));
        }
        if (scale != null) {
            org.teamvan.moneyshift.components.Scale3D.writeColumnsStatic(w, "scale", java.util.List.of(scale));
        }
        if (mat3x3 != null) {
            org.teamvan.moneyshift.components.TransformMat3x3.writeColumnsStatic(w, "mat3x3", java.util.List.of(mat3x3));
        }
        if (relation != null) {
            org.teamvan.moneyshift.components.TransformRelation.writeColumnsStatic(w, "relation", java.util.List.of(relation));
        }
        if (childFrame != null) {
            org.teamvan.moneyshift.components.TransformFrameId.writeColumnsStatic(w, "child_frame", java.util.List.of(childFrame));
        }
        if (parentFrame != null) {
            org.teamvan.moneyshift.components.TransformFrameId.writeColumnsStatic(w, "parent_frame", java.util.List.of(parentFrame));
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Translation3D translation;
        private org.teamvan.moneyshift.components.RotationAxisAngle rotationAxisAngle;
        private org.teamvan.moneyshift.components.RotationQuat quaternion;
        private org.teamvan.moneyshift.components.Scale3D scale;
        private org.teamvan.moneyshift.components.TransformMat3x3 mat3x3;
        private org.teamvan.moneyshift.components.TransformRelation relation;
        private org.teamvan.moneyshift.components.TransformFrameId childFrame;
        private org.teamvan.moneyshift.components.TransformFrameId parentFrame;
        private Builder() {}

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

        public Builder withScale(org.teamvan.moneyshift.components.Scale3D value) {
            this.scale = value;
            return this;
        }

        public Builder withMat3x3(org.teamvan.moneyshift.components.TransformMat3x3 value) {
            this.mat3x3 = value;
            return this;
        }

        public Builder withRelation(org.teamvan.moneyshift.components.TransformRelation value) {
            this.relation = value;
            return this;
        }

        public Builder withChildFrame(org.teamvan.moneyshift.components.TransformFrameId value) {
            this.childFrame = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withChildFrame(java.lang.String value) {
            return withChildFrame(new org.teamvan.moneyshift.components.TransformFrameId(value));
        }

        public Builder withParentFrame(org.teamvan.moneyshift.components.TransformFrameId value) {
            this.parentFrame = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withParentFrame(java.lang.String value) {
            return withParentFrame(new org.teamvan.moneyshift.components.TransformFrameId(value));
        }

        public Transform3D build() {
            return new Transform3D(translation, rotationAxisAngle, quaternion, scale, mat3x3, relation, childFrame, parentFrame);
        }
    }

    public static Transform3D of(
    ) {
        return new Transform3D(null, null, null, null, null, null, null, null);
    }
}
