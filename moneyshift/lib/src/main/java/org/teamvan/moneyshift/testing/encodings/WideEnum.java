// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.encodings;

/**
 * A test enum with values that require more than one byte.
 */
public enum WideEnum {
    /**
     * Low value.
     */
    Low(1L),
    /**
     * High value.
     */
    High(65536L);

    private final long value;

    WideEnum(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static WideEnum fromValue(long value) {
        for (WideEnum candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown WideEnum value: " + value);
    }

    public static final java.lang.String NAME = "rerun.testing.encodings.WideEnum";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT32;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<WideEnum> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            WideEnum value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt4Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt4Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, WideEnum value) {
        ((org.apache.arrow.vector.UInt4Vector) vector).setSafe(index, (int) value.value());
    }
}
