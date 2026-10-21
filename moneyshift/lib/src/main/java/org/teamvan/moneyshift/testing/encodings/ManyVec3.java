// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

/**
 * A fixed-size array of arrays — exercises nested fixed-size lists in Arrow.
 */
public record ManyVec3(
    float[][] triples
) {
    public ManyVec3 {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof ManyVec3 that)) return false;
        return java.util.Arrays.equals(triples, that.triples);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(triples));
    }
    public static final java.lang.String NAME = "rerun.testing.encodings.ManyVec3";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(2);
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(2), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(3), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.FLOAT32, java.util.List.of())))));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ManyVec3> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ManyVec3 value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ManyVec3 value) {
        {
            org.apache.arrow.vector.complex.FixedSizeListVector list0 = (org.apache.arrow.vector.complex.FixedSizeListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list1 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start1 = list1.startNewValue(start0 + 0);
                org.apache.arrow.vector.FieldVector child1 = list1.getDataVector();
                ((org.apache.arrow.vector.Float4Vector) child1).setSafe(start1 + 0, value.triples()[0][0]);
                ((org.apache.arrow.vector.Float4Vector) child1).setSafe(start1 + 1, value.triples()[0][1]);
                ((org.apache.arrow.vector.Float4Vector) child1).setSafe(start1 + 2, value.triples()[0][2]);
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list2 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start2 = list2.startNewValue(start0 + 1);
                org.apache.arrow.vector.FieldVector child2 = list2.getDataVector();
                ((org.apache.arrow.vector.Float4Vector) child2).setSafe(start2 + 0, value.triples()[1][0]);
                ((org.apache.arrow.vector.Float4Vector) child2).setSafe(start2 + 1, value.triples()[1][1]);
                ((org.apache.arrow.vector.Float4Vector) child2).setSafe(start2 + 2, value.triples()[1][2]);
            }
        }
    }
}
