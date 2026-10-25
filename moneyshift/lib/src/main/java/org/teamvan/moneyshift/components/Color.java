// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * An RGBA color with unmultiplied/separate alpha, in sRGB gamma space with linear alpha.
 * 
 * The color is stored as a 32-bit integer, where the most significant
 * byte is {@code R} and the least significant byte is {@code A}.
 * 
 */
public record Color(
    org.teamvan.moneyshift.encodings.Rgba32 rgba
) implements org.teamvan.moneyshift.NtComponent<Color> {
    public Color {
        java.util.Objects.requireNonNull(rgba, "rgba");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Color(long rgba) {
        this(new org.teamvan.moneyshift.encodings.Rgba32(rgba));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Color";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Color> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).rgba();
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
            var v1_0 = values.get(i) == null ? null : values.get(i).rgba();
            var v1_1 = v1_0 == null ? null : v1_0.rgba();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Color> values) {
        writeColumnsStatic(w, path, values);
    }
}
