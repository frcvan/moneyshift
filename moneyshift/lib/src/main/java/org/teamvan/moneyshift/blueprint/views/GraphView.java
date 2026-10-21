// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.views;

/**
 * A graph view to display time-variying, directed or undirected graph visualization.
 * 
 */
public record GraphView(
    /**
     * Configures the background of the graph.
     */
    org.teamvan.moneyshift.blueprint.archetypes.GraphBackground background,
    /**
     * Everything within these bounds is guaranteed to be visible.
     * 
     * Some things outside of these bounds may also be visible due to letterboxing.
     */
    org.teamvan.moneyshift.blueprint.archetypes.VisualBounds2D visualBounds,
    /**
     * Allows to control the interaction between two nodes connected by an edge.
     */
    org.teamvan.moneyshift.blueprint.archetypes.ForceLink forceLink,
    /**
     * A force between each pair of nodes that ressembles an electrical charge.
     */
    org.teamvan.moneyshift.blueprint.archetypes.ForceManyBody forceManyBody,
    /**
     * Similar to gravity, this force pulls nodes towards a specific position.
     */
    org.teamvan.moneyshift.blueprint.archetypes.ForcePosition forcePosition,
    /**
     * Resolves collisions between the bounding spheres, according to the radius of the nodes.
     */
    org.teamvan.moneyshift.blueprint.archetypes.ForceCollisionRadius forceCollisionRadius,
    /**
     * Tries to move the center of mass of the graph to the origin.
     */
    org.teamvan.moneyshift.blueprint.archetypes.ForceCenter forceCenter
) {
    public static final java.lang.String VIEW_NAME = "rerun.blueprint.views.GraphView";

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.archetypes.GraphBackground background;
        private org.teamvan.moneyshift.blueprint.archetypes.VisualBounds2D visualBounds;
        private org.teamvan.moneyshift.blueprint.archetypes.ForceLink forceLink;
        private org.teamvan.moneyshift.blueprint.archetypes.ForceManyBody forceManyBody;
        private org.teamvan.moneyshift.blueprint.archetypes.ForcePosition forcePosition;
        private org.teamvan.moneyshift.blueprint.archetypes.ForceCollisionRadius forceCollisionRadius;
        private org.teamvan.moneyshift.blueprint.archetypes.ForceCenter forceCenter;
        private Builder() {}

        public Builder withBackground(org.teamvan.moneyshift.blueprint.archetypes.GraphBackground value) {
            this.background = value;
            return this;
        }

        public Builder withVisualBounds(org.teamvan.moneyshift.blueprint.archetypes.VisualBounds2D value) {
            this.visualBounds = value;
            return this;
        }

        public Builder withForceLink(org.teamvan.moneyshift.blueprint.archetypes.ForceLink value) {
            this.forceLink = value;
            return this;
        }

        public Builder withForceManyBody(org.teamvan.moneyshift.blueprint.archetypes.ForceManyBody value) {
            this.forceManyBody = value;
            return this;
        }

        public Builder withForcePosition(org.teamvan.moneyshift.blueprint.archetypes.ForcePosition value) {
            this.forcePosition = value;
            return this;
        }

        public Builder withForceCollisionRadius(org.teamvan.moneyshift.blueprint.archetypes.ForceCollisionRadius value) {
            this.forceCollisionRadius = value;
            return this;
        }

        public Builder withForceCenter(org.teamvan.moneyshift.blueprint.archetypes.ForceCenter value) {
            this.forceCenter = value;
            return this;
        }

        public GraphView build() {
            return new GraphView(background, visualBounds, forceLink, forceManyBody, forcePosition, forceCollisionRadius, forceCenter);
        }
    }
}
