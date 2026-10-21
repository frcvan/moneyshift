package org.teamvan.moneyshift;

import java.util.List;

import org.apache.arrow.vector.FieldVector;

/** Writes a list of native values into an already-allocated Arrow vector. */
@FunctionalInterface
public interface ValueWriter<T> {
    void write(FieldVector vector, List<T> values);
}
