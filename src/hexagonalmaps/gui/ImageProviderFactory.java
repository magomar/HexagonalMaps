package hexagonalmaps.gui;

/**
 * Created by mario on 3/01/14.
 */
public interface ImageProviderFactory {
    /**
     * @return the filename.
     */
    String getFilename();

    /**
     * @return an image provider
     */
    ImageProvider createImageProvider();
}
