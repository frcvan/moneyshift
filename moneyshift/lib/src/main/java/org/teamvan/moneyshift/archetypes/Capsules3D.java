// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * 3D capsules; cylinders with hemispherical caps.
 * 
 * Capsules are defined by two endpoints (the centers of their end cap spheres), which are located
 * at (0, 0, 0) and (0, 0, length), that is, extending along the positive direction of the Z axis.
 * Capsules in other orientations may be produced by applying a rotation to the entity or
 * instances.
 * 
 * If there's more instance poses than lengths &amp; radii, the last capsule's orientation will be repeated for the remaining poses.
 * Orienting and placing capsules forms a separate transform that is applied prior to {@link org.teamvan.moneyshift.archetypes.InstancePoses3D} and {@link org.teamvan.moneyshift.archetypes.Transform3D}.
 * 
 */
public record Capsules3D(
    /**
     * Lengths of the capsules, defined as the distance between the centers of the endcaps.
     */
    java.util.List<org.teamvan.moneyshift.components.Length> lengths,
    /**
     * Radii of the capsules.
     */
    java.util.List<org.teamvan.moneyshift.components.Radius> radii,
    /**
     * Optional translations of the capsules.
     * 
     * If not specified, one end of each capsule will be at (0, 0, 0).
     */
    java.util.List<org.teamvan.moneyshift.components.Translation3D> translations,
    /**
     * Rotations via axis + angle.
     * 
     * If no rotation is specified, the capsules align with the +Z axis of the local coordinate system.
     */
    java.util.List<org.teamvan.moneyshift.components.RotationAxisAngle> rotationAxisAngles,
    /**
     * Rotations via quaternion.
     * 
     * If no rotation is specified, the capsules align with the +Z axis of the local coordinate system.
     */
    java.util.List<org.teamvan.moneyshift.components.RotationQuat> quaternions,
    /**
     * Optional colors for the capsules.
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
     * Optional text labels for the capsules, which will be located at their centers.
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
    public Capsules3D {
        java.util.Objects.requireNonNull(lengths, "lengths");
        lengths = java.util.List.copyOf(lengths);
        java.util.Objects.requireNonNull(radii, "radii");
        radii = java.util.List.copyOf(radii);
        if (translations != null) { translations = java.util.List.copyOf(translations); }
        if (rotationAxisAngles != null) { rotationAxisAngles = java.util.List.copyOf(rotationAxisAngles); }
        if (quaternions != null) { quaternions = java.util.List.copyOf(quaternions); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (lineRadii != null) { lineRadii = java.util.List.copyOf(lineRadii); }
        if (labels != null) { labels = java.util.List.copyOf(labels); }
        if (classIds != null) { classIds = java.util.List.copyOf(classIds); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Capsules3D";
    public static final int NUM_COMPONENTS = 11;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LENGTHS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Capsules3D:lengths", "rerun.components.Length");

    /**
     * Lengths of the capsules, defined as the distance between the centers of the endcaps.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLengths() {
        return DESCRIPTOR_LENGTHS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_RADII = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Capsules3D:radii", "rerun.components.Radius");

    /**
     * Radii of the capsules.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRadii() {
        return DESCRIPTOR_RADII;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TRANSLATIONS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Capsules3D:translations", "rerun.components.Translation3D");

    /**
     * Optional translations of the capsules.
     * 
     * If not specified, one end of each capsule will be at (0, 0, 0).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTranslations() {
        return DESCRIPTOR_TRANSLATIONS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ROTATION_AXIS_ANGLES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Capsules3D:rotation_axis_angles", "rerun.components.RotationAxisAngle");

    /**
     * Rotations via axis + angle.
     * 
     * If no rotation is specified, the capsules align with the +Z axis of the local coordinate system.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRotationAxisAngles() {
        return DESCRIPTOR_ROTATION_AXIS_ANGLES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_QUATERNIONS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Capsules3D:quaternions", "rerun.components.RotationQuat");

    /**
     * Rotations via quaternion.
     * 
     * If no rotation is specified, the capsules align with the +Z axis of the local coordinate system.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorQuaternions() {
        return DESCRIPTOR_QUATERNIONS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Capsules3D:colors", "rerun.components.Color");

    /**
     * Optional colors for the capsules.
     * 
     * Alpha channel is used for transparency for solid fill-mode.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColors() {
        return DESCRIPTOR_COLORS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LINE_RADII = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Capsules3D:line_radii", "rerun.components.Radius");

    /**
     * Optional radii for the lines used when the cylinder is rendered as a wireframe.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLineRadii() {
        return DESCRIPTOR_LINE_RADII;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FILL_MODE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Capsules3D:fill_mode", "rerun.components.FillMode");

    /**
     * Optionally choose whether the cylinders are drawn with lines or solid.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFillMode() {
        return DESCRIPTOR_FILL_MODE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LABELS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Capsules3D:labels", "rerun.components.Text");

    /**
     * Optional text labels for the capsules, which will be located at their centers.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLabels() {
        return DESCRIPTOR_LABELS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SHOW_LABELS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Capsules3D:show_labels", "rerun.components.ShowLabels");

    /**
     * Whether the text labels should be shown.
     * 
     * If not set, labels will automatically appear when there is exactly one label for this entity
     * or the number of instances on this entity is under a certain threshold.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorShowLabels() {
        return DESCRIPTOR_SHOW_LABELS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CLASS_IDS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Capsules3D:class_ids", "rerun.components.ClassId");

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
        return java.util.List.of(descriptorTranslations(), descriptorColors());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorRotationAxisAngles(), descriptorQuaternions(), descriptorLineRadii(), descriptorFillMode(), descriptorLabels(), descriptorShowLabels(), descriptorClassIds());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.Length.batch(descriptorLengths(), allocator, lengths));
        batches.add(org.teamvan.moneyshift.components.Radius.batch(descriptorRadii(), allocator, radii));
        if (translations != null) {
            batches.add(org.teamvan.moneyshift.components.Translation3D.batch(descriptorTranslations(), allocator, translations));
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
        private java.util.List<org.teamvan.moneyshift.components.Translation3D> translations;
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

        public Builder withTranslations(java.util.List<org.teamvan.moneyshift.components.Translation3D> value) {
            this.translations = java.util.List.copyOf(value);
            return this;
        }

        public Builder withTranslations(org.teamvan.moneyshift.components.Translation3D... value) {
            return withTranslations(java.util.List.of(value));
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

        public Capsules3D build() {
            return new Capsules3D(lengths, radii, translations, rotationAxisAngles, quaternions, colors, lineRadii, fillMode, labels, showLabels, classIds);
        }
    }

    public static Capsules3D of(
        java.util.List<org.teamvan.moneyshift.components.Length> lengths,
        java.util.List<org.teamvan.moneyshift.components.Radius> radii
    ) {
        return new Capsules3D(lengths, radii, null, null, null, null, null, null, null, null, null);
    }
}
