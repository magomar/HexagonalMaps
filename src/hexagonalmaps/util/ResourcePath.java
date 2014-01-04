package hexagonalmaps.util;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public enum ResourcePath {

    GRAPHICS("resources/graphics");
    private final String relativePath;
    private final Path folderPath;

    private ResourcePath(final String relativePath) {
        this.relativePath = relativePath;
        this.folderPath = FileSystems.getDefault().getPath(System.getProperty("user.dir"), relativePath);
    }

    public Path getFolderPath() {
        return folderPath;
    }

    public Path getFilePath(String filename) {
        return FileSystems.getDefault().getPath(folderPath.toString(), filename);
    }

    public File getFile(String filename) {
        return getFilePath(filename).toFile();
    }

    public String getFilename(String filename) {
        return getFilePath(filename).toString();
    }

    public String getRelativePath() {
        return relativePath;
    }

    public Path getSubPath(String... subPath) {
        return FileSystems.getDefault().getPath(folderPath.toString(), subPath);
    }
}