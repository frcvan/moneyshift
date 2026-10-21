package org.teamvan.moneyshift;

import java.net.URI;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptors;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.TlsChannelCredentials;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.ClientCalls;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;

import org.teamvan.moneyshift.proto.log_msg.v1alpha1.LogMsg;
import org.teamvan.moneyshift.proto.sdk_comms.v1alpha1.WriteMessagesRequest;
import org.teamvan.moneyshift.proto.sdk_comms.v1alpha1.WriteMessagesResponse;

/**
 * The Rerun SDK comms transport: a long-lived client-streaming gRPC call to
 * {@code rerun.sdk_comms.v1alpha1.MessageProxyService/WriteMessages}.
 *
 * <p>Messages are handed to a dedicated writer thread through a bounded queue, which provides
 * backpressure without blocking the logging thread on network I/O. Flushing a message is enough
 * to guarantee it has been serialized into the gRPC transport, matching the flush semantics of
 * the official SDKs.
 */
public final class GrpcTransport implements Transport {
    private static final MethodDescriptor<WriteMessagesRequest, WriteMessagesResponse> WRITE_MESSAGES =
            MethodDescriptor.<WriteMessagesRequest, WriteMessagesResponse>newBuilder()
                    .setType(MethodDescriptor.MethodType.CLIENT_STREAMING)
                    .setFullMethodName(
                            "rerun.sdk_comms.v1alpha1.MessageProxyService/WriteMessages")
                    .setRequestMarshaller(
                            ProtoUtils.marshaller(WriteMessagesRequest.getDefaultInstance()))
                    .setResponseMarshaller(
                            ProtoUtils.marshaller(WriteMessagesResponse.getDefaultInstance()))
                    .build();

    private static final int QUEUE_CAPACITY = 4096;

    private record Command(LogMsg msg, CompletableFuture<Void> flushed, boolean close) {}

    private final String endpoint;
    private final ManagedChannel channel;
    private final BlockingQueue<Command> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    private final CountDownLatch responseLatch = new CountDownLatch(1);
    private final Thread worker;

    private volatile Throwable failure;
    private volatile boolean closed;

    public GrpcTransport(String endpoint) {
        this.endpoint = endpoint;
        ProxyTarget target = ProxyTarget.parse(endpoint);

        Metadata headers = new Metadata();
        headers.put(
                Metadata.Key.of("x-rerun-client-version", Metadata.ASCII_STRING_MARSHALLER),
                RerunVersion.CLIENT_IDENTIFIER);

        Channel baseChannel = Grpc.newChannelBuilder(
                        target.authority,
                        target.tls
                                ? TlsChannelCredentials.create()
                                : InsecureChannelCredentials.create())
                .maxInboundMessageSize(Integer.MAX_VALUE)
                .build();
        this.channel = (ManagedChannel) baseChannel;
        Channel intercepted = ClientInterceptors.intercept(
                baseChannel, MetadataUtils.newAttachHeadersInterceptor(headers));

        this.worker = new Thread(() -> run(intercepted), "moneyshift-grpc");
        this.worker.setDaemon(true);
        this.worker.start();
    }

    private void run(Channel intercepted) {
        StreamObserver<WriteMessagesResponse> responseObserver =
                new StreamObserver<>() {
                    @Override
                    public void onNext(WriteMessagesResponse value) {
                        // Empty response; nothing to do.
                    }

                    @Override
                    public void onError(Throwable t) {
                        failure = t;
                        responseLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        responseLatch.countDown();
                    }
                };

        try {
            ClientCall<WriteMessagesRequest, WriteMessagesResponse> call =
                    intercepted.newCall(
                            WRITE_MESSAGES,
                            CallOptions.DEFAULT
                                    .withMaxInboundMessageSize(Integer.MAX_VALUE)
                                    .withMaxOutboundMessageSize(Integer.MAX_VALUE));
            StreamObserver<WriteMessagesRequest> requests =
                    ClientCalls.asyncClientStreamingCall(call, responseObserver);

            while (true) {
                Command command;
                try {
                    command = queue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                if (command.msg != null) {
                    requests.onNext(WriteMessagesRequest.newBuilder()
                            .setLogMsg(command.msg)
                            .build());
                }
                if (command.flushed != null) {
                    command.flushed.complete(null);
                }
                if (command.close) {
                    requests.onCompleted();
                    break;
                }
            }
        } catch (RuntimeException e) {
            failure = e;
        } finally {
            // Unblock any flush waiters before this thread dies.
            CompletableFuture<Void> f;
            while ((f = pendingFlush()) != null) {
                f.completeExceptionally(
                        new TransportException("gRPC transport stopped", failure));
            }
            if (failure != null) {
                // The response observer will never fire; do not leave close() waiting.
                responseLatch.countDown();
            }
        }
    }

    private CompletableFuture<Void> pendingFlush() {
        Command command = queue.poll();
        while (command != null) {
            if (command.flushed != null) {
                return command.flushed;
            }
            command = queue.poll();
        }
        return null;
    }

    @Override
    public void send(LogMsg msg) {
        if (closed) {
            throw new IllegalStateException("transport is closed");
        }
        if (failure != null) {
            throw new TransportException("gRPC transport failed", failure);
        }
        try {
            queue.put(new Command(msg, null, false));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new TransportException("interrupted while queueing a log message", e);
        }
    }

    @Override
    public void flush(long timeoutMillis) {
        if (closed) {
            return;
        }
        CompletableFuture<Void> done = new CompletableFuture<>();
        try {
            queue.put(new Command(null, done, false));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new TransportException("interrupted while flushing", e);
        }
        try {
            done.get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            throw new TransportException(
                    "gRPC flush timed out after " + timeoutMillis + "ms", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new TransportException("interrupted while flushing", e);
        } catch (ExecutionException e) {
            throw new TransportException("gRPC flush failed", e.getCause());
        }
        if (failure != null) {
            throw new TransportException("gRPC transport failed", failure);
        }
    }

    @Override
    public void close() {
        if (closed) {
            return;
        }
        try {
            flush(30_000);
        } catch (TransportException ignored) {
            // We still want to shut the channel down; the failure is reported by earlier sends.
        }
        closed = true;
        try {
            queue.put(new Command(null, null, true));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        try {
            responseLatch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        channel.shutdownNow();
        try {
            channel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String endpoint() {
        return endpoint;
    }

    private record ProxyTarget(String authority, boolean tls) {
        static ProxyTarget parse(String endpoint) {
            URI uri;
            try {
                uri = URI.create(endpoint);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("invalid Rerun endpoint: " + endpoint, e);
            }
            String scheme = uri.getScheme();
            if (scheme == null) {
                return new ProxyTarget(endpoint, false);
            }
            String bare = scheme.startsWith("rerun+") ? scheme.substring("rerun+".length()) : scheme;
            if (bare.equals("rerun")) {
                bare = "http";
            }
            boolean tls = bare.equals("https") || bare.equals("tls");
            String authority = uri.getRawAuthority();
            if (authority == null) {
                throw new IllegalArgumentException("Rerun endpoint has no host: " + endpoint);
            }
            if (uri.getPort() < 0) {
                authority = authority + ':' + (tls ? 443 : 9876);
            }
            return new ProxyTarget(authority, tls);
        }
    }

    /** Returns the gRPC status if the call failed, otherwise null. */
    public Status status() {
        return failure == null ? null : Status.fromThrowable(failure);
    }
}
