package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
public class Geometries implements Intersectable{
    List <Intersectable> list;

    public Geometries(){
        list = new LinkedList<Intersectable>();
    }

    public  Geometries(Intersectable... geometries){
    }

    public void add(Intersectable... geometries){
    }

    @Override
    public List<Point> findIntersections(Ray ray){
        return null;
    }
}
