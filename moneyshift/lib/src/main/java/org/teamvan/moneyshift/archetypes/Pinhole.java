// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Camera perspective projection (a.k.a. intrinsics).
 * 
 * If {@link org.teamvan.moneyshift.archetypes.Transform3D} is logged for the same child/parent relationship (e.g. for the camera extrinsics), it takes precedence over {@link org.teamvan.moneyshift.archetypes.Pinhole}.
 * 
 * If you use named transform frames via the {@code child_frame} and {@code parent_frame} fields, you don't have to use {@link org.teamvan.moneyshift.archetypes.CoordinateFrame}
 * as it is the case with other visualizations: for any entity with an {@link org.teamvan.moneyshift.archetypes.Pinhole} the viewer will always visualize it
 * directly without needing a {@link org.teamvan.moneyshift.archetypes.CoordinateFrame} to refer to the pinhole's child/parent frame.
 * 
 */
public record Pinhole(
    /**
     * Camera projection, from image coordinates to view coordinates.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    org.teamvan.moneyshift.components.PinholeProjection imageFromCamera,
    /**
     * Pixel resolution (usually integers) of child image space. Width and height.
     * 
     * Example:
     * {@code }`text
     * [1920.0, 1440.0]
     * {@code }`
     * 
     * {@code image_from_camera} project onto the space spanned by {@code (0,0)} and {@code resolution - 1}.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    org.teamvan.moneyshift.components.Resolution resolution,
    /**
     * Sets the camera orientation convention.
     * 
     * All common values are available as constants on the {@link org.teamvan.moneyshift.components.ViewCoordinates} class.
     * 
     * The default is {@code ViewCoordinates::RDF}: +X is right, +Y is down, and +Z is forward.
     * This makes the camera frustum point along +Z in the parent space, with its up direction along -Y.
     * 
     * The camera frustum points along the axis set to {@code F}, or opposite the axis set to {@code B}.
     * When logging a depth image under this entity, this is the direction in which the point cloud is projected.
     * 
     * The frustum's up direction is the axis set to {@code U}, or opposite the axis set to {@code D}.
     * This matches the -Y direction of pixel space, where all images use RDF coordinates.
     * 
     * The frustum's right direction is the axis set to {@code R}, or opposite the axis set to {@code L}.
     * This matches the +X direction of pixel space.
     * 
     * Other common formats are {@code RUB} (X=Right, Y=Up, Z=Back) and {@code FLU} (X=Forward, Y=Left, Z=Up).
     * 
     * {@code image_from_camera} is always defined to project along +Z in camera coordinates.
     * {@code camera_xyz} reorients that projection to the forward axis of the pinhole entity.
     */
    org.teamvan.moneyshift.components.ViewCoordinates cameraXyz,
    /**
     * The child frame this transform transforms from.
     * 
     * The entity at which the transform relationship of any given child frame is specified mustn't change over time, but is allowed to be different for static time.
     * E.g. if you specified the child frame {@code "robot_arm"} on an entity named {@code "my_transforms"}, you may not log transforms
     * with the child frame {@code "robot_arm"} on any other entity than {@code "my_transforms"} unless one of them was logged with static time.
     * 
     * If not specified, this is set to the implicit transform frame of the current entity path.
     * This means that if a {@link org.teamvan.moneyshift.archetypes.Transform3D} is set on an entity called {@code /my/entity/path} then this will default to {@code tf#/my/entity/path}.
     * 
     * To set the frame an entity is part of see {@link org.teamvan.moneyshift.archetypes.CoordinateFrame}.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    org.teamvan.moneyshift.components.TransformFrameId childFrame,
    /**
     * The parent frame this transform transforms into.
     * 
     * If not specified, this is set to the implicit transform frame of the current entity path's parent.
     * This means that if a {@link org.teamvan.moneyshift.archetypes.Transform3D} is set on an entity called {@code /my/entity/path} then this will default to {@code tf#/my/entity}.
     * 
     * To set the frame an entity is part of see {@link org.teamvan.moneyshift.archetypes.CoordinateFrame}.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    org.teamvan.moneyshift.components.TransformFrameId parentFrame,
    /**
     * The distance from the camera origin to the image plane when the projection is shown in a 3D viewer.
     * 
     * This is only used for visualization purposes, and does not affect the projection itself.
     */
    org.teamvan.moneyshift.components.ImagePlaneDistance imagePlaneDistance,
    /**
     * Color of the camera wireframe.
     */
    org.teamvan.moneyshift.components.Color color,
    /**
     * Width of the camera wireframe lines.
     */
    org.teamvan.moneyshift.components.Radius lineWidth
) implements org.teamvan.moneyshift.Archetype {
    public Pinhole {
        java.util.Objects.requireNonNull(imageFromCamera, "imageFromCamera");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Pinhole";
    public static final int NUM_COMPONENTS = 8;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_IMAGE_FROM_CAMERA = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Pinhole:image_from_camera", "rerun.components.PinholeProjection");

    /**
     * Camera projection, from image coordinates to view coordinates.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorImageFromCamera() {
        return DESCRIPTOR_IMAGE_FROM_CAMERA;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_RESOLUTION = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Pinhole:resolution", "rerun.components.Resolution");

    /**
     * Pixel resolution (usually integers) of child image space. Width and height.
     * 
     * Example:
     * {@code }`text
     * [1920.0, 1440.0]
     * {@code }`
     * 
     * {@code image_from_camera} project onto the space spanned by {@code (0,0)} and {@code resolution - 1}.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorResolution() {
        return DESCRIPTOR_RESOLUTION;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CAMERA_XYZ = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Pinhole:camera_xyz", "rerun.components.ViewCoordinates");

    /**
     * Sets the camera orientation convention.
     * 
     * All common values are available as constants on the {@link org.teamvan.moneyshift.components.ViewCoordinates} class.
     * 
     * The default is {@code ViewCoordinates::RDF}: +X is right, +Y is down, and +Z is forward.
     * This makes the camera frustum point along +Z in the parent space, with its up direction along -Y.
     * 
     * The camera frustum points along the axis set to {@code F}, or opposite the axis set to {@code B}.
     * When logging a depth image under this entity, this is the direction in which the point cloud is projected.
     * 
     * The frustum's up direction is the axis set to {@code U}, or opposite the axis set to {@code D}.
     * This matches the -Y direction of pixel space, where all images use RDF coordinates.
     * 
     * The frustum's right direction is the axis set to {@code R}, or opposite the axis set to {@code L}.
     * This matches the +X direction of pixel space.
     * 
     * Other common formats are {@code RUB} (X=Right, Y=Up, Z=Back) and {@code FLU} (X=Forward, Y=Left, Z=Up).
     * 
     * {@code image_from_camera} is always defined to project along +Z in camera coordinates.
     * {@code camera_xyz} reorients that projection to the forward axis of the pinhole entity.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorCameraXyz() {
        return DESCRIPTOR_CAMERA_XYZ;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CHILD_FRAME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Pinhole:child_frame", "rerun.components.TransformFrameId");

    /**
     * The child frame this transform transforms from.
     * 
     * The entity at which the transform relationship of any given child frame is specified mustn't change over time, but is allowed to be different for static time.
     * E.g. if you specified the child frame {@code "robot_arm"} on an entity named {@code "my_transforms"}, you may not log transforms
     * with the child frame {@code "robot_arm"} on any other entity than {@code "my_transforms"} unless one of them was logged with static time.
     * 
     * If not specified, this is set to the implicit transform frame of the current entity path.
     * This means that if a {@link org.teamvan.moneyshift.archetypes.Transform3D} is set on an entity called {@code /my/entity/path} then this will default to {@code tf#/my/entity/path}.
     * 
     * To set the frame an entity is part of see {@link org.teamvan.moneyshift.archetypes.CoordinateFrame}.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorChildFrame() {
        return DESCRIPTOR_CHILD_FRAME;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_PARENT_FRAME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Pinhole:parent_frame", "rerun.components.TransformFrameId");

    /**
     * The parent frame this transform transforms into.
     * 
     * If not specified, this is set to the implicit transform frame of the current entity path's parent.
     * This means that if a {@link org.teamvan.moneyshift.archetypes.Transform3D} is set on an entity called {@code /my/entity/path} then this will default to {@code tf#/my/entity}.
     * 
     * To set the frame an entity is part of see {@link org.teamvan.moneyshift.archetypes.CoordinateFrame}.
     * 
     * Any update to this field will reset all other transform properties that aren't changed in the same log call or {@code send_columns} row.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorParentFrame() {
        return DESCRIPTOR_PARENT_FRAME;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_IMAGE_PLANE_DISTANCE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Pinhole:image_plane_distance", "rerun.components.ImagePlaneDistance");

    /**
     * The distance from the camera origin to the image plane when the projection is shown in a 3D viewer.
     * 
     * This is only used for visualization purposes, and does not affect the projection itself.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorImagePlaneDistance() {
        return DESCRIPTOR_IMAGE_PLANE_DISTANCE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLOR = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Pinhole:color", "rerun.components.Color");

    /**
     * Color of the camera wireframe.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColor() {
        return DESCRIPTOR_COLOR;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LINE_WIDTH = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Pinhole:line_width", "rerun.components.Radius");

    /**
     * Width of the camera wireframe lines.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLineWidth() {
        return DESCRIPTOR_LINE_WIDTH;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorImageFromCamera());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorResolution());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorCameraXyz(), descriptorChildFrame(), descriptorParentFrame(), descriptorImagePlaneDistance(), descriptorColor(), descriptorLineWidth());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.PinholeProjection.batch(descriptorImageFromCamera(), allocator, imageFromCamera));
        if (resolution != null) {
            batches.add(org.teamvan.moneyshift.components.Resolution.batch(descriptorResolution(), allocator, resolution));
        }
        if (cameraXyz != null) {
            batches.add(org.teamvan.moneyshift.components.ViewCoordinates.batch(descriptorCameraXyz(), allocator, cameraXyz));
        }
        if (childFrame != null) {
            batches.add(org.teamvan.moneyshift.components.TransformFrameId.batch(descriptorChildFrame(), allocator, childFrame));
        }
        if (parentFrame != null) {
            batches.add(org.teamvan.moneyshift.components.TransformFrameId.batch(descriptorParentFrame(), allocator, parentFrame));
        }
        if (imagePlaneDistance != null) {
            batches.add(org.teamvan.moneyshift.components.ImagePlaneDistance.batch(descriptorImagePlaneDistance(), allocator, imagePlaneDistance));
        }
        if (color != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColor(), allocator, color));
        }
        if (lineWidth != null) {
            batches.add(org.teamvan.moneyshift.components.Radius.batch(descriptorLineWidth(), allocator, lineWidth));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.PinholeProjection imageFromCamera;
        private org.teamvan.moneyshift.components.Resolution resolution;
        private org.teamvan.moneyshift.components.ViewCoordinates cameraXyz;
        private org.teamvan.moneyshift.components.TransformFrameId childFrame;
        private org.teamvan.moneyshift.components.TransformFrameId parentFrame;
        private org.teamvan.moneyshift.components.ImagePlaneDistance imagePlaneDistance;
        private org.teamvan.moneyshift.components.Color color;
        private org.teamvan.moneyshift.components.Radius lineWidth;
        private Builder() {}

        public Builder withImageFromCamera(org.teamvan.moneyshift.components.PinholeProjection value) {
            this.imageFromCamera = value;
            return this;
        }

        public Builder withResolution(org.teamvan.moneyshift.components.Resolution value) {
            this.resolution = value;
            return this;
        }

        public Builder withCameraXyz(org.teamvan.moneyshift.components.ViewCoordinates value) {
            this.cameraXyz = value;
            return this;
        }

        public Builder withChildFrame(org.teamvan.moneyshift.components.TransformFrameId value) {
            this.childFrame = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withChildFrame(java.lang.String value) {
            return withChildFrame(new org.teamvan.moneyshift.components.TransformFrameId(value));
        }

        public Builder withParentFrame(org.teamvan.moneyshift.components.TransformFrameId value) {
            this.parentFrame = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withParentFrame(java.lang.String value) {
            return withParentFrame(new org.teamvan.moneyshift.components.TransformFrameId(value));
        }

        public Builder withImagePlaneDistance(org.teamvan.moneyshift.components.ImagePlaneDistance value) {
            this.imagePlaneDistance = value;
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withImagePlaneDistance(float value) {
            return withImagePlaneDistance(new org.teamvan.moneyshift.components.ImagePlaneDistance(value));
        }

        public Builder withColor(org.teamvan.moneyshift.components.Color value) {
            this.color = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withColor(long value) {
            return withColor(new org.teamvan.moneyshift.components.Color(value));
        }

        public Builder withLineWidth(org.teamvan.moneyshift.components.Radius value) {
            this.lineWidth = value;
            return this;
        }

        /** Overload that takes the {@code float} value(s) directly. */
        public Builder withLineWidth(float value) {
            return withLineWidth(new org.teamvan.moneyshift.components.Radius(value));
        }

        public Pinhole build() {
            return new Pinhole(imageFromCamera, resolution, cameraXyz, childFrame, parentFrame, imagePlaneDistance, color, lineWidth);
        }
    }

    public static Pinhole of(
        org.teamvan.moneyshift.components.PinholeProjection imageFromCamera
    ) {
        return new Pinhole(imageFromCamera, null, null, null, null, null, null, null);
    }
}
