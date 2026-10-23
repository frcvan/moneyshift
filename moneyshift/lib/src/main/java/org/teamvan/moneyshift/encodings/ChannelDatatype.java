// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * The innermost datatype of an image.
 * 
 * How individual color channel components are encoded.
 */
public enum ChannelDatatype implements org.teamvan.moneyshift.NtEncoding<ChannelDatatype> {
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

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ChannelDatatype> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = new boolean[n];
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            ChannelDatatype value = values.get(i);
            present[i] = value != null;
            anyNull |= value == null;
            column[i] = value == null ? 0L : value.value();
        }
        w.present(path, present, anyNull);
        w.longs(path, column);
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ChannelDatatype> values) {
        writeColumnsStatic(w, path, values);
    }
}
