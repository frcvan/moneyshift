// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Define the style properties for one or more line series in a chart.
 * 
 * This archetype only provides styling information.
 * Changes over time are supported for most but not all its fields (see respective fields for details),
 * it's generally recommended to log this type as static.
 * 
 * The underlying data needs to be logged to the same entity-path using {@link org.teamvan.moneyshift.archetypes.Scalars}.
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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SeriesLines:colors", "rerun.components.Color");

    /**
     * Color for the corresponding series.
     * 
     * May change over time, but can cause discontinuities in the line.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColors() {
        return DESCRIPTOR_COLORS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_WIDTHS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SeriesLines:widths", "rerun.components.StrokeWidth");

    /**
     * Stroke width for the corresponding series.
     * 
     * May change over time, but can cause discontinuities in the line.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorWidths() {
        return DESCRIPTOR_WIDTHS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_NAMES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SeriesLines:names", "rerun.components.Name");

    /**
     * Display name of the series.
     * 
     * Used in the legend. Expected to be unchanging over time.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorNames() {
        return DESCRIPTOR_NAMES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VISIBLE_SERIES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SeriesLines:visible_series", "rerun.components.Visible");

    /**
     * Which lines are visible.
     * 
     * If not set, all line series on this entity are visible.
     * Unlike with the regular visibility property of the entire entity, any series that is hidden
     * via this property will still be visible in the legend.
     * 
     * May change over time, but can cause discontinuities in the line.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVisibleSeries() {
        return DESCRIPTOR_VISIBLE_SERIES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_AGGREGATION_POLICY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SeriesLines:aggregation_policy", "rerun.components.AggregationPolicy");

    /**
     * Configures the zoom-dependent scalar aggregation.
     * 
     * This is done only if steps on the X axis go below a single pixel,
     * i.e. a single pixel covers more than one tick worth of data. It can greatly improve performance
     * (and readability) in such situations as it prevents overdraw.
     * 
     * Expected to be unchanging over time.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorAggregationPolicy() {
        return DESCRIPTOR_AGGREGATION_POLICY;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_INTERPOLATION_MODE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SeriesLines:interpolation_mode", "rerun.components.InterpolationMode");

    /**
     * Specifies how values between data points are interpolated.
     * 
     * Defaults to linear interpolation. Use one of the {@code Step*} variants for a stepped (staircase) line.
     * 
     * Expected to be unchanging over time.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorInterpolationMode() {
        return DESCRIPTOR_INTERPOLATION_MODE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorColors(), descriptorWidths(), descriptorNames(), descriptorVisibleSeries(), descriptorAggregationPolicy(), descriptorInterpolationMode());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (colors != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColors(), allocator, colors));
        }
        if (widths != null) {
            batches.add(org.teamvan.moneyshift.components.StrokeWidth.batch(descriptorWidths(), allocator, widths));
        }
        if (names != null) {
            batches.add(org.teamvan.moneyshift.components.Name.batch(descriptorNames(), allocator, names));
        }
        if (visibleSeries != null) {
            batches.add(org.teamvan.moneyshift.components.Visible.batch(descriptorVisibleSeries(), allocator, visibleSeries));
        }
        if (aggregationPolicy != null) {
            batches.add(org.teamvan.moneyshift.components.AggregationPolicy.batch(descriptorAggregationPolicy(), allocator, aggregationPolicy));
        }
        if (interpolationMode != null) {
            batches.add(org.teamvan.moneyshift.components.InterpolationMode.batch(descriptorInterpolationMode(), allocator, interpolationMode));
        }
        return batches;
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
