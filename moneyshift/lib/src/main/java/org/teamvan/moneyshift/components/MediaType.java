// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.components;

/**
 * A standardized media type (RFC2046, formerly known as MIME types), encoded as a string.
 * 
 * The complete reference of officially registered media types is maintained by the IANA and can be
 * consulted at <https://www.iana.org/assignments/media-types/media-types.xhtml>.
 */
public record MediaType(
    org.teamvan.moneyshift.encodings.Utf8 value
) implements org.teamvan.moneyshift.Component {
    public MediaType {
        java.util.Objects.requireNonNull(value, "value");
    }
    public static final java.lang.String COMPONENT_TYPE = "rerun.components.MediaType";
    public static final org.teamvan.moneyshift.ComponentDescriptor DESCRIPTOR = org.teamvan.moneyshift.ComponentDescriptor.component(COMPONENT_TYPE);

    public static org.apache.arrow.vector.types.pojo.ArrowType arrowType() {
        return org.teamvan.moneyshift.encodings.Utf8.arrowType();
    }

    public static org.apache.arrow.vector.types.pojo.Field arrowField(java.lang.String name, boolean nullable) {
        return org.teamvan.moneyshift.encodings.Utf8.arrowField(name, nullable);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, java.util.List<MediaType> values) {
        return org.teamvan.moneyshift.ComponentBatch.of(descriptor, arrowField("item", true), allocator, values, MediaType::write);
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.teamvan.moneyshift.ComponentDescriptor descriptor, org.apache.arrow.memory.BufferAllocator allocator, MediaType value) {
        return batch(descriptor, allocator, java.util.List.of(value));
    }

    public static org.teamvan.moneyshift.ComponentBatch batch(org.apache.arrow.memory.BufferAllocator allocator, MediaType value) {
        return batch(DESCRIPTOR, allocator, java.util.List.of(value));
    }

    public static void write(org.apache.arrow.vector.FieldVector vector, java.util.List<MediaType> values) {
        int count = values.size();
        for (int i = 0; i < count; i++) {
            MediaType value = values.get(i);
            if (value == null) {
                ((org.apache.arrow.vector.VarCharVector) vector).setNull(i);
            } else {
                writeOne(vector, i, value);
            }
        }
        ((org.apache.arrow.vector.VarCharVector) vector).setValueCount(count);
    }

    public static void writeOne(org.apache.arrow.vector.FieldVector vector, int index, MediaType value) {
        org.teamvan.moneyshift.encodings.Utf8.writeOne(vector, index, value.value());
    }
}
