// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configuration for the legend of a plot.
 */
public record PlotLegend(
    /**
     * To what corner the legend is aligned.
     * 
     * Defaults to the right bottom corner.
     */
    org.teamvan.moneyshift.blueprint.components.Corner2D corner,
    /**
     * Whether the legend is shown at all.
     * 
     * True by default.
     */
    org.teamvan.moneyshift.components.Visible visible
) implements org.teamvan.moneyshift.Archetype {
    public PlotLegend {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.PlotLegend";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CORNER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "PlotLegend:corner", "rerun.blueprint.components.Corner2D");

    /**
     * To what corner the legend is aligned.
     * 
     * Defaults to the right bottom corner.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorCorner() {
        return DESCRIPTOR_CORNER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VISIBLE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "PlotLegend:visible", "rerun.components.Visible");

    /**
     * Whether the legend is shown at all.
     * 
     * True by default.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVisible() {
        return DESCRIPTOR_VISIBLE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorCorner(), descriptorVisible());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (corner != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.Corner2D.batch(descriptorCorner(), allocator, corner));
        }
        if (visible != null) {
            batches.add(org.teamvan.moneyshift.components.Visible.batch(descriptorVisible(), allocator, visible));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.Corner2D corner;
        private org.teamvan.moneyshift.components.Visible visible;
        private Builder() {}

        public Builder withCorner(org.teamvan.moneyshift.blueprint.components.Corner2D value) {
            this.corner = value;
            return this;
        }

        public Builder withVisible(org.teamvan.moneyshift.components.Visible value) {
            this.visible = value;
            return this;
        }

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withVisible(boolean value) {
            return withVisible(new org.teamvan.moneyshift.components.Visible(value));
        }

        public PlotLegend build() {
            return new PlotLegend(corner, visible);
        }
    }

    public static PlotLegend of(
    ) {
        return new PlotLegend(null, null);
    }
}
