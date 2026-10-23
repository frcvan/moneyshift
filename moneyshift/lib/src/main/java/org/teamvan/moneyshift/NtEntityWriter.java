package org.teamvan.moneyshift;

import java.util.List;
import java.util.Objects;

/**
 * Writes the NetworkTables columns of one entity for one log call.
 *
 * <p>Generated {@code writeColumns} methods mirror the component's Arrow layout (§4.2 of the
 * protocol): every leaf is a native NetworkTables array with one entry per component instance.
 * The writer owns no state of its own; change detection for metadata and nullable columns lives in
 * {@link NtRecorder}.
 */
public final class NtEntityWriter {
    private final NtRecorder recorder;
    private final String entity;
    private final String prefix;

    NtEntityWriter(NtRecorder recorder, String entity) {
        this.recorder = Objects.requireNonNull(recorder, "recorder");
        String normalized = Objects.requireNonNull(entity, "entity");
        // `entity` is already escaped by `NtRecorder`; only the root and leading slash are added.
        this.entity = normalized.isEmpty() || normalized.startsWith("/") ? normalized : '/' + normalized;
        this.prefix = NtPath.ROOT + this.entity;
    }

    /** The escaped entity path without the {@code /Rerun} root, e.g. {@code /world/robot}. */
    public String entity() {
        return entity;
    }

    /** The absolute topic prefix of the entity, e.g. {@code /Rerun/world/robot}. */
    public String prefix() {
        return prefix;
    }

    public void doubles(String path, double[] values) {
        recorder.setDoubleArray(topic(path), values);
    }

    public void longs(String path, long[] values) {
        recorder.setIntegerArray(topic(path), values);
    }

    /**
     * Publishes an unsigned 64-bit column.
     *
     * <p>The native {@code long[]} column is always written (bit-reinterpreting values above
     * {@link Long#MAX_VALUE}); when an instance needs the full unsigned range, a decimal
     * {@code string[]} sidecar is written under {@code path/.u64} and kept up to date from then on.
     */
    public void u64(String path, long[] values, boolean hasUnsigned) {
        recorder.writeU64(entity, path, values, hasUnsigned);
    }

    public void booleans(String path, boolean[] values) {
        recorder.setBooleanArray(topic(path), values);
    }

    public void strings(String path, String[] values) {
        recorder.setStringArray(topic(path), values);
    }

    public void raw(String path, byte[] value) {
        recorder.setRaw(topic(path), value);
    }

    /**
     * Publishes a null mask for a nullable column node.
     *
     * @param anyNull whether any instance is null; when {@code false} the mask is only written if
     *     it was ever written before, so a stale mask can never outlive its length.
     */
    public void present(String path, boolean[] values, boolean anyNull) {
        recorder.writePresent(entity, path, values, anyNull);
    }

    /** Writes {@code <entity>/.rerun/<suffix>} if the value changed. */
    public void metaString(String suffix, String value) {
        recorder.metaString(entity, suffix, value);
    }

    public void metaLong(String suffix, long value) {
        recorder.metaLong(entity, suffix, value);
    }

    public void metaBoolean(String suffix, boolean value) {
        recorder.metaBoolean(entity, suffix, value);
    }

    /** Writes a list of already-encoded component columns under {@code path}. */
    public <T> void component(String path, NtComponent<T> component, List<T> values) {
        component.writeColumns(this, path, values);
    }

    /** The absolute topic name of a generated column path. */
    public String topic(String path) {
        return path.isEmpty() ? prefix : prefix + '/' + path;
    }
}
