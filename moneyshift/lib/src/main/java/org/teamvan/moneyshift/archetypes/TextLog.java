// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A log entry in a text log, comprised of a text body and its log level.
 * 
 */
public record TextLog(
    /**
     * The body of the message.
     */
    org.teamvan.moneyshift.components.Text text,
    /**
     * The verbosity level of the message.
     * 
     * This can be used to filter the log messages in the Rerun Viewer.
     */
    org.teamvan.moneyshift.components.TextLogLevel level,
    /**
     * Optional color to use for the log line in the Rerun Viewer.
     */
    org.teamvan.moneyshift.components.Color color
) implements org.teamvan.moneyshift.Archetype {
    public TextLog {
        java.util.Objects.requireNonNull(text, "text");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.TextLog";
    public static final int NUM_COMPONENTS = 3;

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
        if (level != null) {
            if (components.length() > 0) components.append(',');
            components.append("level").append('=').append(org.teamvan.moneyshift.components.TextLogLevel.COMPONENT_TYPE);
        }
        if (color != null) {
            if (components.length() > 0) components.append(',');
            components.append("color").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (true) {
            org.teamvan.moneyshift.components.Text.writeColumnsStatic(w, "text", java.util.List.of(text));
        }
        if (level != null) {
            org.teamvan.moneyshift.components.TextLogLevel.writeColumnsStatic(w, "level", java.util.List.of(level));
        }
        if (color != null) {
            org.teamvan.moneyshift.components.Color.writeColumnsStatic(w, "color", java.util.List.of(color));
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Text text;
        private org.teamvan.moneyshift.components.TextLogLevel level;
        private org.teamvan.moneyshift.components.Color color;
        private Builder() {}

        public Builder withText(org.teamvan.moneyshift.components.Text value) {
            this.text = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withText(java.lang.String value) {
            return withText(new org.teamvan.moneyshift.components.Text(value));
        }

        public Builder withLevel(org.teamvan.moneyshift.components.TextLogLevel value) {
            this.level = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withLevel(java.lang.String value) {
            return withLevel(new org.teamvan.moneyshift.components.TextLogLevel(value));
        }

        public Builder withColor(org.teamvan.moneyshift.components.Color value) {
            this.color = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withColor(long value) {
            return withColor(new org.teamvan.moneyshift.components.Color(value));
        }

        public TextLog build() {
            return new TextLog(text, level, color);
        }
    }

    public static TextLog of(
        org.teamvan.moneyshift.components.Text text
    ) {
        return new TextLog(text, null, null);
    }
}
