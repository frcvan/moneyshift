// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * An infinite 3D plane represented by a unit normal vector and a distance.
 * 
 * Any point P on the plane fulfills the equation {@code dot(xyz, P) - d = 0},
 * where {@code xyz} is the plane's normal and {@code d} the distance of the plane from the origin.
 * This representation is also known as the Hesse normal form.
 * 
 * Note: although the normal will be passed through to the
 * datastore as provided, when used in the Viewer, planes will always be normalized.
 * I.e. the plane with xyz = (2, 0, 0), d = 1 is equivalent to xyz = (1, 0, 0), d = 0.5
 */
public record Plane3D(
    org.teamvan.moneyshift.encodings.Plane3D xyzd
) implements org.teamvan.moneyshift.Component {
    public Plane3D {
        java.util.Objects.requireNonNull(xyzd, "xyzd");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public Plane3D(float[] xyzd) {
        this(new org.teamvan.moneyshift.encodings.Plane3D(xyzd));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.Plane3D";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Plane3D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Plane3D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Plane3D> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Plane3D::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Plane3D value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Plane3D value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Plane3D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Plane3D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Plane3D value) {
        org.teamvan.moneyshift.encodings.Plane3D.writeOne(vector, index, value.xyzd());
    }

    public static Plane3D of(float... values) {
        if (values.length != 4) throw new java.lang.IllegalArgumentException("Plane3D needs exactly 4 values, got " + values.length);
        return new Plane3D(new org.teamvan.moneyshift.encodings.Plane3D(values));
    }
}
