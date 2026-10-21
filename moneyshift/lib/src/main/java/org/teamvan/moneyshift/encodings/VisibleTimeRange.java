// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Visible time range bounds for a specific timeline.
 * 
 */
public record VisibleTimeRange(
    /**
     * Name of the timeline this applies to.
     */
    org.teamvan.moneyshift.encodings.Utf8 timeline,
    /**
     * Time range to use for this timeline.
     */
    org.teamvan.moneyshift.encodings.TimeRange range
) {
    public VisibleTimeRange {
        java.util.Objects.requireNonNull(timeline, "timeline");
        java.util.Objects.requireNonNull(range, "range");
    }
    public static final java.lang.String NAME = "rerun.encodings.VisibleTimeRange";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.Utf8.arrowField("timeline", false), org.teamvan.moneyshift.encodings.TimeRange.arrowField("range", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<VisibleTimeRange> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            VisibleTimeRange value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, VisibleTimeRange value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.Utf8 f0 = value.timeline();
        org.teamvan.moneyshift.encodings.Utf8.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.encodings.TimeRange f1 = value.range();
        org.teamvan.moneyshift.encodings.TimeRange.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
