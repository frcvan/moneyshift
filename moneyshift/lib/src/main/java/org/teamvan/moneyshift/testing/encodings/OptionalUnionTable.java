// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

public record OptionalUnionTable(
    org.teamvan.moneyshift.testing.encodings.NestedUnion singleOptionalUnion
) {
    public OptionalUnionTable {
    }
    public static final java.lang.String NAME = "rerun.testing.encodings.OptionalUnionTable";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.testing.encodings.NestedUnion.arrowField("single_optional_union", true)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<OptionalUnionTable> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            OptionalUnionTable value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, OptionalUnionTable value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.testing.encodings.NestedUnion f0 = value.singleOptionalUnion();
        org.teamvan.moneyshift.testing.encodings.NestedUnion.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
    }
}
