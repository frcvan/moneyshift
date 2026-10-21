// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * Distance to the near clip plane used for {@code Spatial2DView}.
 */
public record NearClipPlane(
    /**
     * Distance to the near clip plane in 3D scene units.
     */
    org.teamvan.moneyshift.encodings.Float32 nearClipPlane
) implements org.teamvan.moneyshift.Component {
    public NearClipPlane {
        java.util.Objects.requireNonNull(nearClipPlane, "nearClipPlane");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.NearClipPlane";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Float32.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Float32.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<NearClipPlane> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, NearClipPlane::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, NearClipPlane value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, NearClipPlane value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<NearClipPlane> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            NearClipPlane value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.Float4Vector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.Float4Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, NearClipPlane value) {
        org.teamvan.moneyshift.encodings.Float32.writeOne(vector, index, value.nearClipPlane());
    }
}
