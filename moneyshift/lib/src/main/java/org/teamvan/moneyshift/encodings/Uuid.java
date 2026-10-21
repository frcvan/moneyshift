// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A 16-byte UUID.
 */
public record Uuid(
    /**
     * The raw bytes representing the UUID.
     */
    short[] bytes
) {
    public Uuid {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof Uuid that)) return false;
        return java.util.Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(bytes));
    }
    public static final java.lang.String NAME = "rerun.encodings.Uuid";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(16);
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(16), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.UINT8, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Uuid> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Uuid value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Uuid value) {
        {
            org.apache.arrow.vector.complex.FixedSizeListVector list0 = (org.apache.arrow.vector.complex.FixedSizeListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 0, (int) value.bytes()[0]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 1, (int) value.bytes()[1]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 2, (int) value.bytes()[2]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 3, (int) value.bytes()[3]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 4, (int) value.bytes()[4]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 5, (int) value.bytes()[5]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 6, (int) value.bytes()[6]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 7, (int) value.bytes()[7]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 8, (int) value.bytes()[8]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 9, (int) value.bytes()[9]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 10, (int) value.bytes()[10]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 11, (int) value.bytes()[11]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 12, (int) value.bytes()[12]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 13, (int) value.bytes()[13]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 14, (int) value.bytes()[14]);
            ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + 15, (int) value.bytes()[15]);
        }
    }

    public static Uuid of(short... values) {
        if (values.length != 16) throw new java.lang.IllegalArgumentException("Uuid needs exactly 16 values, got " + values.length);
        return new Uuid(values);
    }
}
