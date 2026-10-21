// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A 3x3 Matrix.
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
public record Mat3x3(
    /**
     * Flat list of matrix coefficients in column-major order.
     */
    float[] flatColumns
) {
    public Mat3x3 {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof Mat3x3 that)) return false;
        return java.util.Arrays.equals(flatColumns, that.flatColumns);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(flatColumns));
    }
    public static final java.lang.String NAME = "rerun.encodings.Mat3x3";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(9);
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(9), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.FLOAT32, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Mat3x3> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Mat3x3 value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Mat3x3 value) {
        {
            org.apache.arrow.vector.complex.FixedSizeListVector list0 = (org.apache.arrow.vector.complex.FixedSizeListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 0, value.flatColumns()[0]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 1, value.flatColumns()[1]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 2, value.flatColumns()[2]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 3, value.flatColumns()[3]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 4, value.flatColumns()[4]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 5, value.flatColumns()[5]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 6, value.flatColumns()[6]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 7, value.flatColumns()[7]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 8, value.flatColumns()[8]);
        }
    }

    public static Mat3x3 of(float... values) {
        if (values.length != 9) throw new java.lang.IllegalArgumentException("Mat3x3 needs exactly 9 values, got " + values.length);
        return new Mat3x3(values);
    }
}
