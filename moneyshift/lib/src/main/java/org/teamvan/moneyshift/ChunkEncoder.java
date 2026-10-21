package org.teamvan.moneyshift;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.BigIntVector;
import org.apache.arrow.vector.DurationVector;
import org.apache.arrow.vector.FieldVector;
import org.apache.arrow.vector.FixedSizeBinaryVector;
import org.apache.arrow.vector.TimeStampNanoVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.Schema;

/**
 * Turns one row of components into an Arrow IPC payload.
 *
 * <p>This is the hot path of the SDK: no intermediate value model, no reflection. Values are
 * written straight into Arrow vectors, the vectors are handed to Arrow's stream writer, and the
 * payload is ready for LZ4 and gRPC.
 */
public final class ChunkEncoder {
    private ChunkEncoder() {}

    /**
     * Encodes a single-row chunk.
     *
     * <p>Takes ownership of {@code batches} and closes their vectors.
     */
    public static byte[] encode(
            EntityPath entityPath,
            Tuid chunkId,
            Tuid rowId,
            List<TimeCell> timeCells,
            List<ComponentBatch> batches,
            boolean isStatic,
            BufferAllocator allocator) {
        List<Field> fields = new ArrayList<>(1 + timeCells.size() + batches.size());
        List<FieldVector> vectors = new ArrayList<>(fields.size());

        try {
            Field rowIdField = ArrowFields.rowIdField(true);
            FixedSizeBinaryVector rowIdVector =
                    (FixedSizeBinaryVector) rowIdField.createVector(allocator);
            vectors.add(rowIdVector);
            fields.add(rowIdField);
            rowIdVector.setSafe(0, rowId.toBytes());
            rowIdVector.setValueCount(1);

            List<TimeCell> sortedTimes = new ArrayList<>(timeCells);
            sortedTimes.sort((a, b) -> a.timeline().name().compareTo(b.timeline().name()));
            for (TimeCell cell : sortedTimes) {
                Field field = ArrowFields.indexField(cell.timeline(), true);
                FieldVector vector = field.createVector(allocator);
                vectors.add(vector);
                fields.add(field);
                writeTime(vector, cell.value());
                vector.setValueCount(1);
            }

            List<ComponentBatch> sortedBatches = new ArrayList<>(batches);
            sortedBatches.sort((a, b) -> a.descriptor().component()
                    .compareTo(b.descriptor().component()));
            for (ComponentBatch batch : sortedBatches) {
                if (batch.count() == 0) {
                    // Rerun treats empty component batches as "not logged": including them would
                    // create a semantically-empty column for no reason.
                    batch.close();
                    continue;
                }
                vectors.add(batch.data());
                fields.add(batch.data().getField());
            }

            Map<String, String> metadata = new HashMap<>();
            metadata.put(ArrowFields.SORBET_VERSION, ArrowFields.SORBET_VERSION_0_1_3);
            metadata.put(
                    ArrowFields.CHUNK_ID, chunkId.toPrefixedString("chunk"));
            metadata.put(ArrowFields.ENTITY_PATH, entityPath.path());

            Schema schema = new Schema(fields, metadata);
            try (VectorSchemaRoot root = new VectorSchemaRoot(schema, vectors, 1);
                    ByteArrayOutputStream output = new ByteArrayOutputStream(1024)) {
                try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, output)) {
                    writer.start();
                    writer.writeBatch();
                    writer.end();
                }
                return output.toByteArray();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("failed to encode Arrow IPC", e);
        }
    }

    private static void writeTime(FieldVector vector, long value) {
        if (vector instanceof BigIntVector v) {
            v.setSafe(0, value);
        } else if (vector instanceof DurationVector v) {
            v.setSafe(0, value);
        } else if (vector instanceof TimeStampNanoVector v) {
            v.setSafe(0, value);
        } else {
            throw new IllegalArgumentException(
                    "unsupported time vector " + vector.getClass().getName());
        }
    }
}
