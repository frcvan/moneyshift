package org.teamvan.moneyshift;

import java.util.Objects;

/** A normalized Rerun entity path, e.g. {@code /world/robot/pose}. */
public record EntityPath(String path) implements Comparable<EntityPath> {
    public static final EntityPath ROOT = new EntityPath("/");

    public EntityPath {
        Objects.requireNonNull(path, "path");
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("entity path must start with '/': " + path);
        }
    }

    /**
     * Parses a path the same forgiving way Rerun does: empty segments are dropped and a leading
     * slash is implied. {@code "world//robot/"} becomes {@code "/world/robot"}.
     */
    public static EntityPath parse(String path) {
        Objects.requireNonNull(path, "path");
        StringBuilder normalized = new StringBuilder();
        int start = 0;
        int length = path.length();
        while (start < length) {
            int end = path.indexOf('/', start);
            if (end < 0) {
                end = length;
            }
            if (end > start) {
                normalized.append('/').append(path, start, end);
            }
            start = end + 1;
        }
        return normalized.isEmpty() ? ROOT : new EntityPath(normalized.toString());
    }

    /** The last part of the path, e.g. {@code "pose"}. */
    public String lastSegment() {
        int index = path.lastIndexOf('/');
        return index < 0 ? path : path.substring(index + 1);
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public int compareTo(EntityPath other) {
        return path.compareTo(other.path);
    }
}
