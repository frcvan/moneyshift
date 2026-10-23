// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * The six cardinal directions for 3D view-space.
 * 
 * Note that these are abstract directions and do on their own not correspond to any specific
 * coordinate system or numerical values.
 */
public enum ViewDir implements org.teamvan.moneyshift.NtEncoding<ViewDir> {
    /**
     * Up.
     */
    Up(1L),
    /**
     * Down.
     */
    Down(2L),
    /**
     * Right.
     */
    Right(3L),
    /**
     * Left.
     */
    Left(4L),
    /**
     * Forward.
     */
    Forward(5L),
    /**
     * Back.
     */
    Back(6L);

    private final long value;

    ViewDir(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static ViewDir fromValue(long value) {
        for (ViewDir candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown ViewDir value: " + value);
    }

    public static final java.lang.String NAME = "rerun.encodings.ViewDir";

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ViewDir> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = new boolean[n];
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            ViewDir value = values.get(i);
            present[i] = value != null;
            anyNull |= value == null;
            column[i] = value == null ? 0L : value.value();
        }
        w.present(path, present, anyNull);
        w.longs(path, column);
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ViewDir> values) {
        writeColumnsStatic(w, path, values);
    }
}
