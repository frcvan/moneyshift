// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A 2D texture UV coordinate.
 * 
 * Texture coordinates specify a position on a 2D texture.
 * A range from 0-1 covers the entire texture in the respective dimension.
 * Unless configured otherwise, the texture repeats outside of this range.
 * Rerun uses top-left as the origin for UV coordinates.
 * 
 *   0     U     1
 * 0 + --------- →
 *   |           .
 *   |           .
 * V |           .
 *   |           .
 * 1 ↓ . . . . . .
 * 
 * This is the same convention as in Vulkan/Metal/DX12/WebGPU, but (!) unlike OpenGL,
 * which places the origin at the bottom-left.
 */
public record Texcoord2D(
    org.teamvan.moneyshift.encodings.Vec2D uv
) implements org.teamvan.moneyshift.NtComponent<Texcoord2D> {
    public Texcoord2D {
        java.util.Objects.requireNonNull(uv, "uv");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Texcoord2D(float[] xy) {
        this(new org.teamvan.moneyshift.encodings.Vec2D(xy));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Texcoord2D";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Texcoord2D> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).uv();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        double[] column1 = new double[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).uv();
            var v1_1 = v1_0 == null ? null : v1_0.xy();
            var v1_2 = v1_1 == null ? null : v1_1[0];
            var value1 = v1_2;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).uv();
            var v2_1 = v2_0 == null ? null : v2_0.xy();
            var v2_2 = v2_1 == null ? null : v2_1[1];
            var value2 = v2_2;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/1", column2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Texcoord2D> values) {
        writeColumnsStatic(w, path, values);
    }

    public static Texcoord2D of(float... values) {
        if (values.length != 2) throw new java.lang.IllegalArgumentException("Texcoord2D needs exactly 2 values, got " + values.length);
        return new Texcoord2D(new org.teamvan.moneyshift.encodings.Vec2D(values));
    }
}
