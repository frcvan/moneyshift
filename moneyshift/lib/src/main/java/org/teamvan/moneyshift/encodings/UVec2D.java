// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A uint32 vector in 2D space.
 */
public record UVec2D(
    long[] xy
) {
    public UVec2D {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof UVec2D that)) return false;
        return java.util.Arrays.equals(xy, that.xy);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(xy));
    }
    public static final java.lang.String NAME = "rerun.encodings.UVec2D";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(2);
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(2), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.UINT32, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<UVec2D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            UVec2D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, UVec2D value) {
        {
            org.apache.arrow.vector.complex.FixedSizeListVector list0 = (org.apache.arrow.vector.complex.FixedSizeListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            ((org.apache.arrow.vector.UInt4Vector) child0).setSafe(start0 + 0, (int) (long) value.xy()[0]);
            ((org.apache.arrow.vector.UInt4Vector) child0).setSafe(start0 + 1, (int) (long) value.xy()[1]);
        }
    }

    public static UVec2D of(long... values) {
        if (values.length != 2) throw new java.lang.IllegalArgumentException("UVec2D needs exactly 2 values, got " + values.length);
        return new UVec2D(values);
    }
}
