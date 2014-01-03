package hexagonalmaps.scenario.map;

import hexagonalmaps.gui.ImageProvider;
import hexagonalmaps.gui.ImageProviderFactory;
import hexagonalmaps.gui.MatrixImageProvider;
import hexagonalmaps.util.ResourcePath;

public enum TerrainType implements MovementEffects, ImageProviderFactory {
    OPEN(0),
    SAND(1),
    HILLS(2),
    MOUNTAINS(3),
    ALPINE(IMPASSABLE),
    MARSH(3),
    WATER(IMPASSABLE),
    CROPLANDS(1),
    URBAN(0),
    ROCKY(2),
    ESCARPMENT(3),
    RIVER(2),
    SUPER_RIVER(IMPASSABLE),
    FOREST(2),
    LIGHT_WOODS(1),
    ROAD(0);

    private final int movementCost;
    private final String filename;

    private TerrainType(final int movementCost) {
        this.movementCost = movementCost;
        filename = "m_terrain_" + name().toLowerCase() + ".png";
    }

    public int getMovementCost() {
        return movementCost;
    }

    /**
     * Obtains the image index in the image file, given a bitmask representing directions.<p>
     * There are 6 standard directions, so 2^6=64 combinations are possible. In addition, there is a special direction,
     * {@link hexagonalmaps.scenario.map.Direction#C} that excludes any of the other directions. That makes 65 values to represent. However, the
     * absence of any direction is not represented (it is assumed bitMask is never 0), so we actually have 64 values,
     * numbered from 1 to 64.
     *
     * @param bitMask
     * @return
     */
    public static int getImageIndex(int bitMask) {
        return bitMask - 1;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public ImageProvider createImageProvider() {
        return new MatrixImageProvider("", getFilename(), 8, 8, 408, 352);
    }
}
