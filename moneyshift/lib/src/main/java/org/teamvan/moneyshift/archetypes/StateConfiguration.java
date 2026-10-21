// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Define the style and mapping for state values in a state timeline view.
 * 
 * This archetype provides configuration for how state values are displayed.
 * It maps raw state values to display labels, colors, and visibility.
 * 
 * {@code values}, {@code labels}, {@code colors}, and {@code visible} are parallel arrays: the entry
 * at index {@code i} of each describes the same state value, and only the
 * per-index pairing is meaningful. The four arrays should have matching
 * length; any secondary array ({@code labels}, {@code colors}, {@code visible}) that is shorter
 * than {@code values} falls back to defaults for the missing entries.
 * 
 * It's generally recommended to log this type as static.
 * 
 * The underlying data needs to be logged to the same entity path using {@link org.teamvan.moneyshift.archetypes.StateChange}.
 * 
 */
public record StateConfiguration(
    /**
     * The raw state values that this configuration applies to.
     * 
     * Each entry defines a known state value. The order determines the mapping to
     * {@code labels}, {@code colors}, and {@code visible} (by index).
     */
    java.util.List<org.teamvan.moneyshift.components.Text> values,
    /**
     * Display labels for each state value.
     * 
     * If provided, the label at index {@code i} is shown instead of the raw value at index {@code i}.
     * If not provided or shorter than {@code values}, the raw value is used as the label.
     */
    java.util.List<org.teamvan.moneyshift.components.Text> labels,
    /**
     * Colors for each state value.
     * 
     * If provided, the color at index {@code i} is used for the state at index {@code i}.
     * If not provided, colors are assigned automatically from a built-in palette.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * Visibility for each state value.
     * 
     * If provided, the visibility at index {@code i} controls whether the state at index {@code i} is shown.
     * If not provided, all state values are visible.
     */
    java.util.List<org.teamvan.moneyshift.components.Visible> visible
) implements org.teamvan.moneyshift.Archetype {
    public StateConfiguration {
        if (values != null) { values = java.util.List.copyOf(values); }
        if (labels != null) { labels = java.util.List.copyOf(labels); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (visible != null) { visible = java.util.List.copyOf(visible); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.StateConfiguration";
    public static final int NUM_COMPONENTS = 4;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VALUES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "StateConfiguration:values", "rerun.components.Text");

    /**
     * The raw state values that this configuration applies to.
     * 
     * Each entry defines a known state value. The order determines the mapping to
     * {@code labels}, {@code colors}, and {@code visible} (by index).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorValues() {
        return DESCRIPTOR_VALUES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LABELS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "StateConfiguration:labels", "rerun.components.Text");

    /**
     * Display labels for each state value.
     * 
     * If provided, the label at index {@code i} is shown instead of the raw value at index {@code i}.
     * If not provided or shorter than {@code values}, the raw value is used as the label.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLabels() {
        return DESCRIPTOR_LABELS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "StateConfiguration:colors", "rerun.components.Color");

    /**
     * Colors for each state value.
     * 
     * If provided, the color at index {@code i} is used for the state at index {@code i}.
     * If not provided, colors are assigned automatically from a built-in palette.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColors() {
        return DESCRIPTOR_COLORS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VISIBLE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "StateConfiguration:visible", "rerun.components.Visible");

    /**
     * Visibility for each state value.
     * 
     * If provided, the visibility at index {@code i} controls whether the state at index {@code i} is shown.
     * If not provided, all state values are visible.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVisible() {
        return DESCRIPTOR_VISIBLE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorValues(), descriptorLabels(), descriptorColors(), descriptorVisible());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (values != null) {
            batches.add(org.teamvan.moneyshift.components.Text.batch(descriptorValues(), allocator, values));
        }
        if (labels != null) {
            batches.add(org.teamvan.moneyshift.components.Text.batch(descriptorLabels(), allocator, labels));
        }
        if (colors != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColors(), allocator, colors));
        }
        if (visible != null) {
            batches.add(org.teamvan.moneyshift.components.Visible.batch(descriptorVisible(), allocator, visible));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.Text> values;
        private java.util.List<org.teamvan.moneyshift.components.Text> labels;
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private java.util.List<org.teamvan.moneyshift.components.Visible> visible;
        private Builder() {}

        public Builder withValues(java.util.List<org.teamvan.moneyshift.components.Text> value) {
            this.values = java.util.List.copyOf(value);
            return this;
        }

        public Builder withValues(org.teamvan.moneyshift.components.Text... value) {
            return withValues(java.util.List.of(value));
        }

        public Builder withLabels(java.util.List<org.teamvan.moneyshift.components.Text> value) {
            this.labels = java.util.List.copyOf(value);
            return this;
        }

        public Builder withLabels(org.teamvan.moneyshift.components.Text... value) {
            return withLabels(java.util.List.of(value));
        }

        public Builder withColors(java.util.List<org.teamvan.moneyshift.components.Color> value) {
            this.colors = java.util.List.copyOf(value);
            return this;
        }

        public Builder withColors(org.teamvan.moneyshift.components.Color... value) {
            return withColors(java.util.List.of(value));
        }

        public Builder withVisible(java.util.List<org.teamvan.moneyshift.components.Visible> value) {
            this.visible = java.util.List.copyOf(value);
            return this;
        }

        public Builder withVisible(org.teamvan.moneyshift.components.Visible... value) {
            return withVisible(java.util.List.of(value));
        }

        public StateConfiguration build() {
            return new StateConfiguration(values, labels, colors, visible);
        }
    }

    public static StateConfiguration of(
    ) {
        return new StateConfiguration(null, null, null, null);
    }
}
