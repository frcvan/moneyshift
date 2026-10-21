// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Controls the visual bounds of a 2D view.
 * 
 * Everything within these bounds are guaranteed to be visible.
 * Somethings outside of these bounds may also be visible due to letterboxing.
 * 
 * If no visual bounds are set, it will be determined automatically,
 * based on the bounding-box of the data or other camera information present in the view.
 */
public record VisualBounds2D(
    /**
     * Controls the visible range of a 2D view.
     * 
     * Use this to control pan &amp; zoom of the view.
     */
    org.teamvan.moneyshift.blueprint.components.VisualBounds2D range
) implements org.teamvan.moneyshift.Archetype {
    public VisualBounds2D {
        java.util.Objects.requireNonNull(range, "range");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.VisualBounds2D";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_RANGE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "VisualBounds2D:range", "rerun.blueprint.components.VisualBounds2D");

    /**
     * Controls the visible range of a 2D view.
     * 
     * Use this to control pan &amp; zoom of the view.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRange() {
        return DESCRIPTOR_RANGE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorRange());
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
        batches.add(org.teamvan.moneyshift.blueprint.components.VisualBounds2D.batch(descriptorRange(), allocator, range));
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.VisualBounds2D range;
        private Builder() {}

        public Builder withRange(org.teamvan.moneyshift.blueprint.components.VisualBounds2D value) {
            this.range = value;
            return this;
        }

        public VisualBounds2D build() {
            return new VisualBounds2D(range);
        }
    }

    public static VisualBounds2D of(
        org.teamvan.moneyshift.blueprint.components.VisualBounds2D range
    ) {
        return new VisualBounds2D(range);
    }
}
