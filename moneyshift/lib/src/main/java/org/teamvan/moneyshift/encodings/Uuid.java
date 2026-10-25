// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A 16-byte UUID.
 */
public record Uuid(
    /**
     * The raw bytes representing the UUID.
     */
    short[] bytes
) implements org.teamvan.moneyshift.NtEncoding<Uuid> {
    public Uuid {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof Uuid that)) return false;
        return java.util.Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(bytes));
    }
    public static final java.lang.String NAME = "rerun.encodings.Uuid";

    /** Writes this type's NetworkTables columns; one entry per value. */
    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Uuid> values) {
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
        long[] column1 = new long[n];
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v1_1 = v1_0 == null ? null : v1_0[0];
            var value1 = v1_1;
            column1[i] = value1 == null ? 0L : value1;
        }
        w.longs(path + "/0", column1);
        long[] column2 = new long[n];
        for (int i = 0; i < n; i++) {
            var v2_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v2_1 = v2_0 == null ? null : v2_0[1];
            var value2 = v2_1;
            column2[i] = value2 == null ? 0L : value2;
        }
        w.longs(path + "/1", column2);
        long[] column3 = new long[n];
        for (int i = 0; i < n; i++) {
            var v3_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v3_1 = v3_0 == null ? null : v3_0[2];
            var value3 = v3_1;
            column3[i] = value3 == null ? 0L : value3;
        }
        w.longs(path + "/2", column3);
        long[] column4 = new long[n];
        for (int i = 0; i < n; i++) {
            var v4_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v4_1 = v4_0 == null ? null : v4_0[3];
            var value4 = v4_1;
            column4[i] = value4 == null ? 0L : value4;
        }
        w.longs(path + "/3", column4);
        long[] column5 = new long[n];
        for (int i = 0; i < n; i++) {
            var v5_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v5_1 = v5_0 == null ? null : v5_0[4];
            var value5 = v5_1;
            column5[i] = value5 == null ? 0L : value5;
        }
        w.longs(path + "/4", column5);
        long[] column6 = new long[n];
        for (int i = 0; i < n; i++) {
            var v6_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v6_1 = v6_0 == null ? null : v6_0[5];
            var value6 = v6_1;
            column6[i] = value6 == null ? 0L : value6;
        }
        w.longs(path + "/5", column6);
        long[] column7 = new long[n];
        for (int i = 0; i < n; i++) {
            var v7_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v7_1 = v7_0 == null ? null : v7_0[6];
            var value7 = v7_1;
            column7[i] = value7 == null ? 0L : value7;
        }
        w.longs(path + "/6", column7);
        long[] column8 = new long[n];
        for (int i = 0; i < n; i++) {
            var v8_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v8_1 = v8_0 == null ? null : v8_0[7];
            var value8 = v8_1;
            column8[i] = value8 == null ? 0L : value8;
        }
        w.longs(path + "/7", column8);
        long[] column9 = new long[n];
        for (int i = 0; i < n; i++) {
            var v9_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v9_1 = v9_0 == null ? null : v9_0[8];
            var value9 = v9_1;
            column9[i] = value9 == null ? 0L : value9;
        }
        w.longs(path + "/8", column9);
        long[] column10 = new long[n];
        for (int i = 0; i < n; i++) {
            var v10_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v10_1 = v10_0 == null ? null : v10_0[9];
            var value10 = v10_1;
            column10[i] = value10 == null ? 0L : value10;
        }
        w.longs(path + "/9", column10);
        long[] column11 = new long[n];
        for (int i = 0; i < n; i++) {
            var v11_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v11_1 = v11_0 == null ? null : v11_0[10];
            var value11 = v11_1;
            column11[i] = value11 == null ? 0L : value11;
        }
        w.longs(path + "/10", column11);
        long[] column12 = new long[n];
        for (int i = 0; i < n; i++) {
            var v12_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v12_1 = v12_0 == null ? null : v12_0[11];
            var value12 = v12_1;
            column12[i] = value12 == null ? 0L : value12;
        }
        w.longs(path + "/11", column12);
        long[] column13 = new long[n];
        for (int i = 0; i < n; i++) {
            var v13_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v13_1 = v13_0 == null ? null : v13_0[12];
            var value13 = v13_1;
            column13[i] = value13 == null ? 0L : value13;
        }
        w.longs(path + "/12", column13);
        long[] column14 = new long[n];
        for (int i = 0; i < n; i++) {
            var v14_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v14_1 = v14_0 == null ? null : v14_0[13];
            var value14 = v14_1;
            column14[i] = value14 == null ? 0L : value14;
        }
        w.longs(path + "/13", column14);
        long[] column15 = new long[n];
        for (int i = 0; i < n; i++) {
            var v15_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v15_1 = v15_0 == null ? null : v15_0[14];
            var value15 = v15_1;
            column15[i] = value15 == null ? 0L : value15;
        }
        w.longs(path + "/14", column15);
        long[] column16 = new long[n];
        for (int i = 0; i < n; i++) {
            var v16_0 = values.get(i) == null ? null : values.get(i).bytes();
            var v16_1 = v16_0 == null ? null : v16_0[15];
            var value16 = v16_1;
            column16[i] = value16 == null ? 0L : value16;
        }
        w.longs(path + "/15", column16);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<Uuid> values) {
        writeColumnsStatic(w, path, values);
    }

    public static Uuid of(short... values) {
        if (values.length != 16) throw new java.lang.IllegalArgumentException("Uuid needs exactly 16 values, got " + values.length);
        return new Uuid(values);
    }
}
