// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * An N-dimensional array of numbers.
 * 
 * The number of dimensions and their respective lengths is specified by the {@code shape} field.
 * The dimensions are ordered from outermost to innermost. For example, in the common case of
 * a 2D RGB Image, the shape would be {@code [height, width, channel]}.
 * 
 * These dimensions are combined with an index to look up values from the {@code buffer} field,
 * which stores a contiguous array of typed values.
 * 
 */
public record TensorData(
    /**
     * The shape of the tensor, i.e. the length of each dimension.
     */
    java.util.List<java.lang.Long> shape,
    /**
     * The names of the dimensions of the tensor (optional).
     * 
     * If set, should be the same length as {@link org.teamvan.moneyshift.encodings.TensorData#shape rerun::encodings::TensorData::shape}.
     * If it has a different length your names may show up improperly,
     * and some constructors may produce a warning or even an error.
     * 
     * Example: {@code ["height", "width", "channel", "batch"]}.
     */
    java.util.List<java.lang.String> names,
    /**
     * The content/data.
     */
    org.teamvan.moneyshift.encodings.TensorBuffer buffer
) implements org.teamvan.moneyshift.NtEncoding<TensorData> {
    public TensorData {
        shape = java.util.List.copyOf(shape);
        if (names != null) { names = java.util.List.copyOf(names); }
        java.util.Objects.requireNonNull(buffer, "buffer");
    }
    public static final java.lang.String NAME = "rerun.encodings.TensorData";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TensorData> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        long[] counts1 = new long[n];
        int total1 = 0;
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).shape();
            java.util.List<java.lang.Long> list1 = v1_0;
            counts1[i] = list1 == null ? 0L : list1.size();
            total1 += (int) counts1[i];
        }
        w.longs(path + "/shape" + "/.count", counts1);
        java.util.ArrayList<java.lang.Long> items1 = new java.util.ArrayList<>(total1);
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).shape();
            java.util.List<java.lang.Long> list1 = v1_0;
            if (list1 != null) items1.addAll(list1);
        }
        int itemCount1 = items1.size();
        long[] column2 = new long[itemCount1];
        boolean unsigned2 = false;
        for (int j1 = 0; j1 < itemCount1; j1++) {
            long value2 = items1.get(j1);
            column2[j1] = value2;
            unsigned2 |= column2[j1] < 0L;
        }
        w.u64(path + "/shape/.items", column2, unsigned2);
        boolean[] present3 = new boolean[n];
        boolean anyNull3 = false;
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).names();
            boolean ok3 = (v3_0) != null;
            present3[i] = ok3;
            anyNull3 |= !ok3;
        }
        w.present(path + "/names", present3, anyNull3);
        long[] counts4 = new long[n];
        int total4 = 0;
        for (int i = 0; i < n; i++) {
            var v4_0 = values.get(i) == null ? null : values.get(i).names();
            java.util.List<java.lang.String> list4 = v4_0;
            counts4[i] = list4 == null ? 0L : list4.size();
            total4 += (int) counts4[i];
        }
        w.longs(path + "/names" + "/.count", counts4);
        java.util.ArrayList<java.lang.String> items4 = new java.util.ArrayList<>(total4);
        for (int i = 0; i < n; i++) {
            var v4_0 = values.get(i) == null ? null : values.get(i).names();
            java.util.List<java.lang.String> list4 = v4_0;
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
            var v6_0 = values.get(i) == null ? null : values.get(i).buffer();
            org.teamvan.moneyshift.encodings.TensorBuffer value6 = v6_0;
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
        long[] counts7 = new long[payloadCount6_0];
        int total7 = 0;
        for (int k6_0 = 0; k6_0 < payloadCount6_0; k6_0++) {
            java.util.List<java.lang.Short> list7 = payload6_0.get(k6_0);
            counts7[k6_0] = list7 == null ? 0L : list7.size();
            total7 += (int) counts7[k6_0];
        }
        w.longs(path + "/buffer/U8" + "/.count", counts7);
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
        long[] counts9 = new long[payloadCount6_1];
        int total9 = 0;
        for (int k6_1 = 0; k6_1 < payloadCount6_1; k6_1++) {
            java.util.List<java.lang.Integer> list9 = payload6_1.get(k6_1);
            counts9[k6_1] = list9 == null ? 0L : list9.size();
            total9 += (int) counts9[k6_1];
        }
        w.longs(path + "/buffer/U16" + "/.count", counts9);
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
        long[] counts11 = new long[payloadCount6_2];
        int total11 = 0;
        for (int k6_2 = 0; k6_2 < payloadCount6_2; k6_2++) {
            java.util.List<java.lang.Long> list11 = payload6_2.get(k6_2);
            counts11[k6_2] = list11 == null ? 0L : list11.size();
            total11 += (int) counts11[k6_2];
        }
        w.longs(path + "/buffer/U32" + "/.count", counts11);
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
        long[] counts13 = new long[payloadCount6_3];
        int total13 = 0;
        for (int k6_3 = 0; k6_3 < payloadCount6_3; k6_3++) {
            java.util.List<java.lang.Long> list13 = payload6_3.get(k6_3);
            counts13[k6_3] = list13 == null ? 0L : list13.size();
            total13 += (int) counts13[k6_3];
        }
        w.longs(path + "/buffer/U64" + "/.count", counts13);
        java.util.ArrayList<java.lang.Long> items13 = new java.util.ArrayList<>(total13);
        for (int k6_3 = 0; k6_3 < payloadCount6_3; k6_3++) {
            java.util.List<java.lang.Long> list13 = payload6_3.get(k6_3);
            if (list13 != null) items13.addAll(list13);
        }
        int itemCount13 = items13.size();
        long[] column14 = new long[itemCount13];
        boolean unsigned14 = false;
        for (int j13 = 0; j13 < itemCount13; j13++) {
            long value14 = items13.get(j13);
            column14[j13] = value14;
            unsigned14 |= column14[j13] < 0L;
        }
        w.u64(path + "/buffer/U64/.items", column14, unsigned14);
        }
        if (!payload6_4.isEmpty()) {
            int payloadCount6_4 = payload6_4.size();
        long[] counts15 = new long[payloadCount6_4];
        int total15 = 0;
        for (int k6_4 = 0; k6_4 < payloadCount6_4; k6_4++) {
            java.util.List<java.lang.Byte> list15 = payload6_4.get(k6_4);
            counts15[k6_4] = list15 == null ? 0L : list15.size();
            total15 += (int) counts15[k6_4];
        }
        w.longs(path + "/buffer/I8" + "/.count", counts15);
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
        long[] counts17 = new long[payloadCount6_5];
        int total17 = 0;
        for (int k6_5 = 0; k6_5 < payloadCount6_5; k6_5++) {
            java.util.List<java.lang.Short> list17 = payload6_5.get(k6_5);
            counts17[k6_5] = list17 == null ? 0L : list17.size();
            total17 += (int) counts17[k6_5];
        }
        w.longs(path + "/buffer/I16" + "/.count", counts17);
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
        long[] counts19 = new long[payloadCount6_6];
        int total19 = 0;
        for (int k6_6 = 0; k6_6 < payloadCount6_6; k6_6++) {
            java.util.List<java.lang.Integer> list19 = payload6_6.get(k6_6);
            counts19[k6_6] = list19 == null ? 0L : list19.size();
            total19 += (int) counts19[k6_6];
        }
        w.longs(path + "/buffer/I32" + "/.count", counts19);
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
        long[] counts21 = new long[payloadCount6_7];
        int total21 = 0;
        for (int k6_7 = 0; k6_7 < payloadCount6_7; k6_7++) {
            java.util.List<java.lang.Long> list21 = payload6_7.get(k6_7);
            counts21[k6_7] = list21 == null ? 0L : list21.size();
            total21 += (int) counts21[k6_7];
        }
        w.longs(path + "/buffer/I64" + "/.count", counts21);
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
        long[] counts23 = new long[payloadCount6_8];
        int total23 = 0;
        for (int k6_8 = 0; k6_8 < payloadCount6_8; k6_8++) {
            java.util.List<java.lang.Float> list23 = payload6_8.get(k6_8);
            counts23[k6_8] = list23 == null ? 0L : list23.size();
            total23 += (int) counts23[k6_8];
        }
        w.longs(path + "/buffer/F16" + "/.count", counts23);
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
        long[] counts25 = new long[payloadCount6_9];
        int total25 = 0;
        for (int k6_9 = 0; k6_9 < payloadCount6_9; k6_9++) {
            java.util.List<java.lang.Float> list25 = payload6_9.get(k6_9);
            counts25[k6_9] = list25 == null ? 0L : list25.size();
            total25 += (int) counts25[k6_9];
        }
        w.longs(path + "/buffer/F32" + "/.count", counts25);
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
        long[] counts27 = new long[payloadCount6_10];
        int total27 = 0;
        for (int k6_10 = 0; k6_10 < payloadCount6_10; k6_10++) {
            java.util.List<java.lang.Double> list27 = payload6_10.get(k6_10);
            counts27[k6_10] = list27 == null ? 0L : list27.size();
            total27 += (int) counts27[k6_10];
        }
        w.longs(path + "/buffer/F64" + "/.count", counts27);
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
