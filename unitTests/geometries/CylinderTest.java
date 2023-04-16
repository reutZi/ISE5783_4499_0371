package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/** The class is a JUnit test class used to test the functionality of the Tube class */
class CylinderTest {

    @Test
    void testGetNormal() {
        // create a cylinder with height 4 and radius 2, centered at the origin with axis in the z-direction
        Cylinder cylinder = new Cylinder(2, new Ray(new Point(0,0,-2), new Vector(0,0,1)), 4);

        // test the normal at a point on the surface of the cylinder
        Point point = new Point(1, 1, 0);
        Vector expectedNormal = new Vector(1/Math.sqrt(2), 1/Math.sqrt(2), 0);
        Vector actualNormal = cylinder.getNormal(point);
        assertEquals(expectedNormal, actualNormal);

        // test the normal at a point on the top base of the cylinder
        point = new Point(0, 0, 2);
        expectedNormal = new Vector(0, 0, 1);
        actualNormal = cylinder.getNormal(point);
        assertEquals(expectedNormal, actualNormal);

        // test the normal at a point on the bottom base of the cylinder
        point = new Point(0, 0, -2);
        expectedNormal = new Vector(0, 0, -1);
        actualNormal = cylinder.getNormal(point);
        assertEquals(expectedNormal, actualNormal);

        // test the normal at a point on the edge of the top base of the cylinder
        point = new Point(2, 0, 2);
        expectedNormal = new Vector(1, 0, 0).normalize();
        Vector expectedNormal2 = new Vector(0, 1, 0).normalize();
        actualNormal = cylinder.getNormal(point);
        assertTrue(actualNormal.equals(expectedNormal) || actualNormal.equals(expectedNormal2));

        // test the normal at a point in the center of the top base of the cylinder
        point = new Point(0, 0, 2);
        expectedNormal = new Vector(0, 0, 1);
        actualNormal = cylinder.getNormal(point);
        assertEquals(expectedNormal, actualNormal);

        // test the normal at a point in the center of the bottom base of the cylinder
        point = new Point(0, 0, -2);
        expectedNormal = new Vector(0, 0, -1);
        actualNormal = cylinder.getNormal(point);
        assertEquals(expectedNormal, actualNormal);

        // test the normal at a point on the edge of the bottom base of the cylinder
        point = new Point(2, 0, -2);
        expectedNormal = new Vector(1, 0, 0).normalize();
        expectedNormal2 = new Vector(0, 1, 0).normalize();
        actualNormal = cylinder.getNormal(point);
        assertTrue(actualNormal.equals(expectedNormal) || actualNormal.equals(expectedNormal2));
    }
}