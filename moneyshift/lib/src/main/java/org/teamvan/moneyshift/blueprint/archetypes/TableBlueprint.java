// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Blueprint for configuring the styling of a table.
 * 
 * The table blueprint as a whole is distributed across these entity paths:
 * * {@code /table} for this archetype and {@link org.teamvan.moneyshift.blueprint.archetypes.PreviewsConfig}.
 * * {@code /table/layouts/table} for {@link org.teamvan.moneyshift.blueprint.archetypes.TableLayout}.
 * * {@code /table/layouts/table/columns/{column_name}} for table {@link org.teamvan.moneyshift.blueprint.archetypes.TableColumn} archetypes and per-column options such as {@link org.teamvan.moneyshift.blueprint.archetypes.TableColumnPreview}.
 * * {@code /table/layouts/cards} for {@link org.teamvan.moneyshift.blueprint.archetypes.CardLayout}.
 * * {@code /table/layouts/cards/fields/{column_name}} for card {@link org.teamvan.moneyshift.blueprint.archetypes.TableColumn} archetypes and per-field options such as {@link org.teamvan.moneyshift.blueprint.archetypes.TableColumnPreview}.
 * * {@code /view/{view_id}} for preview {@link org.teamvan.moneyshift.blueprint.archetypes.ViewBlueprint} definitions.
 */
public record TableBlueprint(
    /**
     * The currently selected layout.
     * 
     * If unset, defaults to card layout if available.
     * {@code Cards} falls back to table layout when no {@link org.teamvan.moneyshift.blueprint.archetypes.CardLayout} is configured.
     */
    org.teamvan.moneyshift.blueprint.components.TableLayoutKind layout
) implements org.teamvan.moneyshift.Archetype {
    public TableBlueprint {
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.TableBlueprint";
    public static final int NUM_COMPONENTS = 1;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LAYOUT = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "TableBlueprint:layout", "rerun.blueprint.components.TableLayoutKind");

    /**
     * The currently selected layout.
     * 
     * If unset, defaults to card layout if available.
     * {@code Cards} falls back to table layout when no {@link org.teamvan.moneyshift.blueprint.archetypes.CardLayout} is configured.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLayout() {
        return DESCRIPTOR_LAYOUT;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorLayout());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (layout != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.TableLayoutKind.batch(descriptorLayout(), allocator, layout));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.TableLayoutKind layout;
        private Builder() {}

        public Builder withLayout(org.teamvan.moneyshift.blueprint.components.TableLayoutKind value) {
            this.layout = value;
            return this;
        }

        public TableBlueprint build() {
            return new TableBlueprint(layout);
        }
    }

    public static TableBlueprint of(
    ) {
        return new TableBlueprint(null);
    }
}
