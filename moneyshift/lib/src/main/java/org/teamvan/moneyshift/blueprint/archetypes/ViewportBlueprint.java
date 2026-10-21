// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * The top-level description of the viewport.
 */
public record ViewportBlueprint(
    /**
     * The layout of the views
     */
    org.teamvan.moneyshift.blueprint.components.RootContainer rootContainer,
    /**
     * Show one tab as maximized?
     */
    org.teamvan.moneyshift.blueprint.components.ViewMaximized maximized,
    /**
     * Whether the viewport layout is determined automatically.
     * 
     * If {@code true}, the container layout will be reset whenever a new view is added or removed.
     * This defaults to {@code false} and is automatically set to {@code false} when there is user determined layout.
     */
    org.teamvan.moneyshift.blueprint.components.AutoLayout autoLayout,
    /**
     * Whether or not views should be created automatically.
     * 
     * If {@code true}, the viewer will only add views that it hasn't considered previously (as identified by {@code past_viewer_recommendations})
     * and which aren't deemed redundant to existing views.
     * This defaults to {@code false} and is automatically set to {@code false} when the user adds views manually in the viewer.
     */
    org.teamvan.moneyshift.blueprint.components.AutoViews autoViews,
    /**
     * Hashes of all recommended views the viewer has already added and that should not be added again.
     * 
     * This is an internal field and should not be set usually.
     * If you want the viewer from stopping to add views, you should set {@code auto_views} to {@code false}.
     * 
     * The viewer uses this to determine whether it should keep adding views.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.ViewerRecommendationHash> pastViewerRecommendations
) implements org.teamvan.moneyshift.Archetype {
    public ViewportBlueprint {
        if (pastViewerRecommendations != null) { pastViewerRecommendations = java.util.List.copyOf(pastViewerRecommendations); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.ViewportBlueprint";
    public static final int NUM_COMPONENTS = 5;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ROOT_CONTAINER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ViewportBlueprint:root_container", "rerun.blueprint.components.RootContainer");

    /**
     * The layout of the views
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRootContainer() {
        return DESCRIPTOR_ROOT_CONTAINER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MAXIMIZED = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ViewportBlueprint:maximized", "rerun.blueprint.components.ViewMaximized");

    /**
     * Show one tab as maximized?
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMaximized() {
        return DESCRIPTOR_MAXIMIZED;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_AUTO_LAYOUT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ViewportBlueprint:auto_layout", "rerun.blueprint.components.AutoLayout");

    /**
     * Whether the viewport layout is determined automatically.
     * 
     * If {@code true}, the container layout will be reset whenever a new view is added or removed.
     * This defaults to {@code false} and is automatically set to {@code false} when there is user determined layout.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorAutoLayout() {
        return DESCRIPTOR_AUTO_LAYOUT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_AUTO_VIEWS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ViewportBlueprint:auto_views", "rerun.blueprint.components.AutoViews");

    /**
     * Whether or not views should be created automatically.
     * 
     * If {@code true}, the viewer will only add views that it hasn't considered previously (as identified by {@code past_viewer_recommendations})
     * and which aren't deemed redundant to existing views.
     * This defaults to {@code false} and is automatically set to {@code false} when the user adds views manually in the viewer.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorAutoViews() {
        return DESCRIPTOR_AUTO_VIEWS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_PAST_VIEWER_RECOMMENDATIONS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ViewportBlueprint:past_viewer_recommendations", "rerun.blueprint.components.ViewerRecommendationHash");

    /**
     * Hashes of all recommended views the viewer has already added and that should not be added again.
     * 
     * This is an internal field and should not be set usually.
     * If you want the viewer from stopping to add views, you should set {@code auto_views} to {@code false}.
     * 
     * The viewer uses this to determine whether it should keep adding views.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorPastViewerRecommendations() {
        return DESCRIPTOR_PAST_VIEWER_RECOMMENDATIONS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorRootContainer(), descriptorMaximized(), descriptorAutoLayout(), descriptorAutoViews(), descriptorPastViewerRecommendations());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (rootContainer != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.RootContainer.batch(descriptorRootContainer(), allocator, rootContainer));
        }
        if (maximized != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ViewMaximized.batch(descriptorMaximized(), allocator, maximized));
        }
        if (autoLayout != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.AutoLayout.batch(descriptorAutoLayout(), allocator, autoLayout));
        }
        if (autoViews != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.AutoViews.batch(descriptorAutoViews(), allocator, autoViews));
        }
        if (pastViewerRecommendations != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ViewerRecommendationHash.batch(descriptorPastViewerRecommendations(), allocator, pastViewerRecommendations));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.RootContainer rootContainer;
        private org.teamvan.moneyshift.blueprint.components.ViewMaximized maximized;
        private org.teamvan.moneyshift.blueprint.components.AutoLayout autoLayout;
        private org.teamvan.moneyshift.blueprint.components.AutoViews autoViews;
        private java.util.List<org.teamvan.moneyshift.blueprint.components.ViewerRecommendationHash> pastViewerRecommendations;
        private Builder() {}

        public Builder withRootContainer(org.teamvan.moneyshift.blueprint.components.RootContainer value) {
            this.rootContainer = value;
            return this;
        }

        public Builder withMaximized(org.teamvan.moneyshift.blueprint.components.ViewMaximized value) {
            this.maximized = value;
            return this;
        }

        public Builder withAutoLayout(org.teamvan.moneyshift.blueprint.components.AutoLayout value) {
            this.autoLayout = value;
            return this;
        }

        public Builder withAutoViews(org.teamvan.moneyshift.blueprint.components.AutoViews value) {
            this.autoViews = value;
            return this;
        }

        public Builder withPastViewerRecommendations(java.util.List<org.teamvan.moneyshift.blueprint.components.ViewerRecommendationHash> value) {
            this.pastViewerRecommendations = java.util.List.copyOf(value);
            return this;
        }

        public Builder withPastViewerRecommendations(org.teamvan.moneyshift.blueprint.components.ViewerRecommendationHash... value) {
            return withPastViewerRecommendations(java.util.List.of(value));
        }

        public ViewportBlueprint build() {
            return new ViewportBlueprint(rootContainer, maximized, autoLayout, autoViews, pastViewerRecommendations);
        }
    }

    public static ViewportBlueprint of(
    ) {
        return new ViewportBlueprint(null, null, null, null, null);
    }
}
