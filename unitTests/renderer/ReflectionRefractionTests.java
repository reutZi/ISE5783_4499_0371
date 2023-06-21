/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.*;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene.SceneBuilder("Test scene").build();

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.geometries.add( //
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }


    /** Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow */
    /** Produce a picture of a triangle and three spheres */
    /** Produce a picture of a triangle and three spheres */
    /** Produce a picture of a triangle and three spheres */
    /** Produce a picture of an ice cream cone with three scoops */
    /** Produce a picture of a triangle and three spheres resembling an ice cream cone */
    /**
     * Produce a picture of a triangle and three spheres
     */
    @Test
    public void ourTest() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add(
                new Triangle(new Point(50, 20, -10), new Point(0, -100, -50), new Point(-50, 20, -10)).setEmission(new Color(205, 133, 63))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Triangle(new Point(50, 20, -10), new Point(0, -100, -50), new Point(0, 20, -90)).setEmission(new Color(205, 133, 63))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Triangle(new Point(0, 20, -90), new Point(0, -100, -50), new Point(-50, 20, -10)).setEmission(new Color(205, 133, 63))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),

                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //

                new Polygon(
                        new Point(35, 37, -85),
                        new Point(70, 77, -85),
                        new Point(66, 92, -85),
                        new Point(35, 57, -85)
                ).setEmission(new Color(yellow)), //
                new Sphere(31d, new Point(0, 70, -100)).setEmission(new Color(PINK)) // Yellow sphere
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Sphere(33d, new Point(-27, 40, -100)).setEmission(new Color(255, 0, 0)) // Red sphere
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Sphere(33d, new Point(27, 40, -100)).setEmission(new Color(127, 63, 0)) // Green sphere
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30))
        );

        scene.lights.add(new SpotLight(new Color(245, 222, 179), new Point(75, 20, 100), new Vector(0, 0, -4))
                .setKl(4E-5).setKq(2E-7));


        ImageWriter imageWriter = new ImageWriter("ourTest", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
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

    @Test
    public void basicRenderTwoColorTest() {
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
                .setImageWriter(new ImageWriter("base render test antialiasing", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene));

        camera.renderImage();
        camera.writeToImage();
    }


    @Test
    public void antiAliasingBasicTest() {
        Scene scene = new Scene.SceneBuilder("Test Scene")
                .setAmbientLight(new AmbientLight(new Color(black), new Double3(1)))
                .setGeometries(new Geometries( //
                        new Sphere(250d, new Point(0, 0, -50)).setEmission(new Color(yellow))))

                .build();
        ImageWriter imageWriter = new ImageWriter("advancedBeamTestRandom", 400, 400);

        Camera camera1 = new Camera(new Point(0, 0, 500), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPDistance(75).setVPSize(200, 200)
                .setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .setAntiAliasing(true).setN(4).setM(4);

        camera1.renderImage();
        camera1.writeToImage();
    }
}
