// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * Presentation timestamp within a {@link org.teamvan.moneyshift.archetypes.AssetVideo}.
 * 
 * Specified in nanoseconds.
 * Presentation timestamps are typically measured as time since video start.
 */
public record VideoTimestamp(
    /**
     * Presentation timestamp value in nanoseconds.
     */
    long timestampNs
) {
    public VideoTimestamp {
    }
    public static final java.lang.String NAME = "rerun.encodings.VideoTimestamp";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.INT64;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, org.teamvan.moneyshift.ArrowFields.INT64, java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<VideoTimestamp> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            VideoTimestamp value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.BigIntVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.BigIntVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, VideoTimestamp value) {
        ((org.apache.arrow.vector.BigIntVector) vector).setSafe(index, value.timestampNs());
    }
}
