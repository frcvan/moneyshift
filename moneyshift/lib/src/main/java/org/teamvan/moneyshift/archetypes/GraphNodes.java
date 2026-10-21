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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_NODE_IDS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GraphNodes:node_ids", "rerun.components.GraphNode");

    /**
     * A list of node IDs.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorNodeIds() {
        return DESCRIPTOR_NODE_IDS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_POSITIONS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GraphNodes:positions", "rerun.components.Position2D");

    /**
     * Optional center positions of the nodes.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorPositions() {
        return DESCRIPTOR_POSITIONS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GraphNodes:colors", "rerun.components.Color");

    /**
     * Optional colors for the boxes.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColors() {
        return DESCRIPTOR_COLORS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LABELS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GraphNodes:labels", "rerun.components.Text");

    /**
     * Optional text labels for the node.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLabels() {
        return DESCRIPTOR_LABELS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SHOW_LABELS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GraphNodes:show_labels", "rerun.components.ShowLabels");

    /**
     * Whether the text labels should be shown.
     * 
     * If not set, labels will automatically appear when there is exactly one label for this entity
     * or the number of instances on this entity is under a certain threshold.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorShowLabels() {
        return DESCRIPTOR_SHOW_LABELS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_RADII = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GraphNodes:radii", "rerun.components.Radius");

    /**
     * Optional radii for nodes.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRadii() {
        return DESCRIPTOR_RADII;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorNodeIds());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorPositions(), descriptorColors(), descriptorLabels(), descriptorShowLabels(), descriptorRadii());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.GraphNode.batch(descriptorNodeIds(), allocator, nodeIds));
        if (positions != null) {
            batches.add(org.teamvan.moneyshift.components.Position2D.batch(descriptorPositions(), allocator, positions));
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
        if (radii != null) {
            batches.add(org.teamvan.moneyshift.components.Radius.batch(descriptorRadii(), allocator, radii));
        }
        return batches;
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
