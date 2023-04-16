package geometries;

import primitives.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/** The class is a JUnit test class used to test the functionality of the Plane class */
class PlaneTests {

    /** Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point,primitives.Point)}. */
    @Test
    void testConstructor() {

        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct plane
        try {
            new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }

        // =============== Boundary Values Tests ==================

        // TC10 - Test for two equal vertices
        Point p = new Point(1,0,0);
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(p, p, new Point(0,1,0)),
                "Constructed a plane with two equal vertices");

        // TC11 - Constructed a plane with three collinear vertices
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(new Point(1,0,0), new Point(2,0,0),new Point(3,0,0)),
                "Constructed a plane with collinear vertices");
    }


    /** Test method for {@link geometries.Plane#getNormal(primitives.Point)} */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // Define three non-collinear points to create a plane
        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(0, 1, 0);
        Point p3 = new Point(1, 0, 0);

        Plane plane = new Plane(p1, p2, p3);

        // generate the test result
        Vector result = plane.getNormal(p1);
        // ensure |result| = 1 , Verify that the length of the computed normal vector is equal to 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");

        // ensure the result is orthogonal to the plane
        assertTrue(isZero(result.dotProduct(p1.subtract(p2))),
                "Plane's normal is not orthogonal to the plane");

    }
}