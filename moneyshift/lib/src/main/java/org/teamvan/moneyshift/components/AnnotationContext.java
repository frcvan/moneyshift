// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The annotation context provides additional information on how to display entities.
 * 
 * Entities can use {@link org.teamvan.moneyshift.encodings.ClassId}s and {@link org.teamvan.moneyshift.encodings.KeypointId}s to provide annotations, and
 * the labels and colors will be looked up in the appropriate
 * annotation context. We use the *first* annotation context we find in the
 * path-hierarchy when searching up through the ancestors of a given entity
 * path.
 */
public record AnnotationContext(
    /**
     * List of class descriptions, mapping class indices to class names, colors etc.
     */
    java.util.List<org.teamvan.moneyshift.encodings.ClassDescriptionMapElem> classMap
) implements org.teamvan.moneyshift.Component {
    public AnnotationContext {
        classMap = java.util.List.copyOf(classMap);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.AnnotationContext";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.List();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.encodings.ClassDescriptionMapElem.arrowField("item", false)));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<AnnotationContext> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, AnnotationContext::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, AnnotationContext value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, AnnotationContext value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<AnnotationContext> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            AnnotationContext value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.ListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.ListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, AnnotationContext value) {
        {
            org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            java.util.List<org.teamvan.moneyshift.encodings.ClassDescriptionMapElem> values0 = value.classMap();
            for (int j0 = 0; j0 < values0.size(); j0++) {
                org.teamvan.moneyshift.encodings.ClassDescriptionMapElem.writeOne(child0, start0 + j0, values0.get(j0));
            }
            list0.endValue(index, values0.size());
        }
    }
}
