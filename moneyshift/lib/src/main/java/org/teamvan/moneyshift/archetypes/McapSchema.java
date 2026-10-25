// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A schema definition that describes the structure of messages in an MCAP file.
 * 
 * Schemas define the data types and field structures used by messages in MCAP channels.
 * They provide the blueprint for interpreting message payloads, specifying field names,
 * types, and organization. Each schema is referenced by channels to indicate how their
 * messages should be decoded and understood.
 * 
 * See also {@link org.teamvan.moneyshift.archetypes.McapChannel rerun::archetypes::McapChannel} for channels that reference these schemas,
 * {@link org.teamvan.moneyshift.archetypes.McapMessage rerun::archetypes::McapMessage} for the messages that conform to these schemas, and the
 * <a href="https://mcap.dev/">MCAP specification</a> for complete format details.
 * 
 */
public record McapSchema(
    /**
     * Unique identifier for this schema within the MCAP file.
     * 
     * Schema IDs must be unique within an MCAP file and are referenced by channels
     * to specify their message structure. A single schema can be shared across multiple channels.
     */
    org.teamvan.moneyshift.components.SchemaId id,
    /**
     * Human-readable name identifying this schema.
     * 
     * Schema names typically describe the message type or data structure
     * (e.g., {@code "geometry_msgs/msg/Twist"}, {@code "sensor_msgs/msg/Image"}, {@code "MyCustomMessage"}).
     */
    org.teamvan.moneyshift.components.Text name,
    /**
     * The schema definition format used to describe the message structure.
     * 
     * Common schema encodings include:
     * * {@code protobuf} - <a href="https://mcap.dev/spec/registry#protobuf-1">Protocol Buffers</a> schema definition
     * * {@code ros1msg} - <a href="https://mcap.dev/spec/registry#ros1msg">ROS1</a> message definition format
     * * {@code ros2msg} - <a href="https://mcap.dev/spec/registry#ros2msg">ROS2</a> message definition format
     * * {@code jsonschema} - <a href="https://mcap.dev/spec/registry#jsonschema">JSON Schema</a> specification
     * * {@code flatbuffer} - <a href="https://mcap.dev/spec/registry#flatbuffer">FlatBuffers</a> schema definition
     */
    org.teamvan.moneyshift.components.Text encoding,
    /**
     * The schema definition content as binary data.
     * 
     * This contains the actual schema specification in the format indicated by the
     * {@code encoding} field. For text-based schemas (like ROS message definitions or JSON Schema),
     * this is typically UTF-8 encoded text. For binary schema formats, this contains
     * the serialized schema data.
     */
    org.teamvan.moneyshift.components.Blob data
) implements org.teamvan.moneyshift.Archetype {
    public McapSchema {
        java.util.Objects.requireNonNull(id, "id");
        java.util.Objects.requireNonNull(name, "name");
        java.util.Objects.requireNonNull(encoding, "encoding");
        java.util.Objects.requireNonNull(data, "data");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.McapSchema";
    public static final int NUM_COMPONENTS = 4;

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
            components.append("id").append('=').append(org.teamvan.moneyshift.components.SchemaId.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("name").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        if ((mask & 4L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("encoding").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        if ((mask & 8L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("data").append('=').append(org.teamvan.moneyshift.components.Blob.COMPONENT_TYPE);
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
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (true) {
            org.teamvan.moneyshift.components.SchemaId.writeColumnsStatic(w, "id", java.util.List.of(id));
        }
        if (true) {
            org.teamvan.moneyshift.components.Text.writeColumnsStatic(w, "name", java.util.List.of(name));
        }
        if (true) {
            org.teamvan.moneyshift.components.Text.writeColumnsStatic(w, "encoding", java.util.List.of(encoding));
        }
        if (true) {
            org.teamvan.moneyshift.components.Blob.writeColumnsStatic(w, "data", java.util.List.of(data));
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.SchemaId id;
        private org.teamvan.moneyshift.components.Text name;
        private org.teamvan.moneyshift.components.Text encoding;
        private org.teamvan.moneyshift.components.Blob data;
        private Builder() {}

        public Builder withId(org.teamvan.moneyshift.components.SchemaId value) {
            this.id = value;
            return this;
        }

        /** Overload that takes the {@code int} value(s) directly. */
        public Builder withId(int value) {
            return withId(new org.teamvan.moneyshift.components.SchemaId(value));
        }

        public Builder withName(org.teamvan.moneyshift.components.Text value) {
            this.name = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withName(java.lang.String value) {
            return withName(new org.teamvan.moneyshift.components.Text(value));
        }

        public Builder withEncoding(org.teamvan.moneyshift.components.Text value) {
            this.encoding = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withEncoding(java.lang.String value) {
            return withEncoding(new org.teamvan.moneyshift.components.Text(value));
        }

        public Builder withData(org.teamvan.moneyshift.components.Blob value) {
            this.data = value;
            return this;
        }

        public McapSchema build() {
            return new McapSchema(id, name, encoding, data);
        }
    }

    public static McapSchema of(
        org.teamvan.moneyshift.components.SchemaId id,
        org.teamvan.moneyshift.components.Text name,
        org.teamvan.moneyshift.components.Text encoding,
        org.teamvan.moneyshift.components.Blob data
    ) {
        return new McapSchema(id, name, encoding, data);
    }
}
