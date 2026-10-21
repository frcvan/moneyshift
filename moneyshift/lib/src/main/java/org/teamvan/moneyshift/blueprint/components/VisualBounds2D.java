// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * Visual bounds in 2D space used for {@code Spatial2DView}.
 */
public record VisualBounds2D(
    /**
     * X and y ranges that should be visible.
     */
    org.teamvan.moneyshift.encodings.Range2D range2d
) implements org.teamvan.moneyshift.Component {
    public VisualBounds2D {
        java.util.Objects.requireNonNull(range2d, "range2d");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public VisualBounds2D(org.teamvan.moneyshift.encodings.Range1D xRange, org.teamvan.moneyshift.encodings.Range1D yRange) {
        this(new org.teamvan.moneyshift.encodings.Range2D(xRange, yRange));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.VisualBounds2D";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Range2D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Range2D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<VisualBounds2D> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, VisualBounds2D::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, VisualBounds2D value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, VisualBounds2D value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<VisualBounds2D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            VisualBounds2D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, VisualBounds2D value) {
        org.teamvan.moneyshift.encodings.Range2D.writeOne(vector, index, value.range2d());
    }
}
