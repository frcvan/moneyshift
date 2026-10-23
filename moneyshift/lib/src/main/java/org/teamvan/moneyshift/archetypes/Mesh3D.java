// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * A 3D triangle mesh as specified by its per-mesh and per-vertex properties.
 * 
 * See also {@link org.teamvan.moneyshift.archetypes.Asset3D rerun::archetypes::Asset3D}.
 * 
 * If there are multiple {@link org.teamvan.moneyshift.archetypes.InstancePoses3D rerun::archetypes::InstancePoses3D} instances logged to the same entity as a mesh,
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
     * The default is {@link org.teamvan.moneyshift.components.MeshFaceRendering#DoubleSided rerun::components::MeshFaceRendering::DoubleSided}, meaning both front and back faces are shown.
     */
    org.teamvan.moneyshift.components.MeshFaceRendering faceRendering,
    /**
     * Optional albedo texture.
     * 
     * Used with the {@link org.teamvan.moneyshift.components.Texcoord2D rerun::components::Texcoord2D} of the mesh.
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
     * The {@link org.teamvan.moneyshift.components.ClassId rerun::components::ClassId} provides colors and labels if not specified explicitly.
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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (!vertexPositions.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("vertex_positions").append('=').append(org.teamvan.moneyshift.components.Position3D.COMPONENT_TYPE);
        }
        if (triangleIndices != null && !triangleIndices.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("triangle_indices").append('=').append(org.teamvan.moneyshift.components.TriangleIndices.COMPONENT_TYPE);
        }
        if (vertexNormals != null && !vertexNormals.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("vertex_normals").append('=').append(org.teamvan.moneyshift.components.Vector3D.COMPONENT_TYPE);
        }
        if (vertexColors != null && !vertexColors.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("vertex_colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if (vertexTexcoords != null && !vertexTexcoords.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("vertex_texcoords").append('=').append(org.teamvan.moneyshift.components.Texcoord2D.COMPONENT_TYPE);
        }
        if (albedoFactor != null) {
            if (components.length() > 0) components.append(',');
            components.append("albedo_factor").append('=').append(org.teamvan.moneyshift.components.AlbedoFactor.COMPONENT_TYPE);
        }
        if (faceRendering != null) {
            if (components.length() > 0) components.append(',');
            components.append("face_rendering").append('=').append(org.teamvan.moneyshift.components.MeshFaceRendering.COMPONENT_TYPE);
        }
        if (albedoTextureBuffer != null) {
            if (components.length() > 0) components.append(',');
            components.append("albedo_texture_buffer").append('=').append(org.teamvan.moneyshift.components.ImageBuffer.COMPONENT_TYPE);
        }
        if (albedoTextureFormat != null) {
            if (components.length() > 0) components.append(',');
            components.append("albedo_texture_format").append('=').append(org.teamvan.moneyshift.components.ImageFormat.COMPONENT_TYPE);
        }
        if (classIds != null && !classIds.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("class_ids").append('=').append(org.teamvan.moneyshift.components.ClassId.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (!vertexPositions.isEmpty()) {
            org.teamvan.moneyshift.components.Position3D.writeColumnsStatic(w, "vertex_positions", vertexPositions);
        }
        if (triangleIndices != null && !triangleIndices.isEmpty()) {
            org.teamvan.moneyshift.components.TriangleIndices.writeColumnsStatic(w, "triangle_indices", triangleIndices);
        }
        if (vertexNormals != null && !vertexNormals.isEmpty()) {
            org.teamvan.moneyshift.components.Vector3D.writeColumnsStatic(w, "vertex_normals", vertexNormals);
        }
        if (vertexColors != null && !vertexColors.isEmpty()) {
            org.teamvan.moneyshift.components.Color.writeColumnsStatic(w, "vertex_colors", vertexColors);
        }
        if (vertexTexcoords != null && !vertexTexcoords.isEmpty()) {
            org.teamvan.moneyshift.components.Texcoord2D.writeColumnsStatic(w, "vertex_texcoords", vertexTexcoords);
        }
        if (albedoFactor != null) {
            org.teamvan.moneyshift.components.AlbedoFactor.writeColumnsStatic(w, "albedo_factor", java.util.List.of(albedoFactor));
        }
        if (faceRendering != null) {
            org.teamvan.moneyshift.components.MeshFaceRendering.writeColumnsStatic(w, "face_rendering", java.util.List.of(faceRendering));
        }
        if (albedoTextureBuffer != null) {
            org.teamvan.moneyshift.components.ImageBuffer.writeColumnsStatic(w, "albedo_texture_buffer", java.util.List.of(albedoTextureBuffer));
        }
        if (albedoTextureFormat != null) {
            org.teamvan.moneyshift.components.ImageFormat.writeColumnsStatic(w, "albedo_texture_format", java.util.List.of(albedoTextureFormat));
        }
        if (classIds != null && !classIds.isEmpty()) {
            org.teamvan.moneyshift.components.ClassId.writeColumnsStatic(w, "class_ids", classIds);
        }
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

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withVertexColors(long... value) {
            java.util.List<org.teamvan.moneyshift.components.Color> values = new java.util.ArrayList<>(value.length);
            for (long v : value) {
                values.add(new org.teamvan.moneyshift.components.Color(v));
            }
            return withVertexColors(values);
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

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withAlbedoFactor(long value) {
            return withAlbedoFactor(new org.teamvan.moneyshift.components.AlbedoFactor(value));
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

        /** Overload that takes the {@code int} value(s) directly. */
        public Builder withClassIds(int... value) {
            java.util.List<org.teamvan.moneyshift.components.ClassId> values = new java.util.ArrayList<>(value.length);
            for (int v : value) {
                values.add(new org.teamvan.moneyshift.components.ClassId(v));
            }
            return withClassIds(values);
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
