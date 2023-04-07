package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 *Class that represent a cylinder (tube that not infinity)
 * therefore the cylinder is extends from tube, but cylinder has also a height.*/
public class Cylinder extends Tube{

    double height;

    /**
     * create a new Cylinder object
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
        Point p0 = axisRay.getP0();
        Vector v = axisRay.getDir();
        Vector vectorToPoint = point.subtract(p0);

        double projection = vectorToPoint.dotProduct(v);
        // check if the point is at the top or bottom of the cylinder
        if (projection <= 0 || projection >= height) {
            return v;
        }

        // calculate the normal at the side of the cylinder
        Point pointOnAxis = p0.add(v.scale(projection));
        return point.subtract(pointOnAxis).normalize();
    }
}
