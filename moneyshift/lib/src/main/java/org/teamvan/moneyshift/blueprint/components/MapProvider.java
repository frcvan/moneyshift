// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.components;

/**
 * Name of the map provider to be used in Map views.
 */
public enum MapProvider implements org.teamvan.moneyshift.Component {
    /**
     * {@code OpenStreetMap} is the default map provider.
     */
    OpenStreetMap(1L),
    /**
     * Mapbox Streets is a minimalistic map designed by Mapbox.
     */
    MapboxStreets(2L),
    /**
     * Mapbox Dark is a dark-themed map designed by Mapbox.
     */
    MapboxDark(3L),
    /**
     * Mapbox Satellite is a satellite map designed by Mapbox.
     */
    MapboxSatellite(4L),
    /**
     * Mapbox Light is a light-themed map designed by Mapbox.
     */
    MapboxLight(5L);

    private final long value;

    MapProvider(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public static MapProvider fromValue(long value) {
        for (MapProvider candidate : values()) {
            if (candidate.value == value) return candidate;
        }
        throw new java.lang.IllegalArgumentException("unknown MapProvider value: " + value);
    }

    public static final java.lang.String NAME = "rerun.blueprint.components.MapProvider";
    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.ArrowFields.UINT8;
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.ArrowFields.field(name, nullable, arrowType(), java.util.List.of());
    }

    public static final java.lang.String COMPONENT_TYPE = "rerun.blueprint.components.MapProvider";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<MapProvider> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, MapProvider::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, MapProvider value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, MapProvider value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<MapProvider> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            MapProvider value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.UInt1Vector) vector).setNull(i);
            } else {
                ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(i, (int) value.value());
            }
        }
        ((org.apache.arrow.vector.UInt1Vector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, MapProvider value) {
        ((org.apache.arrow.vector.UInt1Vector) vector).setSafe(index, (int) value.value());
    }
}
