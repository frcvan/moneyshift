// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * An N-dimensional array of numbers.
 * 
 * The number of dimensions and their respective lengths is specified by the {@code shape} field.
 * The dimensions are ordered from outermost to innermost. For example, in the common case of
 * a 2D RGB Image, the shape would be {@code [height, width, channel]}.
 * 
 * These dimensions are combined with an index to look up values from the {@code buffer} field,
 * which stores a contiguous array of typed values.
 */
public record TensorData(
    org.teamvan.moneyshift.encodings.TensorData data
) implements org.teamvan.moneyshift.NtComponent<TensorData> {
    public TensorData {
        java.util.Objects.requireNonNull(data, "data");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public TensorData(java.util.List<java.lang.Long> shape, java.util.List<java.lang.String> names, org.teamvan.moneyshift.encodings.TensorBuffer buffer) {
        this(new org.teamvan.moneyshift.encodings.TensorData(shape, names, buffer));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.TensorData";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TensorData> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_root = values.get(i);
            var v0_0 = v0_root == null ? null : v0_root.data();
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
        long[] offsets1 = new long[n + 1];
        int total1 = 0;
        for (int i = 0; i < n; i++) {
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.data();
            var v1_1 = v1_0 == null ? null : v1_0.shape();
            java.util.List<java.lang.Long> list1 = v1_1;
            total1 += list1 == null ? 0 : list1.size();
            offsets1[i + 1] = total1;
        }
        w.longs(path + "/shape" + "/.offsets", offsets1);
        java.util.ArrayList<java.lang.Long> items1 = new java.util.ArrayList<>(total1);
        for (int i = 0; i < n; i++) {
            var v1_root = values.get(i);
            var v1_0 = v1_root == null ? null : v1_root.data();
            var v1_1 = v1_0 == null ? null : v1_0.shape();
            java.util.List<java.lang.Long> list1 = v1_1;
            if (list1 != null) items1.addAll(list1);
        }
        int itemCount1 = items1.size();
        long[] column2 = new long[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            long value2 = items1.get(j1);
            column2[j1] = value2;
        }
        w.longs(path + "/shape/.items", column2);
        boolean[] present3 = null;
        boolean anyNull3 = false;
        for (int i = 0; i < n; i++) {
            var v3_root = values.get(i);
            var v3_0 = v3_root == null ? null : v3_root.data();
            var v3_1 = v3_0 == null ? null : v3_0.names();
            boolean ok3 = (v3_1) != null;
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
        w.present(path + "/names", present3, anyNull3, n);
        long[] offsets4 = new long[n + 1];
        int total4 = 0;
        for (int i = 0; i < n; i++) {
            var v4_root = values.get(i);
            var v4_0 = v4_root == null ? null : v4_root.data();
            var v4_1 = v4_0 == null ? null : v4_0.names();
            java.util.List<java.lang.String> list4 = v4_1;
            total4 += list4 == null ? 0 : list4.size();
            offsets4[i + 1] = total4;
        }
        w.longs(path + "/names" + "/.offsets", offsets4);
        java.util.ArrayList<java.lang.String> items4 = new java.util.ArrayList<>(total4);
        for (int i = 0; i < n; i++) {
            var v4_root = values.get(i);
            var v4_0 = v4_root == null ? null : v4_root.data();
            var v4_1 = v4_0 == null ? null : v4_0.names();
            java.util.List<java.lang.String> list4 = v4_1;
            if (list4 != null) items4.addAll(list4);
        }
        int itemCount4 = items4.size();
        java.lang.String[] column5 = new java.lang.String[itemCount4];
        for (int j4 = 0; j4 < itemCount4; j4++) {
            java.lang.String value5 = items4.get(j4);
            column5[j4] = value5 == null ? "" : value5;
        }
        w.strings(path + "/names/.items", column5);
        long[] variants6 = new long[n];
        java.util.ArrayList<java.util.List<java.lang.Short>> payload6_0 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Integer>> payload6_1 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Long>> payload6_2 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Long>> payload6_3 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Byte>> payload6_4 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Short>> payload6_5 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Integer>> payload6_6 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Long>> payload6_7 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Float>> payload6_8 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Float>> payload6_9 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Double>> payload6_10 = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            var v6_root = values.get(i);
            var v6_0 = v6_root == null ? null : v6_root.data();
            var v6_1 = v6_0 == null ? null : v6_0.buffer();
            org.teamvan.moneyshift.encodings.TensorBuffer value6 = v6_1;
            if (value6 == null) {
                variants6[i] = -1L;
            } else if (value6 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.U8 variant6_0) {
                variants6[i] = 0L;
                payload6_0.add(variant6_0.value());
            } else if (value6 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.U16 variant6_1) {
                variants6[i] = 1L;
                payload6_1.add(variant6_1.value());
            } else if (value6 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.U32 variant6_2) {
                variants6[i] = 2L;
                payload6_2.add(variant6_2.value());
            } else if (value6 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.U64 variant6_3) {
                variants6[i] = 3L;
                payload6_3.add(variant6_3.value());
            } else if (value6 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.I8 variant6_4) {
                variants6[i] = 4L;
                payload6_4.add(variant6_4.value());
            } else if (value6 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.I16 variant6_5) {
                variants6[i] = 5L;
                payload6_5.add(variant6_5.value());
            } else if (value6 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.I32 variant6_6) {
                variants6[i] = 6L;
                payload6_6.add(variant6_6.value());
            } else if (value6 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.I64 variant6_7) {
                variants6[i] = 7L;
                payload6_7.add(variant6_7.value());
            } else if (value6 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.F16 variant6_8) {
                variants6[i] = 8L;
                payload6_8.add(variant6_8.value());
            } else if (value6 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.F32 variant6_9) {
                variants6[i] = 9L;
                payload6_9.add(variant6_9.value());
            } else if (value6 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.F64 variant6_10) {
                variants6[i] = 10L;
                payload6_10.add(variant6_10.value());
            } else {
                variants6[i] = -1L;
            }
        }
        w.longs(path + "/buffer" + "/.variant", variants6);
        if (!payload6_0.isEmpty()) {
            int payloadCount6_0 = payload6_0.size();
        long[] offsets7 = new long[payloadCount6_0 + 1];
        int total7 = 0;
        for (int k6_0 = 0; k6_0 < payloadCount6_0; k6_0++) {
            java.util.List<java.lang.Short> list7 = payload6_0.get(k6_0);
            total7 += list7 == null ? 0 : list7.size();
            offsets7[k6_0 + 1] = total7;
        }
        w.longs(path + "/buffer/U8" + "/.offsets", offsets7);
        java.util.ArrayList<java.lang.Short> items7 = new java.util.ArrayList<>(total7);
        for (int k6_0 = 0; k6_0 < payloadCount6_0; k6_0++) {
            java.util.List<java.lang.Short> list7 = payload6_0.get(k6_0);
            if (list7 != null) items7.addAll(list7);
        }
        int itemCount7 = items7.size();
        long[] column8 = new long[itemCount7];
        for (int j7 = 0; j7 < itemCount7; j7++) {
            long value8 = items7.get(j7);
            column8[j7] = value8;
        }
        w.longs(path + "/buffer/U8/.items", column8);
        }
        if (!payload6_1.isEmpty()) {
            int payloadCount6_1 = payload6_1.size();
        long[] offsets9 = new long[payloadCount6_1 + 1];
        int total9 = 0;
        for (int k6_1 = 0; k6_1 < payloadCount6_1; k6_1++) {
            java.util.List<java.lang.Integer> list9 = payload6_1.get(k6_1);
            total9 += list9 == null ? 0 : list9.size();
            offsets9[k6_1 + 1] = total9;
        }
        w.longs(path + "/buffer/U16" + "/.offsets", offsets9);
        java.util.ArrayList<java.lang.Integer> items9 = new java.util.ArrayList<>(total9);
        for (int k6_1 = 0; k6_1 < payloadCount6_1; k6_1++) {
            java.util.List<java.lang.Integer> list9 = payload6_1.get(k6_1);
            if (list9 != null) items9.addAll(list9);
        }
        int itemCount9 = items9.size();
        long[] column10 = new long[itemCount9];
        for (int j9 = 0; j9 < itemCount9; j9++) {
            long value10 = items9.get(j9);
            column10[j9] = value10;
        }
        w.longs(path + "/buffer/U16/.items", column10);
        }
        if (!payload6_2.isEmpty()) {
            int payloadCount6_2 = payload6_2.size();
        long[] offsets11 = new long[payloadCount6_2 + 1];
        int total11 = 0;
        for (int k6_2 = 0; k6_2 < payloadCount6_2; k6_2++) {
            java.util.List<java.lang.Long> list11 = payload6_2.get(k6_2);
            total11 += list11 == null ? 0 : list11.size();
            offsets11[k6_2 + 1] = total11;
        }
        w.longs(path + "/buffer/U32" + "/.offsets", offsets11);
        java.util.ArrayList<java.lang.Long> items11 = new java.util.ArrayList<>(total11);
        for (int k6_2 = 0; k6_2 < payloadCount6_2; k6_2++) {
            java.util.List<java.lang.Long> list11 = payload6_2.get(k6_2);
            if (list11 != null) items11.addAll(list11);
        }
        int itemCount11 = items11.size();
        long[] column12 = new long[itemCount11];
        for (int j11 = 0; j11 < itemCount11; j11++) {
            long value12 = items11.get(j11);
            column12[j11] = value12;
        }
        w.longs(path + "/buffer/U32/.items", column12);
        }
        if (!payload6_3.isEmpty()) {
            int payloadCount6_3 = payload6_3.size();
        long[] offsets13 = new long[payloadCount6_3 + 1];
        int total13 = 0;
        for (int k6_3 = 0; k6_3 < payloadCount6_3; k6_3++) {
            java.util.List<java.lang.Long> list13 = payload6_3.get(k6_3);
            total13 += list13 == null ? 0 : list13.size();
            offsets13[k6_3 + 1] = total13;
        }
        w.longs(path + "/buffer/U64" + "/.offsets", offsets13);
        java.util.ArrayList<java.lang.Long> items13 = new java.util.ArrayList<>(total13);
        for (int k6_3 = 0; k6_3 < payloadCount6_3; k6_3++) {
            java.util.List<java.lang.Long> list13 = payload6_3.get(k6_3);
            if (list13 != null) items13.addAll(list13);
        }
        int itemCount13 = items13.size();
        long[] column14 = new long[itemCount13];
        for (int j13 = 0; j13 < itemCount13; j13++) {
            long value14 = items13.get(j13);
            column14[j13] = value14;
        }
        w.longs(path + "/buffer/U64/.items", column14);
        }
        if (!payload6_4.isEmpty()) {
            int payloadCount6_4 = payload6_4.size();
        long[] offsets15 = new long[payloadCount6_4 + 1];
        int total15 = 0;
        for (int k6_4 = 0; k6_4 < payloadCount6_4; k6_4++) {
            java.util.List<java.lang.Byte> list15 = payload6_4.get(k6_4);
            total15 += list15 == null ? 0 : list15.size();
            offsets15[k6_4 + 1] = total15;
        }
        w.longs(path + "/buffer/I8" + "/.offsets", offsets15);
        java.util.ArrayList<java.lang.Byte> items15 = new java.util.ArrayList<>(total15);
        for (int k6_4 = 0; k6_4 < payloadCount6_4; k6_4++) {
            java.util.List<java.lang.Byte> list15 = payload6_4.get(k6_4);
            if (list15 != null) items15.addAll(list15);
        }
        int itemCount15 = items15.size();
        long[] column16 = new long[itemCount15];
        for (int j15 = 0; j15 < itemCount15; j15++) {
            long value16 = items15.get(j15);
            column16[j15] = value16;
        }
        w.longs(path + "/buffer/I8/.items", column16);
        }
        if (!payload6_5.isEmpty()) {
            int payloadCount6_5 = payload6_5.size();
        long[] offsets17 = new long[payloadCount6_5 + 1];
        int total17 = 0;
        for (int k6_5 = 0; k6_5 < payloadCount6_5; k6_5++) {
            java.util.List<java.lang.Short> list17 = payload6_5.get(k6_5);
            total17 += list17 == null ? 0 : list17.size();
            offsets17[k6_5 + 1] = total17;
        }
        w.longs(path + "/buffer/I16" + "/.offsets", offsets17);
        java.util.ArrayList<java.lang.Short> items17 = new java.util.ArrayList<>(total17);
        for (int k6_5 = 0; k6_5 < payloadCount6_5; k6_5++) {
            java.util.List<java.lang.Short> list17 = payload6_5.get(k6_5);
            if (list17 != null) items17.addAll(list17);
        }
        int itemCount17 = items17.size();
        long[] column18 = new long[itemCount17];
        for (int j17 = 0; j17 < itemCount17; j17++) {
            long value18 = items17.get(j17);
            column18[j17] = value18;
        }
        w.longs(path + "/buffer/I16/.items", column18);
        }
        if (!payload6_6.isEmpty()) {
            int payloadCount6_6 = payload6_6.size();
        long[] offsets19 = new long[payloadCount6_6 + 1];
        int total19 = 0;
        for (int k6_6 = 0; k6_6 < payloadCount6_6; k6_6++) {
            java.util.List<java.lang.Integer> list19 = payload6_6.get(k6_6);
            total19 += list19 == null ? 0 : list19.size();
            offsets19[k6_6 + 1] = total19;
        }
        w.longs(path + "/buffer/I32" + "/.offsets", offsets19);
        java.util.ArrayList<java.lang.Integer> items19 = new java.util.ArrayList<>(total19);
        for (int k6_6 = 0; k6_6 < payloadCount6_6; k6_6++) {
            java.util.List<java.lang.Integer> list19 = payload6_6.get(k6_6);
            if (list19 != null) items19.addAll(list19);
        }
        int itemCount19 = items19.size();
        long[] column20 = new long[itemCount19];
        for (int j19 = 0; j19 < itemCount19; j19++) {
            long value20 = items19.get(j19);
            column20[j19] = value20;
        }
        w.longs(path + "/buffer/I32/.items", column20);
        }
        if (!payload6_7.isEmpty()) {
            int payloadCount6_7 = payload6_7.size();
        long[] offsets21 = new long[payloadCount6_7 + 1];
        int total21 = 0;
        for (int k6_7 = 0; k6_7 < payloadCount6_7; k6_7++) {
            java.util.List<java.lang.Long> list21 = payload6_7.get(k6_7);
            total21 += list21 == null ? 0 : list21.size();
            offsets21[k6_7 + 1] = total21;
        }
        w.longs(path + "/buffer/I64" + "/.offsets", offsets21);
        java.util.ArrayList<java.lang.Long> items21 = new java.util.ArrayList<>(total21);
        for (int k6_7 = 0; k6_7 < payloadCount6_7; k6_7++) {
            java.util.List<java.lang.Long> list21 = payload6_7.get(k6_7);
            if (list21 != null) items21.addAll(list21);
        }
        int itemCount21 = items21.size();
        long[] column22 = new long[itemCount21];
        for (int j21 = 0; j21 < itemCount21; j21++) {
            long value22 = items21.get(j21);
            column22[j21] = value22;
        }
        w.longs(path + "/buffer/I64/.items", column22);
        }
        if (!payload6_8.isEmpty()) {
            int payloadCount6_8 = payload6_8.size();
        long[] offsets23 = new long[payloadCount6_8 + 1];
        int total23 = 0;
        for (int k6_8 = 0; k6_8 < payloadCount6_8; k6_8++) {
            java.util.List<java.lang.Float> list23 = payload6_8.get(k6_8);
            total23 += list23 == null ? 0 : list23.size();
            offsets23[k6_8 + 1] = total23;
        }
        w.longs(path + "/buffer/F16" + "/.offsets", offsets23);
        java.util.ArrayList<java.lang.Float> items23 = new java.util.ArrayList<>(total23);
        for (int k6_8 = 0; k6_8 < payloadCount6_8; k6_8++) {
            java.util.List<java.lang.Float> list23 = payload6_8.get(k6_8);
            if (list23 != null) items23.addAll(list23);
        }
        int itemCount23 = items23.size();
        double[] column24 = new double[itemCount23];
        for (int j23 = 0; j23 < itemCount23; j23++) {
            double value24 = items23.get(j23);
            column24[j23] = value24;
        }
        w.doubles(path + "/buffer/F16/.items", column24);
        }
        if (!payload6_9.isEmpty()) {
            int payloadCount6_9 = payload6_9.size();
        long[] offsets25 = new long[payloadCount6_9 + 1];
        int total25 = 0;
        for (int k6_9 = 0; k6_9 < payloadCount6_9; k6_9++) {
            java.util.List<java.lang.Float> list25 = payload6_9.get(k6_9);
            total25 += list25 == null ? 0 : list25.size();
            offsets25[k6_9 + 1] = total25;
        }
        w.longs(path + "/buffer/F32" + "/.offsets", offsets25);
        java.util.ArrayList<java.lang.Float> items25 = new java.util.ArrayList<>(total25);
        for (int k6_9 = 0; k6_9 < payloadCount6_9; k6_9++) {
            java.util.List<java.lang.Float> list25 = payload6_9.get(k6_9);
            if (list25 != null) items25.addAll(list25);
        }
        int itemCount25 = items25.size();
        double[] column26 = new double[itemCount25];
        for (int j25 = 0; j25 < itemCount25; j25++) {
            double value26 = items25.get(j25);
            column26[j25] = value26;
        }
        w.doubles(path + "/buffer/F32/.items", column26);
        }
        if (!payload6_10.isEmpty()) {
            int payloadCount6_10 = payload6_10.size();
        long[] offsets27 = new long[payloadCount6_10 + 1];
        int total27 = 0;
        for (int k6_10 = 0; k6_10 < payloadCount6_10; k6_10++) {
            java.util.List<java.lang.Double> list27 = payload6_10.get(k6_10);
            total27 += list27 == null ? 0 : list27.size();
            offsets27[k6_10 + 1] = total27;
        }
        w.longs(path + "/buffer/F64" + "/.offsets", offsets27);
        java.util.ArrayList<java.lang.Double> items27 = new java.util.ArrayList<>(total27);
        for (int k6_10 = 0; k6_10 < payloadCount6_10; k6_10++) {
            java.util.List<java.lang.Double> list27 = payload6_10.get(k6_10);
            if (list27 != null) items27.addAll(list27);
        }
        int itemCount27 = items27.size();
        double[] column28 = new double[itemCount27];
        for (int j27 = 0; j27 < itemCount27; j27++) {
            double value28 = items27.get(j27);
            column28[j27] = value28;
        }
        w.doubles(path + "/buffer/F64/.items", column28);
        }
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TensorData> values) {
        writeColumnsStatic(w, path, values);
    }
}
