// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

public record PrimitiveAndString(
    org.teamvan.moneyshift.testing.encodings.PrimitiveComponent p,
    org.teamvan.moneyshift.testing.encodings.StringComponent s
) {
    public PrimitiveAndString {
        java.util.Objects.requireNonNull(p, "p");
        java.util.Objects.requireNonNull(s, "s");
    }
    public static final java.lang.String NAME = "rerun.testing.encodings.PrimitiveAndString";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.testing.encodings.PrimitiveComponent.arrowField("p", false), org.teamvan.moneyshift.testing.encodings.StringComponent.arrowField("s", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<PrimitiveAndString> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            PrimitiveAndString value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, PrimitiveAndString value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.testing.encodings.PrimitiveComponent f0 = value.p();
        org.teamvan.moneyshift.testing.encodings.PrimitiveComponent.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.testing.encodings.StringComponent f1 = value.s();
        org.teamvan.moneyshift.testing.encodings.StringComponent.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
