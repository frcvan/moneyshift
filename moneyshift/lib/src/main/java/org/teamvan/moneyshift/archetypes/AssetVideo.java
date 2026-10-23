// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A video binary.
 * 
 * Only MP4 containers are currently supported.
 * 
 * See &lt;https://rerun.io/docs/reference/video&gt; for codec support and more general information.
 * 
 * In order to display a video, you also need to log a {@link org.teamvan.moneyshift.archetypes.VideoFrameReference rerun::archetypes::VideoFrameReference} for each frame.
 * 
 */
public record AssetVideo(
    /**
     * The asset's bytes.
     */
    org.teamvan.moneyshift.components.Blob blob,
    /**
     * The Media Type of the asset.
     * 
     * Supported values:
     * * {@code video/mp4}
     * 
     * If omitted, the viewer will try to guess from the data blob.
     * If it cannot guess, it won't be able to render the asset.
     */
    org.teamvan.moneyshift.components.MediaType mediaType
) implements org.teamvan.moneyshift.Archetype {
    public AssetVideo {
        java.util.Objects.requireNonNull(blob, "blob");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.AssetVideo";
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

        public AssetVideo build() {
            return new AssetVideo(blob, mediaType);
        }
    }

    public static AssetVideo of(
        org.teamvan.moneyshift.components.Blob blob
    ) {
        return new AssetVideo(blob, null);
    }
}
