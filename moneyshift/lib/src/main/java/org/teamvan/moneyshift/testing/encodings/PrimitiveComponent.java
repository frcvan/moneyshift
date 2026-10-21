// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

public record PrimitiveComponent(
    long value
) {
    public PrimitiveComponent {
    }
    public static final java.lang.String NAME = "rerun.testing.encodings.PrimitiveComponent";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT32;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, org.teamvan.moneyshift.ArrowFields.UINT32, java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<PrimitiveComponent> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            PrimitiveComponent value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.UInt4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, PrimitiveComponent value) {
        ((org.apache.arrow.vector.UInt4Vector) vector).setSafe(index, (int) (long) value.value());
    }
}
