// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A helper type for mapping {@link org.teamvan.moneyshift.encodings.ClassId}s to class descriptions.
 * 
 * This is internal to {@link org.teamvan.moneyshift.components.AnnotationContext}.
 */
public record ClassDescriptionMapElem(
    /**
     * The key: the {@link org.teamvan.moneyshift.components.ClassId}.
     */
    org.teamvan.moneyshift.encodings.ClassId classId,
    /**
     * The value: class name, color, etc.
     */
    org.teamvan.moneyshift.encodings.ClassDescription classDescription
) {
    public ClassDescriptionMapElem {
        java.util.Objects.requireNonNull(classId, "classId");
        java.util.Objects.requireNonNull(classDescription, "classDescription");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public ClassDescriptionMapElem(int classId, org.teamvan.moneyshift.encodings.ClassDescription classDescription) {
        this(new org.teamvan.moneyshift.encodings.ClassId(classId), classDescription);
    }

    public static final java.lang.String NAME = "rerun.encodings.ClassDescriptionMapElem";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.ClassId.arrowField("class_id", false), org.teamvan.moneyshift.encodings.ClassDescription.arrowField("class_description", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ClassDescriptionMapElem> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ClassDescriptionMapElem value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ClassDescriptionMapElem value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.ClassId f0 = value.classId();
        org.teamvan.moneyshift.encodings.ClassId.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.encodings.ClassDescription f1 = value.classDescription();
        org.teamvan.moneyshift.encodings.ClassDescription.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
