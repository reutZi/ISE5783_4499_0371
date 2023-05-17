package renderer;


import lighting.LightSource;
import primitives.Color;
import primitives.*;
import static primitives.Util.*;
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

        return calcColor(ray.findClosestGeoPoint(pointsList), ray);
    }


    /**
     * Method to calculate the color of an object at a specific point.
     * @param geoPoint The Point object representing the intersection point with the Ray.
     * @return The Color of the object at the intersection point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray){
        return scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission())
                .add(calcLocalEffects(geoPoint, ray));
    }


    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Color color = Color.BLACK;
        Point point = geoPoint.point;
        Vector n = geoPoint.geometry.getNormal(point);
        Vector v = ray.getDir ();

        double nv = alignZero(n.dotProduct(v));
        if(isZero(nv))
            return color;

        Double3 Kd = geoPoint.geometry.getMaterial().Kd;
        Double3 Ks = geoPoint.geometry.getMaterial().Ks;
        double nSh = geoPoint.geometry.getMaterial().nShininess;

        for(LightSource light : scene.lights) {

            Vector l = light.getL(point);
            double ln = alignZero(l.dotProduct(n));
            Color intensity = light.getIntensity(point);

            if(ln * nv > 0){
                Double3 effects = calcDiffusive(ln, Kd)
                        .add((calcSpecular(l, n, ln, v, nSh, Ks)));
                color = color.add(intensity.scale(effects));
            }
        }
        return color;
    }

    private Double3 calcDiffusive(double ln, Double3 Kd) {

        double abs = Math.abs(ln);

        return Kd.scale(abs);
    }

    private Double3 calcSpecular(Vector l, Vector n, double ln, Vector v, double nSh, Double3 Ks) {

        Vector r = l.subtract(n.scale(ln * 2)).normalize();

        double vr = v.scale(-1).dotProduct(r);

        double max = Math.max(0, vr);

        double pow = Math.pow(max, nSh);

       return Ks.scale(pow);
    }
}
