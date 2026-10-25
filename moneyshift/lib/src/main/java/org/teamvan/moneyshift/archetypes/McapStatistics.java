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
 * See also {@link org.teamvan.moneyshift.archetypes.McapChannel rerun::archetypes::McapChannel} for individual channel definitions,
 * {@link org.teamvan.moneyshift.archetypes.McapMessage rerun::archetypes::McapMessage} for message content, {@link org.teamvan.moneyshift.archetypes.McapSchema rerun::archetypes::McapSchema} for schema definitions,
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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    private static final class ComponentsCache {
        final long mask;
        final java.lang.String components;

        ComponentsCache(long mask, java.lang.String components) {
            this.mask = mask;
            this.components = components;
        }
    }

    private static volatile ComponentsCache cachedComponents;

    private static java.lang.String componentsFor(long mask) {
        ComponentsCache cached = cachedComponents;
        if (cached != null && cached.mask == mask) {
            return cached.components;
        }
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if ((mask & 1L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("message_count").append('=').append(org.teamvan.moneyshift.components.Count.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("schema_count").append('=').append(org.teamvan.moneyshift.components.Count.COMPONENT_TYPE);
        }
        if ((mask & 4L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("channel_count").append('=').append(org.teamvan.moneyshift.components.Count.COMPONENT_TYPE);
        }
        if ((mask & 8L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("attachment_count").append('=').append(org.teamvan.moneyshift.components.Count.COMPONENT_TYPE);
        }
        if ((mask & 16L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("metadata_count").append('=').append(org.teamvan.moneyshift.components.Count.COMPONENT_TYPE);
        }
        if ((mask & 32L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("chunk_count").append('=').append(org.teamvan.moneyshift.components.Count.COMPONENT_TYPE);
        }
        if ((mask & 64L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("message_start_time").append('=').append(org.teamvan.moneyshift.components.Timestamp.COMPONENT_TYPE);
        }
        if ((mask & 128L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("message_end_time").append('=').append(org.teamvan.moneyshift.components.Timestamp.COMPONENT_TYPE);
        }
        if ((mask & 256L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("channel_message_counts").append('=').append(org.teamvan.moneyshift.components.ChannelMessageCounts.COMPONENT_TYPE);
        }
        java.lang.String joined = components.toString();
        cachedComponents = new ComponentsCache(mask, joined);
        return joined;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        long mask = 0L;
        if (true) mask |= 1L;
        if (true) mask |= 2L;
        if (true) mask |= 4L;
        if (true) mask |= 8L;
        if (true) mask |= 16L;
        if (true) mask |= 32L;
        if (true) mask |= 64L;
        if (true) mask |= 128L;
        if (channelMessageCounts != null) mask |= 256L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (true) {
            org.teamvan.moneyshift.components.Count.writeColumnsStatic(w, "message_count", java.util.List.of(messageCount));
        }
        if (true) {
            org.teamvan.moneyshift.components.Count.writeColumnsStatic(w, "schema_count", java.util.List.of(schemaCount));
        }
        if (true) {
            org.teamvan.moneyshift.components.Count.writeColumnsStatic(w, "channel_count", java.util.List.of(channelCount));
        }
        if (true) {
            org.teamvan.moneyshift.components.Count.writeColumnsStatic(w, "attachment_count", java.util.List.of(attachmentCount));
        }
        if (true) {
            org.teamvan.moneyshift.components.Count.writeColumnsStatic(w, "metadata_count", java.util.List.of(metadataCount));
        }
        if (true) {
            org.teamvan.moneyshift.components.Count.writeColumnsStatic(w, "chunk_count", java.util.List.of(chunkCount));
        }
        if (true) {
            org.teamvan.moneyshift.components.Timestamp.writeColumnsStatic(w, "message_start_time", java.util.List.of(messageStartTime));
        }
        if (true) {
            org.teamvan.moneyshift.components.Timestamp.writeColumnsStatic(w, "message_end_time", java.util.List.of(messageEndTime));
        }
        if (channelMessageCounts != null) {
            org.teamvan.moneyshift.components.ChannelMessageCounts.writeColumnsStatic(w, "channel_message_counts", java.util.List.of(channelMessageCounts));
        }
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
