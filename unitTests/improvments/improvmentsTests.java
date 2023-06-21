package improvments;

import geometries.Geometries;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.*;
import static java.awt.Color.BLUE;

public class improvmentsTests {

    private Scene scene = new Scene.SceneBuilder("Test scene").build();

    @Test
    public void antiAliasingBasicTest() {
        Scene scene = new Scene.SceneBuilder("Test scene")
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
                        new Double3(1, 1, 1))) //
                .setBackground(new Color(75, 127, 90)).build();

        scene.geometries.add(new Sphere(50d, new Point(0, 0, -100)),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
                // left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                        new Point(-100, -100, -100)), // down
                // left
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
        // right
        Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPDistance(100) //
                .setVPSize(500, 500) //
                .setImageWriter(new ImageWriter("antiAliasing basic test", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene))
                .setAntiAliasing(true).setM(9).setN(9);

        camera.renderImage();
        camera.writeToImage();
    }


    @Test
    public void heart() {
        // Camera camera = new Camera(new Point(0, -150, 800), new Vector(0, 0, -1), new Vector(0, 1, 0))
        //      .setVPSize(200, 200).setVPDistance(1000);

        //  Camera camera = new Camera(new Point(1100, -50, -250), new Vector(-1, 0, 0), new Vector(0, 0, 1))
        //.setVPSize(200, 200).setVPDistance(1000);
        //Camera camera = new Camera(new Point(10, 1200, -200), new Vector(0, -1, 0), new Vector(1 / 2, 0, 1))
        //.setVPSize(200, 200).setVPDistance(1000).setAntiAliasing(true).setN(4).setM(4);
        Camera camera = new Camera(new Point(10, 1200, -200), new Vector(0, -1, 0), new Vector(1 / 2, 0, 1))
                .setVPSize(200, 200).setVPDistance(1000).setAntiAliasing(true).setN(4).setM(4);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(

                new Sphere(12d, new Point(0, -70, -295)).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.7).setKs(0.3).setShininess(30)),
                new Sphere(12d, new Point(0, -70, -196)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(-20, -70, -283)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(20, -70, -283)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(-37, -70, -265)).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(37, -70, -265)).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(-52, -70, -246)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(52, -70, -246)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(-62, -70, -224)).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(62, -70, -224)).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(-62, -70, -200)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(62, -70, -200)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(-55, -70, -177)).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(-15, -70, -178)).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(15, -70, -178)).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(55, -70, -177)).setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(35, -70, -165)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                new Sphere(12d, new Point(-35, -70, -165)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.7).setShininess(30)),
                //מראות
                new Triangle(new Point(150, -200, -300), new Point(-270, -400, -350),
                        new Point(150, -200, 200)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.2)),
                new Triangle(new Point(-160, -150, -300), new Point(270, -400, -350),
                        new Point(-160, -150, 200)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.2)),
//
                //front
                new Polygon(
                        new Point(-23, -100, -230),
                        new Point(-23, -100, -257),
                        new Point(23, -100, -257),
                        new Point(23, -100, -230)
                ).setEmission(new Color(WHITE)),

                //Back Rectangle:
                new Polygon(
                        new Point(-28, -160, -220),
                        new Point(-28, -160, -247),
                        new Point(18, -160, -247),
                        new Point(18, -160, -220)
                ).setEmission(new Color(0, 0, 120)),

                // Top Rectangle:
                new Polygon(
                        new Point(-23, -100, -230),
                        new Point(-28, -160, -220),
                        new Point(18, -160, -220),
                        new Point(23, -100, -230)
                ).setEmission(new Color(0, 0, 90)).setMaterial(new Material().setKr(0.2)),

                //Bottom Rectangle:
                new Polygon(
                        new Point(-23, -100, -257),
                        new Point(-28, -160, -247),
                        new Point(18, -160, -247),
                        new Point(23, -100, -257)
                ).setEmission(new Color(0, 0, 50)),

                //Left Rectangle:
                new Polygon(
                        new Point(-23, -100, -230),
                        new Point(-28, -160, -220),
                        new Point(-28, -160, -247),
                        new Point(-23, -100, -257)
                ).setEmission(new Color(blue)),

                //Right Rectangle:
                new Polygon(
                        new Point(23, -100, -230),
                        new Point(18, -160, -220),
                        new Point(18, -160, -247),
                        new Point(23, -100, -257)
                ).setEmission(new Color(lightGray)),


                new Polygon( //פס תחתון
                        new Point(-23, -100, -252),
                        new Point(-23, -100, -254),
                        new Point(23, -100, -254),
                        new Point(23, -100, -252)
                ).setEmission(new Color(0, 0, 255)),
                new Polygon( //פס עליון
                        new Point(-23, -100, -233),
                        new Point(-23, -100, -235),
                        new Point(23, -100, -235),
                        new Point(23, -100, -233)
                ).setEmission(new Color(0, 0, 255)),
                //מגן דוד
                new Triangle(new Point(0, -95, -236), new Point(-7, -95, -248), new Point(7, -95, -248))
                        .setEmission(new Color(BLUE)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //משולש ישר
                new Triangle(new Point(0, -95, -252), new Point(-7, -95, -240), new Point(7, -95, -240))
                        .setEmission(new Color(BLUE)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),//משולש הפוך
                // משטח
                new Polygon(
                        new Point(-150, -150, -310),
                        new Point(150, -150, -310),
                        new Point(150, 70, -325),
                        new Point(-150, 70, -325)
                ).setEmission(new Color(100, 100, 100)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(0)),
                new Polygon( //פס
                        new Point(-15, -130, -223),
                        new Point(-15, -130, -225),
                        new Point(10, -130, -225),
                        new Point(10, -130, -223)
                ).setEmission(new Color(255, 255, 255))
                //new Triangle(new Point(-150, -150, -305), new Point(150, -150, -305),
                //      new Point(75, 75, -305)) //
                //  .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                //new Triangle(new Point(-150, -150, -305), new Point(-70, 70, -305), new Point(75, 75, -305)) //
                //      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))


        );

        scene.lights.add(new SpotLight(new Color(0, 0, 179), new Point(0, 100, 100), new Vector(0, 0, -4))
                .setKl(2E-5).setKq(1E-7));
        scene.lights.add(new SpotLight(new Color(245, 0, 179), new Point(75, 20, 100), new Vector(0, 0, -2))
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add( //
                new SpotLight(new Color(245, 222, 179), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setKl(4E-4).setKq(2E-6));


        ImageWriter imageWriter = new ImageWriter("heart", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }
}
