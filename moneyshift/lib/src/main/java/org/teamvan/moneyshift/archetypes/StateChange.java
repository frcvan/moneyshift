// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A state change, representing a transition of an entity into a new state.
 * 
 * Useful for representing discrete state machines, mode transitions, or
 * state changes over time. Each logged {@link org.teamvan.moneyshift.archetypes.StateChange} marks a new state
 * at the given time. A {@code null} state resets the state, showing a gap in the state timeline view.
 * 
 * The state timeline view displays these as horizontal colored lanes over time.
 * 
 */
public record StateChange(
    /**
     * The new state values; each instance gets its own lane in the state timeline view.
     * 
     * A reset ends the previous state and shows a gap in the state timeline view until the
     * next state. An empty string, a null array entry, and an empty state array (e.g. from
     * clearing the field) all act as resets.
     * 
     * The length of the state array should not change over time.
     */
    java.util.List<org.teamvan.moneyshift.components.Text> state
) implements org.teamvan.moneyshift.Archetype {
    public StateChange {
        java.util.Objects.requireNonNull(state, "state");
        state = java.util.List.copyOf(state);
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.StateChange";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_STATE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "StateChange:state", "rerun.components.Text");

    /**
     * The new state values; each instance gets its own lane in the state timeline view.
     * 
     * A reset ends the previous state and shows a gap in the state timeline view until the
     * next state. An empty string, a null array entry, and an empty state array (e.g. from
     * clearing the field) all act as resets.
     * 
     * The length of the state array should not change over time.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorState() {
        return DESCRIPTOR_STATE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorState());
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
        batches.add(org.teamvan.moneyshift.components.Text.batch(descriptorState(), allocator, state));
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.Text> state;
        private Builder() {}

        public Builder withState(java.util.List<org.teamvan.moneyshift.components.Text> value) {
            this.state = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withState(java.lang.String... value) {
            java.util.List<org.teamvan.moneyshift.components.Text> values = new java.util.ArrayList<>(value.length);
            for (java.lang.String v : value) {
                values.add(new org.teamvan.moneyshift.components.Text(v));
            }
            return withState(values);
        }

        public Builder withState(org.teamvan.moneyshift.components.Text... value) {
            return withState(java.util.List.of(value));
        }

        public StateChange build() {
            return new StateChange(state);
        }
    }

    public static StateChange of(
        java.util.List<org.teamvan.moneyshift.components.Text> state
    ) {
        return new StateChange(state);
    }
}
