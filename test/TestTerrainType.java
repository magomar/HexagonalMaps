import hexagonalmaps.scenario.map.TerrainType;
import org.junit.Test;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class TestTerrainType {
    @Test
    public void testEnumManipulation() throws Exception {
        System.out.println("*** Testing enum manipulation ***\n");
        System.out.println("Listing all enum values...");
        TerrainType[] allTerrainTypes = TerrainType.values();
        for (TerrainType tt : allTerrainTypes) {
            System.out.println(tt.ordinal() + ": " + tt.name());
        }
        System.out.println("Basic usage of enums...");
        TerrainType t1 = TerrainType.FOREST;
        TerrainType t2 = TerrainType.valueOf("FOREST");
        TerrainType t3 = allTerrainTypes[13];
        TerrainType t4 = Enum.valueOf(TerrainType.class, "RIVER");
        assert t1 == t2;
        assert t1.equals(t2);
        assert t1 == t3;
        assert t1 != t4;

        switch (t1) {
            case FOREST:
                assert t1 == TerrainType.FOREST;
                break;
        }
        assert t1.compareTo(t4) == 2;
        try {
            assert TerrainType.valueOf("Forest") == t1;
        } catch (IllegalArgumentException e) {
            System.out.println("Catched exception: " + e.getMessage());
        }
    }

    @Test
    public void testEnumSet() throws Exception {
        System.out.println("\n*** Testing enum sets manipulation ***\n");
        System.out.println("Creating enum sets...");
        Set<TerrainType> anyTerrain = EnumSet.allOf(TerrainType.class);
        Set<TerrainType> emptyTerrain = EnumSet.noneOf(TerrainType.class);
        Set<TerrainType> stream = EnumSet.of(TerrainType.RIVER, TerrainType.SUPER_RIVER);
        Set<TerrainType> woods = EnumSet.of(TerrainType.FOREST, TerrainType.LIGHT_WOODS);
        Set<TerrainType> water = EnumSet.of(TerrainType.WATER);
        Set<TerrainType> land = EnumSet.complementOf((EnumSet<TerrainType>) water);
        Set<TerrainType> anyMountain = EnumSet.range(TerrainType.HILLS, TerrainType.ALPINE);
        System.out.println("stream = " + stream);
        System.out.println("woods = " + woods);
        System.out.println("water = " + water);
        System.out.println("land = " + land);
        System.out.println("anyMountain = " + anyMountain);
        System.out.println();
        System.out.println("Basic Set operations...");
        assert anyTerrain.contains(TerrainType.MARSH);
        assert !woods.contains(TerrainType.MARSH);
        assert land.containsAll(woods);
        assert !woods.containsAll(stream);
        System.out.println("Union of enum set...");
        Set<TerrainType> terrainSet = EnumSet.copyOf(land);
        terrainSet.addAll(water);
        assert terrainSet.equals(anyTerrain);
        assert !terrainSet.equals(land);
        System.out.println("Intersection of enum set...");
        terrainSet.remove(TerrainType.LIGHT_WOODS);
        terrainSet.retainAll(woods);
        assert terrainSet.contains(TerrainType.FOREST);
        assert !terrainSet.contains(TerrainType.LIGHT_WOODS);
    }

    @Test
    public void testEnumMap() throws Exception {
        System.out.println("\n*** Testing enum maps manipulation ***\n");
        System.out.println("Creating enum maps...");
        Map<TerrainType, String> enumMap = new EnumMap<TerrainType, String>(TerrainType.class);
        Set<TerrainType> woods = EnumSet.of(TerrainType.FOREST, TerrainType.LIGHT_WOODS);
        for (TerrainType terrainType : woods) {
            enumMap.put(terrainType, terrainType.name().toLowerCase() + ".png");
        }
        System.out.println(enumMap);
        for (Map.Entry<TerrainType, String> entry : enumMap.entrySet()) {
            assert entry.getValue().equals(entry.getKey().name().toLowerCase() + ".png");
        }
    }

}
