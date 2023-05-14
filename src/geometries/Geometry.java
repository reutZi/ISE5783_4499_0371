package geometries;

import primitives.*;

/** The Geometry interface represents a geometric shape. */
public abstract class Geometry extends Intersectable{

    protected Color emission = Color.BLACK;
    private Material material = new Material();

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public Material getMaterial() {
        return material;
    }

    /** Returns the normal vector of the specified point on the surface of this geometry.
     * @param point a Point object representing a point on the surface of this geometry. */
    public abstract Vector getNormal(Point point);
}
