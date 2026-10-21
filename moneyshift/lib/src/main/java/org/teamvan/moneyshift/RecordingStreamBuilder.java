package org.teamvan.moneyshift;

import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

/** Configures and starts a {@link RecordingStream}. */
public final class RecordingStreamBuilder {
    private final String applicationId;
    private String recordingId = newRecordingId();
    private boolean blueprint;
    private String grpcEndpoint;
    private Path file;
    private boolean memory;
    private Transport transport;
    private boolean sendLogTime = true;

    private RecordingStreamBuilder(String applicationId) {
        this.applicationId = Objects.requireNonNull(applicationId, "applicationId");
        if (applicationId.isEmpty()) {
            throw new IllegalArgumentException("applicationId must not be empty");
        }
    }

    /** Starts a builder for a recording. */
    public static RecordingStreamBuilder newRecording(String applicationId) {
        return new RecordingStreamBuilder(applicationId);
    }

    /** Alias for {@link #newRecording(String)}. */
    public static RecordingStreamBuilder builder(String applicationId) {
        return newRecording(applicationId);
    }

    /** Starts a builder for a blueprint store rather than a recording store. */
    public static RecordingStreamBuilder newBlueprint(String applicationId) {
        return new RecordingStreamBuilder(applicationId).blueprint(true);
    }

    /** Whether the stream writes a blueprint store. */
    public RecordingStreamBuilder blueprint(boolean blueprint) {
        this.blueprint = blueprint;
        return this;
    }

    /** Overrides the random recording id. */
    public RecordingStreamBuilder recordingId(String recordingId) {
        this.recordingId = Objects.requireNonNull(recordingId, "recordingId");
        return this;
    }

    /** Streams the recording to a Rerun viewer via the gRPC proxy, e.g. {@code rerun+http://localhost:9876/proxy}. */
    public RecordingStreamBuilder connectGrpc(String endpoint) {
        this.grpcEndpoint = Objects.requireNonNull(endpoint, "endpoint");
        return this;
    }

    /** Writes the recording to an {@code .rrd} file. */
    public RecordingStreamBuilder save(Path path) {
        this.file = Objects.requireNonNull(path, "path");
        return this;
    }

    /** Writes the recording to an {@code .rrd} file. */
    public RecordingStreamBuilder save(String path) {
        return save(Path.of(path));
    }

    /** Keeps the recording in memory; retrieve it with {@link RecordingStream#toRrdBytes()}. */
    public RecordingStreamBuilder memory() {
        this.memory = true;
        return this;
    }

    /** Uses a custom transport. */
    public RecordingStreamBuilder transport(Transport transport) {
        this.transport = Objects.requireNonNull(transport, "transport");
        return this;
    }

    /** Whether to add the {@code log_time} timeline to every log call. Enabled by default. */
    public RecordingStreamBuilder sendLogTime(boolean sendLogTime) {
        this.sendLogTime = sendLogTime;
        return this;
    }

    public RecordingStream build() {
        Transport sink;
        int sinks = (grpcEndpoint == null ? 0 : 1) + (file == null ? 0 : 1) + (memory ? 1 : 0)
                + (transport == null ? 0 : 1);
        if (sinks > 1) {
            throw new IllegalStateException(
                    "a recording can only have one sink; call one of connectGrpc/save/memory/transport");
        }
        if (grpcEndpoint != null) {
            sink = new GrpcTransport(grpcEndpoint);
        } else if (file != null) {
            sink = new FileTransport(file);
        } else if (memory) {
            sink = new MemoryTransport();
        } else if (transport != null) {
            sink = transport;
        } else {
            throw new IllegalStateException(
                    "no sink configured: call connectGrpc, save, memory or transport");
        }

        return new RecordingStream(
                new LogMsgFactory.Store(applicationId, recordingId, blueprint), sink, sendLogTime);
    }

    private static String newRecordingId() {
        return "rec_" + UUID.randomUUID().toString().replace("-", "");
    }
}
