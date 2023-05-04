package renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.*;

public class ImageWriterTest {
    @Test
    void pinkAndBlackImage() {

        ImageWriter imageWriter = new ImageWriter("Pink and black", 800, 500 );

        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {

                if (i % 50 == 0 || j % 50 == 0)
                    imageWriter.writePixel(j, i, new Color(0, 0, 0));
                else
                    imageWriter.writePixel(j, i, new Color(255, 0, 255));
            }
        }
        imageWriter.writeToImage();
    }

}
