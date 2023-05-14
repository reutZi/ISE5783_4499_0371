package renderer;

import primitives.Color;
import primitives.*;
import scene.Scene;
import java.util.List;
import geometries.Intersectable.GeoPoint;

/**
 * A basic Ray Tracer implementation that extends the RayTracerBase abstract class.
 * Ray tracing is a rendering technique for generating an image by tracing the path of light
 * as pixels in the image plane and simulating the effects of its encounters with virtual objects.
 */

public class RayTracerBasic extends RayTracerBase{

    /**
     * Constructor for the RayTracerBasic class.
     * @param scene The Scene object containing all the objects to be rendered.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {

        List<GeoPoint> pointsList = scene.geometries.findGeoIntersections(ray);

        if (pointsList == null)
            return this.scene.background;

        return calcColor(ray.findClosestGeoPoint(pointsList));
    }

    /**
     * Method to calculate the color of an object at a specific point.
     * @param geoPoint The Point object representing the intersection point with the Ray.
     * @return The Color of the object at the intersection point.
     */
    private Color calcColor(GeoPoint geoPoint){

        return scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission());
    }

}
