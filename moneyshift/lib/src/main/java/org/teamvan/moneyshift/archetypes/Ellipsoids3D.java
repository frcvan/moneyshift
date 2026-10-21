// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * 3D ellipsoids or spheres.
 * 
 * This archetype is for ellipsoids or spheres whose size is a key part of the data
 * (e.g. a bounding sphere).
 * For points whose radii are for the sake of visualization, use {@link org.teamvan.moneyshift.archetypes.Points3D} instead.
 * 
 * If there's more instance poses than half sizes, the last ellipsoid/sphere's orientation will be repeated for the remaining poses.
 * Orienting and placing ellipsoids/spheres forms a separate transform that is applied prior to {@link org.teamvan.moneyshift.archetypes.InstancePoses3D} and {@link org.teamvan.moneyshift.archetypes.Transform3D}.
 * 
 */
public record Ellipsoids3D(
    /**
     * For each ellipsoid, half of its size on its three axes.
     * 
     * If all components are equal, then it is a sphere with that radius.
     */
    java.util.List<org.teamvan.moneyshift.components.HalfSize3D> halfSizes,
    /**
     * Optional center positions of the ellipsoids.
     * 
     * If not specified, the centers will be at (0, 0, 0).
     */
    java.util.List<org.teamvan.moneyshift.components.Translation3D> centers,
    /**
     * Rotations via axis + angle.
     * 
     * If no rotation is specified, the axes of the ellipsoid align with the axes of the local coordinate system.
     */
    java.util.List<org.teamvan.moneyshift.components.RotationAxisAngle> rotationAxisAngles,
    /**
     * Rotations via quaternion.
     * 
     * If no rotation is specified, the axes of the ellipsoid align with the axes of the local coordinate system.
     */
    java.util.List<org.teamvan.moneyshift.components.RotationQuat> quaternions,
    /**
     * Optional colors for the ellipsoids.
     * 
     * Alpha channel is used for transparency for solid fill-mode.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * Optional radii for the lines used when the ellipsoid is rendered as a wireframe.
     */
    java.util.List<org.teamvan.moneyshift.components.Radius> lineRadii,
    /**
     * Optionally choose whether the ellipsoids are drawn with lines or solid.
     */
    org.teamvan.moneyshift.components.FillMode fillMode,
    /**
     * Optional text labels for the ellipsoids.
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
    public Ellipsoids3D {
        java.util.Objects.requireNonNull(halfSizes, "halfSizes");
        halfSizes = java.util.List.copyOf(halfSizes);
        if (centers != null) { centers = java.util.List.copyOf(centers); }
        if (rotationAxisAngles != null) { rotationAxisAngles = java.util.List.copyOf(rotationAxisAngles); }
        if (quaternions != null) { quaternions = java.util.List.copyOf(quaternions); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (lineRadii != null) { lineRadii = java.util.List.copyOf(lineRadii); }
        if (labels != null) { labels = java.util.List.copyOf(labels); }
        if (classIds != null) { classIds = java.util.List.copyOf(classIds); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Ellipsoids3D";
    public static final int NUM_COMPONENTS = 10;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_HALF_SIZES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipsoids3D:half_sizes", "rerun.components.HalfSize3D");

    /**
     * For each ellipsoid, half of its size on its three axes.
     * 
     * If all components are equal, then it is a sphere with that radius.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorHalfSizes() {
        return DESCRIPTOR_HALF_SIZES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CENTERS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipsoids3D:centers", "rerun.components.Translation3D");

    /**
     * Optional center positions of the ellipsoids.
     * 
     * If not specified, the centers will be at (0, 0, 0).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorCenters() {
        return DESCRIPTOR_CENTERS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ROTATION_AXIS_ANGLES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipsoids3D:rotation_axis_angles", "rerun.components.RotationAxisAngle");

    /**
     * Rotations via axis + angle.
     * 
     * If no rotation is specified, the axes of the ellipsoid align with the axes of the local coordinate system.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRotationAxisAngles() {
        return DESCRIPTOR_ROTATION_AXIS_ANGLES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_QUATERNIONS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipsoids3D:quaternions", "rerun.components.RotationQuat");

    /**
     * Rotations via quaternion.
     * 
     * If no rotation is specified, the axes of the ellipsoid align with the axes of the local coordinate system.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorQuaternions() {
        return DESCRIPTOR_QUATERNIONS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipsoids3D:colors", "rerun.components.Color");

    /**
     * Optional colors for the ellipsoids.
     * 
     * Alpha channel is used for transparency for solid fill-mode.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColors() {
        return DESCRIPTOR_COLORS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LINE_RADII = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipsoids3D:line_radii", "rerun.components.Radius");

    /**
     * Optional radii for the lines used when the ellipsoid is rendered as a wireframe.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLineRadii() {
        return DESCRIPTOR_LINE_RADII;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FILL_MODE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipsoids3D:fill_mode", "rerun.components.FillMode");

    /**
     * Optionally choose whether the ellipsoids are drawn with lines or solid.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFillMode() {
        return DESCRIPTOR_FILL_MODE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LABELS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipsoids3D:labels", "rerun.components.Text");

    /**
     * Optional text labels for the ellipsoids.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLabels() {
        return DESCRIPTOR_LABELS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SHOW_LABELS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipsoids3D:show_labels", "rerun.components.ShowLabels");

    /**
     * Whether the text labels should be shown.
     * 
     * If not set, labels will automatically appear when there is exactly one label for this entity
     * or the number of instances on this entity is under a certain threshold.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorShowLabels() {
        return DESCRIPTOR_SHOW_LABELS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CLASS_IDS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipsoids3D:class_ids", "rerun.components.ClassId");

    /**
     * Optional class ID for the ellipsoids.
     * 
     * The class ID provides colors and labels if not specified explicitly.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorClassIds() {
        return DESCRIPTOR_CLASS_IDS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorHalfSizes());
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
        batches.add(org.teamvan.moneyshift.components.HalfSize3D.batch(descriptorHalfSizes(), allocator, halfSizes));
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
        private java.util.List<org.teamvan.moneyshift.components.HalfSize3D> halfSizes;
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

        public Builder withHalfSizes(java.util.List<org.teamvan.moneyshift.components.HalfSize3D> value) {
            this.halfSizes = java.util.List.copyOf(value);
            return this;
        }

        public Builder withHalfSizes(org.teamvan.moneyshift.components.HalfSize3D... value) {
            return withHalfSizes(java.util.List.of(value));
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

        public Builder withColors(org.teamvan.moneyshift.components.Color... value) {
            return withColors(java.util.List.of(value));
        }

        public Builder withLineRadii(java.util.List<org.teamvan.moneyshift.components.Radius> value) {
            this.lineRadii = java.util.List.copyOf(value);
            return this;
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

        public Builder withLabels(org.teamvan.moneyshift.components.Text... value) {
            return withLabels(java.util.List.of(value));
        }

        public Builder withShowLabels(org.teamvan.moneyshift.components.ShowLabels value) {
            this.showLabels = value;
            return this;
        }

        public Builder withClassIds(java.util.List<org.teamvan.moneyshift.components.ClassId> value) {
            this.classIds = java.util.List.copyOf(value);
            return this;
        }

        public Builder withClassIds(org.teamvan.moneyshift.components.ClassId... value) {
            return withClassIds(java.util.List.of(value));
        }

        public Ellipsoids3D build() {
            return new Ellipsoids3D(halfSizes, centers, rotationAxisAngles, quaternions, colors, lineRadii, fillMode, labels, showLabels, classIds);
        }
    }

    public static Ellipsoids3D of(
        java.util.List<org.teamvan.moneyshift.components.HalfSize3D> halfSizes
    ) {
        return new Ellipsoids3D(halfSizes, null, null, null, null, null, null, null, null, null);
    }
}
