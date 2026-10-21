// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A 3x3 transformation matrix Matrix.
 * 
 * 3x3 matrixes are able to represent any affine transformation in 3D space,
 * i.e. rotation, scaling, shearing, reflection etc.
 * 
 * Matrices in Rerun are stored as flat list of coefficients in column-major order:
 * {@code }`text
 *             column 0       column 1       column 2
 *        -------------------------------------------------
 * row 0 | flat_columnsrow 0 | flat_columns[0] flat_columns[3] flat_columns[6]
 * row 1 | flat_columnsrow 1 | flat_columns[1] flat_columns[4] flat_columns[7]
 * row 2 | flat_columnsrow 2 | flat_columns[2] flat_columns[5] flat_columns[8]
 * {@code }`
 * 
 */
public record TransformMat3x3(
    org.teamvan.moneyshift.encodings.Mat3x3 matrix
) implements org.teamvan.moneyshift.Component {
    public TransformMat3x3 {
        java.util.Objects.requireNonNull(matrix, "matrix");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.TransformMat3x3";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Mat3x3.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Mat3x3.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<TransformMat3x3> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, TransformMat3x3::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, TransformMat3x3 value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, TransformMat3x3 value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TransformMat3x3> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TransformMat3x3 value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TransformMat3x3 value) {
        org.teamvan.moneyshift.encodings.Mat3x3.writeOne(vector, index, value.matrix());
    }

    public static TransformMat3x3 of(float... values) {
        if (values.length != 9) throw new java.lang.IllegalArgumentException("TransformMat3x3 needs exactly 9 values, got " + values.length);
        return new TransformMat3x3(new org.teamvan.moneyshift.encodings.Mat3x3(values));
    }
}
