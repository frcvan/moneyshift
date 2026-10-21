package org.teamvan.moneyshift;

import java.util.Objects;

import org.apache.arrow.vector.types.TimeUnit;
import org.apache.arrow.vector.types.pojo.ArrowType;

/** A Rerun timeline: a name plus the type of time it carries. */
public record Timeline(String name, TimeType type) implements Comparable<Timeline> {
    /** The three kinds of time understood by Rerun. */
    public enum TimeType {
        /** A monotonic integer tick, e.g. a frame number. */
        SEQUENCE,
        /** Nanoseconds since the start of the recording. */
        DURATION_NS,
        /** Nanoseconds since the Unix epoch. */
        TIMESTAMP_NS;

        public ArrowType arrowType() {
            return switch (this) {
                case SEQUENCE -> new ArrowType.Int(64, true);
                case DURATION_NS -> new ArrowType.Duration(TimeUnit.NANOSECOND);
                case TIMESTAMP_NS -> new ArrowType.Timestamp(TimeUnit.NANOSECOND, null);
            };
        }
    }

    public Timeline {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(type, "type");
        if (name.isEmpty()) {
            throw new IllegalArgumentException("timeline name must not be empty");
        }
    }

    public static Timeline sequence(String name) {
        return new Timeline(name, TimeType.SEQUENCE);
    }

    public static Timeline duration(String name) {
        return new Timeline(name, TimeType.DURATION_NS);
    }

    public static Timeline timestamp(String name) {
        return new Timeline(name, TimeType.TIMESTAMP_NS);
    }

    /** The transport identifier of this timeline's time type. */
    public int protoTimeType() {
        return switch (type) {
            case SEQUENCE -> 1;
            case DURATION_NS -> 2;
            case TIMESTAMP_NS -> 3;
        };
    }

    @Override
    public int compareTo(Timeline other) {
        return name.compareTo(other.name);
    }
}
