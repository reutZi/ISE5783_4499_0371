package scene;
import primitives.*;
import lighting.*;
import geometries.*;
import static primitives.Color.BLACK;

public class Scene {

    public String name;
    public Color background = BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;

    public Geometries geometries;

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

    public Scene(String name) {
        this.name = name;
        this.geometries = new Geometries();
    }
}
