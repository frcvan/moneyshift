// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * One or more double-precision scalar values, e.g. for use for time-series plots.
 * 
 * The current timeline value will be used for the time/X-axis, hence scalars
 * should not be static.
 * Number of scalars per timestamp is expected to be the same over time.
 * 
 * When used to produce a plot, this archetype is used to provide the data that
 * is referenced by {@link org.teamvan.moneyshift.archetypes.SeriesLines} or {@link org.teamvan.moneyshift.archetypes.SeriesPoints}. You can do
 * this by logging both archetypes to the same path, or alternatively configuring
 * the plot-specific archetypes through the blueprint.
 * 
 */
public record Scalars(
    /**
     * The scalar values to log.
     */
    java.util.List<org.teamvan.moneyshift.components.Scalar> scalars
) implements org.teamvan.moneyshift.Archetype {
    public Scalars {
        java.util.Objects.requireNonNull(scalars, "scalars");
        scalars = java.util.List.copyOf(scalars);
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Scalars";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SCALARS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Scalars:scalars", "rerun.components.Scalar");

    /**
     * The scalar values to log.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorScalars() {
        return DESCRIPTOR_SCALARS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorScalars());
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
        batches.add(org.teamvan.moneyshift.components.Scalar.batch(descriptorScalars(), allocator, scalars));
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.Scalar> scalars;
        private Builder() {}

        public Builder withScalars(java.util.List<org.teamvan.moneyshift.components.Scalar> value) {
            this.scalars = java.util.List.copyOf(value);
            return this;
        }

        public Builder withScalars(org.teamvan.moneyshift.components.Scalar... value) {
            return withScalars(java.util.List.of(value));
        }

        public Scalars build() {
            return new Scalars(scalars);
        }
    }

    public static Scalars of(
        java.util.List<org.teamvan.moneyshift.components.Scalar> scalars
    ) {
        return new Scalars(scalars);
    }
}
