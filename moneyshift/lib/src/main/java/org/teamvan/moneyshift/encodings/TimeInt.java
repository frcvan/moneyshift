// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A 64-bit number describing either nanoseconds OR sequence numbers.
 */
public record TimeInt(
    long value
) {
    public TimeInt {
    }
    public static final java.lang.String NAME = "rerun.encodings.TimeInt";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.INT64;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, org.teamvan.moneyshift.ArrowFields.INT64, java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TimeInt> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TimeInt value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.BigIntVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.BigIntVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TimeInt value) {
        ((org.apache.arrow.vector.BigIntVector) vector).setSafe(index, value.value());
    }
}
