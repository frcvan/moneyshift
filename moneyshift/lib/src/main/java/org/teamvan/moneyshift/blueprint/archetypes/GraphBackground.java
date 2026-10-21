// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configuration of a background in a graph view.
 */
public record GraphBackground(
    /**
     * Color used for the background.
     */
    org.teamvan.moneyshift.components.Color color
) implements org.teamvan.moneyshift.Archetype {
    public GraphBackground {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.GraphBackground";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLOR = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GraphBackground:color", "rerun.components.Color");

    /**
     * Color used for the background.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColor() {
        return DESCRIPTOR_COLOR;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorColor());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (color != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColor(), allocator, color));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Color color;
        private Builder() {}

        public Builder withColor(org.teamvan.moneyshift.components.Color value) {
            this.color = value;
            return this;
        }

        public GraphBackground build() {
            return new GraphBackground(color);
        }
    }

    public static GraphBackground of(
    ) {
        return new GraphBackground(null);
    }
}
