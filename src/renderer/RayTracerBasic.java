package renderer;

import lighting.*;
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
public class RayTracerBasic extends RayTracerBase {

    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final Double3 INITIAL_K = new Double3(1.0d);



    /**
     * Constructor for the RayTracerBasic class.
     *
     * @param scene The Scene object containing all the objects to be rendered.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint geoPoint = scene.geometries.findClosestIntersection(ray);

        if (geoPoint == null)
            return this.scene.background;

        return calcColor(geoPoint, ray);
    }

    /**
     * Method to calculate the color of an object at a specific point.
     *
     * @param geoPoint The Point object representing the intersection point with the Ray.
     * @return The Color of the object at the intersection point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
         /*return scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission())
                .add(calcLocalEffects(geoPoint, ray));*/

        return scene.ambientLight.getIntensity()
                .add(calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));
    }

    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray).add(gp.geometry.getEmission());
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }


    /**
     * Calculates the local effects of a light source on a given geographical point and ray.
     *
     * @param geoPoint The geographical point at which to calculate the local effects.
     * @param ray      The ray associated with the calculation.
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
                if (unshaded(l,n, geoPoint, light)) {
                    Double3 effects = calcDiffuse(ln, Kd)
                            .add(calcSpecular(l, n, ln, v, nSh, Ks));
                    color = color.add(intensity.scale(effects));
                }
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
     * @param l   The light direction vector.
     * @param n   The surface normal vector.
     * @param ln  The dot product of the light direction and the surface normal.
     * @param v   The view direction vector.
     * @param nSh The shininess coefficient.
     * @param Ks  The specular reflection coefficient.
     * @return The specular reflection color.
     */
    private Double3 calcSpecular(Vector l, Vector n, double ln, Vector v, double nSh, Double3 Ks) {
        Vector r = l.subtract(n.scale(ln * 2)).normalize();
        double vr = v.scale(-1).dotProduct(r);
        double max = Math.max(0, vr);
        double pow = Math.pow(max, nSh);

        return Ks.scale(alignZero(pow));
    }

    private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource light) {

        Vector lightDirection = l.scale(-1);

        Ray lightRay = new Ray(gp.point, lightDirection,n);

        double distance = light.getDistance(gp.point);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, distance);

        if( intersections == null)
            return  true;

        for (GeoPoint geoPoint : intersections) {
            if (geoPoint.geometry.getMaterial().Kt.lowerThan(MIN_CALC_COLOR_K)) {
                return false;
            }
        }

        return true;
    }


    private Color calcColorGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kx)).scale(k);
    }

    /**
     * calculate the color according to the k factor for the reflection and refraction effects
     * @param gp calculate the color of this point
     * @param ray the ray of intersection that 'hit' the point
     * @param level of the recursion
     * @param k the volume of the color
     * @return the calculated color
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        Double3 kkr = k.product(material.Kr);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) // the color is effected by the reflection
            color = calcColorGlobalEffect(constructReflectedRay(gp.point, ray, n), level, material.Kr, kkr);

        Double3 kkt = k.product(material.Kt);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) // the color is effected due to the transparency
            color = color.add(calcColorGlobalEffect(constructRefractedRay(gp.point, ray, n), level, material.Kt, kkt));

        return color;
    }




    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.geometries.findGeoIntersections(ray);
        if (intersectionPoints == null)
            return null;
        return ray.findClosestGeoPoint(intersectionPoints);
    }

    private Ray constructRefractedRay(Point point, Ray ray, Vector n) {
        return new Ray(point, ray.getDir(),n);
    }


    private Ray constructReflectedRay(Point point, Ray ray, Vector n) {
        return new Ray(point, reflectionVector(ray.getDir(), n), n);
    }

    private Vector reflectionVector(Vector l, Vector n) {
        return l.subtract(n.scale(2 * l.dotProduct(n))).normalize();
    }





/*
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcColorGlobalEffect(constructReflectedRay(gp.point, ray.getDir(), n),
                level, k, material.Kr)
                .add(calcColorGlobalEffect(constructRefractedRay(gp.point, ray.getDir(), n),
                        level, k, material.Kt));
    }

    /* private Color calcColorGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = scene.geometries.findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir()))
                ? Color.BLACK : calcColor(gp, ray, level - 1, kkx);
    }
*/


}




