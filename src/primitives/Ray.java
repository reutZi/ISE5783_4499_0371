package primitives;

import java.util.List;
import geometries.Intersectable.GeoPoint;

/** This class represents a ray in 3D space. A ray is defined by a starting point (p0)
 * and a direction vector (dir) that comes out of the point. */
public class Ray {

    /** The starting point of the ray. */
    final Point p0;

    /** The normalized direction vector of the ray. */
    final Vector dir;

    /** Constructs a new Ray object with the specified starting point and direction vector.
     * @param p0  the starting point of the ray
     * @param dir the direction vector of the ray. It will be normalized. */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /** @return the starting point of the ray. */
    public Point getP0() {
        return p0;
    }

    /** @return the direction vector of the ray. */
    public Vector getDir() {
        return dir;
    }

    /** Determines whether this ray is equal to the specified object.
     * Two rays are equal if they have the same starting point and direction vector.
     * @param o the object to compare to
     * @return true if this ray is equal to the specified object, false otherwise. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    /** @return a string representation of this ray. */
    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    public Point getPoint(double t){
        return p0.add(dir.scale(t));
    }

    /** Finds the closest point to this ray among a list of points.
     * @param points the list of points to search for the closest point.
     * @return the closest point to this ray, or null if the list is empty. */
    public Point findClosestPoint(List<Point> points){
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints){

        if (geoPoints.isEmpty())
            return null;

        double minDistance = p0.distanceSquared(geoPoints.get(0).point);
        double distance;
        GeoPoint closestPoint = null;

        for(GeoPoint geoPoint : geoPoints) {

            distance = p0.distanceSquared(geoPoint.point);

            if(distance <= minDistance){
                closestPoint = geoPoint;
                minDistance = distance;
            }
        }
        return closestPoint;
    }
}