package scene;
import primitives.*;
import lighting.*;
import geometries.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Color.BLACK;

public class Scene {

    public String name;
    public Color background = BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;

    public Geometries geometries;
    public List<LightSource> lights = new LinkedList<>();

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(geometries.Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    public Scene(String name) {
        this.name = name;
        this.geometries = new Geometries();
    }
}
