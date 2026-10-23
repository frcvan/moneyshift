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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (!lineStrings.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("line_strings").append('=').append(org.teamvan.moneyshift.components.GeoLineString.COMPONENT_TYPE);
        }
        if (radii != null && !radii.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("radii").append('=').append(org.teamvan.moneyshift.components.Radius.COMPONENT_TYPE);
        }
        if (colors != null && !colors.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (!lineStrings.isEmpty()) {
            org.teamvan.moneyshift.components.GeoLineString.writeColumnsStatic(w, "line_strings", lineStrings);
        }
        if (radii != null && !radii.isEmpty()) {
            org.teamvan.moneyshift.components.Radius.writeColumnsStatic(w, "radii", radii);
        }
        if (colors != null && !colors.isEmpty()) {
            org.teamvan.moneyshift.components.Color.writeColumnsStatic(w, "colors", colors);
        }
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

        /** Overload that takes the elements of a single component directly. */
        public Builder withLineStrings(org.teamvan.moneyshift.encodings.DVec2D... value) {
            return withLineStrings(java.util.List.of(new org.teamvan.moneyshift.components.GeoLineString(java.util.List.of(value))));
        }

        public Builder withLineStrings(org.teamvan.moneyshift.components.GeoLineString... value) {
            return withLineStrings(java.util.List.of(value));
        }

        public Builder withRadii(java.util.List<org.teamvan.moneyshift.components.Radius> value) {
            this.radii = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withRadii(float... value) {
            java.util.List<org.teamvan.moneyshift.components.Radius> values = new java.util.ArrayList<>(value.length);
            for (float v : value) {
                values.add(new org.teamvan.moneyshift.components.Radius(v));
            }
            return withRadii(values);
        }

        public Builder withRadii(org.teamvan.moneyshift.components.Radius... value) {
            return withRadii(java.util.List.of(value));
        }

        public Builder withColors(java.util.List<org.teamvan.moneyshift.components.Color> value) {
            this.colors = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withColors(long... value) {
            java.util.List<org.teamvan.moneyshift.components.Color> values = new java.util.ArrayList<>(value.length);
            for (long v : value) {
                values.add(new org.teamvan.moneyshift.components.Color(v));
            }
            return withColors(values);
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
