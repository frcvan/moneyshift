// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Shared configuration for every preview cell in a table blueprint.
 * 
 * This archetype is stored at the entity {@code /table} alongside {@link org.teamvan.moneyshift.blueprint.archetypes.TableBlueprint}.
 */
public record PreviewsConfig(
    /**
     * The timeline used by every preview cell.
     * 
     * If left empty a timeline is automatically picked, preferring custom over built-in.
     */
    org.teamvan.moneyshift.blueprint.components.TimelineName timeline
) implements org.teamvan.moneyshift.Archetype {
    public PreviewsConfig {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.PreviewsConfig";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TIMELINE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "PreviewsConfig:timeline", "rerun.blueprint.components.TimelineName");

    /**
     * The timeline used by every preview cell.
     * 
     * If left empty a timeline is automatically picked, preferring custom over built-in.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTimeline() {
        return DESCRIPTOR_TIMELINE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorTimeline());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (timeline != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.TimelineName.batch(descriptorTimeline(), allocator, timeline));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.TimelineName timeline;
        private Builder() {}

        public Builder withTimeline(org.teamvan.moneyshift.blueprint.components.TimelineName value) {
            this.timeline = value;
            return this;
        }

        public PreviewsConfig build() {
            return new PreviewsConfig(timeline);
        }
    }

    public static PreviewsConfig of(
    ) {
        return new PreviewsConfig(null);
    }
}
