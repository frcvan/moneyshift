// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * The current play state.
 */
public enum PlayState implements org.teamvan.moneyshift.Component {
    /**
     * Time doesn't move.
     */
    Paused(1L),
    /**
     * Time move steadily.
     */
    Playing(2L),
    /**
     * Follow the latest available data.
     */
    Following(3L);

    private final long value;

    PlayState(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static PlayState fromValue(long value) {
        for (PlayState candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown PlayState value: " + value);
    }

    public static final java.lang.String NAME = "rerun.blueprint.components.PlayState";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.PlayState";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<PlayState> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, PlayState::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, PlayState value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, PlayState value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<PlayState> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            PlayState value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, PlayState value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
