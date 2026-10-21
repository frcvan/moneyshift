// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Colormap for mapping scalar values within a given range to a color.
 * 
 * This provides a number of popular pre-defined colormaps.
 * In the future, the Rerun Viewer will allow users to define their own colormaps,
 * but currently the Viewer is limited to the types defined here.
 */
public enum Colormap implements org.teamvan.moneyshift.Component {
    /**
     * A simple black to white gradient.
     * 
     * This is a sRGB gray gradient which is perceptually uniform.
     */
    Grayscale(1L),
    /**
     * The Inferno colormap from Matplotlib.
     * 
     * This is a perceptually uniform colormap.
     * It interpolates from black to red to bright yellow.
     */
    Inferno(2L),
    /**
     * The Magma colormap from Matplotlib.
     * 
     * This is a perceptually uniform colormap.
     * It interpolates from black to purple to white.
     */
    Magma(3L),
    /**
     * The Plasma colormap from Matplotlib.
     * 
     * This is a perceptually uniform colormap.
     * It interpolates from dark blue to purple to yellow.
     */
    Plasma(4L),
    /**
     * Google's Turbo colormap map.
     * 
     * This is a perceptually non-uniform rainbow colormap addressing many issues of
     * more traditional rainbow colormaps like Jet.
     * It is more perceptually uniform without sharp transitions and is more colorblind-friendly.
     * Details: &lt;https://research.google/blog/turbo-an-improved-rainbow-colormap-for-visualization/&gt;
     */
    Turbo(5L),
    /**
     * The Viridis colormap from Matplotlib
     * 
     * This is a perceptually uniform colormap which is robust to color blindness.
     * It interpolates from dark purple to green to yellow.
     */
    Viridis(6L),
    /**
     * Rasmusgo's Cyan to Yellow colormap
     * 
     * This is a perceptually uniform colormap which is robust to color blindness.
     * It is especially suited for visualizing signed values.
     * It interpolates from cyan to blue to dark gray to brass to yellow.
     */
    CyanToYellow(7L),
    /**
     * The Spectral colormap from Matplotlib.
     * 
     * This is a diverging colormap, often used to visualize data with a meaningful center point,
     * where deviations from that center are important to highlight.
     * It interpolates from red to orange to yellow to green to blue to violet.
     */
    Spectral(8L),
    /**
     * The Twilight colormap from Matplotlib.
     * 
     * This is a perceptually uniform cyclic colormap from Matplotlib, it is useful for
     * visualizing periodic or cyclic data.
     * 
     * It interpolates from white to blue to purple to red to orange and back to white.
     */
    Twilight(9L),
    /**
     * The classic {@code RViz} "Map" grid-map colormap intended for occupancy-style SLAM grid maps.
     * 
     * Known values are mapped to a grayscale ramp from white (free) to black (occupied),
     * unknown values are in a green-blue color. Special / illegal values have highlight colors.
     */
    RvizMap(10L),
    /**
     * The classic {@code RViz} "Costmap" grid-map colormap for robot navigation cost maps.
     * 
     * Cost values are mapped to blue to red spectrum, and special cost values
     * (e.g. lethal obstacles) have highlight colors. Zero values are fully transparent.
     */
    RvizCostmap(11L),
    /**
     * A grid-map colormap for robot navigation cost maps.
     * 
     * Semantically equivalent to the {@code RViz} cost map but with a more pleasing color palette.
     * 
     * Cost values are mapped to a green to yellow spectrum, and special cost values
     * (e.g. lethal obstacles) have highlight colors. Zero values are fully transparent.
     */
    Costmap(12L);

    private final long value;

    Colormap(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static Colormap fromValue(long value) {
        for (Colormap candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown Colormap value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.Colormap";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Colormap";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Colormap> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Colormap::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Colormap value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Colormap value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Colormap> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Colormap value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Colormap value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
