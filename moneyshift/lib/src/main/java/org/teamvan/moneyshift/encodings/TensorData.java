// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * An N-dimensional array of numbers.
 * 
 * The number of dimensions and their respective lengths is specified by the {@code shape} field.
 * The dimensions are ordered from outermost to innermost. For example, in the common case of
 * a 2D RGB Image, the shape would be {@code [height, width, channel]}.
 * 
 * These dimensions are combined with an index to look up values from the {@code buffer} field,
 * which stores a contiguous array of typed values.
 * 
 */
public record TensorData(
    /**
     * The shape of the tensor, i.e. the length of each dimension.
     */
    java.util.List<java.lang.Long> shape,
    /**
     * The names of the dimensions of the tensor (optional).
     * 
     * If set, should be the same length as {@link org.teamvan.moneyshift.encodings.TensorData#shape}.
     * If it has a different length your names may show up improperly,
     * and some constructors may produce a warning or even an error.
     * 
     * Example: {@code ["height", "width", "channel", "batch"]}.
     */
    java.util.List<java.lang.String> names,
    /**
     * The content/data.
     */
    org.teamvan.moneyshift.encodings.TensorBuffer buffer
) {
    public TensorData {
        shape = java.util.List.copyOf(shape);
        if (names != null) { names = java.util.List.copyOf(names); }
        java.util.Objects.requireNonNull(buffer, "buffer");
    }
    public static final java.lang.String NAME = "rerun.encodings.TensorData";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("shape", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.UINT64, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("names", true, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.UTF8, java.util.List.of()))), org.teamvan.moneyshift.encodings.TensorBuffer.arrowField("buffer", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TensorData> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            TensorData value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TensorData value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        java.util.List<java.lang.Long> f0 = value.shape();
        {
            org.apache.arrow.vector.complex.ListVector list1 = (org.apache.arrow.vector.complex.ListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0);
            int start1 = list1.startNewValue(index);
            org.apache.arrow.vector.FieldVector child1 = list1.getDataVector();
            java.util.List<java.lang.Long> values1 = f0;
            for (int j1 = 0; j1 < values1.size(); j1++) {
                ((org.apache.arrow.vector.UInt8Vector) child1).setSafe(start1 + j1, values1.get(j1));
            }
            list1.endValue(index, values1.size());
        }
        java.util.List<java.lang.String> f2 = value.names();
        if (f2 == null) {
            ((org.apache.arrow.vector.complex.ListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1)).setNull(index);
        } else {
            {
                org.apache.arrow.vector.complex.ListVector list3 = (org.apache.arrow.vector.complex.ListVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1);
                int start3 = list3.startNewValue(index);
                org.apache.arrow.vector.FieldVector child3 = list3.getDataVector();
                java.util.List<java.lang.String> values3 = f2;
                for (int j3 = 0; j3 < values3.size(); j3++) {
                    ((org.apache.arrow.vector.VarCharVector) child3).setSafe(start3 + j3, values3.get(j3).getBytes(java.nio.charset.StandardCharsets.UTF_8));
                }
                list3.endValue(index, values3.size());
            }
        }
        org.teamvan.moneyshift.encodings.TensorBuffer f4 = value.buffer();
        org.teamvan.moneyshift.encodings.TensorBuffer.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(2), index, f4);
    }
}
