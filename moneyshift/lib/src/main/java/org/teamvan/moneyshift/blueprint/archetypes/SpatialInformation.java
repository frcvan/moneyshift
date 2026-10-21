// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configures spatial view properties.
 */
public record SpatialInformation(
    /**
     * The target reference frame for all transformations.
     * 
     * Defaults to the coordinate frame used by the space origin entity.
     */
    org.teamvan.moneyshift.components.TransformFrameId targetFrame,
    /**
     * Whether the bounding box should be shown.
     */
    org.teamvan.moneyshift.blueprint.components.Enabled showBoundingBox,
    /**
     * Whether axes should be shown at the origin.
     */
    org.teamvan.moneyshift.blueprint.components.Enabled showAxes,
    /**
     * Controls the orientation of the axes in a 3D view; it has no effect in a 2D view.
     * 
     * This determines the 3D eye orientation, navigation, and default grid plane.
     * 
     * The three directions are always ordered as The three directions are always ordered as [x, y, z] and specify where each positive axis points.
     * For example, For example, [Right, Down, Forward] means that +X points right, +Y points down, and +Z points forward.
     * 
     * When this property is unset, a 3D view first uses {@link org.teamvan.moneyshift.archetypes.ViewCoordinates} logged at its origin entity or the closest ancestor.
     * If none is found, it uses the camera orientation from the closest ancestor {@link org.teamvan.moneyshift.archetypes.Pinhole}.
     * If neither is found, the fallback is RFU.
     * 
     * This property is hidden from the selection panel for 2D views.
     * 
     * ⚠ <a href="https://github.com/rerun-io/rerun/issues/5032">Rerun does not yet support left-handed coordinate systems</a>.
     */
    org.teamvan.moneyshift.components.ViewCoordinates axes
) implements org.teamvan.moneyshift.Archetype {
    public SpatialInformation {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.SpatialInformation";
    public static final int NUM_COMPONENTS = 4;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TARGET_FRAME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SpatialInformation:target_frame", "rerun.components.TransformFrameId");

    /**
     * The target reference frame for all transformations.
     * 
     * Defaults to the coordinate frame used by the space origin entity.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTargetFrame() {
        return DESCRIPTOR_TARGET_FRAME;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SHOW_BOUNDING_BOX = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SpatialInformation:show_bounding_box", "rerun.blueprint.components.Enabled");

    /**
     * Whether the bounding box should be shown.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorShowBoundingBox() {
        return DESCRIPTOR_SHOW_BOUNDING_BOX;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SHOW_AXES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SpatialInformation:show_axes", "rerun.blueprint.components.Enabled");

    /**
     * Whether axes should be shown at the origin.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorShowAxes() {
        return DESCRIPTOR_SHOW_AXES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_AXES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "SpatialInformation:axes", "rerun.components.ViewCoordinates");

    /**
     * Controls the orientation of the axes in a 3D view; it has no effect in a 2D view.
     * 
     * This determines the 3D eye orientation, navigation, and default grid plane.
     * 
     * The three directions are always ordered as The three directions are always ordered as [x, y, z] and specify where each positive axis points.
     * For example, For example, [Right, Down, Forward] means that +X points right, +Y points down, and +Z points forward.
     * 
     * When this property is unset, a 3D view first uses {@link org.teamvan.moneyshift.archetypes.ViewCoordinates} logged at its origin entity or the closest ancestor.
     * If none is found, it uses the camera orientation from the closest ancestor {@link org.teamvan.moneyshift.archetypes.Pinhole}.
     * If neither is found, the fallback is RFU.
     * 
     * This property is hidden from the selection panel for 2D views.
     * 
     * ⚠ <a href="https://github.com/rerun-io/rerun/issues/5032">Rerun does not yet support left-handed coordinate systems</a>.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorAxes() {
        return DESCRIPTOR_AXES;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorTargetFrame(), descriptorShowBoundingBox(), descriptorShowAxes(), descriptorAxes());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (targetFrame != null) {
            batches.add(org.teamvan.moneyshift.components.TransformFrameId.batch(descriptorTargetFrame(), allocator, targetFrame));
        }
        if (showBoundingBox != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.Enabled.batch(descriptorShowBoundingBox(), allocator, showBoundingBox));
        }
        if (showAxes != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.Enabled.batch(descriptorShowAxes(), allocator, showAxes));
        }
        if (axes != null) {
            batches.add(org.teamvan.moneyshift.components.ViewCoordinates.batch(descriptorAxes(), allocator, axes));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.TransformFrameId targetFrame;
        private org.teamvan.moneyshift.blueprint.components.Enabled showBoundingBox;
        private org.teamvan.moneyshift.blueprint.components.Enabled showAxes;
        private org.teamvan.moneyshift.components.ViewCoordinates axes;
        private Builder() {}

        public Builder withTargetFrame(org.teamvan.moneyshift.components.TransformFrameId value) {
            this.targetFrame = value;
            return this;
        }

        public Builder withShowBoundingBox(org.teamvan.moneyshift.blueprint.components.Enabled value) {
            this.showBoundingBox = value;
            return this;
        }

        public Builder withShowAxes(org.teamvan.moneyshift.blueprint.components.Enabled value) {
            this.showAxes = value;
            return this;
        }

        public Builder withAxes(org.teamvan.moneyshift.components.ViewCoordinates value) {
            this.axes = value;
            return this;
        }

        public SpatialInformation build() {
            return new SpatialInformation(targetFrame, showBoundingBox, showAxes, axes);
        }
    }

    public static SpatialInformation of(
    ) {
        return new SpatialInformation(null, null, null, null);
    }
}
