// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * How many columns a grid container should have.
 */
public record GridColumns(
    /**
     * The number of columns.
     */
    org.teamvan.moneyshift.encodings.UInt32 columns
) implements org.teamvan.moneyshift.Component {
    public GridColumns {
        java.util.Objects.requireNonNull(columns, "columns");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.GridColumns";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.UInt32.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.UInt32.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<GridColumns> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, GridColumns::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, GridColumns value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, GridColumns value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<GridColumns> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            GridColumns value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.UInt4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, GridColumns value) {
        org.teamvan.moneyshift.encodings.UInt32.writeOne(vector, index, value.columns());
    }
}
