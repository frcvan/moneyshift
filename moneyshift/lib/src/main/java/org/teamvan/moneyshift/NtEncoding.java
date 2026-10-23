package org.teamvan.moneyshift;

import java.util.List;

/**
 * A Rerun datatype that is not itself a component: a struct, union, or enum encoding.
 *
 * <p>Generated encodings implement this interface so callers can write them directly, though the
 * generated archetype and component code inlines the column layout instead.
 */
public interface NtEncoding<T> {
    /** Writes one column tree with {@code path} as its root. */
    void writeColumns(NtEntityWriter writer, String path, List<T> values);
}
