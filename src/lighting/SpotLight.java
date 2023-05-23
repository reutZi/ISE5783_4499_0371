package lighting;

import primitives.*;

import static primitives.Util.isZero;

/**
 * Represents a spotlight in a 3D scene.
 * This spotlight is a type of point light source that emits light in a specific direction within a cone-shaped region.
 */
public class SpotLight extends PointLight {
    private Vector dir;
    private double narrowness;

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

    /**
     * Sets the angle of the spotlight beam.
     * @param narrowness The angle of the spotlight beam in degrees.
     * */
    public SpotLight setNarrowBeam(double narrowness) {
        this.narrowness = narrowness;
        return this;
    }

    /*@Override
    public Color getIntensity(Point p){

        double projection = this.dir.dotProduct(getL(p));

        double numerator = Math.max(0, projection);

        return super.getIntensity(p).scale(numerator);
    }*/

    /**
     * Calculates the intensity of the spotlight at a given point in the scene.
     * @param p The point at which to calculate the intensity.
     * @return The color intensity at the specified point, based on the spotlight's properties.
     */
    @Override
    public Color getIntensity(Point p) {
        // Calculate the dot product between the light vector and the spotlight direction
        double projection = getL(p).dotProduct(dir);

        // Check if the point is outside the beam of the spotlight
        if (projection <= 0) {
            return Color.BLACK;
        }

        // Calculate the intensity based on the projection raised to the power of narrowness
        double intensity = Math.pow(projection, narrowness);

        // Scale the intensity with the super intensity
        return super.getIntensity(p).scale(intensity);
    }

}
