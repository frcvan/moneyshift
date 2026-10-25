// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Empties all the components of an entity.
 * 
 * The presence of a clear means that a latest-at query of components at a given path(s)
 * will not return any components that were logged at those paths before the clear.
 * Any logged components after the clear are unaffected by the clear.
 * 
 * This implies that a range query that includes time points that are before the clear,
 * still returns all components at the given path(s).
 * Meaning that in practice clears are ineffective when making use of visible time ranges.
 * Scalar plots are an exception: they track clears and use them to represent holes in the
 * data (i.e. discontinuous lines).
 * 
 */
public record Clear(
    org.teamvan.moneyshift.components.ClearIsRecursive isRecursive
) implements org.teamvan.moneyshift.Archetype {
    public Clear {
        java.util.Objects.requireNonNull(isRecursive, "isRecursive");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Clear";
    public static final int NUM_COMPONENTS = 1;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    private static final class ComponentsCache {
        final long mask;
        final java.lang.String components;

        ComponentsCache(long mask, java.lang.String components) {
            this.mask = mask;
            this.components = components;
        }
    }

    private static volatile ComponentsCache cachedComponents;

    private static java.lang.String componentsFor(long mask) {
        ComponentsCache cached = cachedComponents;
        if (cached != null && cached.mask == mask) {
            return cached.components;
        }
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if ((mask & 1L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("is_recursive").append('=').append(org.teamvan.moneyshift.components.ClearIsRecursive.COMPONENT_TYPE);
        }
        java.lang.String joined = components.toString();
        cachedComponents = new ComponentsCache(mask, joined);
        return joined;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        long mask = 0L;
        if (true) mask |= 1L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (true) {
            org.teamvan.moneyshift.components.ClearIsRecursive.writeColumnsStatic(w, "is_recursive", java.util.List.of(isRecursive));
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.ClearIsRecursive isRecursive;
        private Builder() {}

        public Builder withIsRecursive(org.teamvan.moneyshift.components.ClearIsRecursive value) {
            this.isRecursive = value;
            return this;
        }

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withIsRecursive(boolean value) {
            return withIsRecursive(new org.teamvan.moneyshift.components.ClearIsRecursive(value));
        }

        public Clear build() {
            return new Clear(isRecursive);
        }
    }

    public static Clear of(
        org.teamvan.moneyshift.components.ClearIsRecursive isRecursive
    ) {
        return new Clear(isRecursive);
    }
}
