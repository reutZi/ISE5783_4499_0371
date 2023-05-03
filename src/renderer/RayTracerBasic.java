package renderer;

import primitives.Color;
import primitives.*;
import scene.Scene;
import java.util.List;

public class RayTracerBasic extends RayTracerBase{

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> pointsList = scene.geometries.findIntersections(ray);

        if (pointsList == null)
            return this.scene.background;

        return calcColor(ray.findClosestPoint(pointsList));
    }

    private Color calcColor(Point point){
        return scene.ambientLight.getIntensity();
    }
}
