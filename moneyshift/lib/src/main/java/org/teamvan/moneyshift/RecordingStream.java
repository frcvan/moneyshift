package org.teamvan.moneyshift;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;

import org.teamvan.moneyshift.proto.log_msg.v1alpha1.ArrowMsg;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.LogMsg;

/**
 * A Rerun recording stream.
 *
 * <p>Log calls are serialized immediately into Arrow IPC chunks and handed to the configured
 * transport. A single {@link BufferAllocator} is reused for every chunk, so the steady-state hot
 * path performs no allocation beyond the vectors a chunk actually needs.
 */
public final class RecordingStream implements AutoCloseable {
    private final LogMsgFactory.Store store;
    private final Transport transport;
    private final BufferAllocator allocator;
    private final boolean sendLogTime;
    private final Object lock = new Object();

    private final Map<String, TimeCell> timepoint = new LinkedHashMap<>();
    private boolean storeInfoSent;
    private boolean closed;

    RecordingStream(LogMsgFactory.Store store, Transport transport, boolean sendLogTime) {
        this.store = Objects.requireNonNull(store, "store");
        this.transport = Objects.requireNonNull(transport, "transport");
        this.sendLogTime = sendLogTime;
        this.allocator = new RootAllocator();
    }

    public String applicationId() {
        return store.applicationId();
    }

    public String recordingId() {
        return store.recordingId();
    }

    // --- Time handling ---

    public RecordingStream setTimeSequence(String timeline, long value) {
        return setTime(Timeline.sequence(timeline), value);
    }

    public RecordingStream setTimeDurationNanos(String timeline, long value) {
        return setTime(Timeline.duration(timeline), value);
    }

    public RecordingStream setTimeSeconds(String timeline, double seconds) {
        return setTimeDurationNanos(timeline, Math.round(seconds * 1_000_000_000.0));
    }

    public RecordingStream setTimeTimestampNanosSinceEpoch(String timeline, long value) {
        return setTime(Timeline.timestamp(timeline), value);
    }

    public RecordingStream setTime(String timeline, long sequence) {
        return setTimeSequence(timeline, sequence);
    }

    public RecordingStream setTime(Timeline timeline, long value) {
        synchronized (lock) {
            timepoint.put(timeline.name(), new TimeCell(timeline, value));
        }
        return this;
    }

    /** Removes all explicitly set timelines. */
    public RecordingStream resetTime() {
        synchronized (lock) {
            timepoint.clear();
        }
        return this;
    }

    /** Removes one timeline from the current timepoint. */
    public RecordingStream disableTimeline(String timeline) {
        synchronized (lock) {
            timepoint.remove(timeline);
        }
        return this;
    }

    // --- Logging ---

    public void log(String entityPath, Archetype archetype) {
        Objects.requireNonNull(archetype, "archetype");
        List<ComponentBatch> batches = null;
        try {
            synchronized (lock) {
                ensureStoreInfo();
                batches = archetype.serialize(allocator);
                sendRow(EntityPath.parse(entityPath), batches, false);
            }
        } catch (RuntimeException e) {
            closeQuietly(batches);
            throw e;
        }
    }

    public void logStatic(String entityPath, Archetype archetype) {
        Objects.requireNonNull(archetype, "archetype");
        List<ComponentBatch> batches = null;
        try {
            synchronized (lock) {
                ensureStoreInfo();
                batches = archetype.serialize(allocator);
                sendRow(EntityPath.parse(entityPath), batches, true);
            }
        } catch (RuntimeException e) {
            closeQuietly(batches);
            throw e;
        }
    }

    /**
     * Logs already-serialized component batches.
     *
     * <p>The batches are consumed and closed by this call.
     */
    public void log(String entityPath, ComponentBatch... batches) {
        List<ComponentBatch> owned = List.of(batches);
        synchronized (lock) {
            ensureStoreInfo();
            sendRow(EntityPath.parse(entityPath), owned, false);
        }
    }

    private void sendRow(EntityPath entityPath, List<ComponentBatch> batches, boolean isStatic) {
        List<TimeCell> times = new ArrayList<>(timepoint.size() + 1);
        if (!isStatic) {
            if (sendLogTime && !timepoint.containsKey("log_time")) {
                times.add(new TimeCell(
                        Timeline.timestamp("log_time"), Instant.now().getEpochSecond() * 1_000_000_000L
                                + Instant.now().getNano()));
            }
            times.addAll(timepoint.values());
        }

        Tuid rowId = Tuid.newTuid();
        Tuid chunkId = Tuid.newTuid();
        byte[] ipc = ChunkEncoder.encode(
                entityPath, chunkId, rowId, times, batches, isStatic, allocator);
        byte[] compressed = Lz4.compress(ipc);
        ArrowMsg msg = LogMsgFactory.arrowMsg(
                store, chunkId, compressed, ipc.length, isStatic);
        transport.send(LogMsgFactory.arrow(msg));
    }

    private void ensureStoreInfo() {
        if (!storeInfoSent) {
            storeInfoSent = true;
            transport.send(LogMsgFactory.setStoreInfo(store, Tuid.newTuid()));
        }
    }

    // --- Lifecycle ---

    public void flushBlocking() {
        flushBlocking(Duration.ofSeconds(30));
    }

    public void flushBlocking(Duration timeout) {
        synchronized (lock) {
            ensureStoreInfo();
            transport.flush(timeout.toMillis());
        }
    }

    /** Only valid when the stream was built with {@link RecordingStreamBuilder#memory()}. */
    public byte[] toRrdBytes() {
        flushBlocking();
        if (!(transport instanceof MemoryTransport memory)) {
            throw new IllegalStateException("this recording stream does not use a memory sink");
        }
        synchronized (lock) {
            closed = true;
            byte[] bytes = memory.toByteArray();
            allocator.close();
            return bytes;
        }
    }

    @Override
    public void close() {
        synchronized (lock) {
            if (closed) {
                return;
            }
            closed = true;
            try {
                ensureStoreInfo();
                transport.flush(Duration.ofSeconds(30).toMillis());
            } finally {
                transport.close();
                allocator.close();
            }
        }
    }

    private static void closeQuietly(List<ComponentBatch> batches) {
        if (batches == null) {
            return;
        }
        for (ComponentBatch batch : batches) {
            try {
                batch.close();
            } catch (RuntimeException ignored) {
                // Best effort: the original exception is more useful.
            }
        }
    }
}
