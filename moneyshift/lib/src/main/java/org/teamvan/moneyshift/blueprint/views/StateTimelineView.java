// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.views;

/**
 * A view for displaying state transitions over time, for use with {@link org.teamvan.moneyshift.archetypes.StateChange}.
 * 
 */
public record StateTimelineView(
    /**
     * Configures which range on each timeline is shown by this view (unless specified differently per entity).
     * 
     * If not specified, the default is to show the entire timeline.
     * If a timeline is specified more than once, the first entry will be used.
     */
    org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges timeRanges
) {
    public static final java.lang.String VIEW_NAME = "rerun.blueprint.views.StateTimelineView";

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges timeRanges;
        private Builder() {}

        public Builder withTimeRanges(org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges value) {
            this.timeRanges = value;
            return this;
        }

        /** Overload that takes the elements of a single component directly. */
        public Builder withTimeRanges(org.teamvan.moneyshift.blueprint.components.VisibleTimeRange... value) {
            return withTimeRanges(new org.teamvan.moneyshift.blueprint.archetypes.VisibleTimeRanges(java.util.List.of(value)));
        }

        public StateTimelineView build() {
            return new StateTimelineView(timeRanges);
        }
    }
}
