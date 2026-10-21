// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A text element intended to be displayed in its own text box.
 * 
 * Supports raw text and markdown.
 * 
 */
public record TextDocument(
    /**
     * Contents of the text document.
     */
    org.teamvan.moneyshift.components.Text text,
    /**
     * The Media Type of the text.
     * 
     * For instance:
     * * {@code text/plain}
     * * {@code text/markdown}
     * 
     * If omitted, {@code text/plain} is assumed.
     */
    org.teamvan.moneyshift.components.MediaType mediaType
) implements org.teamvan.moneyshift.Archetype {
    public TextDocument {
        java.util.Objects.requireNonNull(text, "text");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.TextDocument";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TEXT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TextDocument:text", "rerun.components.Text");

    /**
     * Contents of the text document.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorText() {
        return DESCRIPTOR_TEXT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MEDIA_TYPE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TextDocument:media_type", "rerun.components.MediaType");

    /**
     * The Media Type of the text.
     * 
     * For instance:
     * * {@code text/plain}
     * * {@code text/markdown}
     * 
     * If omitted, {@code text/plain} is assumed.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMediaType() {
        return DESCRIPTOR_MEDIA_TYPE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorText());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorMediaType());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.Text.batch(descriptorText(), allocator, text));
        if (mediaType != null) {
            batches.add(org.teamvan.moneyshift.components.MediaType.batch(descriptorMediaType(), allocator, mediaType));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Text text;
        private org.teamvan.moneyshift.components.MediaType mediaType;
        private Builder() {}

        public Builder withText(org.teamvan.moneyshift.components.Text value) {
            this.text = value;
            return this;
        }

        public Builder withMediaType(org.teamvan.moneyshift.components.MediaType value) {
            this.mediaType = value;
            return this;
        }

        public TextDocument build() {
            return new TextDocument(text, mediaType);
        }
    }

    public static TextDocument of(
        org.teamvan.moneyshift.components.Text text
    ) {
        return new TextDocument(text, null);
    }
}
