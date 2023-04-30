package geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;

/** Testing Polygons
 * @author Dan */
public class PolygonTests {

    /** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
        Polygon pol = new Polygon(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))),
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    /** Test method for {@link geometries.Polygon#findIntersections(Ray)}. */
    @Test
    void testFindIntersections() {

        //square
        Polygon poly = new Polygon(
                new Point(1d, 4d, 0),
                new Point(4d, 4d, 0),
                new Point(4d, 0, 0),
                new Point(1d, 0, 0)
        );

        Point p = new Point(0, 0, 4d);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Inside triangle
        List<Point> result = poly.findIntersections(new Ray(p, new Vector(2d, 2d, -4d)));
        assertEquals(List.of(new Point(2d, 2d, 0)), result, "Inside polygon");

        // TC02: Outside against edge
        result = poly.findIntersections(new Ray(p, new Vector(5d, 2d, -4d)));
        assertNull(result, "Outside against edge");

        // TC03: Outside against vertex
        result = poly.findIntersections(new Ray(p, new Vector(7d, 7d, -4d)));
        assertNull(result, "Outside against vertex");

        // =============== Boundary Values Tests ==================
        // TC11: On edge
        result = poly.findIntersections(new Ray(p, new Vector(4d, 2, -4d)));
        assertNull(result, "On edge");

        // TC12: In vertex
        result = poly.findIntersections(new Ray(p, new Vector(4d, 4d, -4d)));
        assertNull(result, "In vertex");

        // TC13: On edge's continuation
        result = poly.findIntersections(new Ray(p, new Vector(7d, 8d, -4d)));
        assertNull(result, "On edge's continuation");
    }
}
