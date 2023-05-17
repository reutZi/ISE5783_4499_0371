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

        if(nv == 0)
            return color;

        Double3 Kd = geoPoint.geometry.getMaterial().Kd;
        Double3 Ks = geoPoint.geometry.getMaterial().Ks;
        double nSh = geoPoint.geometry.getMaterial().nShininess;

        for(LightSource light : scene.lights) {

            Vector l = light.getL(point);
            double ln = alignZero(l.dotProduct(n));
            Color intensity = light.getIntensity(point);

            if(ln * nv > 0){

                color.add(calcDiffusive(l, n, Kd, intensity)
                        .add(calcSpecular(l, n, v, nSh, Ks, intensity)));
            }
        }
        return color;
    }

    private Color calcDiffusive(Vector l, Vector n, Double3 Kd, Color intensity) {

        double abs = Math.abs(n.dotProduct(l));

        return intensity.scale(Kd.scale(abs));
    }

    private Color calcSpecular(Vector l, Vector n, Vector v, double nSh, Double3 Ks, Color intensity) {

        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2)).normalize();

        double vr = v.scale(-1).dotProduct(r);

        double max = Math.max(0, vr);

        double pow = Math.pow(max, nSh);

        Double3 ksMax = Ks.scale(pow);

        return intensity.scale(ksMax);
    }
}
