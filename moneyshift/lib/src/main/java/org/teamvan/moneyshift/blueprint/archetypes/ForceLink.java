// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Aims to achieve a target distance between two nodes that are connected by an edge.
 */
public record ForceLink(
    /**
     * Whether the link force is enabled.
     * 
     * The link force aims to achieve a target distance between two nodes that are connected by one ore more edges.
     */
    org.teamvan.moneyshift.blueprint.components.Enabled enabled,
    /**
     * The target distance between two nodes.
     */
    org.teamvan.moneyshift.blueprint.components.ForceDistance distance,
    /**
     * Specifies how often this force should be applied per iteration.
     * 
     * Increasing this parameter can lead to better results at the cost of longer computation time.
     */
    org.teamvan.moneyshift.blueprint.components.ForceIterations iterations
) implements org.teamvan.moneyshift.Archetype {
    public ForceLink {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.ForceLink";
    public static final int NUM_COMPONENTS = 3;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ENABLED = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ForceLink:enabled", "rerun.blueprint.components.Enabled");

    /**
     * Whether the link force is enabled.
     * 
     * The link force aims to achieve a target distance between two nodes that are connected by one ore more edges.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorEnabled() {
        return DESCRIPTOR_ENABLED;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DISTANCE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ForceLink:distance", "rerun.blueprint.components.ForceDistance");

    /**
     * The target distance between two nodes.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorDistance() {
        return DESCRIPTOR_DISTANCE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ITERATIONS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ForceLink:iterations", "rerun.blueprint.components.ForceIterations");

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
        return java.util.List.of(descriptorEnabled(), descriptorDistance(), descriptorIterations());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (enabled != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.Enabled.batch(descriptorEnabled(), allocator, enabled));
        }
        if (distance != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ForceDistance.batch(descriptorDistance(), allocator, distance));
        }
        if (iterations != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ForceIterations.batch(descriptorIterations(), allocator, iterations));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.Enabled enabled;
        private org.teamvan.moneyshift.blueprint.components.ForceDistance distance;
        private org.teamvan.moneyshift.blueprint.components.ForceIterations iterations;
        private Builder() {}

        public Builder withEnabled(org.teamvan.moneyshift.blueprint.components.Enabled value) {
            this.enabled = value;
            return this;
        }

        public Builder withDistance(org.teamvan.moneyshift.blueprint.components.ForceDistance value) {
            this.distance = value;
            return this;
        }

        public Builder withIterations(org.teamvan.moneyshift.blueprint.components.ForceIterations value) {
            this.iterations = value;
            return this;
        }

        public ForceLink build() {
            return new ForceLink(enabled, distance, iterations);
        }
    }

    public static ForceLink of(
    ) {
        return new ForceLink(null, null, null);
    }
}
