// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A binary blob of data.
 */
public record Blob(
    java.util.List<java.lang.Short> data
) {
    public Blob {
        data = java.util.List.copyOf(data);
    }
    public static final java.lang.String NAME = "rerun.encodings.Blob";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.List();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.UINT8, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Blob> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Blob value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.ListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.ListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Blob value) {
        {
            org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            java.util.List<java.lang.Short> values0 = value.data();
            for (int j0 = 0; j0 < values0.size(); j0++) {
                ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + j0, (int) values0.get(j0));
            }
            list0.endValue(index, values0.size());
        }
    }
}
