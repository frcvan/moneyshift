// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configuration of a background in a plot view.
 */
public record PlotBackground(
    /**
     * Color used for the background.
     */
    org.teamvan.moneyshift.components.Color color,
    /**
     * Should the grid be drawn?
     */
    org.teamvan.moneyshift.blueprint.components.Enabled showGrid
) implements org.teamvan.moneyshift.Archetype {
    public PlotBackground {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.PlotBackground";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLOR = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "PlotBackground:color", "rerun.components.Color");

    /**
     * Color used for the background.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColor() {
        return DESCRIPTOR_COLOR;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SHOW_GRID = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "PlotBackground:show_grid", "rerun.blueprint.components.Enabled");

    /**
     * Should the grid be drawn?
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorShowGrid() {
        return DESCRIPTOR_SHOW_GRID;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorColor(), descriptorShowGrid());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (color != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColor(), allocator, color));
        }
        if (showGrid != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.Enabled.batch(descriptorShowGrid(), allocator, showGrid));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Color color;
        private org.teamvan.moneyshift.blueprint.components.Enabled showGrid;
        private Builder() {}

        public Builder withColor(org.teamvan.moneyshift.components.Color value) {
            this.color = value;
            return this;
        }

        public Builder withShowGrid(org.teamvan.moneyshift.blueprint.components.Enabled value) {
            this.showGrid = value;
            return this;
        }

        public PlotBackground build() {
            return new PlotBackground(color, showGrid);
        }
    }

    public static PlotBackground of(
    ) {
        return new PlotBackground(null, null);
    }
}
