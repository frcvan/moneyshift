// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configures what range of each timeline is shown on a view.
 * 
 * Whenever no visual time range applies, queries are done with "latest-at" semantics.
 * This means that the view will, starting from the time cursor position,
 * query the latest data available for each component type.
 * 
 * The default visual time range depends on the type of view this property applies to:
 * - For time series and state timeline views, the default is to show the entire timeline.
 * - For any other view, the default is to apply latest-at semantics.
 */
public record VisibleTimeRanges(
    /**
     * The time ranges to show for each timeline unless specified otherwise on a per-entity basis.
     * 
     * If a timeline is specified more than once, the first entry will be used.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.VisibleTimeRange> ranges
) implements org.teamvan.moneyshift.Archetype {
    public VisibleTimeRanges {
        java.util.Objects.requireNonNull(ranges, "ranges");
        ranges = java.util.List.copyOf(ranges);
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.VisibleTimeRanges";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_RANGES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VisibleTimeRanges:ranges", "rerun.blueprint.components.VisibleTimeRange");

    /**
     * The time ranges to show for each timeline unless specified otherwise on a per-entity basis.
     * 
     * If a timeline is specified more than once, the first entry will be used.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRanges() {
        return DESCRIPTOR_RANGES;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorRanges());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of();
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.blueprint.components.VisibleTimeRange.batch(descriptorRanges(), allocator, ranges));
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.blueprint.components.VisibleTimeRange> ranges;
        private Builder() {}

        public Builder withRanges(java.util.List<org.teamvan.moneyshift.blueprint.components.VisibleTimeRange> value) {
            this.ranges = java.util.List.copyOf(value);
            return this;
        }

        public Builder withRanges(org.teamvan.moneyshift.blueprint.components.VisibleTimeRange... value) {
            return withRanges(java.util.List.of(value));
        }

        public VisibleTimeRanges build() {
            return new VisibleTimeRanges(ranges);
        }
    }

    public static VisibleTimeRanges of(
        java.util.List<org.teamvan.moneyshift.blueprint.components.VisibleTimeRange> ranges
    ) {
        return new VisibleTimeRanges(ranges);
    }
}
