// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A channel within an MCAP file that defines how messages are structured and encoded.
 * 
 * Channels in MCAP files group messages by topic and define their encoding format.
 * Each channel has a unique identifier and specifies the message schema and encoding used
 * for all messages published to that topic.
 * 
 * See also {@link org.teamvan.moneyshift.archetypes.McapMessage rerun::archetypes::McapMessage} for individual messages within a channel,
 * {@link org.teamvan.moneyshift.archetypes.McapSchema rerun::archetypes::McapSchema} for the data structure definitions, and the
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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("id").append('=').append(org.teamvan.moneyshift.components.ChannelId.COMPONENT_TYPE);
        }
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("topic").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("message_encoding").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        if (metadata != null) {
            if (components.length() > 0) components.append(',');
            components.append("metadata").append('=').append(org.teamvan.moneyshift.components.KeyValuePairs.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (true) {
            org.teamvan.moneyshift.components.ChannelId.writeColumnsStatic(w, "id", java.util.List.of(id));
        }
        if (true) {
            org.teamvan.moneyshift.components.Text.writeColumnsStatic(w, "topic", java.util.List.of(topic));
        }
        if (true) {
            org.teamvan.moneyshift.components.Text.writeColumnsStatic(w, "message_encoding", java.util.List.of(messageEncoding));
        }
        if (metadata != null) {
            org.teamvan.moneyshift.components.KeyValuePairs.writeColumnsStatic(w, "metadata", java.util.List.of(metadata));
        }
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
