package renderer;
import static org.junit.jupiter.api.Assertions.*;

import geometries.Triangle;
import org.junit.jupiter.api.Test;

        import primitives.*;
        import renderer.Camera;

import java.util.LinkedList;
import java.util.List;

public class CameraIntegrationTest {
    static final Point ZERO_POINT = new Point(0, 0, 0);

    @Test
    void testIntegrationTriangle(){
        Camera cam = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0));
        cam.setVPSize(3, 3);
        cam.setVPDistance(1);
        Triangle tr = new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -1, -2));

        List<Point> allpoints = null;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                var intersections = tr.findIntersections(cam.constructRay(3, 3, j, i));
                if (intersections != null) {
                    if (allpoints == null) {
                        allpoints = new LinkedList<>();
                    }
                    allpoints.addAll(intersections);
                }
               }
        }
         // TC01: Small triangle 1 point
        assertEquals(1,allpoints.size());
    }

    @Test
    void testIntegrationSphere(){

    }

    @Test
    void testIntegrationPlane(){

    }
}
