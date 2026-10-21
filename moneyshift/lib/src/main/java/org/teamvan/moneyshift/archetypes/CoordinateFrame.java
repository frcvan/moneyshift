// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Specifies the coordinate frame for an entity.
 * 
 * If not specified, the coordinate frame uses an implicit frame derived from the entity path.
 * The implicit frame's name is {@code tf#/your/entity/path} and has an identity transform connection to its parent path.
 * 
 * To learn more about transforms see <a href="https://rerun.io/docs/concepts/spaces-and-transforms">Spaces &amp; Transforms</a> in the reference.
 * 
 */
public record CoordinateFrame(
    /**
     * The coordinate frame to use for the current entity.
     * 
     * Note that empty strings are not valid transform frame IDs.
     */
    org.teamvan.moneyshift.components.TransformFrameId frame
) implements org.teamvan.moneyshift.Archetype {
    public CoordinateFrame {
        java.util.Objects.requireNonNull(frame, "frame");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.CoordinateFrame";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FRAME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "CoordinateFrame:frame", "rerun.components.TransformFrameId");

    /**
     * The coordinate frame to use for the current entity.
     * 
     * Note that empty strings are not valid transform frame IDs.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFrame() {
        return DESCRIPTOR_FRAME;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorFrame());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of();
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.TransformFrameId.batch(descriptorFrame(), allocator, frame));
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.TransformFrameId frame;
        private Builder() {}

        public Builder withFrame(org.teamvan.moneyshift.components.TransformFrameId value) {
            this.frame = value;
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withFrame(java.lang.String value) {
            return withFrame(new org.teamvan.moneyshift.components.TransformFrameId(value));
        }

        public CoordinateFrame build() {
            return new CoordinateFrame(frame);
        }
    }

    public static CoordinateFrame of(
        org.teamvan.moneyshift.components.TransformFrameId frame
    ) {
        return new CoordinateFrame(frame);
    }
}
