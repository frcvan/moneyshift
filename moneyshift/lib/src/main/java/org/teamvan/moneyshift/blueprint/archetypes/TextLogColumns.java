// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configuration of the text log columns.
 */
public record TextLogColumns(
    /**
     * What timeline columns to show.
     * 
     * Defaults to displaying only the active timeline.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.TimelineColumn> timelineColumns,
    /**
     * All columns to be displayed.
     * 
     * Defaults to showing all text log column kinds in the order of the enum.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.TextLogColumn> textLogColumns
) implements org.teamvan.moneyshift.Archetype {
    public TextLogColumns {
        if (timelineColumns != null) { timelineColumns = java.util.List.copyOf(timelineColumns); }
        if (textLogColumns != null) { textLogColumns = java.util.List.copyOf(textLogColumns); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.TextLogColumns";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TIMELINE_COLUMNS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TextLogColumns:timeline_columns", "rerun.blueprint.components.TimelineColumn");

    /**
     * What timeline columns to show.
     * 
     * Defaults to displaying only the active timeline.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTimelineColumns() {
        return DESCRIPTOR_TIMELINE_COLUMNS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TEXT_LOG_COLUMNS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TextLogColumns:text_log_columns", "rerun.blueprint.components.TextLogColumn");

    /**
     * All columns to be displayed.
     * 
     * Defaults to showing all text log column kinds in the order of the enum.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTextLogColumns() {
        return DESCRIPTOR_TEXT_LOG_COLUMNS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorTimelineColumns(), descriptorTextLogColumns());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (timelineColumns != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.TimelineColumn.batch(descriptorTimelineColumns(), allocator, timelineColumns));
        }
        if (textLogColumns != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.TextLogColumn.batch(descriptorTextLogColumns(), allocator, textLogColumns));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.blueprint.components.TimelineColumn> timelineColumns;
        private java.util.List<org.teamvan.moneyshift.blueprint.components.TextLogColumn> textLogColumns;
        private Builder() {}

        public Builder withTimelineColumns(java.util.List<org.teamvan.moneyshift.blueprint.components.TimelineColumn> value) {
            this.timelineColumns = java.util.List.copyOf(value);
            return this;
        }

        public Builder withTimelineColumns(org.teamvan.moneyshift.blueprint.components.TimelineColumn... value) {
            return withTimelineColumns(java.util.List.of(value));
        }

        public Builder withTextLogColumns(java.util.List<org.teamvan.moneyshift.blueprint.components.TextLogColumn> value) {
            this.textLogColumns = java.util.List.copyOf(value);
            return this;
        }

        public Builder withTextLogColumns(org.teamvan.moneyshift.blueprint.components.TextLogColumn... value) {
            return withTextLogColumns(java.util.List.of(value));
        }

        public TextLogColumns build() {
            return new TextLogColumns(timelineColumns, textLogColumns);
        }
    }

    public static TextLogColumns of(
    ) {
        return new TextLogColumns(null, null);
    }
}
