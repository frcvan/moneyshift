// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Whether a record is flagged, e.g. for review.
 * 
 * The Viewer shows it as a flag button.
 * Table card layouts show the first flag field in the card header.
 */
public record Flag(
    org.teamvan.moneyshift.encodings.Bool flagged
) implements org.teamvan.moneyshift.NtComponent<Flag> {
    public Flag {
        java.util.Objects.requireNonNull(flagged, "flagged");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Flag(boolean value) {
        this(new org.teamvan.moneyshift.encodings.Bool(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Flag";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Flag> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_root = values.get(i);
            var v0_0 = v0_root == null ? null : v0_root.flagged();
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
        boolean[] column1 = new boolean[n];
        for (int i = 0; i < n; i++) {
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.flagged();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? false : value1;
        }
        w.booleans(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Flag> values) {
        writeColumnsStatic(w, path, values);
    }
}
