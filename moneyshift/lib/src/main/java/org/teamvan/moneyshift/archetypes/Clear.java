// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Empties all the components of an entity.
 * 
 * The presence of a clear means that a latest-at query of components at a given path(s)
 * will not return any components that were logged at those paths before the clear.
 * Any logged components after the clear are unaffected by the clear.
 * 
 * This implies that a range query that includes time points that are before the clear,
 * still returns all components at the given path(s).
 * Meaning that in practice clears are ineffective when making use of visible time ranges.
 * Scalar plots are an exception: they track clears and use them to represent holes in the
 * data (i.e. discontinuous lines).
 * 
 */
public record Clear(
    org.teamvan.moneyshift.components.ClearIsRecursive isRecursive
) implements org.teamvan.moneyshift.Archetype {
    public Clear {
        java.util.Objects.requireNonNull(isRecursive, "isRecursive");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Clear";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_IS_RECURSIVE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Clear:is_recursive", "rerun.components.ClearIsRecursive");

    public static org.teamvan.moneyshift.ComponentDescriptor descriptorIsRecursive() {
        return DESCRIPTOR_IS_RECURSIVE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorIsRecursive());
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
        batches.add(org.teamvan.moneyshift.components.ClearIsRecursive.batch(descriptorIsRecursive(), allocator, isRecursive));
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.ClearIsRecursive isRecursive;
        private Builder() {}

        public Builder withIsRecursive(org.teamvan.moneyshift.components.ClearIsRecursive value) {
            this.isRecursive = value;
            return this;
        }

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withIsRecursive(boolean value) {
            return withIsRecursive(new org.teamvan.moneyshift.components.ClearIsRecursive(value));
        }

        public Clear build() {
            return new Clear(isRecursive);
        }
    }

    public static Clear of(
        org.teamvan.moneyshift.components.ClearIsRecursive isRecursive
    ) {
        return new Clear(isRecursive);
    }
}
