package renderer;

import primitives.*;
import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.stream.IntStream;

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

    //number of threads to use in image rendering
    private int threadsCount = -1;

    // Indicates whether anti-aliasing is enabled for the camera
    private boolean isAntiAliasing = false;

    // used for thread progress reporting during rendering
    private double printInterval = 0;

    //first parameter for number of random ray to cast for random beam anti aliasing
    private int n;

    // first parameter for number of random ray to cast for random beam anti aliasing
    private int m;

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

    /**
     * Sets the image writer for the camera.
     *
     * @param imageWriter The image writer object.
     * @return The camera object.
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Sets the ray tracer for the camera.
     *
     * @param rayTracer The ray tracer object.
     * @return The camera object.
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Sets the anti-aliasing option for the camera.
     *
     * @param antiAliasing {@code true} to enable anti-aliasing, {@code false} otherwise.
     * @return The camera object.
     */
    public Camera setAntiAliasing(boolean antiAliasing) {
        isAntiAliasing = antiAliasing;
        return this;
    }

    /**
     * Sets the number of random rays to cast for random beam anti-aliasing.
     *
     * @param num The number of random rays.
     * @return The camera object.
     */
    public Camera setN(int num) {
        this.n = num;
        return this;
    }

    /**
     * Sets the number of random rays to cast for random beam anti-aliasing.
     *
     * @param num The number of random rays.
     * @return The camera object.
     */
    public Camera setM(int num) {
        this.m = num;
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
     * Casts a ray from the specified pixel coordinates and writes the resulting color to the image.
     *
     * @param j The x-coordinate of the pixel.
     * @param i The y-coordinate of the pixel.
     */
    private void castRay(int j, int i) {
        Ray ray = constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
        Color color = rayTracer.traceRay(ray);
        imageWriter.writePixel(j, i, color);
    }


    /**
     * given a pixel ,cast a ray to a randomly selected point within a cell in a sub-grid made on a pixel
     *
     * @param Nx         number of rows in view plane
     * @param Ny         number of columns in view plane
     * @param Pij        center point of pixel (i,j)
     * @param gridRow    row index of the cell in  the sub-grid of pixel
     * @param gridColumn column index of the cell in  the sub-grid of pixel
     * @param n          number of rows in the grid
     * @param m          number of columns in the grid
     * @return {@link Ray} from camera to randomly selected point
     * @author menachem bezalel
     */
    public Ray constructRandomRay(int Nx, int Ny, Point Pij, int gridRow, int gridColumn, int n, int m) {

        // calculate "size" of each pixel -
        // height per pixel = total "physical" height / number of rows
        // width per pixel = total "physical" width / number of columns
        double Ry = (double) height / Ny;
        double Rx = (double) width / Nx;

        //calculate height and width of a cell from the sub-grid
        double gridHeight = (double) Ry / n;
        double gridWidth = (double) Rx / m;

        Random r = new Random();
        // set a random value to scale vector on Y axis
        // value range is from -(gridHeight/2) to (gridHeight/2)
        double yI = r.nextDouble(gridHeight) - gridHeight / 2;
        // set a random value to scale vector on X axis
        // value range is from -(gridWidth/2) to (gridWidth/2)
        double xJ = r.nextDouble(gridWidth) - gridWidth / 2;

        // if result of xJ is > 0
        // move result point from middle of pixel to column index in sub-grid
        // then add the random value to move left/right on X axis within the cell
        if (!isZero(xJ)) {
            Pij = Pij.add(vRight.scale(gridWidth * gridColumn + xJ));
        }

        // if result of yI is > 0
        // move result point from middle of pixel to row index in sub-grid
        // then add the random value to move up/down on Y axis within the cell
        if (!isZero(yI)) {
            Pij = Pij.add(vUp.scale(gridHeight * gridRow + yI));
        }

        // return ray cast from camera to randomly selected point within grid of pixel
        // reached by yI and xJ scaling factors
        return new Ray(p0, Pij.subtract(p0));
    }

    /**
     * given a pixel construct a beam of random rays within the grid of the pixel
     *
     * @param Nx  number of rows in view plane
     * @param Ny  number of columns in view plane
     * @param n   first parameter to set number of random rays to cast
     * @param m   second parameter to set number of rays to cast
     * @param ray ray towards the center of the pixel
     * @return list with m*n rays cast randomly within the grid of the pixel
     * @author menachem bezalel
     */
    public List<Ray> constructRayBeam(int Nx, int Ny, int n, int m, Ray ray) {
        // get center point of pixel
        Point Pij = ray.getPoint(distance);
        List<Ray> temp = new LinkedList<>();

        // create a grid of n rows * m columns in each pixel
        // construct a ray from camera to every cell in grid
        // each ray is constructed randomly precisely within the grid borders
        for (int i = -n / 2; i < n / 2; i++)
            for (int j = -m / 2; j < m / 2; j++)
                temp.add(constructRandomRay(Nx, Ny, Pij, i, j, n, m));

        // remove from the list if a  ray was randomly constructed identical to ray to center
        temp.removeIf((item) -> {
            return item.equals(ray);
        });
        // add to list the ray to the center of the pixel
        temp.add(ray);

        return temp;
    }

    /**
     * cast a beam of n*m random beams within a grid of a pixel (i,j)
     *
     * @param Nx number of rows in view plane
     * @param Ny number of columns in view plane
     * @param j  column index of pixel
     * @param i  row index of pixel
     * @param n  first parameter to set number of random rays to cast
     * @param m  second parameter to set number of rays to cast
     * @author menachem bezalel
     */
    private void castRayBeamRandom(int Nx, int Ny, int j, int i, int n, int m) {
        // construct ray through pixel
        Ray ray = constructRay(Nx, Ny, j, i);

        // construct n*m random rays towards the pixel
        var rayBeam = constructRayBeam(Nx, Ny, n, m, ray);

        // calculate color of the pixel using the average from all the rays in beam
        Color color = Color.BLACK;
        for (var r : rayBeam) {
            color = color.add(rayTracer.traceRay(r));
        }
        // reduce final color by total number of rays to get mean value of pixel color
        color = color.reduce(rayBeam.size());

        //write pixel
        imageWriter.writePixel(j, i, color);
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
     * Writes the image to a file using the image writer.
     * @throws MissingResourceException if the image writer field is not initialized.
     */
    public void writeToImage() {

        if (this.imageWriter == null)
            throw new MissingResourceException("The field is not initialized", "Camera", "imageWriter");

        this.imageWriter.writeToImage();
    }


    /**
     * Renders the image using the configured camera settings.
     *
     * @return The camera object.
     * @throws MissingResourceException If the image writer or ray tracer is not initialized.
     */
    public Camera renderImage() throws MissingResourceException {
        // check that image, writing and rendering objects are instantiated
        if (imageWriter == null)
            throw new MissingResourceException("image writer is not initialized", ImageWriter.class.getName(), "");

        if (rayTracer == null)
            throw new MissingResourceException("ray tracer is not initialized", RayTracerBase.class.getName(), "");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        // initialize thread progress reporter
        Pixel.initialize(nY, nX, printInterval);

        if (isAntiAliasing)
            renderImageAntiAliasingRandom(nX, nY);
        else
            renderImage(nX, nY);

        return this;
    }


    /**
     * render image using No improvement with support of multi threading
     * (the fastest runtime - image has the lowest resolution)
     *
     * @param nX number of rows in the view Plane
     * @param nY number of columns in the view plane
     * @author menachem bezalel
     */
    private void renderImage(int nX, int nY) {
        if (threadsCount == 0) {
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j) {
                    castRay(j, i);
                    Pixel.pixelDone();
                    Pixel.printPixel();
                }
        } else if (threadsCount == -1) {
            IntStream.range(0, nY).parallel()
                    .forEach(i -> IntStream.range(0, nX).parallel()
                            .forEach(j -> {
                                castRay(j, i);
                                Pixel.pixelDone();
                                Pixel.printPixel();
                            }));
        } else {
            while (threadsCount-- > 0) {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                        castRay(pixel.col, pixel.row);
                }).start();
            }
            Pixel.waitToFinish();
        }
    }


    /**
     * render image using Random method Anti aliasing improvement with support of multi threading
     * (the slowest runtime - image has high resolution)
     *
     * @param nX number of rows in the view Plane
     * @param nY number of columns in the view plane
     * @author menachem bezalel
     */
    private void renderImageAntiAliasingRandom(int nX, int nY) {
        if (threadsCount == 0) {
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j) {
                    castRayBeamRandom(nX, nY, i, j, n, m);
                    Pixel.pixelDone();
                    Pixel.printPixel();
                }
        } else if (threadsCount == -1) {
            IntStream.range(0, nY).parallel().forEach(i -> IntStream.range(0, nX).parallel().forEach(j -> {
                castRayBeamRandom(nX, nY, i, j, n, m);
                Pixel.pixelDone();
                Pixel.printPixel();
            }));
        } else {
            while (threadsCount-- > 0) {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                        castRayBeamRandom(nX, nY, pixel.col, pixel.row, n, m);
                }).start();
            }
            Pixel.waitToFinish();
        }
    }
}

