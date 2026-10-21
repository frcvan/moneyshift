package org.teamvan.moneyshift;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.teamvan.moneyshift.proto.log_msg.v1alpha1.LogMsg;

/** Accumulates a complete RRD stream in memory. */
public final class MemoryTransport implements Transport {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream(64 * 1024);
    private final RrdWriter writer;
    private boolean finished;

    public MemoryTransport() {
        try {
            this.writer = new RrdWriter(out);
        } catch (IOException e) {
            throw new UncheckedIOException("failed to write RRD header", e);
        }
    }

    @Override
    public synchronized void send(LogMsg msg) {
        if (finished) {
            throw new IllegalStateException("transport is closed");
        }
        try {
            writer.write(msg);
        } catch (IOException e) {
            throw new UncheckedIOException("failed to write RRD message", e);
        }
    }

    @Override
    public synchronized void flush(long timeoutMillis) {
        // Nothing to do: bytes are already in memory.
    }

    /** Finishes the stream and returns its bytes. The transport must not be used afterwards. */
    public synchronized byte[] toByteArray() {
        if (!finished) {
            try {
                writer.finish();
            } catch (IOException e) {
                throw new UncheckedIOException("failed to finish RRD stream", e);
            }
            finished = true;
        }
        return out.toByteArray();
    }

    @Override
    public synchronized void close() {
        toByteArray();
    }
}
