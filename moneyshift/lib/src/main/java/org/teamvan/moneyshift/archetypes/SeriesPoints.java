// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Define the style properties for one or more point series (scatter plot) in a chart.
 * 
 * This archetype only provides styling information.
 * Changes over time are supported for most but not all its fields (see respective fields for details),
 * it's generally recommended to log this type as static.
 * 
 * The underlying data needs to be logged to the same entity-path using {@link org.teamvan.moneyshift.archetypes.Scalars}.
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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SeriesPoints:colors", "rerun.components.Color");

    /**
     * Color for the corresponding series.
     * 
     * May change over time, but can cause discontinuities in the line.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColors() {
        return DESCRIPTOR_COLORS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MARKERS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SeriesPoints:markers", "rerun.components.MarkerShape");

    /**
     * What shape to use to represent the point
     * 
     * May change over time.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMarkers() {
        return DESCRIPTOR_MARKERS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_NAMES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SeriesPoints:names", "rerun.components.Name");

    /**
     * Display name of the series.
     * 
     * Used in the legend. Expected to be unchanging over time.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorNames() {
        return DESCRIPTOR_NAMES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VISIBLE_SERIES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SeriesPoints:visible_series", "rerun.components.Visible");

    /**
     * Which lines are visible.
     * 
     * If not set, all line series on this entity are visible.
     * Unlike with the regular visibility property of the entire entity, any series that is hidden
     * via this property will still be visible in the legend.
     * 
     * May change over time.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVisibleSeries() {
        return DESCRIPTOR_VISIBLE_SERIES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MARKER_SIZES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SeriesPoints:marker_sizes", "rerun.components.MarkerSize");

    /**
     * Sizes of the markers.
     * 
     * May change over time.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMarkerSizes() {
        return DESCRIPTOR_MARKER_SIZES;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorMarkers());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorColors(), descriptorNames(), descriptorVisibleSeries(), descriptorMarkerSizes());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (colors != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColors(), allocator, colors));
        }
        batches.add(org.teamvan.moneyshift.components.MarkerShape.batch(descriptorMarkers(), allocator, markers));
        if (names != null) {
            batches.add(org.teamvan.moneyshift.components.Name.batch(descriptorNames(), allocator, names));
        }
        if (visibleSeries != null) {
            batches.add(org.teamvan.moneyshift.components.Visible.batch(descriptorVisibleSeries(), allocator, visibleSeries));
        }
        if (markerSizes != null) {
            batches.add(org.teamvan.moneyshift.components.MarkerSize.batch(descriptorMarkerSizes(), allocator, markerSizes));
        }
        return batches;
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
