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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VALUES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "BarChart:values", "rerun.components.TensorData");

    /**
     * The values. Should always be a 1-dimensional tensor (i.e. a vector).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorValues() {
        return DESCRIPTOR_VALUES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLOR = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "BarChart:color", "rerun.components.Color");

    /**
     * The color of the bar chart
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColor() {
        return DESCRIPTOR_COLOR;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ABSCISSA = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "BarChart:abscissa", "rerun.components.TensorData");

    /**
     * The abscissa corresponding to each value. Should be a 1-dimensional tensor (i.e. a vector) in same length as values.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorAbscissa() {
        return DESCRIPTOR_ABSCISSA;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_WIDTHS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "BarChart:widths", "rerun.components.Length");

    /**
     * The width of the bins, defined in x-axis units and defaults to 1. Should be a 1-dimensional tensor (i.e. a vector) in same length as values.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorWidths() {
        return DESCRIPTOR_WIDTHS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorValues());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorColor(), descriptorAbscissa(), descriptorWidths());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.TensorData.batch(descriptorValues(), allocator, values));
        if (color != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColor(), allocator, color));
        }
        if (abscissa != null) {
            batches.add(org.teamvan.moneyshift.components.TensorData.batch(descriptorAbscissa(), allocator, abscissa));
        }
        if (widths != null) {
            batches.add(org.teamvan.moneyshift.components.Length.batch(descriptorWidths(), allocator, widths));
        }
        return batches;
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

        public Builder withAbscissa(org.teamvan.moneyshift.components.TensorData value) {
            this.abscissa = value;
            return this;
        }

        public Builder withWidths(java.util.List<org.teamvan.moneyshift.components.Length> value) {
            this.widths = java.util.List.copyOf(value);
            return this;
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
