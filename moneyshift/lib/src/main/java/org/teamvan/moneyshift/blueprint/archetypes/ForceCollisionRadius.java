// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Resolves collisions between the bounding circles, according to the radius of the nodes.
 */
public record ForceCollisionRadius(
    /**
     * Whether the collision force is enabled.
     * 
     * The collision force resolves collisions between nodes based on the bounding circle defined by their radius.
     */
    org.teamvan.moneyshift.blueprint.components.Enabled enabled,
    /**
     * The strength of the force.
     */
    org.teamvan.moneyshift.blueprint.components.ForceStrength strength,
    /**
     * Specifies how often this force should be applied per iteration.
     * 
     * Increasing this parameter can lead to better results at the cost of longer computation time.
     */
    org.teamvan.moneyshift.blueprint.components.ForceIterations iterations
) implements org.teamvan.moneyshift.Archetype {
    public ForceCollisionRadius {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.ForceCollisionRadius";
    public static final int NUM_COMPONENTS = 3;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ENABLED = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ForceCollisionRadius:enabled", "rerun.blueprint.components.Enabled");

    /**
     * Whether the collision force is enabled.
     * 
     * The collision force resolves collisions between nodes based on the bounding circle defined by their radius.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorEnabled() {
        return DESCRIPTOR_ENABLED;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_STRENGTH = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ForceCollisionRadius:strength", "rerun.blueprint.components.ForceStrength");

    /**
     * The strength of the force.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorStrength() {
        return DESCRIPTOR_STRENGTH;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ITERATIONS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ForceCollisionRadius:iterations", "rerun.blueprint.components.ForceIterations");

    /**
     * Specifies how often this force should be applied per iteration.
     * 
     * Increasing this parameter can lead to better results at the cost of longer computation time.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorIterations() {
        return DESCRIPTOR_ITERATIONS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorEnabled(), descriptorStrength(), descriptorIterations());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (enabled != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.Enabled.batch(descriptorEnabled(), allocator, enabled));
        }
        if (strength != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ForceStrength.batch(descriptorStrength(), allocator, strength));
        }
        if (iterations != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ForceIterations.batch(descriptorIterations(), allocator, iterations));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.Enabled enabled;
        private org.teamvan.moneyshift.blueprint.components.ForceStrength strength;
        private org.teamvan.moneyshift.blueprint.components.ForceIterations iterations;
        private Builder() {}

        public Builder withEnabled(org.teamvan.moneyshift.blueprint.components.Enabled value) {
            this.enabled = value;
            return this;
        }

        public Builder withStrength(org.teamvan.moneyshift.blueprint.components.ForceStrength value) {
            this.strength = value;
            return this;
        }

        public Builder withIterations(org.teamvan.moneyshift.blueprint.components.ForceIterations value) {
            this.iterations = value;
            return this;
        }

        public ForceCollisionRadius build() {
            return new ForceCollisionRadius(enabled, strength, iterations);
        }
    }

    public static ForceCollisionRadius of(
    ) {
        return new ForceCollisionRadius(null, null, null);
    }
}
