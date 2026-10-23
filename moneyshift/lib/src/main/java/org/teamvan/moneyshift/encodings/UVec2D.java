// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A uint32 vector in 2D space.
 */
public record UVec2D(
    long[] xy
) implements org.teamvan.moneyshift.NtEncoding<UVec2D> {
    public UVec2D {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof UVec2D that)) return false;
        return java.util.Arrays.equals(xy, that.xy);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(xy));
    }
    public static final java.lang.String NAME = "rerun.encodings.UVec2D";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<UVec2D> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).xy();
            var v1_1 = v1_0 == null ? null : v1_0[0];
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/0", column1);
        long[] column2 = new long[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).xy();
            var v2_1 = v2_0 == null ? null : v2_0[1];
            var value2 = v2_1;
            column2[i] = value2 == null ? 0L : value2;
        }
        w.longs(path + "/1", column2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<UVec2D> values) {
        writeColumnsStatic(w, path, values);
    }

    public static UVec2D of(long... values) {
        if (values.length != 2) throw new java.lang.IllegalArgumentException("UVec2D needs exactly 2 values, got " + values.length);
        return new UVec2D(values);
    }
}
