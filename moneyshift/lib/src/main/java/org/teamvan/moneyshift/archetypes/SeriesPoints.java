// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Define the style properties for one or more point series (scatter plot) in a chart.
 * 
 * This archetype only provides styling information.
 * Changes over time are supported for most but not all its fields (see respective fields for details),
 * it's generally recommended to log this type as static.
 * 
 * The underlying data needs to be logged to the same entity-path using {@link org.teamvan.moneyshift.archetypes.Scalars rerun::archetypes::Scalars}.
 * Dimensionality of the scalar arrays logged at each time point is assumed to be the same over time.
 * 
 */
public record SeriesPoints(
    /**
     * Color for the corresponding series.
     * 
     * May change over time, but can cause discontinuities in the line.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * What shape to use to represent the point
     * 
     * May change over time.
     */
    java.util.List<org.teamvan.moneyshift.components.MarkerShape> markers,
    /**
     * Display name of the series.
     * 
     * Used in the legend. Expected to be unchanging over time.
     */
    java.util.List<org.teamvan.moneyshift.components.Name> names,
    /**
     * Which lines are visible.
     * 
     * If not set, all line series on this entity are visible.
     * Unlike with the regular visibility property of the entire entity, any series that is hidden
     * via this property will still be visible in the legend.
     * 
     * May change over time.
     */
    java.util.List<org.teamvan.moneyshift.components.Visible> visibleSeries,
    /**
     * Sizes of the markers.
     * 
     * May change over time.
     */
    java.util.List<org.teamvan.moneyshift.components.MarkerSize> markerSizes
) implements org.teamvan.moneyshift.Archetype {
    public SeriesPoints {
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        java.util.Objects.requireNonNull(markers, "markers");
        markers = java.util.List.copyOf(markers);
        if (names != null) { names = java.util.List.copyOf(names); }
        if (visibleSeries != null) { visibleSeries = java.util.List.copyOf(visibleSeries); }
        if (markerSizes != null) { markerSizes = java.util.List.copyOf(markerSizes); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.SeriesPoints";
    public static final int NUM_COMPONENTS = 5;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (colors != null && !colors.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if (!markers.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("markers").append('=').append(org.teamvan.moneyshift.components.MarkerShape.COMPONENT_TYPE);
        }
        if (names != null && !names.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("names").append('=').append(org.teamvan.moneyshift.components.Name.COMPONENT_TYPE);
        }
        if (visibleSeries != null && !visibleSeries.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("visible_series").append('=').append(org.teamvan.moneyshift.components.Visible.COMPONENT_TYPE);
        }
        if (markerSizes != null && !markerSizes.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("marker_sizes").append('=').append(org.teamvan.moneyshift.components.MarkerSize.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (colors != null && !colors.isEmpty()) {
            org.teamvan.moneyshift.components.Color.writeColumnsStatic(w, "colors", colors);
        }
        if (!markers.isEmpty()) {
            org.teamvan.moneyshift.components.MarkerShape.writeColumnsStatic(w, "markers", markers);
        }
        if (names != null && !names.isEmpty()) {
            org.teamvan.moneyshift.components.Name.writeColumnsStatic(w, "names", names);
        }
        if (visibleSeries != null && !visibleSeries.isEmpty()) {
            org.teamvan.moneyshift.components.Visible.writeColumnsStatic(w, "visible_series", visibleSeries);
        }
        if (markerSizes != null && !markerSizes.isEmpty()) {
            org.teamvan.moneyshift.components.MarkerSize.writeColumnsStatic(w, "marker_sizes", markerSizes);
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private java.util.List<org.teamvan.moneyshift.components.MarkerShape> markers;
        private java.util.List<org.teamvan.moneyshift.components.Name> names;
        private java.util.List<org.teamvan.moneyshift.components.Visible> visibleSeries;
        private java.util.List<org.teamvan.moneyshift.components.MarkerSize> markerSizes;
        private Builder() {}

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

        public Builder withMarkers(java.util.List<org.teamvan.moneyshift.components.MarkerShape> value) {
            this.markers = java.util.List.copyOf(value);
            return this;
        }

        public Builder withMarkers(org.teamvan.moneyshift.components.MarkerShape... value) {
            return withMarkers(java.util.List.of(value));
        }

        public Builder withNames(java.util.List<org.teamvan.moneyshift.components.Name> value) {
            this.names = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withNames(java.lang.String... value) {
            java.util.List<org.teamvan.moneyshift.components.Name> values = new java.util.ArrayList<>(value.length);
            for (java.lang.String v : value) {
                values.add(new org.teamvan.moneyshift.components.Name(v));
            }
            return withNames(values);
        }

        public Builder withNames(org.teamvan.moneyshift.components.Name... value) {
            return withNames(java.util.List.of(value));
        }

        public Builder withVisibleSeries(java.util.List<org.teamvan.moneyshift.components.Visible> value) {
            this.visibleSeries = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withVisibleSeries(boolean... value) {
            java.util.List<org.teamvan.moneyshift.components.Visible> values = new java.util.ArrayList<>(value.length);
            for (boolean v : value) {
                values.add(new org.teamvan.moneyshift.components.Visible(v));
            }
            return withVisibleSeries(values);
        }

        public Builder withVisibleSeries(org.teamvan.moneyshift.components.Visible... value) {
            return withVisibleSeries(java.util.List.of(value));
        }

        public Builder withMarkerSizes(java.util.List<org.teamvan.moneyshift.components.MarkerSize> value) {
            this.markerSizes = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withMarkerSizes(float... value) {
            java.util.List<org.teamvan.moneyshift.components.MarkerSize> values = new java.util.ArrayList<>(value.length);
            for (float v : value) {
                values.add(new org.teamvan.moneyshift.components.MarkerSize(v));
            }
            return withMarkerSizes(values);
        }

        public Builder withMarkerSizes(org.teamvan.moneyshift.components.MarkerSize... value) {
            return withMarkerSizes(java.util.List.of(value));
        }

        public SeriesPoints build() {
            return new SeriesPoints(colors, markers, names, visibleSeries, markerSizes);
        }
    }

    public static SeriesPoints of(
        java.util.List<org.teamvan.moneyshift.components.MarkerShape> markers
    ) {
        return new SeriesPoints(null, markers, null, null, null);
    }
}
