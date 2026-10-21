// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * The query for the dataframe view.
 */
public record DataframeQuery(
    /**
     * The timeline for this query.
     * 
     * If unset, the timeline currently active on the time panel is used.
     */
    org.teamvan.moneyshift.blueprint.components.TimelineName timeline,
    /**
     * If provided, only rows whose timestamp is within this range will be shown.
     * 
     * Note: will be unset as soon as {@code timeline} is changed.
     */
    org.teamvan.moneyshift.blueprint.components.FilterByRange filterByRange,
    /**
     * If provided, only show rows which contains a logged event for the specified component.
     */
    org.teamvan.moneyshift.blueprint.components.FilterIsNotNull filterIsNotNull,
    /**
     * Should empty cells be filled with latest-at queries?
     */
    org.teamvan.moneyshift.blueprint.components.ApplyLatestAt applyLatestAt,
    /**
     * Selected columns. If unset, only the active timeline and all component columns are selected.
     */
    org.teamvan.moneyshift.blueprint.components.SelectedColumns select,
    /**
     * The order of entity path column groups. If unset, the default order is used.
     * 
     * This affects the order of component columns, which are always grouped by entity path. Timeline columns always
     * come first. Entities not listed here are appended at the end in default order.
     * 
     * If {@code entity_order} contains any entity path that is not included in the view, they are ignored.
     */
    org.teamvan.moneyshift.blueprint.components.ColumnOrder entityOrder,
    /**
     * Whether to auto-scroll to track the time cursor.
     * 
     * When enabled and the view's timeline matches the time panel's active timeline,
     * the view will scroll to keep the row at or before the current time cursor visible.
     */
    org.teamvan.moneyshift.blueprint.components.AutoScroll autoScroll
) implements org.teamvan.moneyshift.Archetype {
    public DataframeQuery {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.DataframeQuery";
    public static final int NUM_COMPONENTS = 7;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TIMELINE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DataframeQuery:timeline", "rerun.blueprint.components.TimelineName");

    /**
     * The timeline for this query.
     * 
     * If unset, the timeline currently active on the time panel is used.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTimeline() {
        return DESCRIPTOR_TIMELINE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FILTER_BY_RANGE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DataframeQuery:filter_by_range", "rerun.blueprint.components.FilterByRange");

    /**
     * If provided, only rows whose timestamp is within this range will be shown.
     * 
     * Note: will be unset as soon as {@code timeline} is changed.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFilterByRange() {
        return DESCRIPTOR_FILTER_BY_RANGE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FILTER_IS_NOT_NULL = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DataframeQuery:filter_is_not_null", "rerun.blueprint.components.FilterIsNotNull");

    /**
     * If provided, only show rows which contains a logged event for the specified component.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFilterIsNotNull() {
        return DESCRIPTOR_FILTER_IS_NOT_NULL;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_APPLY_LATEST_AT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DataframeQuery:apply_latest_at", "rerun.blueprint.components.ApplyLatestAt");

    /**
     * Should empty cells be filled with latest-at queries?
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorApplyLatestAt() {
        return DESCRIPTOR_APPLY_LATEST_AT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SELECT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DataframeQuery:select", "rerun.blueprint.components.SelectedColumns");

    /**
     * Selected columns. If unset, only the active timeline and all component columns are selected.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorSelect() {
        return DESCRIPTOR_SELECT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ENTITY_ORDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DataframeQuery:entity_order", "rerun.blueprint.components.ColumnOrder");

    /**
     * The order of entity path column groups. If unset, the default order is used.
     * 
     * This affects the order of component columns, which are always grouped by entity path. Timeline columns always
     * come first. Entities not listed here are appended at the end in default order.
     * 
     * If {@code entity_order} contains any entity path that is not included in the view, they are ignored.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorEntityOrder() {
        return DESCRIPTOR_ENTITY_ORDER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_AUTO_SCROLL = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "DataframeQuery:auto_scroll", "rerun.blueprint.components.AutoScroll");

    /**
     * Whether to auto-scroll to track the time cursor.
     * 
     * When enabled and the view's timeline matches the time panel's active timeline,
     * the view will scroll to keep the row at or before the current time cursor visible.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorAutoScroll() {
        return DESCRIPTOR_AUTO_SCROLL;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorTimeline(), descriptorFilterByRange(), descriptorFilterIsNotNull(), descriptorApplyLatestAt(), descriptorSelect(), descriptorEntityOrder(), descriptorAutoScroll());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (timeline != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.TimelineName.batch(descriptorTimeline(), allocator, timeline));
        }
        if (filterByRange != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.FilterByRange.batch(descriptorFilterByRange(), allocator, filterByRange));
        }
        if (filterIsNotNull != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.FilterIsNotNull.batch(descriptorFilterIsNotNull(), allocator, filterIsNotNull));
        }
        if (applyLatestAt != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ApplyLatestAt.batch(descriptorApplyLatestAt(), allocator, applyLatestAt));
        }
        if (select != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.SelectedColumns.batch(descriptorSelect(), allocator, select));
        }
        if (entityOrder != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ColumnOrder.batch(descriptorEntityOrder(), allocator, entityOrder));
        }
        if (autoScroll != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.AutoScroll.batch(descriptorAutoScroll(), allocator, autoScroll));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.TimelineName timeline;
        private org.teamvan.moneyshift.blueprint.components.FilterByRange filterByRange;
        private org.teamvan.moneyshift.blueprint.components.FilterIsNotNull filterIsNotNull;
        private org.teamvan.moneyshift.blueprint.components.ApplyLatestAt applyLatestAt;
        private org.teamvan.moneyshift.blueprint.components.SelectedColumns select;
        private org.teamvan.moneyshift.blueprint.components.ColumnOrder entityOrder;
        private org.teamvan.moneyshift.blueprint.components.AutoScroll autoScroll;
        private Builder() {}

        public Builder withTimeline(org.teamvan.moneyshift.blueprint.components.TimelineName value) {
            this.timeline = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withTimeline(java.lang.String value) {
            return withTimeline(new org.teamvan.moneyshift.blueprint.components.TimelineName(value));
        }

        public Builder withFilterByRange(org.teamvan.moneyshift.blueprint.components.FilterByRange value) {
            this.filterByRange = value;
            return this;
        }

        public Builder withFilterIsNotNull(org.teamvan.moneyshift.blueprint.components.FilterIsNotNull value) {
            this.filterIsNotNull = value;
            return this;
        }

        public Builder withApplyLatestAt(org.teamvan.moneyshift.blueprint.components.ApplyLatestAt value) {
            this.applyLatestAt = value;
            return this;
        }

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withApplyLatestAt(boolean value) {
            return withApplyLatestAt(new org.teamvan.moneyshift.blueprint.components.ApplyLatestAt(value));
        }

        public Builder withSelect(org.teamvan.moneyshift.blueprint.components.SelectedColumns value) {
            this.select = value;
            return this;
        }

        public Builder withEntityOrder(org.teamvan.moneyshift.blueprint.components.ColumnOrder value) {
            this.entityOrder = value;
            return this;
        }

        /** Overload that takes the elements of a single component directly. */
        public Builder withEntityOrder(org.teamvan.moneyshift.encodings.EntityPath... value) {
            return withEntityOrder(new org.teamvan.moneyshift.blueprint.components.ColumnOrder(java.util.List.of(value)));
        }

        public Builder withAutoScroll(org.teamvan.moneyshift.blueprint.components.AutoScroll value) {
            this.autoScroll = value;
            return this;
        }

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withAutoScroll(boolean value) {
            return withAutoScroll(new org.teamvan.moneyshift.blueprint.components.AutoScroll(value));
        }

        public DataframeQuery build() {
            return new DataframeQuery(timeline, filterByRange, filterIsNotNull, applyLatestAt, select, entityOrder, autoScroll);
        }
    }

    public static DataframeQuery of(
    ) {
        return new DataframeQuery(null, null, null, null, null, null, null);
    }
}
