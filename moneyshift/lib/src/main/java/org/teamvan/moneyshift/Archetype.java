package org.teamvan.moneyshift;

/** A high-level group of components that Rerun knows how to visualize together. */
public interface Archetype {
    /** The fully-qualified archetype name, e.g. {@code rerun.archetypes.Points3D}. */
    String archetypeName();

    /** Writes this log call's metadata and component columns. */
    void writeColumns(NtEntityWriter writer);
}
