// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * 3D ellipsoids or spheres.
 * 
 * This archetype is for ellipsoids or spheres whose size is a key part of the data
 * (e.g. a bounding sphere).
 * For points whose radii are for the sake of visualization, use {@link org.teamvan.moneyshift.archetypes.Points3D rerun::archetypes::Points3D} instead.
 * 
 * If there's more instance poses than half sizes, the last ellipsoid/sphere's orientation will be repeated for the remaining poses.
 * Orienting and placing ellipsoids/spheres forms a separate transform that is applied prior to {@link org.teamvan.moneyshift.archetypes.InstancePoses3D rerun::archetypes::InstancePoses3D} and {@link org.teamvan.moneyshift.archetypes.Transform3D rerun::archetypes::Transform3D}.
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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (!halfSizes.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("half_sizes").append('=').append(org.teamvan.moneyshift.components.HalfSize3D.COMPONENT_TYPE);
        }
        if (centers != null && !centers.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("centers").append('=').append(org.teamvan.moneyshift.components.Translation3D.COMPONENT_TYPE);
        }
        if (rotationAxisAngles != null && !rotationAxisAngles.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("rotation_axis_angles").append('=').append(org.teamvan.moneyshift.components.RotationAxisAngle.COMPONENT_TYPE);
        }
        if (quaternions != null && !quaternions.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("quaternions").append('=').append(org.teamvan.moneyshift.components.RotationQuat.COMPONENT_TYPE);
        }
        if (colors != null && !colors.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if (lineRadii != null && !lineRadii.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("line_radii").append('=').append(org.teamvan.moneyshift.components.Radius.COMPONENT_TYPE);
        }
        if (fillMode != null) {
            if (components.length() > 0) components.append(',');
            components.append("fill_mode").append('=').append(org.teamvan.moneyshift.components.FillMode.COMPONENT_TYPE);
        }
        if (labels != null && !labels.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("labels").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        if (showLabels != null) {
            if (components.length() > 0) components.append(',');
            components.append("show_labels").append('=').append(org.teamvan.moneyshift.components.ShowLabels.COMPONENT_TYPE);
        }
        if (classIds != null && !classIds.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("class_ids").append('=').append(org.teamvan.moneyshift.components.ClassId.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (!halfSizes.isEmpty()) {
            org.teamvan.moneyshift.components.HalfSize3D.writeColumnsStatic(w, "half_sizes", halfSizes);
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
