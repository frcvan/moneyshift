// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Draw order of 2D elements. Higher values are drawn on top of lower values.
 * 
 * An entity can have only a single draw order component.
 * Within an entity draw order is governed by the order of the components.
 * 
 * Draw order for entities with the same draw order is generally undefined.
 */
public record DrawOrder(
    org.teamvan.moneyshift.encodings.Float32 value
) implements org.teamvan.moneyshift.Component {
    public DrawOrder {
        java.util.Objects.requireNonNull(value, "value");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.DrawOrder";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Float32.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Float32.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<DrawOrder> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, DrawOrder::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, DrawOrder value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, DrawOrder value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<DrawOrder> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            DrawOrder value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.Float4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.Float4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, DrawOrder value) {
        org.teamvan.moneyshift.encodings.Float32.writeOne(vector, index, value.value());
    }
}
