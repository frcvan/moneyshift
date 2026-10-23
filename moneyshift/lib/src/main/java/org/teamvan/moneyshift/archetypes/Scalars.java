// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * One or more double-precision scalar values, e.g. for use for time-series plots.
 * 
 * The current timeline value will be used for the time/X-axis, hence scalars
 * should not be static.
 * Number of scalars per timestamp is expected to be the same over time.
 * 
 * When used to produce a plot, this archetype is used to provide the data that
 * is referenced by {@link org.teamvan.moneyshift.archetypes.SeriesLines rerun::archetypes::SeriesLines} or {@link org.teamvan.moneyshift.archetypes.SeriesPoints rerun::archetypes::SeriesPoints}. You can do
 * this by logging both archetypes to the same path, or alternatively configuring
 * the plot-specific archetypes through the blueprint.
 * 
 * Each scalar in a batch is plotted as its own series.
 * To show only some of them, set {@code visible_series} of {@link org.teamvan.moneyshift.archetypes.SeriesLines rerun::archetypes::SeriesLines} or {@link org.teamvan.moneyshift.archetypes.SeriesPoints rerun::archetypes::SeriesPoints},
 * or click series in the plot legend.
 * 
 */
public record Scalars(
    /**
     * The scalar values to log.
     */
    java.util.List<org.teamvan.moneyshift.components.Scalar> scalars
) implements org.teamvan.moneyshift.Archetype {
    public Scalars {
        java.util.Objects.requireNonNull(scalars, "scalars");
        scalars = java.util.List.copyOf(scalars);
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.Scalars";
    public static final int NUM_COMPONENTS = 1;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (!scalars.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("scalars").append('=').append(org.teamvan.moneyshift.components.Scalar.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (!scalars.isEmpty()) {
            org.teamvan.moneyshift.components.Scalar.writeColumnsStatic(w, "scalars", scalars);
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.Scalar> scalars;
        private Builder() {}

        public Builder withScalars(java.util.List<org.teamvan.moneyshift.components.Scalar> value) {
            this.scalars = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code double} value(s) directly. */
        public Builder withScalars(double... value) {
            java.util.List<org.teamvan.moneyshift.components.Scalar> values = new java.util.ArrayList<>(value.length);
            for (double v : value) {
                values.add(new org.teamvan.moneyshift.components.Scalar(v));
            }
            return withScalars(values);
        }

        public Builder withScalars(org.teamvan.moneyshift.components.Scalar... value) {
            return withScalars(java.util.List.of(value));
        }

        public Scalars build() {
            return new Scalars(scalars);
        }
    }

    public static Scalars of(
        java.util.List<org.teamvan.moneyshift.components.Scalar> scalars
    ) {
        return new Scalars(scalars);
    }
}
