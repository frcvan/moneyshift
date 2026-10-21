// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A channel within an MCAP file that defines how messages are structured and encoded.
 * 
 * Channels in MCAP files group messages by topic and define their encoding format.
 * Each channel has a unique identifier and specifies the message schema and encoding used
 * for all messages published to that topic.
 * 
 * See also {@link org.teamvan.moneyshift.archetypes.McapMessage} for individual messages within a channel,
 * {@link org.teamvan.moneyshift.archetypes.McapSchema} for the data structure definitions, and the
 * <a href="https://mcap.dev/">MCAP specification</a> for complete format details.
 * 
 */
public record McapChannel(
    /**
     * Unique identifier for this channel within the MCAP file.
     * 
     * Channel IDs must be unique within a single MCAP file and are used to associate
     * messages with their corresponding channel definition.
     */
    org.teamvan.moneyshift.components.ChannelId id,
    /**
     * The topic name that this channel publishes to.
     * 
     * Topics are hierarchical paths from the original robotics system (e.g., "/sensors/camera/image")
     * that categorize and organize different data streams.
     * Topics are separate from Rerun's entity paths, but they often can be mapped to them.
     */
    org.teamvan.moneyshift.components.Text topic,
    /**
     * The encoding format used for messages in this channel.
     * 
     * Common encodings include:
     * * {@code ros1} - ROS1 message format
     * * {@code cdr} - Common Data Representation (CDR) message format, used by ROS2
     * * {@code protobuf} - Protocol Buffers
     * * {@code json} - JSON encoding
     */
    org.teamvan.moneyshift.components.Text messageEncoding,
    /**
     * Additional metadata for this channel stored as key-value pairs.
     * 
     * This can include channel-specific configuration, description, units, coordinate frames,
     * or any other contextual information that helps interpret the data in this channel.
     */
    org.teamvan.moneyshift.components.KeyValuePairs metadata
) implements org.teamvan.moneyshift.Archetype {
    public McapChannel {
        java.util.Objects.requireNonNull(id, "id");
        java.util.Objects.requireNonNull(topic, "topic");
        java.util.Objects.requireNonNull(messageEncoding, "messageEncoding");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.McapChannel";
    public static final int NUM_COMPONENTS = 4;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ID = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapChannel:id", "rerun.components.ChannelId");

    /**
     * Unique identifier for this channel within the MCAP file.
     * 
     * Channel IDs must be unique within a single MCAP file and are used to associate
     * messages with their corresponding channel definition.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorId() {
        return DESCRIPTOR_ID;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TOPIC = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapChannel:topic", "rerun.components.Text");

    /**
     * The topic name that this channel publishes to.
     * 
     * Topics are hierarchical paths from the original robotics system (e.g., "/sensors/camera/image")
     * that categorize and organize different data streams.
     * Topics are separate from Rerun's entity paths, but they often can be mapped to them.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTopic() {
        return DESCRIPTOR_TOPIC;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MESSAGE_ENCODING = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapChannel:message_encoding", "rerun.components.Text");

    /**
     * The encoding format used for messages in this channel.
     * 
     * Common encodings include:
     * * {@code ros1} - ROS1 message format
     * * {@code cdr} - Common Data Representation (CDR) message format, used by ROS2
     * * {@code protobuf} - Protocol Buffers
     * * {@code json} - JSON encoding
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMessageEncoding() {
        return DESCRIPTOR_MESSAGE_ENCODING;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_METADATA = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapChannel:metadata", "rerun.components.KeyValuePairs");

    /**
     * Additional metadata for this channel stored as key-value pairs.
     * 
     * This can include channel-specific configuration, description, units, coordinate frames,
     * or any other contextual information that helps interpret the data in this channel.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMetadata() {
        return DESCRIPTOR_METADATA;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorId(), descriptorTopic(), descriptorMessageEncoding());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorMetadata());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.ChannelId.batch(descriptorId(), allocator, id));
        batches.add(org.teamvan.moneyshift.components.Text.batch(descriptorTopic(), allocator, topic));
        batches.add(org.teamvan.moneyshift.components.Text.batch(descriptorMessageEncoding(), allocator, messageEncoding));
        if (metadata != null) {
            batches.add(org.teamvan.moneyshift.components.KeyValuePairs.batch(descriptorMetadata(), allocator, metadata));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.ChannelId id;
        private org.teamvan.moneyshift.components.Text topic;
        private org.teamvan.moneyshift.components.Text messageEncoding;
        private org.teamvan.moneyshift.components.KeyValuePairs metadata;
        private Builder() {}

        public Builder withId(org.teamvan.moneyshift.components.ChannelId value) {
            this.id = value;
            return this;
        }

        /** Overload that takes the {@code int} value(s) directly. */
        public Builder withId(int value) {
            return withId(new org.teamvan.moneyshift.components.ChannelId(value));
        }

        public Builder withTopic(org.teamvan.moneyshift.components.Text value) {
            this.topic = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withTopic(java.lang.String value) {
            return withTopic(new org.teamvan.moneyshift.components.Text(value));
        }

        public Builder withMessageEncoding(org.teamvan.moneyshift.components.Text value) {
            this.messageEncoding = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withMessageEncoding(java.lang.String value) {
            return withMessageEncoding(new org.teamvan.moneyshift.components.Text(value));
        }

        public Builder withMetadata(org.teamvan.moneyshift.components.KeyValuePairs value) {
            this.metadata = value;
            return this;
        }

        /** Overload that takes the elements of a single component directly. */
        public Builder withMetadata(org.teamvan.moneyshift.encodings.Utf8Pair... value) {
            return withMetadata(new org.teamvan.moneyshift.components.KeyValuePairs(java.util.List.of(value)));
        }

        public McapChannel build() {
            return new McapChannel(id, topic, messageEncoding, metadata);
        }
    }

    public static McapChannel of(
        org.teamvan.moneyshift.components.ChannelId id,
        org.teamvan.moneyshift.components.Text topic,
        org.teamvan.moneyshift.components.Text messageEncoding
    ) {
        return new McapChannel(id, topic, messageEncoding, null);
    }
}
