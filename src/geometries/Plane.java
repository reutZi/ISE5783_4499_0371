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

    }


}
