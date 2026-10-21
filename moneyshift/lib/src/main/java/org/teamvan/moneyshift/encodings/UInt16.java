// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A 16bit unsigned integer.
 */
public record UInt16(
    int value
) {
    public UInt16 {
    }
    public static final java.lang.String NAME = "rerun.encodings.UInt16";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT16;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, org.teamvan.moneyshift.ArrowFields.UINT16, java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<UInt16> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            UInt16 value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt2Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.UInt2Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, UInt16 value) {
        ((org.apache.arrow.vector.UInt2Vector) vector).setSafe(index, (int) value.value());
    }
}
