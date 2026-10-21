// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Left or right boundary of a time range.
 */
public sealed interface TimeRangeBoundary permits
    TimeRangeBoundary.CursorRelative,
    TimeRangeBoundary.Absolute,
    TimeRangeBoundary.Infinite {
    /**
     * Boundary is a value relative to the time cursor.
     */
    record CursorRelative(org.teamvan.moneyshift.encodings.TimeInt value) implements TimeRangeBoundary {
        public CursorRelative { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * Boundary is an absolute value.
     */
    record Absolute(org.teamvan.moneyshift.encodings.TimeInt value) implements TimeRangeBoundary {
        public Absolute { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * The boundary extends to infinity.
     */
    record Infinite() implements TimeRangeBoundary {}

    java.lang.String NAME = "rerun.encodings.TimeRangeBoundary";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Union(org.apache.arrow.vector.types.UnionMode.Dense, new int[]{0, 1, 2, 3});
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Union(org.apache.arrow.vector.types.UnionMode.Dense, new int[]{0, 1, 2, 3}), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("_null_markers", true, org.teamvan.moneyshift.ArrowFields.NULL, java.util.List.of()), org.teamvan.moneyshift.encodings.TimeInt.arrowField("CursorRelative", false), org.teamvan.moneyshift.encodings.TimeInt.arrowField("Absolute", false), org.teamvan.moneyshift.ArrowFields.field("Infinite", true, org.teamvan.moneyshift.ArrowFields.NULL, java.util.List.of())));
    }

    public static int typeId(TimeRangeBoundary value) {
        if (value == null) return 0;
        return switch (value) {
            case TimeRangeBoundary.CursorRelative ignored -> 1;
            case TimeRangeBoundary.Absolute ignored -> 2;
            case TimeRangeBoundary.Infinite ignored -> 3;
        };
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TimeRangeBoundary> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            writeOne(vector, i, values.get(i));
        }
        ((org.apache.arrow.vector.complex.DenseUnionVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TimeRangeBoundary value) {
        org.apache.arrow.vector.complex.DenseUnionVector union = (org.apache.arrow.vector.complex.DenseUnionVector) vector;
        if (value == null) {
            org.apache.arrow.vector.FieldVector nullMarkers = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 0);
            int nullOffset = nullMarkers.getValueCount();
            union.setTypeId(index, (byte) 0);
            union.setOffset(index, nullOffset);
            nullMarkers.setValueCount(nullOffset + 1);
            return;
        }
        switch (value) {
            case TimeRangeBoundary.CursorRelative variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 1);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 1);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                org.teamvan.moneyshift.encodings.TimeInt.writeOne(child, offset, variant.value());
            }
            case TimeRangeBoundary.Absolute variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 2);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 2);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                org.teamvan.moneyshift.encodings.TimeInt.writeOne(child, offset, variant.value());
            }
            case TimeRangeBoundary.Infinite ignored -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 3);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 3);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
            }
        }
    }
}
