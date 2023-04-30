package geometries;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import java.util.List;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;

/** The class is a JUnit test class used to test the functionality of the Tube class.*/
class TubeTests {


    /** Test method for {@link geometries.Tube#getNormal(primitives.Point)} */
    @Test
    public void testGetNormal() {

        // Boundary value analysis test when (p - p0) is a vector that is orthogonal to the axisRay
        Point p0 = new Point(0,0,0);
        Tube tube = new Tube(1, new Ray(p0, new Vector(0,0,1)));
        Point p = new Point(0,1,0);
        Vector result = tube.getNormal(p);
        assertEquals(result , new Vector(0,1,0), "Tube's normal vector is incorrect for extreme case when (p - p0) is orthogonal to axisRay");

        // ensure |result| = 1 , Verify that the length of the computed normal vector is equal to 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");

        // Equivalence partitioning test with all the points on the tube
        Point p2 = new Point(1,0,2);
        Vector result2 = tube.getNormal(p2);
        assertEquals(result2, new Vector(1,0,0), "Tube's normal vector is incorrect for all of the points");

        // ensure |result| = 1 , Verify that the length of the computed normal vector is equal to 1
        assertEquals(1, result2.length(), 0.00000001, "Triangle's normal is not a unit vector");
    }
}