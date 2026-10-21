// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Specifies a 2D slice of a tensor.
 */
public record TensorSliceSelection(
    /**
     * Which dimension to map to width.
     * 
     * If not specified, the height will be determined automatically based on the name and index of the dimension.
     */
    org.teamvan.moneyshift.components.TensorWidthDimension width,
    /**
     * Which dimension to map to height.
     * 
     * If not specified, the height will be determined automatically based on the name and index of the dimension.
     */
    org.teamvan.moneyshift.components.TensorHeightDimension height,
    /**
     * Selected indices for all other dimensions.
     * 
     * If any of the here listed dimensions is equal to {@code width} or {@code height}, it will be ignored.
     */
    java.util.List<org.teamvan.moneyshift.components.TensorDimensionIndexSelection> indices,
    /**
     * Any dimension listed here will have a slider for the index.
     * 
     * Edits to the sliders will directly manipulate dimensions on the {@code indices} list.
     * If any of the here listed dimensions is equal to {@code width} or {@code height}, it will be ignored.
     * If not specified, adds slides for any dimension in {@code indices}.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.TensorDimensionIndexSlider> slider
) implements org.teamvan.moneyshift.Archetype {
    public TensorSliceSelection {
        if (indices != null) { indices = java.util.List.copyOf(indices); }
        if (slider != null) { slider = java.util.List.copyOf(slider); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.TensorSliceSelection";
    public static final int NUM_COMPONENTS = 4;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_WIDTH = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TensorSliceSelection:width", "rerun.components.TensorWidthDimension");

    /**
     * Which dimension to map to width.
     * 
     * If not specified, the height will be determined automatically based on the name and index of the dimension.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorWidth() {
        return DESCRIPTOR_WIDTH;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_HEIGHT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TensorSliceSelection:height", "rerun.components.TensorHeightDimension");

    /**
     * Which dimension to map to height.
     * 
     * If not specified, the height will be determined automatically based on the name and index of the dimension.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorHeight() {
        return DESCRIPTOR_HEIGHT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_INDICES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TensorSliceSelection:indices", "rerun.components.TensorDimensionIndexSelection");

    /**
     * Selected indices for all other dimensions.
     * 
     * If any of the here listed dimensions is equal to {@code width} or {@code height}, it will be ignored.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorIndices() {
        return DESCRIPTOR_INDICES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SLIDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TensorSliceSelection:slider", "rerun.blueprint.components.TensorDimensionIndexSlider");

    /**
     * Any dimension listed here will have a slider for the index.
     * 
     * Edits to the sliders will directly manipulate dimensions on the {@code indices} list.
     * If any of the here listed dimensions is equal to {@code width} or {@code height}, it will be ignored.
     * If not specified, adds slides for any dimension in {@code indices}.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorSlider() {
        return DESCRIPTOR_SLIDER;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorWidth(), descriptorHeight(), descriptorIndices(), descriptorSlider());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (width != null) {
            batches.add(org.teamvan.moneyshift.components.TensorWidthDimension.batch(descriptorWidth(), allocator, width));
        }
        if (height != null) {
            batches.add(org.teamvan.moneyshift.components.TensorHeightDimension.batch(descriptorHeight(), allocator, height));
        }
        if (indices != null) {
            batches.add(org.teamvan.moneyshift.components.TensorDimensionIndexSelection.batch(descriptorIndices(), allocator, indices));
        }
        if (slider != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.TensorDimensionIndexSlider.batch(descriptorSlider(), allocator, slider));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.TensorWidthDimension width;
        private org.teamvan.moneyshift.components.TensorHeightDimension height;
        private java.util.List<org.teamvan.moneyshift.components.TensorDimensionIndexSelection> indices;
        private java.util.List<org.teamvan.moneyshift.blueprint.components.TensorDimensionIndexSlider> slider;
        private Builder() {}

        public Builder withWidth(org.teamvan.moneyshift.components.TensorWidthDimension value) {
            this.width = value;
            return this;
        }

        public Builder withHeight(org.teamvan.moneyshift.components.TensorHeightDimension value) {
            this.height = value;
            return this;
        }

        public Builder withIndices(java.util.List<org.teamvan.moneyshift.components.TensorDimensionIndexSelection> value) {
            this.indices = java.util.List.copyOf(value);
            return this;
        }

        public Builder withIndices(org.teamvan.moneyshift.components.TensorDimensionIndexSelection... value) {
            return withIndices(java.util.List.of(value));
        }

        public Builder withSlider(java.util.List<org.teamvan.moneyshift.blueprint.components.TensorDimensionIndexSlider> value) {
            this.slider = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withSlider(long... value) {
            java.util.List<org.teamvan.moneyshift.blueprint.components.TensorDimensionIndexSlider> values = new java.util.ArrayList<>(value.length);
            for (long v : value) {
                values.add(new org.teamvan.moneyshift.blueprint.components.TensorDimensionIndexSlider(v));
            }
            return withSlider(values);
        }

        public Builder withSlider(org.teamvan.moneyshift.blueprint.components.TensorDimensionIndexSlider... value) {
            return withSlider(java.util.List.of(value));
        }

        public TensorSliceSelection build() {
            return new TensorSliceSelection(width, height, indices, slider);
        }
    }

    public static TensorSliceSelection of(
    ) {
        return new TensorSliceSelection(null, null, null, null);
    }
}
