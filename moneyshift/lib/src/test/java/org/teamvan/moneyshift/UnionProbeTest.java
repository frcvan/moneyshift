package org.teamvan.moneyshift;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.FieldVector;
import org.apache.arrow.vector.IntVector;
import org.apache.arrow.vector.NullVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.complex.DenseUnionVector;
import org.apache.arrow.vector.complex.FixedSizeListVector;
import org.apache.arrow.vector.ipc.ArrowStreamReader;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import org.apache.arrow.vector.types.UnionMode;
import org.apache.arrow.vector.types.pojo.ArrowType;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.Schema;
import org.junit.jupiter.api.Test;

public class UnionProbeTest {
    @Test
    void union() throws Exception {
        try (BufferAllocator alloc = new RootAllocator()) {
            Field unionField = ArrowFields.field(
                    "u",
                    true,
                    new ArrowType.Union(UnionMode.Dense, new int[] {0, 1, 2}),
                    List.of(
                            ArrowFields.field("_null_markers", true, ArrowFields.NULL, List.of()),
                            ArrowFields.field("A", false, ArrowFields.INT32, List.of()),
                            ArrowFields.field(
                                    "B",
                                    false,
                                    new ArrowType.FixedSizeList(2),
                                    List.of(ArrowFields.field(
                                            "item", false, ArrowFields.FLOAT32, List.of())))));
            DenseUnionVector v = (DenseUnionVector) unionField.createVector(alloc);

            NullVector nulls = (NullVector) v.getVectorByType((byte) 0);
            v.setTypeId(0, (byte) 0);
            v.setOffset(0, nulls.getValueCount());
            nulls.setValueCount(nulls.getValueCount() + 1);

            IntVector ints = v.getIntVector((byte) 1);
            v.setTypeId(1, (byte) 1);
            v.setOffset(1, ints.getValueCount());
            ints.setSafe(ints.getValueCount(), 42);
            ints.setValueCount(ints.getValueCount() + 1);

            FixedSizeListVector lists = (FixedSizeListVector) v.getVectorByType((byte) 2);
            v.setTypeId(2, (byte) 2);
            v.setOffset(2, lists.getValueCount());
            int start = lists.startNewValue(lists.getValueCount());
            lists.setValueCount(lists.getValueCount() + 1);
            var floats = (org.apache.arrow.vector.Float4Vector) lists.getDataVector();
            floats.setSafe(start, 1.0f);
            floats.setSafe(start + 1, 2.0f);

            v.setValueCount(3);
            System.out.println("union0=" + v.getObject(0));
            System.out.println("union1=" + v.getObject(1));
            System.out.println("union2=" + v.getObject(2));

            Field column = ArrowFields.componentList(
                    new ComponentDescriptor(null, "u", "rerun.components.Unknown"),
                    unionField);
            var list = (org.apache.arrow.vector.complex.ListVector) column.createVector(alloc);
            list.startNewValue(0);
            FieldVector child = list.getDataVector();
            // Copy-ish: write same values via transfer isn't trivial; just use union as top-level.
            list.endValue(0, 0);
            list.setValueCount(1);
            list.close();

            Schema schema = new Schema(List.of(unionField));
            try (VectorSchemaRoot root = new VectorSchemaRoot(schema, List.of((FieldVector) v), 3)) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, out)) {
                    writer.start();
                    writer.writeBatch();
                    writer.end();
                }
                System.out.println("ipc=" + out.size());
                try (ArrowStreamReader reader =
                        new ArrowStreamReader(new ByteArrayInputStream(out.toByteArray()), alloc)) {
                    reader.loadNextBatch();
                    var read = reader.getVectorSchemaRoot().getVector(0);
                    System.out.println("read0=" + read.getObject(0));
                    System.out.println("read1=" + read.getObject(1));
                    System.out.println("read2=" + read.getObject(2));
                }
            }
        }
    }
}
