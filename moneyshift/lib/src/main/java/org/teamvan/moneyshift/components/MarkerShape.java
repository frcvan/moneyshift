// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The visual appearance of a point in e.g. a 2D plot.
 */
public enum MarkerShape implements org.teamvan.moneyshift.NtComponent<MarkerShape> {
    /**
     * {@code ⏺}
     */
    Circle(1L),
    /**
     * {@code ◆}
     */
    Diamond(2L),
    /**
     * {@code ◼️}
     */
    Square(3L),
    /**
     * {@code x}
     */
    Cross(4L),
    /**
     * {@code +}
     */
    Plus(5L),
    /**
     * {@code ▲}
     */
    Up(6L),
    /**
     * {@code ▼}
     */
    Down(7L),
    /**
     * {@code ◀}
     */
    Left(8L),
    /**
     * {@code ▶}
     */
    Right(9L),
    /**
     * {@code *}
     */
    Asterisk(10L);

    private final long value;

    MarkerShape(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static MarkerShape fromValue(long value) {
        for (MarkerShape candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown MarkerShape value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.MarkerShape";

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<MarkerShape> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = null;
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            MarkerShape value = values.get(i);
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
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<MarkerShape> values) {
        writeColumnsStatic(w, path, values);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.MarkerShape";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }
}
