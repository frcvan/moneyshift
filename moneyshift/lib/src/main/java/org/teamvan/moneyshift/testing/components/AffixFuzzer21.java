// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.components;

public record AffixFuzzer21(
    org.teamvan.moneyshift.testing.encodings.Float16Fields nestedHalves
) implements org.teamvan.moneyshift.Component {
    public AffixFuzzer21 {
        java.util.Objects.requireNonNull(nestedHalves, "nestedHalves");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.testing.components.AffixFuzzer21";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.testing.encodings.Float16Fields.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.testing.encodings.Float16Fields.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<AffixFuzzer21> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, AffixFuzzer21::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, AffixFuzzer21 value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, AffixFuzzer21 value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<AffixFuzzer21> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            AffixFuzzer21 value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, AffixFuzzer21 value) {
        org.teamvan.moneyshift.testing.encodings.Float16Fields.writeOne(vector, index, value.nestedHalves());
    }
}
