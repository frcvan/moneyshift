// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * An orientation convention for a camera or 3D view.
 * 
 * On {@link org.teamvan.moneyshift.archetypes.Pinhole}, this component controls the camera orientation and projection direction.
 * On <a href="https://rerun.io/docs/reference/types/views/spatial3d_view">SpatialInformation</a>, it controls the 3D view's eye orientation, navigation, and default grid plane.
 * A logged {@link org.teamvan.moneyshift.archetypes.ViewCoordinates} provides the default for <a href="https://rerun.io/docs/reference/types/views/spatial3d_view">SpatialInformation</a>.
 * 
 * The three directions are always ordered as The three directions are always ordered as [x, y, z] and specify where each positive axis points.
 * For example, For example, [Right, Down, Forward] means that +X points right, +Y points down, and +Z points forward.
 * 
 * ⚠ <a href="https://github.com/rerun-io/rerun/issues/5032">Rerun does not yet support left-handed coordinate systems</a>.
 */
public record ViewCoordinates(
    /**
     * The directions of the The directions of the [x, y, z] axes.
     */
    org.teamvan.moneyshift.encodings.ViewCoordinates coordinates
) implements org.teamvan.moneyshift.Component {
    public ViewCoordinates {
        java.util.Objects.requireNonNull(coordinates, "coordinates");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ViewCoordinates(org.teamvan.moneyshift.encodings.ViewDir[] coordinates) {
        this(new org.teamvan.moneyshift.encodings.ViewCoordinates(coordinates));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.ViewCoordinates";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.ViewCoordinates.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.ViewCoordinates.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ViewCoordinates> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ViewCoordinates::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ViewCoordinates value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ViewCoordinates value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ViewCoordinates> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ViewCoordinates value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ViewCoordinates value) {
        org.teamvan.moneyshift.encodings.ViewCoordinates.writeOne(vector, index, value.coordinates());
    }
}
