// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Tries to move the center of mass of the graph to the origin.
 */
public record ForceCenter(
    /**
     * Whether the center force is enabled.
     * 
     * The center force tries to move the center of mass of the graph towards the origin.
     */
    org.teamvan.moneyshift.blueprint.components.Enabled enabled,
    /**
     * The strength of the force.
     */
    org.teamvan.moneyshift.blueprint.components.ForceStrength strength
) implements org.teamvan.moneyshift.Archetype {
    public ForceCenter {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.ForceCenter";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ENABLED = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ForceCenter:enabled", "rerun.blueprint.components.Enabled");

    /**
     * Whether the center force is enabled.
     * 
     * The center force tries to move the center of mass of the graph towards the origin.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorEnabled() {
        return DESCRIPTOR_ENABLED;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_STRENGTH = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ForceCenter:strength", "rerun.blueprint.components.ForceStrength");

    /**
     * The strength of the force.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorStrength() {
        return DESCRIPTOR_STRENGTH;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorEnabled(), descriptorStrength());
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
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.Enabled enabled;
        private org.teamvan.moneyshift.blueprint.components.ForceStrength strength;
        private Builder() {}

        public Builder withEnabled(org.teamvan.moneyshift.blueprint.components.Enabled value) {
            this.enabled = value;
            return this;
        }

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withEnabled(boolean value) {
            return withEnabled(new org.teamvan.moneyshift.blueprint.components.Enabled(value));
        }

        public Builder withStrength(org.teamvan.moneyshift.blueprint.components.ForceStrength value) {
            this.strength = value;
            return this;
        }

        /** Overload that takes the {@code double} value(s) directly. */
        public Builder withStrength(double value) {
            return withStrength(new org.teamvan.moneyshift.blueprint.components.ForceStrength(value));
        }

        public ForceCenter build() {
            return new ForceCenter(enabled, strength);
        }
    }

    public static ForceCenter of(
    ) {
        return new ForceCenter(null, null);
    }
}
