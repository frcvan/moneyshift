package org.teamvan.moneyshift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.teamvan.moneyshift.blueprint.archetypes.ViewBlueprint;
import org.teamvan.moneyshift.blueprint.components.ViewClass;
import org.teamvan.moneyshift.components.Name;
import org.teamvan.moneyshift.encodings.Utf8;
import org.teamvan.moneyshift.proto.log_msg.v1alpha1.LogMsg;

class BlueprintTest {
    private static final class CollectingTransport implements Transport {
        final List<LogMsg> messages = new ArrayList<>();

        @Override
        public synchronized void send(LogMsg msg) {
            messages.add(msg);
        }

        @Override
        public void flush(long timeoutMillis) {}

        @Override
        public void close() {}
    }

    @Test
    void blueprintStreamsUseBlueprintStoreKindAndCanBeActivated() {
        CollectingTransport recordingMessages = new CollectingTransport();
        CollectingTransport blueprintMessages = new CollectingTransport();

        try (RecordingStream recording = RecordingStreamBuilder.newRecording("app")
                        .recordingId("rec_app")
                        .transport(recordingMessages)
                        .build();
                RecordingStream blueprint = RecordingStreamBuilder.newBlueprint("app")
                        .recordingId("bp_app")
                        .transport(blueprintMessages)
                        .build()) {
            blueprint.log(
                    "view/0",
                    ViewBlueprint.builder()
                            .withClassIdentifier(new ViewClass(new Utf8("3D")))
                            .withDisplayName(new Name(new Utf8("Main")))
                            .build());
            recording.activateBlueprint(blueprint, true, false);
        }

        LogMsg storeInfo = blueprintMessages.messages.get(0);
        assertTrue(storeInfo.hasSetStoreInfo());
        assertEquals(
                org.teamvan.moneyshift.proto.common.v1alpha1.StoreKind.STORE_KIND_BLUEPRINT,
                storeInfo.getSetStoreInfo().getInfo().getStoreId().getKind());

        LogMsg activation = recordingMessages.messages.get(recordingMessages.messages.size() - 1);
        assertTrue(activation.hasBlueprintActivationCommand());
        assertEquals(
                "bp_app",
                activation.getBlueprintActivationCommand().getBlueprintId().getRecordingId());
        assertTrue(activation.getBlueprintActivationCommand().getMakeActive());
    }
}
