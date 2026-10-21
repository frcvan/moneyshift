package org.teamvan.moneyshift;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A 128-bit time-ordered unique identifier.
 *
 * <p>The first 64 bits are nanoseconds since the Unix epoch and the last 64 bits are a per-thread
 * counter. Both are stored big-endian, which makes the raw bytes sort in creation order.
 */
public final class Tuid {
    private static final AtomicLong COUNTER = new AtomicLong(
            ThreadLocalRandom.current().nextLong() >>> 1);

    private final long timeNanos;
    private final long inc;

    public Tuid(long timeNanos, long inc) {
        this.timeNanos = timeNanos;
        this.inc = inc;
    }

    /** A fresh, time-ordered id. */
    public static Tuid newTuid() {
        long nanos = System.currentTimeMillis() * 1_000_000L;
        return new Tuid(nanos, COUNTER.incrementAndGet());
    }

    public long timeNanos() {
        return timeNanos;
    }

    public long inc() {
        return inc;
    }

    /** The 16 big-endian bytes stored in Arrow's {@code FixedSizeBinary(16)} columns. */
    public byte[] toBytes() {
        byte[] bytes = new byte[16];
        ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).putLong(timeNanos).putLong(inc);
        return bytes;
    }

    public static Tuid fromBytes(byte[] bytes) {
        if (bytes.length != 16) {
            throw new IllegalArgumentException("a TUID must be 16 bytes, got " + bytes.length);
        }
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
        return new Tuid(buffer.getLong(), buffer.getLong());
    }

    /** Upper-case time half and lower-case counter half, like Rerun's display format. */
    @Override
    public String toString() {
        return String.format("%016X%016x", timeNanos, inc);
    }

    /** The prefixed form used in Arrow metadata, e.g. {@code chunk_1823…}. */
    public String toPrefixedString(String namespace) {
        return namespace + '_' + this;
    }
}
