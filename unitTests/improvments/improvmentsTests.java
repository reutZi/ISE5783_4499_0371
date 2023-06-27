package improvments;

import geometries.*;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;


import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;

public class improvmentsTests {

    private Scene scene = new Scene.SceneBuilder("Test scene").build();

    @Test
    public void antiAliasingBasicTest() {
        Scene scene = new Scene.SceneBuilder("Test scene")
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
                        new Double3(1, 1, 1))) //
                .setBackground(new Color(BLACK)).build();

        scene.geometries.add(new Sphere(50d, new Point(0, 0, -100)));
        // right
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) //
                .setVPSize(500, 500) //
                .setImageWriter(new ImageWriter("antiAliasing basic test", 500, 500))
                .setRayTracer(new RayTracerBasic(scene))
                .setAntiAliasingFactor(10);

        camera.renderImage();
        camera.writeToImage();
    }

    @Test
    public void withoutAntiAliasingBasicTest() {
        Scene scene = new Scene.SceneBuilder("Test scene")
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
                        new Double3(1, 1, 1))) //
                .setBackground(new Color(BLACK)).build();

        scene.geometries.add(new Sphere(50d, new Point(0, 0, -100)));
        // right
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) //
                .setVPSize(500, 500) //
                .setImageWriter(new ImageWriter("basic test without Antialiasing", 500, 500))
                .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.writeToImage();
    }
}