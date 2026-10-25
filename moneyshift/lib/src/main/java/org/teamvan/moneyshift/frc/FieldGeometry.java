package org.teamvan.moneyshift.frc;

import java.util.List;
import java.util.Objects;

/**
 * The geometry the bridge draws for a field, in the WPILib field frame.
 *
 * <p>The origin is the blue-alliance right corner of the carpet, {@code +x} points toward the red
 * alliance, {@code +y} toward the left of the blue alliance, and distances are meters. Lines and
 * tags are 2D except for a tag's {@code z}, which lifts its plate off the carpet. The field's
 * dimensions are fixed at {@value #LENGTH_METERS} by {@value #WIDTH_METERS} meters.
 *
 * <p>{@link #standard()} is season-neutral: the carpet, its perimeter and the center line. Pass a
 * caller-built instance to {@link FirstMetadata.Builder#field(FieldGeometry)} to describe a
 * specific season; the SDK bakes in no game.
 */
public record FieldGeometry(boolean carpet, List<Line> lines, List<Tag> tags) {

    /** Length of an FRC carpet, in meters. */
    public static final double LENGTH_METERS = 16.54175;

    /** Width of an FRC carpet, in meters. */
    public static final double WIDTH_METERS = 8.069326;

    public FieldGeometry {
        lines = List.copyOf(Objects.requireNonNull(lines, "lines"));
        tags = List.copyOf(Objects.requireNonNull(tags, "tags"));
    }

    /**
     * The standard carpet, its perimeter and the center line, with no tags and no game elements.
     *
     * <p>Structure every FRC field shares, so it is safe to ship. Season-specific geometry (reef,
     * speaker, tags, …) belongs in a caller-built {@code FieldGeometry}.
     */
    public static FieldGeometry standard() {
        return new FieldGeometry(
                true,
                List.of(
                        Line.closed(
                                new Point(0, 0),
                                new Point(LENGTH_METERS, 0),
                                new Point(LENGTH_METERS, WIDTH_METERS),
                                new Point(0, WIDTH_METERS)),
                        Line.open(
                                new Point(LENGTH_METERS / 2, 0),
                                new Point(LENGTH_METERS / 2, WIDTH_METERS))),
                List.of());
    }

    /** A point on the carpet, in meters. */
    public record Point(double x, double y) {
        public Point {
            requireFinite(x, "x");
            requireFinite(y, "y");
        }
    }

    /** One run of points; {@code closed} joins the last point back to the first. */
    public record Line(List<Point> points, boolean closed) {
        public Line {
            points = List.copyOf(Objects.requireNonNull(points, "points"));
            if (points.size() < 2) {
                throw new IllegalArgumentException(
                        "a line needs at least two points, got " + points.size());
            }
        }

        /** An open run of points. */
        public static Line open(Point... points) {
            return new Line(List.of(points), false);
        }

        /** A closed loop through the given points. */
        public static Line closed(Point... points) {
            return new Line(List.of(points), true);
        }
    }

    /**
     * One AprilTag: its id, its plate center, its heading around {@code +z}, and the plate's side
     * length.
     *
     * <p>Tags are vertical plates, so a single yaw describes them; {@code z} is the center height
     * above the carpet.
     */
    public record Tag(int id, double x, double y, double z, double yawRadians, double sizeMeters) {
        public Tag {
            if (id < 0) {
                throw new IllegalArgumentException("tag ids are non-negative, got " + id);
            }
            requireFinite(x, "x");
            requireFinite(y, "y");
            requireFinite(z, "z");
            requireFinite(yawRadians, "yawRadians");
            requirePositive(sizeMeters, "sizeMeters");
        }
    }

    private static void requirePositive(double value, String name) {
        requireFinite(value, name);
        if (value <= 0.0) {
            throw new IllegalArgumentException(name + " must be positive, got " + value);
        }
    }

    private static void requireFinite(double value, String name) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be finite, got " + value);
        }
    }
}
