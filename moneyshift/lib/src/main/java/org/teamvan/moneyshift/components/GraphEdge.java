// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * An edge in a graph connecting two nodes.
 */
public record GraphEdge(
    org.teamvan.moneyshift.encodings.Utf8Pair edge
) implements org.teamvan.moneyshift.NtComponent<GraphEdge> {
    public GraphEdge {
        java.util.Objects.requireNonNull(edge, "edge");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public GraphEdge(java.lang.String first, java.lang.String second) {
        this(new org.teamvan.moneyshift.encodings.Utf8Pair(new org.teamvan.moneyshift.encodings.Utf8(first), new org.teamvan.moneyshift.encodings.Utf8(second)));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.GraphEdge";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<GraphEdge> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).edge();
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
            var v1_0 = values.get(i) == null ? null : values.get(i).edge();
            var v1_1 = v1_0 == null ? null : v1_0.first();
            var v1_2 = v1_1 == null ? null : v1_1.value();
            java.lang.String value1 = v1_2;
            column1[i] = value1 == null ? "" : value1;
        }
        w.strings(path + "/first", column1);
        java.lang.String[] column2 = new java.lang.String[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).edge();
            var v2_1 = v2_0 == null ? null : v2_0.second();
            var v2_2 = v2_1 == null ? null : v2_1.value();
            java.lang.String value2 = v2_2;
            column2[i] = value2 == null ? "" : value2;
        }
        w.strings(path + "/second", column2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<GraphEdge> values) {
        writeColumnsStatic(w, path, values);
    }
}
