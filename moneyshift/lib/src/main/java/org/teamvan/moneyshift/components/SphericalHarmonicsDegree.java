// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The highest spherical harmonics degree to evaluate when rendering, 0-3.
 * 
 * {@code 0} renders the view-independent base color only, and is the fastest.
 * Each higher degree brings in more view-dependent detail, at the cost of fetching and
 * evaluating more coefficients ({@link org.teamvan.moneyshift.components.SphericalHarmonics3Rgb}):
 * 3 of them for degree 1, 8 for degree 2, and all 15 for degree 3.
 * 
 * Lowering this in the blueprint can make the rendering a lot faster.
 * 
 * Defaults to 3, i.e. every coefficient the data has.
 */
public record SphericalHarmonicsDegree(
    org.teamvan.moneyshift.encodings.UInt32 degree
) implements org.teamvan.moneyshift.Component {
    public SphericalHarmonicsDegree {
        java.util.Objects.requireNonNull(degree, "degree");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.SphericalHarmonicsDegree";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.UInt32.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.UInt32.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<SphericalHarmonicsDegree> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, SphericalHarmonicsDegree::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, SphericalHarmonicsDegree value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, SphericalHarmonicsDegree value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<SphericalHarmonicsDegree> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            SphericalHarmonicsDegree value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.UInt4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, SphericalHarmonicsDegree value) {
        org.teamvan.moneyshift.encodings.UInt32.writeOne(vector, index, value.degree());
    }
}
