// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A vector in 2D space.
 */
public record Vector2D(
    org.teamvan.moneyshift.encodings.Vec2D vector
) implements org.teamvan.moneyshift.Component {
    public Vector2D {
        java.util.Objects.requireNonNull(vector, "vector");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Vector2D(float[] xy) {
        this(new org.teamvan.moneyshift.encodings.Vec2D(xy));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Vector2D";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Vec2D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Vec2D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Vector2D> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Vector2D::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Vector2D value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Vector2D value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Vector2D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Vector2D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Vector2D value) {
        org.teamvan.moneyshift.encodings.Vec2D.writeOne(vector, index, value.vector());
    }

    public static Vector2D of(float... values) {
        if (values.length != 2) throw new java.lang.IllegalArgumentException("Vector2D needs exactly 2 values, got " + values.length);
        return new Vector2D(new org.teamvan.moneyshift.encodings.Vec2D(values));
    }
}
