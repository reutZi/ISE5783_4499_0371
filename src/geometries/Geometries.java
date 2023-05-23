package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.LinkedList;
import java.util.List;
public class Geometries extends Intersectable{
    List <Intersectable> geometriesList;

    public Geometries(){
        geometriesList = new LinkedList<Intersectable>();
    }

    public  Geometries(Intersectable... geometries){
        geometriesList = new LinkedList<>();
        add(geometries);
    }

    public void add(Intersectable... geometries){
        for (Intersectable geometry : geometries)
            geometriesList.add(geometry);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){

        // Initialize a new empty list of intersection points.
        List<GeoPoint> Intersection = null;

        // Loop through each intersectable geometry in the geometriesList.
        for (Intersectable intersectable : geometriesList) {
            // Find intersections between the current geometry and the specified Ray.
            List<GeoPoint> currentIntersection = intersectable.findGeoIntersections(ray);

            // If there are intersections, add them to the list of intersection points.
            if (currentIntersection != null && Intersection == null) {

                Intersection = new LinkedList<GeoPoint>();
                Intersection.addAll(currentIntersection);

            } else if (currentIntersection != null) {
                Intersection.addAll(currentIntersection);
            }
        }

        // Otherwise, return the list of intersection points.
        return Intersection;
    }
}
