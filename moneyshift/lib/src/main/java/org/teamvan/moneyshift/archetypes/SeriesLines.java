// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Define the style properties for one or more line series in a chart.
 * 
 * This archetype only provides styling information.
 * Changes over time are supported for most but not all its fields (see respective fields for details),
 * it's generally recommended to log this type as static.
 * 
 * The underlying data needs to be logged to the same entity-path using {@link org.teamvan.moneyshift.archetypes.Scalars rerun::archetypes::Scalars}.
 * Dimensionality of the scalar arrays logged at each time point is assumed to be the same over time.
 * 
 */
public record SeriesLines(
    /**
     * Color for the corresponding series.
     * 
     * May change over time, but can cause discontinuities in the line.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * Stroke width for the corresponding series.
     * 
     * May change over time, but can cause discontinuities in the line.
     */
    java.util.List<org.teamvan.moneyshift.components.StrokeWidth> widths,
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
     * May change over time, but can cause discontinuities in the line.
     */
    java.util.List<org.teamvan.moneyshift.components.Visible> visibleSeries,
    /**
     * Configures the zoom-dependent scalar aggregation.
     * 
     * This is done only if steps on the X axis go below a single pixel,
     * i.e. a single pixel covers more than one tick worth of data. It can greatly improve performance
     * (and readability) in such situations as it prevents overdraw.
     * 
     * Expected to be unchanging over time.
     */
    org.teamvan.moneyshift.components.AggregationPolicy aggregationPolicy,
    /**
     * Specifies how values between data points are interpolated.
     * 
     * Defaults to linear interpolation. Use one of the {@code Step*} variants for a stepped (staircase) line.
     * 
     * Expected to be unchanging over time.
     */
    org.teamvan.moneyshift.components.InterpolationMode interpolationMode
) implements org.teamvan.moneyshift.Archetype {
    public SeriesLines {
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (widths != null) { widths = java.util.List.copyOf(widths); }
        if (names != null) { names = java.util.List.copyOf(names); }
        if (visibleSeries != null) { visibleSeries = java.util.List.copyOf(visibleSeries); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.SeriesLines";
    public static final int NUM_COMPONENTS = 6;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    private static final class ComponentsCache {
        final long mask;
        final java.lang.String components;

        ComponentsCache(long mask, java.lang.String components) {
            this.mask = mask;
            this.components = components;
        }
    }

    private static volatile ComponentsCache cachedComponents;

    private static java.lang.String componentsFor(long mask) {
        ComponentsCache cached = cachedComponents;
        if (cached != null && cached.mask == mask) {
            return cached.components;
        }
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if ((mask & 1L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("widths").append('=').append(org.teamvan.moneyshift.components.StrokeWidth.COMPONENT_TYPE);
        }
        if ((mask & 4L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("names").append('=').append(org.teamvan.moneyshift.components.Name.COMPONENT_TYPE);
        }
        if ((mask & 8L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("visible_series").append('=').append(org.teamvan.moneyshift.components.Visible.COMPONENT_TYPE);
        }
        if ((mask & 16L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("aggregation_policy").append('=').append(org.teamvan.moneyshift.components.AggregationPolicy.COMPONENT_TYPE);
        }
        if ((mask & 32L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("interpolation_mode").append('=').append(org.teamvan.moneyshift.components.InterpolationMode.COMPONENT_TYPE);
        }
        java.lang.String joined = components.toString();
        cachedComponents = new ComponentsCache(mask, joined);
        return joined;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        long mask = 0L;
        if (colors != null && !colors.isEmpty()) mask |= 1L;
        if (widths != null && !widths.isEmpty()) mask |= 2L;
        if (names != null && !names.isEmpty()) mask |= 4L;
        if (visibleSeries != null && !visibleSeries.isEmpty()) mask |= 8L;
        if (aggregationPolicy != null) mask |= 16L;
        if (interpolationMode != null) mask |= 32L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (colors != null && !colors.isEmpty()) {
            org.teamvan.moneyshift.components.Color.writeColumnsStatic(w, "colors", colors);
        }
        if (widths != null && !widths.isEmpty()) {
            org.teamvan.moneyshift.components.StrokeWidth.writeColumnsStatic(w, "widths", widths);
        }
        if (names != null && !names.isEmpty()) {
            org.teamvan.moneyshift.components.Name.writeColumnsStatic(w, "names", names);
        }
        if (visibleSeries != null && !visibleSeries.isEmpty()) {
            org.teamvan.moneyshift.components.Visible.writeColumnsStatic(w, "visible_series", visibleSeries);
        }
        if (aggregationPolicy != null) {
            org.teamvan.moneyshift.components.AggregationPolicy.writeColumnsStatic(w, "aggregation_policy", java.util.List.of(aggregationPolicy));
        }
        if (interpolationMode != null) {
            org.teamvan.moneyshift.components.InterpolationMode.writeColumnsStatic(w, "interpolation_mode", java.util.List.of(interpolationMode));
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private java.util.List<org.teamvan.moneyshift.components.StrokeWidth> widths;
        private java.util.List<org.teamvan.moneyshift.components.Name> names;
        private java.util.List<org.teamvan.moneyshift.components.Visible> visibleSeries;
        private org.teamvan.moneyshift.components.AggregationPolicy aggregationPolicy;
        private org.teamvan.moneyshift.components.InterpolationMode interpolationMode;
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

        public Builder withWidths(java.util.List<org.teamvan.moneyshift.components.StrokeWidth> value) {
            this.widths = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withWidths(float... value) {
            java.util.List<org.teamvan.moneyshift.components.StrokeWidth> values = new java.util.ArrayList<>(value.length);
            for (float v : value) {
                values.add(new org.teamvan.moneyshift.components.StrokeWidth(v));
            }
            return withWidths(values);
        }

        public Builder withWidths(org.teamvan.moneyshift.components.StrokeWidth... value) {
            return withWidths(java.util.List.of(value));
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

        public Builder withAggregationPolicy(org.teamvan.moneyshift.components.AggregationPolicy value) {
            this.aggregationPolicy = value;
            return this;
        }

        public Builder withInterpolationMode(org.teamvan.moneyshift.components.InterpolationMode value) {
            this.interpolationMode = value;
            return this;
        }

        public SeriesLines build() {
            return new SeriesLines(colors, widths, names, visibleSeries, aggregationPolicy, interpolationMode);
        }
    }

    public static SeriesLines of(
    ) {
        return new SeriesLines(null, null, null, null, null, null);
    }
}
