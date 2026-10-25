// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A 3D point cloud with positions and optional colors, radii, labels, etc.
 * 
 * If there are multiple instance poses, the entire point cloud will be repeated for each of the poses.
 * 
 */
public record Points3D(
    /**
     * All the 3D positions at which the point cloud shows points.
     */
    java.util.List<org.teamvan.moneyshift.components.Position3D> positions,
    /**
     * Optional radii for the points, effectively turning them into circles.
     */
    java.util.List<org.teamvan.moneyshift.components.Radius> radii,
    /**
     * Optional colors for the points.
     * 
     * 
     * By default, the alpha channel affects brightness rather than transparency.
     * TODO(#1611): To use the alpha channel for transparency, enable the experimental "Transparent point clouds" feature flag.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * Optional text labels for the points.
     * 
     * If there's a single label present, it will be placed at the center of the entity.
     * Otherwise, each instance will have its own label.
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
     * How points should be shaded.
     * 
     * If not set, points are rendered with {@link org.teamvan.moneyshift.components.PointShading#Gradient rerun::components::PointShading::Gradient} by default.
     */
    org.teamvan.moneyshift.components.PointShading pointShading,
    /**
     * Optional class Ids for the points.
     * 
     * The {@link org.teamvan.moneyshift.components.ClassId rerun::components::ClassId} provides colors and labels if not specified explicitly.
     */
    java.util.List<org.teamvan.moneyshift.components.ClassId> classIds,
    /**
     * Optional keypoint IDs for the points, identifying them within a class.
     * 
     * If keypoint IDs are passed in but no {@link org.teamvan.moneyshift.components.ClassId rerun::components::ClassId}s were specified, the {@link org.teamvan.moneyshift.components.ClassId rerun::components::ClassId} will
     * default to 0.
     * This is useful to identify points within a single classification (which is identified
     * with {@code class_id}).
     * E.g. the classification might be 'Person' and the keypoints refer to joints on a
     * detected skeleton.
     */
    java.util.List<org.teamvan.moneyshift.components.KeypointId> keypointIds
) implements org.teamvan.moneyshift.Archetype {
    public Points3D {
        java.util.Objects.requireNonNull(positions, "positions");
        positions = java.util.List.copyOf(positions);
        if (radii != null) { radii = java.util.List.copyOf(radii); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (labels != null) { labels = java.util.List.copyOf(labels); }
        if (classIds != null) { classIds = java.util.List.copyOf(classIds); }
        if (keypointIds != null) { keypointIds = java.util.List.copyOf(keypointIds); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Points3D";
    public static final int NUM_COMPONENTS = 8;

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
            components.append("positions").append('=').append(org.teamvan.moneyshift.components.Position3D.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("radii").append('=').append(org.teamvan.moneyshift.components.Radius.COMPONENT_TYPE);
        }
        if ((mask & 4L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if ((mask & 8L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("labels").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        if ((mask & 16L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("show_labels").append('=').append(org.teamvan.moneyshift.components.ShowLabels.COMPONENT_TYPE);
        }
        if ((mask & 32L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("point_shading").append('=').append(org.teamvan.moneyshift.components.PointShading.COMPONENT_TYPE);
        }
        if ((mask & 64L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("class_ids").append('=').append(org.teamvan.moneyshift.components.ClassId.COMPONENT_TYPE);
        }
        if ((mask & 128L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("keypoint_ids").append('=').append(org.teamvan.moneyshift.components.KeypointId.COMPONENT_TYPE);
        }
        java.lang.String joined = components.toString();
        cachedComponents = new ComponentsCache(mask, joined);
        return joined;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        long mask = 0L;
        if (!positions.isEmpty()) mask |= 1L;
        if (radii != null && !radii.isEmpty()) mask |= 2L;
        if (colors != null && !colors.isEmpty()) mask |= 4L;
        if (labels != null && !labels.isEmpty()) mask |= 8L;
        if (showLabels != null) mask |= 16L;
        if (pointShading != null) mask |= 32L;
        if (classIds != null && !classIds.isEmpty()) mask |= 64L;
        if (keypointIds != null && !keypointIds.isEmpty()) mask |= 128L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (!positions.isEmpty()) {
            org.teamvan.moneyshift.components.Position3D.writeColumnsStatic(w, "positions", positions);
        }
        if (radii != null && !radii.isEmpty()) {
            org.teamvan.moneyshift.components.Radius.writeColumnsStatic(w, "radii", radii);
        }
        if (colors != null && !colors.isEmpty()) {
            org.teamvan.moneyshift.components.Color.writeColumnsStatic(w, "colors", colors);
        }
        if (labels != null && !labels.isEmpty()) {
            org.teamvan.moneyshift.components.Text.writeColumnsStatic(w, "labels", labels);
        }
        if (showLabels != null) {
            org.teamvan.moneyshift.components.ShowLabels.writeColumnsStatic(w, "show_labels", java.util.List.of(showLabels));
        }
        if (pointShading != null) {
            org.teamvan.moneyshift.components.PointShading.writeColumnsStatic(w, "point_shading", java.util.List.of(pointShading));
        }
        if (classIds != null && !classIds.isEmpty()) {
            org.teamvan.moneyshift.components.ClassId.writeColumnsStatic(w, "class_ids", classIds);
        }
        if (keypointIds != null && !keypointIds.isEmpty()) {
            org.teamvan.moneyshift.components.KeypointId.writeColumnsStatic(w, "keypoint_ids", keypointIds);
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.Position3D> positions;
        private java.util.List<org.teamvan.moneyshift.components.Radius> radii;
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private java.util.List<org.teamvan.moneyshift.components.Text> labels;
        private org.teamvan.moneyshift.components.ShowLabels showLabels;
        private org.teamvan.moneyshift.components.PointShading pointShading;
        private java.util.List<org.teamvan.moneyshift.components.ClassId> classIds;
        private java.util.List<org.teamvan.moneyshift.components.KeypointId> keypointIds;
        private Builder() {}

        public Builder withPositions(java.util.List<org.teamvan.moneyshift.components.Position3D> value) {
            this.positions = java.util.List.copyOf(value);
            return this;
        }

        public Builder withPositions(org.teamvan.moneyshift.components.Position3D... value) {
            return withPositions(java.util.List.of(value));
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

        public Builder withPointShading(org.teamvan.moneyshift.components.PointShading value) {
            this.pointShading = value;
            return this;
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

        public Builder withKeypointIds(java.util.List<org.teamvan.moneyshift.components.KeypointId> value) {
            this.keypointIds = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code int} value(s) directly. */
        public Builder withKeypointIds(int... value) {
            java.util.List<org.teamvan.moneyshift.components.KeypointId> values = new java.util.ArrayList<>(value.length);
            for (int v : value) {
                values.add(new org.teamvan.moneyshift.components.KeypointId(v));
            }
            return withKeypointIds(values);
        }

        public Builder withKeypointIds(org.teamvan.moneyshift.components.KeypointId... value) {
            return withKeypointIds(java.util.List.of(value));
        }

        public Points3D build() {
            return new Points3D(positions, radii, colors, labels, showLabels, pointShading, classIds, keypointIds);
        }
    }

    public static Points3D of(
        java.util.List<org.teamvan.moneyshift.components.Position3D> positions
    ) {
        return new Points3D(positions, null, null, null, null, null, null, null);
    }
}
