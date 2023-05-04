package renderer;

import primitives.*;
import static primitives.Util.*;
import java.util.MissingResourceException;

/** A camera object used to construct rays for rendering an image. */
public class Camera {

    private Point p0; // Camera position
    private Vector vTo; // Direction vector of camera
    private Vector vUp; // Up direction vector of camera
    private Vector vRight; // Right direction vector of camera
    private double height; // Height of view plane
    private double width; // Width of view plane
    private double distance; // Distance of view plane from camera
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

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

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /** Constructs a new Ray object that represents a ray of light in a three-dimensional space.
     * @param nX the number of columns in the camera's view frustum.
     * @param nY the number of rows in the camera's view frustum.
     * @param j the column index of the pixel to construct the ray for.
     * @param i the row index of the pixel to construct the ray for.
     * @return a new Ray object that represents a ray of light in a three-dimensional space. */
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

    public void renderImage(){

        if(height == 0)
            throw new MissingResourceException("The field is not initialized", "Camera", "height");
        if(width == 0)
            throw new MissingResourceException("The field is not initialized", "Camera", "width");
        if(distance == 0)
            throw new MissingResourceException("The field is not initialized", "Camera", "distance");
        if(imageWriter == null)
            throw new MissingResourceException("The field is not initialized", "Camera", "imageWriter");
        if(rayTracer == null)
            throw new MissingResourceException("The field is not initialized", "Camera", "rayTracerBase");

        Ray ray;
        Color color;
        int nY = imageWriter.getNy();
        int nX = imageWriter.getNx();

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {

                ray = constructRay(nX, nY, j, i);
                color = rayTracer.traceRay(ray);
                imageWriter.writePixel(j, i, color);
            }
        }
    }

    public void printGrid(int interval, Color color){

        if (this.imageWriter == null)
            throw new MissingResourceException("The field is not initialized", "Camera", "imageWriter");

        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {

                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(j, i, color);
            }
        }
    }

    public void writeToImage() {

        if (this.imageWriter == null)
            throw new MissingResourceException("The field is not initialized", "Camera", "imageWriter");

        this.imageWriter.writeToImage();
    }

}

