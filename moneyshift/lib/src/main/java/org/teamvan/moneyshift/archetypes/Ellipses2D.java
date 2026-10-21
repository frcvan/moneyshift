// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * 2D ellipses with half-extents (semi-axes) and optional center, colors etc.
 * 
 * The half-sizes specify the lengths of the ellipse's two axes along the local x and y directions.
 * If both half-sizes are equal, the ellipse is a circle.
 * 
 */
public record Ellipses2D(
    /**
     * All half-extents (semi-axes) that make up the batch of ellipses.
     */
    java.util.List<org.teamvan.moneyshift.components.HalfSize2D> halfSizes,
    /**
     * Optional center positions of the ellipses.
     */
    java.util.List<org.teamvan.moneyshift.components.Position2D> centers,
    /**
     * Optional colors for the ellipses.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * Optional radii for the lines that make up the ellipses.
     */
    java.util.List<org.teamvan.moneyshift.components.Radius> lineRadii,
    /**
     * Optional text labels for the ellipses.
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
     * An optional floating point value that specifies the 2D drawing order.
     * 
     * Objects with higher values are drawn on top of those with lower values.
     * Defaults to {@code 10.0}.
     */
    org.teamvan.moneyshift.components.DrawOrder drawOrder,
    /**
     * Optional {@link org.teamvan.moneyshift.components.ClassId}s for the ellipses.
     * 
     * The {@link org.teamvan.moneyshift.components.ClassId} provides colors and labels if not specified explicitly.
     */
    java.util.List<org.teamvan.moneyshift.components.ClassId> classIds
) implements org.teamvan.moneyshift.Archetype {
    public Ellipses2D {
        java.util.Objects.requireNonNull(halfSizes, "halfSizes");
        halfSizes = java.util.List.copyOf(halfSizes);
        if (centers != null) { centers = java.util.List.copyOf(centers); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (lineRadii != null) { lineRadii = java.util.List.copyOf(lineRadii); }
        if (labels != null) { labels = java.util.List.copyOf(labels); }
        if (classIds != null) { classIds = java.util.List.copyOf(classIds); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Ellipses2D";
    public static final int NUM_COMPONENTS = 8;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_HALF_SIZES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipses2D:half_sizes", "rerun.components.HalfSize2D");

    /**
     * All half-extents (semi-axes) that make up the batch of ellipses.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorHalfSizes() {
        return DESCRIPTOR_HALF_SIZES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CENTERS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipses2D:centers", "rerun.components.Position2D");

    /**
     * Optional center positions of the ellipses.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorCenters() {
        return DESCRIPTOR_CENTERS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipses2D:colors", "rerun.components.Color");

    /**
     * Optional colors for the ellipses.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColors() {
        return DESCRIPTOR_COLORS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LINE_RADII = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipses2D:line_radii", "rerun.components.Radius");

    /**
     * Optional radii for the lines that make up the ellipses.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLineRadii() {
        return DESCRIPTOR_LINE_RADII;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LABELS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipses2D:labels", "rerun.components.Text");

    /**
     * Optional text labels for the ellipses.
     * 
     * If there's a single label present, it will be placed at the center of the entity.
     * Otherwise, each instance will have its own label.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLabels() {
        return DESCRIPTOR_LABELS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SHOW_LABELS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipses2D:show_labels", "rerun.components.ShowLabels");

    /**
     * Whether the text labels should be shown.
     * 
     * If not set, labels will automatically appear when there is exactly one label for this entity
     * or the number of instances on this entity is under a certain threshold.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorShowLabels() {
        return DESCRIPTOR_SHOW_LABELS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DRAW_ORDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipses2D:draw_order", "rerun.components.DrawOrder");

    /**
     * An optional floating point value that specifies the 2D drawing order.
     * 
     * Objects with higher values are drawn on top of those with lower values.
     * Defaults to {@code 10.0}.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorDrawOrder() {
        return DESCRIPTOR_DRAW_ORDER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CLASS_IDS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Ellipses2D:class_ids", "rerun.components.ClassId");

    /**
     * Optional {@link org.teamvan.moneyshift.components.ClassId}s for the ellipses.
     * 
     * The {@link org.teamvan.moneyshift.components.ClassId} provides colors and labels if not specified explicitly.
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
        return java.util.List.of(descriptorLineRadii(), descriptorLabels(), descriptorShowLabels(), descriptorDrawOrder(), descriptorClassIds());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.HalfSize2D.batch(descriptorHalfSizes(), allocator, halfSizes));
        if (centers != null) {
            batches.add(org.teamvan.moneyshift.components.Position2D.batch(descriptorCenters(), allocator, centers));
        }
        if (colors != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColors(), allocator, colors));
        }
        if (lineRadii != null) {
            batches.add(org.teamvan.moneyshift.components.Radius.batch(descriptorLineRadii(), allocator, lineRadii));
        }
        if (labels != null) {
            batches.add(org.teamvan.moneyshift.components.Text.batch(descriptorLabels(), allocator, labels));
        }
        if (showLabels != null) {
            batches.add(org.teamvan.moneyshift.components.ShowLabels.batch(descriptorShowLabels(), allocator, showLabels));
        }
        if (drawOrder != null) {
            batches.add(org.teamvan.moneyshift.components.DrawOrder.batch(descriptorDrawOrder(), allocator, drawOrder));
        }
        if (classIds != null) {
            batches.add(org.teamvan.moneyshift.components.ClassId.batch(descriptorClassIds(), allocator, classIds));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.HalfSize2D> halfSizes;
        private java.util.List<org.teamvan.moneyshift.components.Position2D> centers;
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private java.util.List<org.teamvan.moneyshift.components.Radius> lineRadii;
        private java.util.List<org.teamvan.moneyshift.components.Text> labels;
        private org.teamvan.moneyshift.components.ShowLabels showLabels;
        private org.teamvan.moneyshift.components.DrawOrder drawOrder;
        private java.util.List<org.teamvan.moneyshift.components.ClassId> classIds;
        private Builder() {}

        public Builder withHalfSizes(java.util.List<org.teamvan.moneyshift.components.HalfSize2D> value) {
            this.halfSizes = java.util.List.copyOf(value);
            return this;
        }

        public Builder withHalfSizes(org.teamvan.moneyshift.components.HalfSize2D... value) {
            return withHalfSizes(java.util.List.of(value));
        }

        public Builder withCenters(java.util.List<org.teamvan.moneyshift.components.Position2D> value) {
            this.centers = java.util.List.copyOf(value);
            return this;
        }

        public Builder withCenters(org.teamvan.moneyshift.components.Position2D... value) {
            return withCenters(java.util.List.of(value));
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

        public Builder withDrawOrder(org.teamvan.moneyshift.components.DrawOrder value) {
            this.drawOrder = value;
            return this;
        }

        public Builder withClassIds(java.util.List<org.teamvan.moneyshift.components.ClassId> value) {
            this.classIds = java.util.List.copyOf(value);
            return this;
        }

        public Builder withClassIds(org.teamvan.moneyshift.components.ClassId... value) {
            return withClassIds(java.util.List.of(value));
        }

        public Ellipses2D build() {
            return new Ellipses2D(halfSizes, centers, colors, lineRadii, labels, showLabels, drawOrder, classIds);
        }
    }

    public static Ellipses2D of(
        java.util.List<org.teamvan.moneyshift.components.HalfSize2D> halfSizes
    ) {
        return new Ellipses2D(halfSizes, null, null, null, null, null, null, null);
    }
}
