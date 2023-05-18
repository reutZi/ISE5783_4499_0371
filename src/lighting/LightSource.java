package lighting;

import primitives.*;

/**
 * Represents a light source in a 3D scene.
 * This interface defines methods for retrieving the intensity and direction of the light at a given point.
 */
public interface LightSource {
    /**
     * Retrieves the color and intensity of the light at the given point.
     *
     * @param p The point at which to retrieve the light intensity.
     * @return The color and intensity of the light at the given point.
     */
    public Color getIntensity(Point p);

    /**
     * Retrieves the direction of the light at the given point.
     *
     * @param p The point at which to retrieve the light direction.
     * @return The direction of the light at the given point.
     */
    public Vector getL(Point p);
}
