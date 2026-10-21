// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.encodings;

/**
 * Defines a slider for the index of some dimension.
 */
public record TensorDimensionIndexSlider(
    /**
     * The dimension number.
     */
    long dimension
) {
    public TensorDimensionIndexSlider {
    }
    public static final java.lang.String NAME = "rerun.blueprint.encodings.TensorDimensionIndexSlider";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("dimension", false, org.teamvan.moneyshift.ArrowFields.UINT32, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TensorDimensionIndexSlider> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TensorDimensionIndexSlider value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TensorDimensionIndexSlider value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        long f0 = value.dimension();
        ((org.apache.arrow.vector.UInt4Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0)).setSafe(index, (int) (long) f0);
    }
}
