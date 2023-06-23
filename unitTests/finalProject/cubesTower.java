package finalProject;

import geometries.Cuboid;
import lighting.LightSource;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static java.awt.Color.*;

public class cubesTower {

    @Test
    public void cubesTower(){


    }


    Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPSize(250, 250).setVPDistance(1000);

    @Test
    public void cuber() {
        List<LightSource> lights = new LinkedList<>();
        lights.add(new SpotLight(new Color(YELLOW), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));
        Camera camera1 = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPDistance(100) //
                .setVPSize(500, 500);
        Scene scene = new Scene.SceneBuilder("Test scene")
                .setGeometries(new Cuboid(new Point(0,0,0),50,50,50,new Color(PINK)))
                .setBackground(new Color(WHITE))
                .setLights(lights).build();
        camera1.setRayTracer(new RayTracerBasic(scene))
                .setImageWriter(new ImageWriter("cuboid", 1000, 1000))
                .renderImage()
                .writeToImage();
    }
}
