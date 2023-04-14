package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * A class that represents a sphere in space, with the center and the radius of the sphere.
 * sphere is a part of Radial Geometry, so this class extends from "RadialGeometry".
 */
public class Sphere extends RadialGeometry {

    final Point center;

    /**
     * create a new Sphere object with the specified point and radius
     * @param radius the length of the radius of the sphere
     * @param center the point that represent the center of the sphere.
     * or in other words, the point that has the same distance(radius..) to all points on the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius); //call to the father constructor with the radius
        this.center = center;
    }

    /**
     * @return the point that represent the center of the sphere.
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
}
