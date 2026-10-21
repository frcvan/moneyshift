// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * The kind of the 3D eye to view a scene in a {@link org.teamvan.moneyshift.blueprint.views.Spatial3DView}.
 * 
 * This is used to specify how the controls of the view react to user input (such as mouse gestures).
 */
public enum Eye3DKind implements org.teamvan.moneyshift.Component {
    /**
     * First person point of view.
     * 
     * The camera perspective as if one is seeing it through the eyes of a person as popularized by first-person games.
     * The center of rotation is the position of the eye (the camera).
     * Dragging the mouse on the spatial 3D view, will rotation the scene as if one is moving
     * their head around.
     */
    FirstPerson(1L),
    /**
     * Orbital eye.
     * 
     * The center of rotation is located to a center location in front of the eye (it is different from the eye
     * location itself), as if the eye was orbiting around the scene.
     */
    Orbital(2L);

    private final long value;

    Eye3DKind(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static Eye3DKind fromValue(long value) {
        for (Eye3DKind candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown Eye3DKind value: " + value);
    }

    public static final java.lang.String NAME = "rerun.blueprint.components.Eye3DKind";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.Eye3DKind";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<Eye3DKind> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, Eye3DKind::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, Eye3DKind value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, Eye3DKind value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<Eye3DKind> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Eye3DKind value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, Eye3DKind value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
