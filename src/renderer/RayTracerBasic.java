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


    /**
     * Calculates the local effects of a light source on a given geographical point and ray.
     *
     * @param geoPoint The geographical point at which to calculate the local effects.
     * @param ray The ray associated with the calculation.
     * @return The color resulting from the local effects.
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Color color = Color.BLACK;
        Point point = geoPoint.point;
        Vector n = geoPoint.geometry.getNormal(point);
        Vector v = ray.getDir();

        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv))
            return color;

        Double3 Kd = geoPoint.geometry.getMaterial().Kd;
        Double3 Ks = geoPoint.geometry.getMaterial().Ks;
        double nSh = geoPoint.geometry.getMaterial().nShininess;

        for (LightSource light : scene.lights) {
            Vector l = light.getL(point);
            double ln = alignZero(l.dotProduct(n));
            Color intensity = light.getIntensity(point);

            if (ln * nv > 0) {
                Double3 effects = calcDiffuse(ln, Kd)
                        .add(calcSpecular(l, n, ln, v, nSh, Ks));
                color = color.add(intensity.scale(effects));
            }
        }
        return color;
    }

    /**
     * Calculates the diffuse reflection of light based on the given parameters.
     *
     * @param ln The dot product of the light direction and the surface normal.
     * @param Kd The diffuse reflection coefficient.
     * @return The diffuse reflection color.
     */
    private Double3 calcDiffuse(double ln, Double3 Kd) {
        double abs = Math.abs(ln);
        return Kd.scale(abs);
    }

    /**
     * Calculates the specular reflection of light based on the given parameters.
     *
     * @param l The light direction vector.
     * @param n The surface normal vector.
     * @param ln The dot product of the light direction and the surface normal.
     * @param v The view direction vector.
     * @param nSh The shininess coefficient.
     * @param Ks The specular reflection coefficient.
     * @return The specular reflection color.
     */
    private Double3 calcSpecular(Vector l, Vector n, double ln, Vector v, double nSh, Double3 Ks) {
        Vector r = l.subtract(n.scale(ln * 2)).normalize();
        double vr = v.scale(-1).dotProduct(r);
        double max = Math.max(0, vr);
        double pow = Math.pow(max, nSh);
        return Ks.scale(pow);
    }

}
