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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("frame").append('=').append(org.teamvan.moneyshift.components.TransformFrameId.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (true) {
            org.teamvan.moneyshift.components.TransformFrameId.writeColumnsStatic(w, "frame", java.util.List.of(frame));
        }
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
