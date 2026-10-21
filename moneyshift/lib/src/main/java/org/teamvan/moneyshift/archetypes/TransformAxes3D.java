// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A visual representation of a {@link org.teamvan.moneyshift.archetypes.Transform3D}.
 * 
 */
public record TransformAxes3D(
    /**
     * Visual length of the 3 axes.
     * 
     * The length is interpreted in the local coordinate system of the transform.
     * If the transform is scaled, the axes will be scaled accordingly.
     */
    org.teamvan.moneyshift.components.AxisLength axisLength,
    /**
     * Whether to show a text label with the corresponding frame.
     */
    org.teamvan.moneyshift.components.ShowLabels showFrame
) implements org.teamvan.moneyshift.Archetype {
    public TransformAxes3D {
        java.util.Objects.requireNonNull(axisLength, "axisLength");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.TransformAxes3D";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_AXIS_LENGTH = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TransformAxes3D:axis_length", "rerun.components.AxisLength");

    /**
     * Visual length of the 3 axes.
     * 
     * The length is interpreted in the local coordinate system of the transform.
     * If the transform is scaled, the axes will be scaled accordingly.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorAxisLength() {
        return DESCRIPTOR_AXIS_LENGTH;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SHOW_FRAME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TransformAxes3D:show_frame", "rerun.components.ShowLabels");

    /**
     * Whether to show a text label with the corresponding frame.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorShowFrame() {
        return DESCRIPTOR_SHOW_FRAME;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorAxisLength());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorShowFrame());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.AxisLength.batch(descriptorAxisLength(), allocator, axisLength));
        if (showFrame != null) {
            batches.add(org.teamvan.moneyshift.components.ShowLabels.batch(descriptorShowFrame(), allocator, showFrame));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.AxisLength axisLength;
        private org.teamvan.moneyshift.components.ShowLabels showFrame;
        private Builder() {}

        public Builder withAxisLength(org.teamvan.moneyshift.components.AxisLength value) {
            this.axisLength = value;
            return this;
        }

        public Builder withShowFrame(org.teamvan.moneyshift.components.ShowLabels value) {
            this.showFrame = value;
            return this;
        }

        public TransformAxes3D build() {
            return new TransformAxes3D(axisLength, showFrame);
        }
    }

    public static TransformAxes3D of(
        org.teamvan.moneyshift.components.AxisLength axisLength
    ) {
        return new TransformAxes3D(axisLength, null);
    }
}
