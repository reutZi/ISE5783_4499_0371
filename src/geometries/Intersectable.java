package geometries;
import primitives.*;
import java.util.List;

public interface Intersectable {

    /** @returnA List<Point> containing intersection points of the Ray with geometry. */
    List<Point> findIntersections(Ray ray);
}
