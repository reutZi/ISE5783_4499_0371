package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
public class Geometries implements Intersectable{
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
    public List<Point> findIntersections(Ray ray){
        List<Point> Intersection = new LinkedList<>();

        for (Intersectable intersectable: geometriesList) {
            List<Point> currentIntersection = intersectable.findIntersections(ray);
            if(currentIntersection != null)
                Intersection.addAll(currentIntersection);
        }

        if(Intersection.size() == 0)
            return null;
        return Intersection;
    }
}
