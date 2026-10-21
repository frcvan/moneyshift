// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.views;

/**
 * A view on a tensor of any dimensionality.
 * 
 */
public record TensorView(
    /**
     * How to select the slice of the tensor to show.
     */
    org.teamvan.moneyshift.blueprint.archetypes.TensorSliceSelection sliceSelection,
    /**
     * Configures how scalars are mapped to color.
     */
    org.teamvan.moneyshift.blueprint.archetypes.TensorScalarMapping scalarMapping,
    /**
     * Configures how the selected slice should fit into the view.
     */
    org.teamvan.moneyshift.blueprint.archetypes.TensorViewFit viewFit
) {
    public static final java.lang.String VIEW_NAME = "rerun.blueprint.views.TensorView";

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.archetypes.TensorSliceSelection sliceSelection;
        private org.teamvan.moneyshift.blueprint.archetypes.TensorScalarMapping scalarMapping;
        private org.teamvan.moneyshift.blueprint.archetypes.TensorViewFit viewFit;
        private Builder() {}

        public Builder withSliceSelection(org.teamvan.moneyshift.blueprint.archetypes.TensorSliceSelection value) {
            this.sliceSelection = value;
            return this;
        }

        public Builder withScalarMapping(org.teamvan.moneyshift.blueprint.archetypes.TensorScalarMapping value) {
            this.scalarMapping = value;
            return this;
        }

        public Builder withViewFit(org.teamvan.moneyshift.blueprint.archetypes.TensorViewFit value) {
            this.viewFit = value;
            return this;
        }

        public TensorView build() {
            return new TensorView(sliceSelection, scalarMapping, viewFit);
        }
    }
}
