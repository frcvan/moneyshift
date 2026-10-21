package org.teamvan.moneyshift;

import java.util.Objects;

/**
 * Identifies a component column: which archetype field it came from, the component identifier, and
 * the fully-qualified component type.
 *
 * <p>These three names are written into the Arrow schema, exactly like the official Rerun SDKs do,
 * which is what lets the viewer deserialize the data without a registry of definitions.
 */
public record ComponentDescriptor(String archetype, String component, String componentType) {
    public ComponentDescriptor {
        Objects.requireNonNull(component, "component");
        Objects.requireNonNull(componentType, "componentType");
        if (component.isEmpty()) {
            throw new IllegalArgumentException("component identifier must not be empty");
        }
    }

    /** A descriptor for a component logged outside of an archetype. */
    public static ComponentDescriptor component(String componentType) {
        String simpleName = componentType.substring(componentType.lastIndexOf('.') + 1);
        return new ComponentDescriptor(null, simpleName, componentType);
    }

    /** A descriptor for a field of an archetype. */
    public static ComponentDescriptor archetypeField(
            String archetype, String archetypeShortName, String field, String componentType) {
        return new ComponentDescriptor(archetype, archetypeShortName + ':' + field, componentType);
    }
}
