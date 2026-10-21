// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A line strip in 3D space.
 * 
 * A line strip is a list of points connected by line segments. It can be used to draw
 * approximations of smooth curves.
 * 
 * The points will be connected in order, like so:
 * {@code }`text
 *        2------3     5
 *       /        \   /
 * 0----1          \ /
 *                  4
 * {@code }`
 */
public record LineStrip3D(
    java.util.List<org.teamvan.moneyshift.encodings.Vec3D> points
) implements org.teamvan.moneyshift.Component {
    public LineStrip3D {
        points = java.util.List.copyOf(points);
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.LineStrip3D";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.List();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.List(), java.util.List.of(org.teamvan.moneyshift.encodings.Vec3D.arrowField("item", false)));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<LineStrip3D> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, LineStrip3D::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, LineStrip3D value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, LineStrip3D value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<LineStrip3D> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            LineStrip3D value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.ListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.ListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, LineStrip3D value) {
        {
            org.apache.arrow.vector.complex.ListVector list0 = (org.apache.arrow.vector.complex.ListVector) vector;
            int start0 = list0.startNewValue(index);
            org.apache.arrow.vector.FieldVector child0 = list0.getDataVector();
            java.util.List<org.teamvan.moneyshift.encodings.Vec3D> values0 = value.points();
            for (int j0 = 0; j0 < values0.size(); j0++) {
                org.teamvan.moneyshift.encodings.Vec3D.writeOne(child0, start0 + j0, values0.get(j0));
            }
            list0.endValue(index, values0.size());
        }
    }
}
