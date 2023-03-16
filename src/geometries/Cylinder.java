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
        return null; //Temporarily return null
    }
}
