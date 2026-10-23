// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The annotation context provides additional information on how to display entities.
 * 
 * Entities can use {@link org.teamvan.moneyshift.encodings.ClassId rerun::encodings::ClassId}s and {@link org.teamvan.moneyshift.encodings.KeypointId rerun::encodings::KeypointId}s to provide annotations, and
 * the labels and colors will be looked up in the appropriate
 * annotation context. We use the *first* annotation context we find in the
 * path-hierarchy when searching up through the ancestors of a given entity
 * path.
 */
public record AnnotationContext(
    /**
     * List of class descriptions, mapping class indices to class names, colors etc.
     */
    java.util.List<org.teamvan.moneyshift.encodings.ClassDescriptionMapElem> classMap
) implements org.teamvan.moneyshift.NtComponent<AnnotationContext> {
    public AnnotationContext {
        classMap = java.util.List.copyOf(classMap);
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.AnnotationContext";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<AnnotationContext> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).classMap();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        long[] counts1 = new long[n];
        int total1 = 0;
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).classMap();
            java.util.List<org.teamvan.moneyshift.encodings.ClassDescriptionMapElem> list1 = v1_0;
            counts1[i] = list1 == null ? 0L : list1.size();
            total1 += (int) counts1[i];
        }
        w.longs(path + "/.count", counts1);
        java.util.ArrayList<org.teamvan.moneyshift.encodings.ClassDescriptionMapElem> items1 = new java.util.ArrayList<>(total1);
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).classMap();
            java.util.List<org.teamvan.moneyshift.encodings.ClassDescriptionMapElem> list1 = v1_0;
            if (list1 != null) items1.addAll(list1);
        }
        int itemCount1 = items1.size();
        long[] column2 = new long[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v2_0 = items1.get(j1).classId();
            var v2_1 = v2_0.id();
            long value2 = v2_1;
            column2[j1] = value2;
        }
        w.longs(path + "/.items/class_id", column2);
        long[] column3 = new long[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v3_0 = items1.get(j1).classDescription();
            var v3_1 = v3_0.info();
            var v3_2 = v3_1.id();
            long value3 = v3_2;
            column3[j1] = value3;
        }
        w.longs(path + "/.items/class_description/info/id", column3);
        boolean[] present4 = new boolean[itemCount1];
        boolean anyNull4 = false;
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v4_0 = items1.get(j1).classDescription();
            var v4_1 = v4_0.info();
            var v4_2 = v4_1.label();
            boolean ok4 = (v4_2) != null;
            present4[j1] = ok4;
            anyNull4 |= !ok4;
        }
        w.present(path + "/.items/class_description/info/label", present4, anyNull4);
        java.lang.String[] column5 = new java.lang.String[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v5_0 = items1.get(j1).classDescription();
            var v5_1 = v5_0.info();
            var v5_2 = v5_1.label();
            var v5_3 = v5_2 == null ? null : v5_2.value();
            java.lang.String value5 = v5_3;
            column5[j1] = value5 == null ? "" : value5;
        }
        w.strings(path + "/.items/class_description/info/label", column5);
        boolean[] present6 = new boolean[itemCount1];
        boolean anyNull6 = false;
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v6_0 = items1.get(j1).classDescription();
            var v6_1 = v6_0.info();
            var v6_2 = v6_1.color();
            boolean ok6 = (v6_2) != null;
            present6[j1] = ok6;
            anyNull6 |= !ok6;
        }
        w.present(path + "/.items/class_description/info/color", present6, anyNull6);
        long[] column7 = new long[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v7_0 = items1.get(j1).classDescription();
            var v7_1 = v7_0.info();
            var v7_2 = v7_1.color();
            var v7_3 = v7_2 == null ? null : v7_2.rgba();
            var value7 = v7_3;
            column7[j1] = value7 == null ? 0L : value7;
        }
        w.longs(path + "/.items/class_description/info/color", column7);
        long[] counts8 = new long[itemCount1];
        int total8 = 0;
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v8_0 = items1.get(j1).classDescription();
            var v8_1 = v8_0.keypointAnnotations();
            java.util.List<org.teamvan.moneyshift.encodings.AnnotationInfo> list8 = v8_1;
            counts8[j1] = list8 == null ? 0L : list8.size();
            total8 += (int) counts8[j1];
        }
        w.longs(path + "/.items/class_description/keypoint_annotations" + "/.count", counts8);
        java.util.ArrayList<org.teamvan.moneyshift.encodings.AnnotationInfo> items8 = new java.util.ArrayList<>(total8);
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v8_0 = items1.get(j1).classDescription();
            var v8_1 = v8_0.keypointAnnotations();
            java.util.List<org.teamvan.moneyshift.encodings.AnnotationInfo> list8 = v8_1;
            if (list8 != null) items8.addAll(list8);
        }
        int itemCount8 = items8.size();
        long[] column9 = new long[itemCount8];
        for (int j8 = 0; j8 < itemCount8; j8++) {
            var v9_0 = items8.get(j8).id();
            long value9 = v9_0;
            column9[j8] = value9;
        }
        w.longs(path + "/.items/class_description/keypoint_annotations/.items/id", column9);
        boolean[] present10 = new boolean[itemCount8];
        boolean anyNull10 = false;
        for (int j8 = 0; j8 < itemCount8; j8++) {
            var v10_0 = items8.get(j8).label();
            boolean ok10 = (v10_0) != null;
            present10[j8] = ok10;
            anyNull10 |= !ok10;
        }
        w.present(path + "/.items/class_description/keypoint_annotations/.items/label", present10, anyNull10);
        java.lang.String[] column11 = new java.lang.String[itemCount8];
        for (int j8 = 0; j8 < itemCount8; j8++) {
            var v11_0 = items8.get(j8).label();
            var v11_1 = v11_0 == null ? null : v11_0.value();
            java.lang.String value11 = v11_1;
            column11[j8] = value11 == null ? "" : value11;
        }
        w.strings(path + "/.items/class_description/keypoint_annotations/.items/label", column11);
        boolean[] present12 = new boolean[itemCount8];
        boolean anyNull12 = false;
        for (int j8 = 0; j8 < itemCount8; j8++) {
            var v12_0 = items8.get(j8).color();
            boolean ok12 = (v12_0) != null;
            present12[j8] = ok12;
            anyNull12 |= !ok12;
        }
        w.present(path + "/.items/class_description/keypoint_annotations/.items/color", present12, anyNull12);
        long[] column13 = new long[itemCount8];
        for (int j8 = 0; j8 < itemCount8; j8++) {
            var v13_0 = items8.get(j8).color();
            var v13_1 = v13_0 == null ? null : v13_0.rgba();
            var value13 = v13_1;
            column13[j8] = value13 == null ? 0L : value13;
        }
        w.longs(path + "/.items/class_description/keypoint_annotations/.items/color", column13);
        long[] counts14 = new long[itemCount1];
        int total14 = 0;
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v14_0 = items1.get(j1).classDescription();
            var v14_1 = v14_0.keypointConnections();
            java.util.List<org.teamvan.moneyshift.encodings.KeypointPair> list14 = v14_1;
            counts14[j1] = list14 == null ? 0L : list14.size();
            total14 += (int) counts14[j1];
        }
        w.longs(path + "/.items/class_description/keypoint_connections" + "/.count", counts14);
        java.util.ArrayList<org.teamvan.moneyshift.encodings.KeypointPair> items14 = new java.util.ArrayList<>(total14);
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v14_0 = items1.get(j1).classDescription();
            var v14_1 = v14_0.keypointConnections();
            java.util.List<org.teamvan.moneyshift.encodings.KeypointPair> list14 = v14_1;
            if (list14 != null) items14.addAll(list14);
        }
        int itemCount14 = items14.size();
        long[] column15 = new long[itemCount14];
        for (int j14 = 0; j14 < itemCount14; j14++) {
            var v15_0 = items14.get(j14).keypoint0();
            var v15_1 = v15_0.id();
            long value15 = v15_1;
            column15[j14] = value15;
        }
        w.longs(path + "/.items/class_description/keypoint_connections/.items/keypoint0", column15);
        long[] column16 = new long[itemCount14];
        for (int j14 = 0; j14 < itemCount14; j14++) {
            var v16_0 = items14.get(j14).keypoint1();
            var v16_1 = v16_0.id();
            long value16 = v16_1;
            column16[j14] = value16;
        }
        w.longs(path + "/.items/class_description/keypoint_connections/.items/keypoint1", column16);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<AnnotationContext> values) {
        writeColumnsStatic(w, path, values);
    }
}
