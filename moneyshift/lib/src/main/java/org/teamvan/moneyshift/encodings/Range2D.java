// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * An Axis-Aligned Bounding Box in 2D space, implemented as the minimum and maximum corners.
 */
public record Range2D(
    /**
     * The range of the X-axis (usually left and right bounds).
     */
    org.teamvan.moneyshift.encodings.Range1D xRange,
    /**
     * The range of the Y-axis (usually top and bottom bounds).
     */
    org.teamvan.moneyshift.encodings.Range1D yRange
) {
    public Range2D {
        java.util.Objects.requireNonNull(xRange, "xRange");
        java.util.Objects.requireNonNull(yRange, "yRange");
    }
    public static final java.lang.String NAME = "rerun.encodings.Range2D";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.Range1D.arrowField("x_range", false), org.teamvan.moneyshift.encodings.Range1D.arrowField("y_range", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Range2D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Range2D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Range2D value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.Range1D f0 = value.xRange();
        org.teamvan.moneyshift.encodings.Range1D.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.encodings.Range1D f1 = value.yRange();
        org.teamvan.moneyshift.encodings.Range1D.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
