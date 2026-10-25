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
 * The underlying data needs to be logged to the same entity path using {@link org.teamvan.moneyshift.archetypes.StateChange rerun::archetypes::StateChange}.
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
            components.append("values").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("labels").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        if ((mask & 4L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if ((mask & 8L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("visible").append('=').append(org.teamvan.moneyshift.components.Visible.COMPONENT_TYPE);
        }
        java.lang.String joined = components.toString();
        cachedComponents = new ComponentsCache(mask, joined);
        return joined;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        long mask = 0L;
        if (values != null && !values.isEmpty()) mask |= 1L;
        if (labels != null && !labels.isEmpty()) mask |= 2L;
        if (colors != null && !colors.isEmpty()) mask |= 4L;
        if (visible != null && !visible.isEmpty()) mask |= 8L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (values != null && !values.isEmpty()) {
            org.teamvan.moneyshift.components.Text.writeColumnsStatic(w, "values", values);
        }
        if (labels != null && !labels.isEmpty()) {
            org.teamvan.moneyshift.components.Text.writeColumnsStatic(w, "labels", labels);
        }
        if (colors != null && !colors.isEmpty()) {
            org.teamvan.moneyshift.components.Color.writeColumnsStatic(w, "colors", colors);
        }
        if (visible != null && !visible.isEmpty()) {
            org.teamvan.moneyshift.components.Visible.writeColumnsStatic(w, "visible", visible);
        }
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

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withValues(java.lang.String... value) {
            java.util.List<org.teamvan.moneyshift.components.Text> values = new java.util.ArrayList<>(value.length);
            for (java.lang.String v : value) {
                values.add(new org.teamvan.moneyshift.components.Text(v));
            }
            return withValues(values);
        }

        public Builder withValues(org.teamvan.moneyshift.components.Text... value) {
            return withValues(java.util.List.of(value));
        }

        public Builder withLabels(java.util.List<org.teamvan.moneyshift.components.Text> value) {
            this.labels = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withLabels(java.lang.String... value) {
            java.util.List<org.teamvan.moneyshift.components.Text> values = new java.util.ArrayList<>(value.length);
            for (java.lang.String v : value) {
                values.add(new org.teamvan.moneyshift.components.Text(v));
            }
            return withLabels(values);
        }

        public Builder withLabels(org.teamvan.moneyshift.components.Text... value) {
            return withLabels(java.util.List.of(value));
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

        public Builder withVisible(java.util.List<org.teamvan.moneyshift.components.Visible> value) {
            this.visible = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withVisible(boolean... value) {
            java.util.List<org.teamvan.moneyshift.components.Visible> values = new java.util.ArrayList<>(value.length);
            for (boolean v : value) {
                values.add(new org.teamvan.moneyshift.components.Visible(v));
            }
            return withVisible(values);
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
