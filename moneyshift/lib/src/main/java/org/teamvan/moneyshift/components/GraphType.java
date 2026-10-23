// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Specifies if a graph has directed or undirected edges.
 */
public enum GraphType implements org.teamvan.moneyshift.NtComponent<GraphType> {
    /**
     * The graph has undirected edges.
     */
    Undirected(1L),
    /**
     * The graph has directed edges.
     */
    Directed(2L);

    private final long value;

    GraphType(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static GraphType fromValue(long value) {
        for (GraphType candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown GraphType value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.GraphType";

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<GraphType> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = new boolean[n];
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            GraphType value = values.get(i);
            present[i] = value != null;
            anyNull |= value == null;
            column[i] = value == null ? 0L : value.value();
        }
        w.present(path, present, anyNull);
        w.longs(path, column);
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<GraphType> values) {
        writeColumnsStatic(w, path, values);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.GraphType";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }
}
