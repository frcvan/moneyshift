// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Indexing a specific tensor dimension.
 * 
 * Selecting {@code dimension=2} and {@code index=42} is similar to doing {@code tensor[:, :, 42, :, :, …]} in numpy.
 */
public record TensorDimensionIndexSelection(
    /**
     * The dimension number to select.
     */
    long dimension,
    /**
     * The index along the dimension to use.
     */
    long index
) {
    public TensorDimensionIndexSelection {
    }
    public static final java.lang.String NAME = "rerun.encodings.TensorDimensionIndexSelection";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("dimension", false, org.teamvan.moneyshift.ArrowFields.UINT32, java.util.List.of()), org.teamvan.moneyshift.ArrowFields.field("index", false, org.teamvan.moneyshift.ArrowFields.UINT64, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TensorDimensionIndexSelection> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TensorDimensionIndexSelection value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TensorDimensionIndexSelection value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        long f0 = value.dimension();
        ((org.apache.arrow.vector.UInt4Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0)).setSafe(index, (int) (long) f0);
        long f1 = value.index();
        ((org.apache.arrow.vector.UInt8Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1)).setSafe(index, f1);
    }
}
