// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * 3D arrows with optional colors, radii, labels, etc.
 * 
 */
public record Arrows3D(
    /**
     * All the vectors for each arrow in the batch.
     */
    java.util.List<org.teamvan.moneyshift.components.Vector3D> vectors,
    /**
     * All the origin (base) positions for each arrow in the batch.
     * 
     * If no origins are set, (0, 0, 0) is used as the origin for each arrow.
     */
    java.util.List<org.teamvan.moneyshift.components.Position3D> origins,
    /**
     * Optional radii for the arrows.
     * 
     * The shaft is rendered as a line with {@code radius = 0.5 * radius}.
     * The tip is rendered with {@code height = 2.0 * radius} and {@code radius = 1.0 * radius}.
     */
    java.util.List<org.teamvan.moneyshift.components.Radius> radii,
    /**
     * Optional colors for the points.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * Optional text labels for the arrows.
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
     * Optional class Ids for the points.
     * 
     * The {@link org.teamvan.moneyshift.components.ClassId rerun::components::ClassId} provides colors and labels if not specified explicitly.
     */
    java.util.List<org.teamvan.moneyshift.components.ClassId> classIds
) implements org.teamvan.moneyshift.Archetype {
    public Arrows3D {
        java.util.Objects.requireNonNull(vectors, "vectors");
        vectors = java.util.List.copyOf(vectors);
        if (origins != null) { origins = java.util.List.copyOf(origins); }
        if (radii != null) { radii = java.util.List.copyOf(radii); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (labels != null) { labels = java.util.List.copyOf(labels); }
        if (classIds != null) { classIds = java.util.List.copyOf(classIds); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Arrows3D";
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
            components.append("vectors").append('=').append(org.teamvan.moneyshift.components.Vector3D.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("origins").append('=').append(org.teamvan.moneyshift.components.Position3D.COMPONENT_TYPE);
        }
        if ((mask & 4L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("radii").append('=').append(org.teamvan.moneyshift.components.Radius.COMPONENT_TYPE);
        }
        if ((mask & 8L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if ((mask & 16L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("labels").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        if ((mask & 32L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("show_labels").append('=').append(org.teamvan.moneyshift.components.ShowLabels.COMPONENT_TYPE);
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
        if (!vectors.isEmpty()) mask |= 1L;
        if (origins != null && !origins.isEmpty()) mask |= 2L;
        if (radii != null && !radii.isEmpty()) mask |= 4L;
        if (colors != null && !colors.isEmpty()) mask |= 8L;
        if (labels != null && !labels.isEmpty()) mask |= 16L;
        if (showLabels != null) mask |= 32L;
        if (classIds != null && !classIds.isEmpty()) mask |= 64L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (!vectors.isEmpty()) {
            org.teamvan.moneyshift.components.Vector3D.writeColumnsStatic(w, "vectors", vectors);
        }
        if (origins != null && !origins.isEmpty()) {
            org.teamvan.moneyshift.components.Position3D.writeColumnsStatic(w, "origins", origins);
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
        if (classIds != null && !classIds.isEmpty()) {
            org.teamvan.moneyshift.components.ClassId.writeColumnsStatic(w, "class_ids", classIds);
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.Vector3D> vectors;
        private java.util.List<org.teamvan.moneyshift.components.Position3D> origins;
        private java.util.List<org.teamvan.moneyshift.components.Radius> radii;
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private java.util.List<org.teamvan.moneyshift.components.Text> labels;
        private org.teamvan.moneyshift.components.ShowLabels showLabels;
        private java.util.List<org.teamvan.moneyshift.components.ClassId> classIds;
        private Builder() {}

        public Builder withVectors(java.util.List<org.teamvan.moneyshift.components.Vector3D> value) {
            this.vectors = java.util.List.copyOf(value);
            return this;
        }

        public Builder withVectors(org.teamvan.moneyshift.components.Vector3D... value) {
            return withVectors(java.util.List.of(value));
        }

        public Builder withOrigins(java.util.List<org.teamvan.moneyshift.components.Position3D> value) {
            this.origins = java.util.List.copyOf(value);
            return this;
        }

        public Builder withOrigins(org.teamvan.moneyshift.components.Position3D... value) {
            return withOrigins(java.util.List.of(value));
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

        public Arrows3D build() {
            return new Arrows3D(vectors, origins, radii, colors, labels, showLabels, classIds);
        }
    }

    public static Arrows3D of(
        java.util.List<org.teamvan.moneyshift.components.Vector3D> vectors
    ) {
        return new Arrows3D(vectors, null, null, null, null, null, null);
    }
}
