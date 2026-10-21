package org.teamvan.moneyshift;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.arrow.vector.types.FloatingPointPrecision;
import org.apache.arrow.vector.types.UnionMode;
import org.apache.arrow.vector.types.pojo.ArrowType;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.FieldType;

/**
 * Builders for the Arrow fields used by the Rerun chunk format.
 *
 * <p>The metadata keys and field layouts here are wire-format details: they have to match
 * {@code re_sorbet} exactly or the viewer will reject the chunk.
 */
public final class ArrowFields {
    /** Field metadata key for the kind of column ({@code control}, {@code index} or {@code data}). */
    public static final String KIND = "rerun:kind";
    public static final String KIND_CONTROL = "control";
    public static final String KIND_INDEX = "index";
    public static final String KIND_DATA = "data";

    /** Field metadata key carrying a component's identifier. */
    public static final String COMPONENT = "rerun:component";
    public static final String ARCHETYPE = "rerun:archetype";
    public static final String COMPONENT_TYPE = "rerun:component_type";

    /** Record-batch metadata key carrying the entity path. */
    public static final String ENTITY_PATH = "rerun:entity_path";
    /** Record-batch metadata key carrying the chunk id. */
    public static final String CHUNK_ID = "rerun:id";
    /** Record-batch metadata key carrying the Sorbet schema version. */
    public static final String SORBET_VERSION = "sorbet:version";
    public static final String SORBET_VERSION_0_1_3 = "0.1.3";

    public static final String INDEX_NAME = "rerun:index_name";
    public static final String IS_SORTED = "rerun:is_sorted";
    public static final String IS_STATIC = "rerun:is_static";

    public static final String ARROW_EXTENSION_NAME = "ARROW:extension:name";
    public static final String ARROW_EXTENSION_METADATA = "ARROW:extension:metadata";
    public static final String TUID_EXTENSION = "rerun.datatypes.TUID";

    /** The name of the RowId column. */
    public static final String ROW_ID_COLUMN = "rerun.controls.RowId";

    public static final ArrowType INT8 = new ArrowType.Int(8, true);
    public static final ArrowType INT16 = new ArrowType.Int(16, true);
    public static final ArrowType INT32 = new ArrowType.Int(32, true);
    public static final ArrowType INT64 = new ArrowType.Int(64, true);
    public static final ArrowType UINT8 = new ArrowType.Int(8, false);
    public static final ArrowType UINT16 = new ArrowType.Int(16, false);
    public static final ArrowType UINT32 = new ArrowType.Int(32, false);
    public static final ArrowType UINT64 = new ArrowType.Int(64, false);
    public static final ArrowType FLOAT16 =
            new ArrowType.FloatingPoint(FloatingPointPrecision.HALF);
    public static final ArrowType FLOAT32 =
            new ArrowType.FloatingPoint(FloatingPointPrecision.SINGLE);
    public static final ArrowType FLOAT64 =
            new ArrowType.FloatingPoint(FloatingPointPrecision.DOUBLE);
    public static final ArrowType BOOL = new ArrowType.Bool();
    public static final ArrowType UTF8 = new ArrowType.Utf8();
    public static final ArrowType BINARY = new ArrowType.LargeBinary();
    public static final ArrowType NULL = new ArrowType.Null();

    private ArrowFields() {}

    public static Field field(
            String name, boolean nullable, ArrowType type, List<Field> children) {
        return new Field(name, new FieldType(nullable, type, null), children);
    }

    public static Field field(
            String name,
            boolean nullable,
            ArrowType type,
            List<Field> children,
            Map<String, String> metadata) {
        return new Field(name, new FieldType(nullable, type, null, metadata), children);
    }

    /** An unnamed list item field, as Rerun writes them. */
    public static Field itemField(ArrowType type, List<Field> children) {
        return field("item", false, type, children);
    }

    /** The canonical outer list field of a component column. */
    public static Field componentList(ComponentDescriptor descriptor, Field itemType) {
        Field item = new Field(
                "item",
                new FieldType(true, itemType.getType(), null, itemType.getMetadata()),
                itemType.getChildren());
        java.util.Map<String, String> metadata = new java.util.HashMap<>();
        metadata.put(KIND, KIND_DATA);
        metadata.put(COMPONENT, descriptor.component());
        if (descriptor.archetype() != null) {
            metadata.put(ARCHETYPE, descriptor.archetype());
        }
        metadata.put(COMPONENT_TYPE, descriptor.componentType());
        return field(descriptor.component(), true, new ArrowType.List(), List.of(item), metadata);
    }

    /** The RowId column: a 16-byte TUID with Rerun's extension metadata. */
    public static Field rowIdField(boolean sorted) {
        java.util.Map<String, String> metadata = new java.util.HashMap<>();
        metadata.put(KIND, KIND_CONTROL);
        metadata.put(ARROW_EXTENSION_NAME, TUID_EXTENSION);
        metadata.put(ARROW_EXTENSION_METADATA, "{\"namespace\":\"row\"}");
        if (sorted) {
            metadata.put(IS_SORTED, "true");
        }
        return field(ROW_ID_COLUMN, false, new ArrowType.FixedSizeBinary(16), List.of(), metadata);
    }

    /** A time column for one timeline. */
    public static Field indexField(Timeline timeline, boolean sorted) {
        java.util.Map<String, String> metadata = new java.util.HashMap<>();
        metadata.put(KIND, KIND_INDEX);
        metadata.put(INDEX_NAME, timeline.name());
        if (sorted) {
            metadata.put(IS_SORTED, "true");
        }
        return field(timeline.name(), true, timeline.type().arrowType(), List.of(), metadata);
    }

    /** A dense union Arrow type with type ids {@code 0..n}. */
    public static ArrowType denseUnion(int variants) {
        int[] typeIds = new int[variants];
        for (int i = 0; i < variants; i++) {
            typeIds[i] = i;
        }
        return new ArrowType.Union(UnionMode.Dense, typeIds);
    }

    static Map<String, String> metadataOf(Field field) {
        return Objects.requireNonNullElse(field.getMetadata(), Map.of());
    }
}
