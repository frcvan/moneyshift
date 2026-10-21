// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * The innermost datatype of an image.
 * 
 * How individual color channel components are encoded.
 */
public enum ChannelDatatype {
    /**
     * 8-bit unsigned integer.
     */
    U8(6L),
    /**
     * 8-bit signed integer.
     */
    I8(7L),
    /**
     * 16-bit unsigned integer.
     */
    U16(8L),
    /**
     * 16-bit signed integer.
     */
    I16(9L),
    /**
     * 32-bit unsigned integer.
     */
    U32(10L),
    /**
     * 32-bit signed integer.
     */
    I32(11L),
    /**
     * 64-bit unsigned integer.
     */
    U64(12L),
    /**
     * 64-bit signed integer.
     */
    I64(13L),
    /**
     * 16-bit IEEE-754 floating point, also known as {@code half}.
     */
    F16(33L),
    /**
     * 32-bit IEEE-754 floating point, also known as {@code float} or {@code single}.
     */
    F32(34L),
    /**
     * 64-bit IEEE-754 floating point, also known as {@code double}.
     */
    F64(35L);

    private final long value;

    ChannelDatatype(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static ChannelDatatype fromValue(long value) {
        for (ChannelDatatype candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown ChannelDatatype value: " + value);
    }

    public static final java.lang.String NAME = "rerun.encodings.ChannelDatatype";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ChannelDatatype> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ChannelDatatype value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ChannelDatatype value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
