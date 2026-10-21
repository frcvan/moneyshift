// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The three indices of a triangle in a triangle mesh.
 */
public record TriangleIndices(
    org.teamvan.moneyshift.encodings.UVec3D indices
) implements org.teamvan.moneyshift.Component {
    public TriangleIndices {
        java.util.Objects.requireNonNull(indices, "indices");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public TriangleIndices(long[] xyz) {
        this(new org.teamvan.moneyshift.encodings.UVec3D(xyz));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.TriangleIndices";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.UVec3D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.UVec3D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<TriangleIndices> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, TriangleIndices::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, TriangleIndices value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, TriangleIndices value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TriangleIndices> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TriangleIndices value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TriangleIndices value) {
        org.teamvan.moneyshift.encodings.UVec3D.writeOne(vector, index, value.indices());
    }

    public static TriangleIndices of(long... values) {
        if (values.length != 3) throw new java.lang.IllegalArgumentException("TriangleIndices needs exactly 3 values, got " + values.length);
        return new TriangleIndices(new org.teamvan.moneyshift.encodings.UVec3D(values));
    }
}
