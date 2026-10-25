// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * An RGBA color with unmultiplied/separate alpha, in sRGB gamma space with linear alpha.
 * 
 * The color is stored as a 32-bit integer, where the most significant
 * byte is {@code R} and the least significant byte is {@code A}.
 * 
 */
public record Rgba32(
    long rgba
) implements org.teamvan.moneyshift.NtEncoding<Rgba32> {
    public Rgba32 {
    }
    public static final java.lang.String NAME = "rerun.encodings.Rgba32";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Rgba32> values) {
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
            var v1_0 = values.get(i) == null ? null : values.get(i).rgba();
            var value1 = v1_0;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Rgba32> values) {
        writeColumnsStatic(w, path, values);
    }
}
