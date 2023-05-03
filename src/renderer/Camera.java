package renderer;

import primitives.*;
import static primitives.Util.*;

/** A camera object used to construct rays for rendering an image. */
public class Camera {

    private Point p0; // Camera position
    private Vector vTo; // Direction vector of camera
    private Vector vUp; // Up direction vector of camera
    private Vector vRight; // Right direction vector of camera
    private double height; // Height of view plane
    private double width; // Width of view plane
    private double distance; // Distance of view plane from camera

    /** Constructs a new camera object.
     * @param p    The camera position.
     * @param vTo  The direction vector of the camera.
     * @param vUp  The up direction vector of the camera.
     * @throws IllegalArgumentException if the direction and up vectors are not orthogonal. */
    public Camera(Point p, Vector vTo, Vector vUp) throws IllegalArgumentException {
        this.p0 = p;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        if(!isZero(vTo.dotProduct(vUp)))
            throw new IllegalArgumentException("The vectors aren't orthogonal");

        vRight = vTo.crossProduct(vUp).normalize();
    }

    /** @return The camera position. */
    public Point getP0() {
        return p0;
    }

    /** @return The direction vector of the camera. */
    public Vector getvTo() {
        return vTo;
    }

    /** @return The up direction vector of the camera. */
    public Vector getvUp() {
        return vUp;
    }

    /** @return The right direction vector of the camera. */
    public Vector getvRight() {
        return vRight;
    }

    /** @return The height of the view plane. */
    public double getHeight() {
        return height;
    }

    /** @return The width of the view plane. */
    public double getWidth() {
        return width;
    }

    /** @return The distance of the view plane from the camera. */
    public double getDistance() {
        return distance;
    }

    /** Sets the distance of the view plane from the camera.
     * @param distance The distance of the view plane from the camera.
     * @return The camera object. */
    public Camera setVPDistance(double distance){
        this.distance = distance;
        return this;
    }

    /** Sets the size of the view plane.
     * @param width  The width of the view plane.
     * @param height The height of the view plane.
     * @return The camera object. */
    public Camera setVPSize(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }

    public Ray constructRay(int nX, int nY, int j, int i){
        Point pC = p0.add(vTo.scale(distance));

        double rY = height / (double)nY;
        double rX = width / (double)nX;

        double yI = -(i -(nY - 1)/2.0) * rY;
        double xJ = (j - (nX - 1)/2.0) * rX;

        Point pIJ = pC;

        if(xJ != 0)
            pIJ = pIJ.add(vRight.scale(xJ));
        if(yI != 0)
            pIJ = pIJ.add(vUp.scale(yI));

        Vector dirRay = pIJ.subtract(p0);

        return new Ray(p0, dirRay);
    }
}

