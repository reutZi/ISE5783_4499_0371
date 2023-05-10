package renderer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.*;

/** The ImageWriterTest class is responsible for testing the ImageWriter class.
 * It contains a method to test the creation of a pink and black image using the ImageWriter class. */
public class ImageWriterTest {

    /**
     * Test the creation of a pink and black image using the ImageWriter class.
     * The method creates an ImageWriter object with given width and height,
     * and sets the color of each pixel in the image to black or pink based on its location.
     * The resulting image is written to a file.
     */
    @Test
    void testImageWriter() {

        // Create an ImageWriter object with given title, width, and height
        ImageWriter imageWriter = new ImageWriter("Pink and black", 800, 500 );

        // Loop through each pixel in the image and set its color to black or pink based on its location
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if (i % 50 == 0 || j % 50 == 0)
                    imageWriter.writePixel(j, i, new Color(0, 0, 0)); // set pixel to black
                else
                    imageWriter.writePixel(j, i, new Color(255, 0, 255)); // set pixel to pink
            }
        }
        // Write the resulting image to a file
        imageWriter.writeToImage();
    }
}
