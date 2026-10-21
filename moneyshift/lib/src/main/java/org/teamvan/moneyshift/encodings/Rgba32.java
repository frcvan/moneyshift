// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * An RGBA color with unmultiplied/separate alpha, in sRGB gamma space with linear alpha.
 * 
 * The color is stored as a 32-bit integer, where the most significant
 * byte is {@code R} and the least significant byte is {@code A}.
 * 
 */
public record Rgba32(
    long rgba
) {
    public Rgba32 {
    }
    public static final java.lang.String NAME = "rerun.encodings.Rgba32";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT32;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, org.teamvan.moneyshift.ArrowFields.UINT32, java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Rgba32> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Rgba32 value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.UInt4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Rgba32 value) {
        ((org.apache.arrow.vector.UInt4Vector) vector).setSafe(index, (int) (long) value.rgba());
    }
}
