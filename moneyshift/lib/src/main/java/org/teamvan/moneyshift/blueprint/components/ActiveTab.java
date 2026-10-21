// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * The active tab in a tabbed container.
 */
public record ActiveTab(
    /**
     * Which tab is currently active.
     * 
     * This should always correspond to a tab in the container.
     */
    org.teamvan.moneyshift.encodings.EntityPath tab
) implements org.teamvan.moneyshift.Component {
    public ActiveTab {
        java.util.Objects.requireNonNull(tab, "tab");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ActiveTab(java.lang.String path) {
        this(new org.teamvan.moneyshift.encodings.EntityPath(path));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.ActiveTab";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.EntityPath.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.EntityPath.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ActiveTab> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ActiveTab::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ActiveTab value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ActiveTab value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ActiveTab> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ActiveTab value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.VarCharVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.VarCharVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ActiveTab value) {
        org.teamvan.moneyshift.encodings.EntityPath.writeOne(vector, index, value.tab());
    }
}
