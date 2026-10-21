// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Specifies what color components are present in an {@link org.teamvan.moneyshift.archetypes.Image}.
 * 
 * This combined with {@link org.teamvan.moneyshift.encodings.ChannelDatatype} determines the pixel format of an image.
 */
public enum ColorModel {
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
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ColorModel> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ColorModel value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ColorModel value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
