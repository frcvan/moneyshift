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
     * If no {@link org.teamvan.moneyshift.components.GraphType rerun::components::GraphType} is provided, the graph is assumed to be undirected.
     */
    org.teamvan.moneyshift.components.GraphType graphType
) implements org.teamvan.moneyshift.Archetype {
    public GraphEdges {
        java.util.Objects.requireNonNull(edges, "edges");
        edges = java.util.List.copyOf(edges);
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.GraphEdges";
    public static final int NUM_COMPONENTS = 2;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (!edges.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("edges").append('=').append(org.teamvan.moneyshift.components.GraphEdge.COMPONENT_TYPE);
        }
        if (graphType != null) {
            if (components.length() > 0) components.append(',');
            components.append("graph_type").append('=').append(org.teamvan.moneyshift.components.GraphType.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (!edges.isEmpty()) {
            org.teamvan.moneyshift.components.GraphEdge.writeColumnsStatic(w, "edges", edges);
        }
        if (graphType != null) {
            org.teamvan.moneyshift.components.GraphType.writeColumnsStatic(w, "graph_type", java.util.List.of(graphType));
        }
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
