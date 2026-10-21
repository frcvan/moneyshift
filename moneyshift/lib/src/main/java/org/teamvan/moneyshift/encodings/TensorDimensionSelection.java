// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Selection of a single tensor dimension.
 */
public record TensorDimensionSelection(
    /**
     * The dimension number to select.
     */
    long dimension,
    /**
     * Invert the direction of the dimension.
     */
    boolean invert
) {
    public TensorDimensionSelection {
    }
    public static final java.lang.String NAME = "rerun.encodings.TensorDimensionSelection";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("dimension", false, org.teamvan.moneyshift.ArrowFields.UINT32, java.util.List.of()), org.teamvan.moneyshift.ArrowFields.field("invert", false, org.teamvan.moneyshift.ArrowFields.BOOL, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TensorDimensionSelection> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TensorDimensionSelection value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TensorDimensionSelection value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        long f0 = value.dimension();
        ((org.apache.arrow.vector.UInt4Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0)).setSafe(index, (int) (long) f0);
        boolean f1 = value.invert();
        ((org.apache.arrow.vector.BitVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1)).setSafe(index, ((f1) ? 1 : 0));
    }
}
