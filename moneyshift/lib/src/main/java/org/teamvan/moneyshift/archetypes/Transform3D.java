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
 * For transforms that affect only a single entity and do not propagate along the entity tree refer to {@link org.teamvan.moneyshift.archetypes.InstancePoses3D}.
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
     * This means that if a {@link org.teamvan.moneyshift.archetypes.Transform3D} is set on an entity called {@code /my/entity/path} then this will default to {@code tf#/my/entity/path}.
     * 
     * To set the frame an entity is part of see {@link org.teamvan.moneyshift.archetypes.CoordinateFrame}.
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
     * This means that if a {@link org.teamvan.moneyshift.archetypes.Transform3D} is set on an entity called {@code /my/entity/path} then this will default to {@code tf#/my/entity}.
     * 
     * To set the frame an entity is part of see {@link org.teamvan.moneyshift.archetypes.CoordinateFrame}.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    org.teamvan.moneyshift.components.TransformFrameId parentFrame
) implements org.teamvan.moneyshift.Archetype {
    public Transform3D {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Transform3D";
    public static final int NUM_COMPONENTS = 8;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TRANSLATION = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Transform3D:translation", "rerun.components.Translation3D");

    /**
     * Translation vector.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTranslation() {
        return DESCRIPTOR_TRANSLATION;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ROTATION_AXIS_ANGLE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Transform3D:rotation_axis_angle", "rerun.components.RotationAxisAngle");

    /**
     * Rotation via axis + angle.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRotationAxisAngle() {
        return DESCRIPTOR_ROTATION_AXIS_ANGLE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_QUATERNION = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Transform3D:quaternion", "rerun.components.RotationQuat");

    /**
     * Rotation via quaternion.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorQuaternion() {
        return DESCRIPTOR_QUATERNION;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SCALE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Transform3D:scale", "rerun.components.Scale3D");

    /**
     * Scaling factor.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorScale() {
        return DESCRIPTOR_SCALE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MAT3X3 = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Transform3D:mat3x3", "rerun.components.TransformMat3x3");

    /**
     * 3x3 transformation matrix.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMat3x3() {
        return DESCRIPTOR_MAT3X3;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_RELATION = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Transform3D:relation", "rerun.components.TransformRelation");

    /**
     * Specifies the relation this transform establishes between this entity and its parent.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRelation() {
        return DESCRIPTOR_RELATION;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CHILD_FRAME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Transform3D:child_frame", "rerun.components.TransformFrameId");

    /**
     * The child frame this transform transforms from.
     * 
     * The entity at which the transform relationship of any given child frame is specified mustn't change over time, but is allowed to be different for static time.
     * E.g. if you specified the child frame {@code "robot_arm"} on an entity named {@code "my_transforms"}, you may not log transforms
     * with the child frame {@code "robot_arm"} on any other entity than {@code "my_transforms"} unless one of them was logged with static time.
     * 
     * If not specified, this is set to the implicit transform frame of the current entity path.
     * This means that if a {@link org.teamvan.moneyshift.archetypes.Transform3D} is set on an entity called {@code /my/entity/path} then this will default to {@code tf#/my/entity/path}.
     * 
     * To set the frame an entity is part of see {@link org.teamvan.moneyshift.archetypes.CoordinateFrame}.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorChildFrame() {
        return DESCRIPTOR_CHILD_FRAME;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_PARENT_FRAME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Transform3D:parent_frame", "rerun.components.TransformFrameId");

    /**
     * The parent frame this transform transforms into.
     * 
     * Any child frame has exactly one parent frame at any point in time, so logging a different {@code parent_frame} for a known {@code child_frame} replaces the previous relationship.
     * 
     * If not specified, this is set to the implicit transform frame of the current entity path's parent.
     * This means that if a {@link org.teamvan.moneyshift.archetypes.Transform3D} is set on an entity called {@code /my/entity/path} then this will default to {@code tf#/my/entity}.
     * 
     * To set the frame an entity is part of see {@link org.teamvan.moneyshift.archetypes.CoordinateFrame}.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorParentFrame() {
        return DESCRIPTOR_PARENT_FRAME;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorTranslation(), descriptorRotationAxisAngle(), descriptorQuaternion(), descriptorScale(), descriptorMat3x3(), descriptorRelation(), descriptorChildFrame(), descriptorParentFrame());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (translation != null) {
            batches.add(org.teamvan.moneyshift.components.Translation3D.batch(descriptorTranslation(), allocator, translation));
        }
        if (rotationAxisAngle != null) {
            batches.add(org.teamvan.moneyshift.components.RotationAxisAngle.batch(descriptorRotationAxisAngle(), allocator, rotationAxisAngle));
        }
        if (quaternion != null) {
            batches.add(org.teamvan.moneyshift.components.RotationQuat.batch(descriptorQuaternion(), allocator, quaternion));
        }
        if (scale != null) {
            batches.add(org.teamvan.moneyshift.components.Scale3D.batch(descriptorScale(), allocator, scale));
        }
        if (mat3x3 != null) {
            batches.add(org.teamvan.moneyshift.components.TransformMat3x3.batch(descriptorMat3x3(), allocator, mat3x3));
        }
        if (relation != null) {
            batches.add(org.teamvan.moneyshift.components.TransformRelation.batch(descriptorRelation(), allocator, relation));
        }
        if (childFrame != null) {
            batches.add(org.teamvan.moneyshift.components.TransformFrameId.batch(descriptorChildFrame(), allocator, childFrame));
        }
        if (parentFrame != null) {
            batches.add(org.teamvan.moneyshift.components.TransformFrameId.batch(descriptorParentFrame(), allocator, parentFrame));
        }
        return batches;
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
