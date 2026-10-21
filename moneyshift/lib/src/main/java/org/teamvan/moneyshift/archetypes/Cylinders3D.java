// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * 3D cylinders with flat caps.
 * 
 * This archetype is for cylinder primitives defined by their axial length and radius.
 * For points whose radii are for visualization purposes, use {@link org.teamvan.moneyshift.archetypes.Points3D} instead.
 * 
 * Orienting and placing cylinders forms a separate transform that is applied prior to {@link org.teamvan.moneyshift.archetypes.InstancePoses3D} and {@link org.teamvan.moneyshift.archetypes.Transform3D}.
 * 
 */
public record Cylinders3D(
    /**
     * The total axial length of the cylinder, measured as the straight-line distance between the centers of its two endcaps.
     */
    java.util.List<org.teamvan.moneyshift.components.Length> lengths,
    /**
     * Radii of the cylinders.
     */
    java.util.List<org.teamvan.moneyshift.components.Radius> radii,
    /**
     * Optional centers of the cylinders.
     * 
     * If not specified, each cylinder will be centered at (0, 0, 0).
     */
    java.util.List<org.teamvan.moneyshift.components.Translation3D> centers,
    /**
     * Rotations via axis + angle.
     * 
     * If no rotation is specified, the cylinders align with the +Z axis of the local coordinate system.
     */
    java.util.List<org.teamvan.moneyshift.components.RotationAxisAngle> rotationAxisAngles,
    /**
     * Rotations via quaternion.
     * 
     * If no rotation is specified, the cylinders align with the +Z axis of the local coordinate system.
     */
    java.util.List<org.teamvan.moneyshift.components.RotationQuat> quaternions,
    /**
     * Optional colors for the cylinders.
     * 
     * Alpha channel is used for transparency for solid fill-mode.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * Optional radii for the lines used when the cylinder is rendered as a wireframe.
     */
    java.util.List<org.teamvan.moneyshift.components.Radius> lineRadii,
    /**
     * Optionally choose whether the cylinders are drawn with lines or solid.
     */
    org.teamvan.moneyshift.components.FillMode fillMode,
    /**
     * Optional text labels for the cylinders, which will be located at their centers.
     */
    java.util.List<org.teamvan.moneyshift.components.Text> labels,
    /**
     * Whether the text labels should be shown.
     * 
     * If not set, labels will automatically appear when there is exactly one label for this entity
     * or the number of instances on this entity is under a certain threshold.
     */
    org.teamvan.moneyshift.components.ShowLabels showLabels,
    /**
     * Optional class ID for the ellipsoids.
     * 
     * The class ID provides colors and labels if not specified explicitly.
     */
    java.util.List<org.teamvan.moneyshift.components.ClassId> classIds
) implements org.teamvan.moneyshift.Archetype {
    public Cylinders3D {
        java.util.Objects.requireNonNull(lengths, "lengths");
        lengths = java.util.List.copyOf(lengths);
        java.util.Objects.requireNonNull(radii, "radii");
        radii = java.util.List.copyOf(radii);
        if (centers != null) { centers = java.util.List.copyOf(centers); }
        if (rotationAxisAngles != null) { rotationAxisAngles = java.util.List.copyOf(rotationAxisAngles); }
        if (quaternions != null) { quaternions = java.util.List.copyOf(quaternions); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (lineRadii != null) { lineRadii = java.util.List.copyOf(lineRadii); }
        if (labels != null) { labels = java.util.List.copyOf(labels); }
        if (classIds != null) { classIds = java.util.List.copyOf(classIds); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Cylinders3D";
    public static final int NUM_COMPONENTS = 11;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LENGTHS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Cylinders3D:lengths", "rerun.components.Length");

    /**
     * The total axial length of the cylinder, measured as the straight-line distance between the centers of its two endcaps.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLengths() {
        return DESCRIPTOR_LENGTHS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_RADII = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Cylinders3D:radii", "rerun.components.Radius");

    /**
     * Radii of the cylinders.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRadii() {
        return DESCRIPTOR_RADII;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CENTERS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Cylinders3D:centers", "rerun.components.Translation3D");

    /**
     * Optional centers of the cylinders.
     * 
     * If not specified, each cylinder will be centered at (0, 0, 0).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorCenters() {
        return DESCRIPTOR_CENTERS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ROTATION_AXIS_ANGLES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Cylinders3D:rotation_axis_angles", "rerun.components.RotationAxisAngle");

    /**
     * Rotations via axis + angle.
     * 
     * If no rotation is specified, the cylinders align with the +Z axis of the local coordinate system.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRotationAxisAngles() {
        return DESCRIPTOR_ROTATION_AXIS_ANGLES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_QUATERNIONS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Cylinders3D:quaternions", "rerun.components.RotationQuat");

    /**
     * Rotations via quaternion.
     * 
     * If no rotation is specified, the cylinders align with the +Z axis of the local coordinate system.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorQuaternions() {
        return DESCRIPTOR_QUATERNIONS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Cylinders3D:colors", "rerun.components.Color");

    /**
     * Optional colors for the cylinders.
     * 
     * Alpha channel is used for transparency for solid fill-mode.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColors() {
        return DESCRIPTOR_COLORS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LINE_RADII = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Cylinders3D:line_radii", "rerun.components.Radius");

    /**
     * Optional radii for the lines used when the cylinder is rendered as a wireframe.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLineRadii() {
        return DESCRIPTOR_LINE_RADII;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FILL_MODE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Cylinders3D:fill_mode", "rerun.components.FillMode");

    /**
     * Optionally choose whether the cylinders are drawn with lines or solid.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFillMode() {
        return DESCRIPTOR_FILL_MODE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LABELS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Cylinders3D:labels", "rerun.components.Text");

    /**
     * Optional text labels for the cylinders, which will be located at their centers.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLabels() {
        return DESCRIPTOR_LABELS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SHOW_LABELS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Cylinders3D:show_labels", "rerun.components.ShowLabels");

    /**
     * Whether the text labels should be shown.
     * 
     * If not set, labels will automatically appear when there is exactly one label for this entity
     * or the number of instances on this entity is under a certain threshold.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorShowLabels() {
        return DESCRIPTOR_SHOW_LABELS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CLASS_IDS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Cylinders3D:class_ids", "rerun.components.ClassId");

    /**
     * Optional class ID for the ellipsoids.
     * 
     * The class ID provides colors and labels if not specified explicitly.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorClassIds() {
        return DESCRIPTOR_CLASS_IDS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorLengths(), descriptorRadii());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorCenters(), descriptorColors());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorRotationAxisAngles(), descriptorQuaternions(), descriptorLineRadii(), descriptorFillMode(), descriptorLabels(), descriptorShowLabels(), descriptorClassIds());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.Length.batch(descriptorLengths(), allocator, lengths));
        batches.add(org.teamvan.moneyshift.components.Radius.batch(descriptorRadii(), allocator, radii));
        if (centers != null) {
            batches.add(org.teamvan.moneyshift.components.Translation3D.batch(descriptorCenters(), allocator, centers));
        }
        if (rotationAxisAngles != null) {
            batches.add(org.teamvan.moneyshift.components.RotationAxisAngle.batch(descriptorRotationAxisAngles(), allocator, rotationAxisAngles));
        }
        if (quaternions != null) {
            batches.add(org.teamvan.moneyshift.components.RotationQuat.batch(descriptorQuaternions(), allocator, quaternions));
        }
        if (colors != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColors(), allocator, colors));
        }
        if (lineRadii != null) {
            batches.add(org.teamvan.moneyshift.components.Radius.batch(descriptorLineRadii(), allocator, lineRadii));
        }
        if (fillMode != null) {
            batches.add(org.teamvan.moneyshift.components.FillMode.batch(descriptorFillMode(), allocator, fillMode));
        }
        if (labels != null) {
            batches.add(org.teamvan.moneyshift.components.Text.batch(descriptorLabels(), allocator, labels));
        }
        if (showLabels != null) {
            batches.add(org.teamvan.moneyshift.components.ShowLabels.batch(descriptorShowLabels(), allocator, showLabels));
        }
        if (classIds != null) {
            batches.add(org.teamvan.moneyshift.components.ClassId.batch(descriptorClassIds(), allocator, classIds));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.Length> lengths;
        private java.util.List<org.teamvan.moneyshift.components.Radius> radii;
        private java.util.List<org.teamvan.moneyshift.components.Translation3D> centers;
        private java.util.List<org.teamvan.moneyshift.components.RotationAxisAngle> rotationAxisAngles;
        private java.util.List<org.teamvan.moneyshift.components.RotationQuat> quaternions;
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private java.util.List<org.teamvan.moneyshift.components.Radius> lineRadii;
        private org.teamvan.moneyshift.components.FillMode fillMode;
        private java.util.List<org.teamvan.moneyshift.components.Text> labels;
        private org.teamvan.moneyshift.components.ShowLabels showLabels;
        private java.util.List<org.teamvan.moneyshift.components.ClassId> classIds;
        private Builder() {}

        public Builder withLengths(java.util.List<org.teamvan.moneyshift.components.Length> value) {
            this.lengths = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withLengths(float... value) {
            java.util.List<org.teamvan.moneyshift.components.Length> values = new java.util.ArrayList<>(value.length);
            for (float v : value) {
                values.add(new org.teamvan.moneyshift.components.Length(v));
            }
            return withLengths(values);
        }

        public Builder withLengths(org.teamvan.moneyshift.components.Length... value) {
            return withLengths(java.util.List.of(value));
        }

        public Builder withRadii(java.util.List<org.teamvan.moneyshift.components.Radius> value) {
            this.radii = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withRadii(float... value) {
            java.util.List<org.teamvan.moneyshift.components.Radius> values = new java.util.ArrayList<>(value.length);
            for (float v : value) {
                values.add(new org.teamvan.moneyshift.components.Radius(v));
            }
            return withRadii(values);
        }

        public Builder withRadii(org.teamvan.moneyshift.components.Radius... value) {
            return withRadii(java.util.List.of(value));
        }

        public Builder withCenters(java.util.List<org.teamvan.moneyshift.components.Translation3D> value) {
            this.centers = java.util.List.copyOf(value);
            return this;
        }

        public Builder withCenters(org.teamvan.moneyshift.components.Translation3D... value) {
            return withCenters(java.util.List.of(value));
        }

        public Builder withRotationAxisAngles(java.util.List<org.teamvan.moneyshift.components.RotationAxisAngle> value) {
            this.rotationAxisAngles = java.util.List.copyOf(value);
            return this;
        }

        public Builder withRotationAxisAngles(org.teamvan.moneyshift.components.RotationAxisAngle... value) {
            return withRotationAxisAngles(java.util.List.of(value));
        }

        public Builder withQuaternions(java.util.List<org.teamvan.moneyshift.components.RotationQuat> value) {
            this.quaternions = java.util.List.copyOf(value);
            return this;
        }

        public Builder withQuaternions(org.teamvan.moneyshift.components.RotationQuat... value) {
            return withQuaternions(java.util.List.of(value));
        }

        public Builder withColors(java.util.List<org.teamvan.moneyshift.components.Color> value) {
            this.colors = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withColors(long... value) {
            java.util.List<org.teamvan.moneyshift.components.Color> values = new java.util.ArrayList<>(value.length);
            for (long v : value) {
                values.add(new org.teamvan.moneyshift.components.Color(v));
            }
            return withColors(values);
        }

        public Builder withColors(org.teamvan.moneyshift.components.Color... value) {
            return withColors(java.util.List.of(value));
        }

        public Builder withLineRadii(java.util.List<org.teamvan.moneyshift.components.Radius> value) {
            this.lineRadii = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withLineRadii(float... value) {
            java.util.List<org.teamvan.moneyshift.components.Radius> values = new java.util.ArrayList<>(value.length);
            for (float v : value) {
                values.add(new org.teamvan.moneyshift.components.Radius(v));
            }
            return withLineRadii(values);
        }

        public Builder withLineRadii(org.teamvan.moneyshift.components.Radius... value) {
            return withLineRadii(java.util.List.of(value));
        }

        public Builder withFillMode(org.teamvan.moneyshift.components.FillMode value) {
            this.fillMode = value;
            return this;
        }

        public Builder withLabels(java.util.List<org.teamvan.moneyshift.components.Text> value) {
            this.labels = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withLabels(java.lang.String... value) {
            java.util.List<org.teamvan.moneyshift.components.Text> values = new java.util.ArrayList<>(value.length);
            for (java.lang.String v : value) {
                values.add(new org.teamvan.moneyshift.components.Text(v));
            }
            return withLabels(values);
        }

        public Builder withLabels(org.teamvan.moneyshift.components.Text... value) {
            return withLabels(java.util.List.of(value));
        }

        public Builder withShowLabels(org.teamvan.moneyshift.components.ShowLabels value) {
            this.showLabels = value;
            return this;
        }

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withShowLabels(boolean value) {
            return withShowLabels(new org.teamvan.moneyshift.components.ShowLabels(value));
        }

        public Builder withClassIds(java.util.List<org.teamvan.moneyshift.components.ClassId> value) {
            this.classIds = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code int} value(s) directly. */
        public Builder withClassIds(int... value) {
            java.util.List<org.teamvan.moneyshift.components.ClassId> values = new java.util.ArrayList<>(value.length);
            for (int v : value) {
                values.add(new org.teamvan.moneyshift.components.ClassId(v));
            }
            return withClassIds(values);
        }

        public Builder withClassIds(org.teamvan.moneyshift.components.ClassId... value) {
            return withClassIds(java.util.List.of(value));
        }

        public Cylinders3D build() {
            return new Cylinders3D(lengths, radii, centers, rotationAxisAngles, quaternions, colors, lineRadii, fillMode, labels, showLabels, classIds);
        }
    }

    public static Cylinders3D of(
        java.util.List<org.teamvan.moneyshift.components.Length> lengths,
        java.util.List<org.teamvan.moneyshift.components.Radius> radii
    ) {
        return new Cylinders3D(lengths, radii, null, null, null, null, null, null, null, null, null);
    }
}
