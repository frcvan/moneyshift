// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * A text log column
 */
public record TextLogColumn(
    /**
     * The text log column.
     */
    org.teamvan.moneyshift.blueprint.encodings.TextLogColumn textLogColumn
) implements org.teamvan.moneyshift.Component {
    public TextLogColumn {
        java.util.Objects.requireNonNull(textLogColumn, "textLogColumn");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.TextLogColumn";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.blueprint.encodings.TextLogColumn.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.blueprint.encodings.TextLogColumn.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<TextLogColumn> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, TextLogColumn::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, TextLogColumn value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, TextLogColumn value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TextLogColumn> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TextLogColumn value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TextLogColumn value) {
        org.teamvan.moneyshift.blueprint.encodings.TextLogColumn.writeOne(vector, index, value.textLogColumn());
    }
}
