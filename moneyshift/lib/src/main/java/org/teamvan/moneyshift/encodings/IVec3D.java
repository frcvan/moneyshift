// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * An int32 vector in 3D space.
 */
public record IVec3D(
    int[] xyz
) {
    public IVec3D {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof IVec3D that)) return false;
        return java.util.Arrays.equals(xyz, that.xyz);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(xyz));
    }
    public static final java.lang.String NAME = "rerun.encodings.IVec3D";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(3);
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(3), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.INT32, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<IVec3D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            IVec3D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, IVec3D value) {
        {
            org.apache.arrow.vector.complex.FixedSizeListVector list0 = (org.apache.arrow.vector.complex.FixedSizeListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            ((org.apache.arrow.vector.IntVector) child0).setSafe(start0 + 0, value.xyz()[0]);
            ((org.apache.arrow.vector.IntVector) child0).setSafe(start0 + 1, value.xyz()[1]);
            ((org.apache.arrow.vector.IntVector) child0).setSafe(start0 + 2, value.xyz()[2]);
        }
    }

    public static IVec3D of(int... values) {
        if (values.length != 3) throw new java.lang.IllegalArgumentException("IVec3D needs exactly 3 values, got " + values.length);
        return new IVec3D(values);
    }
}
