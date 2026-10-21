// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * An edge in a graph connecting two nodes.
 */
public record GraphEdge(
    org.teamvan.moneyshift.encodings.Utf8Pair edge
) implements org.teamvan.moneyshift.Component {
    public GraphEdge {
        java.util.Objects.requireNonNull(edge, "edge");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.GraphEdge";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Utf8Pair.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Utf8Pair.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<GraphEdge> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, GraphEdge::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, GraphEdge value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, GraphEdge value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<GraphEdge> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            GraphEdge value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, GraphEdge value) {
        org.teamvan.moneyshift.encodings.Utf8Pair.writeOne(vector, index, value.edge());
    }
}
