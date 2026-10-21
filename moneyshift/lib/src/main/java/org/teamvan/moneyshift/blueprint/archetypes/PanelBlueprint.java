// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Shared state for the 3 collapsible panels.
 */
public record PanelBlueprint(
    /**
     * Current state of the panel.
     */
    org.teamvan.moneyshift.blueprint.components.PanelState state
) implements org.teamvan.moneyshift.Archetype {
    public PanelBlueprint {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.PanelBlueprint";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_STATE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "PanelBlueprint:state", "rerun.blueprint.components.PanelState");

    /**
     * Current state of the panel.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorState() {
        return DESCRIPTOR_STATE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorState());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (state != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.PanelState.batch(descriptorState(), allocator, state));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.PanelState state;
        private Builder() {}

        public Builder withState(org.teamvan.moneyshift.blueprint.components.PanelState value) {
            this.state = value;
            return this;
        }

        public PanelBlueprint build() {
            return new PanelBlueprint(state);
        }
    }

    public static PanelBlueprint of(
    ) {
        return new PanelBlueprint(null);
    }
}
