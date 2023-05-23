package lighting;

import primitives.*;

/**
 * Represents a directional light source in a 3D scene.
 * This light source emits light in a specific direction uniformly across the scene.
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector dir;

    /**
     * Constructs a new DirectionalLight object with the given intensity and direction.
     *
     * @param intensity The color and intensity of the light.
     * @param dir The direction in which the light is emitted.
     */
    public DirectionalLight(Color intensity, Vector dir) {
        super(intensity);
        this.dir = dir.normalize();
    }
    @Override
    public Color getIntensity(Point p){
        return this.getIntensity();
    }

    @Override
    public Vector getL(Point p){
        return this.dir;
    }

    @Override
    public double getDistance(Point point){
        return Double.POSITIVE_INFINITY;
    }
}
