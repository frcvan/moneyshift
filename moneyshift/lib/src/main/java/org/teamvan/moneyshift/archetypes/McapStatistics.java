// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Recording-level statistics about an MCAP file.
 * 
 * This archetype contains summary information about an entire MCAP recording, including
 * counts of messages, schemas, channels, and other records, as well as timing information
 * spanning the full recording duration. It is typically logged once per recording to provide
 * an overview of the dataset's structure and content.
 * 
 * See also {@link org.teamvan.moneyshift.archetypes.McapChannel} for individual channel definitions,
 * {@link org.teamvan.moneyshift.archetypes.McapMessage} for message content, {@link org.teamvan.moneyshift.archetypes.McapSchema} for schema definitions,
 * and the <a href="https://mcap.dev/">MCAP specification</a> for complete format details.
 * 
 */
public record McapStatistics(
    /**
     * Total number of data messages contained in the MCAP recording.
     * 
     * This count includes all timestamped data messages but excludes metadata records,
     * schema definitions, and other non-message records.
     */
    org.teamvan.moneyshift.components.Count messageCount,
    /**
     * Number of unique schema definitions in the recording.
     * 
     * Each schema defines the structure for one or more message types used by channels.
     */
    org.teamvan.moneyshift.components.Count schemaCount,
    /**
     * Number of channels defined in the recording.
     * 
     * Each channel represents a unique topic and encoding combination for publishing messages.
     */
    org.teamvan.moneyshift.components.Count channelCount,
    /**
     * Number of file attachments embedded in the recording.
     * 
     * Attachments can include calibration files, configuration data, or other auxiliary files.
     */
    org.teamvan.moneyshift.components.Count attachmentCount,
    /**
     * Number of metadata records providing additional context about the recording.
     * 
     * Metadata records contain key-value pairs with information about the recording environment,
     * system configuration, or other contextual data.
     */
    org.teamvan.moneyshift.components.Count metadataCount,
    /**
     * Number of data chunks used to organize messages in the file.
     * 
     * Chunks group related messages together for efficient storage and indexed access.
     */
    org.teamvan.moneyshift.components.Count chunkCount,
    /**
     * Timestamp of the earliest message in the recording.
     * 
     * This marks the beginning of the recorded data timeline.
     */
    org.teamvan.moneyshift.components.Timestamp messageStartTime,
    /**
     * Timestamp of the latest message in the recording.
     * 
     * Together with {@code message_start_time}, this defines the total duration of the recording.
     */
    org.teamvan.moneyshift.components.Timestamp messageEndTime,
    /**
     * Detailed breakdown of message counts per channel.
     */
    org.teamvan.moneyshift.components.ChannelMessageCounts channelMessageCounts
) implements org.teamvan.moneyshift.Archetype {
    public McapStatistics {
        java.util.Objects.requireNonNull(messageCount, "messageCount");
        java.util.Objects.requireNonNull(schemaCount, "schemaCount");
        java.util.Objects.requireNonNull(channelCount, "channelCount");
        java.util.Objects.requireNonNull(attachmentCount, "attachmentCount");
        java.util.Objects.requireNonNull(metadataCount, "metadataCount");
        java.util.Objects.requireNonNull(chunkCount, "chunkCount");
        java.util.Objects.requireNonNull(messageStartTime, "messageStartTime");
        java.util.Objects.requireNonNull(messageEndTime, "messageEndTime");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.McapStatistics";
    public static final int NUM_COMPONENTS = 9;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MESSAGE_COUNT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapStatistics:message_count", "rerun.components.Count");

    /**
     * Total number of data messages contained in the MCAP recording.
     * 
     * This count includes all timestamped data messages but excludes metadata records,
     * schema definitions, and other non-message records.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMessageCount() {
        return DESCRIPTOR_MESSAGE_COUNT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SCHEMA_COUNT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapStatistics:schema_count", "rerun.components.Count");

    /**
     * Number of unique schema definitions in the recording.
     * 
     * Each schema defines the structure for one or more message types used by channels.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorSchemaCount() {
        return DESCRIPTOR_SCHEMA_COUNT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CHANNEL_COUNT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapStatistics:channel_count", "rerun.components.Count");

    /**
     * Number of channels defined in the recording.
     * 
     * Each channel represents a unique topic and encoding combination for publishing messages.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorChannelCount() {
        return DESCRIPTOR_CHANNEL_COUNT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ATTACHMENT_COUNT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapStatistics:attachment_count", "rerun.components.Count");

    /**
     * Number of file attachments embedded in the recording.
     * 
     * Attachments can include calibration files, configuration data, or other auxiliary files.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorAttachmentCount() {
        return DESCRIPTOR_ATTACHMENT_COUNT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_METADATA_COUNT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapStatistics:metadata_count", "rerun.components.Count");

    /**
     * Number of metadata records providing additional context about the recording.
     * 
     * Metadata records contain key-value pairs with information about the recording environment,
     * system configuration, or other contextual data.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMetadataCount() {
        return DESCRIPTOR_METADATA_COUNT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CHUNK_COUNT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapStatistics:chunk_count", "rerun.components.Count");

    /**
     * Number of data chunks used to organize messages in the file.
     * 
     * Chunks group related messages together for efficient storage and indexed access.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorChunkCount() {
        return DESCRIPTOR_CHUNK_COUNT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MESSAGE_START_TIME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapStatistics:message_start_time", "rerun.components.Timestamp");

    /**
     * Timestamp of the earliest message in the recording.
     * 
     * This marks the beginning of the recorded data timeline.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMessageStartTime() {
        return DESCRIPTOR_MESSAGE_START_TIME;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MESSAGE_END_TIME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapStatistics:message_end_time", "rerun.components.Timestamp");

    /**
     * Timestamp of the latest message in the recording.
     * 
     * Together with {@code message_start_time}, this defines the total duration of the recording.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMessageEndTime() {
        return DESCRIPTOR_MESSAGE_END_TIME;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CHANNEL_MESSAGE_COUNTS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapStatistics:channel_message_counts", "rerun.components.ChannelMessageCounts");

    /**
     * Detailed breakdown of message counts per channel.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorChannelMessageCounts() {
        return DESCRIPTOR_CHANNEL_MESSAGE_COUNTS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorMessageCount(), descriptorSchemaCount(), descriptorChannelCount(), descriptorAttachmentCount(), descriptorMetadataCount(), descriptorChunkCount(), descriptorMessageStartTime(), descriptorMessageEndTime());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorChannelMessageCounts());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.Count.batch(descriptorMessageCount(), allocator, messageCount));
        batches.add(org.teamvan.moneyshift.components.Count.batch(descriptorSchemaCount(), allocator, schemaCount));
        batches.add(org.teamvan.moneyshift.components.Count.batch(descriptorChannelCount(), allocator, channelCount));
        batches.add(org.teamvan.moneyshift.components.Count.batch(descriptorAttachmentCount(), allocator, attachmentCount));
        batches.add(org.teamvan.moneyshift.components.Count.batch(descriptorMetadataCount(), allocator, metadataCount));
        batches.add(org.teamvan.moneyshift.components.Count.batch(descriptorChunkCount(), allocator, chunkCount));
        batches.add(org.teamvan.moneyshift.components.Timestamp.batch(descriptorMessageStartTime(), allocator, messageStartTime));
        batches.add(org.teamvan.moneyshift.components.Timestamp.batch(descriptorMessageEndTime(), allocator, messageEndTime));
        if (channelMessageCounts != null) {
            batches.add(org.teamvan.moneyshift.components.ChannelMessageCounts.batch(descriptorChannelMessageCounts(), allocator, channelMessageCounts));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Count messageCount;
        private org.teamvan.moneyshift.components.Count schemaCount;
        private org.teamvan.moneyshift.components.Count channelCount;
        private org.teamvan.moneyshift.components.Count attachmentCount;
        private org.teamvan.moneyshift.components.Count metadataCount;
        private org.teamvan.moneyshift.components.Count chunkCount;
        private org.teamvan.moneyshift.components.Timestamp messageStartTime;
        private org.teamvan.moneyshift.components.Timestamp messageEndTime;
        private org.teamvan.moneyshift.components.ChannelMessageCounts channelMessageCounts;
        private Builder() {}

        public Builder withMessageCount(org.teamvan.moneyshift.components.Count value) {
            this.messageCount = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withMessageCount(long value) {
            return withMessageCount(new org.teamvan.moneyshift.components.Count(value));
        }

        public Builder withSchemaCount(org.teamvan.moneyshift.components.Count value) {
            this.schemaCount = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withSchemaCount(long value) {
            return withSchemaCount(new org.teamvan.moneyshift.components.Count(value));
        }

        public Builder withChannelCount(org.teamvan.moneyshift.components.Count value) {
            this.channelCount = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withChannelCount(long value) {
            return withChannelCount(new org.teamvan.moneyshift.components.Count(value));
        }

        public Builder withAttachmentCount(org.teamvan.moneyshift.components.Count value) {
            this.attachmentCount = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withAttachmentCount(long value) {
            return withAttachmentCount(new org.teamvan.moneyshift.components.Count(value));
        }

        public Builder withMetadataCount(org.teamvan.moneyshift.components.Count value) {
            this.metadataCount = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withMetadataCount(long value) {
            return withMetadataCount(new org.teamvan.moneyshift.components.Count(value));
        }

        public Builder withChunkCount(org.teamvan.moneyshift.components.Count value) {
            this.chunkCount = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withChunkCount(long value) {
            return withChunkCount(new org.teamvan.moneyshift.components.Count(value));
        }

        public Builder withMessageStartTime(org.teamvan.moneyshift.components.Timestamp value) {
            this.messageStartTime = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withMessageStartTime(long value) {
            return withMessageStartTime(new org.teamvan.moneyshift.components.Timestamp(value));
        }

        public Builder withMessageEndTime(org.teamvan.moneyshift.components.Timestamp value) {
            this.messageEndTime = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withMessageEndTime(long value) {
            return withMessageEndTime(new org.teamvan.moneyshift.components.Timestamp(value));
        }

        public Builder withChannelMessageCounts(org.teamvan.moneyshift.components.ChannelMessageCounts value) {
            this.channelMessageCounts = value;
            return this;
        }

        /** Overload that takes the elements of a single component directly. */
        public Builder withChannelMessageCounts(org.teamvan.moneyshift.encodings.ChannelCountPair... value) {
            return withChannelMessageCounts(new org.teamvan.moneyshift.components.ChannelMessageCounts(java.util.List.of(value)));
        }

        public McapStatistics build() {
            return new McapStatistics(messageCount, schemaCount, channelCount, attachmentCount, metadataCount, chunkCount, messageStartTime, messageEndTime, channelMessageCounts);
        }
    }

    public static McapStatistics of(
        org.teamvan.moneyshift.components.Count messageCount,
        org.teamvan.moneyshift.components.Count schemaCount,
        org.teamvan.moneyshift.components.Count channelCount,
        org.teamvan.moneyshift.components.Count attachmentCount,
        org.teamvan.moneyshift.components.Count metadataCount,
        org.teamvan.moneyshift.components.Count chunkCount,
        org.teamvan.moneyshift.components.Timestamp messageStartTime,
        org.teamvan.moneyshift.components.Timestamp messageEndTime
    ) {
        return new McapStatistics(messageCount, schemaCount, channelCount, attachmentCount, metadataCount, chunkCount, messageStartTime, messageEndTime, null);
    }
}
