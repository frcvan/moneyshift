// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.views;

/**
 * A time series view for scalars over time, for use with {@link org.teamvan.moneyshift.archetypes.Scalars}.
 * 
 */
public record TimeSeriesView(
    /**
     * Configures the horizontal axis of the plot.
     */
    org.teamvan.moneyshift.blueprint.archetypes.TimeAxis axisX,
    /**
     * Configures the vertical axis of the plot.
     */
    org.teamvan.moneyshift.blueprint.archetypes.ScalarAxis axisY,
    /**
     * Configures the legend of the plot.
     */
    org.teamvan.moneyshift.blueprint.archetypes.PlotLegend plotLegend,
    /**
     * Configures the background of the plot.
     */
    org.teamvan.moneyshift.blueprint.archetypes.PlotBackground background,
    /**
     * Configures tooltip and data point marker behavior.
     */
    org.teamvan.moneyshift.blueprint.archetypes.PlotInteraction interaction,
    /**
     * Configures which range on each timeline is shown by this view (unless specified differently per entity).
     * 
     * If not specified, the default is to show the entire timeline.
     * If a timeline is specified more than once, the first entry will be used.
     */
    org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges timeRanges
) {
    public static final java.lang.String VIEW_NAME = "rerun.blueprint.views.TimeSeriesView";

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.archetypes.TimeAxis axisX;
        private org.teamvan.moneyshift.blueprint.archetypes.ScalarAxis axisY;
        private org.teamvan.moneyshift.blueprint.archetypes.PlotLegend plotLegend;
        private org.teamvan.moneyshift.blueprint.archetypes.PlotBackground background;
        private org.teamvan.moneyshift.blueprint.archetypes.PlotInteraction interaction;
        private org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges timeRanges;
        private Builder() {}

        public Builder withAxisX(org.teamvan.moneyshift.blueprint.archetypes.TimeAxis value) {
            this.axisX = value;
            return this;
        }

        public Builder withAxisY(org.teamvan.moneyshift.blueprint.archetypes.ScalarAxis value) {
            this.axisY = value;
            return this;
        }

        public Builder withPlotLegend(org.teamvan.moneyshift.blueprint.archetypes.PlotLegend value) {
            this.plotLegend = value;
            return this;
        }

        public Builder withBackground(org.teamvan.moneyshift.blueprint.archetypes.PlotBackground value) {
            this.background = value;
            return this;
        }

        public Builder withInteraction(org.teamvan.moneyshift.blueprint.archetypes.PlotInteraction value) {
            this.interaction = value;
            return this;
        }

        public Builder withTimeRanges(org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges value) {
            this.timeRanges = value;
            return this;
        }

        public TimeSeriesView build() {
            return new TimeSeriesView(axisX, axisY, plotLegend, background, interaction, timeRanges);
        }
    }
}
