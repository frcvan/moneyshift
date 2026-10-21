// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Integer index of a voxel in a sparse 3D voxel grid.
 * 
 * The voxel center in local grid coordinates is {@code (index + 0.5) * voxel_size}.
 */
public record VoxelIndex(
    org.teamvan.moneyshift.encodings.IVec3D index
) implements org.teamvan.moneyshift.Component {
    public VoxelIndex {
        java.util.Objects.requireNonNull(index, "index");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.VoxelIndex";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.IVec3D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.IVec3D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<VoxelIndex> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, VoxelIndex::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, VoxelIndex value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, VoxelIndex value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<VoxelIndex> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            VoxelIndex value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, VoxelIndex value) {
        org.teamvan.moneyshift.encodings.IVec3D.writeOne(vector, index, value.index());
    }

    public static VoxelIndex of(int... values) {
        if (values.length != 3) throw new java.lang.IllegalArgumentException("VoxelIndex needs exactly 3 values, got " + values.length);
        return new VoxelIndex(new org.teamvan.moneyshift.encodings.IVec3D(values));
    }
}
