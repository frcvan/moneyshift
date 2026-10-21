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
     * If not set, points are rendered with {@link org.teamvan.moneyshift.components.PointShading.Gradient} by default.
     */
    org.teamvan.moneyshift.components.PointShading pointShading,
    /**
     * Optional class Ids for the points.
     * 
     * The {@link org.teamvan.moneyshift.components.ClassId} provides colors and labels if not specified explicitly.
     */
    java.util.List<org.teamvan.moneyshift.components.ClassId> classIds,
    /**
     * Optional keypoint IDs for the points, identifying them within a class.
     * 
     * If keypoint IDs are passed in but no {@link org.teamvan.moneyshift.components.ClassId}s were specified, the {@link org.teamvan.moneyshift.components.ClassId} will
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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_POSITIONS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Points3D:positions", "rerun.components.Position3D");

    /**
     * All the 3D positions at which the point cloud shows points.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorPositions() {
        return DESCRIPTOR_POSITIONS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_RADII = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Points3D:radii", "rerun.components.Radius");

    /**
     * Optional radii for the points, effectively turning them into circles.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRadii() {
        return DESCRIPTOR_RADII;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Points3D:colors", "rerun.components.Color");

    /**
     * Optional colors for the points.
     * 
     * 
     * By default, the alpha channel affects brightness rather than transparency.
     * TODO(#1611): To use the alpha channel for transparency, enable the experimental "Transparent point clouds" feature flag.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColors() {
        return DESCRIPTOR_COLORS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LABELS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Points3D:labels", "rerun.components.Text");

    /**
     * Optional text labels for the points.
     * 
     * If there's a single label present, it will be placed at the center of the entity.
     * Otherwise, each instance will have its own label.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLabels() {
        return DESCRIPTOR_LABELS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SHOW_LABELS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Points3D:show_labels", "rerun.components.ShowLabels");

    /**
     * Whether the text labels should be shown.
     * 
     * If not set, labels will automatically appear when there is exactly one label for this entity
     * or the number of instances on this entity is under a certain threshold.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorShowLabels() {
        return DESCRIPTOR_SHOW_LABELS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_POINT_SHADING = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Points3D:point_shading", "rerun.components.PointShading");

    /**
     * How points should be shaded.
     * 
     * If not set, points are rendered with {@link org.teamvan.moneyshift.components.PointShading.Gradient} by default.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorPointShading() {
        return DESCRIPTOR_POINT_SHADING;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CLASS_IDS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Points3D:class_ids", "rerun.components.ClassId");

    /**
     * Optional class Ids for the points.
     * 
     * The {@link org.teamvan.moneyshift.components.ClassId} provides colors and labels if not specified explicitly.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorClassIds() {
        return DESCRIPTOR_CLASS_IDS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_KEYPOINT_IDS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Points3D:keypoint_ids", "rerun.components.KeypointId");

    /**
     * Optional keypoint IDs for the points, identifying them within a class.
     * 
     * If keypoint IDs are passed in but no {@link org.teamvan.moneyshift.components.ClassId}s were specified, the {@link org.teamvan.moneyshift.components.ClassId} will
     * default to 0.
     * This is useful to identify points within a single classification (which is identified
     * with {@code class_id}).
     * E.g. the classification might be 'Person' and the keypoints refer to joints on a
     * detected skeleton.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorKeypointIds() {
        return DESCRIPTOR_KEYPOINT_IDS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorPositions());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorRadii(), descriptorColors());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorLabels(), descriptorShowLabels(), descriptorPointShading(), descriptorClassIds(), descriptorKeypointIds());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.Position3D.batch(descriptorPositions(), allocator, positions));
        if (radii != null) {
            batches.add(org.teamvan.moneyshift.components.Radius.batch(descriptorRadii(), allocator, radii));
        }
        if (colors != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColors(), allocator, colors));
        }
        if (labels != null) {
            batches.add(org.teamvan.moneyshift.components.Text.batch(descriptorLabels(), allocator, labels));
        }
        if (showLabels != null) {
            batches.add(org.teamvan.moneyshift.components.ShowLabels.batch(descriptorShowLabels(), allocator, showLabels));
        }
        if (pointShading != null) {
            batches.add(org.teamvan.moneyshift.components.PointShading.batch(descriptorPointShading(), allocator, pointShading));
        }
        if (classIds != null) {
            batches.add(org.teamvan.moneyshift.components.ClassId.batch(descriptorClassIds(), allocator, classIds));
        }
        if (keypointIds != null) {
            batches.add(org.teamvan.moneyshift.components.KeypointId.batch(descriptorKeypointIds(), allocator, keypointIds));
        }
        return batches;
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

        public Builder withRadii(org.teamvan.moneyshift.components.Radius... value) {
            return withRadii(java.util.List.of(value));
        }

        public Builder withColors(java.util.List<org.teamvan.moneyshift.components.Color> value) {
            this.colors = java.util.List.copyOf(value);
            return this;
        }

        public Builder withColors(org.teamvan.moneyshift.components.Color... value) {
            return withColors(java.util.List.of(value));
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

        public Builder withPointShading(org.teamvan.moneyshift.components.PointShading value) {
            this.pointShading = value;
            return this;
        }

        public Builder withClassIds(java.util.List<org.teamvan.moneyshift.components.ClassId> value) {
            this.classIds = java.util.List.copyOf(value);
            return this;
        }

        public Builder withClassIds(org.teamvan.moneyshift.components.ClassId... value) {
            return withClassIds(java.util.List.of(value));
        }

        public Builder withKeypointIds(java.util.List<org.teamvan.moneyshift.components.KeypointId> value) {
            this.keypointIds = java.util.List.copyOf(value);
            return this;
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
