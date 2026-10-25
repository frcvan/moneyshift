// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * Sets the default orientation for 3D views at or below this entity.
 * 
 * The orientation determines the 3D view's eye orientation, navigation, and default grid plane.
 * It does not change logged transforms.
 * 
 * The three directions are always ordered as The three directions are always ordered as [x, y, z] and specify where each positive axis points.
 * For example, For example, [Right, Down, Forward] means that +X points right, +Y points down, and +Z points forward.
 * 
 * A 3D view uses the value logged at its origin entity or the closest ancestor.
 * <a href="https://rerun.io/docs/reference/types/views/spatial3d_view">SpatialInformation</a> can override it for an individual view.
 * 
 * ⚠ <a href="https://github.com/rerun-io/rerun/issues/5032">Rerun does not yet support left-handed coordinate systems</a>.
 * 
 */
public record ViewCoordinates(
    /**
     * The directions of the The directions of the [x, y, z] axes.
     */
    org.teamvan.moneyshift.components.ViewCoordinates xyz
) implements org.teamvan.moneyshift.Archetype {
    public ViewCoordinates {
        java.util.Objects.requireNonNull(xyz, "xyz");
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.ViewCoordinates";
    public static final int NUM_COMPONENTS = 1;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    private static final class ComponentsCache {
        final long mask;
        final java.lang.String components;

        ComponentsCache(long mask, java.lang.String components) {
            this.mask = mask;
            this.components = components;
        }
    }

    private static volatile ComponentsCache cachedComponents;

    private static java.lang.String componentsFor(long mask) {
        ComponentsCache cached = cachedComponents;
        if (cached != null && cached.mask == mask) {
            return cached.components;
        }
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if ((mask & 1L) != 0) {
            if (components.length() > 0) components.append(',');
            components.append("xyz").append('=').append(org.teamvan.moneyshift.components.ViewCoordinates.COMPONENT_TYPE);
        }
        java.lang.String joined = components.toString();
        cachedComponents = new ComponentsCache(mask, joined);
        return joined;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        long mask = 0L;
        if (true) mask |= 1L;
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", componentsFor(mask));
        if (true) {
            org.teamvan.moneyshift.components.ViewCoordinates.writeColumnsStatic(w, "xyz", java.util.List.of(xyz));
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.components.ViewCoordinates xyz;
        private Builder() {}

        public Builder withXyz(org.teamvan.moneyshift.components.ViewCoordinates value) {
            this.xyz = value;
            return this;
        }

        public ViewCoordinates build() {
            return new ViewCoordinates(xyz);
        }
    }

    public static ViewCoordinates of(
        org.teamvan.moneyshift.components.ViewCoordinates xyz
    ) {
        return new ViewCoordinates(xyz);
    }
}
