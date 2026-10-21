// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

public record MixedFields(
    java.lang.Float singleFloatOptional,
    java.lang.String singleStringRequired,
    java.lang.String singleStringOptional,
    java.util.List<java.lang.Float> manyFloatsOptional,
    java.util.List<java.lang.String> manyStringsRequired,
    java.util.List<java.lang.String> manyStringsOptional,
    float flattenedScalar,
    org.teamvan.moneyshift.testing.encodings.FlattenedScalar almostFlattenedScalar,
    java.lang.Boolean fromParent
) {
    public MixedFields {
        java.util.Objects.requireNonNull(singleStringRequired, "singleStringRequired");
        if (manyFloatsOptional != null) { manyFloatsOptional = java.util.List.copyOf(manyFloatsOptional); }
        manyStringsRequired = java.util.List.copyOf(manyStringsRequired);
        if (manyStringsOptional != null) { manyStringsOptional = java.util.List.copyOf(manyStringsOptional); }
        java.util.Objects.requireNonNull(almostFlattenedScalar, "almostFlattenedScalar");
    }
    public static final java.lang.String NAME = "rerun.testing.encodings.MixedFields";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("single_float_optional", true, org.teamvan.moneyshift.ArrowFields.FLOAT32, java.util.List.of()), org.teamvan.moneyshift.ArrowFields.field("single_string_required", false, org.teamvan.moneyshift.ArrowFields.UTF8, java.util.List.of()), org.teamvan.moneyshift.ArrowFields.field("single_string_optional", true, org.teamvan.moneyshift.ArrowFields.UTF8, java.util.List.of()), org.teamvan.moneyshift.ArrowFields.field("many_floats_optional", true, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.FLOAT32, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("many_strings_required", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.UTF8, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("many_strings_optional", true, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.UTF8, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("flattened_scalar", false, org.teamvan.moneyshift.ArrowFields.FLOAT32, java.util.List.of()), org.teamvan.moneyshift.testing.encodings.FlattenedScalar.arrowField("almost_flattened_scalar", false), org.teamvan.moneyshift.ArrowFields.field("from_parent", true, org.teamvan.moneyshift.ArrowFields.BOOL, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<MixedFields> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            MixedFields value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, MixedFields value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        java.lang.Float f0 = value.singleFloatOptional();
        if (f0 == null) {
            ((org.apache.arrow.vector.Float4Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0)).setNull(index);
        } else {
            ((org.apache.arrow.vector.Float4Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0)).setSafe(index, f0);
        }
        java.lang.String f1 = value.singleStringRequired();
        ((org.apache.arrow.vector.VarCharVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1)).setSafe(index, f1.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        java.lang.String f2 = value.singleStringOptional();
        if (f2 == null) {
            ((org.apache.arrow.vector.VarCharVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(2)).setNull(index);
        } else {
            ((org.apache.arrow.vector.VarCharVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(2)).setSafe(index, f2.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
        java.util.List<java.lang.Float> f3 = value.manyFloatsOptional();
        if (f3 == null) {
            ((org.apache.arrow.vector.complex.ListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(3)).setNull(index);
        } else {
            {
                org.apache.arrow.vector.complex.ListVector list4 = (org.apache.arrow.vector.complex.ListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(3);
                int start4 = list4.startNewValue(index);
                org.apache.arrow.vector.FieldVector child4 = list4.getDataVector();
                java.util.List<java.lang.Float> values4 = f3;
                for (int j4 = 0; j4 < values4.size(); j4++) {
                    ((org.apache.arrow.vector.Float4Vector) child4).setSafe(start4 + j4, values4.get(j4));
                }
                list4.endValue(index, values4.size());
            }
        }
        java.util.List<java.lang.String> f5 = value.manyStringsRequired();
        {
            org.apache.arrow.vector.complex.ListVector list6 = (org.apache.arrow.vector.complex.ListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(4);
            int start6 = list6.startNewValue(index);
            org.apache.arrow.vector.FieldVector child6 = list6.getDataVector();
            java.util.List<java.lang.String> values6 = f5;
            for (int j6 = 0; j6 < values6.size(); j6++) {
                ((org.apache.arrow.vector.VarCharVector) child6).setSafe(start6 + j6, values6.get(j6).getBytes(java.nio.charset.StandardCharsets.UTF_8));
            }
            list6.endValue(index, values6.size());
        }
        java.util.List<java.lang.String> f7 = value.manyStringsOptional();
        if (f7 == null) {
            ((org.apache.arrow.vector.complex.ListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(5)).setNull(index);
        } else {
            {
                org.apache.arrow.vector.complex.ListVector list8 = (org.apache.arrow.vector.complex.ListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(5);
                int start8 = list8.startNewValue(index);
                org.apache.arrow.vector.FieldVector child8 = list8.getDataVector();
                java.util.List<java.lang.String> values8 = f7;
                for (int j8 = 0; j8 < values8.size(); j8++) {
                    ((org.apache.arrow.vector.VarCharVector) child8).setSafe(start8 + j8, values8.get(j8).getBytes(java.nio.charset.StandardCharsets.UTF_8));
                }
                list8.endValue(index, values8.size());
            }
        }
        float f9 = value.flattenedScalar();
        ((org.apache.arrow.vector.Float4Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(6)).setSafe(index, f9);
        org.teamvan.moneyshift.testing.encodings.FlattenedScalar f10 = value.almostFlattenedScalar();
        org.teamvan.moneyshift.testing.encodings.FlattenedScalar.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(7), index, f10);
        java.lang.Boolean f11 = value.fromParent();
        if (f11 == null) {
            ((org.apache.arrow.vector.BitVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(8)).setNull(index);
        } else {
            ((org.apache.arrow.vector.BitVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(8)).setSafe(index, ((f11) ? 1 : 0));
        }
    }
}
