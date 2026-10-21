// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.encodings;

/**
 * Associate components of an entity to components of a visualizer.
 */
public record VisualizerComponentMapping(
    /**
     * Target component name which is being mapped to.
     * 
     * This represents a "slot" on the visualizer.
     */
    org.teamvan.moneyshift.encodings.Utf8 target,
    /**
     * What kind of source to pick.
     */
    org.teamvan.moneyshift.blueprint.encodings.ComponentSourceKind sourceKind,
    /**
     * Component selector for mapping.
     * 
     * Defaults to {@code target} if not specified.
     */
    java.lang.String sourceComponent,
    /**
     * Optional selector string using jq-like syntax to pick a specific field on {@code source_component}.
     * 
     * Example: ".x" picks a field called "x" from the {@code source_component} if present.
     * 
     * Defaults to empty string if not specified.
     */
    java.lang.String selector
) {
    public VisualizerComponentMapping {
        java.util.Objects.requireNonNull(target, "target");
        java.util.Objects.requireNonNull(sourceKind, "sourceKind");
    }
    public static final java.lang.String NAME = "rerun.blueprint.encodings.VisualizerComponentMapping";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.Utf8.arrowField("target", false), org.teamvan.moneyshift.blueprint.encodings.ComponentSourceKind.arrowField("source_kind", false), org.teamvan.moneyshift.ArrowFields.field("source_component", true, org.teamvan.moneyshift.ArrowFields.UTF8, java.util.List.of()), org.teamvan.moneyshift.ArrowFields.field("selector", true, org.teamvan.moneyshift.ArrowFields.UTF8, java.util.List.of())));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<VisualizerComponentMapping> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            VisualizerComponentMapping value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, VisualizerComponentMapping value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.Utf8 f0 = value.target();
        org.teamvan.moneyshift.encodings.Utf8.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.blueprint.encodings.ComponentSourceKind f1 = value.sourceKind();
        org.teamvan.moneyshift.blueprint.encodings.ComponentSourceKind.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
        java.lang.String f2 = value.sourceComponent();
        if (f2 == null) {
            ((org.apache.arrow.vector.VarCharVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(2)).setNull(index);
        } else {
            ((org.apache.arrow.vector.VarCharVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(2)).setSafe(index, f2.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
        java.lang.String f3 = value.selector();
        if (f3 == null) {
            ((org.apache.arrow.vector.VarCharVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(3)).setNull(index);
        } else {
            ((org.apache.arrow.vector.VarCharVector) (org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(3)).setSafe(index, f3.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
    }
}
