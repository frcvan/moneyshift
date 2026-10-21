// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.components;

public record AffixFuzzer1(
    org.teamvan.moneyshift.testing.encodings.MixedFields singleRequired
) implements org.teamvan.moneyshift.Component {
    public AffixFuzzer1 {
        java.util.Objects.requireNonNull(singleRequired, "singleRequired");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.testing.components.AffixFuzzer1";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.testing.encodings.MixedFields.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.testing.encodings.MixedFields.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<AffixFuzzer1> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, AffixFuzzer1::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, AffixFuzzer1 value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, AffixFuzzer1 value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<AffixFuzzer1> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            AffixFuzzer1 value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, AffixFuzzer1 value) {
        org.teamvan.moneyshift.testing.encodings.MixedFields.writeOne(vector, index, value.singleRequired());
    }
}
