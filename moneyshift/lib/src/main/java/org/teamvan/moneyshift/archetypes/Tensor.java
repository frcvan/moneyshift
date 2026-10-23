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

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (true) {
            if (components.length() > 0) components.append(',');
            components.append("data").append('=').append(org.teamvan.moneyshift.components.TensorData.COMPONENT_TYPE);
        }
        if (valueRange != null) {
            if (components.length() > 0) components.append(',');
            components.append("value_range").append('=').append(org.teamvan.moneyshift.components.ValueRange.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (true) {
            org.teamvan.moneyshift.components.TensorData.writeColumnsStatic(w, "data", java.util.List.of(data));
        }
        if (valueRange != null) {
            org.teamvan.moneyshift.components.ValueRange.writeColumnsStatic(w, "value_range", java.util.List.of(valueRange));
        }
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
