// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A helper type for mapping {@link org.teamvan.moneyshift.encodings.ClassId rerun::encodings::ClassId}s to class descriptions.
 * 
 * This is internal to {@link org.teamvan.moneyshift.components.AnnotationContext rerun::components::AnnotationContext}.
 */
public record ClassDescriptionMapElem(
    /**
     * The key: the {@link org.teamvan.moneyshift.components.ClassId rerun::components::ClassId}.
     */
    org.teamvan.moneyshift.encodings.ClassId classId,
    /**
     * The value: class name, color, etc.
     */
    org.teamvan.moneyshift.encodings.ClassDescription classDescription
) implements org.teamvan.moneyshift.NtEncoding<ClassDescriptionMapElem> {
    public ClassDescriptionMapElem {
        java.util.Objects.requireNonNull(classId, "classId");
        java.util.Objects.requireNonNull(classDescription, "classDescription");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public ClassDescriptionMapElem(int classId, org.teamvan.moneyshift.encodings.ClassDescription classDescription) {
        this(new org.teamvan.moneyshift.encodings.ClassId(classId), classDescription);
    }

    public static final java.lang.String NAME = "rerun.encodings.ClassDescriptionMapElem";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ClassDescriptionMapElem> values) {
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
            var v1_0 = values.get(i) == null ? null : values.get(i).classId();
            var v1_1 = v1_0 == null ? null : v1_0.id();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/class_id", column1);
        long[] column2 = new long[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).classDescription();
            var v2_1 = v2_0 == null ? null : v2_0.info();
            var v2_2 = v2_1 == null ? null : v2_1.id();
            var value2 = v2_2;
            column2[i] = value2 == null ? 0L : value2;
        }
        w.longs(path + "/class_description/info/id", column2);
        boolean[] present3 = null;
        boolean anyNull3 = false;
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).classDescription();
            var v3_1 = v3_0 == null ? null : v3_0.info();
            var v3_2 = v3_1 == null ? null : v3_1.label();
            boolean ok3 = (v3_2) != null;
            if (ok3) {
                if (present3 != null) present3[i] = true;
            } else {
                if (present3 == null) {
                    present3 = new boolean[n];
                    java.util.Arrays.fill(present3, 0, i, true);
                }
                anyNull3 = true;
            }
        }
        w.present(path + "/class_description/info/label", present3, anyNull3, n);
        java.lang.String[] column4 = new java.lang.String[n];
        for (int i = 0; i < n; i++) {
            var v4_0 = values.get(i) == null ? null : values.get(i).classDescription();
            var v4_1 = v4_0 == null ? null : v4_0.info();
            var v4_2 = v4_1 == null ? null : v4_1.label();
            var v4_3 = v4_2 == null ? null : v4_2.value();
            java.lang.String value4 = v4_3;
            column4[i] = value4 == null ? "" : value4;
        }
        w.strings(path + "/class_description/info/label", column4);
        boolean[] present5 = null;
        boolean anyNull5 = false;
        for (int i = 0; i < n; i++) {
            var v5_0 = values.get(i) == null ? null : values.get(i).classDescription();
            var v5_1 = v5_0 == null ? null : v5_0.info();
            var v5_2 = v5_1 == null ? null : v5_1.color();
            boolean ok5 = (v5_2) != null;
            if (ok5) {
                if (present5 != null) present5[i] = true;
            } else {
                if (present5 == null) {
                    present5 = new boolean[n];
                    java.util.Arrays.fill(present5, 0, i, true);
                }
                anyNull5 = true;
            }
        }
        w.present(path + "/class_description/info/color", present5, anyNull5, n);
        long[] column6 = new long[n];
        for (int i = 0; i < n; i++) {
            var v6_0 = values.get(i) == null ? null : values.get(i).classDescription();
            var v6_1 = v6_0 == null ? null : v6_0.info();
            var v6_2 = v6_1 == null ? null : v6_1.color();
            var v6_3 = v6_2 == null ? null : v6_2.rgba();
            var value6 = v6_3;
            column6[i] = value6 == null ? 0L : value6;
        }
        w.longs(path + "/class_description/info/color", column6);
        long[] counts7 = new long[n];
        int total7 = 0;
        for (int i = 0; i < n; i++) {
            var v7_0 = values.get(i) == null ? null : values.get(i).classDescription();
            var v7_1 = v7_0 == null ? null : v7_0.keypointAnnotations();
            java.util.List<org.teamvan.moneyshift.encodings.AnnotationInfo> list7 = v7_1;
            counts7[i] = list7 == null ? 0L : list7.size();
            total7 += (int) counts7[i];
        }
        w.longs(path + "/class_description/keypoint_annotations" + "/.count", counts7);
        java.util.ArrayList<org.teamvan.moneyshift.encodings.AnnotationInfo> items7 = new java.util.ArrayList<>(total7);
        for (int i = 0; i < n; i++) {
            var v7_0 = values.get(i) == null ? null : values.get(i).classDescription();
            var v7_1 = v7_0 == null ? null : v7_0.keypointAnnotations();
            java.util.List<org.teamvan.moneyshift.encodings.AnnotationInfo> list7 = v7_1;
            if (list7 != null) items7.addAll(list7);
        }
        int itemCount7 = items7.size();
        long[] column8 = new long[itemCount7];
        for (int j7 = 0; j7 < itemCount7; j7++) {
            var v8_0 = items7.get(j7).id();
            long value8 = v8_0;
            column8[j7] = value8;
        }
        w.longs(path + "/class_description/keypoint_annotations/.items/id", column8);
        boolean[] present9 = null;
        boolean anyNull9 = false;
        for (int j7 = 0; j7 < itemCount7; j7++) {
            var v9_0 = items7.get(j7).label();
            boolean ok9 = (v9_0) != null;
            if (ok9) {
                if (present9 != null) present9[j7] = true;
            } else {
                if (present9 == null) {
                    present9 = new boolean[itemCount7];
                    java.util.Arrays.fill(present9, 0, j7, true);
                }
                anyNull9 = true;
            }
        }
        w.present(path + "/class_description/keypoint_annotations/.items/label", present9, anyNull9, itemCount7);
        java.lang.String[] column10 = new java.lang.String[itemCount7];
        for (int j7 = 0; j7 < itemCount7; j7++) {
            var v10_0 = items7.get(j7).label();
            var v10_1 = v10_0 == null ? null : v10_0.value();
            java.lang.String value10 = v10_1;
            column10[j7] = value10 == null ? "" : value10;
        }
        w.strings(path + "/class_description/keypoint_annotations/.items/label", column10);
        boolean[] present11 = null;
        boolean anyNull11 = false;
        for (int j7 = 0; j7 < itemCount7; j7++) {
            var v11_0 = items7.get(j7).color();
            boolean ok11 = (v11_0) != null;
            if (ok11) {
                if (present11 != null) present11[j7] = true;
            } else {
                if (present11 == null) {
                    present11 = new boolean[itemCount7];
                    java.util.Arrays.fill(present11, 0, j7, true);
                }
                anyNull11 = true;
            }
        }
        w.present(path + "/class_description/keypoint_annotations/.items/color", present11, anyNull11, itemCount7);
        long[] column12 = new long[itemCount7];
        for (int j7 = 0; j7 < itemCount7; j7++) {
            var v12_0 = items7.get(j7).color();
            var v12_1 = v12_0 == null ? null : v12_0.rgba();
            var value12 = v12_1;
            column12[j7] = value12 == null ? 0L : value12;
        }
        w.longs(path + "/class_description/keypoint_annotations/.items/color", column12);
        long[] counts13 = new long[n];
        int total13 = 0;
        for (int i = 0; i < n; i++) {
            var v13_0 = values.get(i) == null ? null : values.get(i).classDescription();
            var v13_1 = v13_0 == null ? null : v13_0.keypointConnections();
            java.util.List<org.teamvan.moneyshift.encodings.KeypointPair> list13 = v13_1;
            counts13[i] = list13 == null ? 0L : list13.size();
            total13 += (int) counts13[i];
        }
        w.longs(path + "/class_description/keypoint_connections" + "/.count", counts13);
        java.util.ArrayList<org.teamvan.moneyshift.encodings.KeypointPair> items13 = new java.util.ArrayList<>(total13);
        for (int i = 0; i < n; i++) {
            var v13_0 = values.get(i) == null ? null : values.get(i).classDescription();
            var v13_1 = v13_0 == null ? null : v13_0.keypointConnections();
            java.util.List<org.teamvan.moneyshift.encodings.KeypointPair> list13 = v13_1;
            if (list13 != null) items13.addAll(list13);
        }
        int itemCount13 = items13.size();
        long[] column14 = new long[itemCount13];
        for (int j13 = 0; j13 < itemCount13; j13++) {
            var v14_0 = items13.get(j13).keypoint0();
            var v14_1 = v14_0.id();
            long value14 = v14_1;
            column14[j13] = value14;
        }
        w.longs(path + "/class_description/keypoint_connections/.items/keypoint0", column14);
        long[] column15 = new long[itemCount13];
        for (int j13 = 0; j13 < itemCount13; j13++) {
            var v15_0 = items13.get(j13).keypoint1();
            var v15_1 = v15_0.id();
            long value15 = v15_1;
            column15[j13] = value15;
        }
        w.longs(path + "/class_description/keypoint_connections/.items/keypoint1", column15);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ClassDescriptionMapElem> values) {
        writeColumnsStatic(w, path, values);
    }
}
