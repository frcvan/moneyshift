// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * General visualization behavior of an entity.
 * 
 * TODO(#6541): Fields of this archetype currently only have an effect when logged in the blueprint store.
 * 
 */
public record EntityBehavior(
    /**
     * Whether the entity can be interacted with.
     * 
     * This property is propagated down the entity hierarchy until another child entity
     * sets {@code interactive} to a different value at which point propagation continues with that value instead.
     * 
     * Defaults to parent's {@code interactive} value or true if there is no parent.
     */
    org.teamvan.moneyshift.components.Interactive interactive,
    /**
     * Whether the entity is visible.
     * 
     * This property is propagated down the entity hierarchy until another child entity
     * sets {@code visible} to a different value at which point propagation continues with that value instead.
     * 
     * Defaults to parent's {@code visible} value or true if there is no parent.
     */
    org.teamvan.moneyshift.components.Visible visible
) implements org.teamvan.moneyshift.Archetype {
    public EntityBehavior {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.EntityBehavior";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_INTERACTIVE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EntityBehavior:interactive", "rerun.components.Interactive");

    /**
     * Whether the entity can be interacted with.
     * 
     * This property is propagated down the entity hierarchy until another child entity
     * sets {@code interactive} to a different value at which point propagation continues with that value instead.
     * 
     * Defaults to parent's {@code interactive} value or true if there is no parent.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorInteractive() {
        return DESCRIPTOR_INTERACTIVE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VISIBLE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "EntityBehavior:visible", "rerun.components.Visible");

    /**
     * Whether the entity is visible.
     * 
     * This property is propagated down the entity hierarchy until another child entity
     * sets {@code visible} to a different value at which point propagation continues with that value instead.
     * 
     * Defaults to parent's {@code visible} value or true if there is no parent.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVisible() {
        return DESCRIPTOR_VISIBLE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorInteractive(), descriptorVisible());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (interactive != null) {
            batches.add(org.teamvan.moneyshift.components.Interactive.batch(descriptorInteractive(), allocator, interactive));
        }
        if (visible != null) {
            batches.add(org.teamvan.moneyshift.components.Visible.batch(descriptorVisible(), allocator, visible));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Interactive interactive;
        private org.teamvan.moneyshift.components.Visible visible;
        private Builder() {}

        public Builder withInteractive(org.teamvan.moneyshift.components.Interactive value) {
            this.interactive = value;
            return this;
        }

        public Builder withVisible(org.teamvan.moneyshift.components.Visible value) {
            this.visible = value;
            return this;
        }

        public EntityBehavior build() {
            return new EntityBehavior(interactive, visible);
        }
    }

    public static EntityBehavior of(
    ) {
        return new EntityBehavior(null, null);
    }
}
