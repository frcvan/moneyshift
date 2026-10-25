// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * 2D line strips with positions and optional colors, radii, labels, etc.
 * 
 */
public record LineStrips2D(
    /**
     * All the actual 2D line strips that make up the batch.
     */
    java.util.List<org.teamvan.moneyshift.components.LineStrip2D> strips,
    /**
     * Optional radii for the line strips.
     */
    java.util.List<org.teamvan.moneyshift.components.Radius> radii,
    /**
     * Optional colors for the line strips.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * Optional text labels for the line strips.
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
     * An optional floating point value that specifies the 2D drawing order of each line strip.
     * 
     * Objects with higher values are drawn on top of those with lower values.
     * Defaults to {@code 20.0}.
     */
    org.teamvan.moneyshift.components.DrawOrder drawOrder,
    /**
     * Optional {@link org.teamvan.moneyshift.components.ClassId rerun::components::ClassId}s for the lines.
     * 
     * The {@link org.teamvan.moneyshift.components.ClassId rerun::components::ClassId} provides colors and labels if not specified explicitly.
     */
    java.util.List<org.teamvan.moneyshift.components.ClassId> classIds
) implements org.teamvan.moneyshift.Archetype {
    public LineStrips2D {
        java.util.Objects.requireNonNull(strips, "strips");
        strips = java.util.List.copyOf(strips);
        if (radii != null) { radii = java.util.List.copyOf(radii); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (labels != null) { labels = java.util.List.copyOf(labels); }
        if (classIds != null) { classIds = java.util.List.copyOf(classIds); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.LineStrips2D";
    public static final int NUM_COMPONENTS = 7;

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
            components.append("strips").append('=').append(org.teamvan.moneyshift.components.LineStrip2D.COMPONENT_TYPE);
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
            components.append("draw_order").append('=').append(org.teamvan.moneyshift.components.DrawOrder.COMPONENT_TYPE);
        }
        if ((mask & 64L) != 0) {
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
        if (!strips.isEmpty()) mask |= 1L;
        if (radii != null && !radii.isEmpty()) mask |= 2L;
        if (colors != null && !colors.isEmpty()) mask |= 4L;
        if (labels != null && !labels.isEmpty()) mask |= 8L;
        if (showLabels != null) mask |= 16L;
        if (drawOrder != null) mask |= 32L;
        if (classIds != null && !classIds.isEmpty()) mask |= 64L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (!strips.isEmpty()) {
            org.teamvan.moneyshift.components.LineStrip2D.writeColumnsStatic(w, "strips", strips);
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
        if (drawOrder != null) {
            org.teamvan.moneyshift.components.DrawOrder.writeColumnsStatic(w, "draw_order", java.util.List.of(drawOrder));
        }
        if (classIds != null && !classIds.isEmpty()) {
            org.teamvan.moneyshift.components.ClassId.writeColumnsStatic(w, "class_ids", classIds);
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.LineStrip2D> strips;
        private java.util.List<org.teamvan.moneyshift.components.Radius> radii;
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private java.util.List<org.teamvan.moneyshift.components.Text> labels;
        private org.teamvan.moneyshift.components.ShowLabels showLabels;
        private org.teamvan.moneyshift.components.DrawOrder drawOrder;
        private java.util.List<org.teamvan.moneyshift.components.ClassId> classIds;
        private Builder() {}

        public Builder withStrips(java.util.List<org.teamvan.moneyshift.components.LineStrip2D> value) {
            this.strips = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the elements of a single component directly. */
        public Builder withStrips(org.teamvan.moneyshift.encodings.Vec2D... value) {
            return withStrips(java.util.List.of(new org.teamvan.moneyshift.components.LineStrip2D(java.util.List.of(value))));
        }

        public Builder withStrips(org.teamvan.moneyshift.components.LineStrip2D... value) {
            return withStrips(java.util.List.of(value));
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

        public LineStrips2D build() {
            return new LineStrips2D(strips, radii, colors, labels, showLabels, drawOrder, classIds);
        }
    }

    public static LineStrips2D of(
        java.util.List<org.teamvan.moneyshift.components.LineStrip2D> strips
    ) {
        return new LineStrips2D(strips, null, null, null, null, null, null);
    }
}
