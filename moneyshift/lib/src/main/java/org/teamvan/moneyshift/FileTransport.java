package org.teamvan.moneyshift;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.teamvan.moneyshift.proto.log_msg.v1alpha1.LogMsg;

/** Streams messages directly to an RRD file. */
public final class FileTransport implements Transport {
    private final OutputStream out;
    private final RrdWriter writer;

    public FileTransport(Path path) {
        try {
            this.out = Files.newOutputStream(path);
            this.writer = new RrdWriter(out);
        } catch (IOException e) {
            throw new UncheckedIOException("failed to open " + path, e);
        }
    }

    public FileTransport(OutputStream out) {
        try {
            this.out = out;
            this.writer = new RrdWriter(out);
        } catch (IOException e) {
            throw new UncheckedIOException("failed to write RRD header", e);
        }
    }

    @Override
    public synchronized void send(LogMsg msg) {
        try {
            writer.write(msg);
        } catch (IOException e) {
            throw new UncheckedIOException("failed to write RRD message", e);
        }
    }

    @Override
    public synchronized void flush(long timeoutMillis) {
        try {
            out.flush();
        } catch (IOException e) {
            throw new UncheckedIOException("failed to flush RRD stream", e);
        }
    }

    @Override
    public synchronized void close() {
        try {
            writer.finish();
            out.close();
        } catch (IOException e) {
            throw new UncheckedIOException("failed to close RRD stream", e);
        }
    }
}
