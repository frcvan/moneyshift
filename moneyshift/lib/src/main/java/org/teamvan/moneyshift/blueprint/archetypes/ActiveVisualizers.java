// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Override the visualizers for an entity.
 * 
 * This archetype is a stop-gap mechanism based on the current implementation details
 * of the visualizer system. It is not intended to be a long-term solution, but provides
 * enough utility to be useful in the short term.
 * 
 * This can only be used as part of blueprints. It will have no effect if used
 * in a regular entity.
 */
public record ActiveVisualizers(
    /**
     * Id's of the visualizers that should be active.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.VisualizerInstructionId> instructionIds
) implements org.teamvan.moneyshift.Archetype {
    public ActiveVisualizers {
        java.util.Objects.requireNonNull(instructionIds, "instructionIds");
        instructionIds = java.util.List.copyOf(instructionIds);
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.ActiveVisualizers";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_INSTRUCTION_IDS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ActiveVisualizers:instruction_ids", "rerun.blueprint.components.VisualizerInstructionId");

    /**
     * Id's of the visualizers that should be active.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorInstructionIds() {
        return DESCRIPTOR_INSTRUCTION_IDS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorInstructionIds());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of();
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.blueprint.components.VisualizerInstructionId.batch(descriptorInstructionIds(), allocator, instructionIds));
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.blueprint.components.VisualizerInstructionId> instructionIds;
        private Builder() {}

        public Builder withInstructionIds(java.util.List<org.teamvan.moneyshift.blueprint.components.VisualizerInstructionId> value) {
            this.instructionIds = java.util.List.copyOf(value);
            return this;
        }

        public Builder withInstructionIds(org.teamvan.moneyshift.blueprint.components.VisualizerInstructionId... value) {
            return withInstructionIds(java.util.List.of(value));
        }

        public ActiveVisualizers build() {
            return new ActiveVisualizers(instructionIds);
        }
    }

    public static ActiveVisualizers of(
        java.util.List<org.teamvan.moneyshift.blueprint.components.VisualizerInstructionId> instructionIds
    ) {
        return new ActiveVisualizers(instructionIds);
    }
}
