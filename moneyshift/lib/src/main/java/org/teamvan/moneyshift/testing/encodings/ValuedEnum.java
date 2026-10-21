// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

/**
 * A test of an enumerate with specified values.
 */
public enum ValuedEnum {
    /**
     * One.
     */
    One(1L),
    /**
     * Two.
     */
    Two(2L),
    /**
     * Three.
     */
    Three(3L),
    /**
     * The answer to life, the universe, and everything.
     */
    TheAnswer(42L);

    private final long value;

    ValuedEnum(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static ValuedEnum fromValue(long value) {
        for (ValuedEnum candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown ValuedEnum value: " + value);
    }

    public static final java.lang.String NAME = "rerun.testing.encodings.ValuedEnum";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ValuedEnum> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ValuedEnum value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ValuedEnum value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
