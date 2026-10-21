// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A list of edges in a graph.
 * 
 * By default, edges are undirected.
 * 
 */
public record GraphEdges(
    /**
     * A list of node tuples.
     */
    java.util.List<org.teamvan.moneyshift.components.GraphEdge> edges,
    /**
     * Specifies if the graph is directed or undirected.
     * 
     * If no {@link org.teamvan.moneyshift.components.GraphType} is provided, the graph is assumed to be undirected.
     */
    org.teamvan.moneyshift.components.GraphType graphType
) implements org.teamvan.moneyshift.Archetype {
    public GraphEdges {
        java.util.Objects.requireNonNull(edges, "edges");
        edges = java.util.List.copyOf(edges);
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.GraphEdges";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_EDGES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GraphEdges:edges", "rerun.components.GraphEdge");

    /**
     * A list of node tuples.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorEdges() {
        return DESCRIPTOR_EDGES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_GRAPH_TYPE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GraphEdges:graph_type", "rerun.components.GraphType");

    /**
     * Specifies if the graph is directed or undirected.
     * 
     * If no {@link org.teamvan.moneyshift.components.GraphType} is provided, the graph is assumed to be undirected.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorGraphType() {
        return DESCRIPTOR_GRAPH_TYPE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorEdges());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorGraphType());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of();
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.GraphEdge.batch(descriptorEdges(), allocator, edges));
        if (graphType != null) {
            batches.add(org.teamvan.moneyshift.components.GraphType.batch(descriptorGraphType(), allocator, graphType));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.GraphEdge> edges;
        private org.teamvan.moneyshift.components.GraphType graphType;
        private Builder() {}

        public Builder withEdges(java.util.List<org.teamvan.moneyshift.components.GraphEdge> value) {
            this.edges = java.util.List.copyOf(value);
            return this;
        }

        public Builder withEdges(org.teamvan.moneyshift.components.GraphEdge... value) {
            return withEdges(java.util.List.of(value));
        }

        public Builder withGraphType(org.teamvan.moneyshift.components.GraphType value) {
            this.graphType = value;
            return this;
        }

        public GraphEdges build() {
            return new GraphEdges(edges, graphType);
        }
    }

    public static GraphEdges of(
        java.util.List<org.teamvan.moneyshift.components.GraphEdge> edges
    ) {
        return new GraphEdges(edges, null);
    }
}
