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
            components.append("start_time").append('=').append(org.teamvan.moneyshift.components.Timestamp.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("name").append('=').append(org.teamvan.moneyshift.components.Name.COMPONENT_TYPE);
        }
        java.lang.String joined = components.toString();
        cachedComponents = new ComponentsCache(mask, joined);
        return joined;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        long mask = 0L;
        if (startTime != null) mask |= 1L;
        if (name != null) mask |= 2L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (startTime != null) {
            org.teamvan.moneyshift.components.Timestamp.writeColumnsStatic(w, "start_time", java.util.List.of(startTime));
        }
        if (name != null) {
            org.teamvan.moneyshift.components.Name.writeColumnsStatic(w, "name", java.util.List.of(name));
        }
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

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withStartTime(long value) {
            return withStartTime(new org.teamvan.moneyshift.components.Timestamp(value));
        }

        public Builder withName(org.teamvan.moneyshift.components.Name value) {
            this.name = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withName(java.lang.String value) {
            return withName(new org.teamvan.moneyshift.components.Name(value));
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
