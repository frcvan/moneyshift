package org.teamvan.moneyshift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.junit.jupiter.api.Test;

/** Makes sure the generated API is not only compilable, but usable end to end. */
class GeneratedCoverageTest {

    @Test
    void everyArchetypeSerializesEveryField() throws Exception {
        List<Class<?>> archetypes = classesIn("org/teamvan/moneyshift/archetypes");
        archetypes.addAll(classesIn("org/teamvan/moneyshift/blueprint/archetypes"));
        archetypes.addAll(classesIn("org/teamvan/moneyshift/testing/archetypes"));

        assertTrue(archetypes.size() >= 100, "expected the full archetype set, got " + archetypes.size());

        try (BufferAllocator allocator = new RootAllocator()) {
            for (Class<?> archetype : archetypes) {
                assertTrue(
                        Archetype.class.isAssignableFrom(archetype),
                        archetype.getName() + " must implement Archetype");
                Object instance = Dummy.of(archetype);
                Method serialize = archetype.getMethod("serialize", BufferAllocator.class);
                @SuppressWarnings("unchecked")
                List<ComponentBatch> batches =
                        (List<ComponentBatch>) serialize.invoke(instance, allocator);
                assertNotNull(batches, archetype.getName() + " returned null batches");
                for (ComponentBatch batch : batches) {
                    assertNotNull(batch.descriptor().component());
                    assertNotNull(batch.descriptor().componentType());
                    assertTrue(batch.count() > 0, "dummy values should serialize to at least one row");
                    batch.close();
                }
            }
        }
    }

    @Test
    void everyComponentBuildsAnArrowBatch() throws Exception {
        List<Class<?>> components = classesIn("org/teamvan/moneyshift/components");
        components.addAll(classesIn("org/teamvan/moneyshift/blueprint/components"));
        components.addAll(classesIn("org/teamvan/moneyshift/testing/components"));

        assertTrue(components.size() >= 150, "expected the full component set, got " + components.size());

        try (BufferAllocator allocator = new RootAllocator()) {
            for (Class<?> component : components) {
                assertTrue(
                        Component.class.isAssignableFrom(component),
                        component.getName() + " must implement Component");
                Object value = Dummy.of(component);
                Method batch = null;
                for (Method candidate : component.getMethods()) {
                    if (candidate.getName().equals("batch")
                            && candidate.getParameterCount() == 2
                            && candidate.getParameterTypes()[0] == BufferAllocator.class
                            && candidate.getParameterTypes()[1].isAssignableFrom(component)) {
                        batch = candidate;
                        break;
                    }
                }
                assertNotNull(batch, component.getName() + " has no batch(allocator, value)");
                ComponentBatch result = (ComponentBatch) batch.invoke(null, allocator, value);
                assertNotNull(result);
                assertEquals(1, result.count());
                assertFalse(result.data().isNull(0), "the single component must not be null");
                result.close();
            }
        }
    }

    @Test
    void everyEncodingWritesAnArrowVector() throws Exception {
        List<Class<?>> encodings = classesIn("org/teamvan/moneyshift/encodings");
        encodings.addAll(classesIn("org/teamvan/moneyshift/blueprint/encodings"));
        encodings.addAll(classesIn("org/teamvan/moneyshift/testing/encodings"));

        assertTrue(encodings.size() >= 80, "expected the full encoding set, got " + encodings.size());

        try (BufferAllocator allocator = new RootAllocator()) {
            for (Class<?> encoding : encodings) {
                Method arrowField = encoding.getMethod("arrowField", String.class, boolean.class);
                org.apache.arrow.vector.types.pojo.Field field =
                        (org.apache.arrow.vector.types.pojo.Field)
                                arrowField.invoke(null, "item", true);
                Method write = encoding.getMethod("write", org.apache.arrow.vector.FieldVector.class, List.class);
                List<Object> values = new ArrayList<>();
                if (encoding.isInterface() && encoding.getPermittedSubclasses() != null) {
                    for (Class<?> variant : encoding.getPermittedSubclasses()) {
                        values.add(Dummy.of(variant));
                    }
                } else {
                    values.add(Dummy.of(encoding));
                }
                try (org.apache.arrow.vector.FieldVector vector =
                        (org.apache.arrow.vector.FieldVector) field.createVector(allocator)) {
                    write.invoke(null, vector, values);
                    assertEquals(values.size(), vector.getValueCount(), encoding.getName());
                }
            }
        }
    }

    private static List<Class<?>> classesIn(String resourceDirectory) throws Exception {
        URL url = GeneratedCoverageTest.class.getClassLoader().getResource(resourceDirectory);
        assertNotNull(url, "missing generated package " + resourceDirectory);
        Path directory = Path.of(url.toURI());
        List<Class<?>> classes = new ArrayList<>();
        try (Stream<Path> paths = Files.list(directory)) {
            paths.filter(path -> path.toString().endsWith(".class"))
                    .sorted(Comparator.comparing(Path::toString))
                    .forEach(path -> {
                        String file = path.getFileName().toString();
                        String simple = file.substring(0, file.length() - ".class".length());
                        if (simple.contains("$")) {
                            return;
                        }
                        String packageName = resourceDirectory.replace('/', '.');
                        try {
                            classes.add(Class.forName(packageName + '.' + simple));
                        } catch (ClassNotFoundException e) {
                            throw new AssertionError(e);
                        }
                    });
        }
        return classes;
    }

    /** Builds a non-null value for any generated record, enum, list, array, or sealed union. */
    private static final class Dummy {
        static Object of(Class<?> type) throws Exception {
            if (type == boolean.class || type == Boolean.class) {
                return true;
            } else if (type == byte.class || type == Byte.class) {
                return (byte) 1;
            } else if (type == short.class || type == Short.class) {
                return (short) 1;
            } else if (type == int.class || type == Integer.class) {
                return 1;
            } else if (type == long.class || type == Long.class) {
                return 1L;
            } else if (type == float.class || type == Float.class) {
                return 1.0f;
            } else if (type == double.class || type == Double.class) {
                return 1.0;
            } else if (type == char.class || type == Character.class) {
                return 'x';
            } else if (type == String.class) {
                return "x";
            } else if (type == byte[].class) {
                return new byte[] {1};
            } else if (type.isArray()) {
                // Generated fixed-size lists range from 2 to 16 elements; fill enough for all.
                Object array = Array.newInstance(type.getComponentType(), 16);
                for (int i = 0; i < 16; i++) {
                    Array.set(array, i, of(type.getComponentType()));
                }
                return array;
            } else if (List.class.isAssignableFrom(type)) {
                return new ArrayList<>(List.of(of(String.class)));
            } else if (type.isEnum()) {
                return type.getEnumConstants()[0];
            } else if (type.isInterface()) {
                Class<?> implementation = firstPermitted(type);
                return of(implementation);
            } else if (type.isRecord()) {
                RecordComponent[] recordComponents = type.getRecordComponents();
                Class<?>[] parameterTypes = new Class<?>[recordComponents.length];
                Object[] arguments = new Object[recordComponents.length];
                for (int i = 0; i < recordComponents.length; i++) {
                    parameterTypes[i] = recordComponents[i].getType();
                    arguments[i] = of(recordComponents[i].getGenericType());
                }
                Constructor<?> constructor = type.getDeclaredConstructor(parameterTypes);
                constructor.setAccessible(true);
                return constructor.newInstance(arguments);
            }
            throw new IllegalArgumentException("cannot build a dummy " + type.getName());
        }

        static Object of(Type genericType) throws Exception {
            if (genericType instanceof ParameterizedType parameterized) {
                Type raw = parameterized.getRawType();
                if (raw == List.class) {
                    Type element = parameterized.getActualTypeArguments()[0];
                    return new ArrayList<>(List.of(of(element)));
                }
                if (raw instanceof Class<?> rawClass) {
                    return of(rawClass);
                }
            }
            if (genericType instanceof Class<?> rawClass) {
                return of(rawClass);
            }
            throw new IllegalArgumentException("cannot build a dummy " + genericType);
        }

        private static Class<?> firstPermitted(Class<?> type) {
            Class<?>[] permitted = type.getPermittedSubclasses();
            if (permitted != null && permitted.length > 0) {
                return permitted[0];
            }
            throw new IllegalArgumentException("no implementation for " + type.getName());
        }
    }
}
