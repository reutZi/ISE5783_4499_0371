package renderer;

import primitives.*;
import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;

/** A camera object used to construct rays for rendering an image. */
public class Camera {

    // Camera position
    private Point p0;
    // Direction vector of camera
    private Vector vTo;
    // Up direction vector of camera
    private Vector vUp;
    // Right direction vector of camera
    private Vector vRight;
    // Height of view plane
    private double height;
    // Width of view plane
    private double width;
    // Distance of view plane from camera
    private double distance;
    // The object used for writing the rendered image.
    private ImageWriter imageWriter;
    // The object used for tracing rays and computing colors.
    private RayTracerBase rayTracer;

    private final int nSS = 4;
    private int threadsCount = 3;

    /** Constructs a new camera object.
     * @param p    The camera position.
     * @param vTo  The direction vector of the camera.
     * @param vUp  The up direction vector of the camera.
     * @throws IllegalArgumentException if the direction and up vectors are not orthogonal. */
    public Camera(Point p, Vector vTo, Vector vUp) throws IllegalArgumentException {

        if(!isZero(vTo.dotProduct(vUp)))
            throw new IllegalArgumentException("The vectors aren't orthogonal");

        this.p0 = p;

        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        vRight = this.vTo.crossProduct(this.vUp).normalize();
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
     * @param Nx the number of columns in the camera's view frustum.
     * @param Ny the number of rows in the camera's view frustum.
     * @param j the column index of the pixel to construct the ray for.
     * @param i the row index of the pixel to construct the ray for.
     * @return a new Ray object that represents a ray of light in a three-dimensional space. */
    public Ray constructRay(int Nx, int Ny, int j, int i){

        Point Pc = p0.add(vTo.scale(distance));

        double Ry = height / (double)Ny;
        double Rx = width /  (double)Nx;

        double Yi = -(i -(Ny - 1)/2.0) * Ry;
        double Xj = (j - (Nx - 1)/2.0) * Rx;

        Point Pij = Pc;

        if(!isZero(Xj))
            Pij = Pij.add(vRight.scale(Xj));

        if(!isZero(Yi))
            Pij = Pij.add(vUp.scale(Yi));

        Vector dirRay = Pij.subtract(p0);

        return new Ray(p0, dirRay);
    }

    /**
     * Renders an image by tracing rays for each pixel of the view plane.
     * @throws MissingResourceException if any of the fields are not initialized.
     */
    public Camera renderImage(){
        // Check if all the required fields are initialized
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

        // Loop through each pixel in the image and trace a ray for it
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {

                // Construct a ray for the current pixel
                ray = constructRay(nX, nY, j, i);
                // Trace the ray using the rayTracer object to get the color of the pixel
                color = rayTracer.traceRay(ray);
                // Write the color to the image using the imageWriter object
                imageWriter.writePixel(j, i, color);
            }
        }
        return this;
    }

    public Camera renderImageSuperSampling() {
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(j, i, castBeamSuperSampling(j, i));
            }
        }
        return this;
    }

    private Color castRay(int j, int i) {
        Ray ray = constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
        return rayTracer.traceRay(ray);
    }

    public Camera renderImageMultiThreading() {
        Pixel.initialize(imageWriter.getNx(), imageWriter.getNy(), 1);
        while (threadsCount-- > 0) {    //In our case up to 3
            new Thread(() -> {
                for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                    imageWriter.writePixel(pixel.col, pixel.row, castRay(pixel.col, pixel.row));
            }).start();
        }
        Pixel.waitToFinish();
        return this;
    }

    /**
     * Prints a grid on the view plane at a given interval and color.
     * @param interval the interval between each line of the grid.
     * @param color the color of the grid.
     * @throws MissingResourceException if the image writer field is not initialized.
     */
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

    /**
     Writes the image to a file using the image writer.
     @throws MissingResourceException if the image writer field is not initialized.
     */
    public void writeToImage() {

        if (this.imageWriter == null)
            throw new MissingResourceException("The field is not initialized", "Camera", "imageWriter");

        this.imageWriter.writeToImage();
    }

    private Color castBeamSuperSampling(int j, int i) {
        List<Ray> beam = constructBeamSuperSampling(imageWriter.getNx(), imageWriter.getNy(), j, i);
        Color color = Color.BLACK;
        // calculates the average colour of rays traced
        for (Ray ray : beam) {
            color = color.add(rayTracer.traceRay(ray));
        }
        return color.reduce(nSS);
    }

    private List<Ray> constructBeamSuperSampling(int nX, int nY, int j, int i) {
        List<Ray> beam = new LinkedList<>();
        beam.add(constructRay(nX, nY, j, i));
        double ry = height / nY;
        double rx = width / nX;
        double yScale = alignZero((j - nX / 2d) * rx + rx / 2d);
        double xScale = alignZero((i - nY / 2d) * ry + ry / 2d);
        Point pixelCenter = p0.add(vTo.scale(distance)); // center
        if (!isZero(yScale))
            pixelCenter = pixelCenter.add(vRight.scale(yScale));
        if (!isZero(xScale))
            pixelCenter = pixelCenter.add(vUp.scale(-1 * xScale));
        Random rand = new Random();
        // create rays randomly around the center ray
        for (int c = 0; c < nSS; c++) {
            // move randomly in the pixel
            double dxfactor = rand.nextBoolean() ? rand.nextDouble() : -1 *
                    rand.nextDouble();
            double dyfactor = rand.nextBoolean() ? rand.nextDouble() : -1 *
                    rand.nextDouble();
            double dx = rx * dxfactor;
            double dy = ry * dyfactor;
            Point randomPoint = pixelCenter;
            if (!isZero(dx))
                randomPoint = randomPoint.add(vRight.scale(dx));
            if (!isZero(dy))
                randomPoint = randomPoint.add(vUp.scale(-1 * dy));
            beam.add(new Ray(p0, randomPoint.subtract(p0)));
        }
        return beam;

    }
}

