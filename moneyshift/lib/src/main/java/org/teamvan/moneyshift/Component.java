package org.teamvan.moneyshift;

/**
 * Marker for a single Rerun component type.
 *
 * <p>Every generated component class, whether a record wrapper or a C-style enum, implements this
 * interface. The static {@code batch} factory on each component builds the Arrow column that the
 * recording stream stores in a chunk.
 */
public interface Component {}
