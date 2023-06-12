package geometries;

import primitives.*;

/**
 * The Geometry interface represents a geometric shape.
 */
public abstract class Geometry extends Intersectable{

    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * Returns the emission color of this geometry.
     *
     * @return The emission color.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of this geometry.
     *
     * @param emission The emission color to set.
     * @return The updated Geometry object.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Sets the material of this geometry.
     *
     * @param material The material to set.
     * @return The updated Geometry object.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Returns the material of this geometry.
     *
     * @return The material.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Returns the normal vector of the specified point on the surface of this geometry.
     *
     * @param point A Point object representing a point on the surface of this geometry.
     * @return The normal vector at the specified point.
     */
    public abstract Vector getNormal(Point point);
}