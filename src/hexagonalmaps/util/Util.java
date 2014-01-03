package hexagonalmaps.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class Util {
    private static final Logger LOG = Logger.getLogger(Util.class.getName());

    public static boolean isEven(int number) {
        return (number & 1) == 0;
    }

    public static boolean isOdd(int number) {
        return (number & 1) == 1;
    }


    /**
     * Loads an image from file
     *
     * @param file
     * @return the image
     */
    public static BufferedImage loadImage(File file) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error loading " + file.getPath(), ex);
        }
        return bi;
    }
}
