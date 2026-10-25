// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The highest spherical harmonics degree to evaluate when rendering, 0-3.
 * 
 * {@code 0} renders the view-independent base color only, and is the fastest.
 * Each higher degree brings in more view-dependent detail, at the cost of fetching and
 * evaluating more coefficients ({@link org.teamvan.moneyshift.components.SphericalHarmonics3Rgb rerun::components::SphericalHarmonics3Rgb}):
 * 3 of them for degree 1, 8 for degree 2, and all 15 for degree 3.
 * 
 * Lowering this in the blueprint can make the rendering a lot faster.
 * 
 * Defaults to 3, i.e. every coefficient the data has.
 */
public record SphericalHarmonicsDegree(
    org.teamvan.moneyshift.encodings.UInt32 degree
) implements org.teamvan.moneyshift.NtComponent<SphericalHarmonicsDegree> {
    public SphericalHarmonicsDegree {
        java.util.Objects.requireNonNull(degree, "degree");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public SphericalHarmonicsDegree(long value) {
        this(new org.teamvan.moneyshift.encodings.UInt32(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.SphericalHarmonicsDegree";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<SphericalHarmonicsDegree> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).degree();
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
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).degree();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<SphericalHarmonicsDegree> values) {
        writeColumnsStatic(w, path, values);
    }
}
