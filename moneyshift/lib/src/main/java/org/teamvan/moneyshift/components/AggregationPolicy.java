// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Policy for aggregation of multiple scalar plot values.
 * 
 * This is used for lines in plots when the X axis distance of individual points goes below a single pixel,
 * i.e. a single pixel covers more than one tick worth of data. It can greatly improve performance
 * (and readability) in such situations as it prevents overdraw.
 */
public enum AggregationPolicy implements org.teamvan.moneyshift.Component {
    /**
     * No aggregation.
     */
    Off(1L),
    /**
     * Average all points in the range together.
     */
    Average(2L),
    /**
     * Keep only the maximum values in the range.
     */
    Max(3L),
    /**
     * Keep only the minimum values in the range.
     */
    Min(4L),
    /**
     * Keep both the minimum and maximum values in the range.
     * 
     * This will yield two aggregated points instead of one, effectively creating a vertical line.
     */
    MinMax(5L),
    /**
     * Find both the minimum and maximum values in the range, then use the average of those.
     */
    MinMaxAverage(6L);

    private final long value;

    AggregationPolicy(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static AggregationPolicy fromValue(long value) {
        for (AggregationPolicy candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown AggregationPolicy value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.AggregationPolicy";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.AggregationPolicy";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<AggregationPolicy> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, AggregationPolicy::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, AggregationPolicy value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, AggregationPolicy value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<AggregationPolicy> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            AggregationPolicy value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, AggregationPolicy value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
