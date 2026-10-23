// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The world-&gt;depth map scaling factor.
 * 
 * This measures how many depth map units are in a world unit.
 * For instance, if a depth map uses millimeters and the world uses meters,
 * this value would be {@code 1000}.
 * 
 * Note that the only effect on 2D views is the physical depth values shown when hovering the image.
 * In 3D views on the other hand, this affects where the points of the point cloud are placed.
 */
public record DepthMeter(
    org.teamvan.moneyshift.encodings.Float32 value
) implements org.teamvan.moneyshift.NtComponent<DepthMeter> {
    public DepthMeter {
        java.util.Objects.requireNonNull(value, "value");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public DepthMeter(float value) {
        this(new org.teamvan.moneyshift.encodings.Float32(value));
    }


    public static final java.lang.String COMPONENT_TYPE = "rerun.components.DepthMeter";

    @Override
    public java.lang.String componentType() {
        return COMPONENT_TYPE;
    }

    public static void writeColumnsStatic(org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<DepthMeter> values) {
        int n = values.size();
        boolean[] present0 = new boolean[n];
        boolean anyNull0 = false;
        for (int i = 0; i < n; i++) {
            var v0_0 = values.get(i) == null ? null : values.get(i).value();
            boolean ok0 = (v0_0) != null;
            present0[i] = ok0;
            anyNull0 |= !ok0;
        }
        w.present(path, present0, anyNull0);
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
        org.teamvan.moneyshift.NtEntityWriter w, java.lang.String path, java.util.List<DepthMeter> values) {
        writeColumnsStatic(w, path, values);
    }
}
