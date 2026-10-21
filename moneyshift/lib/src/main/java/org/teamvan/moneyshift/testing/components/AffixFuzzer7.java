// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.components;

public record AffixFuzzer7(
    java.util.List<org.teamvan.moneyshift.testing.encodings.MixedFields> manyOptional
) implements org.teamvan.moneyshift.Component {
    public AffixFuzzer7 {
        if (manyOptional != null) { manyOptional = java.util.List.copyOf(manyOptional); }
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.testing.components.AffixFuzzer7";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.List();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.testing.encodings.MixedFields.arrowField("item", false)));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<AffixFuzzer7> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, AffixFuzzer7::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, AffixFuzzer7 value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, AffixFuzzer7 value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<AffixFuzzer7> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            AffixFuzzer7 value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.ListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.ListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, AffixFuzzer7 value) {
        {
            org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            java.util.List<org.teamvan.moneyshift.testing.encodings.MixedFields> values0 = value.manyOptional();
            for (int j0 = 0; j0 < values0.size(); j0++) {
                org.teamvan.moneyshift.testing.encodings.MixedFields.writeOne(child0, start0 + j0, values0.get(j0));
            }
            list0.endValue(index, values0.size());
        }
    }
}
