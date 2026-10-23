// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A visual representation of a {@link org.teamvan.moneyshift.archetypes.Transform3D rerun::archetypes::Transform3D}.
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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("axis_length").append('=').append(org.teamvan.moneyshift.components.AxisLength.COMPONENT_TYPE);
        }
        if (showFrame != null) {
            if (components.length() > 0) components.append(',');
            components.append("show_frame").append('=').append(org.teamvan.moneyshift.components.ShowLabels.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (true) {
            org.teamvan.moneyshift.components.AxisLength.writeColumnsStatic(w, "axis_length", java.util.List.of(axisLength));
        }
        if (showFrame != null) {
            org.teamvan.moneyshift.components.ShowLabels.writeColumnsStatic(w, "show_frame", java.util.List.of(showFrame));
        }
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

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withAxisLength(float value) {
            return withAxisLength(new org.teamvan.moneyshift.components.AxisLength(value));
        }

        public Builder withShowFrame(org.teamvan.moneyshift.components.ShowLabels value) {
            this.showFrame = value;
            return this;
        }

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withShowFrame(boolean value) {
            return withShowFrame(new org.teamvan.moneyshift.components.ShowLabels(value));
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
