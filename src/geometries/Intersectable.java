package geometries;
import primitives.*;
import java.util.List;
import java.util.Objects;

/** This interface represents an intersectable geometry object. */
public abstract class Intersectable {

    /**
     * Represents a geographical point with associated geometry information.
     */
    public static class GeoPoint {
        /**
         * The geometry information associated with the point.
         */
        public Geometry geometry;

        /**
         * The coordinates of the point.
         */
        public Point point;

        /**
         * Constructs a new GeoPoint object with the given geometry and point.
         *
         * @param geometry The geometry information associated with the point.
         * @param point The coordinates of the point.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }


    @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof GeoPoint other)
              return this.geometry.equals(other.geometry) && this.point.equals(other.point);//??
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /** Returns a list of intersection points between the given ray and this geometry.
     * @param ray the ray to intersect with this geometry. */
    public List<Point> findIntersections(Ray ray){
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
        return findGeoIntersectionsHelper(ray);
    }


    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
