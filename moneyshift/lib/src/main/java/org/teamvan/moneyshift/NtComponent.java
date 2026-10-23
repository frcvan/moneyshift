package org.teamvan.moneyshift;

import java.util.List;

/**
 * A single Rerun component type that can be published over NetworkTables.
 *
 * <p>Every generated component implements this interface. The static {@code writeColumns} method
 * on each component writes the columns for a whole list at once.
 */
public interface NtComponent<T> extends Component {
    /** The fully-qualified component type, e.g. {@code rerun.components.Position3D}. */
    String componentType();

    /** The archetype field name; defaults to the component's short name. */
    default String fieldName() {
        String type = componentType();
        return type.substring(type.lastIndexOf('.') + 1);
    }

    /** Writes one column tree with {@code path} as its root. */
    void writeColumns(NtEntityWriter writer, String path, List<T> values);
}
