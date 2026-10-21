// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configuration for the time (X) axis of a plot.
 */
public record TimeAxis(
    /**
     * How should the horizontal/X/time axis be linked across multiple plots?
     * 
     * Linking with global will ignore {@code view_range}.
     */
    org.teamvan.moneyshift.blueprint.components.LinkAxis link,
    /**
     * The view range of the horizontal/X/time axis.
     */
    org.teamvan.moneyshift.blueprint.components.TimeRange viewRange,
    /**
     * If enabled, the X axis range will remain locked to the specified range when zooming.
     */
    org.teamvan.moneyshift.blueprint.components.LockRangeDuringZoom zoomLock
) implements org.teamvan.moneyshift.Archetype {
    public TimeAxis {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.TimeAxis";
    public static final int NUM_COMPONENTS = 3;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LINK = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TimeAxis:link", "rerun.blueprint.components.LinkAxis");

    /**
     * How should the horizontal/X/time axis be linked across multiple plots?
     * 
     * Linking with global will ignore {@code view_range}.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLink() {
        return DESCRIPTOR_LINK;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VIEW_RANGE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TimeAxis:view_range", "rerun.blueprint.components.TimeRange");

    /**
     * The view range of the horizontal/X/time axis.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorViewRange() {
        return DESCRIPTOR_VIEW_RANGE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ZOOM_LOCK = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TimeAxis:zoom_lock", "rerun.blueprint.components.LockRangeDuringZoom");

    /**
     * If enabled, the X axis range will remain locked to the specified range when zooming.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorZoomLock() {
        return DESCRIPTOR_ZOOM_LOCK;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorLink(), descriptorViewRange(), descriptorZoomLock());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (link != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.LinkAxis.batch(descriptorLink(), allocator, link));
        }
        if (viewRange != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.TimeRange.batch(descriptorViewRange(), allocator, viewRange));
        }
        if (zoomLock != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.LockRangeDuringZoom.batch(descriptorZoomLock(), allocator, zoomLock));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.LinkAxis link;
        private org.teamvan.moneyshift.blueprint.components.TimeRange viewRange;
        private org.teamvan.moneyshift.blueprint.components.LockRangeDuringZoom zoomLock;
        private Builder() {}

        public Builder withLink(org.teamvan.moneyshift.blueprint.components.LinkAxis value) {
            this.link = value;
            return this;
        }

        public Builder withViewRange(org.teamvan.moneyshift.blueprint.components.TimeRange value) {
            this.viewRange = value;
            return this;
        }

        public Builder withZoomLock(org.teamvan.moneyshift.blueprint.components.LockRangeDuringZoom value) {
            this.zoomLock = value;
            return this;
        }

        public TimeAxis build() {
            return new TimeAxis(link, viewRange, zoomLock);
        }
    }

    public static TimeAxis of(
    ) {
        return new TimeAxis(null, null, null);
    }
}
