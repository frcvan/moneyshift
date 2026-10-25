// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Specifies what color components are present in an {@link org.teamvan.moneyshift.archetypes.Image rerun::archetypes::Image}.
 * 
 * This combined with {@link org.teamvan.moneyshift.encodings.ChannelDatatype rerun::encodings::ChannelDatatype} determines the pixel format of an image.
 */
public enum ColorModel implements org.teamvan.moneyshift.NtEncoding<ColorModel> {
    /**
     * Grayscale luminance intencity/brightness/value, sometimes called {@code Y}
     */
    L(1L),
    /**
     * Red, Green, Blue
     */
    RGB(2L),
    /**
     * Red, Green, Blue, Alpha
     */
    RGBA(3L),
    /**
     * Blue, Green, Red
     */
    BGR(4L),
    /**
     * Blue, Green, Red, Alpha
     */
    BGRA(5L);

    private final long value;

    ColorModel(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static ColorModel fromValue(long value) {
        for (ColorModel candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown ColorModel value: " + value);
    }

    public static final java.lang.String NAME = "rerun.encodings.ColorModel";

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ColorModel> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = null;
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            ColorModel value = values.get(i);
            if (value != null) {
                if (present != null) present[i] = true;
            } else {
                if (present == null) {
                    present = new boolean[n];
                    java.util.Arrays.fill(present, 0, i, true);
                }
                anyNull = true;
            }
            column[i] = value == null ? 0L : value.value();
        }
        w.present(path, present, anyNull, n);
        w.longs(path, column);
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ColorModel> values) {
        writeColumnsStatic(w, path, values);
    }
}
