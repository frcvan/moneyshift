// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * How a geometric shape is drawn and colored.
 */
public enum FillMode implements org.teamvan.moneyshift.Component {
    /**
     * Lines are drawn around the parts of the shape which directly correspond to the logged data.
     * 
     * Examples of what this means:
     * 
     * * An {@link org.teamvan.moneyshift.archetypes.Ellipsoids3D} will draw three axis-aligned ellipses that are cross-sections
     *   of each ellipsoid, each of which displays two out of three of the sizes of the ellipsoid.
     * * For {@link org.teamvan.moneyshift.archetypes.Boxes3D}, it is the edges of the box, identical to {@link org.teamvan.moneyshift.components.FillMode#DenseWireframe}.
     */
    MajorWireframe(1L),
    /**
     * Many lines are drawn to represent the surface of the shape in a see-through fashion.
     * 
     * Examples of what this means:
     * 
     * * An {@link org.teamvan.moneyshift.archetypes.Ellipsoids3D} will draw a wireframe triangle mesh that approximates each
     *   ellipsoid.
     * * For {@link org.teamvan.moneyshift.archetypes.Boxes3D}, it is the edges of the box, identical to {@link org.teamvan.moneyshift.components.FillMode#MajorWireframe}.
     */
    DenseWireframe(2L),
    /**
     * The surface of the shape is filled in with a solid color. No lines are drawn.
     */
    Solid(3L),
    /**
     * The surface of the shape is filled in with a transparent color, with major wireframe lines on top.
     * 
     * This gives a good default appearance that shows both the shape's surface and its structure.
     */
    TransparentFillMajorWireframe(4L);

    private final long value;

    FillMode(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static FillMode fromValue(long value) {
        for (FillMode candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown FillMode value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.FillMode";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.FillMode";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<FillMode> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, FillMode::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, FillMode value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, FillMode value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<FillMode> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            FillMode value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, FillMode value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
