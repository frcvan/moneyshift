// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A double-precision vector in 2D space.
 */
public record DVec2D(
    double[] xy
) {
    public DVec2D {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof DVec2D that)) return false;
        return java.util.Arrays.equals(xy, that.xy);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(xy));
    }
    public static final java.lang.String NAME = "rerun.encodings.DVec2D";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(2);
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(2), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.FLOAT64, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<DVec2D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            DVec2D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, DVec2D value) {
        {
            org.apache.arrow.vector.complex.FixedSizeListVector list0 = (org.apache.arrow.vector.complex.FixedSizeListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            ((org.apache.arrow.vector.Float8Vector) child0).setSafe(start0 + 0, value.xy()[0]);
            ((org.apache.arrow.vector.Float8Vector) child0).setSafe(start0 + 1, value.xy()[1]);
        }
    }

    public static DVec2D of(double... values) {
        if (values.length != 2) throw new java.lang.IllegalArgumentException("DVec2D needs exactly 2 values, got " + values.length);
        return new DVec2D(values);
    }
}
