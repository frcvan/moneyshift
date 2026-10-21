// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Specifies if a graph has directed or undirected edges.
 */
public enum GraphType implements org.teamvan.moneyshift.Component {
    /**
     * The graph has undirected edges.
     */
    Undirected(1L),
    /**
     * The graph has directed edges.
     */
    Directed(2L);

    private final long value;

    GraphType(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static GraphType fromValue(long value) {
        for (GraphType candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown GraphType value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.GraphType";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.GraphType";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<GraphType> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, GraphType::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, GraphType value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, GraphType value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<GraphType> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            GraphType value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, GraphType value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
