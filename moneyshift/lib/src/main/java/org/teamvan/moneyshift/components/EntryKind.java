// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The kind of a catalog entry.
 * 
 * The values match those of {@code rerun.cloud.v1alpha1.EntryKind}.
 */
public enum EntryKind implements org.teamvan.moneyshift.NtComponent<EntryKind> {
    /**
     * A dataset.
     */
    Dataset(1L),
    /**
     * A view over a dataset.
     */
    DatasetView(2L),
    /**
     * A table.
     */
    Table(3L),
    /**
     * A view over a table.
     */
    TableView(4L),
    /**
     * A dataset holding the blueprints of another entry.
     */
    BlueprintDataset(5L),
    /**
     * A dataset holding the static assets shared by the segments of another dataset.
     */
    AssetDataset(6L);

    private final long value;

    EntryKind(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static EntryKind fromValue(long value) {
        for (EntryKind candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown EntryKind value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.EntryKind";

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<EntryKind> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = null;
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            EntryKind value = values.get(i);
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
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<EntryKind> values) {
        writeColumnsStatic(w, path, values);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.EntryKind";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }
}
