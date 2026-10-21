// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.testing.components;

public record AffixFuzzer11(
    java.util.List<java.lang.Float> manyFloatsOptional
) implements org.teamvan.moneyshift.Component {
    public AffixFuzzer11 {
        if (manyFloatsOptional != null) { manyFloatsOptional = java.util.List.copyOf(manyFloatsOptional); }
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.testing.components.AffixFuzzer11";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.List();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.FLOAT32, java.util.List.of())));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<AffixFuzzer11> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, AffixFuzzer11::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, AffixFuzzer11 value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, AffixFuzzer11 value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<AffixFuzzer11> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            AffixFuzzer11 value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.ListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.ListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, AffixFuzzer11 value) {
        {
            org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            java.util.List<java.lang.Float> values0 = value.manyFloatsOptional();
            for (int j0 = 0; j0 < values0.size(); j0++) {
                ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + j0, values0.get(j0));
            }
            list0.endValue(index, values0.size());
        }
    }
}
