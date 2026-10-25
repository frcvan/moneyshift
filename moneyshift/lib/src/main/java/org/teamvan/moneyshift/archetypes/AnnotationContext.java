// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * The annotation context provides additional information on how to display entities.
 * 
 * Entities can use {@link org.teamvan.moneyshift.components.ClassId rerun::components::ClassId}s and {@link org.teamvan.moneyshift.components.KeypointId rerun::components::KeypointId}s to provide annotations, and
 * the labels and colors will be looked up in the appropriate
 * annotation context. We use the *first* annotation context we find in the
 * path-hierarchy when searching up through the ancestors of a given entity
 * path.
 * 
 * See also {@link org.teamvan.moneyshift.encodings.ClassDescription rerun::encodings::ClassDescription}.
 * 
 */
public record AnnotationContext(
    /**
     * List of class descriptions, mapping class indices to class names, colors etc.
     */
    org.teamvan.moneyshift.components.AnnotationContext context
) implements org.teamvan.moneyshift.Archetype {
    public AnnotationContext {
        java.util.Objects.requireNonNull(context, "context");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.AnnotationContext";
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
            components.append("context").append('=').append(org.teamvan.moneyshift.components.AnnotationContext.COMPONENT_TYPE);
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
            org.teamvan.moneyshift.components.AnnotationContext.writeColumnsStatic(w, "context", java.util.List.of(context));
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.AnnotationContext context;
        private Builder() {}

        public Builder withContext(org.teamvan.moneyshift.components.AnnotationContext value) {
            this.context = value;
            return this;
        }

        /** Overload that takes the elements of a single component directly. */
        public Builder withContext(org.teamvan.moneyshift.encodings.ClassDescriptionMapElem... value) {
            return withContext(new org.teamvan.moneyshift.components.AnnotationContext(java.util.List.of(value)));
        }

        public AnnotationContext build() {
            return new AnnotationContext(context);
        }
    }

    public static AnnotationContext of(
        org.teamvan.moneyshift.components.AnnotationContext context
    ) {
        return new AnnotationContext(context);
    }
}
