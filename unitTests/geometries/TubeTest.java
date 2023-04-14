package geometries;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import geometries.Tube;
import primitives.*;


class TubeTest {

    @Test
    void getNormal() {

        //bva
        Point p0 = new Point(0,0,0);
        Tube tube = new Tube(1, new Ray(p0, new Vector(0,0,1)));
        Point p = new Point(0,1,0);
        assertEquals(tube.getNormal(p), new Vector(0,1,0), "tube's Normal illegal");

        //ep
        Point p2 = new Point(1,0,2);
        assertEquals(tube.getNormal(p2), new Vector(1,0,0), "tube's Normal illegal 2");
    }
}