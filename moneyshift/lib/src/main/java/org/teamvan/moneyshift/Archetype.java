package org.teamvan.moneyshift;

import java.util.List;

import org.apache.arrow.memory.BufferAllocator;

/** A high-level group of components that Rerun knows how to visualize together. */
public interface Archetype {
    /**
     * Serializes this archetype's present components.
     *
     * <p>Returned batches borrow the supplied allocator and are closed by the caller.
     */
    List<ComponentBatch> serialize(BufferAllocator allocator);
}
