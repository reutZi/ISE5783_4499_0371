package geometries;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import primitives.Point;
import primitives.Vector;

/**
 * The class is a JUnit test class used to test the functionality of the Sphere class.
 */
class SphereTest {

    /**
     * Tests the {Sphere getNormal(Point)} method by checking if the normal vector of a sphere
     * at a given point is calculated correctly.
     * The test involves creating a sphere centered at the origin with radius 1,
     * and a point on the sphere's surface with x-coordinate 1. The expected normal vector is (1,0,0).
     * The test passes if the actual normal vector is equal to the expected one.
     * @throws AssertionError if the normal vector of the sphere at the given point is not equal
     * to the expected normal vector.
     */
    @Test
    void getNormal() {
        Sphere sphere = new Sphere(1 , new Point(0,0,0));
        Point p = new Point(1,0,0);
        // ensure normal = (1, 0 , 0)
        assertEquals(sphere.getNormal(p), new Vector(1,0,0), "Sphere's Normal illegal");
    }
}