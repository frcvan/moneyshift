// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A connection between two {@link org.teamvan.moneyshift.encodings.KeypointId}s.
 */
public record KeypointPair(
    /**
     * The first point of the pair.
     */
    org.teamvan.moneyshift.encodings.KeypointId keypoint0,
    /**
     * The second point of the pair.
     */
    org.teamvan.moneyshift.encodings.KeypointId keypoint1
) {
    public KeypointPair {
        java.util.Objects.requireNonNull(keypoint0, "keypoint0");
        java.util.Objects.requireNonNull(keypoint1, "keypoint1");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public KeypointPair(int keypoint0, int keypoint1) {
        this(new org.teamvan.moneyshift.encodings.KeypointId(keypoint0), new org.teamvan.moneyshift.encodings.KeypointId(keypoint1));
    }

    public static final java.lang.String NAME = "rerun.encodings.KeypointPair";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.KeypointId.arrowField("keypoint0", false), org.teamvan.moneyshift.encodings.KeypointId.arrowField("keypoint1", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<KeypointPair> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            KeypointPair value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, KeypointPair value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.KeypointId f0 = value.keypoint0();
        org.teamvan.moneyshift.encodings.KeypointId.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.encodings.KeypointId f1 = value.keypoint1();
        org.teamvan.moneyshift.encodings.KeypointId.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
