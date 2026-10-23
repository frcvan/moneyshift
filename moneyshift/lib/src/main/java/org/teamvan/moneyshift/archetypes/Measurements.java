// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * One or more scalar measurements, each with a variance and a unit.
 * 
 * Use this for sensors that report a value together with its uncertainty:
 * pressure, temperature, illuminance, relative humidity, range, and so on.
 * 
 * In a {@code rerun::blueprint::views::TimeSeriesView} each series is drawn as a line with a translucent
 * band around it, one standard deviation (the square root of the variance) wide in each direction.
 * Leave {@code variances} unset if the uncertainty is unknown.
 * 
 * The current timeline value is used for the time/X-axis, so measurements should not be
 * static. Number of values per timestamp is expected to be the same over time.
 * 
 * Unlike {@link org.teamvan.moneyshift.archetypes.Scalars rerun::archetypes::Scalars}, this archetype carries its own styling, so values and style
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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (!values.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("values").append('=').append(org.teamvan.moneyshift.components.Scalar.COMPONENT_TYPE);
        }
        if (variances != null && !variances.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("variances").append('=').append(org.teamvan.moneyshift.components.Variance.COMPONENT_TYPE);
        }
        if (units != null && !units.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("units").append('=').append(org.teamvan.moneyshift.components.Unit.COMPONENT_TYPE);
        }
        if (colors != null && !colors.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if (widths != null && !widths.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("widths").append('=').append(org.teamvan.moneyshift.components.StrokeWidth.COMPONENT_TYPE);
        }
        if (names != null && !names.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("names").append('=').append(org.teamvan.moneyshift.components.Name.COMPONENT_TYPE);
        }
        if (visibleSeries != null && !visibleSeries.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("visible_series").append('=').append(org.teamvan.moneyshift.components.Visible.COMPONENT_TYPE);
        }
        if (aggregationPolicy != null) {
            if (components.length() > 0) components.append(',');
            components.append("aggregation_policy").append('=').append(org.teamvan.moneyshift.components.AggregationPolicy.COMPONENT_TYPE);
        }
        if (interpolationMode != null) {
            if (components.length() > 0) components.append(',');
            components.append("interpolation_mode").append('=').append(org.teamvan.moneyshift.components.InterpolationMode.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (!values.isEmpty()) {
            org.teamvan.moneyshift.components.Scalar.writeColumnsStatic(w, "values", values);
        }
        if (variances != null && !variances.isEmpty()) {
            org.teamvan.moneyshift.components.Variance.writeColumnsStatic(w, "variances", variances);
        }
        if (units != null && !units.isEmpty()) {
            org.teamvan.moneyshift.components.Unit.writeColumnsStatic(w, "units", units);
        }
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

        /** Overload that takes the {@code double} value(s) directly. */
        public Builder withValues(double... value) {
            java.util.List<org.teamvan.moneyshift.components.Scalar> values = new java.util.ArrayList<>(value.length);
            for (double v : value) {
                values.add(new org.teamvan.moneyshift.components.Scalar(v));
            }
            return withValues(values);
        }

        public Builder withValues(org.teamvan.moneyshift.components.Scalar... value) {
            return withValues(java.util.List.of(value));
        }

        public Builder withVariances(java.util.List<org.teamvan.moneyshift.components.Variance> value) {
            this.variances = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code double} value(s) directly. */
        public Builder withVariances(double... value) {
            java.util.List<org.teamvan.moneyshift.components.Variance> values = new java.util.ArrayList<>(value.length);
            for (double v : value) {
                values.add(new org.teamvan.moneyshift.components.Variance(v));
            }
            return withVariances(values);
        }

        public Builder withVariances(org.teamvan.moneyshift.components.Variance... value) {
            return withVariances(java.util.List.of(value));
        }

        public Builder withUnits(java.util.List<org.teamvan.moneyshift.components.Unit> value) {
            this.units = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withUnits(java.lang.String... value) {
            java.util.List<org.teamvan.moneyshift.components.Unit> values = new java.util.ArrayList<>(value.length);
            for (java.lang.String v : value) {
                values.add(new org.teamvan.moneyshift.components.Unit(v));
            }
            return withUnits(values);
        }

        public Builder withUnits(org.teamvan.moneyshift.components.Unit... value) {
            return withUnits(java.util.List.of(value));
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
