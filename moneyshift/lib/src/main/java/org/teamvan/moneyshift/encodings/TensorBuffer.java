// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * The underlying storage for {@link org.teamvan.moneyshift.archetypes.Tensor rerun::archetypes::Tensor}.
 * 
 * Tensor elements are stored in a contiguous buffer of a single type.
 */
public sealed interface TensorBuffer extends org.teamvan.moneyshift.NtEncoding<TensorBuffer> permits
    TensorBuffer.U8,
    TensorBuffer.U16,
    TensorBuffer.U32,
    TensorBuffer.U64,
    TensorBuffer.I8,
    TensorBuffer.I16,
    TensorBuffer.I32,
    TensorBuffer.I64,
    TensorBuffer.F16,
    TensorBuffer.F32,
    TensorBuffer.F64 {
    /**
     * 8bit unsigned integer.
     */
    record U8(java.util.List<java.lang.Short> value) implements TensorBuffer {
        public U8 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 16bit unsigned integer.
     */
    record U16(java.util.List<java.lang.Integer> value) implements TensorBuffer {
        public U16 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 32bit unsigned integer.
     */
    record U32(java.util.List<java.lang.Long> value) implements TensorBuffer {
        public U32 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 64bit unsigned integer.
     */
    record U64(java.util.List<java.lang.Long> value) implements TensorBuffer {
        public U64 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 8bit signed integer.
     */
    record I8(java.util.List<java.lang.Byte> value) implements TensorBuffer {
        public I8 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 16bit signed integer.
     */
    record I16(java.util.List<java.lang.Short> value) implements TensorBuffer {
        public I16 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 32bit signed integer.
     */
    record I32(java.util.List<java.lang.Integer> value) implements TensorBuffer {
        public I32 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 64bit signed integer.
     */
    record I64(java.util.List<java.lang.Long> value) implements TensorBuffer {
        public I64 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 16bit IEEE-754 floating point, also known as {@code half}.
     */
    record F16(java.util.List<java.lang.Float> value) implements TensorBuffer {
        public F16 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 32bit IEEE-754 floating point, also known as {@code float} or {@code single}.
     */
    record F32(java.util.List<java.lang.Float> value) implements TensorBuffer {
        public F32 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 64bit IEEE-754 floating point, also known as {@code double}.
     */
    record F64(java.util.List<java.lang.Double> value) implements TensorBuffer {
        public F64 { java.util.Objects.requireNonNull(value, "value"); }
    }

    java.lang.String NAME = "rerun.encodings.TensorBuffer";

    @Override
    default void writeColumns(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TensorBuffer> values) {
        writeColumnsStatic(w, path, values);
    }

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<TensorBuffer> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            boolean ok0 = (values.get(i)) != null;
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
        long[] variants1 = new long[n];
        java.util.ArrayList<java.util.List<java.lang.Short>> payload1_0 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Integer>> payload1_1 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Long>> payload1_2 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Long>> payload1_3 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Byte>> payload1_4 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Short>> payload1_5 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Integer>> payload1_6 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Long>> payload1_7 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Float>> payload1_8 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Float>> payload1_9 = new java.util.ArrayList<>();
        java.util.ArrayList<java.util.List<java.lang.Double>> payload1_10 = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            org.teamvan.moneyshift.encodings.TensorBuffer value1 = values.get(i);
            if (value1 == null) {
                variants1[i] = -1L;
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.U8 variant1_0) {
                variants1[i] = 0L;
                payload1_0.add(variant1_0.value());
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.U16 variant1_1) {
                variants1[i] = 1L;
                payload1_1.add(variant1_1.value());
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.U32 variant1_2) {
                variants1[i] = 2L;
                payload1_2.add(variant1_2.value());
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.U64 variant1_3) {
                variants1[i] = 3L;
                payload1_3.add(variant1_3.value());
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.I8 variant1_4) {
                variants1[i] = 4L;
                payload1_4.add(variant1_4.value());
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.I16 variant1_5) {
                variants1[i] = 5L;
                payload1_5.add(variant1_5.value());
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.I32 variant1_6) {
                variants1[i] = 6L;
                payload1_6.add(variant1_6.value());
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.I64 variant1_7) {
                variants1[i] = 7L;
                payload1_7.add(variant1_7.value());
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.F16 variant1_8) {
                variants1[i] = 8L;
                payload1_8.add(variant1_8.value());
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.F32 variant1_9) {
                variants1[i] = 9L;
                payload1_9.add(variant1_9.value());
            } else if (value1 instanceof org.teamvan.moneyshift.encodings.TensorBuffer.F64 variant1_10) {
                variants1[i] = 10L;
                payload1_10.add(variant1_10.value());
            } else {
                variants1[i] = -1L;
            }
        }
        w.longs(path + "/.variant", variants1);
        if (!payload1_0.isEmpty()) {
            int payloadCount1_0 = payload1_0.size();
        long[] offsets2 = new long[payloadCount1_0 + 1];
        int total2 = 0;
        for (int k1_0 = 0; k1_0 < payloadCount1_0; k1_0++) {
            java.util.List<java.lang.Short> list2 = payload1_0.get(k1_0);
            total2 += list2 == null ? 0 : list2.size();
            offsets2[k1_0 + 1] = total2;
        }
        w.longs(path + "/U8" + "/.offsets", offsets2);
        java.util.ArrayList<java.lang.Short> items2 = new java.util.ArrayList<>(total2);
        for (int k1_0 = 0; k1_0 < payloadCount1_0; k1_0++) {
            java.util.List<java.lang.Short> list2 = payload1_0.get(k1_0);
            if (list2 != null) items2.addAll(list2);
        }
        int itemCount2 = items2.size();
        long[] column3 = new long[itemCount2];
        for (int j2 = 0; j2 < itemCount2; j2++) {
            long value3 = items2.get(j2);
            column3[j2] = value3;
        }
        w.longs(path + "/U8/.items", column3);
        }
        if (!payload1_1.isEmpty()) {
            int payloadCount1_1 = payload1_1.size();
        long[] offsets4 = new long[payloadCount1_1 + 1];
        int total4 = 0;
        for (int k1_1 = 0; k1_1 < payloadCount1_1; k1_1++) {
            java.util.List<java.lang.Integer> list4 = payload1_1.get(k1_1);
            total4 += list4 == null ? 0 : list4.size();
            offsets4[k1_1 + 1] = total4;
        }
        w.longs(path + "/U16" + "/.offsets", offsets4);
        java.util.ArrayList<java.lang.Integer> items4 = new java.util.ArrayList<>(total4);
        for (int k1_1 = 0; k1_1 < payloadCount1_1; k1_1++) {
            java.util.List<java.lang.Integer> list4 = payload1_1.get(k1_1);
            if (list4 != null) items4.addAll(list4);
        }
        int itemCount4 = items4.size();
        long[] column5 = new long[itemCount4];
        for (int j4 = 0; j4 < itemCount4; j4++) {
            long value5 = items4.get(j4);
            column5[j4] = value5;
        }
        w.longs(path + "/U16/.items", column5);
        }
        if (!payload1_2.isEmpty()) {
            int payloadCount1_2 = payload1_2.size();
        long[] offsets6 = new long[payloadCount1_2 + 1];
        int total6 = 0;
        for (int k1_2 = 0; k1_2 < payloadCount1_2; k1_2++) {
            java.util.List<java.lang.Long> list6 = payload1_2.get(k1_2);
            total6 += list6 == null ? 0 : list6.size();
            offsets6[k1_2 + 1] = total6;
        }
        w.longs(path + "/U32" + "/.offsets", offsets6);
        java.util.ArrayList<java.lang.Long> items6 = new java.util.ArrayList<>(total6);
        for (int k1_2 = 0; k1_2 < payloadCount1_2; k1_2++) {
            java.util.List<java.lang.Long> list6 = payload1_2.get(k1_2);
            if (list6 != null) items6.addAll(list6);
        }
        int itemCount6 = items6.size();
        long[] column7 = new long[itemCount6];
        for (int j6 = 0; j6 < itemCount6; j6++) {
            long value7 = items6.get(j6);
            column7[j6] = value7;
        }
        w.longs(path + "/U32/.items", column7);
        }
        if (!payload1_3.isEmpty()) {
            int payloadCount1_3 = payload1_3.size();
        long[] offsets8 = new long[payloadCount1_3 + 1];
        int total8 = 0;
        for (int k1_3 = 0; k1_3 < payloadCount1_3; k1_3++) {
            java.util.List<java.lang.Long> list8 = payload1_3.get(k1_3);
            total8 += list8 == null ? 0 : list8.size();
            offsets8[k1_3 + 1] = total8;
        }
        w.longs(path + "/U64" + "/.offsets", offsets8);
        java.util.ArrayList<java.lang.Long> items8 = new java.util.ArrayList<>(total8);
        for (int k1_3 = 0; k1_3 < payloadCount1_3; k1_3++) {
            java.util.List<java.lang.Long> list8 = payload1_3.get(k1_3);
            if (list8 != null) items8.addAll(list8);
        }
        int itemCount8 = items8.size();
        long[] column9 = new long[itemCount8];
        for (int j8 = 0; j8 < itemCount8; j8++) {
            long value9 = items8.get(j8);
            column9[j8] = value9;
        }
        w.longs(path + "/U64/.items", column9);
        }
        if (!payload1_4.isEmpty()) {
            int payloadCount1_4 = payload1_4.size();
        long[] offsets10 = new long[payloadCount1_4 + 1];
        int total10 = 0;
        for (int k1_4 = 0; k1_4 < payloadCount1_4; k1_4++) {
            java.util.List<java.lang.Byte> list10 = payload1_4.get(k1_4);
            total10 += list10 == null ? 0 : list10.size();
            offsets10[k1_4 + 1] = total10;
        }
        w.longs(path + "/I8" + "/.offsets", offsets10);
        java.util.ArrayList<java.lang.Byte> items10 = new java.util.ArrayList<>(total10);
        for (int k1_4 = 0; k1_4 < payloadCount1_4; k1_4++) {
            java.util.List<java.lang.Byte> list10 = payload1_4.get(k1_4);
            if (list10 != null) items10.addAll(list10);
        }
        int itemCount10 = items10.size();
        long[] column11 = new long[itemCount10];
        for (int j10 = 0; j10 < itemCount10; j10++) {
            long value11 = items10.get(j10);
            column11[j10] = value11;
        }
        w.longs(path + "/I8/.items", column11);
        }
        if (!payload1_5.isEmpty()) {
            int payloadCount1_5 = payload1_5.size();
        long[] offsets12 = new long[payloadCount1_5 + 1];
        int total12 = 0;
        for (int k1_5 = 0; k1_5 < payloadCount1_5; k1_5++) {
            java.util.List<java.lang.Short> list12 = payload1_5.get(k1_5);
            total12 += list12 == null ? 0 : list12.size();
            offsets12[k1_5 + 1] = total12;
        }
        w.longs(path + "/I16" + "/.offsets", offsets12);
        java.util.ArrayList<java.lang.Short> items12 = new java.util.ArrayList<>(total12);
        for (int k1_5 = 0; k1_5 < payloadCount1_5; k1_5++) {
            java.util.List<java.lang.Short> list12 = payload1_5.get(k1_5);
            if (list12 != null) items12.addAll(list12);
        }
        int itemCount12 = items12.size();
        long[] column13 = new long[itemCount12];
        for (int j12 = 0; j12 < itemCount12; j12++) {
            long value13 = items12.get(j12);
            column13[j12] = value13;
        }
        w.longs(path + "/I16/.items", column13);
        }
        if (!payload1_6.isEmpty()) {
            int payloadCount1_6 = payload1_6.size();
        long[] offsets14 = new long[payloadCount1_6 + 1];
        int total14 = 0;
        for (int k1_6 = 0; k1_6 < payloadCount1_6; k1_6++) {
            java.util.List<java.lang.Integer> list14 = payload1_6.get(k1_6);
            total14 += list14 == null ? 0 : list14.size();
            offsets14[k1_6 + 1] = total14;
        }
        w.longs(path + "/I32" + "/.offsets", offsets14);
        java.util.ArrayList<java.lang.Integer> items14 = new java.util.ArrayList<>(total14);
        for (int k1_6 = 0; k1_6 < payloadCount1_6; k1_6++) {
            java.util.List<java.lang.Integer> list14 = payload1_6.get(k1_6);
            if (list14 != null) items14.addAll(list14);
        }
        int itemCount14 = items14.size();
        long[] column15 = new long[itemCount14];
        for (int j14 = 0; j14 < itemCount14; j14++) {
            long value15 = items14.get(j14);
            column15[j14] = value15;
        }
        w.longs(path + "/I32/.items", column15);
        }
        if (!payload1_7.isEmpty()) {
            int payloadCount1_7 = payload1_7.size();
        long[] offsets16 = new long[payloadCount1_7 + 1];
        int total16 = 0;
        for (int k1_7 = 0; k1_7 < payloadCount1_7; k1_7++) {
            java.util.List<java.lang.Long> list16 = payload1_7.get(k1_7);
            total16 += list16 == null ? 0 : list16.size();
            offsets16[k1_7 + 1] = total16;
        }
        w.longs(path + "/I64" + "/.offsets", offsets16);
        java.util.ArrayList<java.lang.Long> items16 = new java.util.ArrayList<>(total16);
        for (int k1_7 = 0; k1_7 < payloadCount1_7; k1_7++) {
            java.util.List<java.lang.Long> list16 = payload1_7.get(k1_7);
            if (list16 != null) items16.addAll(list16);
        }
        int itemCount16 = items16.size();
        long[] column17 = new long[itemCount16];
        for (int j16 = 0; j16 < itemCount16; j16++) {
            long value17 = items16.get(j16);
            column17[j16] = value17;
        }
        w.longs(path + "/I64/.items", column17);
        }
        if (!payload1_8.isEmpty()) {
            int payloadCount1_8 = payload1_8.size();
        long[] offsets18 = new long[payloadCount1_8 + 1];
        int total18 = 0;
        for (int k1_8 = 0; k1_8 < payloadCount1_8; k1_8++) {
            java.util.List<java.lang.Float> list18 = payload1_8.get(k1_8);
            total18 += list18 == null ? 0 : list18.size();
            offsets18[k1_8 + 1] = total18;
        }
        w.longs(path + "/F16" + "/.offsets", offsets18);
        java.util.ArrayList<java.lang.Float> items18 = new java.util.ArrayList<>(total18);
        for (int k1_8 = 0; k1_8 < payloadCount1_8; k1_8++) {
            java.util.List<java.lang.Float> list18 = payload1_8.get(k1_8);
            if (list18 != null) items18.addAll(list18);
        }
        int itemCount18 = items18.size();
        double[] column19 = new double[itemCount18];
        for (int j18 = 0; j18 < itemCount18; j18++) {
            double value19 = items18.get(j18);
            column19[j18] = value19;
        }
        w.doubles(path + "/F16/.items", column19);
        }
        if (!payload1_9.isEmpty()) {
            int payloadCount1_9 = payload1_9.size();
        long[] offsets20 = new long[payloadCount1_9 + 1];
        int total20 = 0;
        for (int k1_9 = 0; k1_9 < payloadCount1_9; k1_9++) {
            java.util.List<java.lang.Float> list20 = payload1_9.get(k1_9);
            total20 += list20 == null ? 0 : list20.size();
            offsets20[k1_9 + 1] = total20;
        }
        w.longs(path + "/F32" + "/.offsets", offsets20);
        java.util.ArrayList<java.lang.Float> items20 = new java.util.ArrayList<>(total20);
        for (int k1_9 = 0; k1_9 < payloadCount1_9; k1_9++) {
            java.util.List<java.lang.Float> list20 = payload1_9.get(k1_9);
            if (list20 != null) items20.addAll(list20);
        }
        int itemCount20 = items20.size();
        double[] column21 = new double[itemCount20];
        for (int j20 = 0; j20 < itemCount20; j20++) {
            double value21 = items20.get(j20);
            column21[j20] = value21;
        }
        w.doubles(path + "/F32/.items", column21);
        }
        if (!payload1_10.isEmpty()) {
            int payloadCount1_10 = payload1_10.size();
        long[] offsets22 = new long[payloadCount1_10 + 1];
        int total22 = 0;
        for (int k1_10 = 0; k1_10 < payloadCount1_10; k1_10++) {
            java.util.List<java.lang.Double> list22 = payload1_10.get(k1_10);
            total22 += list22 == null ? 0 : list22.size();
            offsets22[k1_10 + 1] = total22;
        }
        w.longs(path + "/F64" + "/.offsets", offsets22);
        java.util.ArrayList<java.lang.Double> items22 = new java.util.ArrayList<>(total22);
        for (int k1_10 = 0; k1_10 < payloadCount1_10; k1_10++) {
            java.util.List<java.lang.Double> list22 = payload1_10.get(k1_10);
            if (list22 != null) items22.addAll(list22);
        }
        int itemCount22 = items22.size();
        double[] column23 = new double[itemCount22];
        for (int j22 = 0; j22 < itemCount22; j22++) {
            double value23 = items22.get(j22);
            column23[j22] = value23;
        }
        w.doubles(path + "/F64/.items", column23);
        }
    }
}
