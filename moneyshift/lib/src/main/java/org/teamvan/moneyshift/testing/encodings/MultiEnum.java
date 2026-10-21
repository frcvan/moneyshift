// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

public record MultiEnum(
    /**
     * The first value.
     */
    org.teamvan.moneyshift.testing.encodings.EnumTest value1,
    /**
     * The second value.
     */
    org.teamvan.moneyshift.testing.encodings.ValuedEnum value2
) {
    public MultiEnum {
        java.util.Objects.requireNonNull(value1, "value1");
    }
    public static final java.lang.String NAME = "rerun.testing.encodings.MultiEnum";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.testing.encodings.EnumTest.arrowField("value1", false), org.teamvan.moneyshift.testing.encodings.ValuedEnum.arrowField("value2", true)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<MultiEnum> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            MultiEnum value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, MultiEnum value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.testing.encodings.EnumTest f0 = value.value1();
        org.teamvan.moneyshift.testing.encodings.EnumTest.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.testing.encodings.ValuedEnum f1 = value.value2();
        if (f1 == null) {
            ((org.apache.arrow.vector.UInt1Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1)).setNull(index);
        } else {
            org.teamvan.moneyshift.testing.encodings.ValuedEnum.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
        }
    }
}
