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
 * See {@link org.teamvan.moneyshift.archetypes.McapChannel} for channel definitions that specify message encoding,
 * {@link org.teamvan.moneyshift.archetypes.McapSchema} for data structure definitions, and the
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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DATA = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapMessage:data", "rerun.components.Blob");

    /**
     * The raw message payload as a binary blob.
     * 
     * This contains the actual message data encoded according to the format specified
     * by the associated channel's {@code message_encoding} field. The structure and interpretation
     * of this binary data depends on the encoding format (e.g., ros1, cdr, protobuf)
     * and the message schema defined for the channel.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorData() {
        return DESCRIPTOR_DATA;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorData());
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
        batches.add(org.teamvan.moneyshift.components.Blob.batch(descriptorData(), allocator, data));
        return batches;
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
