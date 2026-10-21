// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * If playing, whether and how the playback time should loop.
 */
public enum LoopMode implements org.teamvan.moneyshift.Component {
    /**
     * Looping is off.
     */
    Off(1L),
    /**
     * We are looping within the current loop selection.
     */
    Selection(2L),
    /**
     * We are looping the entire recording.
     * 
     * The loop selection is ignored.
     */
    All(3L);

    private final long value;

    LoopMode(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static LoopMode fromValue(long value) {
        for (LoopMode candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown LoopMode value: " + value);
    }

    public static final java.lang.String NAME = "rerun.blueprint.components.LoopMode";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.LoopMode";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<LoopMode> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, LoopMode::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, LoopMode value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, LoopMode value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<LoopMode> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            LoopMode value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, LoopMode value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
