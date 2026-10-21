// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.views;

/**
 * A view of a text log, for use with {@link org.teamvan.moneyshift.archetypes.TextLog}.
 * 
 */
public record TextLogView(
    /**
     * The columns to display in the view.
     */
    org.teamvan.moneyshift.blueprint.archetypes.TextLogColumns columns,
    /**
     * Filter for rows to display in the view.
     */
    org.teamvan.moneyshift.blueprint.archetypes.TextLogRows rows,
    /**
     * Formatting options for the text log view.
     */
    org.teamvan.moneyshift.blueprint.archetypes.TextLogFormat formatOptions
) {
    public static final java.lang.String VIEW_NAME = "rerun.blueprint.views.TextLogView";

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.archetypes.TextLogColumns columns;
        private org.teamvan.moneyshift.blueprint.archetypes.TextLogRows rows;
        private org.teamvan.moneyshift.blueprint.archetypes.TextLogFormat formatOptions;
        private Builder() {}

        public Builder withColumns(org.teamvan.moneyshift.blueprint.archetypes.TextLogColumns value) {
            this.columns = value;
            return this;
        }

        public Builder withRows(org.teamvan.moneyshift.blueprint.archetypes.TextLogRows value) {
            this.rows = value;
            return this;
        }

        /** Overload that takes the elements of a single component directly. */
        public Builder withRows(org.teamvan.moneyshift.components.TextLogLevel... value) {
            return withRows(new org.teamvan.moneyshift.blueprint.archetypes.TextLogRows(java.util.List.of(value)));
        }

        public Builder withFormatOptions(org.teamvan.moneyshift.blueprint.archetypes.TextLogFormat value) {
            this.formatOptions = value;
            return this;
        }

        public TextLogView build() {
            return new TextLogView(columns, rows, formatOptions);
        }
    }
}
