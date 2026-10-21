package org.teamvan.moneyshift;

import java.util.List;
import java.util.Objects;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.FieldVector;
import org.apache.arrow.vector.complex.ListVector;
import org.apache.arrow.vector.types.pojo.Field;

/**
 * A component's values, ready to be put into a chunk.
 *
 * <p>The backing Arrow vector contains one row whose list holds all the instances logged at this
 * time point, which is exactly how Rerun represents a component column. The batch owns the vector
 * and must be closed, usually by the {@link RecordingStream} that consumed it.
 */
public final class ComponentBatch implements AutoCloseable {
    private final ComponentDescriptor descriptor;
    private final ListVector data;

    public ComponentBatch(ComponentDescriptor descriptor, ListVector data) {
        this.descriptor = Objects.requireNonNull(descriptor, "descriptor");
        this.data = Objects.requireNonNull(data, "data");
    }

    public ComponentDescriptor descriptor() {
        return descriptor;
    }

    public ListVector data() {
        return data;
    }

    /** Number of instances in this batch. */
    public int count() {
        return data.getDataVector().getValueCount();
    }

    /**
     * Serializes {@code values} into a single-row list column for {@code descriptor}.
     *
     * @param itemType a field describing the component's Arrow type; only its type and children
     *     are used, the canonical item name/nullability is imposed here
     */
    public static <T> ComponentBatch of(
            ComponentDescriptor descriptor,
            Field itemType,
            BufferAllocator allocator,
            List<T> values,
            ValueWriter<T> writer) {
        Objects.requireNonNull(values, "values");
        Field column = ArrowFields.componentList(descriptor, itemType);
        ListVector vector = (ListVector) column.createVector(allocator);
        try {
            vector.startNewValue(0);
            FieldVector child = vector.getDataVector();
            writer.write(child, values);
            vector.endValue(0, values.size());
            vector.setValueCount(1);
            return new ComponentBatch(descriptor, vector);
        } catch (RuntimeException | Error e) {
            vector.close();
            throw e;
        }
    }

    @Override
    public void close() {
        data.close();
    }
}
