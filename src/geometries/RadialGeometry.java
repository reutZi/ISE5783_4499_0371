package geometries;

/** The RadialGeometry class represents a geometric shape with a radial dimension.
 * This is an abstract base class that can be extended to implement specific types of
 * radial geometry, such as circles, spheres, or cylinders.
 * This class implements the Geometry interface. */
public abstract class RadialGeometry extends Geometry{

    /** The radius of the radial geometry. */
    final protected double radius;

    /** Constructs a new RadialGeometry object with the specified radius.
     * @param radius the radius of the radial geometry. */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
