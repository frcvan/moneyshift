// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * The container that sits at the root of a viewport.
 */
public record RootContainer(
    /**
     * {@code ContainerId} for the root.
     */
    org.teamvan.moneyshift.encodings.Uuid id
) implements org.teamvan.moneyshift.Component {
    public RootContainer {
        java.util.Objects.requireNonNull(id, "id");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public RootContainer(short[] bytes) {
        this(new org.teamvan.moneyshift.encodings.Uuid(bytes));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.RootContainer";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Uuid.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Uuid.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<RootContainer> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, RootContainer::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, RootContainer value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, RootContainer value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<RootContainer> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            RootContainer value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, RootContainer value) {
        org.teamvan.moneyshift.encodings.Uuid.writeOne(vector, index, value.id());
    }

    public static RootContainer of(short... values) {
        if (values.length != 16) throw new java.lang.IllegalArgumentException("RootContainer needs exactly 16 values, got " + values.length);
        return new RootContainer(new org.teamvan.moneyshift.encodings.Uuid(values));
    }
}
