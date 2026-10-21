package org.teamvan.moneyshift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import io.grpc.MethodDescriptor;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.ServerCalls;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;

import org.teamvan.moneyshift.archetypes.Points3D;
import org.teamvan.moneyshift.components.Position3D;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.ArrowMsg;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.LogMsg;
import org.teamvan.moneyshift.proto.sdk_comms.v1alpha1.WriteMessagesRequest;
import org.teamvan.moneyshift.proto.sdk_comms.v1alpha1.WriteMessagesResponse;

class GrpcTransportTest {
    private static final MethodDescriptor<WriteMessagesRequest, WriteMessagesResponse> METHOD =
            MethodDescriptor.<WriteMessagesRequest, WriteMessagesResponse>newBuilder()
                    .setType(MethodDescriptor.MethodType.CLIENT_STREAMING)
                    .setFullMethodName(
                            "rerun.sdk_comms.v1alpha1.MessageProxyService/WriteMessages")
                    .setRequestMarshaller(
                            ProtoUtils.marshaller(WriteMessagesRequest.getDefaultInstance()))
                    .setResponseMarshaller(
                            ProtoUtils.marshaller(WriteMessagesResponse.getDefaultInstance()))
                    .build();

    @Test
    void streamsStoreInfoAndChunksToAProxy() throws Exception {
        List<LogMsg> received = new CopyOnWriteArrayList<>();
        ServerServiceDefinition service = ServerServiceDefinition.builder(
                        "rerun.sdk_comms.v1alpha1.MessageProxyService")
                .addMethod(
                        METHOD,
                        ServerCalls.asyncClientStreamingCall(
                                new ServerCalls.ClientStreamingMethod<
                                        WriteMessagesRequest, WriteMessagesResponse>() {
                                    @Override
                                    public StreamObserver<WriteMessagesRequest> invoke(
                                            StreamObserver<WriteMessagesResponse> responses) {
                                        return new StreamObserver<>() {
                                            @Override
                                            public void onNext(WriteMessagesRequest request) {
                                                received.add(request.getLogMsg());
                                            }

                                            @Override
                                            public void onError(Throwable t) {}

                                            @Override
                                            public void onCompleted() {
                                                responses.onCompleted();
                                            }
                                        };
                                    }
                                }))
                .build();

        Server server = ServerBuilder.forPort(0).addService(service).build().start();
        try {
            String endpoint = "rerun+http://127.0.0.1:" + server.getPort() + "/proxy";
            try (RecordingStream stream = RecordingStreamBuilder.newRecording("grpc-test")
                    .recordingId("rec_grpc")
                    .connectGrpc(endpoint)
                    .build()) {
                stream.setTimeSequence("frame", 1);
                stream.log(
                        "world/points",
                        Points3D.builder().withPositions(Position3D.of(1, 2, 3)).build());
                stream.flushBlocking();
            }

            assertTrue(received.size() >= 2, "expected SetStoreInfo and one ArrowMsg");
            assertTrue(received.get(0).hasSetStoreInfo());
            long arrowCount = received.stream().filter(LogMsg::hasArrowMsg).count();
            assertEquals(1, arrowCount);
            ArrowMsg arrow = received.stream()
                    .filter(LogMsg::hasArrowMsg)
                    .findFirst()
                    .orElseThrow()
                    .getArrowMsg();
            assertEquals(
                    "rec_grpc",
                    arrow.getStoreId().getRecordingId());
            assertEquals("grpc-test", arrow.getStoreId().getApplicationId().getId());
        } finally {
            server.shutdownNow();
            assertTrue(server.awaitTermination(5, TimeUnit.SECONDS));
        }
    }
}
