// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * Describe a component column to be selected in the dataframe view.
 */
public record SelectedColumns(
    org.teamvan.moneyshift.blueprint.encodings.SelectedColumns selectedColumns
) implements org.teamvan.moneyshift.Component {
    public SelectedColumns {
        java.util.Objects.requireNonNull(selectedColumns, "selectedColumns");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.SelectedColumns";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.blueprint.encodings.SelectedColumns.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.blueprint.encodings.SelectedColumns.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<SelectedColumns> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, SelectedColumns::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, SelectedColumns value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, SelectedColumns value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<SelectedColumns> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            SelectedColumns value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, SelectedColumns value) {
        org.teamvan.moneyshift.blueprint.encodings.SelectedColumns.writeOne(vector, index, value.selectedColumns());
    }
}
