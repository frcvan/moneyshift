// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.views;

/**
 * A 2D map view to display geospatial primitives.
 * 
 */
public record MapView(
    /**
     * Configures the zoom level of the map view.
     */
    org.teamvan.moneyshift.blueprint.archetypes.MapZoom zoom,
    /**
     * Configuration for the background map of the map view.
     */
    org.teamvan.moneyshift.blueprint.archetypes.MapBackground background
) {
    public static final java.lang.String VIEW_NAME = "rerun.blueprint.views.MapView";

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.archetypes.MapZoom zoom;
        private org.teamvan.moneyshift.blueprint.archetypes.MapBackground background;
        private Builder() {}

        public Builder withZoom(org.teamvan.moneyshift.blueprint.archetypes.MapZoom value) {
            this.zoom = value;
            return this;
        }

        public Builder withBackground(org.teamvan.moneyshift.blueprint.archetypes.MapBackground value) {
            this.background = value;
            return this;
        }

        public MapView build() {
            return new MapView(zoom, background);
        }
    }
}
