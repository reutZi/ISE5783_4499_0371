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

    /**
     * Traces a ray and calculates the color of the intersected object.
     *
     * @param ray The Ray object representing the ray to trace.
     * @return The Color of the intersected object or the background color if no intersection occurs.
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint geoPoint = scene.geometries.findClosestIntersection(ray);

        if (geoPoint == null)
            return this.scene.background;

        return calcColor(geoPoint, ray);
    }

    /**
     * Calculates the color of an object at a specific point.
     *
     * @param geoPoint The GeoPoint object representing the intersection point with the Ray.
     * @param ray      The Ray object associated with the calculation.
     * @return The Color of the object at the intersection point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));
    }

    /**
     * Calculates the color of an object at a specific point recursively, considering local and global effects.
     *
     * @param gp    The GeoPoint object representing the intersection point with the Ray.
     * @param ray   The Ray object associated with the calculation.
     * @param level The recursion level.
     * @param k     The accumulated transparency coefficient.
     * @return The Color of the object at the intersection point, considering local and global effects.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * Calculates the local effects of a light source on a given geographical point and ray.
     *
     * @param geoPoint The GeoPoint object representing the geographical point at which to calculate the local effects.
     * @param ray      The Ray object associated with the calculation.
     * @param k        The accumulated transparency coefficient.
     * @return The Color resulting from the local effects.
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
        Color color = geoPoint.geometry.getEmission();
        Point point = geoPoint.point;
        Vector n = geoPoint.geometry.getNormal(point);
        Vector v = ray.getDir();

        // Calculate the dot product of the surface normal and the ray direction vector
        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv))
            return color;

        Double3 Kd = geoPoint.geometry.getMaterial().Kd;
        Double3 Ks = geoPoint.geometry.getMaterial().Ks;
        double nSh = geoPoint.geometry.getMaterial().nShininess;

        // Iterate over all light sources in the scene
        for (LightSource light : scene.lights) {
            Vector l = light.getL(point);
            double ln = alignZero(l.dotProduct(n));
            Color intensity = light.getIntensity(point);

            // Check if the light direction and surface normal are on the same side of the surface
            if (ln * nv > 0) {
                // Calculate the transparency coefficient for the light source at the current geographical point
                Double3 ktr = transparency(geoPoint, light, l, n);

                // Check if the accumulated transparency coefficient multiplied by the current transparency coefficient is above the minimum threshold
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    // Calculate the diffuse and specular components of the local effects
                    Double3 effects = calcDiffuse(ln, Kd).add(calcSpecular(l, n, v, nSh, Ks));

                    // Add the scaled intensity of the light source multiplied by the local effects to the accumulated color
                    color = color.add(intensity.scale(effects).scale(ktr));
                }
            }
        }

        return color;
    }


    /**
     * Calculates the global effects (reflection and refraction) of a light source on a given geographical point and ray.
     *
     * @param gp    The GeoPoint object representing the geographical point at which to calculate the global effects.
     * @param ray   The Ray object associated with the calculation.
     * @param level The recursion level.
     * @param k     The accumulated transparency coefficient.
     * @return The Color resulting from the global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kkr = material.Kr.product(k);

        // Check if the accumulated reflection coefficient is above the minimum threshold
        if (!kkr.lowerThan(MIN_CALC_COLOR_K))
            // Add the color resulting from the reflected ray to the accumulated color
            color = color.add(calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.Kr, kkr));

        Double3 kkt = material.Kt.product(k);

        // Check if the accumulated refraction coefficient is above the minimum threshold
        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
            // Add the color resulting from the refracted ray to the accumulated color
            color = color.add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.Kt, kkt));

        return color;
    }


    /**
     * Calculates the global effect (reflection or refraction) of a light source on a given geographical point and ray.
     *
     * @param ray   The Ray object representing the ray to trace.
     * @param level The recursion level.
     * @param kx    The accumulated transparency coefficient of the object.
     * @param kkx   The accumulated transparency coefficient of the effect.
     * @return The Color resulting from the global effect.
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
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
     * @param v   The view direction vector.
     * @param nSh The shininess coefficient.
     * @param Ks  The specular reflection coefficient.
     * @return The specular reflection color.
     */
    private Double3 calcSpecular(Vector l, Vector n, Vector v, double nSh, Double3 Ks) {
        Vector r = reflectionVector(l, n);
        double vr = v.scale(-1).dotProduct(r);
        double max = Math.max(0, vr);
        double pow = Math.pow(max, nSh);

        return Ks.scale(alignZero(pow));
    }

    /**
     * Checks if a given point on an object is unshaded by other objects in the scene.
     *
     * @param l     The light direction vector.
     * @param n     The surface normal vector.
     * @param gp    The GeoPoint object representing the geographical point.
     * @param light The LightSource object representing the light source.
     * @return true if the point is unshaded, false otherwise.
     */
    private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource light) {
        // Calculate the direction of the light from the point on the object
        Vector lightDirection = l.scale(-1);

        // Create a ray from the point on the object towards the light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);

        // Calculate the distance between the point on the object and the light source
        double distance = light.getDistance(gp.point);

        // Find intersections between the light ray and objects in the scene within the given distance
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, distance);

        // If no intersections are found, the point is unshaded
        if (intersections == null)
            return true;

        // Check if any of the intersections have a transparency coefficient below a minimum threshold
        for (GeoPoint geoPoint : intersections) {
            // If the transparency coefficient of an intersected object falls below the minimum threshold, the point is shaded
            if (geoPoint.geometry.getMaterial().Kt.lowerThan(MIN_CALC_COLOR_K)) {
                return false;
            }
        }

        // If no intersections have a transparency coefficient below the minimum threshold, the point is unshaded
        return true;
    }


    /**
     * Finds the closest intersection point between a ray and the objects in the scene.
     *
     * @param ray The Ray object representing the ray to trace.
     * @return The GeoPoint object representing the closest intersection point, or null if there is no intersection.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.geometries.findGeoIntersections(ray);
        if (intersectionPoints == null)
            return null;
        return ray.findClosestGeoPoint(intersectionPoints);
    }

    /**
     * Constructs a refracted ray based on the given parameters.
     *
     * @param point The Point object representing the starting point of the refracted ray.
     * @param v     The Vector object representing the direction of the refracted ray.
     * @param n     The Vector object representing the normal to the surface.
     * @return The constructed refracted ray.
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * Constructs a reflected ray based on the given parameters.
     *
     * @param point The Point object representing the starting point of the reflected ray.
     * @param v     The Vector object representing the direction of the reflected ray.
     * @param n     The Vector object representing the normal to the surface.
     * @return The constructed reflected ray.
     */
    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        return new Ray(point, reflectionVector(v, n), n);
    }

    /**
     * Calculates the reflection vector based on the given parameters.
     *
     * @param l The light direction vector.
     * @param n The surface normal vector.
     * @return The reflection vector.
     */
    private Vector reflectionVector(Vector l, Vector n) {
        double ln2 = l.dotProduct(n) * 2;
        Vector nln2 = n.scale(ln2);
        return l.subtract(nln2).normalize();
    }


    /**
     * Calculates the transparency coefficient based on the given parameters.
     *
     * @param gp    The GeoPoint object representing the intersection point.
     * @param light The LightSource object representing the light source.
     * @param l     The light direction vector.
     * @param n     The surface normal vector.
     * @return The transparency coefficient.
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
         // Calculate the direction of the light from the intersection point
        Vector lightDirection = l.scale(-1);

         // Create a ray from the intersection point towards the light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);

        // Calculate the distance between the intersection point and the light source
        double distance = light.getDistance(gp.point);

        // Find intersections between the light ray and objects in the scene within the given distance
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, distance);

        // If no intersections are found, return full transparency (transparency coefficient of 1.0)
        if (intersections == null)
            return Double3.ONE;

        // Initialize the transparency coefficient as full transparency
        Double3 ktr = Double3.ONE;

        // Iterate over the intersections and update the transparency coefficient
        for (GeoPoint geoPoint : intersections) {
        // Multiply the transparency coefficient by the material's transparency coefficient at the intersection point
            ktr = ktr.product(geoPoint.geometry.getMaterial().Kt);


            // If the transparency coefficient falls below a minimum threshold, return full opacity (transparency coefficient of 0.0)
            if (ktr.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }

        // Return the final transparency coefficient
        return ktr;
    }
}

