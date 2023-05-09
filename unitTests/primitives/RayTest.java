package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The RayTest class contains a JUnit test method to find the closest point to a given ray among a list of points.
 */
class RayTest {

    /**
     * Tests the {@link Ray#findClosestPoint(List)} method by checking its behavior on different input data.
     * Specifically, it checks if the method returns the expected closest point when given a non-empty list of points,
     * an empty list of points, and when the closest point is in the beginning or end of the list.
     */
    @Test
    void findClosestPoint() {

        Ray ray = new Ray(new Point(1,0,0), new Vector(1,1,0));
        Point closestPoint = new Point(1.5,0.5,0);
        List list = List.of(new Point(2,1,0), closestPoint, new Point(3,2,0));
        assertEquals(ray.findClosestPoint(list), closestPoint,
                "Wrong closest point (when the point in the middle of the list)");

        // =============== Boundary Values Tests ==================
        //TC11 empty list
        List emptylist = new LinkedList();
        assertNull(ray.findClosestPoint(emptylist), "wrong in Empty list ");

        //TC12 the closest Point is in the beginning of list
        List list1 = List.of(closestPoint, new Point(2,1,0), new Point(3,2,0));
        assertEquals(ray.findClosestPoint(list1), closestPoint,
                "Wrong closest point (when the point in the beginning of the list)");

        //TC13 the closest Point is in the end of list
        List list2 = List.of(new Point(2,1,0), new Point(3,2,0),closestPoint);
        assertEquals(ray.findClosestPoint(list2), closestPoint,
                "Wrong closest point (when the point in the end of the list)");


    }
}
