// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * The description of a semantic Class.
 * 
 * If an entity is annotated with a corresponding {@link org.teamvan.moneyshift.components.ClassId}, Rerun will use
 * the attached {@link org.teamvan.moneyshift.encodings.AnnotationInfo} to derive labels and colors.
 * 
 * Keypoints within an annotation class can similarly be annotated with a
 * {@link org.teamvan.moneyshift.components.KeypointId} in which case we should defer to the label and color for the
 * {@link org.teamvan.moneyshift.encodings.AnnotationInfo} specifically associated with the Keypoint.
 * 
 * Keypoints within the class can also be decorated with skeletal edges.
 * Keypoint-connections are pairs of {@link org.teamvan.moneyshift.components.KeypointId}s. If an edge is
 * defined, and both keypoints exist within the instance of the class, then the
 * keypoints should be connected with an edge. The edge should be labeled and
 * colored as described by the class's {@link org.teamvan.moneyshift.encodings.AnnotationInfo}.
 * 
 */
public record ClassDescription(
    /**
     * The {@link org.teamvan.moneyshift.encodings.AnnotationInfo} for the class.
     */
    org.teamvan.moneyshift.encodings.AnnotationInfo info,
    /**
     * The {@link org.teamvan.moneyshift.encodings.AnnotationInfo} for all of the keypoints.
     */
    java.util.List<org.teamvan.moneyshift.encodings.AnnotationInfo> keypointAnnotations,
    /**
     * The connections between keypoints.
     */
    java.util.List<org.teamvan.moneyshift.encodings.KeypointPair> keypointConnections
) {
    public ClassDescription {
        java.util.Objects.requireNonNull(info, "info");
        keypointAnnotations = java.util.List.copyOf(keypointAnnotations);
        keypointConnections = java.util.List.copyOf(keypointConnections);
    }
    public static final java.lang.String NAME = "rerun.encodings.ClassDescription";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.AnnotationInfo.arrowField("info", false), org.teamvan.moneyshift.ArrowFields.field("keypoint_annotations", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.encodings.AnnotationInfo.arrowField("item", false))), org.teamvan.moneyshift.ArrowFields.field("keypoint_connections", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.encodings.KeypointPair.arrowField("item", false)))));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ClassDescription> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ClassDescription value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ClassDescription value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.AnnotationInfo f0 = value.info();
        org.teamvan.moneyshift.encodings.AnnotationInfo.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        java.util.List<org.teamvan.moneyshift.encodings.AnnotationInfo> f1 = value.keypointAnnotations();
        {
            org.apache.arrow.vector.complex.ListVector list2 = (org.apache.arrow.vector.complex.ListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1);
            int start2 = list2.startNewValue(index);
            org.apache.arrow.vector.FieldVector child2 = list2.getDataVector();
            java.util.List<org.teamvan.moneyshift.encodings.AnnotationInfo> values2 = f1;
            for (int j2 = 0; j2 < values2.size(); j2++) {
                org.teamvan.moneyshift.encodings.AnnotationInfo.writeOne(child2, start2 + j2, values2.get(j2));
            }
            list2.endValue(index, values2.size());
        }
        java.util.List<org.teamvan.moneyshift.encodings.KeypointPair> f3 = value.keypointConnections();
        {
            org.apache.arrow.vector.complex.ListVector list4 = (org.apache.arrow.vector.complex.ListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(2);
            int start4 = list4.startNewValue(index);
            org.apache.arrow.vector.FieldVector child4 = list4.getDataVector();
            java.util.List<org.teamvan.moneyshift.encodings.KeypointPair> values4 = f3;
            for (int j4 = 0; j4 < values4.size(); j4++) {
                org.teamvan.moneyshift.encodings.KeypointPair.writeOne(child4, start4 + j4, values4.get(j4));
            }
            list4.endValue(index, values4.size());
        }
    }
}
