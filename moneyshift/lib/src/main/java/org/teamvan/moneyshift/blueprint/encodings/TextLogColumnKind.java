// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.encodings;

/**
 * A text log column kind.
 */
public enum TextLogColumnKind {
    /**
     * Which entity path this was logged to.
     */
    EntityPath(1L),
    /**
     * The log level, i.e INFO, WARN, ERROR.
     */
    LogLevel(2L),
    /**
     * The text message the log has.
     */
    Body(3L);

    private final long value;

    TextLogColumnKind(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static TextLogColumnKind fromValue(long value) {
        for (TextLogColumnKind candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown TextLogColumnKind value: " + value);
    }

    public static final java.lang.String NAME = "rerun.blueprint.encodings.TextLogColumnKind";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TextLogColumnKind> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TextLogColumnKind value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TextLogColumnKind value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
