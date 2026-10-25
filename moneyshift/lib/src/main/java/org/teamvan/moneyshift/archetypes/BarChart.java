// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A bar chart.
 * 
 * The bar heights will be the provided values, and the x coordinates of the bars will be the provided abscissa or default to the index of the provided values.
 * 
 */
public record BarChart(
    /**
     * The values. Should always be a 1-dimensional tensor (i.e. a vector).
     */
    org.teamvan.moneyshift.components.TensorData values,
    /**
     * The color of the bar chart
     */
    org.teamvan.moneyshift.components.Color color,
    /**
     * The abscissa corresponding to each value. Should be a 1-dimensional tensor (i.e. a vector) in same length as values.
     */
    org.teamvan.moneyshift.components.TensorData abscissa,
    /**
     * The width of the bins, defined in x-axis units and defaults to 1. Should be a 1-dimensional tensor (i.e. a vector) in same length as values.
     */
    java.util.List<org.teamvan.moneyshift.components.Length> widths
) implements org.teamvan.moneyshift.Archetype {
    public BarChart {
        java.util.Objects.requireNonNull(values, "values");
        if (widths != null) { widths = java.util.List.copyOf(widths); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.BarChart";
    public static final int NUM_COMPONENTS = 4;

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
            components.append("values").append('=').append(org.teamvan.moneyshift.components.TensorData.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("color").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if ((mask & 4L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("abscissa").append('=').append(org.teamvan.moneyshift.components.TensorData.COMPONENT_TYPE);
        }
        if ((mask & 8L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("widths").append('=').append(org.teamvan.moneyshift.components.Length.COMPONENT_TYPE);
        }
        java.lang.String joined = components.toString();
        cachedComponents = new ComponentsCache(mask, joined);
        return joined;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        long mask = 0L;
        if (true) mask |= 1L;
        if (color != null) mask |= 2L;
        if (abscissa != null) mask |= 4L;
        if (widths != null && !widths.isEmpty()) mask |= 8L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (true) {
            org.teamvan.moneyshift.components.TensorData.writeColumnsStatic(w, "values", java.util.List.of(values));
        }
        if (color != null) {
            org.teamvan.moneyshift.components.Color.writeColumnsStatic(w, "color", java.util.List.of(color));
        }
        if (abscissa != null) {
            org.teamvan.moneyshift.components.TensorData.writeColumnsStatic(w, "abscissa", java.util.List.of(abscissa));
        }
        if (widths != null && !widths.isEmpty()) {
            org.teamvan.moneyshift.components.Length.writeColumnsStatic(w, "widths", widths);
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.TensorData values;
        private org.teamvan.moneyshift.components.Color color;
        private org.teamvan.moneyshift.components.TensorData abscissa;
        private java.util.List<org.teamvan.moneyshift.components.Length> widths;
        private Builder() {}

        public Builder withValues(org.teamvan.moneyshift.components.TensorData value) {
            this.values = value;
            return this;
        }

        public Builder withColor(org.teamvan.moneyshift.components.Color value) {
            this.color = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withColor(long value) {
            return withColor(new org.teamvan.moneyshift.components.Color(value));
        }

        public Builder withAbscissa(org.teamvan.moneyshift.components.TensorData value) {
            this.abscissa = value;
            return this;
        }

        public Builder withWidths(java.util.List<org.teamvan.moneyshift.components.Length> value) {
            this.widths = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withWidths(float... value) {
            java.util.List<org.teamvan.moneyshift.components.Length> values = new java.util.ArrayList<>(value.length);
            for (float v : value) {
                values.add(new org.teamvan.moneyshift.components.Length(v));
            }
            return withWidths(values);
        }

        public Builder withWidths(org.teamvan.moneyshift.components.Length... value) {
            return withWidths(java.util.List.of(value));
        }

        public BarChart build() {
            return new BarChart(values, color, abscissa, widths);
        }
    }

    public static BarChart of(
        org.teamvan.moneyshift.components.TensorData values
    ) {
        return new BarChart(values, null, null, null);
    }
}
