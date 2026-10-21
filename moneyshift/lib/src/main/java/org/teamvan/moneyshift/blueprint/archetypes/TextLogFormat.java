// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Configuration of the text log rows.
 */
public record TextLogFormat(
    /**
     * Whether to use a monospace font for the log message body.
     * 
     * Defaults to not being enabled.
     */
    org.teamvan.moneyshift.blueprint.components.Enabled monospaceBody
) implements org.teamvan.moneyshift.Archetype {
    public TextLogFormat {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.TextLogFormat";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MONOSPACE_BODY = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TextLogFormat:monospace_body", "rerun.blueprint.components.Enabled");

    /**
     * Whether to use a monospace font for the log message body.
     * 
     * Defaults to not being enabled.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMonospaceBody() {
        return DESCRIPTOR_MONOSPACE_BODY;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorMonospaceBody());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (monospaceBody != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.Enabled.batch(descriptorMonospaceBody(), allocator, monospaceBody));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.Enabled monospaceBody;
        private Builder() {}

        public Builder withMonospaceBody(org.teamvan.moneyshift.blueprint.components.Enabled value) {
            this.monospaceBody = value;
            return this;
        }

        public TextLogFormat build() {
            return new TextLogFormat(monospaceBody);
        }
    }

    public static TextLogFormat of(
    ) {
        return new TextLogFormat(null);
    }
}
