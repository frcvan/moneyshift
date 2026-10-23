// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.archetypes;

/**
 * 3D gaussian splats, e.g. from a 3D Gaussian Splatting (3DGS) reconstruction.
 * 
 * Each gaussian is an anisotropic 3D gaussian distribution:
 * a unit isotropic gaussian scaled per-axis, rotated, and translated.
 * The scales are the standard deviations along the principal axes, in scene units.
 * 
 * Note that unlike 3DGS training checkpoints (e.g. PLY files), all values are stored
 * in their natural form: linear scales, and base colors with opacity as RGBA.
 * 
 */
public record GaussianSplats3D(
    /**
     * The centers (means) of the gaussians.
     */
    java.util.List<org.teamvan.moneyshift.components.Position3D> centers,
    /**
     * Per-axis standard deviations of the gaussians, in scene units.
     */
    java.util.List<org.teamvan.moneyshift.components.Scale3D> scales,
    /**
     * The orientations of the gaussians.
     */
    java.util.List<org.teamvan.moneyshift.components.RotationQuat> quaternions,
    /**
     * The base colors and opacities of the gaussians.
     * 
     * The RGB part is the view-independent base color, i.e. the degree-0 (DC) term of the spherical harmonics.
     * The alpha part is the peak opacity of the gaussian; the gaussian falloff further modulates it spatially.
     */
    java.util.List<org.teamvan.moneyshift.components.Color> colors,
    /**
     * Higher-order spherical harmonics coefficients for view-dependent color.
     */
    java.util.List<org.teamvan.moneyshift.components.SphericalHarmonics3Rgb> shCoefficients,
    /**
     * The highest spherical harmonics degree to evaluate when rendering, 0-3.
     * 
     * Lower values render faster; {@code 0} disables view-dependent color entirely.
     * If not set, defaults to 3, i.e. all coefficients present in the data are used.
     */
    org.teamvan.moneyshift.components.SphericalHarmonicsDegree sphericalHarmonicsDegree
) implements org.teamvan.moneyshift.Archetype {
    public GaussianSplats3D {
        java.util.Objects.requireNonNull(centers, "centers");
        centers = java.util.List.copyOf(centers);
        if (scales != null) { scales = java.util.List.copyOf(scales); }
        if (quaternions != null) { quaternions = java.util.List.copyOf(quaternions); }
        if (colors != null) { colors = java.util.List.copyOf(colors); }
        if (shCoefficients != null) { shCoefficients = java.util.List.copyOf(shCoefficients); }
    }
    public static final java.lang.String ARCHETYPE_NAME = "rerun.archetypes.GaussianSplats3D";
    public static final int NUM_COMPONENTS = 6;

    @Override
    public java.lang.String archetypeName() {
        return ARCHETYPE_NAME;
    }

    @Override
    public void writeColumns(org.teamvan.moneyshift.NtEntityWriter w) {
        java.lang.StringBuilder components = new java.lang.StringBuilder();
        if (!centers.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("centers").append('=').append(org.teamvan.moneyshift.components.Position3D.COMPONENT_TYPE);
        }
        if (scales != null && !scales.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("scales").append('=').append(org.teamvan.moneyshift.components.Scale3D.COMPONENT_TYPE);
        }
        if (quaternions != null && !quaternions.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("quaternions").append('=').append(org.teamvan.moneyshift.components.RotationQuat.COMPONENT_TYPE);
        }
        if (colors != null && !colors.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("colors").append('=').append(org.teamvan.moneyshift.components.Color.COMPONENT_TYPE);
        }
        if (shCoefficients != null && !shCoefficients.isEmpty()) {
            if (components.length() > 0) components.append(',');
            components.append("sh_coefficients").append('=').append(org.teamvan.moneyshift.components.SphericalHarmonics3Rgb.COMPONENT_TYPE);
        }
        if (sphericalHarmonicsDegree != null) {
            if (components.length() > 0) components.append(',');
            components.append("spherical_harmonics_degree").append('=').append(org.teamvan.moneyshift.components.SphericalHarmonicsDegree.COMPONENT_TYPE);
        }
        w.metaString("archetype", ARCHETYPE_NAME);
        w.metaString("components", components.toString());
        if (!centers.isEmpty()) {
            org.teamvan.moneyshift.components.Position3D.writeColumnsStatic(w, "centers", centers);
        }
        if (scales != null && !scales.isEmpty()) {
            org.teamvan.moneyshift.components.Scale3D.writeColumnsStatic(w, "scales", scales);
        }
        if (quaternions != null && !quaternions.isEmpty()) {
            org.teamvan.moneyshift.components.RotationQuat.writeColumnsStatic(w, "quaternions", quaternions);
        }
        if (colors != null && !colors.isEmpty()) {
            org.teamvan.moneyshift.components.Color.writeColumnsStatic(w, "colors", colors);
        }
        if (shCoefficients != null && !shCoefficients.isEmpty()) {
            org.teamvan.moneyshift.components.SphericalHarmonics3Rgb.writeColumnsStatic(w, "sh_coefficients", shCoefficients);
        }
        if (sphericalHarmonicsDegree != null) {
            org.teamvan.moneyshift.components.SphericalHarmonicsDegree.writeColumnsStatic(w, "spherical_harmonics_degree", java.util.List.of(sphericalHarmonicsDegree));
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private java.util.List<org.teamvan.moneyshift.components.Position3D> centers;
        private java.util.List<org.teamvan.moneyshift.components.Scale3D> scales;
        private java.util.List<org.teamvan.moneyshift.components.RotationQuat> quaternions;
        private java.util.List<org.teamvan.moneyshift.components.Color> colors;
        private java.util.List<org.teamvan.moneyshift.components.SphericalHarmonics3Rgb> shCoefficients;
        private org.teamvan.moneyshift.components.SphericalHarmonicsDegree sphericalHarmonicsDegree;
        private Builder() {}

        public Builder withCenters(java.util.List<org.teamvan.moneyshift.components.Position3D> value) {
            this.centers = java.util.List.copyOf(value);
            return this;
        }

        public Builder withCenters(org.teamvan.moneyshift.components.Position3D... value) {
            return withCenters(java.util.List.of(value));
        }

        public Builder withScales(java.util.List<org.teamvan.moneyshift.components.Scale3D> value) {
            this.scales = java.util.List.copyOf(value);
            return this;
        }

        public Builder withScales(org.teamvan.moneyshift.components.Scale3D... value) {
            return withScales(java.util.List.of(value));
        }

        public Builder withQuaternions(java.util.List<org.teamvan.moneyshift.components.RotationQuat> value) {
            this.quaternions = java.util.List.copyOf(value);
            return this;
        }

        public Builder withQuaternions(org.teamvan.moneyshift.components.RotationQuat... value) {
            return withQuaternions(java.util.List.of(value));
        }

        public Builder withColors(java.util.List<org.teamvan.moneyshift.components.Color> value) {
            this.colors = java.util.List.copyOf(value);
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withColors(long... value) {
            java.util.List<org.teamvan.moneyshift.components.Color> values = new java.util.ArrayList<>(value.length);
            for (long v : value) {
                values.add(new org.teamvan.moneyshift.components.Color(v));
            }
            return withColors(values);
        }

        public Builder withColors(org.teamvan.moneyshift.components.Color... value) {
            return withColors(java.util.List.of(value));
        }

        public Builder withShCoefficients(java.util.List<org.teamvan.moneyshift.components.SphericalHarmonics3Rgb> value) {
            this.shCoefficients = java.util.List.copyOf(value);
            return this;
        }

        public Builder withShCoefficients(org.teamvan.moneyshift.components.SphericalHarmonics3Rgb... value) {
            return withShCoefficients(java.util.List.of(value));
        }

        public Builder withSphericalHarmonicsDegree(org.teamvan.moneyshift.components.SphericalHarmonicsDegree value) {
            this.sphericalHarmonicsDegree = value;
            return this;
        }

        /** Overload that takes the {@code long} value(s) directly. */
        public Builder withSphericalHarmonicsDegree(long value) {
            return withSphericalHarmonicsDegree(new org.teamvan.moneyshift.components.SphericalHarmonicsDegree(value));
        }

        public GaussianSplats3D build() {
            return new GaussianSplats3D(centers, scales, quaternions, colors, shCoefficients, sphericalHarmonicsDegree);
        }
    }

    public static GaussianSplats3D of(
        java.util.List<org.teamvan.moneyshift.components.Position3D> centers
    ) {
        return new GaussianSplats3D(centers, null, null, null, null, null);
    }
}
