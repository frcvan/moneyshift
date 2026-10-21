// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A video binary.
 * 
 * Only MP4 containers are currently supported.
 * 
 * See &lt;https://rerun.io/docs/reference/video&gt; for codec support and more general information.
 * 
 * In order to display a video, you also need to log a {@link org.teamvan.moneyshift.archetypes.VideoFrameReference} for each frame.
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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_BLOB = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "AssetVideo:blob", "rerun.components.Blob");

    /**
     * The asset's bytes.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorBlob() {
        return DESCRIPTOR_BLOB;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MEDIA_TYPE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "AssetVideo:media_type", "rerun.components.MediaType");

    /**
     * The Media Type of the asset.
     * 
     * Supported values:
     * * {@code video/mp4}
     * 
     * If omitted, the viewer will try to guess from the data blob.
     * If it cannot guess, it won't be able to render the asset.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMediaType() {
        return DESCRIPTOR_MEDIA_TYPE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorBlob());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorMediaType());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of();
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.Blob.batch(descriptorBlob(), allocator, blob));
        if (mediaType != null) {
            batches.add(org.teamvan.moneyshift.components.MediaType.batch(descriptorMediaType(), allocator, mediaType));
        }
        return batches;
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
