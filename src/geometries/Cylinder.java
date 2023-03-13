package geometries;

import primitives.Point;
import primitives.Vector;

public class Cylinder extends RadialGeometry{

    double height;

    public Cylinder(double radius, double height) {
        super(radius);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point point) {
        return null; //Temporarily return null
    }
}
