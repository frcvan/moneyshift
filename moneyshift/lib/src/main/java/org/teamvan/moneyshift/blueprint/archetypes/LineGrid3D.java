// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configuration for the 3D line grid.
 */
public record LineGrid3D(
    /**
     * Whether the grid is visible.
     * 
     * Defaults to true.
     */
    org.teamvan.moneyshift.components.Visible visible,
    /**
     * Space between grid lines spacing of one line to the next in scene units.
     * 
     * As you zoom out, successively only every tenth line is shown.
     * This controls the closest zoom level.
     */
    org.teamvan.moneyshift.blueprint.components.GridSpacing spacing,
    /**
     * In what plane the grid is drawn.
     * 
     * Defaults to the plane at zero units along the up/down axis defined by {@link org.teamvan.moneyshift.blueprint.archetypes.SpatialInformation}'s axes property.
     */
    org.teamvan.moneyshift.components.Plane3D plane,
    /**
     * How thick the lines should be in ui units.
     * 
     * Default is 1.0 ui unit.
     */
    org.teamvan.moneyshift.components.StrokeWidth strokeWidth,
    /**
     * Color used for the grid.
     * 
     * Transparency via alpha channel is supported.
     * Defaults to a slightly transparent light gray.
     */
    org.teamvan.moneyshift.components.Color color
) implements org.teamvan.moneyshift.Archetype {
    public LineGrid3D {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.LineGrid3D";
    public static final int NUM_COMPONENTS = 5;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VISIBLE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "LineGrid3D:visible", "rerun.components.Visible");

    /**
     * Whether the grid is visible.
     * 
     * Defaults to true.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVisible() {
        return DESCRIPTOR_VISIBLE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SPACING = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "LineGrid3D:spacing", "rerun.blueprint.components.GridSpacing");

    /**
     * Space between grid lines spacing of one line to the next in scene units.
     * 
     * As you zoom out, successively only every tenth line is shown.
     * This controls the closest zoom level.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorSpacing() {
        return DESCRIPTOR_SPACING;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_PLANE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "LineGrid3D:plane", "rerun.components.Plane3D");

    /**
     * In what plane the grid is drawn.
     * 
     * Defaults to the plane at zero units along the up/down axis defined by {@link org.teamvan.moneyshift.blueprint.archetypes.SpatialInformation}'s axes property.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorPlane() {
        return DESCRIPTOR_PLANE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_STROKE_WIDTH = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "LineGrid3D:stroke_width", "rerun.components.StrokeWidth");

    /**
     * How thick the lines should be in ui units.
     * 
     * Default is 1.0 ui unit.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorStrokeWidth() {
        return DESCRIPTOR_STROKE_WIDTH;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLOR = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "LineGrid3D:color", "rerun.components.Color");

    /**
     * Color used for the grid.
     * 
     * Transparency via alpha channel is supported.
     * Defaults to a slightly transparent light gray.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColor() {
        return DESCRIPTOR_COLOR;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorVisible(), descriptorSpacing(), descriptorPlane(), descriptorStrokeWidth(), descriptorColor());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (visible != null) {
            batches.add(org.teamvan.moneyshift.components.Visible.batch(descriptorVisible(), allocator, visible));
        }
        if (spacing != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.GridSpacing.batch(descriptorSpacing(), allocator, spacing));
        }
        if (plane != null) {
            batches.add(org.teamvan.moneyshift.components.Plane3D.batch(descriptorPlane(), allocator, plane));
        }
        if (strokeWidth != null) {
            batches.add(org.teamvan.moneyshift.components.StrokeWidth.batch(descriptorStrokeWidth(), allocator, strokeWidth));
        }
        if (color != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColor(), allocator, color));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Visible visible;
        private org.teamvan.moneyshift.blueprint.components.GridSpacing spacing;
        private org.teamvan.moneyshift.components.Plane3D plane;
        private org.teamvan.moneyshift.components.StrokeWidth strokeWidth;
        private org.teamvan.moneyshift.components.Color color;
        private Builder() {}

        public Builder withVisible(org.teamvan.moneyshift.components.Visible value) {
            this.visible = value;
            return this;
        }

        public Builder withSpacing(org.teamvan.moneyshift.blueprint.components.GridSpacing value) {
            this.spacing = value;
            return this;
        }

        public Builder withPlane(org.teamvan.moneyshift.components.Plane3D value) {
            this.plane = value;
            return this;
        }

        public Builder withStrokeWidth(org.teamvan.moneyshift.components.StrokeWidth value) {
            this.strokeWidth = value;
            return this;
        }

        public Builder withColor(org.teamvan.moneyshift.components.Color value) {
            this.color = value;
            return this;
        }

        public LineGrid3D build() {
            return new LineGrid3D(visible, spacing, plane, strokeWidth, color);
        }
    }

    public static LineGrid3D of(
    ) {
        return new LineGrid3D(null, null, null, null, null);
    }
}
