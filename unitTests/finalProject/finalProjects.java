package finalProject;

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

public class finalProjects {
    private Scene scene = new Scene.SceneBuilder("Test scene").build();

    @Test
    public void heart() {
        // Camera camera = new Camera(new Point(0, -150, 800), new Vector(0, 0, -1), new Vector(0, 1, 0))
        //      .setVPSize(200, 200).setVPDistance(1000);

        //  Camera camera = new Camera(new Point(1100, -50, -250), new Vector(-1, 0, 0), new Vector(0, 0, 1))
        //.setVPSize(200, 200).setVPDistance(1000);
        //Camera camera = new Camera(new Point(10, 1200, -200), new Vector(0, -1, 0), new Vector(1 / 2, 0, 1))
        //.setVPSize(200, 200).setVPDistance(1000).setAntiAliasing(true).setN(4).setM(4);
        Camera camera = new Camera(new Point(10, 1200, -200), new Vector(0, -1, 0), new Vector(1 / 2, 0, 1))
                .setVPSize(200, 200).setVPDistance(1000);

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
     //   Camera camera = new Camera(new Point(0, 1000, 50), new Vector(0, -1,0), new Vector(0, 0, -1))
     //      .setVPSize(200, 200).setVPDistance(550); //מלמעלה
        //Camera camera = new Camera(new Point(0, 20, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
      //    .setVPSize(200, 200).setVPDistance(550);//.setUseAdaptive(true);//ממול
        //  Camera camera = new Camera(new Point(-700, 10, 20), new Vector(1, 0, 0), new Vector(0, 1, 0))
      //      .setVPSize(200, 200).setVPDistance(400); //מהצד
        Camera camera = new Camera(new Point(-450, 20, 1200), new Vector(0.3, 0, -0.75), new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(500);//.setUseAdaptive(true);//באלכסון

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.geometries.add(
                new Polygon( //floor
                        new Point(-200, -70, -100),
                        new Point(200, -70, -100),
                        new Point(200, -70, 600),
                        new Point(-200, -70, 600)
                ).setEmission(new Color(100, 100, 100))
                        .setMaterial(new Material()
                                .setKd(0.5)
                                .setKs(0.5)
                                .setShininess(0)),
                new Polygon( //back wall
                        new Point(-200, -70, -100.2),
                        new Point(200, -70, -100.2),
                        new Point(200, 220, -100.2),
                        new Point(-200, 220, -100.2)
                ).setEmission(new Color(100, 0, 0))
                        .setMaterial(new Material()
                                .setKd(0.5)
                                .setKs(0.5)
                                .setShininess(0)),
                new Polygon(//white picture
                        new Point(-100, 80, -99.9),
                        new Point(-10, 80, -99.9),
                        new Point(-10, 145, -99.9),
                        new Point(-100, 145, -99.9)
                ).setEmission(new Color(WHITE)),
                new Polygon(//light blue picture
                        new Point(-60, 100, -99.9),
                        new Point(-10, 100, -99.9),
                        new Point(-10, 145, -99.9),
                        new Point(-100, 145, -99.9)
                ).setEmission(new Color(100,149,237)),
                new Triangle(//light purple picture
                        new Point(-10, 80, -99.8),
                        new Point(-10, 145, -99.8),
                        new Point(-50, 145, -99.8)
                ).setEmission(new Color(185,140,185)),
                new Triangle(//light green picture
                        new Point(-90, 80, -99.7),
                        new Point(-50, 80, -99.7),
                        new Point(-70, 100, -99.7)
                ).setEmission(new Color(0,128,128)),
                new Polygon(//yellow picture
                        new Point(-100, 80, -99.8),
                        new Point(-80, 80, -99.8),
                        new Point(-80, 145, -99.8),
                        new Point(-100, 145, -99.8)
                ).setEmission(new Color(245,255, 129)),
                new Polygon(//frame
                        new Point(-105, 75, -100.1),
                        new Point(-5, 75, -100.1),
                        new Point(-5, 150, -100.1),
                        new Point(-105, 150, -100.1)
                ).setEmission(new Color(GRAY)),

                new Cuboid(new Point(-120, 13, -15),150,80, 80,new Color(92, 64, 51)),// counter

                new Polygon(//right door in the counter
                        new Point(-10, 8, -15),
                        new Point(27, 8, -15),
                        new Point(27, -63, -15),
                        new Point(-10, -63, -15)
                ).setEmission(new Color(49.8, 24.71, 0)),

                new Polygon(//left door in the counter
                        new Point(-11, 8, -15),
                        new Point(-48, 8, -15),
                        new Point(-48, -63, -15),
                        new Point(-11, -63, -15)
                ).setEmission(new Color(49.8, 24.71, 0)),

                new Sphere(2d,new Point(-5, 3, -13)).setEmission(new Color(92, 64, 51)),
                new Sphere(2d,new Point(-16, 3, -13)).setEmission(new Color(92, 64, 51)),
                new Sphere(2d,new Point(-85, 3, -13)).setEmission(new Color(92, 64, 51)),
                new Sphere(2d,new Point(-85, -22, -13)).setEmission(new Color(92, 64, 51)),
                new Sphere(2d,new Point(-85, -47, -13)).setEmission(new Color(92, 64, 51)),


                new Polygon(//drawer
                        new Point(-115, 8, -15),
                        new Point(-52, 8, -15),
                        new Point(-52, -12, -15),
                        new Point(-115, -12, -15)
                ).setEmission(new Color(49.8, 24.71, 0)),

                new Polygon(//drawer
                        new Point(-115, -37, -15),
                        new Point(-52, -37, -15),
                        new Point(-52, -17, -15),
                        new Point(-115, -17, -15)
                ).setEmission(new Color(49.8, 24.71, 0)),

                new Polygon(//drawer
                        new Point(-115, -62, -15),
                        new Point(-52, -62, -15),
                        new Point(-52, -42, -15),
                        new Point(-115, -42, -15)
                ).setEmission(new Color(49.8, 24.71, 0)),

                new Sphere(3d, new Point(27, -67, -18))//leg of counter
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30)),
                new Sphere(3d, new Point(27, -67, -93))//leg of counter
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30)),
                new Sphere(3d, new Point(-117, -67, -93))//leg of counter
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30)),
                new Sphere(3d, new Point(-117, -67, -18))//leg of counter
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30)),
                new Polygon(//frame of mirror
                        new Point(75, -40, -98),
                        new Point(150, -40, -98),
                        new Point(150, 90, -98),
                        new Point(75, 90, -98)
                ).setEmission(new Color(20, 20, 20)),

                new Polygon(//mirror
                        new Point(80, -35, -97),
                        new Point(145, -35, -97),
                        new Point(145, 85, -97),
                        new Point(80, 85, -97))
                        .setEmission(new Color(140,140,140))
                        .setMaterial(new Material()
                                .setKr(0.7)),

                new Cuboid(new Point(-180, 100, -80), 5, 160, 5, new Color(BLACK)),//stick of lamp

                new Cuboid(new Point(-193, -60, -70), 30, 20, 30, new Color(BLACK)),

                new Sphere(20d, new Point(-177, 120, -80))  //lamp
                        .setMaterial((new Material()
                                .setKd(0.5)
                                .setKs(0.2)
                                .setKt(0.7))),

                //cuboids
                new Cuboid(new Point(-90, -60, 130), 10, 10, 10, new Color(0, 255, 100)),
                new Cuboid(new Point(-100, -60, 130), 10, 10, 10, new Color(255, 0,255)),
                new Cuboid(new Point(-112, -60, 130), 10, 10, 10, new Color(100,100,200)),
                new Cuboid(new Point(-93, -49, 130), 10, 10, 10, new Color(PINK)),
                new Cuboid(new Point(-105, -49, 130), 10, 10, 10, new Color(ORANGE)),
                new Cuboid(new Point(-50, -60, 150), 10, 10, 10, new Color(BLUE)),

                new Sphere(12d, new Point(40, -58, 200))//red ball
                        .setEmission(new Color(RED))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.7)
                                .setShininess(30)),
                new Sphere(8d, new Point(90, -62, 120))//pink ball
                        .setEmission(new Color(PINK))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.7)
                                .setShininess(30)),
                new Sphere(8d, new Point(-20, -62, 90))//yellow ball
                        .setEmission(new Color(YELLOW))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.7)
                                .setShininess(30)),

                new Cuboid(new Point(100, -50, 30), 60, 20, 50, new Color(120,120,120)),//little chair

                new Sphere(20, new Point(130, -28, 20))//body
                        .setEmission(new Color(150, 75, 0))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(90)),
                new Sphere(15d, new Point(130, 2, 22))//head
                        .setEmission(new Color(150, 75, 0))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(90)),
                new Sphere(7d, new Point(113, -18, 23))//left arm
                        .setEmission(new Color(92, 64, 51))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30)),
                new Sphere(7d, new Point(146, -18.3, 24))//right arm
                        .setEmission(new Color(92, 64, 51))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30)),
                new Sphere(7d, new Point(120, -45, 30))//left leg
                        .setEmission(new Color(92, 64, 51))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30)),
                new Sphere(7d, new Point(140, -45, 30))//right leg
                        .setEmission(new Color(92, 64, 51))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30)),
                new Sphere(6d, new Point(120, 12, 25))//left ear
                        .setEmission(new Color(92, 64, 51))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30)),
                new Sphere(6d, new Point(139, 12, 25))//right ear
                        .setEmission(new Color(92, 64, 51))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30)),
                new Sphere(3d, new Point(133, 6, 37))//right eye
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30)),
                new Sphere(3d, new Point(123, 6, 37))//left eye
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30)),
                new Sphere(3.5d, new Point(128, -3, 37))//nose
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30)),
                new Sphere(3d, new Point(128, -4, 37))//mouse
                        .setEmission(new Color(255,0,0))
                        .setMaterial(new Material()
                                .setKd(0.2)
                                .setKs(0.3)
                                .setShininess(30))
        );


        scene.lights.add(new SpotLight(new Color(245, 222, 179), new Point(75, 20, 200), new Vector(0, -1, -4))
                .setKl(4E-4).setKq(2E-5));
        scene.lights.add(new SpotLight(new Color(0,200,20), new Point(200,100, 200), new Vector(0, -1,-1))
                .setKl(4E-4).setKq(2E-6));
        scene.lights.add(new SpotLight(new Color(10, 222,20), new Point(-150, 200, 300), new Vector(0.5, -0.75,-1))
               .setKl(4E-4).setKq(2E-5));

        scene.lights.add(new PointLight(new Color(WHITE), new Point(-177, 120, -80)).setKl(0.001).setKq(0.00015));


        ImageWriter imageWriter = new ImageWriter("final project diagonally", 3000, 3000);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .setMultiThreading(3)
                .writeToImage();
    }
}
