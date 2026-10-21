// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * Describe a component column to be selected in the dataframe view.
 */
public record ComponentColumnSelector(
    org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector selector
) implements org.teamvan.moneyshift.Component {
    public ComponentColumnSelector {
        java.util.Objects.requireNonNull(selector, "selector");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public ComponentColumnSelector(java.lang.String entityPath, java.lang.String component) {
        this(new org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector(new org.teamvan.moneyshift.encodings.EntityPath(entityPath), new org.teamvan.moneyshift.encodings.Utf8(component)));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.ComponentColumnSelector";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<ComponentColumnSelector> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, ComponentColumnSelector::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, ComponentColumnSelector value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, ComponentColumnSelector value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ComponentColumnSelector> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ComponentColumnSelector value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ComponentColumnSelector value) {
        org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector.writeOne(vector, index, value.selector());
    }
}
