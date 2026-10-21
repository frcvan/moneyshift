// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.views;

/**
 * A view of a single text document, for use with {@link org.teamvan.moneyshift.archetypes.TextDocument}.
 * 
 */
public record TextDocumentView(
    /**
     * Formatting options for the text document view.
     */
    org.teamvan.moneyshift.blueprint.archetypes.TextDocumentFormat formatOptions
) {
    public static final java.lang.String VIEW_NAME = "rerun.blueprint.views.TextDocumentView";

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.archetypes.TextDocumentFormat formatOptions;
        private Builder() {}

        public Builder withFormatOptions(org.teamvan.moneyshift.blueprint.archetypes.TextDocumentFormat value) {
            this.formatOptions = value;
            return this;
        }

        public TextDocumentView build() {
            return new TextDocumentView(formatOptions);
        }
    }
}
