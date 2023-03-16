package primitives;


/**
 * this class represents a Ray. That is, a point and a direction vector coming out of the point.
 */
public class Ray {
    final Point p0;
    final Vector dir;

    /**
     * function to get the point
     * @return the started point of the ray
     */
    public Point getP0() {
        return p0;
    }
    /**
     * function to get the direction vector
     * @return the direction of the ray. That normalize.
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Creates a Ray object with the specified point and the direction vector
     * @param p0 The started point of the ray
     * @param dir the direction of the ray. We will keep the vector normalizedץ
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return this.p0.equals(ray.p0) && this.dir.equals(ray.dir);
        //equal between the points' and between the vectors.
    }

    @Override
    public String toString() {
        return "Ray:" +
                "p0=" + p0.toString() +
                ", dir=" + dir.toString();
    }
}
