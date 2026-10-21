// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configures how tensor scalars are mapped to color.
 */
public record TensorScalarMapping(
    /**
     * Filter used when zooming in on the tensor.
     * 
     * Note that the filter is applied to the scalar values *before* they are mapped to color.
     */
    org.teamvan.moneyshift.components.MagnificationFilter magFilter,
    /**
     * How scalar values map to colors.
     */
    org.teamvan.moneyshift.components.Colormap colormap,
    /**
     * Gamma exponent applied to normalized values before mapping to color.
     * 
     * Raises the normalized values to the power of this value before mapping to color.
     * Acts like an inverse brightness. Defaults to 1.0.
     * 
     * The final value for display is set as:
     * {@code colormap( ((value - data_display_range.min) / (data_display_range.max - data_display_range.min)) ** gamma )}
     */
    org.teamvan.moneyshift.components.GammaCorrection gamma
) implements org.teamvan.moneyshift.Archetype {
    public TensorScalarMapping {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.TensorScalarMapping";
    public static final int NUM_COMPONENTS = 3;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MAG_FILTER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TensorScalarMapping:mag_filter", "rerun.components.MagnificationFilter");

    /**
     * Filter used when zooming in on the tensor.
     * 
     * Note that the filter is applied to the scalar values *before* they are mapped to color.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMagFilter() {
        return DESCRIPTOR_MAG_FILTER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORMAP = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TensorScalarMapping:colormap", "rerun.components.Colormap");

    /**
     * How scalar values map to colors.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColormap() {
        return DESCRIPTOR_COLORMAP;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_GAMMA = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TensorScalarMapping:gamma", "rerun.components.GammaCorrection");

    /**
     * Gamma exponent applied to normalized values before mapping to color.
     * 
     * Raises the normalized values to the power of this value before mapping to color.
     * Acts like an inverse brightness. Defaults to 1.0.
     * 
     * The final value for display is set as:
     * {@code colormap( ((value - data_display_range.min) / (data_display_range.max - data_display_range.min)) ** gamma )}
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorGamma() {
        return DESCRIPTOR_GAMMA;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorMagFilter(), descriptorColormap(), descriptorGamma());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (magFilter != null) {
            batches.add(org.teamvan.moneyshift.components.MagnificationFilter.batch(descriptorMagFilter(), allocator, magFilter));
        }
        if (colormap != null) {
            batches.add(org.teamvan.moneyshift.components.Colormap.batch(descriptorColormap(), allocator, colormap));
        }
        if (gamma != null) {
            batches.add(org.teamvan.moneyshift.components.GammaCorrection.batch(descriptorGamma(), allocator, gamma));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.MagnificationFilter magFilter;
        private org.teamvan.moneyshift.components.Colormap colormap;
        private org.teamvan.moneyshift.components.GammaCorrection gamma;
        private Builder() {}

        public Builder withMagFilter(org.teamvan.moneyshift.components.MagnificationFilter value) {
            this.magFilter = value;
            return this;
        }

        public Builder withColormap(org.teamvan.moneyshift.components.Colormap value) {
            this.colormap = value;
            return this;
        }

        public Builder withGamma(org.teamvan.moneyshift.components.GammaCorrection value) {
            this.gamma = value;
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withGamma(float value) {
            return withGamma(new org.teamvan.moneyshift.components.GammaCorrection(value));
        }

        public TensorScalarMapping build() {
            return new TensorScalarMapping(magFilter, colormap, gamma);
        }
    }

    public static TensorScalarMapping of(
    ) {
        return new TensorScalarMapping(null, null, null);
    }
}
