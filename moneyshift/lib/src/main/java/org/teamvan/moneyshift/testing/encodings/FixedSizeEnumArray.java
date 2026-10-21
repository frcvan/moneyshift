// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

/**
 * Test encoding for fixed-size enum arrays.
 */
public record FixedSizeEnumArray(
    /**
     * Fixed-size enum array.
     */
    org.teamvan.moneyshift.testing.encodings.EnumTest[] values
) {
    public FixedSizeEnumArray {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof FixedSizeEnumArray that)) return false;
        return java.util.Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(values));
    }
    public static final java.lang.String NAME = "rerun.testing.encodings.FixedSizeEnumArray";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(3);
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(3), java.util.List.of(org.teamvan.moneyshift.testing.encodings.EnumTest.arrowField("item", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<FixedSizeEnumArray> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            FixedSizeEnumArray value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, FixedSizeEnumArray value) {
        {
            org.apache.arrow.vector.complex.FixedSizeListVector list0 = (org.apache.arrow.vector.complex.FixedSizeListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            org.teamvan.moneyshift.testing.encodings.EnumTest.writeOne(child0, start0 + 0, value.values()[0]);
            org.teamvan.moneyshift.testing.encodings.EnumTest.writeOne(child0, start0 + 1, value.values()[1]);
            org.teamvan.moneyshift.testing.encodings.EnumTest.writeOne(child0, start0 + 2, value.values()[2]);
        }
    }
}
