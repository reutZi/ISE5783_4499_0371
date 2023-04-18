package primitives;

/**
 * This class represents a ray in 3D space. A ray is defined by a starting point (p0)
 * <p>
 * and a direction vector (dir) that comes out of the point.
 */
public class Ray {

    /**
     * The starting point of the ray.
     */
    final Point p0;
    /**
     * The normalized direction vector of the ray.
     */
    final Vector dir;

    /**
     * Constructs a new Ray object with the specified starting point and direction vector.
     *
     * @param p0  the starting point of the ray
     * @param dir the direction vector of the ray. It will be normalized.
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Returns the starting point of the ray.
     *
     * @return the starting point of the ray
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the direction vector of the ray.
     *
     * @return the direction vector of the ray
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Determines whether this ray is equal to the specified object.
     * Two rays are equal if they have the same starting point and direction vector.
     *
     * @param o the object to compare to
     * @return true if this ray is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    /**
     * Returns a string representation of this ray.
     *
     * @return a string representation of this ray
     */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}