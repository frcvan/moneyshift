// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Formatting options for the text document view.
 * 
 * These options only apply to plain text documents and have no effect on Markdown documents.
 */
public record TextDocumentFormat(
    /**
     * Whether to use a monospace font for the document body.
     * 
     * Defaults to disabled.
     */
    org.teamvan.moneyshift.blueprint.components.Enabled monospace,
    /**
     * Whether to wrap long lines in the document body.
     * 
     * Defaults to enabled.
     */
    org.teamvan.moneyshift.blueprint.components.Enabled wordWrap
) implements org.teamvan.moneyshift.Archetype {
    public TextDocumentFormat {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.TextDocumentFormat";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MONOSPACE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TextDocumentFormat:monospace", "rerun.blueprint.components.Enabled");

    /**
     * Whether to use a monospace font for the document body.
     * 
     * Defaults to disabled.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMonospace() {
        return DESCRIPTOR_MONOSPACE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_WORD_WRAP = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TextDocumentFormat:word_wrap", "rerun.blueprint.components.Enabled");

    /**
     * Whether to wrap long lines in the document body.
     * 
     * Defaults to enabled.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorWordWrap() {
        return DESCRIPTOR_WORD_WRAP;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorMonospace(), descriptorWordWrap());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (monospace != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.Enabled.batch(descriptorMonospace(), allocator, monospace));
        }
        if (wordWrap != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.Enabled.batch(descriptorWordWrap(), allocator, wordWrap));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.Enabled monospace;
        private org.teamvan.moneyshift.blueprint.components.Enabled wordWrap;
        private Builder() {}

        public Builder withMonospace(org.teamvan.moneyshift.blueprint.components.Enabled value) {
            this.monospace = value;
            return this;
        }

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withMonospace(boolean value) {
            return withMonospace(new org.teamvan.moneyshift.blueprint.components.Enabled(value));
        }

        public Builder withWordWrap(org.teamvan.moneyshift.blueprint.components.Enabled value) {
            this.wordWrap = value;
            return this;
        }

        /** Overload that takes the {@code boolean} value(s) directly. */
        public Builder withWordWrap(boolean value) {
            return withWordWrap(new org.teamvan.moneyshift.blueprint.components.Enabled(value));
        }

        public TextDocumentFormat build() {
            return new TextDocumentFormat(monospace, wordWrap);
        }
    }

    public static TextDocumentFormat of(
    ) {
        return new TextDocumentFormat(null, null);
    }
}
