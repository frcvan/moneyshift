// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A 2D texture UV coordinate.
 * 
 * Texture coordinates specify a position on a 2D texture.
 * A range from 0-1 covers the entire texture in the respective dimension.
 * Unless configured otherwise, the texture repeats outside of this range.
 * Rerun uses top-left as the origin for UV coordinates.
 * 
 *   0     U     1
 * 0 + --------- →
 *   |           .
 *   |           .
 * V |           .
 *   |           .
 * 1 ↓ . . . . . .
 * 
 * This is the same convention as in Vulkan/Metal/DX12/WebGPU, but (!) unlike OpenGL,
 * which places the origin at the bottom-left.
 */
public record Texcoord2D(
    org.teamvan.moneyshift.encodings.Vec2D uv
) implements org.teamvan.moneyshift.Component {
    public Texcoord2D {
        java.util.Objects.requireNonNull(uv, "uv");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Texcoord2D(float[] xy) {
        this(new org.teamvan.moneyshift.encodings.Vec2D(xy));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Texcoord2D";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Vec2D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Vec2D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Texcoord2D> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Texcoord2D::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Texcoord2D value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Texcoord2D value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Texcoord2D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Texcoord2D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Texcoord2D value) {
        org.teamvan.moneyshift.encodings.Vec2D.writeOne(vector, index, value.uv());
    }

    public static Texcoord2D of(float... values) {
        if (values.length != 2) throw new java.lang.IllegalArgumentException("Texcoord2D needs exactly 2 values, got " + values.length);
        return new Texcoord2D(new org.teamvan.moneyshift.encodings.Vec2D(values));
    }
}
