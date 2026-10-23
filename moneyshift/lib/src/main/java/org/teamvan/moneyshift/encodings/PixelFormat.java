// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Specifieds a particular format of an {@link org.teamvan.moneyshift.archetypes.Image rerun::archetypes::Image}.
 * 
 * Most images can be described by a {@link org.teamvan.moneyshift.encodings.ColorModel rerun::encodings::ColorModel} and a {@link org.teamvan.moneyshift.encodings.ChannelDatatype rerun::encodings::ChannelDatatype},
 * e.g. {@code RGB} and {@code U8} respectively.
 * 
 * However, some image formats has chroma downsampling and/or
 * use differing number of bits per channel, and that is what this {@link org.teamvan.moneyshift.encodings.PixelFormat rerun::encodings::PixelFormat} is for.
 * 
 * All these formats support random access.
 * 
 * For more compressed image formats, see {@link org.teamvan.moneyshift.archetypes.EncodedImage rerun::archetypes::EncodedImage}.
 */
public enum PixelFormat implements org.teamvan.moneyshift.NtEncoding<PixelFormat> {
    /**
     * {@code Y_U_V12} is a YUV 4:2:0 fully planar YUV format without chroma downsampling, also known as {@code I420}.
     * 
     * This uses limited range YUV, i.e. Y is expected to be within This uses limited range YUV, i.e. Y is expected to be within [16, 235] and U/V within [16, 240].
     * 
     * First comes entire image in Y in one plane, followed by the U and V planes, which each only have half
     * the resolution of the Y plane.
     */
    Y_U_V12_LimitedRange(20L),
    /**
     * {@code NV12} (aka {@code Y_UV12}) is a YUV 4:2:0 chroma downsampled form at with 12 bits per pixel and 8 bits per channel.
     * 
     * This uses limited range YUV, i.e. Y is expected to be within This uses limited range YUV, i.e. Y is expected to be within [16, 235] and U/V within [16, 240].
     * 
     * First comes entire image in Y in one plane,
     * followed by a plane with interleaved lines ordered as U0, V0, U1, V1, etc.
     */
    NV12(26L),
    /**
     * {@code YUY2} (aka 'YUYV', 'YUYV16' or 'NV21'), is a YUV 4:2:2 chroma downsampled format with 16 bits per pixel and 8 bits per channel.
     * 
     * This uses limited range YUV, i.e. Y is expected to be within This uses limited range YUV, i.e. Y is expected to be within [16, 235] and U/V within [16, 240].
     * 
     * The order of the channels is Y0, U0, Y1, V0, all in the same plane.
     */
    YUY2(27L),
    /**
     * Monochrome Y plane only, essentially a YUV 4:0:0 planar format.
     * 
     * Also known as just "gray". This is virtually identical to a 8bit luminance/grayscale (see {@link org.teamvan.moneyshift.encodings.ColorModel rerun::encodings::ColorModel}).
     * 
     * This uses entire range YUV, i.e. Y is expected to be within This uses entire range YUV, i.e. Y is expected to be within [0, 255].
     * (as opposed to "limited range" YUV as used e.g. in NV12).
     */
    Y8_FullRange(30L),
    /**
     * {@code Y_U_V24} is a YUV 4:4:4 fully planar YUV format without chroma downsampling, also known as {@code I444}.
     * 
     * This uses limited range YUV, i.e. Y is expected to be within This uses limited range YUV, i.e. Y is expected to be within [16, 235] and U/V within [16, 240].
     * 
     * First comes entire image in Y in one plane, followed by the U and V planes.
     */
    Y_U_V24_LimitedRange(39L),
    /**
     * {@code Y_U_V24} is a YUV 4:4:4 fully planar YUV format without chroma downsampling, also known as {@code I444}.
     * 
     * This uses full range YUV with all components ranging from 0 to 255
     * (as opposed to "limited range" YUV as used e.g. in NV12).
     * 
     * First comes entire image in Y in one plane, followed by the U and V planes.
     */
    Y_U_V24_FullRange(40L),
    /**
     * Monochrome Y plane only, essentially a YUV 4:0:0 planar format.
     * 
     * Also known as just "gray".
     * 
     * This uses limited range YUV, i.e. Y is expected to be within This uses limited range YUV, i.e. Y is expected to be within [16, 235].
     * If not for this range limitation/remapping, this is almost identical to 8bit luminace/grayscale (see {@link org.teamvan.moneyshift.encodings.ColorModel rerun::encodings::ColorModel}).
     */
    Y8_LimitedRange(41L),
    /**
     * {@code Y_U_V12} is a YUV 4:2:0 fully planar YUV format without chroma downsampling, also known as {@code I420}.
     * 
     * This uses full range YUV with all components ranging from 0 to 255
     * (as opposed to "limited range" YUV as used e.g. in NV12).
     * 
     * First comes entire image in Y in one plane, followed by the U and V planes, which each only have half
     * the resolution of the Y plane.
     */
    Y_U_V12_FullRange(44L),
    /**
     * {@code Y_U_V16} is a YUV 4:2:2 fully planar YUV format without chroma downsampling, also known as {@code I422}.
     * 
     * This uses limited range YUV, i.e. Y is expected to be within This uses limited range YUV, i.e. Y is expected to be within [16, 235] and U/V within [16, 240].
     * 
     * First comes entire image in Y in one plane, followed by the U and V planes, which each only have half
     * the horizontal resolution of the Y plane.
     */
    Y_U_V16_LimitedRange(49L),
    /**
     * {@code Y_U_V16} is a YUV 4:2:2 fully planar YUV format without chroma downsampling, also known as {@code I422}.
     * 
     * This uses full range YUV with all components ranging from 0 to 255
     * (as opposed to "limited range" YUV as used e.g. in NV12).
     * 
     * First comes entire image in Y in one plane, followed by the U and V planes, which each only have half
     * the horizontal resolution of the Y plane.
     */
    Y_U_V16_FullRange(50L);

    private final long value;

    PixelFormat(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static PixelFormat fromValue(long value) {
        for (PixelFormat candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown PixelFormat value: " + value);
    }

    public static final java.lang.String NAME = "rerun.encodings.PixelFormat";

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<PixelFormat> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = new boolean[n];
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            PixelFormat value = values.get(i);
            present[i] = value != null;
            anyNull |= value == null;
            column[i] = value == null ? 0L : value.value();
        }
        w.present(path, present, anyNull);
        w.longs(path, column);
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<PixelFormat> values) {
        writeColumnsStatic(w, path, values);
    }
}
