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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TEXT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TextLog:text", "rerun.components.Text");

    /**
     * The body of the message.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorText() {
        return DESCRIPTOR_TEXT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LEVEL = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TextLog:level", "rerun.components.TextLogLevel");

    /**
     * The verbosity level of the message.
     * 
     * This can be used to filter the log messages in the Rerun Viewer.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLevel() {
        return DESCRIPTOR_LEVEL;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLOR = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TextLog:color", "rerun.components.Color");

    /**
     * Optional color to use for the log line in the Rerun Viewer.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColor() {
        return DESCRIPTOR_COLOR;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorText());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorLevel());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorColor());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.Text.batch(descriptorText(), allocator, text));
        if (level != null) {
            batches.add(org.teamvan.moneyshift.components.TextLogLevel.batch(descriptorLevel(), allocator, level));
        }
        if (color != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColor(), allocator, color));
        }
        return batches;
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
