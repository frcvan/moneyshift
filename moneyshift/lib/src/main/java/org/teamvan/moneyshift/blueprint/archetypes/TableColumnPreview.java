// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Blueprint for previewing recording references from a table column.
 * 
 * This archetype is stored on the same layout-specific column entity as {@link org.teamvan.moneyshift.blueprint.archetypes.TableColumn}.
 * Table columns use {@code /table/layouts/table/columns/{column_name}}.
 * Card fields use {@code /table/layouts/cards/fields/{column_name}}.
 * Shared preview configuration is stored as {@link org.teamvan.moneyshift.blueprint.archetypes.PreviewsConfig} at {@code /table}.
 * 
 * A column uses this payload only when its {@code TableCellKind::Preview} is set.
 */
public record TableColumnPreview(
    /**
     * The views rendered for the preview, in display order.
     * 
     * Each {@link org.teamvan.moneyshift.blueprint.components.IncludedContent} must reference a {@link org.teamvan.moneyshift.blueprint.archetypes.ViewBlueprint} at {@code /view/{view_id}}.
     * View contents, properties, defaults, and overrides remain at their regular blueprint paths.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.IncludedContent> views
) implements org.teamvan.moneyshift.Archetype {
    public TableColumnPreview {
        java.util.Objects.requireNonNull(views, "views");
        views = java.util.List.copyOf(views);
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.TableColumnPreview";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VIEWS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TableColumnPreview:views", "rerun.blueprint.components.IncludedContent");

    /**
     * The views rendered for the preview, in display order.
     * 
     * Each {@link org.teamvan.moneyshift.blueprint.components.IncludedContent} must reference a {@link org.teamvan.moneyshift.blueprint.archetypes.ViewBlueprint} at {@code /view/{view_id}}.
     * View contents, properties, defaults, and overrides remain at their regular blueprint paths.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorViews() {
        return DESCRIPTOR_VIEWS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorViews());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of();
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.blueprint.components.IncludedContent.batch(descriptorViews(), allocator, views));
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.blueprint.components.IncludedContent> views;
        private Builder() {}

        public Builder withViews(java.util.List<org.teamvan.moneyshift.blueprint.components.IncludedContent> value) {
            this.views = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code java.lang.String} value(s) directly. */
        public Builder withViews(java.lang.String... value) {
            java.util.List<org.teamvan.moneyshift.blueprint.components.IncludedContent> values = new java.util.ArrayList<>(value.length);
            for (java.lang.String v : value) {
                values.add(new org.teamvan.moneyshift.blueprint.components.IncludedContent(v));
            }
            return withViews(values);
        }

        public Builder withViews(org.teamvan.moneyshift.blueprint.components.IncludedContent... value) {
            return withViews(java.util.List.of(value));
        }

        public TableColumnPreview build() {
            return new TableColumnPreview(views);
        }
    }

    public static TableColumnPreview of(
        java.util.List<org.teamvan.moneyshift.blueprint.components.IncludedContent> views
    ) {
        return new TableColumnPreview(views);
    }
}
