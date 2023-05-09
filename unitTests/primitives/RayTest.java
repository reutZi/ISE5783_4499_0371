package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void findClosestPoint() {

        Ray ray = new Ray(new Point(1,0,0), new Vector(1,1,0));
        Point closestPoint = new Point(1.5,0.5,0);
        List list = List.of(new Point(2,1,0), closestPoint, new Point(3,2,0));
        assertEquals(ray.findClosestPoint(list), closestPoint, "Wrong closest point");

        list = new LinkedList();
        assertNull(list, "Empty list ");

    }
}