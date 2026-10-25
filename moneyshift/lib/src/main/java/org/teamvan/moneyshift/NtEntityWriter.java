package org.teamvan.moneyshift;

import java.util.List;

/**
 * Writes the NetworkTables columns of one entity for one log call.
 *
 * <p>Generated {@code writeColumns} methods mirror the component's Arrow layout (§4.2 of the
 * protocol): every leaf is a native NetworkTables array with one entry per component instance.
 * The writer owns no data of its own; change detection for metadata and nullable columns lives in
 * {@link NtRecorder}, and every column's {@code NetworkTableEntry} is cached per entity so a
 * steady-state publish does not build or hash topic strings.
 */
public final class NtEntityWriter {
    private final NtRecorder recorder;
    private final NtRecorder.EntityState state;

    /**
     * Creates a standalone writer for {@code entity}, a relative path such as
     * {@code "world/points"}.
     *
     * <p>Prefer letting {@link NtRecorder} hand out writers: a writer created here only shares the
     * per-entity caches if the recorder created its state first.
     */
    NtEntityWriter(NtRecorder recorder, String entity) {
        this(recorder, recorder.state(NtPath.encodeEntityPath(entity)));
    }

    NtEntityWriter(NtRecorder recorder, NtRecorder.EntityState state) {
        this.recorder = recorder;
        this.state = state;
    }

    /** The escaped entity path without the {@code /Rerun} root, e.g. {@code /world/robot}. */
    public String entity() {
        return state.entity;
    }

    /** The absolute topic prefix of the entity, e.g. {@code /Rerun/world/robot}. */
    public String prefix() {
        return state.prefix;
    }

    public void doubles(String path, double[] values) {
        recorder.columnEntry(state, path).setDoubleArray(values);
    }

    public void longs(String path, long[] values) {
        recorder.columnEntry(state, path).setIntegerArray(values);
    }

    /** Publishes an opaque byte column, e.g. a {@code Binary} payload. */
    public void raw(String path, byte[] value) {
        recorder.columnEntry(state, path).setRaw(value);
    }

    public void booleans(String path, boolean[] values) {
        recorder.columnEntry(state, path).setBooleanArray(values);
    }

    public void strings(String path, String[] values) {
        recorder.columnEntry(state, path).setStringArray(values);
    }

    /**
     * Publishes a null mask for a nullable column node.
     *
     * @param values the mask, or {@code null} when every instance is present; generated code only
     *     allocates it once it sees a null
     * @param anyNull whether any instance is null; when {@code false} the mask is only written if
     *     it was ever written before, so a stale mask can never outlive its length.
     * @param count the number of instances in this batch
     */
    public void present(String path, boolean[] values, boolean anyNull, int count) {
        recorder.writePresent(state, path, values, anyNull, count);
    }

    /** Writes {@code <entity>/.rerun/<suffix>} if the value changed. */
    public void metaString(String suffix, String value) {
        recorder.metaString(state, suffix, value);
    }

    public void metaLong(String suffix, long value) {
        recorder.metaLong(state, suffix, value);
    }

    public void metaBoolean(String suffix, boolean value) {
        recorder.metaBoolean(state, suffix, value);
    }

    /** Writes a list of already-encoded component columns under {@code path}. */
    public <T> void component(String path, NtComponent<T> component, List<T> values) {
        component.writeColumns(this, path, values);
    }

    /** The absolute topic name of a generated column path. */
    public String topic(String path) {
        return path.isEmpty() ? state.prefix : state.prefix + '/' + path;
    }
}
