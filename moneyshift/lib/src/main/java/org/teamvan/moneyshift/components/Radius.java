// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The radius of something, e.g. a point.
 * 
 * Internally, positive values indicate scene units, whereas negative values
 * are interpreted as UI points.
 * 
 * UI points are independent of zooming in Views, but are sensitive to the application UI scaling.
 * at 100% UI scaling, UI points are equal to pixels
 * The Viewer's UI scaling defaults to the OS scaling which typically is 100% for full HD screens and 200% for 4k screens.
 */
public record Radius(
    org.teamvan.moneyshift.encodings.Float32 value
) implements org.teamvan.moneyshift.NtComponent<Radius> {
    public Radius {
        java.util.Objects.requireNonNull(value, "value");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Radius(float value) {
        this(new org.teamvan.moneyshift.encodings.Float32(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Radius";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Radius> values) {
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
        double[] column1 = new double[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).value();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Radius> values) {
        writeColumnsStatic(w, path, values);
    }
}
