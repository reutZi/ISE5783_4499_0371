package renderer;

import static org.junit.jupiter.api.Assertions.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;

/** The CameraIntegrationTest class contains JUnit tests
 *  to check that cameras intersect with various geometries in the scene correctly. */
public class CameraIntegrationTest {

    /** The ZERO_POINT constant represents the point (0,0,0). */
    static final Point ZERO_POINT = new Point(0, 0, 0);


    int intersectionNumber(Camera camera, Intersectable intersectable, int nX, int nY){

        int intersectionsNum = 0;

        for (int i = 0 ; i < nX ; i++) {
            for (int j = 0; j < nY; j++) {

                List<Point> l = intersectable.findIntersections(camera.constructRay(nX, nY, j, i));

                if (l != null)
                    intersectionsNum += l.size();
            }
        }
        return intersectionsNum;
    }
    @Test
    void testIntegrationTriangle(){
        Camera cam = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3, 3)
                .setVPDistance(1);

        Triangle triangle = new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -1, -2));
        int intersectionsNum = intersectionNumber(cam, triangle, 3,3);
        assertEquals(1,intersectionsNum, "");
    }

    @Test
    void testIntegrationSphere(){

        Camera cam1 = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3,3)
                .setVPDistance(1);

        Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3,3)
                .setVPDistance(1);

        int intersectionsNum;

        Sphere sphere = new Sphere(1,new Point(0,0,-3));
        intersectionsNum = intersectionNumber(cam1, sphere,3,3);
        assertEquals(2, intersectionsNum, "");

        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        intersectionsNum = intersectionNumber(cam2, sphere,3,3);
        assertEquals(18, intersectionsNum, "");

        sphere = new Sphere(2, new Point(0,0,-2));
        intersectionsNum = intersectionNumber(cam2, sphere,3,3);
        assertEquals(10, intersectionsNum, "");

        sphere = new Sphere(4,new Point(0,0,-0.5));
        intersectionsNum = intersectionNumber(cam2, sphere,3,3);
        assertEquals(9, intersectionsNum, "");

        sphere = new Sphere(0.5,new Point(0,0,1));
        intersectionsNum = intersectionNumber(cam1, sphere,3,3);
        assertEquals(0, intersectionsNum, "");
    }

    @Test
    void testIntegrationPlane(){
        Camera cam = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3, 3)
                .setVPDistance(1);

        int intersectionsNum;

        Plane plane = new Plane(new Point(0,0,-2), new Vector(0,0,1));
        intersectionsNum = intersectionNumber(cam, plane,3,3);
        assertEquals(9, intersectionsNum, "");

        plane = new Plane(new Point(1.5, 1.5, 0), new Vector(-1,0,3));
        intersectionsNum = intersectionNumber(cam, plane,3,3);
        assertEquals(9, intersectionsNum, "");

        plane = new Plane(new Point(1.5, 1.5, 0), new Vector(-1,0,1));
        intersectionsNum = intersectionNumber(cam, plane,3,3);
        assertEquals(6, intersectionsNum, "");
    }
}
