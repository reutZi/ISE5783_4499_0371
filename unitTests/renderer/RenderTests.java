package renderer;

import static java.awt.Color.YELLOW;

import com.google.gson.*;
import org.junit.jupiter.api.Test;
import geometries.*;
import lighting.AmbientLight;
import org.w3c.dom.*;
import primitives.*;
import scene.Scene;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

/** Test rendering a basic image
 * @author Dan */
public class RenderTests {

   /**
    * Produce a scene with basic 3D model and render it into a png image with a grid
    */
   @Test
   public void basicRenderTwoColorTest() {
      Scene scene = new Scene("Test scene")//
              .setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
                      new Double3(1, 1, 1))) //
              .setBackground(new Color(75, 127, 90));

      scene.geometries.add(new Sphere(50d, new Point(0, 0, -100)),
              new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
              // left
              new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                      new Point(-100, -100, -100)), // down
              // left
              new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
      // right
      Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
              .setVPDistance(100)
              .setVPSize(500, 500)
              .setImageWriter(new ImageWriter("base render test", 1000, 1000))
              .setRayTracer(new RayTracerBasic(scene));

      camera.renderImage();
      camera.printGrid(100, new Color(YELLOW));
      camera.writeToImage();
   }

   // For stage 6 - please disregard in stage 5
   /** Produce a scene with basic 3D model - including individual lights of the
    * bodies and render it into a png image with a grid */
   // @Test
   // public void basicRenderMultiColorTest() {
   // Scene scene = new Scene("Test scene")//
   // .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2))); //
   //
   // scene.geometries.add( //
   // new Sphere(new Point(0, 0, -100), 50),
   // // up left
   // new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new
   // Point(-100, 100, -100))
   // .setEmission(new Color(GREEN)),
   // // down left
   // new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new
   // Point(-100, -100, -100))
   // .setEmission(new Color(RED)),
   // // down right
   // new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new
   // Point(100, -100, -100))
   // .setEmission(new Color(BLUE)));
   //
   // Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1,
   // 0)) //
   // .setVPDistance(100) //
   // .setVPSize(500, 500) //
   // .setImageWriter(new ImageWriter("color render test", 1000, 1000))
   // .setRayTracer(new RayTracerBasic(scene));
   //
   // camera.renderImage();
   // camera.printGrid(100, new Color(WHITE));
   // camera.writeToImage();
   // }

   /** Test for XML based scene - for bonus */
   /**
    * @Test public void basicRenderXml() {
    * Scene scene = new Scene("XML Test scene");
    * File inputFile = new File("basicRenderTestTwoColors.xml");
    * <p>
    * try {
    * DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    * DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    * Document doc = dBuilder.parse(inputFile);
    * doc.getDocumentElement().normalize();
    * <p>
    * // Get background color
    * String[] backgroundColor = doc.getDocumentElement().getAttribute("background-color").split(" ");
    * scene.setBackground(new Color(Double.parseDouble(backgroundColor[0]), Double.parseDouble(backgroundColor[1]), Double.parseDouble(backgroundColor[2])));
    * <p>
    * // Get ambient light
    * Node ambientLightNode = doc.getElementsByTagName("ambient-light").item(0);
    * if (ambientLightNode != null) {
    * NamedNodeMap attributes = ambientLightNode.getAttributes();
    * String[] color = attributes.getNamedItem("color").getNodeValue().split(" ");
    * scene.setAmbientLight(new AmbientLight(new Color(Double.parseDouble(color[0]), Double.parseDouble(color[1]), Double.parseDouble(color[2])), 1));
    * }
    * <p>
    * // Get geometries
    * Geometries geometries = new Geometries();
    * Node geometriesNode = doc.getElementsByTagName("geometries").item(0);
    * NodeList geometryNodes = geometriesNode.getChildNodes();
    * <p>
    * for (int i = 0; i < geometryNodes.getLength(); i++) {
    * Node geometryNode = geometryNodes.item(i);
    * <p>
    * if (geometryNode.getNodeType() == Node.ELEMENT_NODE) {
    * NamedNodeMap attributes = geometryNode.getAttributes();
    * switch (geometryNode.getNodeName()) {
    * case "sphere":
    * Point center = new Point(attributes.getNamedItem("center").getNodeValue());
    * double radius = Double.parseDouble(attributes.getNamedItem("radius").getNodeValue());
    * geometries.add(new Sphere(center, radius));
    * break;
    * case "triangle":
    * Point p0 = new Point(attributes.getNamedItem("p0").getNodeValue());
    * Point p1 = new Point(attributes.getNamedItem("p1").getNodeValue());
    * Point p2 = new Point(attributes.getNamedItem("p2").getNodeValue());
    * geometries.add(new Triangle(p0, p1, p2));
    * break;
    * }
    * }
    * }
    * scene.setGeometries(geometries);
    * } catch (Exception e) {
    * throw new RuntimeException("Error parsing XML file", e);
    * }
    * <p>
    * Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
    * .setVPDistance(100)
    * .setVPSize(500, 500).setImageWriter(new ImageWriter("xml render test", 1000, 1000))
    * .setRayTracer(new RayTracerBasic(scene));
    * camera.renderImage();
    * camera.printGrid(100, new Color(YELLOW));
    * camera.writeToImage();
    * }
    **/

   /**
   @Test
   public void basicRenderXml() {
      Scene scene = new Scene("XML Test scene");

      // parse the XML file using DOM
      try {
         File inputFile = new File("basicRenderTestTwoColors.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         //dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
         //dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();

         // get the background color of the scene
         String[] backgroundColorComponents = doc.getDocumentElement().getAttribute("background-color").split(" ");
         Color bgColor = new Color(stringToColor(backgroundColorComponents).getColor());

         // get the ambient light of the scene
         Element ambientLightElement = (Element) doc.getElementsByTagName("ambient-light").item(0);
         String[] ambientLightColorComponents = ambientLightElement.getAttribute("color").split(" ");
         Color ambientColor = new Color(stringToColor(ambientLightColorComponents).getColor());
         double ka = Double.parseDouble(ambientLightElement.getAttribute("ka"));
         AmbientLight ambientLight = new AmbientLight(ambientColor, ka);

         // get the geometries of the scene
         Geometries geometries = new Geometries();
         NodeList geometriesNodes = doc.getElementsByTagName("geometries").item(0).getChildNodes();
         for (int i = 0; i < geometriesNodes.getLength(); i++) {
            Node geometryNode = geometriesNodes.item(i);
            if (geometryNode.getNodeType() == Node.ELEMENT_NODE) {
               Element geometryElement = (Element) geometryNode;
               String geometryType = geometryElement.getTagName();
               switch (geometryType) {
                  case "sphere":
                     String[] sphereCenterComponents = geometryElement.getAttribute("center").split(" ");
                     Point center = stringToPoint(sphereCenterComponents);
                     double radius = Double.parseDouble(geometryElement.getAttribute("radius"));
                     Sphere sphere = new Sphere(radius, center);
                     geometries.add(sphere);
                     break;
                  case "triangle":
                     String[] triangleP0Components = geometryElement.getAttribute("p0").split(" ");
                     String[] triangleP1Components = geometryElement.getAttribute("p1").split(" ");
                     String[] triangleP2Components = geometryElement.getAttribute("p2").split(" ");
                     Point p0 = stringToPoint(triangleP0Components);
                     Point p1 = stringToPoint(triangleP1Components);
                     Point p2 = stringToPoint(triangleP2Components);
                     Triangle triangle = new Triangle(p0, p1, p2);
                     geometries.add(triangle);
                     break;
               }
            }
         }

         scene.setBackground(bgColor)
                 .setAmbientLight(ambientLight)
                 .setGeometries(geometries);

      } catch (Exception e) {
         throw new RuntimeException("Failed to parse XML file", e);
      }

      Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))     //
              .setVPDistance(100)                                                                //
              .setVPSize(500, 500).setImageWriter(new ImageWriter("xml render test", 1000, 1000))
              .setRayTracer(new RayTracerBasic(scene));
      camera.renderImage();
      camera.printGrid(100, new Color(YELLOW));
      camera.writeToImage();
   }

   public Point stringToPoint(String[] str){
      return new Point(Double.parseDouble(str[0]),
              Double.parseDouble(str[1]),
              Double.parseDouble(str[2]));
   }

   public Color stringToColor(String[] str){
      return new Color(Double.parseDouble(str[0]),
              Double.parseDouble(str[1]),
              Double.parseDouble(str[2]));
   }**/

   @Test
   public void basicRenderXml() {
      Gson gson = new Gson();
      /**Scene scene = new Scene("Test scene")//
              .setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
                      new Double3(1, 1, 1))) //
              .setBackground(new Color(75, 127, 90));

      scene.geometries.add(new Sphere(50d, new Point(0, 0, -100)),
              new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
              // left
              new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                      new Point(-100, -100, -100)), // down
              // left
              new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down

      try {
         FileWriter writer = new FileWriter("GSON");
         gson.toJson(scene, writer);
         writer.close();

      } catch(IOException e) {
         throw new RuntimeException();
      }*/

      try {
         FileReader reader = new FileReader("GSON");
         JsonElement json = JsonParser.parseReader(reader);
         JsonObject jsonObject = json.getAsJsonObject();

         Scene scene = new Scene(jsonObject.get("name").getAsString())
                 .setBackground(gson.fromJson(jsonObject.get("background"), Color.class))
                 .setAmbientLight(gson.fromJson(jsonObject.get("ambientLight"), AmbientLight.class));

         JsonElement jsonGeometries = jsonObject.get("geometries");

         if (jsonGeometries != null) {
            JsonArray jsonGeometriesList = jsonGeometries.getAsJsonObject().get("geometriesList").getAsJsonArray();
            Geometries geometries = new Geometries();
            for (JsonElement jsonGeometry : jsonGeometriesList) {
               JsonObject jsonShape = jsonGeometry.getAsJsonObject();

               if (jsonShape.has("vertices")) {

                  JsonArray jsonVertices = jsonShape.get("vertices").getAsJsonArray();

                  Point p1 = createPoint(jsonVertices.get(0));
                  Point p2 = createPoint(jsonVertices.get(1));
                  Point p3 = createPoint(jsonVertices.get(2));

                  geometries.add(new Triangle(p1, p2, p3));

               } else if (jsonShape.has("radius")) {

                  JsonObject jsonCenter = jsonShape.get("center").getAsJsonObject();

                  Point center = createPoint(jsonCenter);
                  double radius = jsonShape.get("radius").getAsDouble();

                  geometries.add(new Sphere(radius, center));
               }
            }
            scene.setGeometries(geometries);
         }

            Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
                    .setVPDistance(100)
                    .setVPSize(500, 500)
                    .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
                    .setRayTracer(new RayTracerBasic(scene));

            camera.renderImage();
            camera.printGrid(100, new Color(YELLOW));
            camera.writeToImage();
      }
      catch(FileNotFoundException e){
         throw new RuntimeException();
      }
   }

      private static Point createPoint(JsonElement jsonPoint) {
         JsonObject jsonPointXyz = jsonPoint.getAsJsonObject().get("xyz").getAsJsonObject();
         double x = jsonPointXyz.get("d1").getAsDouble();
         double y = jsonPointXyz.get("d2").getAsDouble();
         double z = jsonPointXyz.get("d3").getAsDouble();
         return new Point(x,y,z);
      }
}
/**
   @Test
   public void basicRenderXml() {
      Scene scene = new Scene("XML Test scene");

      try{
         Gson gson = new Gson();
         JsonObject json = gson.fromJson(new FileReader("GSON"), JsonObject.class);
         JsonElement jsonBackground = json.get("background");
         JsonElement jsonAmbientLight = json.get("ambientLight");
         JsonElement jsonGeometries = json.get("geometries");

         if (jsonBackground != null) {
            JsonObject jsonBackgroundRgb = jsonBackground.getAsJsonObject().get("rgb").getAsJsonObject();
            double r = jsonBackgroundRgb.get("d1").getAsDouble();
            double g = jsonBackgroundRgb.get("d2").getAsDouble();
            double b = jsonBackgroundRgb.get("d3").getAsDouble();
            scene.setBackground(new Color(r, g, b));
         }

         if (jsonAmbientLight != null) {
            JsonObject jsonAmbientLightIntensity = jsonAmbientLight.getAsJsonObject().get("intensity").getAsJsonObject().get("rgb").getAsJsonObject();
            double r = jsonAmbientLightIntensity.get("d1").getAsDouble();
            double g = jsonAmbientLightIntensity.get("d2").getAsDouble();
            double b = jsonAmbientLightIntensity.get("d3").getAsDouble();
            scene.setAmbientLight(new AmbientLight(new Color(r, g, b), 1.0));
         }

         if (jsonGeometries != null) {
            JsonArray jsonGeometriesList = jsonGeometries.getAsJsonObject().get("geometriesList").getAsJsonArray();
            Geometries geometries = new Geometries();
            for (JsonElement jsonGeometry : jsonGeometriesList) {
               JsonObject jsonShape = jsonGeometry.getAsJsonObject();
               String type = jsonShape.get("type").getAsString();
               switch (type) {
                  case "Triangle":
                     JsonArray jsonVertices = jsonShape.get("vertices").getAsJsonArray();
                     Point3D p1 = createPoint(jsonVertices.get(0));
                     Point3D p2 = createPoint(jsonVertices.get(1));
                     Point3D p3 = createPoint(jsonVertices.get(2));
                     geometries.add(new Triangle(p1, p2, p3));
                     break;
                  case "Sphere":
                     JsonObject jsonCenter = jsonShape.get("center").getAsJsonObject();
                     Point3D center = createPoint(jsonCenter);
                     double radius = jsonShape.get("radius").getAsDouble();
                     geometries.add(new Sphere(radius, center));
                     break;
                  default:
                     throw new IllegalArgumentException("Unsupported shape type: " + type);
               }
            }
            scene.setGeometries(geometries);
         }
      }catch(FileNotFoundException e){
         throw new RuntimeException();
      }

      Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))     //
              .setVPDistance(100)                                                                //
              .setVPSize(500, 500)
              .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
              .setRayTracer(new RayTracerBasic(scene));
      camera.renderImage();
      camera.printGrid(100, new Color(YELLOW));
      camera.writeToImage();
   }

   private static Point createPoint(JsonElement jsonPoint) {
      JsonObject jsonPointXyz = jsonPoint.getAsJsonObject().get("xyz").getAsJsonObject();
      double x = jsonPointXyz.get("d1").getAsDouble();
      double y = jsonPointXyz**/


      /**scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), new Double3(1, 1, 1)));
       scene.setBackground(new Color(75, 127, 190));
       Geometries geometries = new Geometries();
       Triangle tr1 = new Triangle(new Point(-100,0, -100), new Point(0, 100, -100), new Point(-100, 100, -100));
       geometries.add(tr1);
       Triangle tr2 = new Triangle(new Point(-100,0, -100), new Point(0, -100, -100), new Point(-100, -100, -100));
       geometries.add(tr2);
       Triangle tr3 = new Triangle(new Point(100,0, -100), new Point(0, -100, -100), new Point(100, -100, -100));
       geometries.add(tr3);
       Sphere sphere = new Sphere(50, new Point(0,0,-100));
       geometries.add(sphere);
       scene.setGeometries(geometries);**/

   /** try {
    FileWriter writer = new FileWriter("GSON");
    gson.toJson(scene, writer);
    writer.close();

    } catch(IOException e) {
    throw new RuntimeException();
    }**/



