package renderer;

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

import static java.awt.Color.*;
import static java.awt.Color.WHITE;

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
      Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
              .setVPDistance(100) //
              .setVPSize(500, 500) //
              .setImageWriter(new ImageWriter("base render test", 1000, 1000))
              .setRayTracer(new RayTracerBasic(scene));

      camera.renderImage();
      camera.printGrid(100, new Color(YELLOW));
      camera.writeToImage();
   }

   /** Produce a scene with basic 3D model - including individual lights of the
    * bodies and render it into a png image with a grid */
   @Test
   public void basicRenderMultiColorTest() {
      Scene scene = new Scene("Test scene")//
              .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2))); //

      scene.geometries.add( // center
              new Sphere(50, new Point(0, 0, -100)),
              // up left
              new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100))
                      .setEmission(new Color(GREEN)),
              // down left
              new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100))
                      .setEmission(new Color(RED)),
              // down right
              new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))
                      .setEmission(new Color(BLUE)));

      Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
              .setVPDistance(100) //
              .setVPSize(500, 500) //
              .setImageWriter(new ImageWriter("color render test", 1000, 1000))
              .setRayTracer(new RayTracerBasic(scene));

      camera.renderImage();
      camera.printGrid(100, new Color(WHITE));
      camera.writeToImage();
   }

   /** Tests the rendering of a scene from a JSON file using Gson library. */
   @Test
   public void basicRenderJson() {

      // Create a new Gson instance
      Gson gson = new Gson();

      try {
         // Read the JSON file using a FileReader
         FileReader reader = new FileReader("GSON");
         JsonElement json = JsonParser.parseReader(reader);
         JsonObject jsonObject = json.getAsJsonObject();

         // Create a new Scene object and set its background and ambient light properties
         Scene scene = new Scene(jsonObject.get("name").getAsString())
                 .setBackground(gson.fromJson(jsonObject.get("background"), Color.class))
                 .setAmbientLight(gson.fromJson(jsonObject.get("ambientLight"), AmbientLight.class));

         // Get the geometries section of the JSON file, if it exists
         JsonElement jsonGeometries = jsonObject.get("geometries");

         if (jsonGeometries != null) {
            // If the geometries section exists, get the list of geometries
            JsonArray jsonGeometriesList = jsonGeometries.getAsJsonObject().get("geometriesList").getAsJsonArray();
            Geometries geometries = new Geometries();

            // Iterate over each geometry object in the list
            for (JsonElement jsonGeometry : jsonGeometriesList) {
               JsonObject jsonShape = jsonGeometry.getAsJsonObject();

               // If the geometry is a triangle, create a new Triangle object and add it to the Geometries list
               if (jsonShape.has("vertices")) {

                  JsonArray jsonVertices = jsonShape.get("vertices").getAsJsonArray();

                  Point p1 = createPoint(jsonVertices.get(0));
                  Point p2 = createPoint(jsonVertices.get(1));
                  Point p3 = createPoint(jsonVertices.get(2));

                  geometries.add(new Triangle(p1, p2, p3));

                  // If the geometry is a sphere, create a new Sphere object and add it to the Geometries list
               } else if (jsonShape.has("radius")) {

                  JsonObject jsonCenter = jsonShape.get("center").getAsJsonObject();

                  Point center = createPoint(jsonCenter);
                  double radius = jsonShape.get("radius").getAsDouble();

                  geometries.add(new Sphere(radius, center));
               }
            }
            // Set the Scene's Geometries property to the Geometries list
            scene.setGeometries(geometries);
         }

         // Create a new Camera object with the given parameters, render the image, print the grid, and write to an image file
            Camera camera = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
                    .setVPDistance(100)
                    .setVPSize(500, 500)
                    .setImageWriter(new ImageWriter("json render test", 1000, 1000))
                    .setRayTracer(new RayTracerBasic(scene));

            camera.renderImage();
            camera.printGrid(100, new Color(YELLOW));
            camera.writeToImage();
      }
      // Catch a FileNotFoundException and throw a RuntimeException
      catch(FileNotFoundException e){
         throw new RuntimeException();
      }
   }

   /**
    * Creates a new Point object from a JsonElement that represents a point in 3D space.
    * @param jsonPoint the JsonElement that represents the point in JSON format
    * @return a new Point object with the x, y, and z coordinates from the JsonElement
    */
      private static Point createPoint(JsonElement jsonPoint) {

         JsonObject jsonPointXyz = jsonPoint.getAsJsonObject().get("xyz").getAsJsonObject();

         double x = jsonPointXyz.get("d1").getAsDouble();
         double y = jsonPointXyz.get("d2").getAsDouble();
         double z = jsonPointXyz.get("d3").getAsDouble();

         return new Point(x,y,z);
      }
}




