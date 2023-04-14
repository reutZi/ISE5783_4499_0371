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
 *
 */
class SphereTest {

    @Test
    void getNormal() {
        Sphere sphere = new Sphere(1 , new Point(0,0,0));
        Point p = new Point(1,0,0);
        // ensure normal = (1, 0 , 0)
        assertEquals(sphere.getNormal(p), new Vector(1,0,0), "Sphere's Normal illegal");
    }
}