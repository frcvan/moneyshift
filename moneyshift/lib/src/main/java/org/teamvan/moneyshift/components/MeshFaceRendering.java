// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Determines which faces of a mesh are rendered.
 * 
 * For this purpose, we assume that the winding order of vertices in a mesh is
 * consistent and that front faces are defined as those with vertices in counter clockwise order.
 */
public enum MeshFaceRendering implements org.teamvan.moneyshift.NtComponent<MeshFaceRendering> {
    /**
     * Show both back and front faces.
     */
    DoubleSided(1L),
    /**
     * Only front faces are shown.
     * 
     * Front faces are assumed to have a counter clockwise vertex winding order on screen.
     */
    Front(2L),
    /**
     * Only back faces are shown.
     * 
     * Back faces are assumed to have a clockwise vertex winding order on screen.
     */
    Back(3L);

    private final long value;

    MeshFaceRendering(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static MeshFaceRendering fromValue(long value) {
        for (MeshFaceRendering candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown MeshFaceRendering value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.MeshFaceRendering";

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<MeshFaceRendering> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = null;
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            MeshFaceRendering value = values.get(i);
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
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<MeshFaceRendering> values) {
        writeColumnsStatic(w, path, values);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.MeshFaceRendering";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }
}
