// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configuration for the scalar (Y) axis of a plot.
 */
public record ScalarAxis(
    /**
     * The range of the axis.
     * 
     * If unset, the range well be automatically determined based on the queried data.
     */
    org.teamvan.moneyshift.components.Range1D range,
    /**
     * If enabled, the Y axis range will remain locked to the specified range when zooming.
     */
    org.teamvan.moneyshift.blueprint.components.LockRangeDuringZoom zoomLock
) implements org.teamvan.moneyshift.Archetype {
    public ScalarAxis {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.ScalarAxis";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_RANGE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ScalarAxis:range", "rerun.components.Range1D");

    /**
     * The range of the axis.
     * 
     * If unset, the range well be automatically determined based on the queried data.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRange() {
        return DESCRIPTOR_RANGE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ZOOM_LOCK = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ScalarAxis:zoom_lock", "rerun.blueprint.components.LockRangeDuringZoom");

    /**
     * If enabled, the Y axis range will remain locked to the specified range when zooming.
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
        return java.util.List.of(descriptorRange(), descriptorZoomLock());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (range != null) {
            batches.add(org.teamvan.moneyshift.components.Range1D.batch(descriptorRange(), allocator, range));
        }
        if (zoomLock != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.LockRangeDuringZoom.batch(descriptorZoomLock(), allocator, zoomLock));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Range1D range;
        private org.teamvan.moneyshift.blueprint.components.LockRangeDuringZoom zoomLock;
        private Builder() {}

        public Builder withRange(org.teamvan.moneyshift.components.Range1D value) {
            this.range = value;
            return this;
        }

        public Builder withZoomLock(org.teamvan.moneyshift.blueprint.components.LockRangeDuringZoom value) {
            this.zoomLock = value;
            return this;
        }

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withZoomLock(boolean value) {
            return withZoomLock(new org.teamvan.moneyshift.blueprint.components.LockRangeDuringZoom(value));
        }

        public ScalarAxis build() {
            return new ScalarAxis(range, zoomLock);
        }
    }

    public static ScalarAxis of(
    ) {
        return new ScalarAxis(null, null);
    }
}
