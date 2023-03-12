package geometries;

import primitives.*;

/**
 * The Geometry interface represents a geometric shape.
 */
public interface Geometry {

    /**
     * Returns the normal vector of the specified point on the surface of this geometry.
     * @param point a Point object representing a point on the surface of this geometry
     * @return the normal vector of the specified point on the surface of this geometry
     */
    public Vector getNormal(Point point);
}
