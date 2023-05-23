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

    /** The name of the scene. */
    public String name;

    /** The background color of the scene. */
    public Color background = BLACK;

    /** The ambient light of the scene. */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /** The geometries in the scene. */
    public Geometries geometries;

    /** The light sources in the scene. */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Constructs a new Scene object with the provided builder.
     * @param builder The builder object containing the scene's properties.
     */
    private Scene(SceneBuilder builder) {
        this.name = builder.name;
        this.background = builder.background;
        this.ambientLight = builder.ambientLight;
        this.geometries = builder.geometries;
        this.lights = builder.lights;
    }

    /**
     * Sets the geometries of the scene.
     * @param geometries The geometries to be added to the scene.
     */
    public void setGeometries(Geometries geometries) {
        this.geometries = geometries;
    }

    public static class SceneBuilder {
        private String name;
        private Color background = BLACK;
        private AmbientLight ambientLight = AmbientLight.NONE;
        private Geometries geometries = new Geometries();
        private List<LightSource> lights = new LinkedList<>();

        /**
         * Sets the background color of the scene.
         * @param background The background color of the scene.
         * @return The modified Scene object.
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        /**
         * Sets the ambient light of the scene.
         * @param ambientLight The ambient light of the scene.
         * @return The modified Scene object.
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * Sets the geometries of the scene.
         * @param geometries The geometries to be added to the scene.
         * @return The modified Scene object.
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        /**
         * Sets the light sources of the scene.
         * @param lights The light sources to be added to the scene.
         * @return The modified Scene object.
         */
        public SceneBuilder setLights(List<LightSource> lights) {
            this.lights = lights;
            return this;
        }

        public SceneBuilder(String name) {
            this.name = name;
        }

        /**
         * Constructs a new Scene object with the configured properties.
         * @return The constructed Scene object.
         */
        public Scene build() {
            return new Scene(this);
        }
    }
    /**
     * Constructs a new Scene object with the given name.
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
        this.geometries = new Geometries();
    }
}

