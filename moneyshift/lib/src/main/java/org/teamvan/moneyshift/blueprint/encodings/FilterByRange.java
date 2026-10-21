// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.encodings;

/**
 * Configuration for the filter-by-range feature of the dataframe view.
 */
public record FilterByRange(
    /**
     * Beginning of the time range.
     */
    org.teamvan.moneyshift.encodings.TimeInt start,
    /**
     * End of the time range (inclusive).
     */
    org.teamvan.moneyshift.encodings.TimeInt end
) {
    public FilterByRange {
        java.util.Objects.requireNonNull(start, "start");
        java.util.Objects.requireNonNull(end, "end");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public FilterByRange(long start, long end) {
        this(new org.teamvan.moneyshift.encodings.TimeInt(start), new org.teamvan.moneyshift.encodings.TimeInt(end));
    }

    public static final java.lang.String NAME = "rerun.blueprint.encodings.FilterByRange";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.TimeInt.arrowField("start", false), org.teamvan.moneyshift.encodings.TimeInt.arrowField("end", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<FilterByRange> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            FilterByRange value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, FilterByRange value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.TimeInt f0 = value.start();
        org.teamvan.moneyshift.encodings.TimeInt.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.encodings.TimeInt f1 = value.end();
        org.teamvan.moneyshift.encodings.TimeInt.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
