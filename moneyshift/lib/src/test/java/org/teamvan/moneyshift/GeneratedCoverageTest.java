package org.teamvan.moneyshift;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wpilib.networktables.NetworkTableInstance;

/** Every generated archetype, component, and encoding must write its columns. */
class GeneratedCoverageTest {
    private NetworkTableInstance instance;
    private NtRecorder recorder;

    @BeforeEach
    void setUp() {
        instance = NetworkTableInstance.create();
        NtRecorder.setInstance(instance);
        recorder = NtRecorder.getInstance();
    }

    @AfterEach
    void tearDown() {
        NtRecorder.setInstance(null);
        instance.close();
    }

    @Test
    void everyArchetypeWritesColumns() throws Exception {
        List<Class<?>> archetypes = classesIn("org/teamvan/moneyshift/archetypes");
        assertTrue(archetypes.size() > 50, "expected the full non-testing archetype set");

        for (Class<?> archetype : archetypes) {
            assertTrue(
                    Archetype.class.isAssignableFrom(archetype),
                    archetype.getName() + " must implement Archetype");
            Archetype value = (Archetype) Dummy.of(archetype);
            NtEntityWriter writer =
                    new NtEntityWriter(recorder, "coverage/" + archetype.getSimpleName());
            value.writeColumns(writer);
            assertNotNull(value.archetypeName());
        }
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void everyComponentWritesColumns() throws Exception {
        List<Class<?>> components = classesIn("org/teamvan/moneyshift/components");
        assertTrue(components.size() > 50, "expected the full non-testing component set");

        for (Class<?> component : components) {
            assertTrue(
                    NtComponent.class.isAssignableFrom(component),
                    component.getName() + " must implement NtComponent");
            NtComponent value = (NtComponent) Dummy.of(component);
            NtEntityWriter writer =
                    new NtEntityWriter(recorder, "coverage/" + component.getSimpleName());
            value.writeColumns(writer, "field", List.of(value));
        }
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void everyEncodingWritesColumns() throws Exception {
        List<Class<?>> encodings = classesIn("org/teamvan/moneyshift/encodings");
        assertTrue(encodings.size() > 40, "expected the full non-testing encoding set");

        for (Class<?> encoding : encodings) {
            assertTrue(
                    NtEncoding.class.isAssignableFrom(encoding),
                    encoding.getName() + " must implement NtEncoding");
            NtEncoding value = (NtEncoding) Dummy.of(encoding);
            NtEntityWriter writer =
                    new NtEntityWriter(recorder, "coverage/" + encoding.getSimpleName());
            value.writeColumns(writer, "field", List.of(value));
        }
    }

    @Test
    void noBlueprintOrTestingPackagesAreGenerated() {
        ClassLoader loader = GeneratedCoverageTest.class.getClassLoader();
        assertNull(loader.getResource("org/teamvan/moneyshift/testing"));
        assertNull(loader.getResource("org/teamvan/moneyshift/blueprint"));
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
