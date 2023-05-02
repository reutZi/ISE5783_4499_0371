package renderer;
import primitives.*;
import static primitives.Util.*;


public class Camera {

    private Point p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double height;
    private double width;
    private double distance;

    public Point getP0() {
        return p0;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getHeight() { return height;}

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }

    public Camera(Point p, Vector vTo, Vector vUp) {
        this.p0 = p;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        if(!isZero(vTo.dotProduct(vUp)))
            throw new IllegalArgumentException("The vectors arnt orthogonal");

        vRight = vTo.crossProduct(vUp).normalize();
    }

    public Camera setVPSize(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }

    public Camera setVPDistance(double distance){
        this.distance = distance;
        return this;
    }

    public Ray constructRay(int nX, int nY, int j, int i){

        Point pC = p0.add(vTo.scale(distance));

        double rY = height / (double)nY;
        double rX = width / (double)nX;

        double yI = -(i -(nY - 1)/2.0) * rY;
        double xJ = (j - (nX - 1)/2.0) * rX;

        Point pIJ = pC;

        if(!isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));
        if(!isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));

        Vector dirRay = pIJ.subtract(p0);

        return new Ray(p0, dirRay);
    }
}
