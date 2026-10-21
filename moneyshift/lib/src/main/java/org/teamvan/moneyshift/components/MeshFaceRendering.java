// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * Determines which faces of a mesh are rendered.
 * 
 * For this purpose, we assume that the winding order of vertices in a mesh is
 * consistent and that front faces are defined as those with vertices in counter clockwise order.
 */
public enum MeshFaceRendering implements org.teamvan.moneyshift.Component {
    /**
     * Show both back and front faces.
     */
    DoubleSided(1L),
    /**
     * Only front faces are shown.
     * 
     * Front faces are assumed to have a counter clockwise vertex winding order on screen.
     */
    Front(2L),
    /**
     * Only back faces are shown.
     * 
     * Back faces are assumed to have a clockwise vertex winding order on screen.
     */
    Back(3L);

    private final long value;

    MeshFaceRendering(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static MeshFaceRendering fromValue(long value) {
        for (MeshFaceRendering candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown MeshFaceRendering value: " + value);
    }

    public static final java.lang.String NAME = "rerun.components.MeshFaceRendering";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.components.MeshFaceRendering";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<MeshFaceRendering> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, MeshFaceRendering::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, MeshFaceRendering value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, MeshFaceRendering value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<MeshFaceRendering> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            MeshFaceRendering value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, MeshFaceRendering value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
