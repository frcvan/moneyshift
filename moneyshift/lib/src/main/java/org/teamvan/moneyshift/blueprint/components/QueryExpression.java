// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * An individual query expression used to filter a set of {@link org.teamvan.moneyshift.encodings.EntityPath}s.
 * 
 * Each expression is either an inclusion or an exclusion expression.
 * Inclusions start with an optional {@code +} and exclusions must start with a {@code -}.
 * 
 * Multiple expressions are combined together as part of {@link org.teamvan.moneyshift.blueprint.archetypes.ViewContents}.
 * 
 * The {@code /**} suffix matches the whole subtree, i.e. self and any child, recursively
 * ({@code /world/**} matches both {@code /world} and {@code /world/car/driver}).
 * Other uses of {@code *} are not (yet) supported.
 */
public record QueryExpression(
    org.teamvan.moneyshift.encodings.Utf8 filter
) implements org.teamvan.moneyshift.Component {
    public QueryExpression {
        java.util.Objects.requireNonNull(filter, "filter");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public QueryExpression(java.lang.String value) {
        this(new org.teamvan.moneyshift.encodings.Utf8(value));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.QueryExpression";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Utf8.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Utf8.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<QueryExpression> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, QueryExpression::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, QueryExpression value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, QueryExpression value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<QueryExpression> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            QueryExpression value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.VarCharVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.VarCharVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, QueryExpression value) {
        org.teamvan.moneyshift.encodings.Utf8.writeOne(vector, index, value.filter());
    }
}
