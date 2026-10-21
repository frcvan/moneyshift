// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A single-precision 32-bit IEEE 754 floating point number.
 */
public record Float32(
    float value
) {
    public Float32 {
    }
    public static final java.lang.String NAME = "rerun.encodings.Float32";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.FLOAT32;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, org.teamvan.moneyshift.ArrowFields.FLOAT32, java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Float32> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Float32 value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.Float4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.Float4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Float32 value) {
        ((org.apache.arrow.vector.Float4Vector) vector).setSafe(index, value.value());
    }
}
