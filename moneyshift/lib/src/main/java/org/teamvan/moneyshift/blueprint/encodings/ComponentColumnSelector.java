// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.encodings;

/**
 * Describe a component column to be selected in the dataframe view.
 */
public record ComponentColumnSelector(
    /**
     * The entity path for this component.
     */
    org.teamvan.moneyshift.encodings.EntityPath entityPath,
    /**
     * The name of the component.
     * 
     * This acts as the component name in the context of a given {@code entity_path}
     * An example for this would be {@code Points3D:positions}, for the {@code positions} field in {@link org.teamvan.moneyshift.archetypes.Points3D}.
     */
    org.teamvan.moneyshift.encodings.Utf8 component
) {
    public ComponentColumnSelector {
        java.util.Objects.requireNonNull(entityPath, "entityPath");
        java.util.Objects.requireNonNull(component, "component");
    }

    /** Creates a value from unwrapped fields, without encoding records. */
    public ComponentColumnSelector(java.lang.String entityPath, java.lang.String component) {
        this(new org.teamvan.moneyshift.encodings.EntityPath(entityPath), new org.teamvan.moneyshift.encodings.Utf8(component));
    }

    public static final java.lang.String NAME = "rerun.blueprint.encodings.ComponentColumnSelector";

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return new org.apache.arrow.vector.types.pojo.ArrowType.Struct();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, new org.apache.arrow.vector.types.pojo.ArrowType.Struct(), java.util.List.of(org.teamvan.moneyshift.encodings.EntityPath.arrowField("entity_path", false), org.teamvan.moneyshift.encodings.Utf8.arrowField("component", false)));
    }


    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<ComponentColumnSelector> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            ComponentColumnSelector value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.StructVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.StructVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, ComponentColumnSelector value) {
        org.apache.arrow.vector.complex.StructVector struct = (org.apache.arrow.vector.complex.StructVector) vector;
        struct.setIndexDefined(index);
        org.teamvan.moneyshift.encodings.EntityPath f0 = value.entityPath();
        org.teamvan.moneyshift.encodings.EntityPath.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(0), index, f0);
        org.teamvan.moneyshift.encodings.Utf8 f1 = value.component();
        org.teamvan.moneyshift.encodings.Utf8.writeOne((org.apache.arrow.vector.FieldVector) struct.getChildByOrdinal(1), index, f1);
    }
}
