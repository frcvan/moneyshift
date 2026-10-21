// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Spherical harmonics coefficients of degrees 1 through 3 for RGB, as 15 half-precision RGB triples.
 * 
 * The coefficients are stored coefficient-major: {@code [c1.rgb, c2.rgb, …, c15.rgb]}.
 * The 15 coefficients {@code c1…c15} are ordered by ascending degree {@code l = 1, 2, 3}, and within
 * each degree by ascending order {@code m = -l … +l}:
 * degree 1 is {@code c1…c3}, degree 2 is {@code c4…c8}, and degree 3 is {@code c9…c15}.
 * The degrees are not exposed as separate groups, since each degree has a different
 * number of coefficients (3, 5, and 7).
 * 
 * This per-coefficient order matches the {@code f_rest_*} properties of the PLY files used by
 * <a href="https://repo-sam.inria.fr/fungraph/3d-gaussian-splatting/">3D Gaussian Splatting</a> (Kerbl et al., 2023),
 * but those store the channels channel-major (all 15 coefficients of R, then G, then B),
 * so they must be transposed on import.
 * 
 * The degree-0 (DC) term is *not* included — it is represented as a {@link org.teamvan.moneyshift.encodings.Rgba32} color instead.
 * 
 * Data of a lower spherical harmonics degree should be zero-padded,
 * which represents the exact same function (the spherical harmonics basis is orthonormal).
 * Conversely, truncating trailing coefficients at the degree boundaries (3 and 8 triples)
 * yields the optimal least-squares approximation of that lower degree.
 */
public record SphericalHarmonics3Rgb(
    /**
     * Spherical harmonics coefficients of degrees 1 through 3, coefficient-major.
     */
    float[][] coefficients
) {
    public SphericalHarmonics3Rgb {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof SphericalHarmonics3Rgb that)) return false;
        return java.util.Arrays.equals(coefficients, that.coefficients);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(coefficients));
    }
    public static final java.lang.String NAME = "rerun.encodings.SphericalHarmonics3Rgb";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(15);
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(15), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(3), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.FLOAT16, java.util.List.of())))));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<SphericalHarmonics3Rgb> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            SphericalHarmonics3Rgb value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, SphericalHarmonics3Rgb value) {
        {
            org.apache.arrow.vector.complex.FixedSizeListVector list0 = (org.apache.arrow.vector.complex.FixedSizeListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list1 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start1 = list1.startNewValue(start0 + 0);
                org.apache.arrow.vector.FieldVector child1 = list1.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child1).setSafe(start1 + 0, java.lang.Float.floatToFloat16(value.coefficients()[0][0]));
                ((org.apache.arrow.vector.Float2Vector) child1).setSafe(start1 + 1, java.lang.Float.floatToFloat16(value.coefficients()[0][1]));
                ((org.apache.arrow.vector.Float2Vector) child1).setSafe(start1 + 2, java.lang.Float.floatToFloat16(value.coefficients()[0][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list2 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start2 = list2.startNewValue(start0 + 1);
                org.apache.arrow.vector.FieldVector child2 = list2.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child2).setSafe(start2 + 0, java.lang.Float.floatToFloat16(value.coefficients()[1][0]));
                ((org.apache.arrow.vector.Float2Vector) child2).setSafe(start2 + 1, java.lang.Float.floatToFloat16(value.coefficients()[1][1]));
                ((org.apache.arrow.vector.Float2Vector) child2).setSafe(start2 + 2, java.lang.Float.floatToFloat16(value.coefficients()[1][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list3 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start3 = list3.startNewValue(start0 + 2);
                org.apache.arrow.vector.FieldVector child3 = list3.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child3).setSafe(start3 + 0, java.lang.Float.floatToFloat16(value.coefficients()[2][0]));
                ((org.apache.arrow.vector.Float2Vector) child3).setSafe(start3 + 1, java.lang.Float.floatToFloat16(value.coefficients()[2][1]));
                ((org.apache.arrow.vector.Float2Vector) child3).setSafe(start3 + 2, java.lang.Float.floatToFloat16(value.coefficients()[2][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list4 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start4 = list4.startNewValue(start0 + 3);
                org.apache.arrow.vector.FieldVector child4 = list4.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child4).setSafe(start4 + 0, java.lang.Float.floatToFloat16(value.coefficients()[3][0]));
                ((org.apache.arrow.vector.Float2Vector) child4).setSafe(start4 + 1, java.lang.Float.floatToFloat16(value.coefficients()[3][1]));
                ((org.apache.arrow.vector.Float2Vector) child4).setSafe(start4 + 2, java.lang.Float.floatToFloat16(value.coefficients()[3][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list5 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start5 = list5.startNewValue(start0 + 4);
                org.apache.arrow.vector.FieldVector child5 = list5.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child5).setSafe(start5 + 0, java.lang.Float.floatToFloat16(value.coefficients()[4][0]));
                ((org.apache.arrow.vector.Float2Vector) child5).setSafe(start5 + 1, java.lang.Float.floatToFloat16(value.coefficients()[4][1]));
                ((org.apache.arrow.vector.Float2Vector) child5).setSafe(start5 + 2, java.lang.Float.floatToFloat16(value.coefficients()[4][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list6 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start6 = list6.startNewValue(start0 + 5);
                org.apache.arrow.vector.FieldVector child6 = list6.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child6).setSafe(start6 + 0, java.lang.Float.floatToFloat16(value.coefficients()[5][0]));
                ((org.apache.arrow.vector.Float2Vector) child6).setSafe(start6 + 1, java.lang.Float.floatToFloat16(value.coefficients()[5][1]));
                ((org.apache.arrow.vector.Float2Vector) child6).setSafe(start6 + 2, java.lang.Float.floatToFloat16(value.coefficients()[5][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list7 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start7 = list7.startNewValue(start0 + 6);
                org.apache.arrow.vector.FieldVector child7 = list7.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child7).setSafe(start7 + 0, java.lang.Float.floatToFloat16(value.coefficients()[6][0]));
                ((org.apache.arrow.vector.Float2Vector) child7).setSafe(start7 + 1, java.lang.Float.floatToFloat16(value.coefficients()[6][1]));
                ((org.apache.arrow.vector.Float2Vector) child7).setSafe(start7 + 2, java.lang.Float.floatToFloat16(value.coefficients()[6][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list8 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start8 = list8.startNewValue(start0 + 7);
                org.apache.arrow.vector.FieldVector child8 = list8.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child8).setSafe(start8 + 0, java.lang.Float.floatToFloat16(value.coefficients()[7][0]));
                ((org.apache.arrow.vector.Float2Vector) child8).setSafe(start8 + 1, java.lang.Float.floatToFloat16(value.coefficients()[7][1]));
                ((org.apache.arrow.vector.Float2Vector) child8).setSafe(start8 + 2, java.lang.Float.floatToFloat16(value.coefficients()[7][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list9 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start9 = list9.startNewValue(start0 + 8);
                org.apache.arrow.vector.FieldVector child9 = list9.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child9).setSafe(start9 + 0, java.lang.Float.floatToFloat16(value.coefficients()[8][0]));
                ((org.apache.arrow.vector.Float2Vector) child9).setSafe(start9 + 1, java.lang.Float.floatToFloat16(value.coefficients()[8][1]));
                ((org.apache.arrow.vector.Float2Vector) child9).setSafe(start9 + 2, java.lang.Float.floatToFloat16(value.coefficients()[8][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list10 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start10 = list10.startNewValue(start0 + 9);
                org.apache.arrow.vector.FieldVector child10 = list10.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child10).setSafe(start10 + 0, java.lang.Float.floatToFloat16(value.coefficients()[9][0]));
                ((org.apache.arrow.vector.Float2Vector) child10).setSafe(start10 + 1, java.lang.Float.floatToFloat16(value.coefficients()[9][1]));
                ((org.apache.arrow.vector.Float2Vector) child10).setSafe(start10 + 2, java.lang.Float.floatToFloat16(value.coefficients()[9][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list11 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start11 = list11.startNewValue(start0 + 10);
                org.apache.arrow.vector.FieldVector child11 = list11.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child11).setSafe(start11 + 0, java.lang.Float.floatToFloat16(value.coefficients()[10][0]));
                ((org.apache.arrow.vector.Float2Vector) child11).setSafe(start11 + 1, java.lang.Float.floatToFloat16(value.coefficients()[10][1]));
                ((org.apache.arrow.vector.Float2Vector) child11).setSafe(start11 + 2, java.lang.Float.floatToFloat16(value.coefficients()[10][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list12 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start12 = list12.startNewValue(start0 + 11);
                org.apache.arrow.vector.FieldVector child12 = list12.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child12).setSafe(start12 + 0, java.lang.Float.floatToFloat16(value.coefficients()[11][0]));
                ((org.apache.arrow.vector.Float2Vector) child12).setSafe(start12 + 1, java.lang.Float.floatToFloat16(value.coefficients()[11][1]));
                ((org.apache.arrow.vector.Float2Vector) child12).setSafe(start12 + 2, java.lang.Float.floatToFloat16(value.coefficients()[11][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list13 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start13 = list13.startNewValue(start0 + 12);
                org.apache.arrow.vector.FieldVector child13 = list13.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child13).setSafe(start13 + 0, java.lang.Float.floatToFloat16(value.coefficients()[12][0]));
                ((org.apache.arrow.vector.Float2Vector) child13).setSafe(start13 + 1, java.lang.Float.floatToFloat16(value.coefficients()[12][1]));
                ((org.apache.arrow.vector.Float2Vector) child13).setSafe(start13 + 2, java.lang.Float.floatToFloat16(value.coefficients()[12][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list14 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start14 = list14.startNewValue(start0 + 13);
                org.apache.arrow.vector.FieldVector child14 = list14.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child14).setSafe(start14 + 0, java.lang.Float.floatToFloat16(value.coefficients()[13][0]));
                ((org.apache.arrow.vector.Float2Vector) child14).setSafe(start14 + 1, java.lang.Float.floatToFloat16(value.coefficients()[13][1]));
                ((org.apache.arrow.vector.Float2Vector) child14).setSafe(start14 + 2, java.lang.Float.floatToFloat16(value.coefficients()[13][2]));
            }
            {
                org.apache.arrow.vector.complex.FixedSizeListVector list15 = (org.apache.arrow.vector.complex.FixedSizeListVector) child0;
                int start15 = list15.startNewValue(start0 + 14);
                org.apache.arrow.vector.FieldVector child15 = list15.getDataVector();
                ((org.apache.arrow.vector.Float2Vector) child15).setSafe(start15 + 0, java.lang.Float.floatToFloat16(value.coefficients()[14][0]));
                ((org.apache.arrow.vector.Float2Vector) child15).setSafe(start15 + 1, java.lang.Float.floatToFloat16(value.coefficients()[14][1]));
                ((org.apache.arrow.vector.Float2Vector) child15).setSafe(start15 + 2, java.lang.Float.floatToFloat16(value.coefficients()[14][2]));
            }
        }
    }
}
