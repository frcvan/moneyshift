// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configuration for the background map of the map view.
 */
public record MapBackground(
    /**
     * Map provider and style to use.
     * 
     * **Note**: Requires a Mapbox API key in the {@code RERUN_MAPBOX_ACCESS_TOKEN} environment variable.
     */
    org.teamvan.moneyshift.blueprint.components.MapProvider provider
) implements org.teamvan.moneyshift.Archetype {
    public MapBackground {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.MapBackground";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_PROVIDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "MapBackground:provider", "rerun.blueprint.components.MapProvider");

    /**
     * Map provider and style to use.
     * 
     * **Note**: Requires a Mapbox API key in the {@code RERUN_MAPBOX_ACCESS_TOKEN} environment variable.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorProvider() {
        return DESCRIPTOR_PROVIDER;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorProvider());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (provider != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.MapProvider.batch(descriptorProvider(), allocator, provider));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.MapProvider provider;
        private Builder() {}

        public Builder withProvider(org.teamvan.moneyshift.blueprint.components.MapProvider value) {
            this.provider = value;
            return this;
        }

        public MapBackground build() {
            return new MapBackground(provider);
        }
    }

    public static MapBackground of(
    ) {
        return new MapBackground(null);
    }
}
