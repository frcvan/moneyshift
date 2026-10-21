// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Visible time range bounds for a specific timeline.
 */
public record TimeRange(
    /**
     * Low time boundary for sequence timeline.
     */
    org.teamvan.moneyshift.encodings.TimeRangeBoundary start,
    /**
     * High time boundary for sequence timeline.
     */
    org.teamvan.moneyshift.encodings.TimeRangeBoundary end
) {
    public TimeRange {
        java.util.Objects.requireNonNull(start, "start");
        java.util.Objects.requireNonNull(end, "end");
    }
    public static final java.lang.String NAME = "rerun.encodings.TimeRange";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.TimeRangeBoundary.arrowField("start", false), org.teamvan.moneyshift.encodings.TimeRangeBoundary.arrowField("end", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TimeRange> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TimeRange value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TimeRange value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.TimeRangeBoundary f0 = value.start();
        org.teamvan.moneyshift.encodings.TimeRangeBoundary.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.encodings.TimeRangeBoundary f1 = value.end();
        org.teamvan.moneyshift.encodings.TimeRangeBoundary.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
