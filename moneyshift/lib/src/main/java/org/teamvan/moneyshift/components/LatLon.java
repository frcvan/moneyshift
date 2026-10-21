// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A geospatial position expressed in <a href="https://epsg.io/4326">EPSG:4326</a> latitude and longitude (North/East-positive degrees).
 */
public record LatLon(
    org.teamvan.moneyshift.encodings.DVec2D latLon
) implements org.teamvan.moneyshift.Component {
    public LatLon {
        java.util.Objects.requireNonNull(latLon, "latLon");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.LatLon";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.DVec2D.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.DVec2D.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<LatLon> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, LatLon::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, LatLon value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, LatLon value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<LatLon> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            LatLon value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.complex.FixedSizeListVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, LatLon value) {
        org.teamvan.moneyshift.encodings.DVec2D.writeOne(vector, index, value.latLon());
    }

    public static LatLon of(double... values) {
        if (values.length != 2) throw new java.lang.IllegalArgumentException("LatLon needs exactly 2 values, got " + values.length);
        return new LatLon(new org.teamvan.moneyshift.encodings.DVec2D(values));
    }
}
