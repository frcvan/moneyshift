// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configures how a selected tensor slice is shown on screen.
 */
public record TensorViewFit(
    /**
     * How the image is scaled to fit the view.
     */
    org.teamvan.moneyshift.blueprint.components.ViewFit scaling
) implements org.teamvan.moneyshift.Archetype {
    public TensorViewFit {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.TensorViewFit";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SCALING = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TensorViewFit:scaling", "rerun.blueprint.components.ViewFit");

    /**
     * How the image is scaled to fit the view.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorScaling() {
        return DESCRIPTOR_SCALING;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorScaling());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (scaling != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ViewFit.batch(descriptorScaling(), allocator, scaling));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.ViewFit scaling;
        private Builder() {}

        public Builder withScaling(org.teamvan.moneyshift.blueprint.components.ViewFit value) {
            this.scaling = value;
            return this;
        }

        public TensorViewFit build() {
            return new TensorViewFit(scaling);
        }
    }

    public static TensorViewFit of(
    ) {
        return new TensorViewFit(null);
    }
}
