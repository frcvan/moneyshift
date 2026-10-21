// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.encodings;

/**
 * The underlying storage for {@link org.teamvan.moneyshift.archetypes.Tensor}.
 * 
 * Tensor elements are stored in a contiguous buffer of a single type.
 */
public sealed interface TensorBuffer permits
    TensorBuffer.U8,
    TensorBuffer.U16,
    TensorBuffer.U32,
    TensorBuffer.U64,
    TensorBuffer.I8,
    TensorBuffer.I16,
    TensorBuffer.I32,
    TensorBuffer.I64,
    TensorBuffer.F16,
    TensorBuffer.F32,
    TensorBuffer.F64 {
    /**
     * 8bit unsigned integer.
     */
    record U8(java.util.List<java.lang.Short> value) implements TensorBuffer {
        public U8 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 16bit unsigned integer.
     */
    record U16(java.util.List<java.lang.Integer> value) implements TensorBuffer {
        public U16 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 32bit unsigned integer.
     */
    record U32(java.util.List<java.lang.Long> value) implements TensorBuffer {
        public U32 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 64bit unsigned integer.
     */
    record U64(java.util.List<java.lang.Long> value) implements TensorBuffer {
        public U64 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 8bit signed integer.
     */
    record I8(java.util.List<java.lang.Byte> value) implements TensorBuffer {
        public I8 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 16bit signed integer.
     */
    record I16(java.util.List<java.lang.Short> value) implements TensorBuffer {
        public I16 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 32bit signed integer.
     */
    record I32(java.util.List<java.lang.Integer> value) implements TensorBuffer {
        public I32 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 64bit signed integer.
     */
    record I64(java.util.List<java.lang.Long> value) implements TensorBuffer {
        public I64 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 16bit IEEE-754 floating point, also known as {@code half}.
     */
    record F16(java.util.List<java.lang.Float> value) implements TensorBuffer {
        public F16 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 32bit IEEE-754 floating point, also known as {@code float} or {@code single}.
     */
    record F32(java.util.List<java.lang.Float> value) implements TensorBuffer {
        public F32 { java.util.Objects.requireNonNull(value, "value"); }
    }
    /**
     * 64bit IEEE-754 floating point, also known as {@code double}.
     */
    record F64(java.util.List<java.lang.Double> value) implements TensorBuffer {
        public F64 { java.util.Objects.requireNonNull(value, "value"); }
    }

    java.lang.String NAME = "rerun.encodings.TensorBuffer";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Union(org.apache.arrow.vector.types.UnionMode.Dense, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Union(org.apache.arrow.vector.types.UnionMode.Dense, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("_null_markers", true, org.teamvan.moneyshift.ArrowFields.NULL, java.util.List.of()), org.teamvan.moneyshift.ArrowFields.field("U8", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.UINT8, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("U16", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.UINT16, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("U32", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.UINT32, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("U64", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.UINT64, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("I8", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.INT8, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("I16", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.INT16, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("I32", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.INT32, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("I64", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.INT64, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("F16", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.FLOAT16, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("F32", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.FLOAT32, java.util.List.of()))), org.teamvan.moneyshift.ArrowFields.field("F64", false, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.ArrowFields.field("item", false, org.teamvan.moneyshift.ArrowFields.FLOAT64, java.util.List.of())))));
    }

    public static int typeId(TensorBuffer value) {
        if (value == null) return 0;
        return switch (value) {
            case TensorBuffer.U8 ignored -> 1;
            case TensorBuffer.U16 ignored -> 2;
            case TensorBuffer.U32 ignored -> 3;
            case TensorBuffer.U64 ignored -> 4;
            case TensorBuffer.I8 ignored -> 5;
            case TensorBuffer.I16 ignored -> 6;
            case TensorBuffer.I32 ignored -> 7;
            case TensorBuffer.I64 ignored -> 8;
            case TensorBuffer.F16 ignored -> 9;
            case TensorBuffer.F32 ignored -> 10;
            case TensorBuffer.F64 ignored -> 11;
        };
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<TensorBuffer> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            writeOne(vector, i, values.get(i));
        }
        ((org.apache.arrow.vector.complex.DenseUnionVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, TensorBuffer value) {
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
            case TensorBuffer.U8 variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 1);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 1);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    java.util.List<java.lang.Short> values0 = variant.value();
                    for (int j0 = 0; j0 < values0.size(); j0++) {
                        ((org.apache.arrow.vector.UInt1Vector) child0).setSafe(start0 + j0, (int) values0.get(j0));
                    }
                    list0.endValue(offset, values0.size());
                }
            }
            case TensorBuffer.U16 variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 2);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 2);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    java.util.List<java.lang.Integer> values0 = variant.value();
                    for (int j0 = 0; j0 < values0.size(); j0++) {
                        ((org.apache.arrow.vector.UInt2Vector) child0).setSafe(start0 + j0, (int) values0.get(j0));
                    }
                    list0.endValue(offset, values0.size());
                }
            }
            case TensorBuffer.U32 variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 3);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 3);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    java.util.List<java.lang.Long> values0 = variant.value();
                    for (int j0 = 0; j0 < values0.size(); j0++) {
                        ((org.apache.arrow.vector.UInt4Vector) child0).setSafe(start0 + j0, (int) (long) values0.get(j0));
                    }
                    list0.endValue(offset, values0.size());
                }
            }
            case TensorBuffer.U64 variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 4);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 4);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    java.util.List<java.lang.Long> values0 = variant.value();
                    for (int j0 = 0; j0 < values0.size(); j0++) {
                        ((org.apache.arrow.vector.UInt8Vector) child0).setSafe(start0 + j0, values0.get(j0));
                    }
                    list0.endValue(offset, values0.size());
                }
            }
            case TensorBuffer.I8 variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 5);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 5);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    java.util.List<java.lang.Byte> values0 = variant.value();
                    for (int j0 = 0; j0 < values0.size(); j0++) {
                        ((org.apache.arrow.vector.TinyIntVector) child0).setSafe(start0 + j0, values0.get(j0));
                    }
                    list0.endValue(offset, values0.size());
                }
            }
            case TensorBuffer.I16 variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 6);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 6);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    java.util.List<java.lang.Short> values0 = variant.value();
                    for (int j0 = 0; j0 < values0.size(); j0++) {
                        ((org.apache.arrow.vector.SmallIntVector) child0).setSafe(start0 + j0, values0.get(j0));
                    }
                    list0.endValue(offset, values0.size());
                }
            }
            case TensorBuffer.I32 variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 7);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 7);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    java.util.List<java.lang.Integer> values0 = variant.value();
                    for (int j0 = 0; j0 < values0.size(); j0++) {
                        ((org.apache.arrow.vector.IntVector) child0).setSafe(start0 + j0, values0.get(j0));
                    }
                    list0.endValue(offset, values0.size());
                }
            }
            case TensorBuffer.I64 variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 8);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 8);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    java.util.List<java.lang.Long> values0 = variant.value();
                    for (int j0 = 0; j0 < values0.size(); j0++) {
                        ((org.apache.arrow.vector.BigIntVector) child0).setSafe(start0 + j0, values0.get(j0));
                    }
                    list0.endValue(offset, values0.size());
                }
            }
            case TensorBuffer.F16 variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 9);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 9);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    java.util.List<java.lang.Float> values0 = variant.value();
                    for (int j0 = 0; j0 < values0.size(); j0++) {
                        ((org.apache.arrow.vector.Float2Vector) child0).setSafe(start0 + j0, java.lang.Float.floatToFloat16(values0.get(j0)));
                    }
                    list0.endValue(offset, values0.size());
                }
            }
            case TensorBuffer.F32 variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 10);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 10);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    java.util.List<java.lang.Float> values0 = variant.value();
                    for (int j0 = 0; j0 < values0.size(); j0++) {
                        ((org.apache.arrow.vector.Float4Vector) child0).setSafe(start0 + j0, values0.get(j0));
                    }
                    list0.endValue(offset, values0.size());
                }
            }
            case TensorBuffer.F64 variant -> {
                org.apache.arrow.vector.FieldVector child = (org.apache.arrow.vector.FieldVector) union.getVectorByType((byte) 11);
                int offset = child.getValueCount();
                union.setTypeId(index, (byte) 11);
                union.setOffset(index, offset);
                child.setValueCount(offset + 1);
                {
                    org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) child;
                    int start0 = list0.startNewValue(offset);
                    org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
                    java.util.List<java.lang.Double> values0 = variant.value();
                    for (int j0 = 0; j0 < values0.size(); j0++) {
                        ((org.apache.arrow.vector.Float8Vector) child0).setSafe(start0 + j0, values0.get(j0));
                    }
                    list0.endValue(offset, values0.size());
                }
            }
        }
    }
}
