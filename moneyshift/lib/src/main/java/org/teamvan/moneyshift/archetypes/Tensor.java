// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * An N-dimensional array of numbers.
 * 
 * 
 * 
 */
public record Tensor(
    /**
     * The tensor data
     */
    org.teamvan.moneyshift.components.TensorData data,
    /**
     * The expected range of values.
     * 
     * This is typically the expected range of valid values.
     * Everything outside of the range is clamped to the range for the purpose of colormpaping.
     * Any colormap applied for display, will map this range.
     * 
     * If not specified, the range will be automatically estimated from the data.
     * Note that the Viewer may try to guess a wider range than the minimum/maximum of values
     * in the contents of the tensor.
     * E.g. if all values are positive, some bigger than 1.0 and all smaller than 255.0,
     * the Viewer will guess that the data likely came from an 8bit image, thus assuming a range of 0-255.
     */
    org.teamvan.moneyshift.components.ValueRange valueRange
) implements org.teamvan.moneyshift.Archetype {
    public Tensor {
        java.util.Objects.requireNonNull(data, "data");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Tensor";
    public static final int NUM_COMPONENTS = 2;
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_DATA = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Tensor:data", "rerun.components.TensorData");

    /**
     * The tensor data
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorData() {
        return DESCRIPTOR_DATA;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_VALUE_RANGE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "Tensor:value_range", "rerun.components.ValueRange");

    /**
     * The expected range of values.
     * 
     * This is typically the expected range of valid values.
     * Everything outside of the range is clamped to the range for the purpose of colormpaping.
     * Any colormap applied for display, will map this range.
     * 
     * If not specified, the range will be automatically estimated from the data.
     * Note that the Viewer may try to guess a wider range than the minimum/maximum of values
     * in the contents of the tensor.
     * E.g. if all values are positive, some bigger than 1.0 and all smaller than 255.0,
     * the Viewer will guess that the data likely came from an 8bit image, thus assuming a range of 0-255.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorValueRange() {
        return DESCRIPTOR_VALUE_RANGE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorData());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of();
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorValueRange());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.TensorData.batch(descriptorData(), allocator, data));
        if (valueRange != null) {
            batches.add(org.teamvan.moneyshift.components.ValueRange.batch(descriptorValueRange(), allocator, valueRange));
        }
        return batches;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.TensorData data;
        private org.teamvan.moneyshift.components.ValueRange valueRange;
        private Builder() {}

        public Builder withData(org.teamvan.moneyshift.components.TensorData value) {
            this.data = value;
            return this;
        }

        public Builder withValueRange(org.teamvan.moneyshift.components.ValueRange value) {
            this.valueRange = value;
            return this;
        }

        public Tensor build() {
            return new Tensor(data, valueRange);
        }
    }

    public static Tensor of(
        org.teamvan.moneyshift.components.TensorData data
    ) {
        return new Tensor(data, null);
    }
}
