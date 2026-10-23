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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("text").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        if (mediaType != null) {
            if (components.length() > 0) components.append(',');
            components.append("media_type").append('=').append(org.teamvan.moneyshift.components.MediaType.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (true) {
            org.teamvan.moneyshift.components.Text.writeColumnsStatic(w, "text", java.util.List.of(text));
        }
        if (mediaType != null) {
            org.teamvan.moneyshift.components.MediaType.writeColumnsStatic(w, "media_type", java.util.List.of(mediaType));
        }
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

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withText(java.lang.String value) {
            return withText(new org.teamvan.moneyshift.components.Text(value));
        }

        public Builder withMediaType(org.teamvan.moneyshift.components.MediaType value) {
            this.mediaType = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withMediaType(java.lang.String value) {
            return withMediaType(new org.teamvan.moneyshift.components.MediaType(value));
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
