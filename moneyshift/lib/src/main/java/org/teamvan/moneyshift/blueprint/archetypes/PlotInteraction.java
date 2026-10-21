// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configuration for plot interaction behavior.
 */
public record PlotInteraction(
    /**
     * How the tooltip behaves when hovering over the plot.
     */
    org.teamvan.moneyshift.blueprint.components.TooltipMode tooltipMode,
    /**
     * When data point markers are displayed on line series.
     */
    org.teamvan.moneyshift.blueprint.components.PointsDisplay pointsDisplay
) implements org.teamvan.moneyshift.Archetype {
    public PlotInteraction {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.PlotInteraction";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TOOLTIP_MODE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "PlotInteraction:tooltip_mode", "rerun.blueprint.components.TooltipMode");

    /**
     * How the tooltip behaves when hovering over the plot.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTooltipMode() {
        return DESCRIPTOR_TOOLTIP_MODE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_POINTS_DISPLAY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "PlotInteraction:points_display", "rerun.blueprint.components.PointsDisplay");

    /**
     * When data point markers are displayed on line series.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorPointsDisplay() {
        return DESCRIPTOR_POINTS_DISPLAY;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorTooltipMode(), descriptorPointsDisplay());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (tooltipMode != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.TooltipMode.batch(descriptorTooltipMode(), allocator, tooltipMode));
        }
        if (pointsDisplay != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.PointsDisplay.batch(descriptorPointsDisplay(), allocator, pointsDisplay));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.TooltipMode tooltipMode;
        private org.teamvan.moneyshift.blueprint.components.PointsDisplay pointsDisplay;
        private Builder() {}

        public Builder withTooltipMode(org.teamvan.moneyshift.blueprint.components.TooltipMode value) {
            this.tooltipMode = value;
            return this;
        }

        public Builder withPointsDisplay(org.teamvan.moneyshift.blueprint.components.PointsDisplay value) {
            this.pointsDisplay = value;
            return this;
        }

        public PlotInteraction build() {
            return new PlotInteraction(tooltipMode, pointsDisplay);
        }
    }

    public static PlotInteraction of(
    ) {
        return new PlotInteraction(null, null);
    }
}
