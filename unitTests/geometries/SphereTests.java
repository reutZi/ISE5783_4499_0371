package geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import primitives.*;

/** The class is a JUnit test class used to test the functionality of the Sphere class. */
class SphereTests {

    /** Test method for {@link Sphere#getNormal(Point)} */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        Sphere sphere = new Sphere(1 , new Point(0,0,0));
        Point p = new Point(1,0,0);
        Vector result = sphere.getNormal(p);
        // ensure normal = (1, 0 , 0)
        assertEquals(result, new Vector(1,0,0), "Sphere's Normal illegal");
        // ensure |result| = 1 , Verify that the length of the computed normal vector is equal to 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
    }
}