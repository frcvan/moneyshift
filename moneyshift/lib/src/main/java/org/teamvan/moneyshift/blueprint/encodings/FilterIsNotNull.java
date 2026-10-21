// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.encodings;

/**
 * Configuration for the filter is not null feature of the dataframe view.
 */
public record FilterIsNotNull(
    /**
     * Whether the filter by event feature is active.
     */
    org.teamvan.moneyshift.encodings.Bool active,
    /**
     * The column used when the filter by event feature is used.
     */
    org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector column
) {
    public FilterIsNotNull {
        java.util.Objects.requireNonNull(active, "active");
        java.util.Objects.requireNonNull(column, "column");
    }
    public static final java.lang.String NAME = "rerun.blueprint.encodings.FilterIsNotNull";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.Bool.arrowField("active", false), org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector.arrowField("column", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<FilterIsNotNull> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            FilterIsNotNull value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, FilterIsNotNull value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.Bool f0 = value.active();
        org.teamvan.moneyshift.encodings.Bool.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector f1 = value.column();
        org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
