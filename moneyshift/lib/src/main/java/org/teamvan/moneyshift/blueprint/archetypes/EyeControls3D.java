// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * The controls for the 3D eye in a spatial 3D view.
 * 
 * This configures the camera through which the 3D scene is viewed.
 */
public record EyeControls3D(
    /**
     * The kind of the eye for the spatial 3D view.
     * 
     * This controls how the eye movement behaves when the user interact with the view.
     * Defaults to orbital.
     */
    org.teamvan.moneyshift.blueprint.components.Eye3DKind kind,
    /**
     * The cameras current position.
     */
    org.teamvan.moneyshift.components.Position3D position,
    /**
     * The position the camera is currently looking at.
     * 
     * If this is an orbital camera, this also is the center it orbits around.
     * 
     * By default this is the center of the scene bounds.
     */
    org.teamvan.moneyshift.components.Position3D lookTarget,
    /**
     * The up-axis of the eye itself, in world-space.
     * 
     * Initially, the up-axis of the eye will be the same as the up-axis of the scene (or +Z if
     * the scene has no up axis defined).
     */
    org.teamvan.moneyshift.components.Vector3D eyeUp,
    /**
     * Translation speed of the eye in the view (when using WASDQE keys to move in the 3D scene).
     * 
     * The default depends on the control kind.
     * For orbit cameras it is derived from the distance to the orbit center.
     * For first person cameras it is derived from the scene size.
     */
    org.teamvan.moneyshift.components.LinearSpeed speed,
    /**
     * Currently tracked entity.
     * 
     * If this is a camera, it takes over the camera pose, otherwise follows the entity.
     */
    org.teamvan.moneyshift.components.EntityPath trackingEntity,
    /**
     * What speed, if any, the camera should spin around the eye-up axis.
     * 
     * Defaults to zero, meaning no spinning.
     */
    org.teamvan.moneyshift.blueprint.components.AngularSpeed spinSpeed
) implements org.teamvan.moneyshift.Archetype {
    public EyeControls3D {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.EyeControls3D";
    public static final int NUM_COMPONENTS = 7;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_KIND = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EyeControls3D:kind", "rerun.blueprint.components.Eye3DKind");

    /**
     * The kind of the eye for the spatial 3D view.
     * 
     * This controls how the eye movement behaves when the user interact with the view.
     * Defaults to orbital.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorKind() {
        return DESCRIPTOR_KIND;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_POSITION = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EyeControls3D:position", "rerun.components.Position3D");

    /**
     * The cameras current position.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorPosition() {
        return DESCRIPTOR_POSITION;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LOOK_TARGET = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EyeControls3D:look_target", "rerun.components.Position3D");

    /**
     * The position the camera is currently looking at.
     * 
     * If this is an orbital camera, this also is the center it orbits around.
     * 
     * By default this is the center of the scene bounds.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLookTarget() {
        return DESCRIPTOR_LOOK_TARGET;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_EYE_UP = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EyeControls3D:eye_up", "rerun.components.Vector3D");

    /**
     * The up-axis of the eye itself, in world-space.
     * 
     * Initially, the up-axis of the eye will be the same as the up-axis of the scene (or +Z if
     * the scene has no up axis defined).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorEyeUp() {
        return DESCRIPTOR_EYE_UP;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SPEED = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EyeControls3D:speed", "rerun.components.LinearSpeed");

    /**
     * Translation speed of the eye in the view (when using WASDQE keys to move in the 3D scene).
     * 
     * The default depends on the control kind.
     * For orbit cameras it is derived from the distance to the orbit center.
     * For first person cameras it is derived from the scene size.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorSpeed() {
        return DESCRIPTOR_SPEED;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TRACKING_ENTITY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EyeControls3D:tracking_entity", "rerun.components.EntityPath");

    /**
     * Currently tracked entity.
     * 
     * If this is a camera, it takes over the camera pose, otherwise follows the entity.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTrackingEntity() {
        return DESCRIPTOR_TRACKING_ENTITY;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SPIN_SPEED = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EyeControls3D:spin_speed", "rerun.blueprint.components.AngularSpeed");

    /**
     * What speed, if any, the camera should spin around the eye-up axis.
     * 
     * Defaults to zero, meaning no spinning.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorSpinSpeed() {
        return DESCRIPTOR_SPIN_SPEED;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorKind(), descriptorPosition(), descriptorLookTarget(), descriptorEyeUp(), descriptorSpeed(), descriptorTrackingEntity(), descriptorSpinSpeed());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (kind != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.Eye3DKind.batch(descriptorKind(), allocator, kind));
        }
        if (position != null) {
            batches.add(org.teamvan.moneyshift.components.Position3D.batch(descriptorPosition(), allocator, position));
        }
        if (lookTarget != null) {
            batches.add(org.teamvan.moneyshift.components.Position3D.batch(descriptorLookTarget(), allocator, lookTarget));
        }
        if (eyeUp != null) {
            batches.add(org.teamvan.moneyshift.components.Vector3D.batch(descriptorEyeUp(), allocator, eyeUp));
        }
        if (speed != null) {
            batches.add(org.teamvan.moneyshift.components.LinearSpeed.batch(descriptorSpeed(), allocator, speed));
        }
        if (trackingEntity != null) {
            batches.add(org.teamvan.moneyshift.components.EntityPath.batch(descriptorTrackingEntity(), allocator, trackingEntity));
        }
        if (spinSpeed != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.AngularSpeed.batch(descriptorSpinSpeed(), allocator, spinSpeed));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.Eye3DKind kind;
        private org.teamvan.moneyshift.components.Position3D position;
        private org.teamvan.moneyshift.components.Position3D lookTarget;
        private org.teamvan.moneyshift.components.Vector3D eyeUp;
        private org.teamvan.moneyshift.components.LinearSpeed speed;
        private org.teamvan.moneyshift.components.EntityPath trackingEntity;
        private org.teamvan.moneyshift.blueprint.components.AngularSpeed spinSpeed;
        private Builder() {}

        public Builder withKind(org.teamvan.moneyshift.blueprint.components.Eye3DKind value) {
            this.kind = value;
            return this;
        }

        public Builder withPosition(org.teamvan.moneyshift.components.Position3D value) {
            this.position = value;
            return this;
        }

        public Builder withLookTarget(org.teamvan.moneyshift.components.Position3D value) {
            this.lookTarget = value;
            return this;
        }

        public Builder withEyeUp(org.teamvan.moneyshift.components.Vector3D value) {
            this.eyeUp = value;
            return this;
        }

        public Builder withSpeed(org.teamvan.moneyshift.components.LinearSpeed value) {
            this.speed = value;
            return this;
        }

        /** Overload that takes the {@code double} value(s) directly. */
        public Builder withSpeed(double value) {
            return withSpeed(new org.teamvan.moneyshift.components.LinearSpeed(value));
        }

        public Builder withTrackingEntity(org.teamvan.moneyshift.components.EntityPath value) {
            this.trackingEntity = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withTrackingEntity(java.lang.String value) {
            return withTrackingEntity(new org.teamvan.moneyshift.components.EntityPath(value));
        }

        public Builder withSpinSpeed(org.teamvan.moneyshift.blueprint.components.AngularSpeed value) {
            this.spinSpeed = value;
            return this;
        }

        /** Overload that takes the {@code double} value(s) directly. */
        public Builder withSpinSpeed(double value) {
            return withSpinSpeed(new org.teamvan.moneyshift.blueprint.components.AngularSpeed(value));
        }

        public EyeControls3D build() {
            return new EyeControls3D(kind, position, lookTarget, eyeUp, speed, trackingEntity, spinSpeed);
        }
    }

    public static EyeControls3D of(
    ) {
        return new EyeControls3D(null, null, null, null, null, null, null);
    }
}
