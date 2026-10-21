// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * Blueprint for displaying table records as cards.
 * 
 * This archetype is stored at the entity {@code /table/layouts/cards}.
 */
public record CardLayout(
    /**
     * The source column used for card titles.
     * 
     * If unset, the first visible string column is used as the title.
     */
    org.teamvan.moneyshift.blueprint.components.ColumnName title,
    /**
     * The source column containing the target opened when a card is activated.
     * 
     * If unset, the first configured preview field is used, then the first inferred URL column.
     */
    org.teamvan.moneyshift.blueprint.components.ColumnName link,
    /**
     * Source columns visible by default in each card, in display order.
     * 
     * Unlisted fields are hidden unless their {@link org.teamvan.moneyshift.blueprint.archetypes.TableColumn} visibility overrides the default.
     * Each source column may appear at most once.
     * 
     * Fields with {@code TableCellKind::Flag} are omitted from the labeled-field list and the first one is shown in the card header.
     * Card layouts currently support at most one flag field.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.ColumnName> fieldOrder
) implements org.teamvan.moneyshift.Archetype {
    public CardLayout {
        java.util.Objects.requireNonNull(fieldOrder, "fieldOrder");
        fieldOrder = java.util.List.copyOf(fieldOrder);
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.CardLayout";
    public static final int NUM_COMPONENTS = 3;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_TITLE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "CardLayout:title", "rerun.blueprint.components.ColumnName");

    /**
     * The source column used for card titles.
     * 
     * If unset, the first visible string column is used as the title.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorTitle() {
        return DESCRIPTOR_TITLE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_LINK = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "CardLayout:link", "rerun.blueprint.components.ColumnName");

    /**
     * The source column containing the target opened when a card is activated.
     * 
     * If unset, the first configured preview field is used, then the first inferred URL column.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorLink() {
        return DESCRIPTOR_LINK;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_FIELD_ORDER = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "CardLayout:field_order", "rerun.blueprint.components.ColumnName");

    /**
     * Source columns visible by default in each card, in display order.
     * 
     * Unlisted fields are hidden unless their {@link org.teamvan.moneyshift.blueprint.archetypes.TableColumn} visibility overrides the default.
     * Each source column may appear at most once.
     * 
     * Fields with {@code TableCellKind::Flag} are omitted from the labeled-field list and the first one is shown in the card header.
     * Card layouts currently support at most one flag field.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorFieldOrder() {
        return DESCRIPTOR_FIELD_ORDER;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorFieldOrder());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorTitle(), descriptorLink());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        if (title != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ColumnName.batch(descriptorTitle(), allocator, title));
        }
        if (link != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ColumnName.batch(descriptorLink(), allocator, link));
        }
        batches.add(org.teamvan.moneyshift.blueprint.components.ColumnName.batch(descriptorFieldOrder(), allocator, fieldOrder));
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.ColumnName title;
        private org.teamvan.moneyshift.blueprint.components.ColumnName link;
        private java.util.List<org.teamvan.moneyshift.blueprint.components.ColumnName> fieldOrder;
        private Builder() {}

        public Builder withTitle(org.teamvan.moneyshift.blueprint.components.ColumnName value) {
            this.title = value;
            return this;
        }

        public Builder withLink(org.teamvan.moneyshift.blueprint.components.ColumnName value) {
            this.link = value;
            return this;
        }

        public Builder withFieldOrder(java.util.List<org.teamvan.moneyshift.blueprint.components.ColumnName> value) {
            this.fieldOrder = java.util.List.copyOf(value);
            return this;
        }

        public Builder withFieldOrder(org.teamvan.moneyshift.blueprint.components.ColumnName... value) {
            return withFieldOrder(java.util.List.of(value));
        }

        public CardLayout build() {
            return new CardLayout(title, link, fieldOrder);
        }
    }

    public static CardLayout of(
        java.util.List<org.teamvan.moneyshift.blueprint.components.ColumnName> fieldOrder
    ) {
        return new CardLayout(null, null, fieldOrder);
    }
}
