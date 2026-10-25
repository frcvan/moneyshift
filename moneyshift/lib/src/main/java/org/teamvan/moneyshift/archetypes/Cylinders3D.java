// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * 3D cylinders with flat caps.
 * 
 * This archetype is for cylinder primitives defined by their axial length and radius.
 * For points whose radii are for visualization purposes, use {@link org.teamvan.moneyshift.archetypes.Points3D rerun::archetypes::Points3D} instead.
 * 
 * Orienting and placing cylinders forms a separate transform that is applied prior to {@link org.teamvan.moneyshift.archetypes.InstancePoses3D rerun::archetypes::InstancePoses3D} and {@link org.teamvan.moneyshift.archetypes.Transform3D rerun::archetypes::Transform3D}.
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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    private static final class ComponentsCache {
        final long mask;
        final java.lang.String components;

        ComponentsCache(long mask, java.lang.String components) {
            this.mask = mask;
            this.components = components;
        }
    }

    private static volatile ComponentsCache cachedComponents;

    private static java.lang.String componentsFor(long mask) {
        ComponentsCache cached = cachedComponents;
        if (cached != null && cached.mask == mask) {
            return cached.components;
        }
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if ((mask & 1L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("lengths").append('=').append(org.teamvan.moneyshift.components.Length.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("radii").append('=').append(org.teamvan.moneyshift.components.Radius.COMPONENT_TYPE);
        }
        if ((mask & 4L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("centers").append('=').append(org.teamvan.moneyshift.components.Translation3D.COMPONENT_TYPE);
        }
        if ((mask & 8L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("rotation_axis_angles").append('=').append(org.teamvan.moneyshift.components.RotationAxisAngle.COMPONENT_TYPE);
        }
        if ((mask & 16L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("quaternions").append('=').append(org.teamvan.moneyshift.components.RotationQuat.COMPONENT_TYPE);
        }
        if ((mask & 32L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if ((mask & 64L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("line_radii").append('=').append(org.teamvan.moneyshift.components.Radius.COMPONENT_TYPE);
        }
        if ((mask & 128L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("fill_mode").append('=').append(org.teamvan.moneyshift.components.FillMode.COMPONENT_TYPE);
        }
        if ((mask & 256L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("labels").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        if ((mask & 512L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("show_labels").append('=').append(org.teamvan.moneyshift.components.ShowLabels.COMPONENT_TYPE);
        }
        if ((mask & 1024L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("class_ids").append('=').append(org.teamvan.moneyshift.components.ClassId.COMPONENT_TYPE);
        }
        java.lang.String joined = components.toString();
        cachedComponents = new ComponentsCache(mask, joined);
        return joined;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        long mask = 0L;
        if (!lengths.isEmpty()) mask |= 1L;
        if (!radii.isEmpty()) mask |= 2L;
        if (centers != null && !centers.isEmpty()) mask |= 4L;
        if (rotationAxisAngles != null && !rotationAxisAngles.isEmpty()) mask |= 8L;
        if (quaternions != null && !quaternions.isEmpty()) mask |= 16L;
        if (colors != null && !colors.isEmpty()) mask |= 32L;
        if (lineRadii != null && !lineRadii.isEmpty()) mask |= 64L;
        if (fillMode != null) mask |= 128L;
        if (labels != null && !labels.isEmpty()) mask |= 256L;
        if (showLabels != null) mask |= 512L;
        if (classIds != null && !classIds.isEmpty()) mask |= 1024L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (!lengths.isEmpty()) {
            org.teamvan.moneyshift.components.Length.writeColumnsStatic(w, "lengths", lengths);
        }
        if (!radii.isEmpty()) {
            org.teamvan.moneyshift.components.Radius.writeColumnsStatic(w, "radii", radii);
        }
        if (centers != null && !centers.isEmpty()) {
            org.teamvan.moneyshift.components.Translation3D.writeColumnsStatic(w, "centers", centers);
        }
        if (rotationAxisAngles != null && !rotationAxisAngles.isEmpty()) {
            org.teamvan.moneyshift.components.RotationAxisAngle.writeColumnsStatic(w, "rotation_axis_angles", rotationAxisAngles);
        }
        if (quaternions != null && !quaternions.isEmpty()) {
            org.teamvan.moneyshift.components.RotationQuat.writeColumnsStatic(w, "quaternions", quaternions);
        }
        if (colors != null && !colors.isEmpty()) {
            org.teamvan.moneyshift.components.Color.writeColumnsStatic(w, "colors", colors);
        }
        if (lineRadii != null && !lineRadii.isEmpty()) {
            org.teamvan.moneyshift.components.Radius.writeColumnsStatic(w, "line_radii", lineRadii);
        }
        if (fillMode != null) {
            org.teamvan.moneyshift.components.FillMode.writeColumnsStatic(w, "fill_mode", java.util.List.of(fillMode));
        }
        if (labels != null && !labels.isEmpty()) {
            org.teamvan.moneyshift.components.Text.writeColumnsStatic(w, "labels", labels);
        }
        if (showLabels != null) {
            org.teamvan.moneyshift.components.ShowLabels.writeColumnsStatic(w, "show_labels", java.util.List.of(showLabels));
        }
        if (classIds != null && !classIds.isEmpty()) {
            org.teamvan.moneyshift.components.ClassId.writeColumnsStatic(w, "class_ids", classIds);
        }
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
