// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A state change, representing a transition of an entity into a new state.
 * 
 * Useful for representing discrete state machines, mode transitions, or
 * state changes over time. Each logged {@link org.teamvan.moneyshift.archetypes.StateChange rerun::archetypes::StateChange} marks a new state
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
            components.append("state").append('=').append(org.teamvan.moneyshift.components.Text.COMPONENT_TYPE);
        }
        java.lang.String joined = components.toString();
        cachedComponents = new ComponentsCache(mask, joined);
        return joined;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        long mask = 0L;
        if (!state.isEmpty()) mask |= 1L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (!state.isEmpty()) {
            org.teamvan.moneyshift.components.Text.writeColumnsStatic(w, "state", state);
        }
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
