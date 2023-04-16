package geometries;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import primitives.*;

/** The class is a JUnit test class used to test the functionality of the Tube class.*/
class TubeTests {

    /**
     * The method tests the functionality of the Tube getNormal(Point) method in the Tube class.
     *
     * This method performs two tests:
     * 1. Boundary value analysis test with a point lying on the tube's axis.
     * 2. Equivalence partitioning test with a point not lying on the tube's axis.
     *
     * If the calculated normal vector does not match the expected normal vector, the test fails with an error message.
     */
    @Test

    public void testGetNormal() {

        // Boundary value analysis test when (p - p0) is a vector that is orthogonal to the axisRay
        Point p0 = new Point(0,0,0);
        Tube tube = new Tube(1, new Ray(p0, new Vector(0,0,1)));
        Point p = new Point(0,1,0);
        assertEquals(tube.getNormal(p), new Vector(0,1,0), "Tube's normal vector is incorrect for extreme case when (p - p0) is orthogonal to axisRay");

        // Equivalence partitioning test with all the points on the tube
        Point p2 = new Point(1,0,2);
        assertEquals(tube.getNormal(p2), new Vector(1,0,0), "Tube's normal vector is incorrect for all of the points");
    }
}