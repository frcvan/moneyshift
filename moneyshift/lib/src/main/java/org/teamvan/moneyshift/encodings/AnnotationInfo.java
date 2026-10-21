// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Annotation info annotating a class id or key-point id.
 * 
 * Color and label will be used to annotate entities/keypoints which reference the id.
 * The id refers either to a class or key-point id
 */
public record AnnotationInfo(
    /**
     * {@link org.teamvan.moneyshift.encodings.ClassId} or {@link org.teamvan.moneyshift.encodings.KeypointId} to which this annotation info belongs.
     */
    int id,
    /**
     * The label that will be shown in the UI.
     */
    org.teamvan.moneyshift.encodings.Utf8 label,
    /**
     * The color that will be applied to the annotated entity.
     */
    org.teamvan.moneyshift.encodings.Rgba32 color
) {
    public AnnotationInfo {
    }
    public static final java.lang.String NAME = "rerun.encodings.AnnotationInfo";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("id", false, org.teamvan.moneyshift.ArrowFields.UINT16, java.util.List.of()), org.teamvan.moneyshift.encodings.Utf8.arrowField("label", true), org.teamvan.moneyshift.encodings.Rgba32.arrowField("color", true)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<AnnotationInfo> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            AnnotationInfo value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, AnnotationInfo value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        int f0 = value.id();
        ((org.apache.arrow.vector.UInt2Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0)).setSafe(index, (int) f0);
        org.teamvan.moneyshift.encodings.Utf8 f1 = value.label();
        if (f1 == null) {
            ((org.apache.arrow.vector.VarCharVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1)).setNull(index);
        } else {
            org.teamvan.moneyshift.encodings.Utf8.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
        }
        org.teamvan.moneyshift.encodings.Rgba32 f2 = value.color();
        if (f2 == null) {
            ((org.apache.arrow.vector.UInt4Vector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(2)).setNull(index);
        } else {
            org.teamvan.moneyshift.encodings.Rgba32.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(2), index, f2);
        }
    }
}
