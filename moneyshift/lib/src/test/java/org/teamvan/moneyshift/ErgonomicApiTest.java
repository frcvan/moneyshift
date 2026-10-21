package org.teamvan.moneyshift;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.teamvan.moneyshift.archetypes.AnnotationContext;
import org.teamvan.moneyshift.archetypes.Points3D;
import org.teamvan.moneyshift.archetypes.TextLog;
import org.teamvan.moneyshift.blueprint.archetypes.ViewBlueprint;
import org.teamvan.moneyshift.blueprint.components.ViewClass;
import org.teamvan.moneyshift.components.ClassId;
import org.teamvan.moneyshift.components.Color;
import org.teamvan.moneyshift.components.Position3D;
import org.teamvan.moneyshift.components.Radius;
import org.teamvan.moneyshift.components.RotationAxisAngle;
import org.teamvan.moneyshift.components.Text;
import org.teamvan.moneyshift.components.TextLogLevel;
import org.teamvan.moneyshift.encodings.AnnotationInfo;
import org.teamvan.moneyshift.encodings.ClassDescription;
import org.teamvan.moneyshift.encodings.ClassDescriptionMapElem;
import org.teamvan.moneyshift.encodings.Rgba32;
import org.teamvan.moneyshift.encodings.Utf8;
import org.teamvan.moneyshift.encodings.Vec3D;

/** The unwrapped constructors and builder overloads must be exactly the canonical values. */
class ErgonomicApiTest {

    @Test
    void unwrappedConstructorsMatchTheCanonicalOnes() {
        assertEquals(new Color(new Rgba32(0xFF00E5FFL)), new Color(0xFF00E5FFL));
        assertEquals(
                new Radius(new org.teamvan.moneyshift.encodings.Float32(0.25f)), new Radius(0.25f));
        assertEquals(new Text(new Utf8("hello")), new Text("hello"));
        assertEquals(new ViewClass(new Utf8("3D")), new ViewClass("3D"));
        assertEquals(
                new AnnotationInfo(1, new Utf8("thing"), new Rgba32(0xFF00FFFFL)),
                new AnnotationInfo(1, "thing", 0xFF00FFFFL));
        assertEquals(
                new ClassDescriptionMapElem(1, classDescription()),
                new ClassDescriptionMapElem(
                        new org.teamvan.moneyshift.encodings.ClassId(1), classDescription()));
        assertEquals(
                new RotationAxisAngle(
                        new org.teamvan.moneyshift.encodings.RotationAxisAngle(
                                Vec3D.of(0, 0, 1), new org.teamvan.moneyshift.encodings.Angle(0.5f))),
                new RotationAxisAngle(Vec3D.of(0, 0, 1), 0.5f));
    }

    @Test
    void buildersAcceptUnwrappedValues() {
        Points3D explicit = Points3D.builder()
                .withPositions(Position3D.of(1, 2, 3))
                .withColors(new Color(new Rgba32(0xFF00E5FFL)))
                .withRadii(new Radius(new org.teamvan.moneyshift.encodings.Float32(0.25f)))
                .withLabels(new Text(new Utf8("point")))
                .withClassIds(new ClassId(new org.teamvan.moneyshift.encodings.ClassId(7)))
                .build();
        Points3D unwrapped = Points3D.builder()
                .withPositions(Position3D.of(1, 2, 3))
                .withColors(0xFF00E5FFL)
                .withRadii(0.25f)
                .withLabels("point")
                .withClassIds(7)
                .build();
        assertEquals(explicit, unwrapped);
    }

    @Test
    void buildersAcceptSeveralUnwrappedValuesInOrder() {
        assertEquals(
                Points3D.builder()
                        .withPositions(Position3D.of(1, 2, 3))
                        .withRadii(
                                new Radius(new org.teamvan.moneyshift.encodings.Float32(0.25f)),
                                new Radius(new org.teamvan.moneyshift.encodings.Float32(0.5f)))
                        .build(),
                Points3D.builder()
                        .withPositions(Position3D.of(1, 2, 3))
                        .withRadii(0.25f, 0.5f)
                        .build());
    }

    @Test
    void buildersAcceptElementsOfListValuedComponents() {
        assertEquals(
                AnnotationContext.builder()
                        .withContext(new org.teamvan.moneyshift.components.AnnotationContext(
                                List.of(new ClassDescriptionMapElem(1, classDescription()))))
                        .build(),
                AnnotationContext.builder()
                        .withContext(new ClassDescriptionMapElem(1, classDescription()))
                        .build());
    }

    @Test
    void scalarFieldsAcceptUnwrappedValues() {
        assertEquals(
                TextLog.builder()
                        .withText(new Text(new Utf8("hello")))
                        .withLevel(new TextLogLevel(new Utf8("INFO")))
                        .withColor(new Color(new Rgba32(0xFF32D74BL)))
                        .build(),
                TextLog.builder()
                        .withText("hello")
                        .withLevel("INFO")
                        .withColor(0xFF32D74BL)
                        .build());
        assertEquals(
                ViewBlueprint.builder()
                        .withClassIdentifier(new ViewClass(new Utf8("3D")))
                        .build(),
                ViewBlueprint.builder().withClassIdentifier("3D").build());
    }

    private static ClassDescription classDescription() {
        return new ClassDescription(new AnnotationInfo(1, "sensor return", 0xFF00E5FFL), List.of(), List.of());
    }
}
