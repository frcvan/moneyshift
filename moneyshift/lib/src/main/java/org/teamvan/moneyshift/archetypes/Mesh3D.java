// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A 3D triangle mesh as specified by its per-mesh and per-vertex properties.
 * 
 * See also {@link org.teamvan.moneyshift.archetypes.Asset3D}.
 * 
 * If there are multiple {@link org.teamvan.moneyshift.archetypes.InstancePoses3D} instances logged to the same entity as a mesh,
 * an instance of the mesh will be drawn for each transform.
 * 
 * For transparency ordering, as well as back face culling (disabled by default),
 * front faces are assumed to be those with counter clockwise triangle winding order
 * (this is the same as in the GLTF specification).
 * 
 */
public record Mesh3D(
    /**
     * The positions of each vertex.
     * 
     * If no {@code triangle_indices} are specified, then each triplet of positions is interpreted as a triangle.
     */
    java.util.List<org.teamvan.moneyshift.components.Position3D> vertexPositions,
    /**
     * Optional indices for the triangles that make up the mesh.
     */
    java.util.List<org.teamvan.moneyshift.components.TriangleIndices> triangleIndices,
    /**
     * An optional normal for each vertex.
     */
    java.util.List<org.teamvan.moneyshift.components.Vector3D> vertexNormals,
    /**
     * An optional color for each vertex.
     * 
     * The alpha channel is ignored.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> vertexColors,
    /**
     * An optional uv texture coordinate for each vertex.
     */
    java.util.List<org.teamvan.moneyshift.components.Texcoord2D> vertexTexcoords,
    /**
     * A color multiplier applied to the whole mesh.
     * 
     * Alpha channel governs the overall mesh transparency.
     */
    org.teamvan.moneyshift.components.AlbedoFactor albedoFactor,
    /**
     * Determines which faces of the mesh are rendered.
     * 
     * The default is {@link org.teamvan.moneyshift.components.MeshFaceRendering.DoubleSided}, meaning both front and back faces are shown.
     */
    org.teamvan.moneyshift.components.MeshFaceRendering faceRendering,
    /**
     * Optional albedo texture.
     * 
     * Used with the {@link org.teamvan.moneyshift.components.Texcoord2D} of the mesh.
     * 
     * Currently supports only sRGB(A) textures, ignoring alpha.
     * (meaning that the tensor must have 3 or 4 channels and use the {@code u8} format)
     * 
     * The alpha channel is ignored.
     */
    org.teamvan.moneyshift.components.ImageBuffer albedoTextureBuffer,
    /**
     * The format of the {@code albedo_texture_buffer}, if any.
     */
    org.teamvan.moneyshift.components.ImageFormat albedoTextureFormat,
    /**
     * Optional class Ids for the vertices.
     * 
     * The {@link org.teamvan.moneyshift.components.ClassId} provides colors and labels if not specified explicitly.
     */
    java.util.List<org.teamvan.moneyshift.components.ClassId> classIds
) implements org.teamvan.moneyshift.Archetype {
    public Mesh3D {
        java.util.Objects.requireNonNull(vertexPositions, "vertexPositions");
        vertexPositions = java.util.List.copyOf(vertexPositions);
        if (triangleIndices != null) { triangleIndices = java.util.List.copyOf(triangleIndices); }
        if (vertexNormals != null) { vertexNormals = java.util.List.copyOf(vertexNormals); }
        if (vertexColors != null) { vertexColors = java.util.List.copyOf(vertexColors); }
        if (vertexTexcoords != null) { vertexTexcoords = java.util.List.copyOf(vertexTexcoords); }
        if (classIds != null) { classIds = java.util.List.copyOf(classIds); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Mesh3D";
    public static final int NUM_COMPONENTS = 10;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VERTEX_POSITIONS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Mesh3D:vertex_positions", "rerun.components.Position3D");

    /**
     * The positions of each vertex.
     * 
     * If no {@code triangle_indices} are specified, then each triplet of positions is interpreted as a triangle.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVertexPositions() {
        return DESCRIPTOR_VERTEX_POSITIONS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TRIANGLE_INDICES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Mesh3D:triangle_indices", "rerun.components.TriangleIndices");

    /**
     * Optional indices for the triangles that make up the mesh.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTriangleIndices() {
        return DESCRIPTOR_TRIANGLE_INDICES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VERTEX_NORMALS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Mesh3D:vertex_normals", "rerun.components.Vector3D");

    /**
     * An optional normal for each vertex.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVertexNormals() {
        return DESCRIPTOR_VERTEX_NORMALS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VERTEX_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Mesh3D:vertex_colors", "rerun.components.Color");

    /**
     * An optional color for each vertex.
     * 
     * The alpha channel is ignored.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVertexColors() {
        return DESCRIPTOR_VERTEX_COLORS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VERTEX_TEXCOORDS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Mesh3D:vertex_texcoords", "rerun.components.Texcoord2D");

    /**
     * An optional uv texture coordinate for each vertex.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVertexTexcoords() {
        return DESCRIPTOR_VERTEX_TEXCOORDS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ALBEDO_FACTOR = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Mesh3D:albedo_factor", "rerun.components.AlbedoFactor");

    /**
     * A color multiplier applied to the whole mesh.
     * 
     * Alpha channel governs the overall mesh transparency.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorAlbedoFactor() {
        return DESCRIPTOR_ALBEDO_FACTOR;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FACE_RENDERING = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Mesh3D:face_rendering", "rerun.components.MeshFaceRendering");

    /**
     * Determines which faces of the mesh are rendered.
     * 
     * The default is {@link org.teamvan.moneyshift.components.MeshFaceRendering.DoubleSided}, meaning both front and back faces are shown.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFaceRendering() {
        return DESCRIPTOR_FACE_RENDERING;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ALBEDO_TEXTURE_BUFFER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Mesh3D:albedo_texture_buffer", "rerun.components.ImageBuffer");

    /**
     * Optional albedo texture.
     * 
     * Used with the {@link org.teamvan.moneyshift.components.Texcoord2D} of the mesh.
     * 
     * Currently supports only sRGB(A) textures, ignoring alpha.
     * (meaning that the tensor must have 3 or 4 channels and use the {@code u8} format)
     * 
     * The alpha channel is ignored.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorAlbedoTextureBuffer() {
        return DESCRIPTOR_ALBEDO_TEXTURE_BUFFER;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ALBEDO_TEXTURE_FORMAT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Mesh3D:albedo_texture_format", "rerun.components.ImageFormat");

    /**
     * The format of the {@code albedo_texture_buffer}, if any.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorAlbedoTextureFormat() {
        return DESCRIPTOR_ALBEDO_TEXTURE_FORMAT;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CLASS_IDS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Mesh3D:class_ids", "rerun.components.ClassId");

    /**
     * Optional class Ids for the vertices.
     * 
     * The {@link org.teamvan.moneyshift.components.ClassId} provides colors and labels if not specified explicitly.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorClassIds() {
        return DESCRIPTOR_CLASS_IDS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorVertexPositions());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorTriangleIndices(), descriptorVertexNormals());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorVertexColors(), descriptorVertexTexcoords(), descriptorAlbedoFactor(), descriptorFaceRendering(), descriptorAlbedoTextureBuffer(), descriptorAlbedoTextureFormat(), descriptorClassIds());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.Position3D.batch(descriptorVertexPositions(), allocator, vertexPositions));
        if (triangleIndices != null) {
            batches.add(org.teamvan.moneyshift.components.TriangleIndices.batch(descriptorTriangleIndices(), allocator, triangleIndices));
        }
        if (vertexNormals != null) {
            batches.add(org.teamvan.moneyshift.components.Vector3D.batch(descriptorVertexNormals(), allocator, vertexNormals));
        }
        if (vertexColors != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorVertexColors(), allocator, vertexColors));
        }
        if (vertexTexcoords != null) {
            batches.add(org.teamvan.moneyshift.components.Texcoord2D.batch(descriptorVertexTexcoords(), allocator, vertexTexcoords));
        }
        if (albedoFactor != null) {
            batches.add(org.teamvan.moneyshift.components.AlbedoFactor.batch(descriptorAlbedoFactor(), allocator, albedoFactor));
        }
        if (faceRendering != null) {
            batches.add(org.teamvan.moneyshift.components.MeshFaceRendering.batch(descriptorFaceRendering(), allocator, faceRendering));
        }
        if (albedoTextureBuffer != null) {
            batches.add(org.teamvan.moneyshift.components.ImageBuffer.batch(descriptorAlbedoTextureBuffer(), allocator, albedoTextureBuffer));
        }
        if (albedoTextureFormat != null) {
            batches.add(org.teamvan.moneyshift.components.ImageFormat.batch(descriptorAlbedoTextureFormat(), allocator, albedoTextureFormat));
        }
        if (classIds != null) {
            batches.add(org.teamvan.moneyshift.components.ClassId.batch(descriptorClassIds(), allocator, classIds));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.Position3D> vertexPositions;
        private java.util.List<org.teamvan.moneyshift.components.TriangleIndices> triangleIndices;
        private java.util.List<org.teamvan.moneyshift.components.Vector3D> vertexNormals;
        private java.util.List<org.teamvan.moneyshift.components.Color> vertexColors;
        private java.util.List<org.teamvan.moneyshift.components.Texcoord2D> vertexTexcoords;
        private org.teamvan.moneyshift.components.AlbedoFactor albedoFactor;
        private org.teamvan.moneyshift.components.MeshFaceRendering faceRendering;
        private org.teamvan.moneyshift.components.ImageBuffer albedoTextureBuffer;
        private org.teamvan.moneyshift.components.ImageFormat albedoTextureFormat;
        private java.util.List<org.teamvan.moneyshift.components.ClassId> classIds;
        private Builder() {}

        public Builder withVertexPositions(java.util.List<org.teamvan.moneyshift.components.Position3D> value) {
            this.vertexPositions = java.util.List.copyOf(value);
            return this;
        }

        public Builder withVertexPositions(org.teamvan.moneyshift.components.Position3D... value) {
            return withVertexPositions(java.util.List.of(value));
        }

        public Builder withTriangleIndices(java.util.List<org.teamvan.moneyshift.components.TriangleIndices> value) {
            this.triangleIndices = java.util.List.copyOf(value);
            return this;
        }

        public Builder withTriangleIndices(org.teamvan.moneyshift.components.TriangleIndices... value) {
            return withTriangleIndices(java.util.List.of(value));
        }

        public Builder withVertexNormals(java.util.List<org.teamvan.moneyshift.components.Vector3D> value) {
            this.vertexNormals = java.util.List.copyOf(value);
            return this;
        }

        public Builder withVertexNormals(org.teamvan.moneyshift.components.Vector3D... value) {
            return withVertexNormals(java.util.List.of(value));
        }

        public Builder withVertexColors(java.util.List<org.teamvan.moneyshift.components.Color> value) {
            this.vertexColors = java.util.List.copyOf(value);
            return this;
        }

        public Builder withVertexColors(org.teamvan.moneyshift.components.Color... value) {
            return withVertexColors(java.util.List.of(value));
        }

        public Builder withVertexTexcoords(java.util.List<org.teamvan.moneyshift.components.Texcoord2D> value) {
            this.vertexTexcoords = java.util.List.copyOf(value);
            return this;
        }

        public Builder withVertexTexcoords(org.teamvan.moneyshift.components.Texcoord2D... value) {
            return withVertexTexcoords(java.util.List.of(value));
        }

        public Builder withAlbedoFactor(org.teamvan.moneyshift.components.AlbedoFactor value) {
            this.albedoFactor = value;
            return this;
        }

        public Builder withFaceRendering(org.teamvan.moneyshift.components.MeshFaceRendering value) {
            this.faceRendering = value;
            return this;
        }

        public Builder withAlbedoTextureBuffer(org.teamvan.moneyshift.components.ImageBuffer value) {
            this.albedoTextureBuffer = value;
            return this;
        }

        public Builder withAlbedoTextureFormat(org.teamvan.moneyshift.components.ImageFormat value) {
            this.albedoTextureFormat = value;
            return this;
        }

        public Builder withClassIds(java.util.List<org.teamvan.moneyshift.components.ClassId> value) {
            this.classIds = java.util.List.copyOf(value);
            return this;
        }

        public Builder withClassIds(org.teamvan.moneyshift.components.ClassId... value) {
            return withClassIds(java.util.List.of(value));
        }

        public Mesh3D build() {
            return new Mesh3D(vertexPositions, triangleIndices, vertexNormals, vertexColors, vertexTexcoords, albedoFactor, faceRendering, albedoTextureBuffer, albedoTextureFormat, classIds);
        }
    }

    public static Mesh3D of(
        java.util.List<org.teamvan.moneyshift.components.Position3D> vertexPositions
    ) {
        return new Mesh3D(vertexPositions, null, null, null, null, null, null, null, null, null);
    }
}
