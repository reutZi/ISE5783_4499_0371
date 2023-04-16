package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/** The class is a JUnit test class used to test the functionality of the Triangle class */
class TriangleTest {

    /** Test method for {@link geometries.Triangle#getNormal(primitives.Point)} */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // Define three non-collinear points to create a triangle
        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(0, 1, 0);
        Point p3 = new Point(1, 0, 0);

        Triangle triangle = new Triangle(p1, p2, p3);

        // generate the test result
        Vector result = triangle.getNormal(p1);
        // ensure |result| = 1 , Verify that the length of the computed normal vector is equal to 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");

        // ensure the result is orthogonal to all the edges
        assertTrue(isZero(result.dotProduct(p1.subtract(p2))),
                "Triangle's normal is not orthogonal to one of the edges");

        assertTrue(isZero(result.dotProduct(p1.subtract(p3))),
                "Triangle's normal is not orthogonal to one of the edges");

        assertTrue(isZero(result.dotProduct(p2.subtract(p3))),
                "Triangle's normal is not orthogonal to one of the edges");

    }
}