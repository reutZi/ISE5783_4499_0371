package geometries;

import primitives.*;

/**
 * Plane ia A class that implements the "Geometry" interface.
 * This class represents a plane by a one point on the plane and a vector
 * that orthogonal to the plane (the normal)
 */
public class Plane implements Geometry{

    final Point q0;
    final Vector normal;

    /**
     *  Creates a Plane object with the specified point and vector.
     * @param q0 - point of reference on the plane
     * @param vector - the normal. vector that orthogonal to the plane
     */
    public Plane(Point q0, Vector vector) {
        this.q0 = q0;
        this.normal = vector.normalize();

    }

    /**
     * Creates a Plane object with the specified 3 points on the plane.
     * with the help of this 3 points we will find the normal vector
     * @param p1 - point on the plane
     * @param p2- point on the plane
     * @param p3- point on the plane
     */
    public  Plane(Point p1, Point p2, Point p3){
        this.q0=p1; //save one random point to be point of reference
        Vector u=p1.subtract(p2);
        Vector v=p1.subtract(p3);
        Vector n=u.crossProduct(v);
        this.normal=n.normalize();

    }
    /**
     * function to get the point
     * @return the plane's point of reference.
     */
    public Point getQ0() {
        return q0;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * function to get normal without specified point.
     * because in a plane the normal at all points is the same.
     * @return call to the other func to get normal, with one point from our plane
     * (p0 in our case is very comfortable for us).
     */
    public Vector getNormal() {
        return getNormal(q0);
    }
}
