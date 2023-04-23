package primitives;

import java.util.zip.ZipError;

/** A class representing a three-dimensional vector in Cartesian space, inheriting from Point.*/
public class Vector extends Point {

    /** Constructs a new Vector with the specified x, y, and z components.
     * @param x the x component of the vector
     * @param y the y component of the vector
     * @param z the z component of the vector
     * @throws IllegalArgumentException if the vector is the zero vector. */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("");
    }

    /** Constructs a new Vector with the specified Double3 object.
     * @param double3 the Double3 object representing the vector's components. */
    Vector(Double3 double3) {
        this(double3.d1, double3.d2, double3.d3);
    }

    /** @return the squared length of this vector. */
    public double lengthSquared() {
        double dx = xyz.d1;
        double dy = xyz.d2;
        double dz = xyz.d3;

        return dx * dx + dy * dy + dz * dz;
    }

    /** @return the length (magnitude) of this vector. */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /** Determines whether this vector is equal to the specified object.
     * @param o the object to compare to this vector.
     * @return true if the object is a Vector and has the same components as this vector, false otherwise. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Vector other)
            return xyz.equals(other.xyz);
        return false;
    }

    /** @return a new Vector representing this vector scaled to unit length. */
    public Vector normalize() {
        double len = length();
        // for better performance we are not using the following
        // return new Vector(xyz.reduce(len));
        return new Vector(xyz.d1 / len, xyz.d2 / len, xyz.d3 / len);
    }

    /** @param other the vector to calculate the dot product with.
     * @return the dot product of this vector and the specified vector. */
    public double dotProduct(Vector other) {
        double dx = xyz.d1 * other.xyz.d1;
        double dy = xyz.d2 * other.xyz.d2;
        double dz = xyz.d3 * other.xyz.d3;

        return dx + dy + dz;
    }

    /** @param other the vector to calculate the cross product with
     * @return a new Vector representing the cross product of this vector and the specified vector. */
    public Vector crossProduct(Vector other) {
        double x = xyz.d2 * other.xyz.d3 - xyz.d3 * other.xyz.d2;
        double y = xyz.d3 * other.xyz.d1 - xyz.d1 * other.xyz.d3;
        double z = xyz.d1 * other.xyz.d2 - xyz.d2 * other.xyz.d1;

        return new Vector(x, y, z);
    }

    /** @param other the Vector to add to the current Vector.
     * @return a new Vector that is the result of adding the current Vector with the specified Vector. */
    public Vector add(Vector other) {
        // for better performance we are not using the following
        // return new Vector(xyz.add(other.xyz));
        return new Vector(xyz.d1 + other.xyz.d1, xyz.d2 + other.xyz.d2,  xyz.d3 + other.xyz.d3);
    }

    /** @param rhs the scalar value to scale the Vector by.
     * @return a new Vector that is the result of scaling the current Vector by the specified scalar value. */
    public Vector scale(Double rhs) {
        // for better performance we are not using the following
        // return new Vector(xyz.scale(rhs));
        return new Vector(xyz.d1 * rhs, xyz.d2 * rhs,  xyz.d3 * rhs);
    }

    @Override
    public String toString() {
        return "Vector: " + xyz;
    }
}
