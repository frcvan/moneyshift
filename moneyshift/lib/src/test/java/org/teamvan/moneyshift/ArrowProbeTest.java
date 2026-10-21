package org.teamvan.moneyshift;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.FieldVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.complex.FixedSizeListVector;
import org.apache.arrow.vector.complex.ListVector;
import org.apache.arrow.vector.ipc.ArrowStreamReader;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import org.apache.arrow.vector.types.pojo.ArrowType;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.FieldType;
import org.apache.arrow.vector.types.pojo.Schema;
import org.junit.jupiter.api.Test;

public class ArrowProbeTest {
    @Test
    void nestedList() throws Exception {
        try (BufferAllocator alloc = new RootAllocator()) {
            Field itemField = new Field("item", FieldType.nullable(new ArrowType.FixedSizeList(2)),
                    List.of(new Field("item", FieldType.notNullable(new ArrowType.FloatingPoint(
                            org.apache.arrow.vector.types.FloatingPointPrecision.SINGLE)), null)));
            Field column = new Field("Points2D:positions",
                    new FieldType(true, new ArrowType.List(), null, Map.of("rerun:component", "Points2D:positions")),
                    List.of(itemField));

            ListVector list = (ListVector) column.createVector(alloc);
            int start0 = list.startNewValue(0);
            FixedSizeListVector fs = (FixedSizeListVector) list.getDataVector();
            fs.startNewValue(0);
            var child = (org.apache.arrow.vector.Float4Vector) fs.getDataVector();
            child.setSafe(0, 1.0f);
            child.setSafe(1, 2.0f);
            fs.setValueCount(1);
            System.out.println("start0=" + start0 + " listChildCountAfterFirst=" + list.getDataVector().getValueCount());
            list.endValue(0, 1);
            int start1 = list.startNewValue(1);
            int listChildCount = list.getDataVector().getValueCount();
            fs.startNewValue(1);
            child.setSafe(2, 3.0f);
            child.setSafe(3, 4.0f);
            fs.setValueCount(2);
            list.endValue(1, 1);
            list.setValueCount(2);
            System.out.println("start1=" + start1 + " listChildCount=" + listChildCount + " list=" + list);
            System.out.println("list=" + list + " child=" + fs.getObject(0));

            Schema schema = new Schema(List.of(column));
            try (VectorSchemaRoot root = new VectorSchemaRoot(schema, List.of((FieldVector) list), 1)) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, out)) {
                    writer.start();
                    writer.writeBatch();
                    writer.end();
                }
                System.out.println("ipc bytes=" + out.size());
                try (ArrowStreamReader reader = new ArrowStreamReader(new ByteArrayInputStream(out.toByteArray()), alloc)) {
                    VectorSchemaRoot read = reader.getVectorSchemaRoot();
                    reader.loadNextBatch();
                    System.out.println("read schema=" + read.getSchema());
                    System.out.println("read value=" + read.getVector(0).getObject(0));
                }
            }
        }
    }
}
