// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.views;

/**
 * For viewing spatial 3D data.
 * 
 */
public record Spatial3DView(
    /**
     * Configuration for the background of the view.
     */
    org.teamvan.moneyshift.blueprint.archetypes.Background background,
    /**
     * Configuration for the 3D line grid.
     */
    org.teamvan.moneyshift.blueprint.archetypes.LineGrid3D lineGrid,
    /**
     * Configuration of debug drawing in the 3D view.
     */
    org.teamvan.moneyshift.blueprint.archetypes.SpatialInformation spatialInformation,
    /**
     * Configuration for the 3D eye
     */
    org.teamvan.moneyshift.blueprint.archetypes.EyeControls3D eyeControls,
    /**
     * Configures which range on each timeline is shown by this view (unless specified differently per entity).
     * 
     * If not specified, the default is to show the latest state of each component.
     * If a timeline is specified more than once, the first entry will be used.
     */
    org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges timeRanges
) {
    public static final java.lang.String VIEW_NAME = "rerun.blueprint.views.Spatial3DView";

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.archetypes.Background background;
        private org.teamvan.moneyshift.blueprint.archetypes.LineGrid3D lineGrid;
        private org.teamvan.moneyshift.blueprint.archetypes.SpatialInformation spatialInformation;
        private org.teamvan.moneyshift.blueprint.archetypes.EyeControls3D eyeControls;
        private org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges timeRanges;
        private Builder() {}

        public Builder withBackground(org.teamvan.moneyshift.blueprint.archetypes.Background value) {
            this.background = value;
            return this;
        }

        public Builder withLineGrid(org.teamvan.moneyshift.blueprint.archetypes.LineGrid3D value) {
            this.lineGrid = value;
            return this;
        }

        public Builder withSpatialInformation(org.teamvan.moneyshift.blueprint.archetypes.SpatialInformation value) {
            this.spatialInformation = value;
            return this;
        }

        public Builder withEyeControls(org.teamvan.moneyshift.blueprint.archetypes.EyeControls3D value) {
            this.eyeControls = value;
            return this;
        }

        public Builder withTimeRanges(org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges value) {
            this.timeRanges = value;
            return this;
        }

        public Spatial3DView build() {
            return new Spatial3DView(background, lineGrid, spatialInformation, eyeControls, timeRanges);
        }
    }
}
