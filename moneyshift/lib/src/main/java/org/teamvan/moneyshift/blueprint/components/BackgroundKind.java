// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * The type of the background in a view.
 */
public enum BackgroundKind implements org.teamvan.moneyshift.Component {
    /**
     * A dark gradient.
     * 
     * In 3D views it changes depending on the direction of the view.
     */
    GradientDark(1L),
    /**
     * A bright gradient.
     * 
     * In 3D views it changes depending on the direction of the view.
     */
    GradientBright(2L),
    /**
     * Simple uniform color.
     */
    SolidColor(3L);

    private final long value;

    BackgroundKind(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static BackgroundKind fromValue(long value) {
        for (BackgroundKind candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown BackgroundKind value: " + value);
    }

    public static final java.lang.String NAME = "rerun.blueprint.components.BackgroundKind";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.BackgroundKind";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<BackgroundKind> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, BackgroundKind::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, BackgroundKind value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, BackgroundKind value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<BackgroundKind> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            BackgroundKind value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, BackgroundKind value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
