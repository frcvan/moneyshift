// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A single boolean.
 */
public record Bool(
    boolean value
) {
    public Bool {
    }
    public static final java.lang.String NAME = "rerun.encodings.Bool";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.BOOL;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, org.teamvan.moneyshift.ArrowFields.BOOL, java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Bool> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Bool value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.BitVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.BitVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Bool value) {
        ((org.apache.arrow.vector.BitVector) vector).setSafe(index, ((value.value()) ? 1 : 0));
    }
}
