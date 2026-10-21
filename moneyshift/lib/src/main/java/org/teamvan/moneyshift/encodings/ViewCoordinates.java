// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * An orientation convention for three-dimensional coordinates.
 * 
 * The three directions are always ordered as The three directions are always ordered as [x, y, z] and specify where each positive axis points.
 * For example, For example, [Right, Down, Forward] means that +X points right, +Y points down, and +Z points forward.
 * 
 * ⚠ <a href="https://github.com/rerun-io/rerun/issues/5032">Rerun does not yet support left-handed coordinate systems</a>.
 * 
 */
public record ViewCoordinates(
    /**
     * The directions of the The directions of the [x, y, z] axes.
     */
    org.teamvan.moneyshift.encodings.ViewDir[] coordinates
) {
    public ViewCoordinates {
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (this == other) return true;
        if (!(other instanceof ViewCoordinates that)) return false;
        return java.util.Arrays.equals(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(java.util.Arrays.hashCode(coordinates));
    }
    public static final java.lang.String NAME = "rerun.encodings.ViewCoordinates";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(3);
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeList(3), java.util.List.of(org.teamvan.moneyshift.encodings.ViewDir.arrowField("item", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ViewCoordinates> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ViewCoordinates value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ViewCoordinates value) {
        {
            org.apache.arrow.vector.complex.FixedSizeListVector list0 = (org.apache.arrow.vector.complex.FixedSizeListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            org.teamvan.moneyshift.encodings.ViewDir.writeOne(child0, start0 + 0, value.coordinates()[0]);
            org.teamvan.moneyshift.encodings.ViewDir.writeOne(child0, start0 + 1, value.coordinates()[1]);
            org.teamvan.moneyshift.encodings.ViewDir.writeOne(child0, start0 + 2, value.coordinates()[2]);
        }
    }
}
