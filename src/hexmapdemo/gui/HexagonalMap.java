package hexmapdemo.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class HexagonalMap extends JPanel {
    private int width; // Number of columns
    private int height; // Number of rows
    private int hexSide; // Side of the hexagons (s)
    private int hexHeight; // Height of the hexagons (h)
    private int hexRadius; // Radius of the hexagons (r)
    private int hexOffset;  // Height + Side of the hexagons (h + s)
    private int hexRectWidth; // Width of the minimal rectangle containing an hexagon (b)
    private int hexRectHeight; // Height of the minimal rectangle containing an hexagon (a)

    public HexagonalMap(int width, int height, int hexSide) {
        this.width = width;
        this.height = height;
        this.hexSide = hexSide;
        hexRadius = (int) (hexSide * Math.cos(Math.PI / 6));
        hexHeight = (int) (hexSide * Math.sin(Math.PI / 6));
        hexOffset = hexHeight + hexSide;
        hexRectWidth = 2 * hexHeight + hexSide;
        hexRectHeight = 2 * hexRadius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                g.drawPolygon(buildHexagon(i, j));
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int panelWidth = width * hexOffset + hexHeight;
        int panelHeight = height * hexRectHeight + hexRadius + 1;
        return new Dimension(panelWidth, panelHeight);
    }

    Polygon buildHexagon(int column, int row) {
        Polygon hex = new Polygon();
        Point origin = tileToPixel(column, row);
        hex.addPoint(origin.x + hexHeight, origin.y);
        hex.addPoint(origin.x + hexOffset, origin.y);
        hex.addPoint(origin.x + hexRectWidth, origin.y + hexRadius);
        hex.addPoint(origin.x + hexOffset, origin.y + hexRectHeight);
        hex.addPoint(origin.x + hexHeight, origin.y + hexRectHeight);
        hex.addPoint(origin.x, origin.y + hexRadius);
        return hex;
    }

    Point tileToPixel(int column, int row) {
        Point pixel = new Point();
        pixel.x = hexOffset * column;
        if (Util.isOdd(column)) pixel.y = hexRectHeight * row;
        else pixel.y = hexRectHeight * row + hexRadius;
        return pixel;
    }

    Point pixelToTile(int x, int y) {
        double hexRise = (double) hexRadius / (double) hexHeight;
        Point p = new Point(x / hexOffset, y / hexRectHeight);
        Point r = new Point(x % hexOffset, y % hexRectHeight);
        Direction direction;
        if (Util.isOdd(p.x)) { //odd column
            if (r.y < -hexRise * r.x + hexRadius) {
                direction = Direction.NW;
            } else if (r.y > hexRise * r.x + hexRadius) {
                direction = Direction.SW;
            } else {
                direction = Direction.C;
            }
        } else { //even column
            if (r.y > hexRise * r.x && r.y < -hexRise * r.x + hexRectHeight) {
                direction = Direction.NW;
            } else if (r.y < hexRadius) {
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
