package scene;
import primitives.*;
import lighting.*;
import geometries.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Color.BLACK;

/**
 * Represents a scene in a 3D environment.
 * This scene contains information about the scene's name, background color, ambient light, geometries, and light sources.
 * The scene is constructed using the Builder pattern.
 */
public class Scene {
    public String name;
    public Color background = BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;

    public Geometries geometries;
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Sets the background color of the scene.
     *
     * @param background The background color of the scene.
     * @return The modified Scene object.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight The ambient light of the scene.
     * @return The modified Scene object.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries of the scene.
     *
     * @param geometries The geometries to be added to the scene.
     * @return The modified Scene object.
     */
    public Scene setGeometries(geometries.Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the light sources of the scene.
     *
     * @param lights The light sources to be added to the scene.
     * @return The modified Scene object.
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * Constructs a new Scene object with the given name.
     *
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
        this.geometries = new Geometries();
    }
}

