// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The visual appearance of a point in e.g. a 2D plot.
 */
public enum MarkerShape implements org.teamvan.moneyshift.Component {
    /**
     * {@code ⏺}
     */
    Circle(1L),
    /**
     * {@code ◆}
     */
    Diamond(2L),
    /**
     * {@code ◼️}
     */
    Square(3L),
    /**
     * {@code x}
     */
    Cross(4L),
    /**
     * {@code +}
     */
    Plus(5L),
    /**
     * {@code ▲}
     */
    Up(6L),
    /**
     * {@code ▼}
     */
    Down(7L),
    /**
     * {@code ◀}
     */
    Left(8L),
    /**
     * {@code ▶}
     */
    Right(9L),
    /**
     * {@code *}
     */
    Asterisk(10L);

    private final long value;

    MarkerShape(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static MarkerShape fromValue(long value) {
        for (MarkerShape candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown MarkerShape value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.MarkerShape";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.MarkerShape";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<MarkerShape> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, MarkerShape::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, MarkerShape value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, MarkerShape value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<MarkerShape> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            MarkerShape value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, MarkerShape value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
