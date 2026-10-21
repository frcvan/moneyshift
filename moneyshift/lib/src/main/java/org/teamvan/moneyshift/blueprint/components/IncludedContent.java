// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * All the contents in the container.
 */
public record IncludedContent(
    /**
     * List of the contents by {@link org.teamvan.moneyshift.encodings.EntityPath}.
     * 
     * This must be a path in the blueprint store.
     * Typically structure as {@code &lt;blueprint_registry&gt;/&lt;uuid&gt;}.
     */
    org.teamvan.moneyshift.encodings.EntityPath contents
) implements org.teamvan.moneyshift.Component {
    public IncludedContent {
        java.util.Objects.requireNonNull(contents, "contents");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public IncludedContent(java.lang.String path) {
        this(new org.teamvan.moneyshift.encodings.EntityPath(path));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.IncludedContent";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.EntityPath.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.EntityPath.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<IncludedContent> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, IncludedContent::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, IncludedContent value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, IncludedContent value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<IncludedContent> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            IncludedContent value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.VarCharVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.VarCharVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, IncludedContent value) {
        org.teamvan.moneyshift.encodings.EntityPath.writeOne(vector, index, value.contents());
    }
}
