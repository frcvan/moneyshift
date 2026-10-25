// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The distance from the camera origin to the image plane when the projection is shown in a 3D viewer.
 * 
 * This is only used for visualization purposes, and does not affect the projection itself.
 */
public record ImagePlaneDistance(
    org.teamvan.moneyshift.encodings.Float32 imageFromCamera
) implements org.teamvan.moneyshift.NtComponent<ImagePlaneDistance> {
    public ImagePlaneDistance {
        java.util.Objects.requireNonNull(imageFromCamera, "imageFromCamera");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ImagePlaneDistance(float value) {
        this(new org.teamvan.moneyshift.encodings.Float32(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ImagePlaneDistance";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ImagePlaneDistance> values) {
        int n = values.size();
        boolean[] present0 = null;
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).imageFromCamera();
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
            var v1_0 = values.get(i) == null ? null : values.get(i).imageFromCamera();
            var v1_1 = v1_0 == null ? null : v1_0.value();
            var value1 = v1_1;
            column1[i] = value1 == null ? 0.0 : value1;
        }
        w.doubles(path, column1);
    }

    @Override
    public void writeColumns(
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<ImagePlaneDistance> values) {
        writeColumnsStatic(w, path, values);
    }
}
