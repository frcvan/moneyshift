// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * How a table column value is rendered.
 */
public enum TableCellKind implements org.teamvan.moneyshift.Component {
    /**
     * Infer the renderer from the component or Arrow datatype.
     */
    Auto(1L),
    /**
     * Render a Rerun URI as an interactive link.
     */
    Link(2L),
    /**
     * Render an image blob as a thumbnail.
     */
    Thumbnail(3L),
    /**
     * Render a boolean as a flag.
     * 
     * Card layouts place the first flag field in the special flag position and
     * currently support at most one flag field.
     */
    Flag(4L),
    /**
     * Render a recording reference as embedded views.
     * 
     * The column must also have {@link org.teamvan.moneyshift.blueprint.archetypes.TableColumnPreview} configuration with at least one view.
     * All preview columns use the shared {@link org.teamvan.moneyshift.blueprint.archetypes.PreviewsConfig}.
     */
    Preview(5L),
    /**
     * Interpret scalar {@code i32} values as {@code rerun.cloud.v1alpha1.EntryKind} enum values and render their human-readable names.
     * 
     * Unknown numeric values are rendered as {@code Unknown EntryKind}.
     */
    EntryKind(6L);

    private final long value;

    TableCellKind(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static TableCellKind fromValue(long value) {
        for (TableCellKind candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown TableCellKind value: " + value);
    }

    public static final java.lang.String NAME = "rerun.blueprint.components.TableCellKind";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.TableCellKind";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<TableCellKind> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, TableCellKind::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, TableCellKind value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, TableCellKind value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TableCellKind> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TableCellKind value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TableCellKind value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
