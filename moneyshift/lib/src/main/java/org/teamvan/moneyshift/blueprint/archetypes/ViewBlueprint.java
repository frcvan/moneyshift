// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * The description of a single view.
 */
public record ViewBlueprint(
    /**
     * The class of the view.
     */
    org.teamvan.moneyshift.blueprint.components.ViewClass classIdentifier,
    /**
     * The name of the view.
     */
    org.teamvan.moneyshift.components.Name displayName,
    /**
     * The "anchor point" of this view.
     * 
     * In other words, the coordinate frame at this entity becomes the reference frame of the view.
     * 
     * Defaults to the root path '/' if not specified.
     * 
     * The transform at this path forms the reference point for all scene-&gt;world transforms in this view.
     * I.e. the position of this entity path in space forms the origin of the coordinate system in this view.
     * Furthermore, this is the primary indicator for heuristics on what entities we show in this view.
     */
    org.teamvan.moneyshift.blueprint.components.ViewOrigin spaceOrigin,
    /**
     * Whether this view is visible.
     * 
     * Defaults to true if not specified.
     */
    org.teamvan.moneyshift.components.Visible visible
) implements org.teamvan.moneyshift.Archetype {
    public ViewBlueprint {
        java.util.Objects.requireNonNull(classIdentifier, "classIdentifier");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.ViewBlueprint";
    public static final int NUM_COMPONENTS = 4;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CLASS_IDENTIFIER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ViewBlueprint:class_identifier", "rerun.blueprint.components.ViewClass");

    /**
     * The class of the view.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorClassIdentifier() {
        return DESCRIPTOR_CLASS_IDENTIFIER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DISPLAY_NAME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ViewBlueprint:display_name", "rerun.components.Name");

    /**
     * The name of the view.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorDisplayName() {
        return DESCRIPTOR_DISPLAY_NAME;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SPACE_ORIGIN = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ViewBlueprint:space_origin", "rerun.blueprint.components.ViewOrigin");

    /**
     * The "anchor point" of this view.
     * 
     * In other words, the coordinate frame at this entity becomes the reference frame of the view.
     * 
     * Defaults to the root path '/' if not specified.
     * 
     * The transform at this path forms the reference point for all scene-&gt;world transforms in this view.
     * I.e. the position of this entity path in space forms the origin of the coordinate system in this view.
     * Furthermore, this is the primary indicator for heuristics on what entities we show in this view.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorSpaceOrigin() {
        return DESCRIPTOR_SPACE_ORIGIN;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VISIBLE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ViewBlueprint:visible", "rerun.components.Visible");

    /**
     * Whether this view is visible.
     * 
     * Defaults to true if not specified.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVisible() {
        return DESCRIPTOR_VISIBLE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorClassIdentifier());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorDisplayName(), descriptorSpaceOrigin(), descriptorVisible());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.blueprint.components.ViewClass.batch(descriptorClassIdentifier(), allocator, classIdentifier));
        if (displayName != null) {
            batches.add(org.teamvan.moneyshift.components.Name.batch(descriptorDisplayName(), allocator, displayName));
        }
        if (spaceOrigin != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ViewOrigin.batch(descriptorSpaceOrigin(), allocator, spaceOrigin));
        }
        if (visible != null) {
            batches.add(org.teamvan.moneyshift.components.Visible.batch(descriptorVisible(), allocator, visible));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.ViewClass classIdentifier;
        private org.teamvan.moneyshift.components.Name displayName;
        private org.teamvan.moneyshift.blueprint.components.ViewOrigin spaceOrigin;
        private org.teamvan.moneyshift.components.Visible visible;
        private Builder() {}

        public Builder withClassIdentifier(org.teamvan.moneyshift.blueprint.components.ViewClass value) {
            this.classIdentifier = value;
            return this;
        }

        public Builder withDisplayName(org.teamvan.moneyshift.components.Name value) {
            this.displayName = value;
            return this;
        }

        public Builder withSpaceOrigin(org.teamvan.moneyshift.blueprint.components.ViewOrigin value) {
            this.spaceOrigin = value;
            return this;
        }

        public Builder withVisible(org.teamvan.moneyshift.components.Visible value) {
            this.visible = value;
            return this;
        }

        public ViewBlueprint build() {
            return new ViewBlueprint(classIdentifier, displayName, spaceOrigin, visible);
        }
    }

    public static ViewBlueprint of(
        org.teamvan.moneyshift.blueprint.components.ViewClass classIdentifier
    ) {
        return new ViewBlueprint(classIdentifier, null, null, null);
    }
}
