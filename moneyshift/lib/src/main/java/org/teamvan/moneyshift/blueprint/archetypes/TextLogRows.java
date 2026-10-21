// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configuration of the text log rows.
 */
public record TextLogRows(
    /**
     * Log levels to display.
     * 
     * Defaults to showing all logged levels.
     */
    java.util.List<org.teamvan.moneyshift.components.TextLogLevel> filterByLogLevel
) implements org.teamvan.moneyshift.Archetype {
    public TextLogRows {
        if (filterByLogLevel != null) { filterByLogLevel = java.util.List.copyOf(filterByLogLevel); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.TextLogRows";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FILTER_BY_LOG_LEVEL = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TextLogRows:filter_by_log_level", "rerun.components.TextLogLevel");

    /**
     * Log levels to display.
     * 
     * Defaults to showing all logged levels.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFilterByLogLevel() {
        return DESCRIPTOR_FILTER_BY_LOG_LEVEL;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorFilterByLogLevel());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (filterByLogLevel != null) {
            batches.add(org.teamvan.moneyshift.components.TextLogLevel.batch(descriptorFilterByLogLevel(), allocator, filterByLogLevel));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.TextLogLevel> filterByLogLevel;
        private Builder() {}

        public Builder withFilterByLogLevel(java.util.List<org.teamvan.moneyshift.components.TextLogLevel> value) {
            this.filterByLogLevel = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withFilterByLogLevel(java.lang.String... value) {
            java.util.List<org.teamvan.moneyshift.components.TextLogLevel> values = new java.util.ArrayList<>(value.length);
            for (java.lang.String v : value) {
                values.add(new org.teamvan.moneyshift.components.TextLogLevel(v));
            }
            return withFilterByLogLevel(values);
        }

        public Builder withFilterByLogLevel(org.teamvan.moneyshift.components.TextLogLevel... value) {
            return withFilterByLogLevel(java.util.List.of(value));
        }

        public TextLogRows build() {
            return new TextLogRows(filterByLogLevel);
        }
    }

    public static TextLogRows of(
    ) {
        return new TextLogRows(null);
    }
}
