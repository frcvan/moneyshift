// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A prepacked 3D asset ({@code .gltf}, {@code .glb}, {@code .obj}, {@code .stl}, etc.).
 * 
 * See also {@link org.teamvan.moneyshift.archetypes.Mesh3D rerun::archetypes::Mesh3D}.
 * 
 * If there are multiple {@link org.teamvan.moneyshift.archetypes.InstancePoses3D rerun::archetypes::InstancePoses3D} instances logged to the same entity as a mesh,
 * an instance of the mesh will be drawn for each transform.
 * 
 * Meshes, or parts of meshes, without vertex normals get flat normals computed by the Viewer,
 * i.e. each triangle is shaded with its own face normal.
 * To render an asset unlit instead, give it all-zero vertex normals.
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
     * * {@code application/x-ply}
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
     * it will be overwritten by actual {@code albedo_factor} of {@link org.teamvan.moneyshift.archetypes.Asset3D rerun::archetypes::Asset3D} (if specified).
     */
    org.teamvan.moneyshift.components.AlbedoFactor albedoFactor
) implements org.teamvan.moneyshift.Archetype {
    public Asset3D {
        java.util.Objects.requireNonNull(blob, "blob");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Asset3D";
    public static final int NUM_COMPONENTS = 3;

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
            components.append("blob").append('=').append(org.teamvan.moneyshift.components.Blob.COMPONENT_TYPE);
        }
        if ((mask & 2L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("media_type").append('=').append(org.teamvan.moneyshift.components.MediaType.COMPONENT_TYPE);
        }
        if ((mask & 4L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("albedo_factor").append('=').append(org.teamvan.moneyshift.components.AlbedoFactor.COMPONENT_TYPE);
        }
        java.lang.String joined = components.toString();
        cachedComponents = new ComponentsCache(mask, joined);
        return joined;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        long mask = 0L;
        if (true) mask |= 1L;
        if (mediaType != null) mask |= 2L;
        if (albedoFactor != null) mask |= 4L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (true) {
            org.teamvan.moneyshift.components.Blob.writeColumnsStatic(w, "blob", java.util.List.of(blob));
        }
        if (mediaType != null) {
            org.teamvan.moneyshift.components.MediaType.writeColumnsStatic(w, "media_type", java.util.List.of(mediaType));
        }
        if (albedoFactor != null) {
            org.teamvan.moneyshift.components.AlbedoFactor.writeColumnsStatic(w, "albedo_factor", java.util.List.of(albedoFactor));
        }
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
