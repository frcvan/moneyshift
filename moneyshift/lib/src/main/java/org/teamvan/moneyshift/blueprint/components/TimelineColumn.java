// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * A timeline column in a text log table.
 */
public record TimelineColumn(
    /**
     * The timeline column.
     */
    org.teamvan.moneyshift.blueprint.encodings.TimelineColumn timelineColumn
) implements org.teamvan.moneyshift.Component {
    public TimelineColumn {
        java.util.Objects.requireNonNull(timelineColumn, "timelineColumn");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public TimelineColumn(boolean visible, java.lang.String timeline) {
        this(new org.teamvan.moneyshift.blueprint.encodings.TimelineColumn(new org.teamvan.moneyshift.encodings.Bool(visible), new org.teamvan.moneyshift.encodings.Utf8(timeline)));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.TimelineColumn";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.blueprint.encodings.TimelineColumn.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.blueprint.encodings.TimelineColumn.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<TimelineColumn> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, TimelineColumn::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, TimelineColumn value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, TimelineColumn value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TimelineColumn> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TimelineColumn value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TimelineColumn value) {
        org.teamvan.moneyshift.blueprint.encodings.TimelineColumn.writeOne(vector, index, value.timelineColumn());
    }
}
