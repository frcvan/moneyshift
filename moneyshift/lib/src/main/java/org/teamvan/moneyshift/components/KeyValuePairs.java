// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A map of string keys to string values.
 * 
 * This component can be used to attach arbitrary metadata or annotations to entities.
 * Each key-value pair is stored as a UTF-8 string mapping.
 */
public record KeyValuePairs(
    /**
     * The key-value pairs that make up this string map.
     */
    java.util.List<org.teamvan.moneyshift.encodings.Utf8Pair> pairs
) implements org.teamvan.moneyshift.Component {
    public KeyValuePairs {
        pairs = java.util.List.copyOf(pairs);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.KeyValuePairs";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.List();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.encodings.Utf8Pair.arrowField("item", false)));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<KeyValuePairs> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, KeyValuePairs::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, KeyValuePairs value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, KeyValuePairs value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<KeyValuePairs> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            KeyValuePairs value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.ListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.ListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, KeyValuePairs value) {
        {
            org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            java.util.List<org.teamvan.moneyshift.encodings.Utf8Pair> values0 = value.pairs();
            for (int j0 = 0; j0 < values0.size(); j0++) {
                org.teamvan.moneyshift.encodings.Utf8Pair.writeOne(child0, start0 + j0, values0.get(j0));
            }
            list0.endValue(index, values0.size());
        }
    }
}
