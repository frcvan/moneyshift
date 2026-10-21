// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A list of properties associated with a recording.
 * 
 */
public record RecordingInfo(
    /**
     * When the recording started.
     * 
     * Should be an absolute time, i.e. relative to Unix Epoch.
     */
    org.teamvan.moneyshift.components.Timestamp startTime,
    /**
     * A user-chosen name for the recording.
     */
    org.teamvan.moneyshift.components.Name name
) implements org.teamvan.moneyshift.Archetype {
    public RecordingInfo {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.RecordingInfo";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_START_TIME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "RecordingInfo:start_time", "rerun.components.Timestamp");

    /**
     * When the recording started.
     * 
     * Should be an absolute time, i.e. relative to Unix Epoch.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorStartTime() {
        return DESCRIPTOR_START_TIME;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_NAME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "RecordingInfo:name", "rerun.components.Name");

    /**
     * A user-chosen name for the recording.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorName() {
        return DESCRIPTOR_NAME;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorStartTime(), descriptorName());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (startTime != null) {
            batches.add(org.teamvan.moneyshift.components.Timestamp.batch(descriptorStartTime(), allocator, startTime));
        }
        if (name != null) {
            batches.add(org.teamvan.moneyshift.components.Name.batch(descriptorName(), allocator, name));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Timestamp startTime;
        private org.teamvan.moneyshift.components.Name name;
        private Builder() {}

        public Builder withStartTime(org.teamvan.moneyshift.components.Timestamp value) {
            this.startTime = value;
            return this;
        }

        public Builder withName(org.teamvan.moneyshift.components.Name value) {
            this.name = value;
            return this;
        }

        public RecordingInfo build() {
            return new RecordingInfo(startTime, name);
        }
    }

    public static RecordingInfo of(
    ) {
        return new RecordingInfo(null, null);
    }
}
