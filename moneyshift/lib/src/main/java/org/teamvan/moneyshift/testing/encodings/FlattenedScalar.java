// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

public record FlattenedScalar(
    float value
) {
    public FlattenedScalar {
    }
    public static final java.lang.String NAME = "rerun.testing.encodings.FlattenedScalar";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("value", false, org.teamvan.moneyshift.ArrowFields.FLOAT32, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<FlattenedScalar> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            FlattenedScalar value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, FlattenedScalar value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        float f0 = value.value();
        ((org.apache.arrow.vector.Float4Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0)).setSafe(index, f0);
    }
}
