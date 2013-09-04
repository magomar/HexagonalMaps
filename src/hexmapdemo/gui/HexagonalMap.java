package hexmapdemo.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class HexagonalMap extends JPanel {
    private int width;
    private int height;
    private int hexSide;
    private int hexHeight;
    private int hexRadius;
    private int hexOffset;
    private int hexRectWidth;
    private int hexRectHeight;

    public HexagonalMap(int width, int height, int hexSide) {
        this.width = width;
        this.height = height;
        this.hexSide = hexSide;
        hexRadius = (int) (hexSide * Math.cos(Math.PI / 6));
        hexHeight = (int) (hexSide * Math.sin(Math.PI / 6));
        hexOffset = hexSide + hexHeight;
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
        int panelHeight = height * hexRadius * 2 + hexRadius + 1;
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

    Point tileToPixel(int x, int y) {
        Point pixel = new Point();
        pixel.x = hexOffset * x;
        pixel.y = x % 2 == 0 ? (hexRectHeight * y) + (hexRadius) : (hexRectHeight * y);
        return pixel;
    }

    Point pixelToTile(int x, int y) {
        double hexRise = hexRadius / hexHeight;
        int dy = hexRectHeight / 2;
        Point section = new Point(x / hexOffset, y / hexRectHeight);
        Point pixelInSection = new Point(x % hexOffset, y % hexRectHeight);

        if ((section.x % 2) == 1) {
            //odd column
            if ((-hexRise) * pixelInSection.x + dy > pixelInSection.y) {
                //Pixel is in the NW neighbor tile
                section.x--;
                section.y--;
            } else if (pixelInSection.x * hexRise + dy < pixelInSection.y) {
                //Pixel is in the SE neighbour tile
                section.x--;
            } else {
                //pixel is in our tile
            }
        } else {
            //even column
            if (pixelInSection.y < dy) {
                //upper side
                if ((hexRise * pixelInSection.x) > pixelInSection.y) {
                    // Pixel is in the N neighbor tile
                    section.y--;
                } else {
                    // Pixel is in the upper area of NW neighbor
                    section.x--;
                }
            } else {
                //lower side
                if (((-hexRise) * pixelInSection.x + hexHeight) > pixelInSection.y) {
                    // Pixel is in the lower area of the NW neighbor
                    section.x--;
                } else {
                    // Pixel is in our tile
                }
            }
        }
        return section;
    }

    public boolean tileIsWithinBoard(Point coordinates) {
        int column = coordinates.x;
        int row = coordinates.y;
        return (column >= 0 && column < width) && (row >= 0 && row < height);
    }
}
