// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A prepacked 3D asset ({@code .gltf}, {@code .glb}, {@code .obj}, {@code .stl}, etc.).
 * 
 * See also {@link org.teamvan.moneyshift.archetypes.Mesh3D}.
 * 
 * If there are multiple {@link org.teamvan.moneyshift.archetypes.InstancePoses3D} instances logged to the same entity as a mesh,
 * an instance of the mesh will be drawn for each transform.
 * 
 */
public record Asset3D(
    /**
     * The asset's bytes.
     */
    org.teamvan.moneyshift.components.Blob blob,
    /**
     * The Media Type of the asset.
     * 
     * Supported values:
     * * {@code model/gltf-binary}
     * * {@code model/gltf+json}
     * * {@code model/obj} (.mtl material files are not supported yet, references are silently ignored)
     * * {@code model/stl}
     * 
     * If omitted, the viewer will try to guess from the data blob.
     * If it cannot guess, it won't be able to render the asset.
     */
    org.teamvan.moneyshift.components.MediaType mediaType,
    /**
     * A color multiplier applied to the whole asset.
     * 
     * For mesh who already have {@code albedo_factor} in materials,
     * it will be overwritten by actual {@code albedo_factor} of {@link org.teamvan.moneyshift.archetypes.Asset3D} (if specified).
     */
    org.teamvan.moneyshift.components.AlbedoFactor albedoFactor
) implements org.teamvan.moneyshift.Archetype {
    public Asset3D {
        java.util.Objects.requireNonNull(blob, "blob");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Asset3D";
    public static final int NUM_COMPONENTS = 3;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_BLOB = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Asset3D:blob", "rerun.components.Blob");

    /**
     * The asset's bytes.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorBlob() {
        return DESCRIPTOR_BLOB;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_MEDIA_TYPE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Asset3D:media_type", "rerun.components.MediaType");

    /**
     * The Media Type of the asset.
     * 
     * Supported values:
     * * {@code model/gltf-binary}
     * * {@code model/gltf+json}
     * * {@code model/obj} (.mtl material files are not supported yet, references are silently ignored)
     * * {@code model/stl}
     * 
     * If omitted, the viewer will try to guess from the data blob.
     * If it cannot guess, it won't be able to render the asset.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorMediaType() {
        return DESCRIPTOR_MEDIA_TYPE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ALBEDO_FACTOR = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Asset3D:albedo_factor", "rerun.components.AlbedoFactor");

    /**
     * A color multiplier applied to the whole asset.
     * 
     * For mesh who already have {@code albedo_factor} in materials,
     * it will be overwritten by actual {@code albedo_factor} of {@link org.teamvan.moneyshift.archetypes.Asset3D} (if specified).
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorAlbedoFactor() {
        return DESCRIPTOR_ALBEDO_FACTOR;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorBlob());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorMediaType());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorAlbedoFactor());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.Blob.batch(descriptorBlob(), allocator, blob));
        if (mediaType != null) {
            batches.add(org.teamvan.moneyshift.components.MediaType.batch(descriptorMediaType(), allocator, mediaType));
        }
        if (albedoFactor != null) {
            batches.add(org.teamvan.moneyshift.components.AlbedoFactor.batch(descriptorAlbedoFactor(), allocator, albedoFactor));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Blob blob;
        private org.teamvan.moneyshift.components.MediaType mediaType;
        private org.teamvan.moneyshift.components.AlbedoFactor albedoFactor;
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

        public Builder withAlbedoFactor(org.teamvan.moneyshift.components.AlbedoFactor value) {
            this.albedoFactor = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withAlbedoFactor(long value) {
            return withAlbedoFactor(new org.teamvan.moneyshift.components.AlbedoFactor(value));
        }

        public Asset3D build() {
            return new Asset3D(blob, mediaType, albedoFactor);
        }
    }

    public static Asset3D of(
        org.teamvan.moneyshift.components.Blob blob
    ) {
        return new Asset3D(blob, null, null);
    }
}
