// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A 16-bit ID representing a type of semantic keypoint within a class.
 * 
 * 
 */
public record KeypointId(
    int id
) {
    public KeypointId {
    }
    public static final java.lang.String NAME = "rerun.encodings.KeypointId";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT16;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, org.teamvan.moneyshift.ArrowFields.UINT16, java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<KeypointId> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            KeypointId value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt2Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.UInt2Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, KeypointId value) {
        ((org.apache.arrow.vector.UInt2Vector) vector).setSafe(index, (int) value.id());
    }
}
