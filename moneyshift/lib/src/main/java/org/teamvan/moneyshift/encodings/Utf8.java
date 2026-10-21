// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A string of text, encoded as UTF-8.
 */
public record Utf8(
    java.lang.String value
) {
    public Utf8 {
        java.util.Objects.requireNonNull(value, "value");
    }
    public static final java.lang.String NAME = "rerun.encodings.Utf8";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UTF8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, org.teamvan.moneyshift.ArrowFields.UTF8, java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Utf8> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Utf8 value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.VarCharVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.VarCharVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Utf8 value) {
        ((org.apache.arrow.vector.VarCharVector) vector).setSafe(index, value.value().getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }
}
