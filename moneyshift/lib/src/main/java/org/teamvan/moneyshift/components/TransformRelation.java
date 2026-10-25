// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Specifies relation a spatial transform describes.
 */
public enum TransformRelation implements org.teamvan.moneyshift.NtComponent<TransformRelation> {
    /**
     * The transform describes how to transform into the parent entity's space.
     * 
     * E.g. a translation of (0, 1, 0) with this {@link org.teamvan.moneyshift.components.TransformRelation rerun::components::TransformRelation} logged at {@code parent/child} means
     * that from the point of view of {@code parent}, {@code parent/child} is translated 1 unit along {@code parent}'s Y axis.
     * From perspective of {@code parent/child}, the {@code parent} entity is translated -1 unit along {@code parent/child}'s Y axis.
     */
    ParentFromChild(1L),
    /**
     * The transform describes how to transform into the child entity's space.
     * 
     * E.g. a translation of (0, 1, 0) with this {@link org.teamvan.moneyshift.components.TransformRelation rerun::components::TransformRelation} logged at {@code parent/child} means
     * that from the point of view of {@code parent}, {@code parent/child} is translated -1 unit along {@code parent}'s Y axis.
     * From perspective of {@code parent/child}, the {@code parent} entity is translated 1 unit along {@code parent/child}'s Y axis.
     */
    ChildFromParent(2L);

    private final long value;

    TransformRelation(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static TransformRelation fromValue(long value) {
        for (TransformRelation candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown TransformRelation value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.TransformRelation";

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TransformRelation> values) {
        int n = values.size();
        long[] column = new long[n];
        boolean[] present = null;
        boolean anyNull = false;
        for (int i = 0; i < n; i++) {
            TransformRelation value = values.get(i);
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
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TransformRelation> values) {
        writeColumnsStatic(w, path, values);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.TransformRelation";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }
}
