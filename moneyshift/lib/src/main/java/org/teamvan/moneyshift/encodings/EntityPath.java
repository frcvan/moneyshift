// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * A path to an entity in the {@code ChunkStore}.
 */
public record EntityPath(
    java.lang.String path
) {
    public EntityPath {
        java.util.Objects.requireNonNull(path, "path");
    }
    public static final java.lang.String NAME = "rerun.encodings.EntityPath";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UTF8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, org.teamvan.moneyshift.ArrowFields.UTF8, java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<EntityPath> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            EntityPath value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.VarCharVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.VarCharVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, EntityPath value) {
        ((org.apache.arrow.vector.VarCharVector) vector).setSafe(index, value.path().getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }
}
