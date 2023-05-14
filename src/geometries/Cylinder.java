package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;

/** Class that represent a cylinder (tube that not infinity)
 * therefore the cylinder is extends from tube, but cylinder has also a height. */
public class Cylinder extends Tube{

    double height;

    /** create a new Cylinder object
     * @param radius the radius of the base of the cylinder
     * @param height the height of the cylinder.
     * @param axisRay */
    public Cylinder(double radius, Ray axisRay,double height) {

        super(radius,axisRay);
        this.height = height;
    }

    /** @return the height*/
    public double getHeight() {
        return height;
    }


    @Override
    public Vector getNormal(Point point) {
        // Get the origin point of the cylinder axis ray.
        Point p0 = axisRay.getP0();
        // Get the direction vector of the cylinder axis ray.
        Vector v = axisRay.getDir();

        // Check if the given point is the same as the axis ray's origin point.
        // If so, return the direction vector of the axis ray.
        if (point.equals(p0))
            return v;

        // Calculate a vector u from the cylinder's axis ray origin to the given point.
        Vector u = point.subtract(p0);

        // Calculate the projection of u onto v.
        double t = alignZero(u.dotProduct(v));

        // Check if the point is on the top or bottom base of the cylinder.
        if (t == 0 || isZero(height - t))
            return v;

        // Calculate the intersection point o between the cylinder axis ray and the plane that passes through
        // the given point and is perpendicular to the cylinder's axis ray.
        Point o = p0.add(v.scale(t));

        // Calculate the normal vector from the given point to the intersection point o.
        return point.subtract(o).normalize();
    }
}
