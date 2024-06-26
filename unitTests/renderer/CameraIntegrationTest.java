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


    /** Computes the number of intersections between a camera and an intersectable object
     * in a view plane with the given number of columns and rows.
     * @param camera the camera to test.
     * @param intersectable the object to intersect with.
     * @param nX the number of columns in the view plane.
     * @param nY the number of rows in the view plane.
     * @return the total number of intersections found. */
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

    /** Tests that a triangle intersects with a camera correctly. */
    @Test
    void testIntegrationTriangle(){
        Camera cam = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3, 3)
                .setVPDistance(1);

        Triangle triangle = new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -1, -2));
        int intersectionsNum = intersectionNumber(cam, triangle, 3,3);
        assertEquals(1,intersectionsNum, "wrong in the triangle's test");
    }

    /** Tests that a sphere intersects with a camera correctly. */
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
        assertEquals(2, intersectionsNum, "wrong in the case of sphere with 2 intersections");

        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        intersectionsNum = intersectionNumber(cam2, sphere,3,3);
        assertEquals(18, intersectionsNum, "wrong in the case of sphere with 18 intersections");

        sphere = new Sphere(2, new Point(0,0,-2));
        intersectionsNum = intersectionNumber(cam2, sphere,3,3);
        assertEquals(10, intersectionsNum, "wrong in the case of sphere with 10 intersections");

        sphere = new Sphere(4,new Point(0,0,-0.5));
        intersectionsNum = intersectionNumber(cam2, sphere,3,3);
        assertEquals(9, intersectionsNum, "wrong in the case of sphere with 9 intersections");

        sphere = new Sphere(0.5,new Point(0,0,1));
        intersectionsNum = intersectionNumber(cam1, sphere,3,3);
        assertEquals(0, intersectionsNum, "wrong in the case of sphere with no intersections");
    }


    /** Tests that a plane intersects with a camera correctly. */
    @Test
    void testIntegrationPlane(){
        Camera cam = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(3, 3)
                .setVPDistance(1);

        int intersectionsNum;

        Plane plane = new Plane(new Point(0,0,-2), new Vector(0,0,1));
        intersectionsNum = intersectionNumber(cam, plane,3,3);
        assertEquals(9, intersectionsNum, "wrong in the case1 of plane with 9 intersections");

        plane = new Plane(new Point(1.5, 1.5, 0), new Vector(-1,0,3));
        intersectionsNum = intersectionNumber(cam, plane,3,3);
        assertEquals(9, intersectionsNum, "wrong in the case2 of plane with 9 intersections");

        plane = new Plane(new Point(1.5, 1.5, 0), new Vector(-1,0,1));
        intersectionsNum = intersectionNumber(cam, plane,3,3);
        assertEquals(6, intersectionsNum, "wrong in the case of plane with 6 intersections");
    }
}
