// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * 3D rotation represented by a rotation around a given axis.
 */
public record RotationAxisAngle(
    /**
     * Axis to rotate around.
     * 
     * This is not required to be normalized.
     * However, if normalization of the rotation axis fails (typically due to a zero vector)
     * the rotation is treated as an invalid transform, unless the angle is zero in which case
     * it is treated as an identity.
     */
    org.teamvan.moneyshift.encodings.Vec3D axis,
    /**
     * How much to rotate around the axis.
     */
    org.teamvan.moneyshift.encodings.Angle angle
) {
    public RotationAxisAngle {
        java.util.Objects.requireNonNull(axis, "axis");
        java.util.Objects.requireNonNull(angle, "angle");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public RotationAxisAngle(org.teamvan.moneyshift.encodings.Vec3D axis, float angle) {
        this(axis, new org.teamvan.moneyshift.encodings.Angle(angle));
    }

    public static final java.lang.String NAME = "rerun.encodings.RotationAxisAngle";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.Vec3D.arrowField("axis", false), org.teamvan.moneyshift.encodings.Angle.arrowField("angle", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<RotationAxisAngle> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            RotationAxisAngle value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, RotationAxisAngle value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.Vec3D f0 = value.axis();
        org.teamvan.moneyshift.encodings.Vec3D.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.encodings.Angle f1 = value.angle();
        org.teamvan.moneyshift.encodings.Angle.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
