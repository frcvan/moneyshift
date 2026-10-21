// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * View-dependent color, expressed as spherical harmonics coefficients of degrees 1 through 3.
 * 
 * The view-independent (degree-0) base color is represented as a separate {@link org.teamvan.moneyshift.components.Color}.
 */
public record SphericalHarmonics3Rgb(
    org.teamvan.moneyshift.encodings.SphericalHarmonics3Rgb coefficients
) implements org.teamvan.moneyshift.Component {
    public SphericalHarmonics3Rgb {
        java.util.Objects.requireNonNull(coefficients, "coefficients");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.SphericalHarmonics3Rgb";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.SphericalHarmonics3Rgb.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.SphericalHarmonics3Rgb.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<SphericalHarmonics3Rgb> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, SphericalHarmonics3Rgb::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, SphericalHarmonics3Rgb value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, SphericalHarmonics3Rgb value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<SphericalHarmonics3Rgb> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            SphericalHarmonics3Rgb value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, SphericalHarmonics3Rgb value) {
        org.teamvan.moneyshift.encodings.SphericalHarmonics3Rgb.writeOne(vector, index, value.coefficients());
    }
}
