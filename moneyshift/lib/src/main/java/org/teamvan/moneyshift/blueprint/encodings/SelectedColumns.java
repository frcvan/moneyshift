// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.encodings;

/**
 * List of selected columns in a dataframe.
 */
public record SelectedColumns(
    /**
     * The time columns to include
     */
    java.util.List<org.teamvan.moneyshift.encodings.Utf8> timeColumns,
    /**
     * The component columns to include
     */
    java.util.List<org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector> componentColumns
) {
    public SelectedColumns {
        timeColumns = java.util.List.copyOf(timeColumns);
        componentColumns = java.util.List.copyOf(componentColumns);
    }
    public static final java.lang.String NAME = "rerun.blueprint.encodings.SelectedColumns";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("time_columns", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.encodings.Utf8.arrowField("item", false))), org.teamvan.moneyshift.ArrowFields.field("component_columns", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector.arrowField("item", false)))));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<SelectedColumns> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            SelectedColumns value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, SelectedColumns value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        java.util.List<org.teamvan.moneyshift.encodings.Utf8> f0 = value.timeColumns();
        {
            org.apache.arrow.vector.complex.ListVector list1 = (org.apache.arrow.vector.complex.ListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0);
            int start1 = list1.startNewValue(index);
            org.apache.arrow.vector.FieldVector child1 = list1.getDataVector();
            java.util.List<org.teamvan.moneyshift.encodings.Utf8> values1 = f0;
            for (int j1 = 0; j1 < values1.size(); j1++) {
                org.teamvan.moneyshift.encodings.Utf8.writeOne(child1, start1 + j1, values1.get(j1));
            }
            list1.endValue(index, values1.size());
        }
        java.util.List<org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector> f2 = value.componentColumns();
        {
            org.apache.arrow.vector.complex.ListVector list3 = (org.apache.arrow.vector.complex.ListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1);
            int start3 = list3.startNewValue(index);
            org.apache.arrow.vector.FieldVector child3 = list3.getDataVector();
            java.util.List<org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector> values3 = f2;
            for (int j3 = 0; j3 < values3.size(); j3++) {
                org.teamvan.moneyshift.blueprint.encodings.ComponentColumnSelector.writeOne(child3, start3 + j3, values3.get(j3));
            }
            list3.endValue(index, values3.size());
        }
    }
}
