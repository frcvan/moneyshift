// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * Configuration for the filter is not null feature of the dataframe view.
 */
public record FilterIsNotNull(
    org.teamvan.moneyshift.blueprint.encodings.FilterIsNotNull filterIsNotNull
) implements org.teamvan.moneyshift.Component {
    public FilterIsNotNull {
        java.util.Objects.requireNonNull(filterIsNotNull, "filterIsNotNull");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public FilterIsNotNull(boolean active, org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector column) {
        this(new org.teamvan.moneyshift.blueprint.encodings.FilterIsNotNull(new org.teamvan.moneyshift.encodings.Bool(active), column));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.FilterIsNotNull";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.blueprint.encodings.FilterIsNotNull.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.blueprint.encodings.FilterIsNotNull.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<FilterIsNotNull> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, FilterIsNotNull::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, FilterIsNotNull value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, FilterIsNotNull value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<FilterIsNotNull> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            FilterIsNotNull value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, FilterIsNotNull value) {
        org.teamvan.moneyshift.blueprint.encodings.FilterIsNotNull.writeOne(vector, index, value.filterIsNotNull());
    }
}
