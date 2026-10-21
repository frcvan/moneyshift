// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * The metadata describing the contents of a {@link org.teamvan.moneyshift.components.ImageBuffer}.
 */
public record ImageFormat(
    /**
     * The width of the image in pixels.
     */
    long width,
    /**
     * The height of the image in pixels.
     */
    long height,
    /**
     * Used mainly for chroma downsampled formats and differing number of bits per channel.
     * 
     * If specified, this takes precedence over both {@link org.teamvan.moneyshift.encodings.ColorModel} and {@link org.teamvan.moneyshift.encodings.ChannelDatatype} (which are ignored).
     */
    org.teamvan.moneyshift.encodings.PixelFormat pixelFormat,
    /**
     * L, RGB, RGBA, …
     * 
     * Also requires a {@link org.teamvan.moneyshift.encodings.ChannelDatatype} to fully specify the pixel format.
     */
    org.teamvan.moneyshift.encodings.ColorModel colorModel,
    /**
     * The data type of each channel (e.g. the red channel) of the image data (U8, F16, …).
     * 
     * Also requires a {@link org.teamvan.moneyshift.encodings.ColorModel} to fully specify the pixel format.
     */
    org.teamvan.moneyshift.encodings.ChannelDatatype channelDatatype
) {
    public ImageFormat {
    }
    public static final java.lang.String NAME = "rerun.encodings.ImageFormat";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("width", false, org.teamvan.moneyshift.ArrowFields.UINT32, java.util.List.of()), org.teamvan.moneyshift.ArrowFields.field("height", false, org.teamvan.moneyshift.ArrowFields.UINT32, java.util.List.of()), org.teamvan.moneyshift.encodings.PixelFormat.arrowField("pixel_format", true), org.teamvan.moneyshift.encodings.ColorModel.arrowField("color_model", true), org.teamvan.moneyshift.encodings.ChannelDatatype.arrowField("channel_datatype", true)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ImageFormat> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ImageFormat value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ImageFormat value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        long f0 = value.width();
        ((org.apache.arrow.vector.UInt4Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0)).setSafe(index, (int) (long) f0);
        long f1 = value.height();
        ((org.apache.arrow.vector.UInt4Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1)).setSafe(index, (int) (long) f1);
        org.teamvan.moneyshift.encodings.PixelFormat f2 = value.pixelFormat();
        if (f2 == null) {
            ((org.apache.arrow.vector.UInt1Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(2)).setNull(index);
        } else {
            org.teamvan.moneyshift.encodings.PixelFormat.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(2), index, f2);
        }
        org.teamvan.moneyshift.encodings.ColorModel f3 = value.colorModel();
        if (f3 == null) {
            ((org.apache.arrow.vector.UInt1Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(3)).setNull(index);
        } else {
            org.teamvan.moneyshift.encodings.ColorModel.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(3), index, f3);
        }
        org.teamvan.moneyshift.encodings.ChannelDatatype f4 = value.channelDatatype();
        if (f4 == null) {
            ((org.apache.arrow.vector.UInt1Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(4)).setNull(index);
        } else {
            org.teamvan.moneyshift.encodings.ChannelDatatype.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(4), index, f4);
        }
    }
}
