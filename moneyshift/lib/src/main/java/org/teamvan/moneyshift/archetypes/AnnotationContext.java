// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * The annotation context provides additional information on how to display entities.
 * 
 * Entities can use {@link org.teamvan.moneyshift.components.ClassId}s and {@link org.teamvan.moneyshift.components.KeypointId}s to provide annotations, and
 * the labels and colors will be looked up in the appropriate
 * annotation context. We use the *first* annotation context we find in the
 * path-hierarchy when searching up through the ancestors of a given entity
 * path.
 * 
 * See also {@link org.teamvan.moneyshift.encodings.ClassDescription}.
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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CONTEXT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "AnnotationContext:context", "rerun.components.AnnotationContext");

    /**
     * List of class descriptions, mapping class indices to class names, colors etc.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorContext() {
        return DESCRIPTOR_CONTEXT;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorContext());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of();
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.AnnotationContext.batch(descriptorContext(), allocator, context));
        return batches;
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
