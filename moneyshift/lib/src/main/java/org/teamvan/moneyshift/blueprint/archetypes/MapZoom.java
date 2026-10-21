// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configuration of the map view zoom level.
 */
public record MapZoom(
    /**
     * Zoom level for the map.
     * 
     * Zoom level follow the Zoom level follow the <a href="https://wiki.openstreetmap.org/wiki/Zoom_levels">`OpenStreetMap` definition</a>.
     */
    org.teamvan.moneyshift.blueprint.components.ZoomLevel zoom
) implements org.teamvan.moneyshift.Archetype {
    public MapZoom {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.MapZoom";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ZOOM = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "MapZoom:zoom", "rerun.blueprint.components.ZoomLevel");

    /**
     * Zoom level for the map.
     * 
     * Zoom level follow the Zoom level follow the <a href="https://wiki.openstreetmap.org/wiki/Zoom_levels">`OpenStreetMap` definition</a>.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorZoom() {
        return DESCRIPTOR_ZOOM;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorZoom());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (zoom != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ZoomLevel.batch(descriptorZoom(), allocator, zoom));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.ZoomLevel zoom;
        private Builder() {}

        public Builder withZoom(org.teamvan.moneyshift.blueprint.components.ZoomLevel value) {
            this.zoom = value;
            return this;
        }

        public MapZoom build() {
            return new MapZoom(zoom);
        }
    }

    public static MapZoom of(
    ) {
        return new MapZoom(null);
    }
}
