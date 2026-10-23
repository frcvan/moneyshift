package org.teamvan.moneyshift;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.wpilib.networktables.NetworkTableEntry;
import org.wpilib.networktables.NetworkTableInstance;

/**
 * The process-wide Rerun recorder for FRC robots.
 *
 * <p>Everything is published as plain NetworkTables topics under the fixed {@value NtPath#ROOT}
 * root; the Rust {@code rerun-importer-firstrun --nt} bridge turns those topics into Rerun chunks.
 * The recorder never connects, starts, or stops a NetworkTables instance — the team owns it.
 *
 * <pre>{@code
 * NtRecorder rec = NtRecorder.getInstance();
 * rec.log("world/points", Points3D.builder()
 *         .withPositions(Position3D.of(1, 2, 3), Position3D.of(4, 5, 6))
 *         .build());
 * rec.flush();
 * }</pre>
 *
 * <p>Thread-safe. All topics for one {@code log} call are written together under a per-entity lock,
 * so readers never observe half a batch.
 */
public final class NtRecorder {
    private static NtRecorder singleton;

    private final NetworkTableInstance instance;
    private final ConcurrentHashMap<String, NetworkTableEntry> entries = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, EntityState> entities = new ConcurrentHashMap<>();

    private NtRecorder(NetworkTableInstance instance) {
        this.instance = Objects.requireNonNull(instance, "instance");
    }

    /**
     * The singleton recorder, backed by {@link NetworkTableInstance#getDefault()} until {@link
     * #setInstance(NetworkTableInstance)} swaps it.
     */
    public static NtRecorder getInstance() {
        synchronized (NtRecorder.class) {
            if (singleton == null) {
                singleton = new NtRecorder(NetworkTableInstance.getDefault());
            }
            return singleton;
        }
    }

    /**
     * Swaps the backing instance process-wide, flushing pending writes and clearing the entry and
     * metadata caches first. {@code null} restores {@link NetworkTableInstance#getDefault()}.
     */
    public static void setInstance(NetworkTableInstance instance) {
        NetworkTableInstance target =
                instance != null ? instance : NetworkTableInstance.getDefault();
        synchronized (NtRecorder.class) {
            if (NtRecorder.singleton != null) {
                NtRecorder.singleton.flush();
            }
            NtRecorder.singleton = new NtRecorder(target);
        }
    }

    /** The NetworkTables instance this recorder publishes to. */
    public NetworkTableInstance instance() {
        return instance;
    }

    /** Logs an archetype at {@code entity}, a relative path such as {@code "world/points"}. */
    public void log(String entity, Archetype archetype) {
        Objects.requireNonNull(archetype, "archetype");
        write(entity, false, archetype::writeColumns);
    }

    /** Logs an archetype once as static data (it exists on all timelines). */
    public void logStatic(String entity, Archetype archetype) {
        Objects.requireNonNull(archetype, "archetype");
        write(entity, true, archetype::writeColumns);
    }

    /**
     * Logs a component outside of an archetype. {@code field} becomes the column root and the
     * entity has no archetype marker.
     */
    public <T extends NtComponent<T>> void logComponent(
            String entity, String field, List<T> values) {
        Objects.requireNonNull(field, "field");
        Objects.requireNonNull(values, "values");
        if (field.isEmpty()) {
            throw new IllegalArgumentException("field must not be empty");
        }
        if (values.isEmpty()) {
            return;
        }
        String normalized = NtPath.encodeEntityPath(entity);
        EntityState state = state(normalized);
        synchronized (state) {
            ensureMode(state, false);
            if (values.get(0) == null) {
                throw new IllegalArgumentException("the first component value must not be null");
            }
            String componentType = values.get(0).componentType();
            state.rawComponents.put(field, componentType);
            metaLong(state, "layout", 1L);
            metaString(state, "archetype", "");
            metaString(state, "components", joinComponents(state.rawComponents));
            NtEntityWriter writer = new NtEntityWriter(this, normalized);
            values.get(0).writeColumns(writer, field, values);
        }
    }

    /** Flushes this recorder's instance, handing all pending entries to the NetworkTables server. */
    public void flush() {
        instance.flush();
    }

    // ------------------------------------------------------------- internals

    private void write(String entity, boolean isStatic, java.util.function.Consumer<NtEntityWriter> body) {
        String normalized = NtPath.encodeEntityPath(entity);
        EntityState state = state(normalized);
        synchronized (state) {
            ensureMode(state, isStatic);
            state.rawComponents.clear();
            metaLong(state, "layout", 1L);
            NtEntityWriter writer = new NtEntityWriter(this, normalized);
            body.accept(writer);
            if (isStatic) {
                metaBoolean(state, "static", true);
            }
        }
    }

    private EntityState state(String normalizedEntity) {
        return entities.computeIfAbsent(
                normalizedEntity,
                entity -> new EntityState(entity, NtPath.topicPrefix(entity)));
    }

    private static void ensureMode(EntityState state, boolean isStatic) {
        if (!state.modeKnown) {
            state.modeKnown = true;
            state.staticMode = isStatic;
            return;
        }
        if (state.staticMode != isStatic) {
            throw new IllegalStateException(
                    "entity '"
                            + state.entity
                            + "' was already logged "
                            + (state.staticMode ? "statically" : "dynamically")
                            + " and cannot switch to "
                            + (isStatic ? "static" : "dynamic"));
        }
    }

    private NetworkTableEntry entry(String topic) {
        NetworkTableEntry cached = entries.get(topic);
        if (cached != null) {
            return cached;
        }
        return entries.computeIfAbsent(topic, instance::getEntry);
    }

    private static String joinComponents(Map<String, String> components) {
        StringBuilder joined = new StringBuilder();
        for (Map.Entry<String, String> entry : components.entrySet()) {
            if (joined.length() > 0) {
                joined.append(',');
            }
            joined.append(entry.getKey()).append('=').append(entry.getValue());
        }
        return joined.toString();
    }

    void metaString(String entity, String suffix, String value) {
        metaString(state(entity), suffix, value);
    }

    private void metaString(EntityState state, String suffix, String value) {
        synchronized (state) {
            if (state.metaStrings.getOrDefault(suffix, null) != null
                    && state.metaStrings.get(suffix).equals(value)) {
                return;
            }
            state.metaStrings.put(suffix, value);
            entry(state.prefix + "/.rerun/" + suffix).setString(value);
        }
    }

    void metaLong(String entity, String suffix, long value) {
        metaLong(state(entity), suffix, value);
    }

    private void metaLong(EntityState state, String suffix, long value) {
        synchronized (state) {
            Boxed<Long> cached = state.metaLongs.get(suffix);
            if (cached != null && cached.value == value) {
                return;
            }
            state.metaLongs.put(suffix, new Boxed<>(value));
            entry(state.prefix + "/.rerun/" + suffix).setInteger(value);
        }
    }

    void metaBoolean(String entity, String suffix, boolean value) {
        metaBoolean(state(entity), suffix, value);
    }

    private void metaBoolean(EntityState state, String suffix, boolean value) {
        synchronized (state) {
            Boxed<Boolean> cached = state.metaBooleans.get(suffix);
            if (cached != null && cached.value == value) {
                return;
            }
            state.metaBooleans.put(suffix, new Boxed<>(value));
            entry(state.prefix + "/.rerun/" + suffix).setBoolean(value);
        }
    }

    void writePresent(String entity, String path, boolean[] values, boolean anyNull) {
        EntityState state = state(entity);
        synchronized (state) {
            boolean sticky = state.presentPaths.contains(path);
            if (!anyNull && !sticky) {
                return;
            }
            state.presentPaths.add(path);
            entry(state.prefix + '/' + path + "/.present").setBooleanArray(values);
        }
    }

    /**
     * Writes an unsigned 64-bit column and, when needed, its decimal string sidecar.
     *
     * <p>NetworkTables fixes a topic's type on first publish, so the column cannot switch between
     * {@code long[]} and {@code string[]}. Values that exceed {@link Long#MAX_VALUE} are therefore
     * published as decimal strings under {@code <path>/.u64}; the sidecar is sticky so later
     * batches of small values cannot hide the unsigned ones.
     */
    void writeU64(String entity, String path, long[] values, boolean hasUnsigned) {
        EntityState state = state(entity);
        synchronized (state) {
            entry(state.prefix + '/' + path).setIntegerArray(values);
            boolean sticky = state.u64Paths.contains(path);
            if (!hasUnsigned && !sticky) {
                return;
            }
            state.u64Paths.add(path);
            String[] text = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                text[i] = values[i] < 0L
                        ? java.lang.Long.toUnsignedString(values[i])
                        : java.lang.Long.toString(values[i]);
            }
            entry(state.prefix + '/' + path + "/.u64").setStringArray(text);
        }
    }

    void setDoubleArray(String topic, double[] values) {
        entry(topic).setDoubleArray(values);
    }

    void setIntegerArray(String topic, long[] values) {
        entry(topic).setIntegerArray(values);
    }

    void setBooleanArray(String topic, boolean[] values) {
        entry(topic).setBooleanArray(values);
    }

    void setStringArray(String topic, String[] values) {
        entry(topic).setStringArray(values);
    }

    void setRaw(String topic, byte[] value) {
        entry(topic).setRaw(value);
    }

    /** Mutable box for cache values, avoiding Optional allocation on the hot path. */
    private static final class Boxed<T> {
        final T value;

        Boxed(T value) {
            this.value = value;
        }
    }

    private static final class EntityState {
        final String entity;
        final String prefix;
        final Map<String, String> metaStrings = new HashMap<>();
        final Map<String, Boxed<Long>> metaLongs = new HashMap<>();
        final Map<String, Boxed<Boolean>> metaBooleans = new HashMap<>();
        final Set<String> presentPaths = new HashSet<>();
        final Set<String> u64Paths = new HashSet<>();
        final LinkedHashMap<String, String> rawComponents = new LinkedHashMap<>();
        boolean modeKnown;
        boolean staticMode;

        EntityState(String entity, String prefix) {
            this.entity = entity;
            this.prefix = prefix;
        }
    }
}
