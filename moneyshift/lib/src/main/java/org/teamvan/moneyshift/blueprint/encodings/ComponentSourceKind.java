// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.encodings;

/**
 * What kind of source to use for a visualizer component mapping.
 */
public enum ComponentSourceKind {
    /**
     * Use an explicit selection defined by {@code source_component}.
     * 
     * May or may not make use of a selector string.
     * 
     * Selecting a source component that is not found on the entity is an error.
     */
    SourceComponent(1L),
    /**
     * Use a timeless override value that is defined in the blueprint.
     * 
     * The override value is stored on the same entity as the visualizer instruction
     * and uses the {@code target} as its component name.
     * 
     * Selecting this source without an override value for the target component is an error.
     */
    Override(2L),
    /**
     * Default as specified on the view's blueprint.
     * 
     * If the view doesn't specify a default for the target component name,
     * a heuristically determined value will be used instead.
     */
    Default(3L),
    /**
     * Resolve the value from class and keypoint IDs using the recording's annotation context.
     * 
     * If the annotation context does not provide a value, default values are used instead.
     * (Any overlapping recording values for the target component are ignored.)
     * 
     * This is only available for visualizers that support annotation context.
     * Selecting it for an unsupported component is an error.
     */
    AnnotationContext(4L);

    private final long value;

    ComponentSourceKind(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static ComponentSourceKind fromValue(long value) {
        for (ComponentSourceKind candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown ComponentSourceKind value: " + value);
    }

    public static final java.lang.String NAME = "rerun.blueprint.encodings.ComponentSourceKind";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ComponentSourceKind> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ComponentSourceKind value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ComponentSourceKind value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
