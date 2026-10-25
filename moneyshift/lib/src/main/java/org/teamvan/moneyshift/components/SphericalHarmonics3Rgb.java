// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * View-dependent color, expressed as spherical harmonics coefficients of degrees 1 through 3.
 * 
 * The view-independent (degree-0) base color is represented as a separate {@link org.teamvan.moneyshift.components.Color rerun::components::Color}.
 */
public record SphericalHarmonics3Rgb(
    org.teamvan.moneyshift.encodings.SphericalHarmonics3Rgb coefficients
) implements org.teamvan.moneyshift.NtComponent<SphericalHarmonics3Rgb> {
    public SphericalHarmonics3Rgb {
        java.util.Objects.requireNonNull(coefficients, "coefficients");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public SphericalHarmonics3Rgb(float[][] coefficients) {
        this(new org.teamvan.moneyshift.encodings.SphericalHarmonics3Rgb(coefficients));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.SphericalHarmonics3Rgb";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<SphericalHarmonics3Rgb> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).coefficients();
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
            var v1_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v1_1 = v1_0 == null ? null : v1_0.coefficients();
            var v1_2 = v1_1 == null ? null : v1_1[0];
            var v1_3 = v1_2 == null ? null : v1_2[0];
            var value1 = v1_3;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path + "/0/0", column1);
        double[] column2 = new double[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v2_1 = v2_0 == null ? null : v2_0.coefficients();
            var v2_2 = v2_1 == null ? null : v2_1[0];
            var v2_3 = v2_2 == null ? null : v2_2[1];
            var value2 = v2_3;
            column2[i] = value2 == null ? 0.0 : value2;
        }
        w.doubles(path + "/0/1", column2);
        double[] column3 = new double[n];
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v3_1 = v3_0 == null ? null : v3_0.coefficients();
            var v3_2 = v3_1 == null ? null : v3_1[0];
            var v3_3 = v3_2 == null ? null : v3_2[2];
            var value3 = v3_3;
            column3[i] = value3 == null ? 0.0 : value3;
        }
        w.doubles(path + "/0/2", column3);
        double[] column4 = new double[n];
        for (int i = 0; i < n; i++) {
            var v4_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v4_1 = v4_0 == null ? null : v4_0.coefficients();
            var v4_2 = v4_1 == null ? null : v4_1[1];
            var v4_3 = v4_2 == null ? null : v4_2[0];
            var value4 = v4_3;
            column4[i] = value4 == null ? 0.0 : value4;
        }
        w.doubles(path + "/1/0", column4);
        double[] column5 = new double[n];
        for (int i = 0; i < n; i++) {
            var v5_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v5_1 = v5_0 == null ? null : v5_0.coefficients();
            var v5_2 = v5_1 == null ? null : v5_1[1];
            var v5_3 = v5_2 == null ? null : v5_2[1];
            var value5 = v5_3;
            column5[i] = value5 == null ? 0.0 : value5;
        }
        w.doubles(path + "/1/1", column5);
        double[] column6 = new double[n];
        for (int i = 0; i < n; i++) {
            var v6_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v6_1 = v6_0 == null ? null : v6_0.coefficients();
            var v6_2 = v6_1 == null ? null : v6_1[1];
            var v6_3 = v6_2 == null ? null : v6_2[2];
            var value6 = v6_3;
            column6[i] = value6 == null ? 0.0 : value6;
        }
        w.doubles(path + "/1/2", column6);
        double[] column7 = new double[n];
        for (int i = 0; i < n; i++) {
            var v7_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v7_1 = v7_0 == null ? null : v7_0.coefficients();
            var v7_2 = v7_1 == null ? null : v7_1[2];
            var v7_3 = v7_2 == null ? null : v7_2[0];
            var value7 = v7_3;
            column7[i] = value7 == null ? 0.0 : value7;
        }
        w.doubles(path + "/2/0", column7);
        double[] column8 = new double[n];
        for (int i = 0; i < n; i++) {
            var v8_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v8_1 = v8_0 == null ? null : v8_0.coefficients();
            var v8_2 = v8_1 == null ? null : v8_1[2];
            var v8_3 = v8_2 == null ? null : v8_2[1];
            var value8 = v8_3;
            column8[i] = value8 == null ? 0.0 : value8;
        }
        w.doubles(path + "/2/1", column8);
        double[] column9 = new double[n];
        for (int i = 0; i < n; i++) {
            var v9_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v9_1 = v9_0 == null ? null : v9_0.coefficients();
            var v9_2 = v9_1 == null ? null : v9_1[2];
            var v9_3 = v9_2 == null ? null : v9_2[2];
            var value9 = v9_3;
            column9[i] = value9 == null ? 0.0 : value9;
        }
        w.doubles(path + "/2/2", column9);
        double[] column10 = new double[n];
        for (int i = 0; i < n; i++) {
            var v10_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v10_1 = v10_0 == null ? null : v10_0.coefficients();
            var v10_2 = v10_1 == null ? null : v10_1[3];
            var v10_3 = v10_2 == null ? null : v10_2[0];
            var value10 = v10_3;
            column10[i] = value10 == null ? 0.0 : value10;
        }
        w.doubles(path + "/3/0", column10);
        double[] column11 = new double[n];
        for (int i = 0; i < n; i++) {
            var v11_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v11_1 = v11_0 == null ? null : v11_0.coefficients();
            var v11_2 = v11_1 == null ? null : v11_1[3];
            var v11_3 = v11_2 == null ? null : v11_2[1];
            var value11 = v11_3;
            column11[i] = value11 == null ? 0.0 : value11;
        }
        w.doubles(path + "/3/1", column11);
        double[] column12 = new double[n];
        for (int i = 0; i < n; i++) {
            var v12_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v12_1 = v12_0 == null ? null : v12_0.coefficients();
            var v12_2 = v12_1 == null ? null : v12_1[3];
            var v12_3 = v12_2 == null ? null : v12_2[2];
            var value12 = v12_3;
            column12[i] = value12 == null ? 0.0 : value12;
        }
        w.doubles(path + "/3/2", column12);
        double[] column13 = new double[n];
        for (int i = 0; i < n; i++) {
            var v13_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v13_1 = v13_0 == null ? null : v13_0.coefficients();
            var v13_2 = v13_1 == null ? null : v13_1[4];
            var v13_3 = v13_2 == null ? null : v13_2[0];
            var value13 = v13_3;
            column13[i] = value13 == null ? 0.0 : value13;
        }
        w.doubles(path + "/4/0", column13);
        double[] column14 = new double[n];
        for (int i = 0; i < n; i++) {
            var v14_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v14_1 = v14_0 == null ? null : v14_0.coefficients();
            var v14_2 = v14_1 == null ? null : v14_1[4];
            var v14_3 = v14_2 == null ? null : v14_2[1];
            var value14 = v14_3;
            column14[i] = value14 == null ? 0.0 : value14;
        }
        w.doubles(path + "/4/1", column14);
        double[] column15 = new double[n];
        for (int i = 0; i < n; i++) {
            var v15_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v15_1 = v15_0 == null ? null : v15_0.coefficients();
            var v15_2 = v15_1 == null ? null : v15_1[4];
            var v15_3 = v15_2 == null ? null : v15_2[2];
            var value15 = v15_3;
            column15[i] = value15 == null ? 0.0 : value15;
        }
        w.doubles(path + "/4/2", column15);
        double[] column16 = new double[n];
        for (int i = 0; i < n; i++) {
            var v16_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v16_1 = v16_0 == null ? null : v16_0.coefficients();
            var v16_2 = v16_1 == null ? null : v16_1[5];
            var v16_3 = v16_2 == null ? null : v16_2[0];
            var value16 = v16_3;
            column16[i] = value16 == null ? 0.0 : value16;
        }
        w.doubles(path + "/5/0", column16);
        double[] column17 = new double[n];
        for (int i = 0; i < n; i++) {
            var v17_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v17_1 = v17_0 == null ? null : v17_0.coefficients();
            var v17_2 = v17_1 == null ? null : v17_1[5];
            var v17_3 = v17_2 == null ? null : v17_2[1];
            var value17 = v17_3;
            column17[i] = value17 == null ? 0.0 : value17;
        }
        w.doubles(path + "/5/1", column17);
        double[] column18 = new double[n];
        for (int i = 0; i < n; i++) {
            var v18_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v18_1 = v18_0 == null ? null : v18_0.coefficients();
            var v18_2 = v18_1 == null ? null : v18_1[5];
            var v18_3 = v18_2 == null ? null : v18_2[2];
            var value18 = v18_3;
            column18[i] = value18 == null ? 0.0 : value18;
        }
        w.doubles(path + "/5/2", column18);
        double[] column19 = new double[n];
        for (int i = 0; i < n; i++) {
            var v19_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v19_1 = v19_0 == null ? null : v19_0.coefficients();
            var v19_2 = v19_1 == null ? null : v19_1[6];
            var v19_3 = v19_2 == null ? null : v19_2[0];
            var value19 = v19_3;
            column19[i] = value19 == null ? 0.0 : value19;
        }
        w.doubles(path + "/6/0", column19);
        double[] column20 = new double[n];
        for (int i = 0; i < n; i++) {
            var v20_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v20_1 = v20_0 == null ? null : v20_0.coefficients();
            var v20_2 = v20_1 == null ? null : v20_1[6];
            var v20_3 = v20_2 == null ? null : v20_2[1];
            var value20 = v20_3;
            column20[i] = value20 == null ? 0.0 : value20;
        }
        w.doubles(path + "/6/1", column20);
        double[] column21 = new double[n];
        for (int i = 0; i < n; i++) {
            var v21_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v21_1 = v21_0 == null ? null : v21_0.coefficients();
            var v21_2 = v21_1 == null ? null : v21_1[6];
            var v21_3 = v21_2 == null ? null : v21_2[2];
            var value21 = v21_3;
            column21[i] = value21 == null ? 0.0 : value21;
        }
        w.doubles(path + "/6/2", column21);
        double[] column22 = new double[n];
        for (int i = 0; i < n; i++) {
            var v22_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v22_1 = v22_0 == null ? null : v22_0.coefficients();
            var v22_2 = v22_1 == null ? null : v22_1[7];
            var v22_3 = v22_2 == null ? null : v22_2[0];
            var value22 = v22_3;
            column22[i] = value22 == null ? 0.0 : value22;
        }
        w.doubles(path + "/7/0", column22);
        double[] column23 = new double[n];
        for (int i = 0; i < n; i++) {
            var v23_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v23_1 = v23_0 == null ? null : v23_0.coefficients();
            var v23_2 = v23_1 == null ? null : v23_1[7];
            var v23_3 = v23_2 == null ? null : v23_2[1];
            var value23 = v23_3;
            column23[i] = value23 == null ? 0.0 : value23;
        }
        w.doubles(path + "/7/1", column23);
        double[] column24 = new double[n];
        for (int i = 0; i < n; i++) {
            var v24_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v24_1 = v24_0 == null ? null : v24_0.coefficients();
            var v24_2 = v24_1 == null ? null : v24_1[7];
            var v24_3 = v24_2 == null ? null : v24_2[2];
            var value24 = v24_3;
            column24[i] = value24 == null ? 0.0 : value24;
        }
        w.doubles(path + "/7/2", column24);
        double[] column25 = new double[n];
        for (int i = 0; i < n; i++) {
            var v25_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v25_1 = v25_0 == null ? null : v25_0.coefficients();
            var v25_2 = v25_1 == null ? null : v25_1[8];
            var v25_3 = v25_2 == null ? null : v25_2[0];
            var value25 = v25_3;
            column25[i] = value25 == null ? 0.0 : value25;
        }
        w.doubles(path + "/8/0", column25);
        double[] column26 = new double[n];
        for (int i = 0; i < n; i++) {
            var v26_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v26_1 = v26_0 == null ? null : v26_0.coefficients();
            var v26_2 = v26_1 == null ? null : v26_1[8];
            var v26_3 = v26_2 == null ? null : v26_2[1];
            var value26 = v26_3;
            column26[i] = value26 == null ? 0.0 : value26;
        }
        w.doubles(path + "/8/1", column26);
        double[] column27 = new double[n];
        for (int i = 0; i < n; i++) {
            var v27_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v27_1 = v27_0 == null ? null : v27_0.coefficients();
            var v27_2 = v27_1 == null ? null : v27_1[8];
            var v27_3 = v27_2 == null ? null : v27_2[2];
            var value27 = v27_3;
            column27[i] = value27 == null ? 0.0 : value27;
        }
        w.doubles(path + "/8/2", column27);
        double[] column28 = new double[n];
        for (int i = 0; i < n; i++) {
            var v28_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v28_1 = v28_0 == null ? null : v28_0.coefficients();
            var v28_2 = v28_1 == null ? null : v28_1[9];
            var v28_3 = v28_2 == null ? null : v28_2[0];
            var value28 = v28_3;
            column28[i] = value28 == null ? 0.0 : value28;
        }
        w.doubles(path + "/9/0", column28);
        double[] column29 = new double[n];
        for (int i = 0; i < n; i++) {
            var v29_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v29_1 = v29_0 == null ? null : v29_0.coefficients();
            var v29_2 = v29_1 == null ? null : v29_1[9];
            var v29_3 = v29_2 == null ? null : v29_2[1];
            var value29 = v29_3;
            column29[i] = value29 == null ? 0.0 : value29;
        }
        w.doubles(path + "/9/1", column29);
        double[] column30 = new double[n];
        for (int i = 0; i < n; i++) {
            var v30_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v30_1 = v30_0 == null ? null : v30_0.coefficients();
            var v30_2 = v30_1 == null ? null : v30_1[9];
            var v30_3 = v30_2 == null ? null : v30_2[2];
            var value30 = v30_3;
            column30[i] = value30 == null ? 0.0 : value30;
        }
        w.doubles(path + "/9/2", column30);
        double[] column31 = new double[n];
        for (int i = 0; i < n; i++) {
            var v31_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v31_1 = v31_0 == null ? null : v31_0.coefficients();
            var v31_2 = v31_1 == null ? null : v31_1[10];
            var v31_3 = v31_2 == null ? null : v31_2[0];
            var value31 = v31_3;
            column31[i] = value31 == null ? 0.0 : value31;
        }
        w.doubles(path + "/10/0", column31);
        double[] column32 = new double[n];
        for (int i = 0; i < n; i++) {
            var v32_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v32_1 = v32_0 == null ? null : v32_0.coefficients();
            var v32_2 = v32_1 == null ? null : v32_1[10];
            var v32_3 = v32_2 == null ? null : v32_2[1];
            var value32 = v32_3;
            column32[i] = value32 == null ? 0.0 : value32;
        }
        w.doubles(path + "/10/1", column32);
        double[] column33 = new double[n];
        for (int i = 0; i < n; i++) {
            var v33_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v33_1 = v33_0 == null ? null : v33_0.coefficients();
            var v33_2 = v33_1 == null ? null : v33_1[10];
            var v33_3 = v33_2 == null ? null : v33_2[2];
            var value33 = v33_3;
            column33[i] = value33 == null ? 0.0 : value33;
        }
        w.doubles(path + "/10/2", column33);
        double[] column34 = new double[n];
        for (int i = 0; i < n; i++) {
            var v34_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v34_1 = v34_0 == null ? null : v34_0.coefficients();
            var v34_2 = v34_1 == null ? null : v34_1[11];
            var v34_3 = v34_2 == null ? null : v34_2[0];
            var value34 = v34_3;
            column34[i] = value34 == null ? 0.0 : value34;
        }
        w.doubles(path + "/11/0", column34);
        double[] column35 = new double[n];
        for (int i = 0; i < n; i++) {
            var v35_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v35_1 = v35_0 == null ? null : v35_0.coefficients();
            var v35_2 = v35_1 == null ? null : v35_1[11];
            var v35_3 = v35_2 == null ? null : v35_2[1];
            var value35 = v35_3;
            column35[i] = value35 == null ? 0.0 : value35;
        }
        w.doubles(path + "/11/1", column35);
        double[] column36 = new double[n];
        for (int i = 0; i < n; i++) {
            var v36_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v36_1 = v36_0 == null ? null : v36_0.coefficients();
            var v36_2 = v36_1 == null ? null : v36_1[11];
            var v36_3 = v36_2 == null ? null : v36_2[2];
            var value36 = v36_3;
            column36[i] = value36 == null ? 0.0 : value36;
        }
        w.doubles(path + "/11/2", column36);
        double[] column37 = new double[n];
        for (int i = 0; i < n; i++) {
            var v37_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v37_1 = v37_0 == null ? null : v37_0.coefficients();
            var v37_2 = v37_1 == null ? null : v37_1[12];
            var v37_3 = v37_2 == null ? null : v37_2[0];
            var value37 = v37_3;
            column37[i] = value37 == null ? 0.0 : value37;
        }
        w.doubles(path + "/12/0", column37);
        double[] column38 = new double[n];
        for (int i = 0; i < n; i++) {
            var v38_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v38_1 = v38_0 == null ? null : v38_0.coefficients();
            var v38_2 = v38_1 == null ? null : v38_1[12];
            var v38_3 = v38_2 == null ? null : v38_2[1];
            var value38 = v38_3;
            column38[i] = value38 == null ? 0.0 : value38;
        }
        w.doubles(path + "/12/1", column38);
        double[] column39 = new double[n];
        for (int i = 0; i < n; i++) {
            var v39_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v39_1 = v39_0 == null ? null : v39_0.coefficients();
            var v39_2 = v39_1 == null ? null : v39_1[12];
            var v39_3 = v39_2 == null ? null : v39_2[2];
            var value39 = v39_3;
            column39[i] = value39 == null ? 0.0 : value39;
        }
        w.doubles(path + "/12/2", column39);
        double[] column40 = new double[n];
        for (int i = 0; i < n; i++) {
            var v40_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v40_1 = v40_0 == null ? null : v40_0.coefficients();
            var v40_2 = v40_1 == null ? null : v40_1[13];
            var v40_3 = v40_2 == null ? null : v40_2[0];
            var value40 = v40_3;
            column40[i] = value40 == null ? 0.0 : value40;
        }
        w.doubles(path + "/13/0", column40);
        double[] column41 = new double[n];
        for (int i = 0; i < n; i++) {
            var v41_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v41_1 = v41_0 == null ? null : v41_0.coefficients();
            var v41_2 = v41_1 == null ? null : v41_1[13];
            var v41_3 = v41_2 == null ? null : v41_2[1];
            var value41 = v41_3;
            column41[i] = value41 == null ? 0.0 : value41;
        }
        w.doubles(path + "/13/1", column41);
        double[] column42 = new double[n];
        for (int i = 0; i < n; i++) {
            var v42_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v42_1 = v42_0 == null ? null : v42_0.coefficients();
            var v42_2 = v42_1 == null ? null : v42_1[13];
            var v42_3 = v42_2 == null ? null : v42_2[2];
            var value42 = v42_3;
            column42[i] = value42 == null ? 0.0 : value42;
        }
        w.doubles(path + "/13/2", column42);
        double[] column43 = new double[n];
        for (int i = 0; i < n; i++) {
            var v43_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v43_1 = v43_0 == null ? null : v43_0.coefficients();
            var v43_2 = v43_1 == null ? null : v43_1[14];
            var v43_3 = v43_2 == null ? null : v43_2[0];
            var value43 = v43_3;
            column43[i] = value43 == null ? 0.0 : value43;
        }
        w.doubles(path + "/14/0", column43);
        double[] column44 = new double[n];
        for (int i = 0; i < n; i++) {
            var v44_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v44_1 = v44_0 == null ? null : v44_0.coefficients();
            var v44_2 = v44_1 == null ? null : v44_1[14];
            var v44_3 = v44_2 == null ? null : v44_2[1];
            var value44 = v44_3;
            column44[i] = value44 == null ? 0.0 : value44;
        }
        w.doubles(path + "/14/1", column44);
        double[] column45 = new double[n];
        for (int i = 0; i < n; i++) {
            var v45_0 = values.get(i) == null ? null : values.get(i).coefficients();
            var v45_1 = v45_0 == null ? null : v45_0.coefficients();
            var v45_2 = v45_1 == null ? null : v45_1[14];
            var v45_3 = v45_2 == null ? null : v45_2[2];
            var value45 = v45_3;
            column45[i] = value45 == null ? 0.0 : value45;
        }
        w.doubles(path + "/14/2", column45);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<SphericalHarmonics3Rgb> values) {
        writeColumnsStatic(w, path, values);
    }
}
