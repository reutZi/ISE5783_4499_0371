package primitives;

import java.util.Objects;

/**
 * The Point class represents a point in three-dimensional space with an x, y, and z coordinate.
 */
public class Point {
    final Double3 xyz;

    /**
     * Creates a Point object with the specified x, y, and z coordinates.
     * @param x the x coordinate of the Point.
     * @param y the y coordinate of the Point.
     * @param z the z coordinate of the Point.
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x,y,z);
    }

    /**
     * Creates a Point object with the coordinates specified in the given Double3 object.
     * @param double3 the Double3 object containing the x, y, and z coordinates of the Point.
     */
    Point(Double3 double3) {
        this(double3.d1, double3.d2, double3.d3);
    }

    /**
     * Compares the Point with the specified object for equality.
     * @param o the object to be compared with the Point.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Point other)
            return this.xyz.equals(other.xyz);
        return false;
    }

    /**
     * @return a hash code for the Point.
     */
    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    /**
     * @return a string representation of the Point.
     */
    @Override
    public String toString() {
        return "Point:" + xyz;
    }

    /**
     * Calculates the squared distance between this Point and another Point.
     * @param other the Point to calculate the distance to.
     * @return the squared distance between the two Points.
     */
    public double distanceSquared(Point other){
        double dx = other.xyz.d1 - xyz.d1;
        double dy = other.xyz.d2 - xyz.d2;
        double dz = other.xyz.d3 - xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Calculates the distance between this Point and another Point.
     * @param other the Point to calculate the distance to.
     * @return the distance between the two Points.
     */
    public double distance(Point other){
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Adds a Vector to this Point and returns a new Point.
     * @param vector the Vector to add to this Point.
     * @return a new Point representing the result of the addition.
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Subtracts another Point from this Point and returns a new Vector.
     * @param other the Point to subtract from this Point.
     * @return a new Vector representing the result of the subtraction.
     */
    public Vector subtract(Point other) {
        return new Vector(xyz.subtract(other.xyz));
    }
}
