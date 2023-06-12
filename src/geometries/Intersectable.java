package geometries;
import primitives.*;
import java.util.List;
import java.util.Objects;

/**
 * This interface represents an intersectable geometry object.
 */
public abstract class Intersectable {

    /**
     * Represents a geographical point with associated geometry information.
     */
    public static class GeoPoint {

        /** The geometry information associated with the point. */
        public Geometry geometry;

        /** The coordinates of the point. */
        public Point point;

        /**
         * Constructs a new GeoPoint object with the given geometry and point.
         *
         * @param geometry The geometry information associated with the point.
         * @param point    The coordinates of the point.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * Checks if this GeoPoint is equal to the specified object.
         *
         * @param o The object to compare.
         * @return true if the objects are equal, false otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof GeoPoint other)
                return this.geometry.equals(other.geometry) && this.point.equals(other.point);
            return false;
        }

        /**
         * Computes the hash code of this GeoPoint.
         *
         * @return The computed hash code.
         */
        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        /**
         * Returns a string representation of this GeoPoint.
         *
         * @return The string representation.
         */
        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Returns a list of intersection points between the given ray and this geometry.
     *
     * @param ray the ray to intersect with this geometry.
     * @return A list of intersection points, or null if no intersections occur.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the geographical intersections between a given ray and the geometry.
     *
     * @param ray The ray used to find intersections.
     * @return A list of GeoPoint objects representing the geographical intersections.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * Finds the geographical intersections between a given ray and the geometry, up to a maximum distance.
     *
     * @param ray         The ray used to find intersections.
     * @param maxDistance The maximum distance for the intersections.
     * @return A list of GeoPoint objects representing the geographical intersections.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Helper method for finding the geographical intersections between a given ray and the geometry.
     *
     * @param ray         The ray used to find intersections.
     * @param maxDistance The maximum distance for the intersections.
     * @return A list of GeoPoint objects representing the geographical intersections.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /**
     * Finds the closest geographical intersection point between a given ray and the geometry.
     *
     * @param ray The ray used to find the closest intersection.
     * @return The closest GeoPoint object representing the geographical intersection.
     */
    public GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> list = findGeoIntersections(ray);
        return ray.findClosestGeoPoint(list);
    }
}
