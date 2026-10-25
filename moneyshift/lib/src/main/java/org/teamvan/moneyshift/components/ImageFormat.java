// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The metadata describing the contents of a {@link org.teamvan.moneyshift.components.ImageBuffer rerun::components::ImageBuffer}.
 */
public record ImageFormat(
    org.teamvan.moneyshift.encodings.ImageFormat imageFormat
) implements org.teamvan.moneyshift.NtComponent<ImageFormat> {
    public ImageFormat {
        java.util.Objects.requireNonNull(imageFormat, "imageFormat");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ImageFormat(long width, long height, org.teamvan.moneyshift.encodings.PixelFormat pixelFormat, org.teamvan.moneyshift.encodings.ColorModel colorModel, org.teamvan.moneyshift.encodings.ChannelDatatype channelDatatype) {
        this(new org.teamvan.moneyshift.encodings.ImageFormat(width, height, pixelFormat, colorModel, channelDatatype));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ImageFormat";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ImageFormat> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).imageFormat();
            boolean ok0 = (v0_0) != null;
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
            var v1_0 = values.get(i) == null ? null : values.get(i).imageFormat();
            var v1_1 = v1_0 == null ? null : v1_0.width();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/width", column1);
        long[] column2 = new long[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).imageFormat();
            var v2_1 = v2_0 == null ? null : v2_0.height();
            var value2 = v2_1;
            column2[i] = value2 == null ? 0L : value2;
        }
        w.longs(path + "/height", column2);
        boolean[] present3 = null;
        boolean anyNull3 = false;
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).imageFormat();
            var v3_1 = v3_0 == null ? null : v3_0.pixelFormat();
            boolean ok3 = (v3_1) != null;
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
            var v4_0 = values.get(i) == null ? null : values.get(i).imageFormat();
            var v4_1 = v4_0 == null ? null : v4_0.pixelFormat();
            org.teamvan.moneyshift.encodings.PixelFormat value4 = v4_1;
            column4[i] = value4 == null ? 0L : value4.value();
        }
        w.longs(path + "/pixel_format", column4);
        boolean[] present5 = null;
        boolean anyNull5 = false;
        for (int i = 0; i < n; i++) {
            var v5_0 = values.get(i) == null ? null : values.get(i).imageFormat();
            var v5_1 = v5_0 == null ? null : v5_0.colorModel();
            boolean ok5 = (v5_1) != null;
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
            var v6_0 = values.get(i) == null ? null : values.get(i).imageFormat();
            var v6_1 = v6_0 == null ? null : v6_0.colorModel();
            org.teamvan.moneyshift.encodings.ColorModel value6 = v6_1;
            column6[i] = value6 == null ? 0L : value6.value();
        }
        w.longs(path + "/color_model", column6);
        boolean[] present7 = null;
        boolean anyNull7 = false;
        for (int i = 0; i < n; i++) {
            var v7_0 = values.get(i) == null ? null : values.get(i).imageFormat();
            var v7_1 = v7_0 == null ? null : v7_0.channelDatatype();
            boolean ok7 = (v7_1) != null;
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
            var v8_0 = values.get(i) == null ? null : values.get(i).imageFormat();
            var v8_1 = v8_0 == null ? null : v8_0.channelDatatype();
            org.teamvan.moneyshift.encodings.ChannelDatatype value8 = v8_1;
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
