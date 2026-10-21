// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Stores a tuple of UTF-8 strings.
 */
public record Utf8Pair(
    /**
     * The first string.
     */
    org.teamvan.moneyshift.encodings.Utf8 first,
    /**
     * The second string.
     */
    org.teamvan.moneyshift.encodings.Utf8 second
) {
    public Utf8Pair {
        java.util.Objects.requireNonNull(first, "first");
        java.util.Objects.requireNonNull(second, "second");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public Utf8Pair(java.lang.String first, java.lang.String second) {
        this(new org.teamvan.moneyshift.encodings.Utf8(first), new org.teamvan.moneyshift.encodings.Utf8(second));
    }

    public static final java.lang.String NAME = "rerun.encodings.Utf8Pair";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.Utf8.arrowField("first", false), org.teamvan.moneyshift.encodings.Utf8.arrowField("second", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Utf8Pair> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Utf8Pair value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Utf8Pair value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.Utf8 f0 = value.first();
        org.teamvan.moneyshift.encodings.Utf8.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.encodings.Utf8 f1 = value.second();
        org.teamvan.moneyshift.encodings.Utf8.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
