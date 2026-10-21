// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

public record FixedSizeBytes(
    short[] fixedSizedNative
) {
    public FixedSizeBytes {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof FixedSizeBytes that)) return false;
        return java.util.Arrays.equals(fixedSizedNative, that.fixedSizedNative);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(fixedSizedNative));
    }
    public static final java.lang.String NAME = "rerun.testing.encodings.FixedSizeBytes";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("fixed_sized_native", false, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(4), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.UINT8, java.util.List.of())))));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<FixedSizeBytes> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            FixedSizeBytes value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, FixedSizeBytes value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        short[] f0 = value.fixedSizedNative();
        {
            org.apache.arrow.vector.complex.FixedSizeListVector list1 = (org.apache.arrow.vector.complex.FixedSizeListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0);
            int start1 = list1.startNewValue(index);
            org.apache.arrow.vector.FieldVector child1 = list1.getDataVector();
            ((org.apache.arrow.vector.UInt1Vector) child1).setSafe(start1 + 0, (int) f0[0]);
            ((org.apache.arrow.vector.UInt1Vector) child1).setSafe(start1 + 1, (int) f0[1]);
            ((org.apache.arrow.vector.UInt1Vector) child1).setSafe(start1 + 2, (int) f0[2]);
            ((org.apache.arrow.vector.UInt1Vector) child1).setSafe(start1 + 3, (int) f0[3]);
        }
    }

    public static FixedSizeBytes of(short... values) {
        if (values.length != 4) throw new java.lang.IllegalArgumentException("FixedSizeBytes needs exactly 4 values, got " + values.length);
        return new FixedSizeBytes(values);
    }
}
