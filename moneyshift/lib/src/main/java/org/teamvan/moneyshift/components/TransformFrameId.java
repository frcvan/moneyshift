// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A string identifier for a transform frame.
 * 
 * Transform frames may be derived from entity paths to refer to Rerun's implicit
 * entity path driven hierarchy which is defined via {@link org.teamvan.moneyshift.archetypes.Transform3D}, {@link org.teamvan.moneyshift.archetypes.Pinhole} etc..
 * These implicit transform frames look like {@code tf#path/to/entity}.
 * 
 * Note that any {@link org.teamvan.moneyshift.archetypes.Transform3D}s logged with both {@code parent_frame} and {@code child_frame} set
 * describes a relationship between these parent and child transform frames, **not** the transform frame
 * that the entity path may be using (defined by an {@link org.teamvan.moneyshift.archetypes.CoordinateFrame}).
 */
public record TransformFrameId(
    org.teamvan.moneyshift.encodings.Utf8 value
) implements org.teamvan.moneyshift.Component {
    public TransformFrameId {
        java.util.Objects.requireNonNull(value, "value");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public TransformFrameId(java.lang.String value) {
        this(new org.teamvan.moneyshift.encodings.Utf8(value));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.TransformFrameId";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Utf8.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Utf8.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<TransformFrameId> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, TransformFrameId::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, TransformFrameId value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, TransformFrameId value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TransformFrameId> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TransformFrameId value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.VarCharVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.VarCharVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TransformFrameId value) {
        org.teamvan.moneyshift.encodings.Utf8.writeOne(vector, index, value.value());
    }
}
