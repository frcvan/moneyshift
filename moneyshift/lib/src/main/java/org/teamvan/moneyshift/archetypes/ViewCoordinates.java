// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Sets the default orientation for 3D views at or below this entity.
 * 
 * The orientation determines the 3D view's eye orientation, navigation, and default grid plane.
 * It does not change logged transforms.
 * 
 * The three directions are always ordered as The three directions are always ordered as [x, y, z] and specify where each positive axis points.
 * For example, For example, [Right, Down, Forward] means that +X points right, +Y points down, and +Z points forward.
 * 
 * A 3D view uses the value logged at its origin entity or the closest ancestor.
 * <a href="https://rerun.io/docs/reference/types/views/spatial3d_view">SpatialInformation</a> can override it for an individual view.
 * 
 * ⚠ <a href="https://github.com/rerun-io/rerun/issues/5032">Rerun does not yet support left-handed coordinate systems</a>.
 * 
 */
public record ViewCoordinates(
    /**
     * The directions of the The directions of the [x, y, z] axes.
     */
    org.teamvan.moneyshift.components.ViewCoordinates xyz
) implements org.teamvan.moneyshift.Archetype {
    public ViewCoordinates {
        java.util.Objects.requireNonNull(xyz, "xyz");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.ViewCoordinates";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_XYZ = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ViewCoordinates:xyz", "rerun.components.ViewCoordinates");

    /**
     * The directions of the The directions of the [x, y, z] axes.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorXyz() {
        return DESCRIPTOR_XYZ;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorXyz());
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
        batches.add(org.teamvan.moneyshift.components.ViewCoordinates.batch(descriptorXyz(), allocator, xyz));
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.ViewCoordinates xyz;
        private Builder() {}

        public Builder withXyz(org.teamvan.moneyshift.components.ViewCoordinates value) {
            this.xyz = value;
            return this;
        }

        public ViewCoordinates build() {
            return new ViewCoordinates(xyz);
        }
    }

    public static ViewCoordinates of(
        org.teamvan.moneyshift.components.ViewCoordinates xyz
    ) {
        return new ViewCoordinates(xyz);
    }
}
