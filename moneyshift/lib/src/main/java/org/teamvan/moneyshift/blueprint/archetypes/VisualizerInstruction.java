// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * A visualizer instruction for an entity.
 */
public record VisualizerInstruction(
    /**
     * The type of the visualizer.
     */
    org.teamvan.moneyshift.blueprint.components.VisualizerType visualizerType,
    /**
     * The component mapping pairs.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.VisualizerComponentMapping> componentMap
) implements org.teamvan.moneyshift.Archetype {
    public VisualizerInstruction {
        java.util.Objects.requireNonNull(visualizerType, "visualizerType");
        if (componentMap != null) { componentMap = java.util.List.copyOf(componentMap); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.VisualizerInstruction";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VISUALIZER_TYPE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VisualizerInstruction:visualizer_type", "rerun.blueprint.components.VisualizerType");

    /**
     * The type of the visualizer.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVisualizerType() {
        return DESCRIPTOR_VISUALIZER_TYPE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COMPONENT_MAP = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VisualizerInstruction:component_map", "rerun.blueprint.components.VisualizerComponentMapping");

    /**
     * The component mapping pairs.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorComponentMap() {
        return DESCRIPTOR_COMPONENT_MAP;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorVisualizerType());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorComponentMap());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.blueprint.components.VisualizerType.batch(descriptorVisualizerType(), allocator, visualizerType));
        if (componentMap != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.VisualizerComponentMapping.batch(descriptorComponentMap(), allocator, componentMap));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.VisualizerType visualizerType;
        private java.util.List<org.teamvan.moneyshift.blueprint.components.VisualizerComponentMapping> componentMap;
        private Builder() {}

        public Builder withVisualizerType(org.teamvan.moneyshift.blueprint.components.VisualizerType value) {
            this.visualizerType = value;
            return this;
        }

        public Builder withComponentMap(java.util.List<org.teamvan.moneyshift.blueprint.components.VisualizerComponentMapping> value) {
            this.componentMap = java.util.List.copyOf(value);
            return this;
        }

        public Builder withComponentMap(org.teamvan.moneyshift.blueprint.components.VisualizerComponentMapping... value) {
            return withComponentMap(java.util.List.of(value));
        }

        public VisualizerInstruction build() {
            return new VisualizerInstruction(visualizerType, componentMap);
        }
    }

    public static VisualizerInstruction of(
        org.teamvan.moneyshift.blueprint.components.VisualizerType visualizerType
    ) {
        return new VisualizerInstruction(visualizerType, null);
    }
}
