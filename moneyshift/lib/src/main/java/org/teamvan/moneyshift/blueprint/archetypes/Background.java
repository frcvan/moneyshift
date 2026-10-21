// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configuration for the background of a spatial view.
 */
public record Background(
    /**
     * The type of the background.
     */
    org.teamvan.moneyshift.blueprint.components.BackgroundKind kind,
    /**
     * Color used for the solid background type.
     */
    org.teamvan.moneyshift.components.Color color
) implements org.teamvan.moneyshift.Archetype {
    public Background {
        java.util.Objects.requireNonNull(kind, "kind");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.Background";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_KIND = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Background:kind", "rerun.blueprint.components.BackgroundKind");

    /**
     * The type of the background.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorKind() {
        return DESCRIPTOR_KIND;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLOR = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Background:color", "rerun.components.Color");

    /**
     * Color used for the solid background type.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColor() {
        return DESCRIPTOR_COLOR;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorKind());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorColor());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.blueprint.components.BackgroundKind.batch(descriptorKind(), allocator, kind));
        if (color != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColor(), allocator, color));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.BackgroundKind kind;
        private org.teamvan.moneyshift.components.Color color;
        private Builder() {}

        public Builder withKind(org.teamvan.moneyshift.blueprint.components.BackgroundKind value) {
            this.kind = value;
            return this;
        }

        public Builder withColor(org.teamvan.moneyshift.components.Color value) {
            this.color = value;
            return this;
        }

        public Background build() {
            return new Background(kind, color);
        }
    }

    public static Background of(
        org.teamvan.moneyshift.blueprint.components.BackgroundKind kind
    ) {
        return new Background(kind, null);
    }
}
