// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

public record Float16Fields(
    float singleHalf,
    java.util.List<java.lang.Float> manyHalves
) {
    public Float16Fields {
        manyHalves = java.util.List.copyOf(manyHalves);
    }
    public static final java.lang.String NAME = "rerun.testing.encodings.Float16Fields";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("single_half", false, org.teamvan.moneyshift.ArrowFields.FLOAT16, java.util.List.of()), org.teamvan.moneyshift.ArrowFields.field("many_halves", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.FLOAT16, java.util.List.of())))));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Float16Fields> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Float16Fields value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Float16Fields value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        float f0 = value.singleHalf();
        ((org.apache.arrow.vector.Float2Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0)).setSafe(index, java.lang.Float.floatToFloat16(f0));
        java.util.List<java.lang.Float> f1 = value.manyHalves();
        {
            org.apache.arrow.vector.complex.ListVector list2 = (org.apache.arrow.vector.complex.ListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1);
            int start2 = list2.startNewValue(index);
            org.apache.arrow.vector.FieldVector child2 = list2.getDataVector();
            java.util.List<java.lang.Float> values2 = f1;
            for (int j2 = 0; j2 < values2.size(); j2++) {
                ((org.apache.arrow.vector.Float2Vector) child2).setSafe(start2 + j2, java.lang.Float.floatToFloat16(values2.get(j2)));
            }
            list2.endValue(index, values2.size());
        }
    }
}
