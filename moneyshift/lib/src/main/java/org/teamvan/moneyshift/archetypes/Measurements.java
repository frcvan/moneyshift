// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * One or more scalar measurements, each with a variance and a unit.
 * 
 * Use this for sensors that report a value together with its uncertainty:
 * pressure, temperature, illuminance, relative humidity, range, and so on.
 * 
 * In a {@link org.teamvan.moneyshift.blueprint.views.TimeSeriesView} each series is drawn as a line with a translucent
 * band around it, one standard deviation (the square root of the variance) wide in each direction.
 * Leave {@code variances} unset if the uncertainty is unknown.
 * 
 * The current timeline value is used for the time/X-axis, so measurements should not be
 * static. Number of values per timestamp is expected to be the same over time.
 * 
 * Unlike {@link org.teamvan.moneyshift.archetypes.Scalars}, this archetype carries its own styling, so values and style
 * are logged together.
 * Changes over time are supported for most but not all styling fields (see respective fields for details).
 * 
 */
public record Measurements(
    /**
     * The measured scalar values.
     */
    java.util.List<org.teamvan.moneyshift.components.Scalar> values,
    /**
     * Variances of the measurements (σ², in the units of {@code values} squared).
     * 
     * When set, length is expected to match {@code values}.
     */
    java.util.List<org.teamvan.moneyshift.components.Variance> variances,
    /**
     * Units of the measurements, shown in the legend and in tooltips.
     * 
     * When set, length is expected to match {@code values}.
     * Expected to be unchanging over time.
     */
    java.util.List<org.teamvan.moneyshift.components.Unit> units,
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
     * Which series are visible.
     * 
     * If not set, all series on this entity are visible.
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
    public Measurements {
        java.util.Objects.requireNonNull(values, "values");
        values = java.util.List.copyOf(values);
        if (variances != null) { variances = java.util.List.copyOf(variances); }
        if (units != null) { units = java.util.List.copyOf(units); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (widths != null) { widths = java.util.List.copyOf(widths); }
        if (names != null) { names = java.util.List.copyOf(names); }
        if (visibleSeries != null) { visibleSeries = java.util.List.copyOf(visibleSeries); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Measurements";
    public static final int NUM_COMPONENTS = 9;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VALUES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Measurements:values", "rerun.components.Scalar");

    /**
     * The measured scalar values.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorValues() {
        return DESCRIPTOR_VALUES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VARIANCES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Measurements:variances", "rerun.components.Variance");

    /**
     * Variances of the measurements (σ², in the units of {@code values} squared).
     * 
     * When set, length is expected to match {@code values}.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVariances() {
        return DESCRIPTOR_VARIANCES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_UNITS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Measurements:units", "rerun.components.Unit");

    /**
     * Units of the measurements, shown in the legend and in tooltips.
     * 
     * When set, length is expected to match {@code values}.
     * Expected to be unchanging over time.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorUnits() {
        return DESCRIPTOR_UNITS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Measurements:colors", "rerun.components.Color");

    /**
     * Color for the corresponding series.
     * 
     * May change over time, but can cause discontinuities in the line.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColors() {
        return DESCRIPTOR_COLORS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_WIDTHS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Measurements:widths", "rerun.components.StrokeWidth");

    /**
     * Stroke width for the corresponding series.
     * 
     * May change over time, but can cause discontinuities in the line.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorWidths() {
        return DESCRIPTOR_WIDTHS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_NAMES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Measurements:names", "rerun.components.Name");

    /**
     * Display name of the series.
     * 
     * Used in the legend. Expected to be unchanging over time.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorNames() {
        return DESCRIPTOR_NAMES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VISIBLE_SERIES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Measurements:visible_series", "rerun.components.Visible");

    /**
     * Which series are visible.
     * 
     * If not set, all series on this entity are visible.
     * Unlike with the regular visibility property of the entire entity, any series that is hidden
     * via this property will still be visible in the legend.
     * 
     * May change over time, but can cause discontinuities in the line.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVisibleSeries() {
        return DESCRIPTOR_VISIBLE_SERIES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_AGGREGATION_POLICY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Measurements:aggregation_policy", "rerun.components.AggregationPolicy");

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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_INTERPOLATION_MODE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Measurements:interpolation_mode", "rerun.components.InterpolationMode");

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
        return java.util.List.of(descriptorValues());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorVariances());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorUnits(), descriptorColors(), descriptorWidths(), descriptorNames(), descriptorVisibleSeries(), descriptorAggregationPolicy(), descriptorInterpolationMode());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.Scalar.batch(descriptorValues(), allocator, values));
        if (variances != null) {
            batches.add(org.teamvan.moneyshift.components.Variance.batch(descriptorVariances(), allocator, variances));
        }
        if (units != null) {
            batches.add(org.teamvan.moneyshift.components.Unit.batch(descriptorUnits(), allocator, units));
        }
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
        private java.util.List<org.teamvan.moneyshift.components.Scalar> values;
        private java.util.List<org.teamvan.moneyshift.components.Variance> variances;
        private java.util.List<org.teamvan.moneyshift.components.Unit> units;
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private java.util.List<org.teamvan.moneyshift.components.StrokeWidth> widths;
        private java.util.List<org.teamvan.moneyshift.components.Name> names;
        private java.util.List<org.teamvan.moneyshift.components.Visible> visibleSeries;
        private org.teamvan.moneyshift.components.AggregationPolicy aggregationPolicy;
        private org.teamvan.moneyshift.components.InterpolationMode interpolationMode;
        private Builder() {}

        public Builder withValues(java.util.List<org.teamvan.moneyshift.components.Scalar> value) {
            this.values = java.util.List.copyOf(value);
            return this;
        }

        public Builder withValues(org.teamvan.moneyshift.components.Scalar... value) {
            return withValues(java.util.List.of(value));
        }

        public Builder withVariances(java.util.List<org.teamvan.moneyshift.components.Variance> value) {
            this.variances = java.util.List.copyOf(value);
            return this;
        }

        public Builder withVariances(org.teamvan.moneyshift.components.Variance... value) {
            return withVariances(java.util.List.of(value));
        }

        public Builder withUnits(java.util.List<org.teamvan.moneyshift.components.Unit> value) {
            this.units = java.util.List.copyOf(value);
            return this;
        }

        public Builder withUnits(org.teamvan.moneyshift.components.Unit... value) {
            return withUnits(java.util.List.of(value));
        }

        public Builder withColors(java.util.List<org.teamvan.moneyshift.components.Color> value) {
            this.colors = java.util.List.copyOf(value);
            return this;
        }

        public Builder withColors(org.teamvan.moneyshift.components.Color... value) {
            return withColors(java.util.List.of(value));
        }

        public Builder withWidths(java.util.List<org.teamvan.moneyshift.components.StrokeWidth> value) {
            this.widths = java.util.List.copyOf(value);
            return this;
        }

        public Builder withWidths(org.teamvan.moneyshift.components.StrokeWidth... value) {
            return withWidths(java.util.List.of(value));
        }

        public Builder withNames(java.util.List<org.teamvan.moneyshift.components.Name> value) {
            this.names = java.util.List.copyOf(value);
            return this;
        }

        public Builder withNames(org.teamvan.moneyshift.components.Name... value) {
            return withNames(java.util.List.of(value));
        }

        public Builder withVisibleSeries(java.util.List<org.teamvan.moneyshift.components.Visible> value) {
            this.visibleSeries = java.util.List.copyOf(value);
            return this;
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

        public Measurements build() {
            return new Measurements(values, variances, units, colors, widths, names, visibleSeries, aggregationPolicy, interpolationMode);
        }
    }

    public static Measurements of(
        java.util.List<org.teamvan.moneyshift.components.Scalar> values
    ) {
        return new Measurements(values, null, null, null, null, null, null, null, null);
    }
}
