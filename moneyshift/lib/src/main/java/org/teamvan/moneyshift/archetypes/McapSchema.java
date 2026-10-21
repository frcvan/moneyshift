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
 * See also {@link org.teamvan.moneyshift.archetypes.McapChannel} for channels that reference these schemas,
 * {@link org.teamvan.moneyshift.archetypes.McapMessage} for the messages that conform to these schemas, and the
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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ID = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapSchema:id", "rerun.components.SchemaId");

    /**
     * Unique identifier for this schema within the MCAP file.
     * 
     * Schema IDs must be unique within an MCAP file and are referenced by channels
     * to specify their message structure. A single schema can be shared across multiple channels.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorId() {
        return DESCRIPTOR_ID;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_NAME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapSchema:name", "rerun.components.Text");

    /**
     * Human-readable name identifying this schema.
     * 
     * Schema names typically describe the message type or data structure
     * (e.g., {@code "geometry_msgs/msg/Twist"}, {@code "sensor_msgs/msg/Image"}, {@code "MyCustomMessage"}).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorName() {
        return DESCRIPTOR_NAME;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ENCODING = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapSchema:encoding", "rerun.components.Text");

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
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorEncoding() {
        return DESCRIPTOR_ENCODING;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DATA = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "McapSchema:data", "rerun.components.Blob");

    /**
     * The schema definition content as binary data.
     * 
     * This contains the actual schema specification in the format indicated by the
     * {@code encoding} field. For text-based schemas (like ROS message definitions or JSON Schema),
     * this is typically UTF-8 encoded text. For binary schema formats, this contains
     * the serialized schema data.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorData() {
        return DESCRIPTOR_DATA;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorId(), descriptorName(), descriptorEncoding(), descriptorData());
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
        batches.add(org.teamvan.moneyshift.components.SchemaId.batch(descriptorId(), allocator, id));
        batches.add(org.teamvan.moneyshift.components.Text.batch(descriptorName(), allocator, name));
        batches.add(org.teamvan.moneyshift.components.Text.batch(descriptorEncoding(), allocator, encoding));
        batches.add(org.teamvan.moneyshift.components.Blob.batch(descriptorData(), allocator, data));
        return batches;
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
