// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A buffer that is known to store image data.
 * 
 * To interpret the contents of this buffer, see, {@link org.teamvan.moneyshift.components.ImageFormat rerun::components::ImageFormat}.
 */
public record ImageBuffer(
    org.teamvan.moneyshift.encodings.Blob buffer
) implements org.teamvan.moneyshift.NtComponent<ImageBuffer> {
    public ImageBuffer {
        java.util.Objects.requireNonNull(buffer, "buffer");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ImageBuffer(java.util.List<java.lang.Short> data) {
        this(new org.teamvan.moneyshift.encodings.Blob(data));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ImageBuffer";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ImageBuffer> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).buffer();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
        long[] counts1 = new long[n];
        int total1 = 0;
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).buffer();
            var v1_1 = v1_0 == null ? null : v1_0.data();
            java.util.List<java.lang.Short> list1 = v1_1;
            counts1[i] = list1 == null ? 0L : list1.size();
            total1 += (int) counts1[i];
        }
        w.longs(path + "/.count", counts1);
        java.util.ArrayList<java.lang.Short> items1 = new java.util.ArrayList<>(total1);
        for (int i = 0; i < n; i++) {
            var v1_0 = values.get(i) == null ? null : values.get(i).buffer();
            var v1_1 = v1_0 == null ? null : v1_0.data();
            java.util.List<java.lang.Short> list1 = v1_1;
            if (list1 != null) items1.addAll(list1);
        }
        int itemCount1 = items1.size();
        long[] column2 = new long[itemCount1];
        for (int j1 = 0; j1 < itemCount1; j1++) {
            long value2 = items1.get(j1);
            column2[j1] = value2;
        }
        w.longs(path + "/.items", column2);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ImageBuffer> values) {
        writeColumnsStatic(w, path, values);
    }
}
