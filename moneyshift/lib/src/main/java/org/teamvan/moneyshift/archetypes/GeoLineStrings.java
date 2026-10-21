// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Geospatial line strings with positions expressed in <a href="https://epsg.io/4326">EPSG:4326</a> latitude and longitude (North/East-positive degrees), and optional colors and radii.
 * 
 * Also known as "line strips" or "polylines".
 * 
 */
public record GeoLineStrings(
    /**
     * The line strings, expressed in <a href="https://epsg.io/4326">EPSG:4326</a> coordinates (North/East-positive degrees).
     */
    java.util.List<org.teamvan.moneyshift.components.GeoLineString> lineStrings,
    /**
     * Optional radii for the line strings.
     * 
     * *Note*: scene units radiii are interpreted as meters. Currently, the display scale only considers the latitude of
     * the first vertex of each line string (see <a href="https://github.com/rerun-io/rerun/issues/8013">this issue</a>).
     */
    java.util.List<org.teamvan.moneyshift.components.Radius> radii,
    /**
     * Optional colors for the line strings.
     * 
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors
) implements org.teamvan.moneyshift.Archetype {
    public GeoLineStrings {
        java.util.Objects.requireNonNull(lineStrings, "lineStrings");
        lineStrings = java.util.List.copyOf(lineStrings);
        if (radii != null) { radii = java.util.List.copyOf(radii); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.GeoLineStrings";
    public static final int NUM_COMPONENTS = 3;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LINE_STRINGS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GeoLineStrings:line_strings", "rerun.components.GeoLineString");

    /**
     * The line strings, expressed in <a href="https://epsg.io/4326">EPSG:4326</a> coordinates (North/East-positive degrees).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLineStrings() {
        return DESCRIPTOR_LINE_STRINGS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_RADII = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GeoLineStrings:radii", "rerun.components.Radius");

    /**
     * Optional radii for the line strings.
     * 
     * *Note*: scene units radiii are interpreted as meters. Currently, the display scale only considers the latitude of
     * the first vertex of each line string (see <a href="https://github.com/rerun-io/rerun/issues/8013">this issue</a>).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRadii() {
        return DESCRIPTOR_RADII;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GeoLineStrings:colors", "rerun.components.Color");

    /**
     * Optional colors for the line strings.
     * 
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColors() {
        return DESCRIPTOR_COLORS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorLineStrings());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorRadii(), descriptorColors());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of();
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.GeoLineString.batch(descriptorLineStrings(), allocator, lineStrings));
        if (radii != null) {
            batches.add(org.teamvan.moneyshift.components.Radius.batch(descriptorRadii(), allocator, radii));
        }
        if (colors != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColors(), allocator, colors));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.GeoLineString> lineStrings;
        private java.util.List<org.teamvan.moneyshift.components.Radius> radii;
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private Builder() {}

        public Builder withLineStrings(java.util.List<org.teamvan.moneyshift.components.GeoLineString> value) {
            this.lineStrings = java.util.List.copyOf(value);
            return this;
        }

        public Builder withLineStrings(org.teamvan.moneyshift.components.GeoLineString... value) {
            return withLineStrings(java.util.List.of(value));
        }

        public Builder withRadii(java.util.List<org.teamvan.moneyshift.components.Radius> value) {
            this.radii = java.util.List.copyOf(value);
            return this;
        }

        public Builder withRadii(org.teamvan.moneyshift.components.Radius... value) {
            return withRadii(java.util.List.of(value));
        }

        public Builder withColors(java.util.List<org.teamvan.moneyshift.components.Color> value) {
            this.colors = java.util.List.copyOf(value);
            return this;
        }

        public Builder withColors(org.teamvan.moneyshift.components.Color... value) {
            return withColors(java.util.List.of(value));
        }

        public GeoLineStrings build() {
            return new GeoLineStrings(lineStrings, radii, colors);
        }
    }

    public static GeoLineStrings of(
        java.util.List<org.teamvan.moneyshift.components.GeoLineString> lineStrings
    ) {
        return new GeoLineStrings(lineStrings, null, null);
    }
}
