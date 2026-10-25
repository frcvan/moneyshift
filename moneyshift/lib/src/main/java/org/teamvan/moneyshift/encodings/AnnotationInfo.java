// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Annotation info annotating a class id or key-point id.
 * 
 * Color and label will be used to annotate entities/keypoints which reference the id.
 * The id refers either to a class or key-point id
 */
public record AnnotationInfo(
    /**
     * {@link org.teamvan.moneyshift.encodings.ClassId rerun::encodings::ClassId} or {@link org.teamvan.moneyshift.encodings.KeypointId rerun::encodings::KeypointId} to which this annotation info belongs.
     */
    int id,
    /**
     * The label that will be shown in the UI.
     */
    org.teamvan.moneyshift.encodings.Utf8 label,
    /**
     * The color that will be applied to the annotated entity.
     */
    org.teamvan.moneyshift.encodings.Rgba32 color
) implements org.teamvan.moneyshift.NtEncoding<AnnotationInfo> {
    public AnnotationInfo {
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public AnnotationInfo(int id, java.lang.String label, long color) {
        this(id, new org.teamvan.moneyshift.encodings.Utf8(label), new org.teamvan.moneyshift.encodings.Rgba32(color));
    }

    public static final java.lang.String NAME = "rerun.encodings.AnnotationInfo";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<AnnotationInfo> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
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
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.id();
            var value1 = v1_0;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/id", column1);
        boolean[] present2 = null;
        boolean anyNull2 = false;
        for (int i = 0; i < n; i++) {
            var v2_root = values.get(i);
            var v2_0 = v2_root == null ? null : v2_root.label();
            boolean ok2 = (v2_0) != null;
            if (ok2) {
                if (present2 != null) present2[i] = true;
            } else {
                if (present2 == null) {
                    present2 = new boolean[n];
                    java.util.Arrays.fill(present2, 0, i, true);
                }
                anyNull2 = true;
            }
        }
        w.present(path + "/label", present2, anyNull2, n);
        java.lang.String[] column3 = new java.lang.String[n];
        for (int i = 0; i < n; i++) {
            var v3_root = values.get(i);
            var v3_0 = v3_root == null ? null : v3_root.label();
            var v3_1 = v3_0 == null ? null : v3_0.value();
            java.lang.String value3 = v3_1;
            column3[i] = value3 == null ? "" : value3;
        }
        w.strings(path + "/label", column3);
        boolean[] present4 = null;
        boolean anyNull4 = false;
        for (int i = 0; i < n; i++) {
            var v4_root = values.get(i);
            var v4_0 = v4_root == null ? null : v4_root.color();
            boolean ok4 = (v4_0) != null;
            if (ok4) {
                if (present4 != null) present4[i] = true;
            } else {
                if (present4 == null) {
                    present4 = new boolean[n];
                    java.util.Arrays.fill(present4, 0, i, true);
                }
                anyNull4 = true;
            }
        }
        w.present(path + "/color", present4, anyNull4, n);
        long[] column5 = new long[n];
        for (int i = 0; i < n; i++) {
            var v5_root = values.get(i);
            var v5_0 = v5_root == null ? null : v5_root.color();
            var v5_1 = v5_0 == null ? null : v5_0.rgba();
            var value5 = v5_1;
            column5[i] = value5 == null ? 0L : value5;
        }
        w.longs(path + "/color", column5);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<AnnotationInfo> values) {
        writeColumnsStatic(w, path, values);
    }
}
