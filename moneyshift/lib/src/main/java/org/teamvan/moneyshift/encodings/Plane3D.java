// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * An infinite 3D plane represented by a unit normal vector and a distance.
 * 
 * Any point P on the plane fulfills the equation {@code dot(xyz, P) - d = 0},
 * where {@code xyz} is the plane's normal and {@code d} the distance of the plane from the origin.
 * This representation is also known as the Hesse normal form.
 * 
 * Note: although the normal will be passed through to the
 * datastore as provided, when used in the Viewer, planes will always be normalized.
 * I.e. the plane with xyz = (2, 0, 0), d = 1 is equivalent to xyz = (1, 0, 0), d = 0.5
 */
public record Plane3D(
    float[] xyzd
) {
    public Plane3D {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof Plane3D that)) return false;
        return java.util.Arrays.equals(xyzd, that.xyzd);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(xyzd));
    }
    public static final java.lang.String NAME = "rerun.encodings.Plane3D";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(4);
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(4), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.FLOAT32, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Plane3D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Plane3D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Plane3D value) {
        {
            org.apache.arrow.vector.complex.FixedSizeListVector list0 = (org.apache.arrow.vector.complex.FixedSizeListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 0, value.xyzd()[0]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 1, value.xyzd()[1]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 2, value.xyzd()[2]);
            ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + 3, value.xyzd()[3]);
        }
    }

    public static Plane3D of(float... values) {
        if (values.length != 4) throw new java.lang.IllegalArgumentException("Plane3D needs exactly 4 values, got " + values.length);
        return new Plane3D(values);
    }
}
