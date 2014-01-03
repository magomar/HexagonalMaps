package hexagonalmaps.gui;

import hexagonalmaps.scenario.map.*;
import hexagonalmaps.util.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class HexagonalMap extends JPanel {
    private int width; // Number of columns
    private int height; // Number of rows
    private int hexSide; // Side of the hexagon
    private int hexOffset; // Distance from left horizontal vertex to vertical axis
    private int hexApotheme; // Apotheme of the hexagon = radius of inscribed circumference
    private int hexRectWidth; // Width of the circumscribed rectangle
    private int hexRectHeight; // Height of the circumscribed rectangle
    private int hexGridWidth;  // hexOffset + hexSide (b + s)
    private BufferedImage globalImage;
    private Board board;
    private Map<TerrainType, ImageProvider> terrainImageProvider;


    /**
     * Create new hexagonal map given a board
     *
     * @param board
     */
    public HexagonalMap(Board board) {
        this.board = board;
        this.width = board.getWidth();
        this.height = board.getHeight();

        terrainImageProvider = new EnumMap<>(TerrainType.class);
        for (TerrainType tt : TerrainType.values()) {
            terrainImageProvider.put(tt, tt.createImageProvider());
        }

        ImageProvider someImageProvider = terrainImageProvider.get(TerrainType.FOREST);
        BufferedImage someTerrainImage = someImageProvider.getImage(0);
        hexRectWidth = someTerrainImage.getWidth();
        hexRectHeight = someTerrainImage.getHeight();
        hexApotheme = hexRectHeight / 2;
        hexSide = (int) ((double) hexApotheme / Math.cos(Math.PI / 6));
        hexOffset = (int) (hexSide * Math.sin(Math.PI / 6));
        hexGridWidth = hexOffset + hexSide;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (globalImage == null)
            globalImage = new BufferedImage(width * hexGridWidth + hexOffset, height * hexRectHeight + hexApotheme, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = globalImage.createGraphics();
        // Paint it black!
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, globalImage.getWidth(), globalImage.getHeight());
        Tile[][] tiles = board.getTiles();
        for (int i = 0; i < width; i++) {
            Tile[] tileColumn = tiles[i];
            for (int j = 0; j < height; j++) {
                g.drawPolygon(buildHexagon(i,j));
                paintTile(g2, i, j, tileColumn[j]);
            }
        }
        g2.dispose();
        ((Graphics2D) g).drawImage(globalImage, 0, 0, null);
//        repaint();
    }

    void paintTile(Graphics2D g2, int column, int row, Tile tile) {
        //Obtain tile position
        Point pos = tileToPixel(column, row);
        // First paint open terrain for the background
        BufferedImage terrainImage = terrainImageProvider.get(TerrainType.OPEN).getImage(0);
        g2.drawImage(terrainImage, pos.x, pos.y, null);
        Map<TerrainType, Directions> m = tile.getTerrain();
        for (Map.Entry<TerrainType, Directions> entry : m.entrySet()) {
            TerrainType terrainType = entry.getKey();
            Directions directions = entry.getValue();
            // Next paint the actual terrain type
            Point imageCoordinates = directions.getCoordinates();
            terrainImage = terrainImageProvider.get(terrainType).getImage(imageCoordinates);
            // Paint terrain image
            g2.drawImage(terrainImage, pos.x, pos.y, null);
//            repaint(pos.x, pos.y, terrainImage.getWidth(), terrainImage.getHeight());
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int panelWidth = width * hexGridWidth + hexOffset;
        int panelHeight = height * hexRectHeight + hexApotheme + 1;
        return new Dimension(panelWidth, panelHeight);
    }

    private Polygon buildHexagon(int column, int row) {
        Polygon hex = new Polygon();
        Point origin = tileToPixel(column, row);
        hex.addPoint(origin.x + hexOffset, origin.y);
        hex.addPoint(origin.x + hexGridWidth, origin.y);
        hex.addPoint(origin.x + hexRectWidth, origin.y + hexApotheme);
        hex.addPoint(origin.x + hexGridWidth, origin.y + hexRectHeight);
        hex.addPoint(origin.x + hexOffset, origin.y + hexRectHeight);
        hex.addPoint(origin.x, origin.y + hexApotheme);
        return hex;
    }

    public Point tileToPixel(int column, int row) {
        Point pixel = new Point();
        pixel.x = hexGridWidth * column;
        if (Util.isOdd(column)) pixel.y = hexRectHeight * row;
        else pixel.y = hexRectHeight * row + hexApotheme;
        return pixel;
    }

    public Point pixelToTile(int x, int y) {
        double hexRise = (double) hexApotheme / (double) hexOffset;
        Point p = new Point(x / hexGridWidth, y / hexRectHeight);
        Point r = new Point(x % hexGridWidth, y % hexRectHeight);
        Direction direction;
        if (Util.isOdd(p.x)) { //odd column
            if (r.y < -hexRise * r.x + hexApotheme) {
                direction = Direction.NW;
            } else if (r.y > hexRise * r.x + hexApotheme) {
                direction = Direction.SW;
            } else {
                direction = Direction.C;
            }
        } else { //even column
            if (r.y > hexRise * r.x && r.y < -hexRise * r.x + hexRectHeight) {
                direction = Direction.NW;
            } else if (r.y < hexApotheme) {
                direction = Direction.N;
            } else direction = Direction.C;
        }
        return new Point(direction.getNeighborCoordinates(p));
    }

    public boolean tileIsWithinBoard(Point coordinates) {
        int column = coordinates.x;
        int row = coordinates.y;
        return (column >= 0 && column < width) && (row >= 0 && row < height);
    }
}