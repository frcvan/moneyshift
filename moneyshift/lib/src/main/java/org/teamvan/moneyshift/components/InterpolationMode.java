// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Specifies how values between data points are interpolated in time series.
 */
public enum InterpolationMode implements org.teamvan.moneyshift.Component {
    /**
     * Connect data points with straight line segments.
     */
    Linear(1L),
    /**
     * Hold the previous value until the next data point, then jump.
     * 
     * The step occurs at the end of the interval.
     */
    StepAfter(2L),
    /**
     * Jump to the new value immediately, then hold until the next data point.
     * 
     * The step occurs at the beginning of the interval.
     */
    StepBefore(3L),
    /**
     * Hold the previous value until the midpoint between data points, then jump to the new value.
     * 
     * The step occurs at the midpoint of the interval.
     */
    StepMid(4L);

    private final long value;

    InterpolationMode(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static InterpolationMode fromValue(long value) {
        for (InterpolationMode candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown InterpolationMode value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.InterpolationMode";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.InterpolationMode";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<InterpolationMode> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, InterpolationMode::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, InterpolationMode value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, InterpolationMode value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<InterpolationMode> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            InterpolationMode value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, InterpolationMode value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
