package org.teamvan.moneyshift;

import com.google.protobuf.ByteString;

import org.teamvan.moneyshift.proto.common.v1alpha1.ApplicationId;
import org.teamvan.moneyshift.proto.common.v1alpha1.Compression;
import org.teamvan.moneyshift.proto.common.v1alpha1.StoreKind;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.ArrowMsg;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.Encoding;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.LogMsg;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.SetStoreInfo;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.StoreInfo;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.StoreSource;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.StoreSourceExtra;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.StoreSourceKind;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.StoreVersion;

/** Builds the transport-level messages used by both the gRPC and .rrd sinks. */
public final class LogMsgFactory {
    private LogMsgFactory() {}

    /** A recording or blueprint identity. */
    public record Store(String applicationId, String recordingId, boolean blueprint) {
        public org.teamvan.moneyshift.proto.common.v1alpha1.StoreId toProto() {
            return org.teamvan.moneyshift.proto.common.v1alpha1.StoreId.newBuilder()
                    .setKind(blueprint
                            ? StoreKind.STORE_KIND_BLUEPRINT
                            : StoreKind.STORE_KIND_RECORDING)
                    .setRecordingId(recordingId)
                    .setApplicationId(ApplicationId.newBuilder().setId(applicationId))
                    .build();
        }

        @Override
        public String toString() {
            return applicationId + '/' + recordingId;
        }
    }

    static org.teamvan.moneyshift.proto.common.v1alpha1.Tuid tuidProto(Tuid tuid) {
        return org.teamvan.moneyshift.proto.common.v1alpha1.Tuid.newBuilder()
                .setTimeNs(tuid.timeNanos())
                .setInc(tuid.inc())
                .build();
    }

    public static LogMsg setStoreInfo(Store store, Tuid rowId) {
        StoreInfo info = StoreInfo.newBuilder()
                .setStoreId(store.toProto())
                .setStoreSource(StoreSource.newBuilder()
                        .setKind(StoreSourceKind.STORE_SOURCE_KIND_OTHER)
                        .setExtra(StoreSourceExtra.newBuilder()
                                .setPayload(ByteString.copyFromUtf8(
                                        RerunVersion.SDK_NAME + " (Java)"))))
                .setStoreVersion(StoreVersion.newBuilder()
                        .setCrateVersionBits(RerunVersion.STORE_VERSION_BITS))
                .build();
        return LogMsg.newBuilder()
                .setSetStoreInfo(SetStoreInfo.newBuilder()
                        .setRowId(tuidProto(rowId))
                        .setInfo(info))
                .build();
    }

    public static ArrowMsg arrowMsg(
            Store store,
            Tuid chunkId,
            byte[] compressedPayload,
            long uncompressedSize,
            boolean isStatic) {
        return ArrowMsg.newBuilder()
                .setStoreId(store.toProto())
                .setChunkId(tuidProto(chunkId))
                .setCompression(Compression.COMPRESSION_LZ4)
                .setUncompressedSize(uncompressedSize)
                .setEncoding(Encoding.ENCODING_ARROW_IPC)
                .setPayload(ByteString.copyFrom(compressedPayload))
                .setIsStatic(isStatic)
                .build();
    }

    public static LogMsg arrow(ArrowMsg msg) {
        return LogMsg.newBuilder().setArrowMsg(msg).build();
    }
}
