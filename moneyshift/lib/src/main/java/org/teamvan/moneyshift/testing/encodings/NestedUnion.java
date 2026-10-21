// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

public sealed interface NestedUnion permits
    NestedUnion.SingleRequired,
    NestedUnion.ManyRequired {
    record SingleRequired(org.teamvan.moneyshift.testing.encodings.ScalarUnion value) implements NestedUnion {
        public SingleRequired { java.util.Objects.requireNonNull(value, "value"); }
    }
    record ManyRequired(java.util.List<org.teamvan.moneyshift.testing.encodings.ScalarUnion> value) implements NestedUnion {
        public ManyRequired { java.util.Objects.requireNonNull(value, "value"); }
    }

    java.lang.String NAME = "rerun.testing.encodings.NestedUnion";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Union(org.apache.arrow.vector.types.UnionMode.Dense, new int[]{0, 1, 2});
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Union(org.apache.arrow.vector.types.UnionMode.Dense, new int[]{0, 1, 2}), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("_null_markers", true, org.teamvan.moneyshift.ArrowFields.NULL, java.util.List.of()), org.teamvan.moneyshift.testing.encodings.ScalarUnion.arrowField("single_required", false), org.teamvan.moneyshift.ArrowFields.field("many_required", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.testing.encodings.ScalarUnion.arrowField("item", false)))));
    }

    public static int typeId(NestedUnion value) {
        if (value == null) return 0;
        return switch (value) {
            case NestedUnion.SingleRequired ignored -> 1;
            case NestedUnion.ManyRequired ignored -> 2;
        };
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<NestedUnion> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            writeOne(vector, i, values.get(i));
        }
        ((org.apache.arrow.vector.complex.DenseUnionVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, NestedUnion value) {
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
            case NestedUnion.SingleRequired variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 1);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 1);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                org.teamvan.moneyshift.testing.encodings.ScalarUnion.writeOne(child, offset, variant.value());
            }
            case NestedUnion.ManyRequired variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 2);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 2);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    java.util.List<org.teamvan.moneyshift.testing.encodings.ScalarUnion> values0 = variant.value();
                    for (int j0 = 0; j0 < values0.size(); j0++) {
                        org.teamvan.moneyshift.testing.encodings.ScalarUnion.writeOne(child0, start0 + j0, values0.get(j0));
                    }
                    list0.endValue(offset, values0.size());
                }
            }
        }
    }
}
