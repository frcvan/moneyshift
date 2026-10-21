// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Specifies relation a spatial transform describes.
 */
public enum TransformRelation implements org.teamvan.moneyshift.Component {
    /**
     * The transform describes how to transform into the parent entity's space.
     * 
     * E.g. a translation of (0, 1, 0) with this {@link org.teamvan.moneyshift.components.TransformRelation} logged at {@code parent/child} means
     * that from the point of view of {@code parent}, {@code parent/child} is translated 1 unit along {@code parent}'s Y axis.
     * From perspective of {@code parent/child}, the {@code parent} entity is translated -1 unit along {@code parent/child}'s Y axis.
     */
    ParentFromChild(1L),
    /**
     * The transform describes how to transform into the child entity's space.
     * 
     * E.g. a translation of (0, 1, 0) with this {@link org.teamvan.moneyshift.components.TransformRelation} logged at {@code parent/child} means
     * that from the point of view of {@code parent}, {@code parent/child} is translated -1 unit along {@code parent}'s Y axis.
     * From perspective of {@code parent/child}, the {@code parent} entity is translated 1 unit along {@code parent/child}'s Y axis.
     */
    ChildFromParent(2L);

    private final long value;

    TransformRelation(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static TransformRelation fromValue(long value) {
        for (TransformRelation candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown TransformRelation value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.TransformRelation";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.TransformRelation";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<TransformRelation> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, TransformRelation::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, TransformRelation value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, TransformRelation value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TransformRelation> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TransformRelation value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TransformRelation value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
