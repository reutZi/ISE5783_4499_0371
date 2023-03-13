package geometries;

import primitives.*;

public class Plane implements Geometry{

    Point q0;
    Vector normal;

    public Plane(Point q0, Vector vector) {
        this.q0 = q0;
        this.normal = vector.normalize();
      //  this.normal = vector;
    }

    public  Plane(Point p1, Point p2, Point p3){
        this.q0=p1; //save one random point to be point of reference
        //!!!!we need to calculate the normal with the 3 points!!!! now we save null in the normal
        this.normal=null;

    }

    public Point getQ0() {
        return q0;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }
    public Vector getNormal() {
        return getNormal(q0);
    }
}
