// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Geospatial points with positions expressed in <a href="https://epsg.io/4326">EPSG:4326</a> latitude and longitude (North/East-positive degrees), and optional colors and radii.
 * 
 */
public record GeoPoints(
    /**
     * The <a href="https://epsg.io/4326">EPSG:4326</a> coordinates for the points (North/East-positive degrees).
     */
    java.util.List<org.teamvan.moneyshift.components.LatLon> positions,
    /**
     * Optional radii for the points, effectively turning them into circles.
     * 
     * *Note*: scene units radiii are interpreted as meters.
     */
    java.util.List<org.teamvan.moneyshift.components.Radius> radii,
    /**
     * Optional colors for the points.
     * 
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * Optional class Ids for the points.
     * 
     * The {@link org.teamvan.moneyshift.components.ClassId rerun::components::ClassId} provides colors if not specified explicitly.
     */
    java.util.List<org.teamvan.moneyshift.components.ClassId> classIds
) implements org.teamvan.moneyshift.Archetype {
    public GeoPoints {
        java.util.Objects.requireNonNull(positions, "positions");
        positions = java.util.List.copyOf(positions);
        if (radii != null) { radii = java.util.List.copyOf(radii); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (classIds != null) { classIds = java.util.List.copyOf(classIds); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.GeoPoints";
    public static final int NUM_COMPONENTS = 4;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (!positions.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("positions").append('=').append(org.teamvan.moneyshift.components.LatLon.COMPONENT_TYPE);
        }
        if (radii != null && !radii.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("radii").append('=').append(org.teamvan.moneyshift.components.Radius.COMPONENT_TYPE);
        }
        if (colors != null && !colors.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if (classIds != null && !classIds.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("class_ids").append('=').append(org.teamvan.moneyshift.components.ClassId.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (!positions.isEmpty()) {
            org.teamvan.moneyshift.components.LatLon.writeColumnsStatic(w, "positions", positions);
        }
        if (radii != null && !radii.isEmpty()) {
            org.teamvan.moneyshift.components.Radius.writeColumnsStatic(w, "radii", radii);
        }
        if (colors != null && !colors.isEmpty()) {
            org.teamvan.moneyshift.components.Color.writeColumnsStatic(w, "colors", colors);
        }
        if (classIds != null && !classIds.isEmpty()) {
            org.teamvan.moneyshift.components.ClassId.writeColumnsStatic(w, "class_ids", classIds);
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.LatLon> positions;
        private java.util.List<org.teamvan.moneyshift.components.Radius> radii;
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private java.util.List<org.teamvan.moneyshift.components.ClassId> classIds;
        private Builder() {}

        public Builder withPositions(java.util.List<org.teamvan.moneyshift.components.LatLon> value) {
            this.positions = java.util.List.copyOf(value);
            return this;
        }

        public Builder withPositions(org.teamvan.moneyshift.components.LatLon... value) {
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

        public GeoPoints build() {
            return new GeoPoints(positions, radii, colors, classIds);
        }
    }

    public static GeoPoints of(
        java.util.List<org.teamvan.moneyshift.components.LatLon> positions
    ) {
        return new GeoPoints(positions, null, null, null);
    }
}
