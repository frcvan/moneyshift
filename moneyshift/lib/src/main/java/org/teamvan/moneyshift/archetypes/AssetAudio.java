// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * An audio file, stored as-is ({@code .aac}, {@code .flac}, {@code .m4a}, {@code .mp3}, {@code .ogg}, {@code .wav}).
 * 
 * The audio starts playing at the time it was logged,
 * so log it on a temporal timeline (duration or timestamp) at the point where playback should begin.
 * The viewer plays it while time is playing, in an {@code rerun::blueprint::views::AudioView}.
 * 
 */
public record AssetAudio(
    /**
     * The asset's bytes.
     */
    org.teamvan.moneyshift.components.Blob blob,
    /**
     * The Media Type of the asset.
     * 
     * For instance:
     * * {@code audio/aac} (raw ADTS stream)
     * * {@code audio/flac}
     * * {@code audio/mp4} (M4A)
     * * {@code audio/mpeg} (MP3)
     * * {@code audio/ogg}
     * * {@code audio/wav}
     * 
     * Any audio media type can be stored.
     * Which ones the viewer can decode depends on the viewer version.
     * 
     * If omitted, the viewer will try to guess from the data blob.
     * If it cannot guess, it won't be able to play the asset.
     */
    org.teamvan.moneyshift.components.MediaType mediaType
) implements org.teamvan.moneyshift.Archetype {
    public AssetAudio {
        java.util.Objects.requireNonNull(blob, "blob");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.AssetAudio";
    public static final int NUM_COMPONENTS = 2;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("blob").append('=').append(org.teamvan.moneyshift.components.Blob.COMPONENT_TYPE);
        }
        if (mediaType != null) {
            if (components.length() > 0) components.append(',');
            components.append("media_type").append('=').append(org.teamvan.moneyshift.components.MediaType.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (true) {
            org.teamvan.moneyshift.components.Blob.writeColumnsStatic(w, "blob", java.util.List.of(blob));
        }
        if (mediaType != null) {
            org.teamvan.moneyshift.components.MediaType.writeColumnsStatic(w, "media_type", java.util.List.of(mediaType));
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Blob blob;
        private org.teamvan.moneyshift.components.MediaType mediaType;
        private Builder() {}

        public Builder withBlob(org.teamvan.moneyshift.components.Blob value) {
            this.blob = value;
            return this;
        }

        public Builder withMediaType(org.teamvan.moneyshift.components.MediaType value) {
            this.mediaType = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withMediaType(java.lang.String value) {
            return withMediaType(new org.teamvan.moneyshift.components.MediaType(value));
        }

        public AssetAudio build() {
            return new AssetAudio(blob, mediaType);
        }
    }

    public static AssetAudio of(
        org.teamvan.moneyshift.components.Blob blob
    ) {
        return new AssetAudio(blob, null);
    }
}
