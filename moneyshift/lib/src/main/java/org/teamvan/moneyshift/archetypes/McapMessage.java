// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * The binary payload of a single MCAP message, without metadata.
 * 
 * This archetype represents only the raw message data from an MCAP file. It does not include
 * MCAP message metadata such as timestamps, channel IDs, sequence numbers, or publication times.
 * The binary payload represents sensor data, commands, or other information encoded according
 * to the format specified by the associated channel.
 * 
 * See {@link org.teamvan.moneyshift.archetypes.McapChannel rerun::archetypes::McapChannel} for channel definitions that specify message encoding,
 * {@link org.teamvan.moneyshift.archetypes.McapSchema rerun::archetypes::McapSchema} for data structure definitions, and the
 * <a href="https://mcap.dev/">MCAP specification</a> for complete format details.
 * 
 */
public record McapMessage(
    /**
     * The raw message payload as a binary blob.
     * 
     * This contains the actual message data encoded according to the format specified
     * by the associated channel's {@code message_encoding} field. The structure and interpretation
     * of this binary data depends on the encoding format (e.g., ros1, cdr, protobuf)
     * and the message schema defined for the channel.
     */
    org.teamvan.moneyshift.components.Blob data
) implements org.teamvan.moneyshift.Archetype {
    public McapMessage {
        java.util.Objects.requireNonNull(data, "data");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.McapMessage";
    public static final int NUM_COMPONENTS = 1;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("data").append('=').append(org.teamvan.moneyshift.components.Blob.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (true) {
            org.teamvan.moneyshift.components.Blob.writeColumnsStatic(w, "data", java.util.List.of(data));
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Blob data;
        private Builder() {}

        public Builder withData(org.teamvan.moneyshift.components.Blob value) {
            this.data = value;
            return this;
        }

        public McapMessage build() {
            return new McapMessage(data);
        }
    }

    public static McapMessage of(
        org.teamvan.moneyshift.components.Blob data
    ) {
        return new McapMessage(data);
    }
}
