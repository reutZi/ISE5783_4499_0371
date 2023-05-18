package lighting;

import primitives.Color;

/**
 * Represents a light source in a 3D scene.
 * This is an abstract class that serves as a base for different types of lights.
 */
abstract class Light {
    private Color intensity;

    /**
     * Constructs a new Light object with the given intensity.
     *
     * @param intensity The color and intensity of the light.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Retrieves the color and intensity of the light.
     *
     * @return The color and intensity of the light.
     */
    public Color getIntensity() {
        return intensity;
    }
}

