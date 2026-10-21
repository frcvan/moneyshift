// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Filter used when a single texel/pixel of an image is displayed larger than a single screen pixel.
 * 
 * This happens when zooming into an image, when displaying a low-resolution image in a large area,
 * or when viewing an image up close in 3D space.
 */
public enum MagnificationFilter implements org.teamvan.moneyshift.Component {
    /**
     * Show the nearest pixel value.
     * 
     * This will give a blocky appearance when the image is scaled up.
     * Used as default when rendering 2D images.
     */
    Nearest(1L),
    /**
     * Linearly interpolate the nearest neighbors, creating a smoother look when the image is scaled up.
     * 
     * Used as default for mesh rendering.
     */
    Linear(2L),
    /**
     * Bicubic interpolation using a Catmull-Rom spline, creating the smoothest look when the image is scaled up.
     * 
     * This is computationally more expensive than linear filtering but produces sharper results with less blurring.
     * Unlike bilinear filtering, this avoids cross-shaped artifacts at texel boundaries.
     */
    Bicubic(3L);

    private final long value;

    MagnificationFilter(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static MagnificationFilter fromValue(long value) {
        for (MagnificationFilter candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown MagnificationFilter value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.MagnificationFilter";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.MagnificationFilter";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<MagnificationFilter> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, MagnificationFilter::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, MagnificationFilter value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, MagnificationFilter value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<MagnificationFilter> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            MagnificationFilter value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, MagnificationFilter value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
