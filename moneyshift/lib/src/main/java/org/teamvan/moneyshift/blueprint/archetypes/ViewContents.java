// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * The contents of a {@code View}.
 * 
 * The contents are found by combining a collection of {@code QueryExpression}s.
 * 
 * {@code }`diff
 * + /world/**           # add everything…
 * - /world/roads/**     # …but remove all roads…
 * + /world/roads/main   # …but show main road
 * {@code }`
 * 
 * If there is multiple matching rules, the most specific rule wins.
 * If there are multiple rules of the same specificity, the last one wins.
 * If no rules match, the path is excluded.
 * 
 * Specifying a path without a {@code +} or {@code -} prefix is equivalent to {@code +}:
 * {@code }`diff
 * /world/**           # add everything…
 * - /world/roads/**   # …but remove all roads…
 * /world/roads/main   # …but show main road
 * {@code }`
 * 
 * The {@code /**} suffix matches the whole subtree, i.e. self and any child, recursively
 * ({@code /world/**} matches both {@code /world} and {@code /world/car/driver}).
 * Other uses of {@code *} are not (yet) supported.
 * 
 * Internally, {@code EntityPathFilter} sorts the rule by entity path, with recursive coming before non-recursive.
 * This means the last matching rule is also the most specific one. For instance:
 * {@code }`diff
 * + /world/**
 * - /world
 * - /world/car/**
 * + /world/car/driver
 * {@code }`
 * 
 * The last rule matching {@code /world/car/driver} is {@code + /world/car/driver}, so it is included.
 * The last rule matching {@code /world/car/hood} is {@code - /world/car/**}, so it is excluded.
 * The last rule matching {@code /world} is {@code - /world}, so it is excluded.
 * The last rule matching {@code /world/house} is {@code + /world/**}, so it is included.
 */
public record ViewContents(
    /**
     * The {@code QueryExpression} that populates the contents for the view.
     * 
     * They determine which entities are part of the view.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.QueryExpression> query
) implements org.teamvan.moneyshift.Archetype {
    public ViewContents {
        if (query != null) { query = java.util.List.copyOf(query); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.ViewContents";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_QUERY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ViewContents:query", "rerun.blueprint.components.QueryExpression");

    /**
     * The {@code QueryExpression} that populates the contents for the view.
     * 
     * They determine which entities are part of the view.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorQuery() {
        return DESCRIPTOR_QUERY;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorQuery());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (query != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.QueryExpression.batch(descriptorQuery(), allocator, query));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.blueprint.components.QueryExpression> query;
        private Builder() {}

        public Builder withQuery(java.util.List<org.teamvan.moneyshift.blueprint.components.QueryExpression> value) {
            this.query = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withQuery(java.lang.String... value) {
            java.util.List<org.teamvan.moneyshift.blueprint.components.QueryExpression> values = new java.util.ArrayList<>(value.length);
            for (java.lang.String v : value) {
                values.add(new org.teamvan.moneyshift.blueprint.components.QueryExpression(v));
            }
            return withQuery(values);
        }

        public Builder withQuery(org.teamvan.moneyshift.blueprint.components.QueryExpression... value) {
            return withQuery(java.util.List.of(value));
        }

        public ViewContents build() {
            return new ViewContents(query);
        }
    }

    public static ViewContents of(
    ) {
        return new ViewContents(null);
    }
}
