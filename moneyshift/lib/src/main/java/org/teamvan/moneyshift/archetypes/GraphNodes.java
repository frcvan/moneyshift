// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A list of nodes in a graph with optional labels, colors, etc.
 * 
 */
public record GraphNodes(
    /**
     * A list of node IDs.
     */
    java.util.List<org.teamvan.moneyshift.components.GraphNode> nodeIds,
    /**
     * Optional center positions of the nodes.
     */
    java.util.List<org.teamvan.moneyshift.components.Position2D> positions,
    /**
     * Optional colors for the boxes.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * Optional text labels for the node.
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
     * Optional radii for nodes.
     */
    java.util.List<org.teamvan.moneyshift.components.Radius> radii
) implements org.teamvan.moneyshift.Archetype {
    public GraphNodes {
        java.util.Objects.requireNonNull(nodeIds, "nodeIds");
        nodeIds = java.util.List.copyOf(nodeIds);
        if (positions != null) { positions = java.util.List.copyOf(positions); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (labels != null) { labels = java.util.List.copyOf(labels); }
        if (radii != null) { radii = java.util.List.copyOf(radii); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.GraphNodes";
    public static final int NUM_COMPONENTS = 6;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (!nodeIds.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("node_ids").append('=').append(org.teamvan.moneyshift.components.GraphNode.COMPONENT_TYPE);
        }
        if (positions != null && !positions.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("positions").append('=').append(org.teamvan.moneyshift.components.Position2D.COMPONENT_TYPE);
        }
        if (colors != null && !colors.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if (labels != null && !labels.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("labels").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        if (showLabels != null) {
            if (components.length() > 0) components.append(',');
            components.append("show_labels").append('=').append(org.teamvan.moneyshift.components.ShowLabels.COMPONENT_TYPE);
        }
        if (radii != null && !radii.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("radii").append('=').append(org.teamvan.moneyshift.components.Radius.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (!nodeIds.isEmpty()) {
            org.teamvan.moneyshift.components.GraphNode.writeColumnsStatic(w, "node_ids", nodeIds);
        }
        if (positions != null && !positions.isEmpty()) {
            org.teamvan.moneyshift.components.Position2D.writeColumnsStatic(w, "positions", positions);
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
        if (radii != null && !radii.isEmpty()) {
            org.teamvan.moneyshift.components.Radius.writeColumnsStatic(w, "radii", radii);
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.GraphNode> nodeIds;
        private java.util.List<org.teamvan.moneyshift.components.Position2D> positions;
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private java.util.List<org.teamvan.moneyshift.components.Text> labels;
        private org.teamvan.moneyshift.components.ShowLabels showLabels;
        private java.util.List<org.teamvan.moneyshift.components.Radius> radii;
        private Builder() {}

        public Builder withNodeIds(java.util.List<org.teamvan.moneyshift.components.GraphNode> value) {
            this.nodeIds = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withNodeIds(java.lang.String... value) {
            java.util.List<org.teamvan.moneyshift.components.GraphNode> values = new java.util.ArrayList<>(value.length);
            for (java.lang.String v : value) {
                values.add(new org.teamvan.moneyshift.components.GraphNode(v));
            }
            return withNodeIds(values);
        }

        public Builder withNodeIds(org.teamvan.moneyshift.components.GraphNode... value) {
            return withNodeIds(java.util.List.of(value));
        }

        public Builder withPositions(java.util.List<org.teamvan.moneyshift.components.Position2D> value) {
            this.positions = java.util.List.copyOf(value);
            return this;
        }

        public Builder withPositions(org.teamvan.moneyshift.components.Position2D... value) {
            return withPositions(java.util.List.of(value));
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

        public GraphNodes build() {
            return new GraphNodes(nodeIds, positions, colors, labels, showLabels, radii);
        }
    }

    public static GraphNodes of(
        java.util.List<org.teamvan.moneyshift.components.GraphNode> nodeIds
    ) {
        return new GraphNodes(nodeIds, null, null, null, null, null);
    }
}
