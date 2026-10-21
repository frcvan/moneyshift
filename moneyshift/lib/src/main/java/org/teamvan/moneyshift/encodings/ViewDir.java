// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * The six cardinal directions for 3D view-space.
 * 
 * Note that these are abstract directions and do on their own not correspond to any specific
 * coordinate system or numerical values.
 */
public enum ViewDir {
    /**
     * Up.
     */
    Up(1L),
    /**
     * Down.
     */
    Down(2L),
    /**
     * Right.
     */
    Right(3L),
    /**
     * Left.
     */
    Left(4L),
    /**
     * Forward.
     */
    Forward(5L),
    /**
     * Back.
     */
    Back(6L);

    private final long value;

    ViewDir(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static ViewDir fromValue(long value) {
        for (ViewDir candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown ViewDir value: " + value);
    }

    public static final java.lang.String NAME = "rerun.encodings.ViewDir";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ViewDir> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ViewDir value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ViewDir value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
