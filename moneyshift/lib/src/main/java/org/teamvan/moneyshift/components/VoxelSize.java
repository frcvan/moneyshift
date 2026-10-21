// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * The scene-unit dimensions of one voxel in a sparse 3D voxel grid.
 * 
 * Each component is the size of a voxel along the corresponding local grid axis.
 * All components must be finite and positive.
 */
public record VoxelSize(
    org.teamvan.moneyshift.encodings.Vec3D xyz
) implements org.teamvan.moneyshift.Component {
    public VoxelSize {
        java.util.Objects.requireNonNull(xyz, "xyz");
    }

    /** Creates a value from unwrapped fields, skipping the encoding record. */
    public VoxelSize(float[] xyz) {
        this(new org.teamvan.moneyshift.encodings.Vec3D(xyz));
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.VoxelSize";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Vec3D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Vec3D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<VoxelSize> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, VoxelSize::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, VoxelSize value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, VoxelSize value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<VoxelSize> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            VoxelSize value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, VoxelSize value) {
        org.teamvan.moneyshift.encodings.Vec3D.writeOne(vector, index, value.xyz());
    }

    public static VoxelSize of(float... values) {
        if (values.length != 3) throw new java.lang.IllegalArgumentException("VoxelSize needs exactly 3 values, got " + values.length);
        return new VoxelSize(new org.teamvan.moneyshift.encodings.Vec3D(values));
    }
}
