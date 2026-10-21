// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Blueprint for a column used by a table or card layout.
 * 
 * This archetype is stored at the layout-specific path for its source column.
 * The source is the final {@link org.teamvan.moneyshift.blueprint.components.ColumnName} part of that path.
 */
public record TableColumn(
    /**
     * The name shown for the column.
     * 
     * If unset, the name is inferred from the source column.
     */
    org.teamvan.moneyshift.components.Name name,
    /**
     * Whether the column's values can be edited.
     * 
     * If unset, editing is disabled.
     * Edits require a remote table with a column marked by {@code rerun:is_table_index} metadata and write permission.
     * ⚠ Currently only boolean values with {@code cell_kind} set to {@code Flag} are supported.
     */
    org.teamvan.moneyshift.blueprint.components.Editable editable,
    /**
     * Whether the column is visible in this layout.
     * 
     * If unset, the enclosing layout determines visibility.
     * Columns listed in the enclosing layout's {@code column_order} or {@code field_order} are shown.
     * Unlisted columns use the viewer default for table layouts and are hidden in card layouts.
     */
    org.teamvan.moneyshift.components.Visible visible,
    /**
     * How to render the column's values.
     * 
     * If unset or {@code Auto}, the viewer infers the renderer from the component or Arrow datatype.
     */
    org.teamvan.moneyshift.blueprint.components.TableCellKind cellKind
) implements org.teamvan.moneyshift.Archetype {
    public TableColumn {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.TableColumn";
    public static final int NUM_COMPONENTS = 4;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_NAME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TableColumn:name", "rerun.components.Name");

    /**
     * The name shown for the column.
     * 
     * If unset, the name is inferred from the source column.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorName() {
        return DESCRIPTOR_NAME;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_EDITABLE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TableColumn:editable", "rerun.blueprint.components.Editable");

    /**
     * Whether the column's values can be edited.
     * 
     * If unset, editing is disabled.
     * Edits require a remote table with a column marked by {@code rerun:is_table_index} metadata and write permission.
     * ⚠ Currently only boolean values with {@code cell_kind} set to {@code Flag} are supported.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorEditable() {
        return DESCRIPTOR_EDITABLE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VISIBLE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TableColumn:visible", "rerun.components.Visible");

    /**
     * Whether the column is visible in this layout.
     * 
     * If unset, the enclosing layout determines visibility.
     * Columns listed in the enclosing layout's {@code column_order} or {@code field_order} are shown.
     * Unlisted columns use the viewer default for table layouts and are hidden in card layouts.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVisible() {
        return DESCRIPTOR_VISIBLE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CELL_KIND = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TableColumn:cell_kind", "rerun.blueprint.components.TableCellKind");

    /**
     * How to render the column's values.
     * 
     * If unset or {@code Auto}, the viewer infers the renderer from the component or Arrow datatype.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorCellKind() {
        return DESCRIPTOR_CELL_KIND;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorName(), descriptorEditable(), descriptorVisible(), descriptorCellKind());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (name != null) {
            batches.add(org.teamvan.moneyshift.components.Name.batch(descriptorName(), allocator, name));
        }
        if (editable != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.Editable.batch(descriptorEditable(), allocator, editable));
        }
        if (visible != null) {
            batches.add(org.teamvan.moneyshift.components.Visible.batch(descriptorVisible(), allocator, visible));
        }
        if (cellKind != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.TableCellKind.batch(descriptorCellKind(), allocator, cellKind));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.Name name;
        private org.teamvan.moneyshift.blueprint.components.Editable editable;
        private org.teamvan.moneyshift.components.Visible visible;
        private org.teamvan.moneyshift.blueprint.components.TableCellKind cellKind;
        private Builder() {}

        public Builder withName(org.teamvan.moneyshift.components.Name value) {
            this.name = value;
            return this;
        }

        public Builder withEditable(org.teamvan.moneyshift.blueprint.components.Editable value) {
            this.editable = value;
            return this;
        }

        public Builder withVisible(org.teamvan.moneyshift.components.Visible value) {
            this.visible = value;
            return this;
        }

        public Builder withCellKind(org.teamvan.moneyshift.blueprint.components.TableCellKind value) {
            this.cellKind = value;
            return this;
        }

        public TableColumn build() {
            return new TableColumn(name, editable, visible, cellKind);
        }
    }

    public static TableColumn of(
    ) {
        return new TableColumn(null, null, null, null);
    }
}
