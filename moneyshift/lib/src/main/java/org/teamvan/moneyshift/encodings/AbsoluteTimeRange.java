// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Two {@link org.teamvan.moneyshift.encodings.TimeInt} describing a range of time.
 */
public record AbsoluteTimeRange(
    /**
     * Start of the range.
     */
    org.teamvan.moneyshift.encodings.TimeInt min,
    /**
     * End of the range (inclusive).
     */
    org.teamvan.moneyshift.encodings.TimeInt max
) {
    public AbsoluteTimeRange {
        java.util.Objects.requireNonNull(min, "min");
        java.util.Objects.requireNonNull(max, "max");
    }
    public static final java.lang.String NAME = "rerun.encodings.AbsoluteTimeRange";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.TimeInt.arrowField("min", false), org.teamvan.moneyshift.encodings.TimeInt.arrowField("max", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<AbsoluteTimeRange> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            AbsoluteTimeRange value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, AbsoluteTimeRange value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.TimeInt f0 = value.min();
        org.teamvan.moneyshift.encodings.TimeInt.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.encodings.TimeInt f1 = value.max();
        org.teamvan.moneyshift.encodings.TimeInt.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
