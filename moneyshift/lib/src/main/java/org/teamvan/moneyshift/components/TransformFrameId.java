// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A string identifier for a transform frame.
 * 
 * Transform frames may be derived from entity paths to refer to Rerun's implicit
 * entity path driven hierarchy which is defined via {@link org.teamvan.moneyshift.archetypes.Transform3D rerun::archetypes::Transform3D}, {@link org.teamvan.moneyshift.archetypes.Pinhole rerun::archetypes::Pinhole} etc..
 * These implicit transform frames look like {@code tf#path/to/entity}.
 * 
 * Note that any {@link org.teamvan.moneyshift.archetypes.Transform3D rerun::archetypes::Transform3D}s logged with both {@code parent_frame} and {@code child_frame} set
 * describes a relationship between these parent and child transform frames, **not** the transform frame
 * that the entity path may be using (defined by an {@link org.teamvan.moneyshift.archetypes.CoordinateFrame rerun::archetypes::CoordinateFrame}).
 */
public record TransformFrameId(
    org.teamvan.moneyshift.encodings.Utf8 value
) implements org.teamvan.moneyshift.NtComponent<TransformFrameId> {
    public TransformFrameId {
        java.util.Objects.requireNonNull(value, "value");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public TransformFrameId(java.lang.String value) {
        this(new org.teamvan.moneyshift.encodings.Utf8(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.TransformFrameId";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TransformFrameId> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).value();
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
        java.lang.String[] column1 = new java.lang.String[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).value();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            java.lang.String value1 = v1_1;
            column1[i] = value1 == null ? "" : value1;
        }
        w.strings(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TransformFrameId> values) {
        writeColumnsStatic(w, path, values);
    }
}
