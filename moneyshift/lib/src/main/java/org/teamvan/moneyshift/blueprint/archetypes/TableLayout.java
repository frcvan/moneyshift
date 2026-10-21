// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Blueprint for displaying table records as rows and columns.
 * 
 * This archetype is stored at the entity {@code /table/layouts/table}.
 */
public record TableLayout(
    /**
     * Source columns visible by default and shown first, in display order.
     * 
     * Unmentioned columns retain the viewer visibility defaults and follow in default order.
     * Each source column may appear at most once.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.ColumnName> columnOrder
) implements org.teamvan.moneyshift.Archetype {
    public TableLayout {
        if (columnOrder != null) { columnOrder = java.util.List.copyOf(columnOrder); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.TableLayout";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLUMN_ORDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TableLayout:column_order", "rerun.blueprint.components.ColumnName");

    /**
     * Source columns visible by default and shown first, in display order.
     * 
     * Unmentioned columns retain the viewer visibility defaults and follow in default order.
     * Each source column may appear at most once.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColumnOrder() {
        return DESCRIPTOR_COLUMN_ORDER;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorColumnOrder());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (columnOrder != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ColumnName.batch(descriptorColumnOrder(), allocator, columnOrder));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.blueprint.components.ColumnName> columnOrder;
        private Builder() {}

        public Builder withColumnOrder(java.util.List<org.teamvan.moneyshift.blueprint.components.ColumnName> value) {
            this.columnOrder = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withColumnOrder(java.lang.String... value) {
            java.util.List<org.teamvan.moneyshift.blueprint.components.ColumnName> values = new java.util.ArrayList<>(value.length);
            for (java.lang.String v : value) {
                values.add(new org.teamvan.moneyshift.blueprint.components.ColumnName(v));
            }
            return withColumnOrder(values);
        }

        public Builder withColumnOrder(org.teamvan.moneyshift.blueprint.components.ColumnName... value) {
            return withColumnOrder(java.util.List.of(value));
        }

        public TableLayout build() {
            return new TableLayout(columnOrder);
        }
    }

    public static TableLayout of(
    ) {
        return new TableLayout(null);
    }
}
