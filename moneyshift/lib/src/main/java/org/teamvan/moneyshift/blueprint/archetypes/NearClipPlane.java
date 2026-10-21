// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Controls the distance to the near clip plane in 3D scene units.
 */
public record NearClipPlane(
    /**
     * Controls the distance to the near clip plane in 3D scene units.
     * 
     * Content closer than this distance will not be visible.
     */
    org.teamvan.moneyshift.blueprint.components.NearClipPlane nearClipPlane
) implements org.teamvan.moneyshift.Archetype {
    public NearClipPlane {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.NearClipPlane";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_NEAR_CLIP_PLANE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "NearClipPlane:near_clip_plane", "rerun.blueprint.components.NearClipPlane");

    /**
     * Controls the distance to the near clip plane in 3D scene units.
     * 
     * Content closer than this distance will not be visible.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorNearClipPlane() {
        return DESCRIPTOR_NEAR_CLIP_PLANE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorNearClipPlane());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (nearClipPlane != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.NearClipPlane.batch(descriptorNearClipPlane(), allocator, nearClipPlane));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.NearClipPlane nearClipPlane;
        private Builder() {}

        public Builder withNearClipPlane(org.teamvan.moneyshift.blueprint.components.NearClipPlane value) {
            this.nearClipPlane = value;
            return this;
        }

        public NearClipPlane build() {
            return new NearClipPlane(nearClipPlane);
        }
    }

    public static NearClipPlane of(
    ) {
        return new NearClipPlane(null);
    }
}
