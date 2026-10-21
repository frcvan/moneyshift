package org.teamvan.moneyshift;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

import com.google.protobuf.Message;

import org.teamvan.moneyshift.proto.log_msg.v1alpha1.LogMsg;

/**
 * Writes a Rerun RRD stream ({@code .rrd}) exactly as {@code re_log_encoding} does.
 *
 * <p>Layout: a twelve-byte stream header, then framed protobuf messages, then an end marker
 * followed by an empty RRD footer and the fixed-size stream footer.
 */
public final class RrdWriter implements AutoCloseable {
    /** {@code MessageKind} values from {@code re_log_encoding}. */
    private static final int END = 0;
    private static final int SET_STORE_INFO = 1;
    private static final int ARROW_MSG = 2;
    private static final int BLUEPRINT_ACTIVATION_COMMAND = 3;

    private static final byte[] FOURCC = {'R', 'R', 'F', '2'};
    private static final byte[] FOOTER_IDENTIFIER = {'F', 'O', 'O', 'T'};
    private static final int CRC_SEED = 7850921;

    private final OutputStream out;
    private long position;
    private boolean finished;

    public RrdWriter(OutputStream out) throws IOException {
        this.out = Objects.requireNonNull(out, "out");
        // StreamHeader: RRF2, CrateVersion bytes, then EncodingOptions
        // (compression=LZ4, serializer=protobuf, two reserved bytes).
        byte[] header = {
            FOURCC[0], FOURCC[1], FOURCC[2], FOURCC[3],
            (byte) RerunVersion.MAJOR, (byte) RerunVersion.MINOR,
            (byte) RerunVersion.PATCH, 0,
            1, 2, 0, 0,
        };
        out.write(header);
        this.position = header.length;
    }

    /** Appends one log message. */
    public void write(LogMsg msg) throws IOException {
        switch (msg.getMsgCase()) {
            case SET_STORE_INFO -> writeFrame(SET_STORE_INFO, msg.getSetStoreInfo());
            case ARROW_MSG -> writeFrame(ARROW_MSG, msg.getArrowMsg());
            case BLUEPRINT_ACTIVATION_COMMAND ->
                    writeFrame(BLUEPRINT_ACTIVATION_COMMAND, msg.getBlueprintActivationCommand());
            case MSG_NOT_SET -> throw new IllegalArgumentException("log message has no payload");
        }
    }

    private void writeFrame(int kind, Message payload) throws IOException {
        byte[] bytes = payload.toByteArray();
        writeHeader(kind, bytes.length);
        out.write(bytes);
        position += bytes.length;
    }

    private void writeHeader(int kind, long length) throws IOException {
        ByteBuffer header = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN);
        header.putLong(kind);
        header.putLong(length);
        out.write(header.array());
        position += 16;
    }

    /** Writes the end-of-stream marker and stream footer. Idempotent. */
    public void finish() throws IOException {
        if (finished) {
            return;
        }
        finished = true;

        writeHeader(END, 0);

        long footerStart = position;
        int crc = net.jpountz.xxhash.XXHashFactory.fastestInstance()
                .hash32()
                .hash(new byte[0], 0, 0, CRC_SEED);

        ByteBuffer footer = ByteBuffer.allocate(32).order(ByteOrder.LITTLE_ENDIAN);
        footer.putLong(footerStart);
        footer.putLong(0); // footer payload length
        footer.putInt(crc);
        footer.put(FOURCC);
        footer.put(FOOTER_IDENTIFIER);
        footer.putInt(1); // number of footer entries
        out.write(footer.array());
        position += footer.array().length;
        out.flush();
    }

    @Override
    public void close() throws IOException {
        if (!finished) {
            finish();
        }
        out.close();
    }
}
