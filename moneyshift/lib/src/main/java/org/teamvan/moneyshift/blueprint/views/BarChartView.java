// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.views;

/**
 * A bar chart view.
 * 
 */
public record BarChartView(
    /**
     * Configures the legend of the plot.
     */
    org.teamvan.moneyshift.blueprint.archetypes.PlotLegend plotLegend,
    /**
     * Configures the background of the plot.
     */
    org.teamvan.moneyshift.blueprint.archetypes.PlotBackground background
) {
    public static final java.lang.String VIEW_NAME = "rerun.blueprint.views.BarChartView";

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.archetypes.PlotLegend plotLegend;
        private org.teamvan.moneyshift.blueprint.archetypes.PlotBackground background;
        private Builder() {}

        public Builder withPlotLegend(org.teamvan.moneyshift.blueprint.archetypes.PlotLegend value) {
            this.plotLegend = value;
            return this;
        }

        public Builder withBackground(org.teamvan.moneyshift.blueprint.archetypes.PlotBackground value) {
            this.background = value;
            return this;
        }

        public BarChartView build() {
            return new BarChartView(plotLegend, background);
        }
    }
}
