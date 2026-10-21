// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

public sealed interface ScalarUnion permits
    ScalarUnion.Degrees,
    ScalarUnion.Craziness,
    ScalarUnion.FixedSizeShenanigans,
    ScalarUnion.EmptyVariant {
    record Degrees(float value) implements ScalarUnion {
        public Degrees { java.util.Objects.requireNonNull(value, "value"); }
    }
    record Craziness(java.util.List<org.teamvan.moneyshift.testing.encodings.MixedFields> value) implements ScalarUnion {
        public Craziness { java.util.Objects.requireNonNull(value, "value"); }
    }
    record FixedSizeShenanigans(float[] value) implements ScalarUnion {
        public FixedSizeShenanigans { java.util.Objects.requireNonNull(value, "value"); }
    }
    record EmptyVariant() implements ScalarUnion {}

    java.lang.String NAME = "rerun.testing.encodings.ScalarUnion";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Union(org.apache.arrow.vector.types.UnionMode.Dense, new int[]{0, 1, 2, 3, 4});
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Union(org.apache.arrow.vector.types.UnionMode.Dense, new int[]{0, 1, 2, 3, 4}), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("_null_markers", true, org.teamvan.moneyshift.ArrowFields.NULL, java.util.List.of()), org.teamvan.moneyshift.ArrowFields.field("degrees", false, org.teamvan.moneyshift.ArrowFields.FLOAT32, java.util.List.of()), org.teamvan.moneyshift.ArrowFields.field("craziness", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.testing.encodings.MixedFields.arrowField("item", false))), org.teamvan.moneyshift.ArrowFields.field("fixed_size_shenanigans", false, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(3), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.FLOAT32, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("empty_variant", true, org.teamvan.moneyshift.ArrowFields.NULL, java.util.List.of())));
    }

    public static int typeId(ScalarUnion value) {
        if (value == null) return 0;
        return switch (value) {
            case ScalarUnion.Degrees ignored -> 1;
            case ScalarUnion.Craziness ignored -> 2;
            case ScalarUnion.FixedSizeShenanigans ignored -> 3;
            case ScalarUnion.EmptyVariant ignored -> 4;
        };
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ScalarUnion> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            writeOne(vector, i, values.get(i));
        }
        ((org.apache.arrow.vector.complex.DenseUnionVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ScalarUnion value) {
        org.apache.arrow.vector.complex.DenseUnionVector union = (org.apache.arrow.vector.complex.DenseUnionVector) vector;
        if (value == null) {
            org.apache.arrow.vector.FieldVector nullMarkers = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 0);
            int nullOffset = nullMarkers.getValueCount();
            union.setTypeId(index, (byte) 0);
            union.setOffset(index, nullOffset);
            nullMarkers.setValueCount(nullOffset + 1);
            return;
        }
        switch (value) {
            case ScalarUnion.Degrees variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 1);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 1);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                ((org.apache.arrow.vector.Float4Vector) child).setSafe(offset, variant.value());
            }
            case ScalarUnion.Craziness variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 2);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 2);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    java.util.List<org.teamvan.moneyshift.testing.encodings.MixedFields> values0 = variant.value();
                    for (int j0 = 0; j0 < values0.size(); j0++) {
                        org.teamvan.moneyshift.testing.encodings.MixedFields.writeOne(child0, start0 + j0, values0.get(j0));
                    }
                    list0.endValue(offset, values0.size());
                }
            }
            case ScalarUnion.FixedSizeShenanigans variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 3);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 3);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.FixedSizeListVector list0 = (org.apache.arrow.vector.complex.FixedSizeListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 0, variant.value()[0]);
                    ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 1, variant.value()[1]);
                    ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 2, variant.value()[2]);
                }
            }
            case ScalarUnion.EmptyVariant ignored -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 4);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 4);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
            }
        }
    }
}
