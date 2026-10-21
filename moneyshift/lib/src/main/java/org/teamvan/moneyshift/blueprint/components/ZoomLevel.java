// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * A zoom level determines how much of the world is visible on a map.
 */
public record ZoomLevel(
    /**
     * Zoom level: 0 being the lowest zoom level (fully zoomed out) and 22 being the highest (fully zoomed in).
     */
    org.teamvan.moneyshift.encodings.Float64 zoom
) implements org.teamvan.moneyshift.Component {
    public ZoomLevel {
        java.util.Objects.requireNonNull(zoom, "zoom");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.ZoomLevel";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Float64.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Float64.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ZoomLevel> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ZoomLevel::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ZoomLevel value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ZoomLevel value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ZoomLevel> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ZoomLevel value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.Float8Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.Float8Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ZoomLevel value) {
        org.teamvan.moneyshift.encodings.Float64.writeOne(vector, index, value.zoom());
    }
}
