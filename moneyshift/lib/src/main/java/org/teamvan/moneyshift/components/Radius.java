// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The radius of something, e.g. a point.
 * 
 * Internally, positive values indicate scene units, whereas negative values
 * are interpreted as UI points.
 * 
 * UI points are independent of zooming in Views, but are sensitive to the application UI scaling.
 * at 100% UI scaling, UI points are equal to pixels
 * The Viewer's UI scaling defaults to the OS scaling which typically is 100% for full HD screens and 200% for 4k screens.
 */
public record Radius(
    org.teamvan.moneyshift.encodings.Float32 value
) implements org.teamvan.moneyshift.Component {
    public Radius {
        java.util.Objects.requireNonNull(value, "value");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Radius";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Float32.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Float32.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Radius> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Radius::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Radius value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Radius value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Radius> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Radius value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.Float4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.Float4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Radius value) {
        org.teamvan.moneyshift.encodings.Float32.writeOne(vector, index, value.value());
    }
}
