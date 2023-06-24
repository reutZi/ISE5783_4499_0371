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
    public void MP2() {
//        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
//                .setVPSize(200, 200).setVPDistance(500);
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(400);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.geometries.add(
                new Polygon( //floor
                        new Point(-200, -50, -100),
                        new Point(200, -50, -100),
                        new Point(200, -100, 320),
                        new Point(-200, -100, 320)
                ).setEmission(new Color(100, 100, 100))
                        .setMaterial(new Material()
                                .setKd(0.5)
                                .setKs(0.5)
                                .setShininess(0)),
                new Polygon( //back wall
                        new Point(-200, -90, -100),
                        new Point(200, -90, -100),
                        new Point(200, 200, -100),
                        new Point(-200, 200, -100)
                ).setEmission(new Color(100, 0, 0))
                        .setMaterial(new Material()
                                .setKd(0.5)
                                .setKs(0.5)
                                .setShininess(0)),
                //new Cuboid(new Point(-100, -10, -70), 100, 20, 30, new Color(BLUE)),
                new Polygon(//frame
                        new Point(-140, 90, -100),
                        new Point(-70, 90, -100),
                        new Point(-70, 140, -100),
                        new Point(-140, 140, -100)
                ).setEmission(new Color(0, 0, 0)),
                new Polygon(//white picture
                        new Point(-136, 94, -100),
                        new Point(-74, 94, -100),
                        new Point(-74, 136, -100),
                        new Point(-136, 136, -100)
                ).setEmission(new Color(255, 255, 255)),
                new Polygon(//frame of mirror
                        new Point(50, -70, -80),
                        new Point(150, -70, -80),
                        new Point(150, 140, -100),
                        new Point(50, 140, -100)
                ).setEmission(new Color(20, 20, 20)),
                new Polygon(//mirror
                        new Point(55, -55, -77),
                        new Point(145, -55, -77),
                        new Point(145, 135, -97),
                        new Point(55, 135, -97)).setEmission(new Color(130,130,130)).setMaterial(new Material().setKr(0.7)),
                new Cuboid(new Point(-180, 100, -80), 5, 160, 5, new Color(BLACK)),//stick of lamp
                new Sphere(20d, new Point(-177, 120, -80))  //lamp
                        .setMaterial((new Material()
                                .setKd(0.5)
                                .setKs(0.2)
                                .setKt(0.7))),

                new Polygon( //bed
                        new Point(-146, 10, -50),
                        new Point(14, 10, -50),
                        new Point(5, -30, 80),
                        new Point(-155, -30, 80)
                ).setEmission(new Color(0, 0, 200)),
//                        .setMaterial((new Material()
//                                .setKd(0.5)
//                                .setKs(0.5)
//                                .setKr(0.5)
//                                .setShininess(30))),
                // new Cuboid(new Point(-155, -27, 65),110,3, 50,new Color(WHITE)),//כרית

                //top left bed leg
                new Cuboid(new Point(-155, 10, -40), 10, 80, 10),
                //top right bed leg
                new Cuboid(new Point(4, 10, -40), 10, 80, 10),
                //bottom right bed leg
                new Cuboid(new Point(-5, -30, 70), 10, 80, 10),
                //bottom left bed leg
                new Cuboid(new Point(-155, -30, 70), 10, 80, 10),

                new Polygon(new Point(-155, -29.5, 80),
                        new Point(-130, -29.5, 80),
                        new Point(-130, 10.5, -50),
                        new Point(-155, 10.5, -50)),

                new Polygon(new Point(-130, -30, 50),
                        new Point(-110, -30, 50),
                        new Point(-110, 10, -20),
                        new Point(-130, 10, -20)).setEmission(new Color(WHITE)),
                new Sphere(35d, new Point(100, -20, 80))//body
                        .setEmission(new Color(150, 75, 0))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.7)
                                .setShininess(30)),
                new Sphere(25d, new Point(100, 33, 80))//head
                        .setEmission(new Color(150, 75, 0))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.7)
                                .setShininess(30)),
                new Sphere(17d, new Point(70, -16, 80))//left arm
                        .setEmission(new Color(92, 64, 51))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.7)
                                .setShininess(30)),
                new Sphere(17d, new Point(130, -16, 80))//right arm
                        .setEmission(new Color(92, 64, 51))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.7)
                                .setShininess(30)),
                new Sphere(18d, new Point(80, -55, 96))//left leg
                        .setEmission(new Color(92, 64, 51))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0)
                                .setShininess(30)),
                new Sphere(18d, new Point(120, -55, 96))//right leg
                        .setEmission(new Color(92, 64, 51))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0)
                                .setShininess(30)),
                new Sphere(12d, new Point(83, 55, 80))//left ear
                        .setEmission(new Color(92, 64, 51))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.7)
                                .setShininess(30)),
                new Sphere(12d, new Point(117, 55, 80))//right ear
                        .setEmission(new Color(92, 64, 51))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.7)
                                .setShininess(30)),
                new Sphere(5d, new Point(110, 40, 100))//right eye
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.7)
                                .setShininess(30)),
                new Sphere(5d, new Point(90, 40, 100))//left eye
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.7)
                                .setShininess(30)),
                new Sphere(7d, new Point(100, 25, 120))//nose
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.7)
                                .setShininess(30)),
                new Sphere(3d, new Point(100, 20, 120))//mouse
                        .setEmission(new Color(255,0,0))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.7)
                                .setShininess(30)),








        new Sphere(12d, new Point(40, -75, 200))
                .setEmission(new Color(RED))
                .setMaterial(new Material()
                        .setKd(0.2)
                        .setKs(0.7)
                        .setShininess(30))

        //new Cuboid(new Point(-125, 0, 70),10,10,200, new Color(255,255,255))


        );


        scene.lights.add(new SpotLight(new Color(245, 222, 179), new Point(75, 20, 200), new Vector(0, -1, -4))
              .setKl(4E-4).setKq(2E-5));
        //scene.lights.add(new SpotLight(new Color(0, 255,0), new Point(0,30,700), new Vector(0, -0.5,-1))
        //      .setKl(4E-4).setKq(2E-5));
        scene.lights.add(new PointLight(new Color(WHITE), new Point(-177, 120, -80)).setKl(0.001).setKq(0.00015));


        ImageWriter imageWriter = new ImageWriter("newMP2", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

}
