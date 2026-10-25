// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * The description of a semantic Class.
 * 
 * If an entity is annotated with a corresponding {@link org.teamvan.moneyshift.components.ClassId rerun::components::ClassId}, Rerun will use
 * the attached {@link org.teamvan.moneyshift.encodings.AnnotationInfo rerun::encodings::AnnotationInfo} to derive labels and colors.
 * 
 * Keypoints within an annotation class can similarly be annotated with a
 * {@link org.teamvan.moneyshift.components.KeypointId rerun::components::KeypointId} in which case we should defer to the label and color for the
 * {@link org.teamvan.moneyshift.encodings.AnnotationInfo rerun::encodings::AnnotationInfo} specifically associated with the Keypoint.
 * 
 * Keypoints within the class can also be decorated with skeletal edges.
 * Keypoint-connections are pairs of {@link org.teamvan.moneyshift.components.KeypointId rerun::components::KeypointId}s. If an edge is
 * defined, and both keypoints exist within the instance of the class, then the
 * keypoints should be connected with an edge. The edge should be labeled and
 * colored as described by the class's {@link org.teamvan.moneyshift.encodings.AnnotationInfo rerun::encodings::AnnotationInfo}.
 * 
 */
public record ClassDescription(
    /**
     * The {@link org.teamvan.moneyshift.encodings.AnnotationInfo rerun::encodings::AnnotationInfo} for the class.
     */
    org.teamvan.moneyshift.encodings.AnnotationInfo info,
    /**
     * The {@link org.teamvan.moneyshift.encodings.AnnotationInfo rerun::encodings::AnnotationInfo} for all of the keypoints.
     */
    java.util.List<org.teamvan.moneyshift.encodings.AnnotationInfo> keypointAnnotations,
    /**
     * The connections between keypoints.
     */
    java.util.List<org.teamvan.moneyshift.encodings.KeypointPair> keypointConnections
) implements org.teamvan.moneyshift.NtEncoding<ClassDescription> {
    public ClassDescription {
        java.util.Objects.requireNonNull(info, "info");
        keypointAnnotations = java.util.List.copyOf(keypointAnnotations);
        keypointConnections = java.util.List.copyOf(keypointConnections);
    }
    public static final java.lang.String NAME = "rerun.encodings.ClassDescription";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ClassDescription> values) {
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
            var v1_0 = values.get(i) == null ? null : values.get(i).info();
            var v1_1 = v1_0 == null ? null : v1_0.id();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/info/id", column1);
        boolean[] present2 = null;
        boolean anyNull2 = false;
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).info();
            var v2_1 = v2_0 == null ? null : v2_0.label();
            boolean ok2 = (v2_1) != null;
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
        w.present(path + "/info/label", present2, anyNull2, n);
        java.lang.String[] column3 = new java.lang.String[n];
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).info();
            var v3_1 = v3_0 == null ? null : v3_0.label();
            var v3_2 = v3_1 == null ? null : v3_1.value();
            java.lang.String value3 = v3_2;
            column3[i] = value3 == null ? "" : value3;
        }
        w.strings(path + "/info/label", column3);
        boolean[] present4 = null;
        boolean anyNull4 = false;
        for (int i = 0; i < n; i++) {
            var v4_0 = values.get(i) == null ? null : values.get(i).info();
            var v4_1 = v4_0 == null ? null : v4_0.color();
            boolean ok4 = (v4_1) != null;
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
        w.present(path + "/info/color", present4, anyNull4, n);
        long[] column5 = new long[n];
        for (int i = 0; i < n; i++) {
            var v5_0 = values.get(i) == null ? null : values.get(i).info();
            var v5_1 = v5_0 == null ? null : v5_0.color();
            var v5_2 = v5_1 == null ? null : v5_1.rgba();
            var value5 = v5_2;
            column5[i] = value5 == null ? 0L : value5;
        }
        w.longs(path + "/info/color", column5);
        long[] counts6 = new long[n];
        int total6 = 0;
        for (int i = 0; i < n; i++) {
            var v6_0 = values.get(i) == null ? null : values.get(i).keypointAnnotations();
            java.util.List<org.teamvan.moneyshift.encodings.AnnotationInfo> list6 = v6_0;
            counts6[i] = list6 == null ? 0L : list6.size();
            total6 += (int) counts6[i];
        }
        w.longs(path + "/keypoint_annotations" + "/.count", counts6);
        java.util.ArrayList<org.teamvan.moneyshift.encodings.AnnotationInfo> items6 = new java.util.ArrayList<>(total6);
        for (int i = 0; i < n; i++) {
            var v6_0 = values.get(i) == null ? null : values.get(i).keypointAnnotations();
            java.util.List<org.teamvan.moneyshift.encodings.AnnotationInfo> list6 = v6_0;
            if (list6 != null) items6.addAll(list6);
        }
        int itemCount6 = items6.size();
        long[] column7 = new long[itemCount6];
        for (int j6 = 0; j6 < itemCount6; j6++) {
            var v7_0 = items6.get(j6).id();
            long value7 = v7_0;
            column7[j6] = value7;
        }
        w.longs(path + "/keypoint_annotations/.items/id", column7);
        boolean[] present8 = null;
        boolean anyNull8 = false;
        for (int j6 = 0; j6 < itemCount6; j6++) {
            var v8_0 = items6.get(j6).label();
            boolean ok8 = (v8_0) != null;
            if (ok8) {
                if (present8 != null) present8[j6] = true;
            } else {
                if (present8 == null) {
                    present8 = new boolean[itemCount6];
                    java.util.Arrays.fill(present8, 0, j6, true);
                }
                anyNull8 = true;
            }
        }
        w.present(path + "/keypoint_annotations/.items/label", present8, anyNull8, itemCount6);
        java.lang.String[] column9 = new java.lang.String[itemCount6];
        for (int j6 = 0; j6 < itemCount6; j6++) {
            var v9_0 = items6.get(j6).label();
            var v9_1 = v9_0 == null ? null : v9_0.value();
            java.lang.String value9 = v9_1;
            column9[j6] = value9 == null ? "" : value9;
        }
        w.strings(path + "/keypoint_annotations/.items/label", column9);
        boolean[] present10 = null;
        boolean anyNull10 = false;
        for (int j6 = 0; j6 < itemCount6; j6++) {
            var v10_0 = items6.get(j6).color();
            boolean ok10 = (v10_0) != null;
            if (ok10) {
                if (present10 != null) present10[j6] = true;
            } else {
                if (present10 == null) {
                    present10 = new boolean[itemCount6];
                    java.util.Arrays.fill(present10, 0, j6, true);
                }
                anyNull10 = true;
            }
        }
        w.present(path + "/keypoint_annotations/.items/color", present10, anyNull10, itemCount6);
        long[] column11 = new long[itemCount6];
        for (int j6 = 0; j6 < itemCount6; j6++) {
            var v11_0 = items6.get(j6).color();
            var v11_1 = v11_0 == null ? null : v11_0.rgba();
            var value11 = v11_1;
            column11[j6] = value11 == null ? 0L : value11;
        }
        w.longs(path + "/keypoint_annotations/.items/color", column11);
        long[] counts12 = new long[n];
        int total12 = 0;
        for (int i = 0; i < n; i++) {
            var v12_0 = values.get(i) == null ? null : values.get(i).keypointConnections();
            java.util.List<org.teamvan.moneyshift.encodings.KeypointPair> list12 = v12_0;
            counts12[i] = list12 == null ? 0L : list12.size();
            total12 += (int) counts12[i];
        }
        w.longs(path + "/keypoint_connections" + "/.count", counts12);
        java.util.ArrayList<org.teamvan.moneyshift.encodings.KeypointPair> items12 = new java.util.ArrayList<>(total12);
        for (int i = 0; i < n; i++) {
            var v12_0 = values.get(i) == null ? null : values.get(i).keypointConnections();
            java.util.List<org.teamvan.moneyshift.encodings.KeypointPair> list12 = v12_0;
            if (list12 != null) items12.addAll(list12);
        }
        int itemCount12 = items12.size();
        long[] column13 = new long[itemCount12];
        for (int j12 = 0; j12 < itemCount12; j12++) {
            var v13_0 = items12.get(j12).keypoint0();
            var v13_1 = v13_0.id();
            long value13 = v13_1;
            column13[j12] = value13;
        }
        w.longs(path + "/keypoint_connections/.items/keypoint0", column13);
        long[] column14 = new long[itemCount12];
        for (int j12 = 0; j12 < itemCount12; j12++) {
            var v14_0 = items12.get(j12).keypoint1();
            var v14_1 = v14_0.id();
            long value14 = v14_1;
            column14[j12] = value14;
        }
        w.longs(path + "/keypoint_connections/.items/keypoint1", column14);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ClassDescription> values) {
        writeColumnsStatic(w, path, values);
    }
}
