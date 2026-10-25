// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * The metadata describing the contents of a {@link org.teamvan.moneyshift.components.ImageBuffer rerun::components::ImageBuffer}.
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
     * If specified, this takes precedence over both {@link org.teamvan.moneyshift.encodings.ColorModel rerun::encodings::ColorModel} and {@link org.teamvan.moneyshift.encodings.ChannelDatatype rerun::encodings::ChannelDatatype} (which are ignored).
     */
    org.teamvan.moneyshift.encodings.PixelFormat pixelFormat,
    /**
     * L, RGB, RGBA, …
     * 
     * Also requires a {@link org.teamvan.moneyshift.encodings.ChannelDatatype rerun::encodings::ChannelDatatype} to fully specify the pixel format.
     */
    org.teamvan.moneyshift.encodings.ColorModel colorModel,
    /**
     * The data type of each channel (e.g. the red channel) of the image data (U8, F16, …).
     * 
     * Also requires a {@link org.teamvan.moneyshift.encodings.ColorModel rerun::encodings::ColorModel} to fully specify the pixel format.
     */
    org.teamvan.moneyshift.encodings.ChannelDatatype channelDatatype
) implements org.teamvan.moneyshift.NtEncoding<ImageFormat> {
    public ImageFormat {
    }
    public static final java.lang.String NAME = "rerun.encodings.ImageFormat";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ImageFormat> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
            if (ok0) {
                if (present0 != null) present0[i] = true;
            } else {
                if (present0 == null) {
                    present0 = new boolean[n];
                    java.util.Arrays.fill(present0, 0, i, true);
                }
                anyNull0 = true;
            }
        }
        w.present(path, present0, anyNull0, n);
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.width();
            var value1 = v1_0;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/width", column1);
        long[] column2 = new long[n];
        for (int i = 0; i < n; i++) {
            var v2_root = values.get(i);
            var v2_0 = v2_root == null ? null : v2_root.height();
            var value2 = v2_0;
            column2[i] = value2 == null ? 0L : value2;
        }
        w.longs(path + "/height", column2);
        boolean[] present3 = null;
        boolean anyNull3 = false;
        for (int i = 0; i < n; i++) {
            var v3_root = values.get(i);
            var v3_0 = v3_root == null ? null : v3_root.pixelFormat();
            boolean ok3 = (v3_0) != null;
            if (ok3) {
                if (present3 != null) present3[i] = true;
            } else {
                if (present3 == null) {
                    present3 = new boolean[n];
                    java.util.Arrays.fill(present3, 0, i, true);
                }
                anyNull3 = true;
            }
        }
        w.present(path + "/pixel_format", present3, anyNull3, n);
        long[] column4 = new long[n];
        for (int i = 0; i < n; i++) {
            var v4_root = values.get(i);
            var v4_0 = v4_root == null ? null : v4_root.pixelFormat();
            org.teamvan.moneyshift.encodings.PixelFormat value4 = v4_0;
            column4[i] = value4 == null ? 0L : value4.value();
        }
        w.longs(path + "/pixel_format", column4);
        boolean[] present5 = null;
        boolean anyNull5 = false;
        for (int i = 0; i < n; i++) {
            var v5_root = values.get(i);
            var v5_0 = v5_root == null ? null : v5_root.colorModel();
            boolean ok5 = (v5_0) != null;
            if (ok5) {
                if (present5 != null) present5[i] = true;
            } else {
                if (present5 == null) {
                    present5 = new boolean[n];
                    java.util.Arrays.fill(present5, 0, i, true);
                }
                anyNull5 = true;
            }
        }
        w.present(path + "/color_model", present5, anyNull5, n);
        long[] column6 = new long[n];
        for (int i = 0; i < n; i++) {
            var v6_root = values.get(i);
            var v6_0 = v6_root == null ? null : v6_root.colorModel();
            org.teamvan.moneyshift.encodings.ColorModel value6 = v6_0;
            column6[i] = value6 == null ? 0L : value6.value();
        }
        w.longs(path + "/color_model", column6);
        boolean[] present7 = null;
        boolean anyNull7 = false;
        for (int i = 0; i < n; i++) {
            var v7_root = values.get(i);
            var v7_0 = v7_root == null ? null : v7_root.channelDatatype();
            boolean ok7 = (v7_0) != null;
            if (ok7) {
                if (present7 != null) present7[i] = true;
            } else {
                if (present7 == null) {
                    present7 = new boolean[n];
                    java.util.Arrays.fill(present7, 0, i, true);
                }
                anyNull7 = true;
            }
        }
        w.present(path + "/channel_datatype", present7, anyNull7, n);
        long[] column8 = new long[n];
        for (int i = 0; i < n; i++) {
            var v8_root = values.get(i);
            var v8_0 = v8_root == null ? null : v8_root.channelDatatype();
            org.teamvan.moneyshift.encodings.ChannelDatatype value8 = v8_0;
            column8[i] = value8 == null ? 0L : value8.value();
        }
        w.longs(path + "/channel_datatype", column8);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ImageFormat> values) {
        writeColumnsStatic(w, path, values);
    }
}
