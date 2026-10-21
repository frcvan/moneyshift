// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * Controls how the plot tooltip behaves when hovering over data.
 */
public enum TooltipMode implements org.teamvan.moneyshift.Component {
    /**
     * Show only the nearest data point to the cursor.
     */
    Nearest(1L),
    /**
     * Show values of all visible series at the hovered time.
     */
    All(2L);

    private final long value;

    TooltipMode(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static TooltipMode fromValue(long value) {
        for (TooltipMode candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown TooltipMode value: " + value);
    }

    public static final java.lang.String NAME = "rerun.blueprint.components.TooltipMode";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.TooltipMode";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<TooltipMode> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, TooltipMode::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, TooltipMode value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, TooltipMode value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TooltipMode> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TooltipMode value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TooltipMode value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
