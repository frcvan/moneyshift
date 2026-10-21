// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.archetypes;

/**
 * The description of a container.
 */
public record ContainerBlueprint(
    /**
     * The class of the view.
     */
    org.teamvan.moneyshift.blueprint.components.ContainerKind containerKind,
    /**
     * The name of the container.
     */
    org.teamvan.moneyshift.components.Name displayName,
    /**
     * {@code ContainerId}s or {@code ViewId}s that are children of this container.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.IncludedContent> contents,
    /**
     * The layout shares of each column in the container.
     * 
     * For {@link org.teamvan.moneyshift.blueprint.components.ContainerKind.Horizontal} containers, the length of this list should always match the number of contents.
     * 
     * Ignored for {@link org.teamvan.moneyshift.blueprint.components.ContainerKind.Vertical} containers.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.ColumnShare> colShares,
    /**
     * The layout shares of each row of the container.
     * 
     * For {@link org.teamvan.moneyshift.blueprint.components.ContainerKind.Vertical} containers, the length of this list should always match the number of contents.
     * 
     * Ignored for {@link org.teamvan.moneyshift.blueprint.components.ContainerKind.Horizontal} containers.
     */
    java.util.List<org.teamvan.moneyshift.blueprint.components.RowShare> rowShares,
    /**
     * Which tab is active.
     * 
     * Only applies to {@code Tabs} containers.
     */
    org.teamvan.moneyshift.blueprint.components.ActiveTab activeTab,
    /**
     * Whether this container is visible.
     * 
     * Defaults to true if not specified.
     */
    org.teamvan.moneyshift.components.Visible visible,
    /**
     * How many columns this grid should have.
     * 
     * If unset, the grid layout will be auto.
     * 
     * Ignored for {@link org.teamvan.moneyshift.blueprint.components.ContainerKind.Horizontal}/{@link org.teamvan.moneyshift.blueprint.components.ContainerKind.Vertical} containers.
     */
    org.teamvan.moneyshift.blueprint.components.GridColumns gridColumns
) implements org.teamvan.moneyshift.Archetype {
    public ContainerBlueprint {
        java.util.Objects.requireNonNull(containerKind, "containerKind");
        if (contents != null) { contents = java.util.List.copyOf(contents); }
        if (colShares != null) { colShares = java.util.List.copyOf(colShares); }
        if (rowShares != null) { rowShares = java.util.List.copyOf(rowShares); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.blueprint.archetypes.ContainerBlueprint";
    public static final int NUM_COMPONENTS = 8;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CONTAINER_KIND = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ContainerBlueprint:container_kind", "rerun.blueprint.components.ContainerKind");

    /**
     * The class of the view.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorContainerKind() {
        return DESCRIPTOR_CONTAINER_KIND;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DISPLAY_NAME = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ContainerBlueprint:display_name", "rerun.components.Name");

    /**
     * The name of the container.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorDisplayName() {
        return DESCRIPTOR_DISPLAY_NAME;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CONTENTS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ContainerBlueprint:contents", "rerun.blueprint.components.IncludedContent");

    /**
     * {@code ContainerId}s or {@code ViewId}s that are children of this container.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorContents() {
        return DESCRIPTOR_CONTENTS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COL_SHARES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ContainerBlueprint:col_shares", "rerun.blueprint.components.ColumnShare");

    /**
     * The layout shares of each column in the container.
     * 
     * For {@link org.teamvan.moneyshift.blueprint.components.ContainerKind.Horizontal} containers, the length of this list should always match the number of contents.
     * 
     * Ignored for {@link org.teamvan.moneyshift.blueprint.components.ContainerKind.Vertical} containers.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColShares() {
        return DESCRIPTOR_COL_SHARES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ROW_SHARES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ContainerBlueprint:row_shares", "rerun.blueprint.components.RowShare");

    /**
     * The layout shares of each row of the container.
     * 
     * For {@link org.teamvan.moneyshift.blueprint.components.ContainerKind.Vertical} containers, the length of this list should always match the number of contents.
     * 
     * Ignored for {@link org.teamvan.moneyshift.blueprint.components.ContainerKind.Horizontal} containers.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorRowShares() {
        return DESCRIPTOR_ROW_SHARES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_ACTIVE_TAB = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ContainerBlueprint:active_tab", "rerun.blueprint.components.ActiveTab");

    /**
     * Which tab is active.
     * 
     * Only applies to {@code Tabs} containers.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorActiveTab() {
        return DESCRIPTOR_ACTIVE_TAB;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VISIBLE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ContainerBlueprint:visible", "rerun.components.Visible");

    /**
     * Whether this container is visible.
     * 
     * Defaults to true if not specified.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorVisible() {
        return DESCRIPTOR_VISIBLE;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_GRID_COLUMNS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "ContainerBlueprint:grid_columns", "rerun.blueprint.components.GridColumns");

    /**
     * How many columns this grid should have.
     * 
     * If unset, the grid layout will be auto.
     * 
     * Ignored for {@link org.teamvan.moneyshift.blueprint.components.ContainerKind.Horizontal}/{@link org.teamvan.moneyshift.blueprint.components.ContainerKind.Vertical} containers.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorGridColumns() {
        return DESCRIPTOR_GRID_COLUMNS;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorContainerKind());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorDisplayName(), descriptorContents(), descriptorColShares(), descriptorRowShares(), descriptorActiveTab(), descriptorVisible(), descriptorGridColumns());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.blueprint.components.ContainerKind.batch(descriptorContainerKind(), allocator, containerKind));
        if (displayName != null) {
            batches.add(org.teamvan.moneyshift.components.Name.batch(descriptorDisplayName(), allocator, displayName));
        }
        if (contents != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.IncludedContent.batch(descriptorContents(), allocator, contents));
        }
        if (colShares != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ColumnShare.batch(descriptorColShares(), allocator, colShares));
        }
        if (rowShares != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.RowShare.batch(descriptorRowShares(), allocator, rowShares));
        }
        if (activeTab != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.ActiveTab.batch(descriptorActiveTab(), allocator, activeTab));
        }
        if (visible != null) {
            batches.add(org.teamvan.moneyshift.components.Visible.batch(descriptorVisible(), allocator, visible));
        }
        if (gridColumns != null) {
            batches.add(org.teamvan.moneyshift.blueprint.components.GridColumns.batch(descriptorGridColumns(), allocator, gridColumns));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.components.ContainerKind containerKind;
        private org.teamvan.moneyshift.components.Name displayName;
        private java.util.List<org.teamvan.moneyshift.blueprint.components.IncludedContent> contents;
        private java.util.List<org.teamvan.moneyshift.blueprint.components.ColumnShare> colShares;
        private java.util.List<org.teamvan.moneyshift.blueprint.components.RowShare> rowShares;
        private org.teamvan.moneyshift.blueprint.components.ActiveTab activeTab;
        private org.teamvan.moneyshift.components.Visible visible;
        private org.teamvan.moneyshift.blueprint.components.GridColumns gridColumns;
        private Builder() {}

        public Builder withContainerKind(org.teamvan.moneyshift.blueprint.components.ContainerKind value) {
            this.containerKind = value;
            return this;
        }

        public Builder withDisplayName(org.teamvan.moneyshift.components.Name value) {
            this.displayName = value;
            return this;
        }

        public Builder withContents(java.util.List<org.teamvan.moneyshift.blueprint.components.IncludedContent> value) {
            this.contents = java.util.List.copyOf(value);
            return this;
        }

        public Builder withContents(org.teamvan.moneyshift.blueprint.components.IncludedContent... value) {
            return withContents(java.util.List.of(value));
        }

        public Builder withColShares(java.util.List<org.teamvan.moneyshift.blueprint.components.ColumnShare> value) {
            this.colShares = java.util.List.copyOf(value);
            return this;
        }

        public Builder withColShares(org.teamvan.moneyshift.blueprint.components.ColumnShare... value) {
            return withColShares(java.util.List.of(value));
        }

        public Builder withRowShares(java.util.List<org.teamvan.moneyshift.blueprint.components.RowShare> value) {
            this.rowShares = java.util.List.copyOf(value);
            return this;
        }

        public Builder withRowShares(org.teamvan.moneyshift.blueprint.components.RowShare... value) {
            return withRowShares(java.util.List.of(value));
        }

        public Builder withActiveTab(org.teamvan.moneyshift.blueprint.components.ActiveTab value) {
            this.activeTab = value;
            return this;
        }

        public Builder withVisible(org.teamvan.moneyshift.components.Visible value) {
            this.visible = value;
            return this;
        }

        public Builder withGridColumns(org.teamvan.moneyshift.blueprint.components.GridColumns value) {
            this.gridColumns = value;
            return this;
        }

        public ContainerBlueprint build() {
            return new ContainerBlueprint(containerKind, displayName, contents, colShares, rowShares, activeTab, visible, gridColumns);
        }
    }

    public static ContainerBlueprint of(
        org.teamvan.moneyshift.blueprint.components.ContainerKind containerKind
    ) {
        return new ContainerBlueprint(containerKind, null, null, null, null, null, null, null);
    }
}
