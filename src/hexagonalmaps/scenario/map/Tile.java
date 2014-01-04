package hexagonalmaps.scenario.map;

import hexagonalmaps.util.RandomGenerator;

import java.util.*;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class Tile {
    private java.util.Map<TerrainType, Directions> terrain;

    private Tile() {
        this.terrain = new EnumMap<>(TerrainType.class);
    }

    public Map<TerrainType, Directions> getTerrain() {
        return terrain;
    }

    private void setTerrain(Map<TerrainType, Directions> terrain) {
        this.terrain = terrain;
    }

    public static Tile createSingleTerrainRandomTile(TerrainType terrainType, double probability) {
        if (probability > 1 || probability < 0)
            throw new IllegalArgumentException("Wrong probability parameter (should be in range [0,1])");
        Tile tile = new Tile();
        EnumMap<TerrainType, Directions> terrain = new EnumMap<>(TerrainType.class);
        if (RandomGenerator.probabilityCheck(probability)) {
            int directions = RandomGenerator.getInstance().nextInt(Directions.ALL_COMBINED_DIRECTIONS.length);
            terrain.put(terrainType, Directions.ALL_COMBINED_DIRECTIONS[directions]);
        }
        tile.setTerrain(terrain);
        return tile;
    }
}
