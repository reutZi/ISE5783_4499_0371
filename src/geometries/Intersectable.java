package geometries;
import primitives.*;
import java.util.List;

/** This interface represents an intersectable geometry object. */
public interface Intersectable {

    /** Returns a list of intersection points between the given ray and this geometry.
     * @param ray the ray to intersect with this geometry. */
    List<Point> findIntersections(Ray ray);
}
