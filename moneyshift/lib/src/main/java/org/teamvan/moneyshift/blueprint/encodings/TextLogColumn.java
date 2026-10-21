// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.encodings;

/**
 * A text log column.
 */
public record TextLogColumn(
    /**
     * Is this column visible?
     * 
     * Defaults to true.
     */
    org.teamvan.moneyshift.encodings.Bool visible,
    /**
     * What kind of column is this?
     */
    org.teamvan.moneyshift.blueprint.encodings.TextLogColumnKind kind
) {
    public TextLogColumn {
        java.util.Objects.requireNonNull(visible, "visible");
        java.util.Objects.requireNonNull(kind, "kind");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public TextLogColumn(boolean visible, org.teamvan.moneyshift.blueprint.encodings.TextLogColumnKind kind) {
        this(new org.teamvan.moneyshift.encodings.Bool(visible), kind);
    }

    public static final java.lang.String NAME = "rerun.blueprint.encodings.TextLogColumn";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.Bool.arrowField("visible", false), org.teamvan.moneyshift.blueprint.encodings.TextLogColumnKind.arrowField("kind", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TextLogColumn> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TextLogColumn value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TextLogColumn value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.Bool f0 = value.visible();
        org.teamvan.moneyshift.encodings.Bool.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.blueprint.encodings.TextLogColumnKind f1 = value.kind();
        org.teamvan.moneyshift.blueprint.encodings.TextLogColumnKind.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
