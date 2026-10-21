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
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_CENTERS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GaussianSplats3D:centers", "rerun.components.Position3D");

    /**
     * The centers (means) of the gaussians.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorCenters() {
        return DESCRIPTOR_CENTERS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SCALES = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GaussianSplats3D:scales", "rerun.components.Scale3D");

    /**
     * Per-axis standard deviations of the gaussians, in scene units.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorScales() {
        return DESCRIPTOR_SCALES;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_QUATERNIONS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GaussianSplats3D:quaternions", "rerun.components.RotationQuat");

    /**
     * The orientations of the gaussians.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorQuaternions() {
        return DESCRIPTOR_QUATERNIONS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_COLORS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GaussianSplats3D:colors", "rerun.components.Color");

    /**
     * The base colors and opacities of the gaussians.
     * 
     * The RGB part is the view-independent base color, i.e. the degree-0 (DC) term of the spherical harmonics.
     * The alpha part is the peak opacity of the gaussian; the gaussian falloff further modulates it spatially.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorColors() {
        return DESCRIPTOR_COLORS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SH_COEFFICIENTS = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GaussianSplats3D:sh_coefficients", "rerun.components.SphericalHarmonics3Rgb");

    /**
     * Higher-order spherical harmonics coefficients for view-dependent color.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorShCoefficients() {
        return DESCRIPTOR_SH_COEFFICIENTS;
    }
    private static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR_SPHERICAL_HARMONICS_DEGREE = new org.teamvan.moneyshift.ComponentDescriptor(ARCHETYPE_NAME, "GaussianSplats3D:spherical_harmonics_degree", "rerun.components.SphericalHarmonicsDegree");

    /**
     * The highest spherical harmonics degree to evaluate when rendering, 0-3.
     * 
     * Lower values render faster; {@code 0} disables view-dependent color entirely.
     * If not set, defaults to 3, i.e. all coefficients present in the data are used.
     */
    public static org.teamvan.moneyshift.ComponentDescriptor descriptorSphericalHarmonicsDegree() {
        return DESCRIPTOR_SPHERICAL_HARMONICS_DEGREE;
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> requiredComponents() {
        return java.util.List.of(descriptorCenters());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> recommendedComponents() {
        return java.util.List.of(descriptorScales(), descriptorQuaternions(), descriptorColors());
    }

    public static java.util.List<org.teamvan.moneyshift.ComponentDescriptor> optionalComponents() {
        return java.util.List.of(descriptorShCoefficients(), descriptorSphericalHarmonicsDegree());
    }

    @Override
    public java.util.List<org.teamvan.moneyshift.ComponentBatch> serialize(org.apache.arrow.memory.BufferAllocator allocator) {
        java.util.List<org.teamvan.moneyshift.ComponentBatch> batches = new java.util.ArrayList<>(NUM_COMPONENTS);
        batches.add(org.teamvan.moneyshift.components.Position3D.batch(descriptorCenters(), allocator, centers));
        if (scales != null) {
            batches.add(org.teamvan.moneyshift.components.Scale3D.batch(descriptorScales(), allocator, scales));
        }
        if (quaternions != null) {
            batches.add(org.teamvan.moneyshift.components.RotationQuat.batch(descriptorQuaternions(), allocator, quaternions));
        }
        if (colors != null) {
            batches.add(org.teamvan.moneyshift.components.Color.batch(descriptorColors(), allocator, colors));
        }
        if (shCoefficients != null) {
            batches.add(org.teamvan.moneyshift.components.SphericalHarmonics3Rgb.batch(descriptorShCoefficients(), allocator, shCoefficients));
        }
        if (sphericalHarmonicsDegree != null) {
            batches.add(org.teamvan.moneyshift.components.SphericalHarmonicsDegree.batch(descriptorSphericalHarmonicsDegree(), allocator, sphericalHarmonicsDegree));
        }
        return batches;
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
