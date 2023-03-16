package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * A class that represents a Tube in space, with the ray and the radius of the tube.
 * the tube is infinity.
 * tube is a part of Radial Geometry, so this class extends from "RadialGeometry".
 */
public class Tube extends RadialGeometry {
    Ray axisRay;

    /**
     * crate a new Tube object
     * @param radius the radius of the base of the tube.
     * @param axisRay
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * @return the ray of the tube.
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;  //Temporarily return null
    }
}
