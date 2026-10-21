// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Time panel specific state.
 */
public record TimePanelBlueprint(
    /**
     * Current state of the panel.
     */
    org.teamvan.moneyshift.blueprint.components.PanelState state,
    /**
     * What timeline the panel is on.
     */
    org.teamvan.moneyshift.blueprint.components.TimelineName timeline,
    /**
     * A time playback speed multiplier.
     */
    org.teamvan.moneyshift.blueprint.components.PlaybackSpeed playbackSpeed,
    /**
     * Frames per second. Only applicable for sequence timelines.
     */
    org.teamvan.moneyshift.blueprint.components.Fps fps,
    /**
     * If the time is currently paused, playing, or following.
     * 
     * Defaults to either playing or following, depending on the data source.
     */
    org.teamvan.moneyshift.blueprint.components.PlayState playState,
    /**
     * How the time should loop. A selection loop only works if there is also a {@code time_selection} passed.
     * 
     * Defaults to off.
     */
    org.teamvan.moneyshift.blueprint.components.LoopMode loopMode,
    /**
     * Selects a range of time on the time panel.
     */
    org.teamvan.moneyshift.blueprint.components.AbsoluteTimeRange timeSelection
) implements org.teamvan.moneyshift.Archetype {
    public TimePanelBlueprint {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.TimePanelBlueprint";
    public static final int NUM_COMPONENTS = 7;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_STATE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TimePanelBlueprint:state", "rerun.blueprint.components.PanelState");

    /**
     * Current state of the panel.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorState() {
        return DESCRIPTOR_STATE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TIMELINE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TimePanelBlueprint:timeline", "rerun.blueprint.components.TimelineName");

    /**
     * What timeline the panel is on.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTimeline() {
        return DESCRIPTOR_TIMELINE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_PLAYBACK_SPEED = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TimePanelBlueprint:playback_speed", "rerun.blueprint.components.PlaybackSpeed");

    /**
     * A time playback speed multiplier.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorPlaybackSpeed() {
        return DESCRIPTOR_PLAYBACK_SPEED;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FPS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TimePanelBlueprint:fps", "rerun.blueprint.components.Fps");

    /**
     * Frames per second. Only applicable for sequence timelines.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFps() {
        return DESCRIPTOR_FPS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_PLAY_STATE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TimePanelBlueprint:play_state", "rerun.blueprint.components.PlayState");

    /**
     * If the time is currently paused, playing, or following.
     * 
     * Defaults to either playing or following, depending on the data source.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorPlayState() {
        return DESCRIPTOR_PLAY_STATE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LOOP_MODE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TimePanelBlueprint:loop_mode", "rerun.blueprint.components.LoopMode");

    /**
     * How the time should loop. A selection loop only works if there is also a {@code time_selection} passed.
     * 
     * Defaults to off.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLoopMode() {
        return DESCRIPTOR_LOOP_MODE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TIME_SELECTION = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TimePanelBlueprint:time_selection", "rerun.blueprint.components.AbsoluteTimeRange");

    /**
     * Selects a range of time on the time panel.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTimeSelection() {
        return DESCRIPTOR_TIME_SELECTION;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorState(), descriptorTimeline(), descriptorPlaybackSpeed(), descriptorFps(), descriptorPlayState(), descriptorLoopMode(), descriptorTimeSelection());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (state != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.PanelState.batch(descriptorState(), allocator, state));
        }
        if (timeline != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.TimelineName.batch(descriptorTimeline(), allocator, timeline));
        }
        if (playbackSpeed != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.PlaybackSpeed.batch(descriptorPlaybackSpeed(), allocator, playbackSpeed));
        }
        if (fps != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.Fps.batch(descriptorFps(), allocator, fps));
        }
        if (playState != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.PlayState.batch(descriptorPlayState(), allocator, playState));
        }
        if (loopMode != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.LoopMode.batch(descriptorLoopMode(), allocator, loopMode));
        }
        if (timeSelection != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.AbsoluteTimeRange.batch(descriptorTimeSelection(), allocator, timeSelection));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.PanelState state;
        private org.teamvan.moneyshift.blueprint.components.TimelineName timeline;
        private org.teamvan.moneyshift.blueprint.components.PlaybackSpeed playbackSpeed;
        private org.teamvan.moneyshift.blueprint.components.Fps fps;
        private org.teamvan.moneyshift.blueprint.components.PlayState playState;
        private org.teamvan.moneyshift.blueprint.components.LoopMode loopMode;
        private org.teamvan.moneyshift.blueprint.components.AbsoluteTimeRange timeSelection;
        private Builder() {}

        public Builder withState(org.teamvan.moneyshift.blueprint.components.PanelState value) {
            this.state = value;
            return this;
        }

        public Builder withTimeline(org.teamvan.moneyshift.blueprint.components.TimelineName value) {
            this.timeline = value;
            return this;
        }

        public Builder withPlaybackSpeed(org.teamvan.moneyshift.blueprint.components.PlaybackSpeed value) {
            this.playbackSpeed = value;
            return this;
        }

        public Builder withFps(org.teamvan.moneyshift.blueprint.components.Fps value) {
            this.fps = value;
            return this;
        }

        public Builder withPlayState(org.teamvan.moneyshift.blueprint.components.PlayState value) {
            this.playState = value;
            return this;
        }

        public Builder withLoopMode(org.teamvan.moneyshift.blueprint.components.LoopMode value) {
            this.loopMode = value;
            return this;
        }

        public Builder withTimeSelection(org.teamvan.moneyshift.blueprint.components.AbsoluteTimeRange value) {
            this.timeSelection = value;
            return this;
        }

        public TimePanelBlueprint build() {
            return new TimePanelBlueprint(state, timeline, playbackSpeed, fps, playState, loopMode, timeSelection);
        }
    }

    public static TimePanelBlueprint of(
    ) {
        return new TimePanelBlueprint(null, null, null, null, null, null, null);
    }
}
