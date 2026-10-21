// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Similar to gravity, this force pulls nodes towards a specific position.
 */
public record ForcePosition(
    /**
     * Whether the position force is enabled.
     * 
     * The position force pulls nodes towards a specific position, similar to gravity.
     */
    org.teamvan.moneyshift.blueprint.components.Enabled enabled,
    /**
     * The strength of the force.
     */
    org.teamvan.moneyshift.blueprint.components.ForceStrength strength,
    /**
     * The position where the nodes should be pulled towards.
     */
    org.teamvan.moneyshift.components.Position2D position
) implements org.teamvan.moneyshift.Archetype {
    public ForcePosition {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.ForcePosition";
    public static final int NUM_COMPONENTS = 3;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ENABLED = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ForcePosition:enabled", "rerun.blueprint.components.Enabled");

    /**
     * Whether the position force is enabled.
     * 
     * The position force pulls nodes towards a specific position, similar to gravity.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorEnabled() {
        return DESCRIPTOR_ENABLED;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_STRENGTH = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ForcePosition:strength", "rerun.blueprint.components.ForceStrength");

    /**
     * The strength of the force.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorStrength() {
        return DESCRIPTOR_STRENGTH;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_POSITION = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ForcePosition:position", "rerun.components.Position2D");

    /**
     * The position where the nodes should be pulled towards.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorPosition() {
        return DESCRIPTOR_POSITION;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorEnabled(), descriptorStrength(), descriptorPosition());
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
        if (position != null) {
            batches.add(org.teamvan.moneyshift.components.Position2D.batch(descriptorPosition(), allocator, position));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.Enabled enabled;
        private org.teamvan.moneyshift.blueprint.components.ForceStrength strength;
        private org.teamvan.moneyshift.components.Position2D position;
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

        public Builder withPosition(org.teamvan.moneyshift.components.Position2D value) {
            this.position = value;
            return this;
        }

        public ForcePosition build() {
            return new ForcePosition(enabled, strength, position);
        }
    }

    public static ForcePosition of(
    ) {
        return new ForcePosition(null, null, null);
    }
}
