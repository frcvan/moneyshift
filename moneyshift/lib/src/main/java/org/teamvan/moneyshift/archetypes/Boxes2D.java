// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * 2D boxes with half-extents and optional center, colors etc.
 * 
 */
public record Boxes2D(
    /**
     * All half-extents that make up the batch of boxes.
     */
    java.util.List<org.teamvan.moneyshift.components.HalfSize2D> halfSizes,
    /**
     * Optional center positions of the boxes.
     */
    java.util.List<org.teamvan.moneyshift.components.Position2D> centers,
    /**
     * Optional colors for the boxes.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * Optional radii for the lines that make up the boxes.
     */
    java.util.List<org.teamvan.moneyshift.components.Radius> radii,
    /**
     * Optional text labels for the boxes.
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
     * Optional {@link org.teamvan.moneyshift.components.ClassId rerun::components::ClassId}s for the boxes.
     * 
     * The {@link org.teamvan.moneyshift.components.ClassId rerun::components::ClassId} provides colors and labels if not specified explicitly.
     */
    java.util.List<org.teamvan.moneyshift.components.ClassId> classIds
) implements org.teamvan.moneyshift.Archetype {
    public Boxes2D {
        java.util.Objects.requireNonNull(halfSizes, "halfSizes");
        halfSizes = java.util.List.copyOf(halfSizes);
        if (centers != null) { centers = java.util.List.copyOf(centers); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (radii != null) { radii = java.util.List.copyOf(radii); }
        if (labels != null) { labels = java.util.List.copyOf(labels); }
        if (classIds != null) { classIds = java.util.List.copyOf(classIds); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Boxes2D";
    public static final int NUM_COMPONENTS = 8;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (!halfSizes.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("half_sizes").append('=').append(org.teamvan.moneyshift.components.HalfSize2D.COMPONENT_TYPE);
        }
        if (centers != null && !centers.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("centers").append('=').append(org.teamvan.moneyshift.components.Position2D.COMPONENT_TYPE);
        }
        if (colors != null && !colors.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if (radii != null && !radii.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("radii").append('=').append(org.teamvan.moneyshift.components.Radius.COMPONENT_TYPE);
        }
        if (labels != null && !labels.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("labels").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        if (showLabels != null) {
            if (components.length() > 0) components.append(',');
            components.append("show_labels").append('=').append(org.teamvan.moneyshift.components.ShowLabels.COMPONENT_TYPE);
        }
        if (drawOrder != null) {
            if (components.length() > 0) components.append(',');
            components.append("draw_order").append('=').append(org.teamvan.moneyshift.components.DrawOrder.COMPONENT_TYPE);
        }
        if (classIds != null && !classIds.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("class_ids").append('=').append(org.teamvan.moneyshift.components.ClassId.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (!halfSizes.isEmpty()) {
            org.teamvan.moneyshift.components.HalfSize2D.writeColumnsStatic(w, "half_sizes", halfSizes);
        }
        if (centers != null && !centers.isEmpty()) {
            org.teamvan.moneyshift.components.Position2D.writeColumnsStatic(w, "centers", centers);
        }
        if (colors != null && !colors.isEmpty()) {
            org.teamvan.moneyshift.components.Color.writeColumnsStatic(w, "colors", colors);
        }
        if (radii != null && !radii.isEmpty()) {
            org.teamvan.moneyshift.components.Radius.writeColumnsStatic(w, "radii", radii);
        }
        if (labels != null && !labels.isEmpty()) {
            org.teamvan.moneyshift.components.Text.writeColumnsStatic(w, "labels", labels);
        }
        if (showLabels != null) {
            org.teamvan.moneyshift.components.ShowLabels.writeColumnsStatic(w, "show_labels", java.util.List.of(showLabels));
        }
        if (drawOrder != null) {
            org.teamvan.moneyshift.components.DrawOrder.writeColumnsStatic(w, "draw_order", java.util.List.of(drawOrder));
        }
        if (classIds != null && !classIds.isEmpty()) {
            org.teamvan.moneyshift.components.ClassId.writeColumnsStatic(w, "class_ids", classIds);
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.HalfSize2D> halfSizes;
        private java.util.List<org.teamvan.moneyshift.components.Position2D> centers;
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private java.util.List<org.teamvan.moneyshift.components.Radius> radii;
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

        public Builder withDrawOrder(org.teamvan.moneyshift.components.DrawOrder value) {
            this.drawOrder = value;
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withDrawOrder(float value) {
            return withDrawOrder(new org.teamvan.moneyshift.components.DrawOrder(value));
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

        public Boxes2D build() {
            return new Boxes2D(halfSizes, centers, colors, radii, labels, showLabels, drawOrder, classIds);
        }
    }

    public static Boxes2D of(
        java.util.List<org.teamvan.moneyshift.components.HalfSize2D> halfSizes
    ) {
        return new Boxes2D(halfSizes, null, null, null, null, null, null, null);
    }
}
