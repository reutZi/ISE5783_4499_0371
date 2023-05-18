package lighting;

import primitives.*;

/**
 * Represents a spotlight in a 3D scene.
 * This spotlight is a type of point light source that emits light in a specific direction within a cone-shaped region.
 */
public class SpotLight extends PointLight {
    private Vector dir;

    /**
     * Sets the direction of the spotlight.
     *
     * @param dir The direction in which the spotlight emits light.
     */
    public void setDir(Vector dir) {
        this.dir = dir;
    }

    /**
     * Constructs a new SpotLight object with the given intensity, position, and direction.
     *
     * @param intensity The color and intensity of the light.
     * @param position The position of the light source.
     * @param dir The direction in which the spotlight emits light.
     */
    public SpotLight(Color intensity, Point position, Vector dir) {
        super(intensity, position);
        this.dir = dir.normalize();
    }

    @Override
    public Color getIntensity(Point p){

        double projection = this.dir.dotProduct(getL(p));

        double numerator = Math.max(0, projection);

        return super.getIntensity(p).scale(numerator);
    }
}
