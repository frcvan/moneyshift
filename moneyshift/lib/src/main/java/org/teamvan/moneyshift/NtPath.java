package org.teamvan.moneyshift;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Entity path encoding for the {@code /Rerun} NetworkTables protocol.
 *
 * <p>Topics live under the fixed root {@value #ROOT}. Every entity segment is percent-encoded as
 * UTF-8 with {@code [A-Za-z0-9_-]} passing through unchanged, so a user segment can never
 * accidentally produce the reserved {@code .rerun} metadata segment or a nested path.
 */
public final class NtPath {
    /** The fixed NetworkTables topic root of the Rerun protocol. */
    public static final String ROOT = "/Rerun";

    private static final char[] HEX = "0123456789ABCDEF".toCharArray();

    private NtPath() {}

    /** Percent-encodes one path segment. */
    public static String encodeSegment(String segment) {
        Objects.requireNonNull(segment, "segment");
        StringBuilder encoded = new StringBuilder(segment.length());
        for (byte value : segment.getBytes(StandardCharsets.UTF_8)) {
            int b = value & 0xFF;
            if (isUnreserved(b)) {
                encoded.append((char) b);
            } else {
                encoded.append('%').append(HEX[b >>> 4]).append(HEX[b & 0x0F]);
            }
        }
        return encoded.toString();
    }

    /**
     * Normalizes a relative entity path into an absolute, escaped path below {@link #ROOT}.
     *
     * <p>Empty segments are dropped, matching Rerun's forgiving path parsing: {@code
     * "world//robot/"} becomes {@code "/world/robot"}. The result is never empty: the root entity
     * is rejected, because its topic prefix would also cover every other entity.
     */
    public static String encodeEntityPath(String path) {
        Objects.requireNonNull(path, "path");
        StringBuilder encoded = new StringBuilder(path.length() + 8);
        int start = 0;
        int length = path.length();
        while (start < length) {
            int end = path.indexOf('/', start);
            if (end < 0) {
                end = length;
            }
            if (end > start) {
                encoded.append('/').append(encodeSegment(path.substring(start, end)));
            }
            start = end + 1;
        }
        if (encoded.length() == 0) {
            throw new IllegalArgumentException(
                    "entity paths need at least one non-empty segment, got \"" + path + "\"");
        }
        return encoded.toString();
    }

    /**
     * The absolute topic prefix of an entity, e.g. {@code /Rerun/world/robot}.
     *
     * <p>{@code path} must be unescaped; passing an already escaped path escapes it twice.
     */
    public static String topicPrefix(String path) {
        return ROOT + encodeEntityPath(path);
    }

    private static boolean isUnreserved(int b) {
        return (b >= 'a' && b <= 'z')
                || (b >= 'A' && b <= 'Z')
                || (b >= '0' && b <= '9')
                || b == '_'
                || b == '-';
    }
}
