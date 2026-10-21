// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.views;

/**
 * For viewing spatial 2D data.
 * 
 */
public record Spatial2DView(
    /**
     * Configuration for the background of the view.
     */
    org.teamvan.moneyshift.blueprint.archetypes.Background background,
    /**
     * The visible parts of the scene, in the coordinate space of the scene.
     * 
     * Everything within these bounds are guaranteed to be visible.
     * Somethings outside of these bounds may also be visible due to letterboxing.
     */
    org.teamvan.moneyshift.blueprint.archetypes.VisualBounds2D visualBounds,
    /**
     * Configuration of spatial information shown in the view.
     */
    org.teamvan.moneyshift.blueprint.archetypes.SpatialInformation spatialInformation,
    /**
     * Configures which range on each timeline is shown by this view (unless specified differently per entity).
     * 
     * If not specified, the default is to show the latest state of each component.
     * If a timeline is specified more than once, the first entry will be used.
     */
    org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges timeRanges
) {
    public static final java.lang.String VIEW_NAME = "rerun.blueprint.views.Spatial2DView";

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.archetypes.Background background;
        private org.teamvan.moneyshift.blueprint.archetypes.VisualBounds2D visualBounds;
        private org.teamvan.moneyshift.blueprint.archetypes.SpatialInformation spatialInformation;
        private org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges timeRanges;
        private Builder() {}

        public Builder withBackground(org.teamvan.moneyshift.blueprint.archetypes.Background value) {
            this.background = value;
            return this;
        }

        public Builder withVisualBounds(org.teamvan.moneyshift.blueprint.archetypes.VisualBounds2D value) {
            this.visualBounds = value;
            return this;
        }

        public Builder withSpatialInformation(org.teamvan.moneyshift.blueprint.archetypes.SpatialInformation value) {
            this.spatialInformation = value;
            return this;
        }

        public Builder withTimeRanges(org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges value) {
            this.timeRanges = value;
            return this;
        }

        /** Overload that takes the elements of a single component directly. */
        public Builder withTimeRanges(org.teamvan.moneyshift.blueprint.components.VisibleTimeRange... value) {
            return withTimeRanges(new org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges(java.util.List.of(value)));
        }

        public Spatial2DView build() {
            return new Spatial2DView(background, visualBounds, spatialInformation, timeRanges);
        }
    }
}
