// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A Quaternion represented by 4 real numbers.
 * 
 * Note: although the x,y,z,w components of the quaternion will be passed through to the
 * datastore as provided, when used in the Viewer Quaternions will always be normalized.
 */
public record Quaternion(
    float[] xyzw
) {
    public Quaternion {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof Quaternion that)) return false;
        return java.util.Arrays.equals(xyzw, that.xyzw);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(xyzw));
    }
    public static final java.lang.String NAME = "rerun.encodings.Quaternion";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(4);
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(4), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.FLOAT32, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Quaternion> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Quaternion value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Quaternion value) {
        {
            org.apache.arrow.vector.complex.FixedSizeListVector list0 = (org.apache.arrow.vector.complex.FixedSizeListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 0, value.xyzw()[0]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 1, value.xyzw()[1]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 2, value.xyzw()[2]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 3, value.xyzw()[3]);
        }
    }

    public static Quaternion of(float... values) {
        if (values.length != 4) throw new java.lang.IllegalArgumentException("Quaternion needs exactly 4 values, got " + values.length);
        return new Quaternion(values);
    }
}
