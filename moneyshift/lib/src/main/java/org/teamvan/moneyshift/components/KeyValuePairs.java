// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A map of string keys to string values.
 * 
 * This component can be used to attach arbitrary metadata or annotations to entities.
 * Each key-value pair is stored as a UTF-8 string mapping.
 */
public record KeyValuePairs(
    /**
     * The key-value pairs that make up this string map.
     */
    java.util.List<org.teamvan.moneyshift.encodings.Utf8Pair> pairs
) implements org.teamvan.moneyshift.NtComponent<KeyValuePairs> {
    public KeyValuePairs {
        pairs = java.util.List.copyOf(pairs);
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.KeyValuePairs";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<KeyValuePairs> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).pairs();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        long[] counts1 = new long[n];
        int total1 = 0;
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).pairs();
            java.util.List<org.teamvan.moneyshift.encodings.Utf8Pair> list1 = v1_0;
            counts1[i] = list1 == null ? 0L : list1.size();
            total1 += (int) counts1[i];
        }
        w.longs(path + "/.count", counts1);
        java.util.ArrayList<org.teamvan.moneyshift.encodings.Utf8Pair> items1 = new java.util.ArrayList<>(total1);
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).pairs();
            java.util.List<org.teamvan.moneyshift.encodings.Utf8Pair> list1 = v1_0;
            if (list1 != null) items1.addAll(list1);
        }
        int itemCount1 = items1.size();
        java.lang.String[] column2 = new java.lang.String[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v2_0 = items1.get(j1).first();
            var v2_1 = v2_0.value();
            java.lang.String value2 = v2_1;
            column2[j1] = value2 == null ? "" : value2;
        }
        w.strings(path + "/.items/first", column2);
        java.lang.String[] column3 = new java.lang.String[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            var v3_0 = items1.get(j1).second();
            var v3_1 = v3_0.value();
            java.lang.String value3 = v3_1;
            column3[j1] = value3 == null ? "" : value3;
        }
        w.strings(path + "/.items/second", column3);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<KeyValuePairs> values) {
        writeColumnsStatic(w, path, values);
    }
}
