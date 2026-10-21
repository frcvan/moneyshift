// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

/**
 * A test of the enum type.
 */
public enum EnumTest {
    /**
     * Great film.
     */
    Up(1L),
    /**
     * Feeling blue.
     */
    Down(2L),
    /**
     * Correct.
     */
    Right(3L),
    /**
     * It's what's remaining.
     */
    Left(4L),
    /**
     * It's the only way to go.
     */
    Forward(5L),
    /**
     * Baby's got it.
     */
    Back(6L);

    private final long value;

    EnumTest(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static EnumTest fromValue(long value) {
        for (EnumTest candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown EnumTest value: " + value);
    }

    public static final java.lang.String NAME = "rerun.testing.encodings.EnumTest";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<EnumTest> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            EnumTest value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, EnumTest value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
